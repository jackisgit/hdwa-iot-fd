package com.wanda.epc.device;

import com.alibaba.fastjson.JSON;
import com.wanda.epc.common.SpringUtil;
import com.wanda.epc.param.DeviceMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * @author 孙率众
 */
@Slf4j
@Component
public class NioClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("客户端收到消息：{}", msg);
            PackageDto packageDto = JSON.parseObject(msg.toString(), PackageDto.class);
            String cmdType = packageDto.getCmdType();
            if (StringUtils.isEmpty(cmdType)) {
                return;
            }
            if (Constant.AA_STATUS.equals(cmdType)) {
                String outParamId = packageDto.getData1() + Constant.DEPLOY_WITH_DRAW_ALARM_SET_FEEDBACK;
                if (Constant.BU_FANG.contains(packageDto.getData2())) {
                    sendMsg(outParamId, "1");
                } else if (Constant.CHE_FANG.contains(packageDto.getData2())) {
                    //如果为撤防，则需要把分区下的所有防区都设置成报警恢复
                    sendMsg(outParamId, "0");
                    final FdDevice fdDevice = SpringUtil.getBean(FdDevice.class);
                    for (Map.Entry<String, List<DeviceMessage>> entry : fdDevice.deviceParamListMap.entrySet()) {
                        if (entry.getKey().startsWith(packageDto.getData1()) && entry.getKey().startsWith(Constant.IS_ALARM)) {
                            for (DeviceMessage deviceMessage : entry.getValue()) {
                                deviceMessage.setValue("0");
                                fdDevice.sendMessage(deviceMessage);
                            }
                        }
                    }
                }
            }
            if (Constant.AZ_STATUS.equals(cmdType)) {
                String outParamId = packageDto.getData1() + Constant.IS_ALARM;
                if (Constant.BAO_JING.contains(packageDto.getData2())) {
                    sendMsg(outParamId, "1");
                } else if (Constant.BAO_JING_HUI_FU.contains(packageDto.getData2())) {
                    sendMsg(outParamId, "0");
                }
            }
        } catch (Exception e) {
            log.error("客户端接收消息异常", e);
        }
    }

    /**
     * 给点位发送信息
     *
     * @param outParamId
     * @param value
     */
    private void sendMsg(String outParamId, String value) {
        final FdDevice fdDevice = SpringUtil.getBean(FdDevice.class);
        List<DeviceMessage> deviceMessageList = fdDevice.deviceParamListMap.get(outParamId);
        if (CollectionUtils.isEmpty(deviceMessageList)) {
            return;
        }
        for (DeviceMessage deviceMessage : deviceMessageList) {
            deviceMessage.setValue(value);
            fdDevice.sendMessage(deviceMessage);
        }
    }

    /**
     * 连接关闭!
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socket.getAddress().getHostAddress();
        int port = socket.getPort();
        log.error("{}连接关闭！", ip + ":" + port);
        final FdDevice fdDevice = SpringUtil.getBean(FdDevice.class);
        fdDevice.reconnect();
        super.channelInactive(ctx);
    }

    /**
     * 客户端主动连接服务端
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            super.channelActive(ctx);
        } catch (Exception e) {
            log.error("客户端连接异常", e);
        }
    }

    /**
     * 发生异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        try {
            super.userEventTriggered(ctx, evt);
        } catch (Exception e) {
            log.error("客户端异常", e);
        }
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                ctx.close();
            }
        }
    }
}

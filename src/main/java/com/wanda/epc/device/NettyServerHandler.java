package com.wanda.epc.device;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.wanda.epc.common.SpringUtil;
import com.wanda.epc.param.DeviceMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 孙率众
 */
@Slf4j
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 已连接客户端集合，key为客户端的Ip+port
     */
    Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("服务端收到消息：{}", msg);
            if (ObjectUtils.isEmpty(msg)) {
                return;
            }
            String message = String.valueOf(msg);
            final Object cmd = JSONPath.read(message, "$.cmd");
            if (ObjectUtils.isEmpty(cmd)) {
                return;
            }
            FdHandler fdHandler = SpringUtil.getBean(FdHandler.class);
            if (cmd.equals(Constant.HEART)) {
            } else if (cmd.equals(Constant.DEVICE_LIST)) {
                final Object deviceList = JSONPath.read(message, "$.data.deviceList");
                if (ObjectUtils.isEmpty(deviceList)) {
                    return;
                }
                final List<Object> list = (List<Object>) deviceList;
                for (Object obj : list) {
                    DeviceDto deviceDto = JSONObject.parseObject(obj.toString(), DeviceDto.class);
                    buildAndSendMsg(deviceDto);
                }
            } else if (cmd.equals(Constant.ALARM_RECEIVE)) {
                final Object alarmReceive = JSONPath.read(message, "$.data");
                if (ObjectUtils.isEmpty(alarmReceive)) {
                    return;
                }
                DeviceDto deviceDto = JSONObject.parseObject(alarmReceive.toString(), DeviceDto.class);
                if (ObjectUtils.isEmpty(deviceDto)) {
                    return;
                }
                List<DeviceMessage> deviceMessages = fdHandler.deviceParamListMap.get(deviceDto.getDeviceId() + "_" + deviceDto.getDeviceType() + "_isAlrm");
                if (CollectionUtils.isEmpty(deviceMessages)) {
                    return;
                }
                for (DeviceMessage deviceMessage : deviceMessages) {
                    deviceMessage.setValue("1");
                    fdHandler.sendMessage(deviceMessage);
                }
            } else if (cmd.equals(Constant.DEVICE_STATUS_CHANGE)) {
                final Object deviceStatusChange = JSONPath.read(message, "$.data");
                if (ObjectUtils.isEmpty(deviceStatusChange)) {
                    return;
                }
                DeviceDto deviceDto = JSONObject.parseObject(deviceStatusChange.toString(), DeviceDto.class);
                if (ObjectUtils.isEmpty(deviceDto)) {
                    return;
                }
                buildAndSendMsg(deviceDto);
            }
        } catch (Exception e) {
            log.error("服务端接收消息异常", e);
        }
    }

    /**
     * 构建和发送消息
     *
     * @param deviceDto
     */
    private void buildAndSendMsg(DeviceDto deviceDto) {
        FdHandler fdHandler = SpringUtil.getBean(FdHandler.class);
        //报警点位集合
        List<DeviceMessage> alarmDeviceMessages = fdHandler.deviceParamListMap.get(deviceDto.getDeviceId() + "_" + deviceDto.getDeviceType() + "_alarmStatus");
        if (!CollectionUtils.isEmpty(alarmDeviceMessages)) {
            String value = "0";
            if (deviceDto.getDeviceStatus() == 3) {
                value = "1";
            }
            for (DeviceMessage deviceMessage : alarmDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //撤布防反馈点位集合
        List<DeviceMessage> dwasFeedbackDeviceMessages = fdHandler.deviceParamListMap.get(deviceDto.getDeviceId() + "_" + deviceDto.getDeviceType() + "_deployWithdrawAlarmSetFeedback");
        if (!CollectionUtils.isEmpty(dwasFeedbackDeviceMessages)) {
            String value = "0";
            if (deviceDto.getArmingStatus() == 1 || deviceDto.getArmingStatus() == 2) {
                value = "1";
            }
            for (DeviceMessage deviceMessage : dwasFeedbackDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //在线状态点位集合
        List<DeviceMessage> onlineDeviceMessages = fdHandler.deviceParamListMap.get(deviceDto.getDeviceId() + "_" + deviceDto.getDeviceType() + "_onlineStatus");
        if (!CollectionUtils.isEmpty(onlineDeviceMessages)) {
            String value = "1";
            if (deviceDto.getDeviceStatus() == 2) {
                value = "0";
            }
            for (DeviceMessage deviceMessage : onlineDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //故障状态点位集合
        List<DeviceMessage> faultDeviceMessages = fdHandler.deviceParamListMap.get(deviceDto.getDeviceId() + "_" + deviceDto.getDeviceType() + "_faultStatus");
        if (!CollectionUtils.isEmpty(faultDeviceMessages)) {
            String value = "0";
            if (deviceDto.getDeviceStatus() == 4) {
                value = "1";
            }
            for (DeviceMessage deviceMessage : faultDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
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
    }

    /**
     * 通道读取完毕事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 服务端连接建立
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socket.getAddress().getHostAddress();
        int port = socket.getPort();
        log.info("有客户端注册IP：{}，PORT：{}", ip, port);
        map.put(ip + port, ctx);
        try {
            super.channelActive(ctx);
        } catch (Exception e) {
            log.error("服务端连接异常", e);
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
            log.error("服务端异常", e);
        }
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                ctx.close();
            }
        }
    }
}

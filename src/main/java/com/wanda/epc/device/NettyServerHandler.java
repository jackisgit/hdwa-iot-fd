package com.wanda.epc.device;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.wanda.epc.DTO.AlarmDto;
import com.wanda.epc.DTO.DeviceDto;
import com.wanda.epc.DTO.ZoneDto;
import com.wanda.epc.common.SpringUtil;
import com.wanda.epc.param.DeviceMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
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
                    String collectMsg = "{\"clientId\":32730,\"cmd\":108005,\"data\":\"{\\\"deviceType\\\":"
                            + deviceDto.getDeviceType() + ",\\\"deviceId\\\":" + deviceDto.getDeviceId() + "}\",\"sn\":49701,\"timeStamp\":1698139999976}\n";
                    log.info("发送查询防区报文为:{}", collectMsg);
                    for (Map.Entry<String, ChannelHandlerContext> entry : map.entrySet()) {
                        entry.getValue().writeAndFlush(Unpooled.copiedBuffer(collectMsg, CharsetUtil.UTF_8));
                    }
                }
            } else if (cmd.equals(Constant.ALARM_RECEIVE)) {
                final Object alarmReceive = JSONPath.read(message, "$.data");
                if (ObjectUtils.isEmpty(alarmReceive)) {
                    return;
                }
                AlarmDto alarmDto = JSONObject.parseObject(alarmReceive.toString(), AlarmDto.class);
                if (ObjectUtils.isEmpty(alarmDto)) {
                    return;
                }
                List<DeviceMessage> deviceMessages = fdHandler.deviceParamListMap.get(
                        alarmDto.getDeviceId() + "_" + alarmDto.getDeviceType() + "_" + alarmDto.getZoneId() + "_isAlarm");
                if (CollectionUtils.isEmpty(deviceMessages)) {
                    return;
                }
                for (DeviceMessage deviceMessage : deviceMessages) {
                    deviceMessage.setValue("1");
                    fdHandler.sendMessage(deviceMessage);
                }
            } else if (cmd.equals(Constant.ZONE_LIST)) {
                final Object zoneList = JSONPath.read(message, "$.data.zoneList");
                final Object deviceId = JSONPath.read(message, "$.data.deviceId");
                final Object deviceType = JSONPath.read(message, "$.data.deviceType");
                if (ObjectUtils.isEmpty(zoneList)) {
                    return;
                }
                final List<Object> list = (List<Object>) zoneList;
                for (Object obj : list) {
                    ZoneDto zoneDto = JSONObject.parseObject(obj.toString(), ZoneDto.class);
                    buildAndSendMsg(deviceId, deviceType, zoneDto);
                }
            }
        } catch (Exception e) {
            log.error("服务端接收消息异常", e);
        }
    }

    /**
     * 构建和发送消息
     *
     * @param zoneDto
     */
    private void buildAndSendMsg(Object deviceId, Object deviceType, ZoneDto zoneDto) {
        FdHandler fdHandler = SpringUtil.getBean(FdHandler.class);
        String prefix = deviceId + "_" + deviceType + "_" + zoneDto.getZoneId();
        //报警点位集合
        List<DeviceMessage> alarmDeviceMessages = fdHandler.deviceParamListMap.get(prefix + "_alarmStatus");
        if (!CollectionUtils.isEmpty(alarmDeviceMessages)) {
            String value = "0";
            if (zoneDto.getZoneStatus() == 3) {
                value = "1";
            }
            for (DeviceMessage deviceMessage : alarmDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //撤布防反馈点位集合
        List<DeviceMessage> dwasFeedbackDeviceMessages = fdHandler.deviceParamListMap.get(prefix + "_deployWithdrawAlarmSetFeedback");
        if (!CollectionUtils.isEmpty(dwasFeedbackDeviceMessages)) {
            String value = "0";
            if (zoneDto.getZoneArmingStatus() == 1 || zoneDto.getZoneArmingStatus() == 2) {
                value = "1";
            }
            for (DeviceMessage deviceMessage : dwasFeedbackDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //在线状态点位集合
        List<DeviceMessage> onlineDeviceMessages = fdHandler.deviceParamListMap.get(prefix + "_onlineStatus");
        if (!CollectionUtils.isEmpty(onlineDeviceMessages)) {
            String value = "1";
            if (zoneDto.getZoneStatus() == 2) {
                value = "0";
            }
            for (DeviceMessage deviceMessage : onlineDeviceMessages) {
                deviceMessage.setValue(value);
                fdHandler.sendMessage(deviceMessage);
            }
        }
        //故障状态点位集合
        List<DeviceMessage> faultDeviceMessages = fdHandler.deviceParamListMap.get(prefix + "_faultStatus");
        if (!CollectionUtils.isEmpty(faultDeviceMessages)) {
            String value = "0";
            if (zoneDto.getZoneStatus() == 4) {
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

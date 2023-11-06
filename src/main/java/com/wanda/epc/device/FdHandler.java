package com.wanda.epc.device;

import com.wanda.epc.param.DeviceMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 孙率众
 */
@Service
@Slf4j
public class FdHandler extends BaseDevice {

    @Resource
    NettyServerHandler nettyServerHandler;
    @Resource
    private CommonDevice commonDevice;


    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    @Override
    public boolean processData() throws Exception {
        String collectMsg = "{\"clientId\":32730,\"cmd\":108009,\"data\":\"\",\"sn\":49698,\"timeStamp\":1657259119841}\n";
        log.info("发送查询设备报文为:{}", collectMsg);
        for (Map.Entry<String, ChannelHandlerContext> entry : nettyServerHandler.map.entrySet()) {
            entry.getValue().writeAndFlush(Unpooled.copiedBuffer(collectMsg, CharsetUtil.UTF_8));
        }
        return true;
    }

    /**
     * 控制
     *
     * @param deviceId
     * @param deviceType
     * @param armingAction 1、外出布防 2、在家布防 3、撤防状态 4、布防延时
     */
    public void control(int deviceId, int deviceType, int zoneId, int armingAction) {
        String controlMsg = "{\"clientId\":32730,\"cmd\":108006,\"data\":\"{\\\"armingAction\\\":" + armingAction + ",\\\"deviceId\\\":" + deviceId + ",\\\"zoneId\\\":" + zoneId + ",\\\"deviceType\\\":" + deviceType + "}\",\"sn\":49698,\"timeStamp\":1657259119841}\n";
        log.info("发送撤布防指令设备id为:{},设备类型为:{},防区为:{},命令为:{},完整报文为:{}", deviceId, deviceType, zoneId, armingAction, controlMsg);
        for (Map.Entry<String, ChannelHandlerContext> entry : nettyServerHandler.map.entrySet()) {
            entry.getValue().writeAndFlush(Unpooled.copiedBuffer(controlMsg, CharsetUtil.UTF_8));
        }
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        if (ObjectUtils.isNotEmpty(deviceMessage) && StringUtils.isNotBlank(deviceMessage.getOutParamId())
                && deviceMessage.getOutParamId().endsWith("_deployWithdrawAlarmSet")) {
            String outParamId = deviceMessage.getOutParamId();
            if (redisUtil.hasKey(outParamId)) {
                return;
            }
            redisUtil.set(outParamId, "0", 5);
            final String[] strings = deviceMessage.getOutParamId().split("_");
            int command;
            if ("1.0".equals(value)) {
                command = 1;
            } else {
                command = 3;
            }
            control(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]), command);
        }
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

}

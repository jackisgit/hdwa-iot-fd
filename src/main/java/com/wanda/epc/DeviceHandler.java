package com.wanda.epc;

import com.alibaba.fastjson.JSON;
import com.netsdk.lib.enumeration.EM_DEV_STATUS;
import com.netsdk.lib.enumeration.EM_ZONE_STATUS;
import com.netsdk.lib.structure.NET_CHANNELS_STATE;
import com.netsdk.lib.structure.NET_OUT_GET_CHANNELS_STATE;
import com.netsdk.lib.structure.NET_OUT_GET_ZONE_ARMODE_INFO;
import com.wanda.epc.device.BaseDevice;
import com.wanda.epc.device.CommonDevice;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 大华设备
 * @date 2023/4/11 09:55:13
 */
@Slf4j
@Service
public class DeviceHandler extends BaseDevice {

    @Resource
    ZoneArmMode zoneArmMode;
    @Resource
    private CommonDevice commonDevice;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        zoneArmMode.initTest();
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        log.info("SDK实例销毁!");
        zoneArmMode.endTest();
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    public boolean processData() throws Exception {
        getArmMode();
        getChannelsState();
        zoneArmMode.startListenAlarm();
        return true;
    }

    /**
     * 获取撤布防状态
     */
    private void getArmMode() {
        NET_OUT_GET_ZONE_ARMODE_INFO outPut = zoneArmMode.getZoneArmMode();
        int nStateNum = outPut.nStateNum;
        log.info("nStateNum :" + nStateNum);
        byte[] szState = outPut.szState;

        for (int i = 0; i < nStateNum; i++) {
            byte[] tmp = new byte[32];
            System.arraycopy(szState, i * 32, tmp, 0, 32);
            String text = new String(tmp).trim();
            log.info("[" + i + "] = " + text);
            String value = "0";
            if ("T".equalsIgnoreCase(text)) {
                value = "1";
            }
            sendMsg((i + 1) + "_deployWithdrawAlarmStatus", value);
        }
    }

    /**
     * 获取在离线/报警
     */
    private void getChannelsState() {
        NET_OUT_GET_CHANNELS_STATE stuOut = zoneArmMode.channelsState();
        if (ObjectUtils.isEmpty(stuOut)) {
            return;
        }
        log.info("通道状态个数:" + stuOut.nChannelsStatesCount);
        NET_CHANNELS_STATE[] stuChannelsStates = stuOut.stuChannelsStates;
        for (int i = 0; i < stuOut.nChannelsStatesCount; i++) {
            String onlineStatus = "1";
            if (EM_DEV_STATUS.EM_DEV_STATUS_OFFLINE.getValue() == stuChannelsStates[i].emOnlineState) {
                onlineStatus = "0";
            }
            sendMsg((i + 1) + "_onlineStatus", onlineStatus);
            String alarmStatus = "0";
            if (EM_ZONE_STATUS.EM_ZONE_STATUS_ALARM.getValue() == stuChannelsStates[i].emAlarmState) {
                alarmStatus = "1";
            }
            sendMsg((i + 1) + "_alarmStatus", alarmStatus);
            log.info("Area号:" + (i + 1));
            log.info("在线状态:" + EM_DEV_STATUS.getNoteByValue(stuChannelsStates[i].emOnlineState));
            log.info("报警状态:" + EM_ZONE_STATUS.getNoteByValue(stuChannelsStates[i].emAlarmState));
        }
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接收到指令下发：{},状态：{}", JSON.toJSONString(deviceMessage), value);
        if (ObjectUtils.isEmpty(deviceMessage) || !deviceMessage.getOutParamId().contains("_deployWithdrawAlarmSet")) {
            return;
        }
        String outParamId = deviceMessage.getOutParamId();
        String[] param = outParamId.split("_");
        Integer zoneNo = Integer.valueOf(param[0]);
        String armMode = "D";
        if (!value.equals("1.0")) {
            armMode = "T";
        }
        zoneArmMode.setZoneArmMode(zoneNo, armMode);
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    /**
     * 发送消息
     *
     * @param outParamId
     * @param value
     */
    public void sendMsg(String outParamId, String value) {
        List<DeviceMessage> deviceMessageList = deviceParamListMap.get(outParamId);
        if (!CollectionUtils.isEmpty(deviceMessageList)) {
            deviceMessageList.forEach(deviceMessage -> {
                deviceMessage.setValue(value);
                sendMessage(deviceMessage);
            });
        }
    }

}

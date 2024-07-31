package com.wanda.epc;

import com.alibaba.fastjson.JSON;
import com.netsdk.lib.enumeration.EM_DEV_STATUS;
import com.netsdk.lib.enumeration.EM_ZONE_STATUS;
import com.netsdk.lib.structure.*;
import com.wanda.epc.device.BaseDevice;
import com.wanda.epc.device.CommonDevice;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 孙率众
 */
@Slf4j
@Service
public class DeviceHandler extends BaseDevice {
    public static final String FAULT_STATUS = "_faultStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_STATUS = "_deployWithdrawAlarmStatus";
    public static final String ONLINE_STATUS = "_onlineStatus";
    public static final String ALARM_STATUS = "_alarmStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_SET = "_deployWithdrawAlarmSet";
    @Resource
    ZoneArmMode zoneArmMode;
    @Resource
    private CommonDevice commonDevice;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        zoneArmMode.init();
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        zoneArmMode.end();
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
        getAlarmregionInfoChannelsstate();
        return true;
    }

    private void getAlarmregionInfoChannelsstate() {
        //正常防区
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, List<DeviceMessage>> entry : deviceParamListMap.entrySet()) {
            if (entry.getKey().contains(FAULT_STATUS)) {
                set.add(entry.getKey());
            }
        }
        log.info("查询防区故障状态");
        NET_OUT_GET_AREAS_STATUS stuOut = zoneArmMode.getAlarmregionInfoChannelsstate();
        if (ObjectUtils.isEmpty(stuOut)) {
            return;
        }
        //区域个数
        int nAreaRet = stuOut.nAreaRet;
        log.info("防区个数:" + nAreaRet);
        if (nAreaRet <= 8) {
            NET_AREA_STATUS[] stuAreaStatus = stuOut.stuAreaStatus;
            for (int i = 0; i < nAreaRet; i++) {
                NET_AREA_STATUS stuAreaStatus1 = stuAreaStatus[i];
                int nZoneRet = stuAreaStatus1.nZoneRet;
                NET_ZONE_STATUS[] stuZoneStatus = stuAreaStatus1.stuZoneStatus;
                for (int j = 0; j < nZoneRet; j++) {
                    NET_ZONE_STATUS stuZoneStatus1 = stuZoneStatus[j];
                    log.info("防区" + "[" + i + "]" + "[" + j + "]:" + stuZoneStatus1.nIndex + "," + EM_ZONE_STATUS.getNoteByValue(stuZoneStatus1.emStatus));
                    String key = i + FAULT_STATUS;
                    sendMsg(key, "1");
                    set.remove(key);

                    //单个设备
                    String key2 = stuZoneStatus1.nIndex + FAULT_STATUS;
                    sendMsg(key2, "1");
                    set.remove(key2);
                }
            }
        } else {
            NET_AREA_STATUS_EX[] stuAreaStatusEx = stuOut.stuAreaStatusEx;
            for (int i = 0; i < nAreaRet; i++) {
                NET_AREA_STATUS_EX stuAreaStatus1 = stuAreaStatusEx[i];
                int nZoneRetEx = stuAreaStatus1.nZoneRetEx;
                NET_ZONE_STATUS[] stuZoneStatus = stuAreaStatus1.stuZoneStatusEx;
                for (int j = 0; j < nZoneRetEx; j++) {
                    NET_ZONE_STATUS stuZoneStatus1 = stuZoneStatus[j];
                    log.info("防区[" + "[" + i + "]" + "[" + j + "]" + "]:" + stuZoneStatus1.nIndex + "," + EM_ZONE_STATUS.getNoteByValue(stuZoneStatus1.emStatus));
                    String key = i + FAULT_STATUS;
                    sendMsg(key, "1");
                    set.remove(key);

                    String key2 = stuZoneStatus1.nIndex + FAULT_STATUS;
                    sendMsg(key2, "1");
                    set.remove(key2);
                }
            }
        }
        for (String str : set) {
            sendMsg(str, "0");
        }
    }

    /**
     * 获取撤布防状态
     */
    private void getArmMode() {
        NET_OUT_GET_ZONE_ARMODE_INFO outPut = zoneArmMode.getZoneArmMode();
        int nStateNum = outPut.nStateNum;
        byte[] szState = outPut.szState;

        for (int i = 0; i < nStateNum; i++) {
            byte[] tmp = new byte[32];
            System.arraycopy(szState, i * 32, tmp, 0, 32);
            String text = new String(tmp).trim();
            String value = "0";
            if ("T".equalsIgnoreCase(text)) {
                value = "1";
            }
            sendMsg((i + 1) + DEPLOY_WITHDRAW_ALARM_STATUS, value);
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
            sendMsg((i + 1) + ONLINE_STATUS, onlineStatus);
            String alarmStatus = "0";
            if (EM_ZONE_STATUS.EM_ZONE_STATUS_ALARM.getValue() == stuChannelsStates[i].emAlarmState) {
                alarmStatus = "1";
            }
            sendMsg((i + 1) + ALARM_STATUS, alarmStatus);
        }
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接收到指令下发：{},状态：{}", JSON.toJSONString(deviceMessage), value);
        if (ObjectUtils.isEmpty(deviceMessage) || !deviceMessage.getOutParamId().contains(DEPLOY_WITHDRAW_ALARM_SET)) {
            return;
        }
        String outParamId = deviceMessage.getOutParamId();
        String[] param = outParamId.split("_");
        Integer zoneNo = Integer.valueOf(param[0]);
        //D是撤防，T是布防
        String armMode = "D";
        if (value.equals("1.0")) {
            armMode = "T";
        }
        zoneArmMode.setZoneArmMode(zoneNo, armMode);
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

}

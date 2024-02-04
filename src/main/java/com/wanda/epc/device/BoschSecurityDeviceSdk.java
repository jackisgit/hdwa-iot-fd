package com.wanda.epc.device;

import com.wanda.epc.common.RedisUtil;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("boschSecurityDeviceSdk")
@Slf4j
public class BoschSecurityDeviceSdk extends BaseDevice implements BostFdDataArriveListener {

    public static final String IS_ALARM = "_isAlarm";
    public static final String DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK = "_deployWithdrawAlarmSetFeedback";
    public static final String ONLINE_STATUS = "_onlineStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_SET = "deployWithdrawAlarmSet";
    @Autowired
    CommonDevice commonDevice;

    @Autowired
    RedisUtil redisUtil;
    Thread thread;
    @Autowired
    private BosFdCommunicator bosFdCommunicator;

    @Value("${epc.onlineStatusCheckTime}")
    private Long onlineStatusCheckTime;

    @Override
    public void sendMessage(DeviceMessage dm) {
        //如果数据变化则，发送emqx
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    @PostConstruct
    public boolean processData() throws Exception {
        this.bosFdCommunicator.addDataArriveListener(this);
        this.thread = new Thread((Runnable) this.bosFdCommunicator);
        this.thread.start();
        return true;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接收到防盗报警撤布防指令：meter:{},funcId：{},value:{},deviceMessage:{}", meter, funcid, value, message);
        if (deviceMessage != null && deviceMessage.getOutParamId() != null && deviceMessage.getOutParamId().endsWith(DEPLOY_WITHDRAW_ALARM_SET)) {
            if (redisUtil.hasKey(deviceMessage.getOutParamId())) {
                return;
            }
            redisUtil.set(deviceMessage.getOutParamId(), "0", 5);
            try {
                String outParamId = deviceMessage.getOutParamId();
                String[] split = outParamId.split("_");
                String fenqu = split[0];
                String command;
                String desc;
                if ("1.0".equals(value)) {
                    command = "ARM";
                    desc = "布防";
                } else {
                    command = "DISARMWAY";
                    desc = "撤防";
                }
                BosFdData data = new BosFdData();
                data.setType(0);
                data.setsPara(fenqu);
                data.setCommand(command);
                data.setDesc(desc);
                log.info("开始调用子系统做撤布防操作====");
                this.bosFdCommunicator.write(data);
            } catch (Exception e) {
                log.info("防盗报警控制命令下发失败：" + e.getMessage());
            }
        }
    }


    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    @Override
    public void dataArrive(String message) {
        log.info("接收到主机传回的事件信息" + message);
        if (!StringUtils.hasText(message)) {
            return;
        }
        String[] ms = message.split("><");
        if (ms.length != 6) {
            return;
        }
        String serviceIP = ms[0].replace("<", "");
        String time = ms[1];
        String access = ms[2];
        String eventCode = ms[3];
        String fenqu = ms[4];
        String fangqu = ms[5].replace(">", "");
        String onlineStatusOutParamId = fenqu + "_" + fangqu + ONLINE_STATUS;
        if ("0000".equals(eventCode)) {
            //所有点位均设置为正常
            //撤布防状态变为撤防，报警状态变为正常
            Iterator<Map.Entry<String, List<DeviceMessage>>> iterator = deviceParamListMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<DeviceMessage>> next = iterator.next();
                if (next.getKey().endsWith(IS_ALARM)) {
                    for (DeviceMessage deviceMessage : next.getValue()) {
                        deviceMessage.setValue("0");
                        commonDevice.sendMessage(deviceMessage);
                    }
                } else if (next.getKey().endsWith(DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK)) {
                    for (DeviceMessage deviceMessage : next.getValue()) {
                        deviceMessage.setValue("0");
                        commonDevice.sendMessage(deviceMessage);
                    }
                } else if (next.getKey().endsWith(ONLINE_STATUS)) {
                    for (DeviceMessage deviceMessage : next.getValue()) {
                        deviceMessage.setValue("1");
                        commonDevice.sendMessage(deviceMessage);
                    }
                    redisUtil.set(next.getKey(), "1", onlineStatusCheckTime);
                }
            }
        } else if (eventCode.startsWith("11")) {
            sendMsg(fangqu + IS_ALARM, "1");
            sendOnlineStatus(onlineStatusOutParamId);
        } else if ("1615,1465,3465,3642,3654,3344".contains(eventCode) || eventCode.startsWith("313") || eventCode
                .startsWith("314") || eventCode.startsWith("315") || eventCode.startsWith("316")) {
            sendMsg(fangqu + IS_ALARM, "0");
            sendOnlineStatus(onlineStatusOutParamId);
        } else if ("1408,1441,1456".contains(eventCode) || eventCode.startsWith("340")) {
            sendMsg(fenqu + DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK, "1");
            sendOnlineStatus(onlineStatusOutParamId);
        } else if (eventCode.startsWith("140") && !"1408,1441,1456".contains(eventCode)) {
            sendMsg(fenqu + DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK, "0");
            sendOnlineStatus(onlineStatusOutParamId);
        } else {
            log.warn("未处理事件，中心IP：" + serviceIP + "，事件：" + time + "，账号：" + access + "，事件代码：" + eventCode + "，分区：" + fenqu + "，防区：" + fangqu);
        }
    }

    /**
     * 发送在线
     * @param onlineStatusOutParamId
     */
    private void sendOnlineStatus(String onlineStatusOutParamId) {
        sendMsg(onlineStatusOutParamId, "1");
        redisUtil.set(onlineStatusOutParamId, "1", onlineStatusCheckTime);
    }

}

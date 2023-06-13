package com.wanda.epc.device;

import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class BoshiFD extends BaseDevice {

    @Autowired
    CommonDevice commonDevice;

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            //log.info("发送消息[{}]", dm);
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    public boolean processData() throws Exception {
        log.info("定时任务开始执行");
        Iterator<Map.Entry<String, DeviceMessage>> iterator = super.deviceParamMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DeviceMessage> next = iterator.next();
            String outParamId = next.getKey();
            DeviceMessage deviceMessage = next.getValue();
            if (StringUtils.isEmpty(deviceMessage.getValue())) {
                if (outParamId.endsWith("_alarmStatus")) {
                    deviceMessage.setValue("0");
                } else if (outParamId.endsWith("_faultStatus")) {
                    deviceMessage.setValue("0");
                } else if (outParamId.endsWith("_defenceStatus")) {
                    deviceMessage.setValue("0");
                } else if (outParamId.endsWith("_onlineStatus")) {
                    deviceMessage.setValue("1");
                }
            }
            deviceMessage.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
            sendMessage(deviceMessage);
            log.info("发送" + outParamId + "数据值为：" + deviceMessage.getValue());
        }
        log.info("定时任务执行完成");
        return true;
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
    }

    public void dataArrive(byte[] buff) {
        log.info("收到防盗报警数据:" + toHex(buff, 0, buff.length));
        if (buff.length % 3 == 0) {

        } else if (buff.length % 4 == 0) {
            int count = buff.length / 4; //需要执行次数 因为有可能上来不是一条指令
            for (int i = 1; i <= count; i++) {
                if (buff[4 * (i - 1)] == -123) { //85
                    int fqNumber = (buff[i * 4 - 2] & 0xFF) + 1; //防区号
                    int status = buff[i * 4 - 3];
                   /* String outParamId = "ipec_fd_" + fqNumber + "_onlineStatus";
                    //在线状态
                    DeviceMessage deviceMessage = deviceParamMap.get(outParamId);
                    if(deviceMessage != null){
                        deviceMessage.setValue("0");
                        sendMessage(deviceMessage);
                        super.deviceParamMap.put(outParamId, deviceMessage);
                    }*/
                    if (status == 1 || status == 2 || status == 3 || status == 4 || status == 5 || status == 6 || status == 7 ||
                            status == 28 || status == 29 || status == 30) {
                        log.info("第[{}]防区报警,报警状态为[{}],报警描述[{}]", fqNumber, status, dataDesc(status));
                        String outParamId1 = "ipec_fd_" + fqNumber + "_alarmStatus";
                        //报警状态
                        DeviceMessage deviceMessage1 = deviceParamMap.get(outParamId1);
                        if (deviceMessage1 != null) {
                            deviceMessage1.setValue("1");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }
                    if (status == 17 || status == 31 || status == 32 || status == 33 || status == 12) {
                        log.info("第[{}]防区报警恢复,描述[{}]", fqNumber, dataDesc(status));
                        String outParamId1 = "ipec_fd_" + fqNumber + "_alarmStatus";
                        //报警状态恢复
                        DeviceMessage deviceMessage1 = deviceParamMap.get(outParamId1);
                        if (deviceMessage1 != null) {
                            deviceMessage1.setValue("0");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }

                    /*if(status == 8 || status == 9 || status == 10 || status == 11 || status == 23 || status == 24){
                        log.info("第[{}]防区故障,故障状态为[{}],故障描述[{}]", fqNumber, status,  dataDesc(status));
                        String outParamId1 = "ipec_fd_" + fqNumber + "_faultStatus";
                        //故障状态
                        DeviceMessage deviceMessage1 = deviceParamMap.get("ipec_fd_" + fqNumber + "_faultStatus");
                        if(deviceMessage1 != null) {
                            deviceMessage1.setValue("1");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }
                    if(status == 22 || status == 31 || status == 32 || status == 33 ){
                        log.info("第[{}]防区故障恢复", fqNumber);
                        String outParamId1 = "ipec_fd_" + fqNumber + "_faultStatus";
                        //故障状态恢复
                        DeviceMessage deviceMessage1 = deviceParamMap.get(outParamId1);
                        if(deviceMessage1 != null) {
                            deviceMessage1.setValue("0");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }*/

                    if (status == 13) {
                        log.info("第[{}]防区撤防操作成功", fqNumber);
                        String outParamId1 = "ipec_fd_" + fqNumber + "_defenceStatus";
                        //撤防状态
                        DeviceMessage deviceMessage1 = deviceParamMap.get("ipec_fd_" + fqNumber + "_defenceStatus");
                        if (deviceMessage1 != null) {
                            deviceMessage1.setValue("0");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }
                    if (status == 14) {
                        log.info("第[{}]防区布防操作成功", fqNumber);
                        String outParamId1 = "ipec_fd_" + fqNumber + "_defenceStatus";
                        //布防状态
                        DeviceMessage deviceMessage1 = deviceParamMap.get(outParamId1);
                        if (deviceMessage1 != null) {
                            deviceMessage1.setValue("1");
                            sendMessage(deviceMessage1);
                            super.deviceParamMap.put(outParamId1, deviceMessage1);
                        }
                    }

                    if (status == 19) {
                        log.info("第[{}]防区正常状态", fqNumber);
                        String outParamId1 = "ipec_fd_" + fqNumber + "_faultStatus";
                        //故障状态
                        DeviceMessage deviceMessage2 = deviceParamMap.get(outParamId1);
                        if (deviceMessage2 != null) {
                            deviceMessage2.setValue("0");
                            sendMessage(deviceMessage2);
                            super.deviceParamMap.put(outParamId1, deviceMessage2);
                        }

                        String outParamId2 = "ipec_fd_" + fqNumber + "_alarmStatus";
                        //报警状态
                        DeviceMessage deviceMessage3 = deviceParamMap.get(outParamId2);
                        if (deviceMessage3 != null) {
                            deviceMessage3.setValue("0");
                            sendMessage(deviceMessage3);
                            super.deviceParamMap.put(outParamId2, deviceMessage3);
                        }
                    }
                }
            }
        }
    }

    /**
     * 进制转换
     *
     * @param data
     * @param off
     * @param length
     * @return
     */
    private String toHex(byte[] data, int off, int length) {
        StringBuffer buf = new StringBuffer(data.length * 2);
        for (int i = off; i < length; i++) {
            if ((data[i] & 0xFF) < 16)
                buf.append("0");
            buf.append(Long.toString((data[i] & 0xFF), 16));
            if (i < data.length - 1)
                buf.append(" ");
        }
        return buf.toString();
    }


    /**
     * 数据备注
     *
     * @param status
     * @return
     */
    private String dataDesc(int status) {
        String desc = "";
        switch (status) {
            case 1:
                desc = "火警报警";
                break;
            case 2:
                desc = "键盘火警报警";
                break;
            case 3:
                desc = "键盘紧急报警";
                break;
            case 4:
                desc = "键盘求助报警";
                break;
            case 5:
                desc = "胁持码操作";
                break;
            case 6:
                desc = "未授权进入";
                break;
            case 7:
                desc = "盗警防区报警";
                break;
            case 8:
                desc = "监测故障";
                break;
            case 9:
                desc = "火警故障";
                break;
            case 10:
                desc = "火警防区故障";
                break;
            case 11:
                desc = "盗警防区故障";
                break;
            case 12:
                desc = "消警";
                break;
            case 13:
                desc = "撤防操作成功";
                break;
            case 14:
                desc = "布防操作成功";
                break;
            case 15:
                desc = "强制旁路操作";
                break;
            case 16:
                desc = "防区旁路操作";
                break;
            case 17:
                desc = "防区报警恢复";
                break;
            case 18:
                desc = "布防操作失败";
                break;
            case 19:
                desc = "撤防操作失败";
                break;
            case 20:
                desc = "时间记录满";
                break;
            case 21:
                desc = "测试信息";
                break;
            case 22:
                desc = "防区故障恢复";
                break;
            case 23:
                desc = "防区短路状态";
                break;
            case 24:
                desc = "防区开路状态";
                break;
            case 25:
                desc = "防区正常状态";
                break;
            case 26:
                desc = "MX设备布防";
                break;
            case 27:
                desc = "MX设备撤防";
                break;
            case 28:
                desc = "MX设备1防区报警";
                break;
            case 29:
                desc = "MX设备2防区报警";
                break;
            case 30:
                desc = "MX设备3防区报警";
                break;
            case 31:
                desc = "MX设备1防区恢复";
                break;
            case 32:
                desc = "MX设备2防区恢复";
                break;
            case 33:
                desc = "MX设备3防区恢复";
                break;
        }
        return desc;
    }


}

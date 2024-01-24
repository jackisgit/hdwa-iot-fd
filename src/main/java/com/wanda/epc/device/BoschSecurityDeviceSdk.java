package com.wanda.epc.device;

import com.wanda.epc.common.RedisUtil;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;


@Service("boschSecurityDeviceSdk")
@Slf4j
public class BoschSecurityDeviceSdk extends BaseDevice implements BostFdDataArriveListener {

    public static final String DEPLOY_WITHDRAW_ALARM_SET = "deployWithdrawAlarmSet";
    public static final String DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK = "_deployWithdrawAlarmSetFeedback";
    @Autowired
    CommonDevice commonDevice;

    @Autowired
    RedisUtil redisUtil;
    Thread thread;
    @Autowired
    private BosFdCommunicator bosFdCommunicator;

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
                commonDevice.feedback(message);
                return;
            }
            redisUtil.set(deviceMessage.getOutParamId(), "0", 5);
            try {
                String outParamId = deviceMessage.getOutParamId();
                String[] split = outParamId.split("_");
                String fenqu = split[0];
                String command = "";
                String desc = "";
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
                //因为上饶子系统不发送撤布防反馈，所以直接在下发撤布防指令完成后直接往反馈点反馈
                String feedBackOutParamId = fenqu + DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK;
                sendMsg(feedBackOutParamId, value);
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
        if (StringUtils.hasText(message)) {
            String[] ms = message.split("><");
            if (ms.length == 6) {
                String serviceIP = ms[0].replace("<", "");
                String time = ms[1];
                String access = ms[2];
                String eventCode = ms[3];
                String fenqu = ms[4];
                String fangqu = ms[5].replace(">", "");
                if (eventCode.equals("1137")) {
                    /*String outParamId = fenqu + "_" + fangqu + "_tamperAlarm";
                    DeviceMessage deviceMessage = super.deviceParamMap.get(outParamId);
                    if(deviceMessage != null){
                        deviceMessage.setValue("1"); //1为拆动报警
                        sendMessage(deviceMessage);
                    }*/
                    log.info("防盗报警 分区防区号:" + fenqu + "_" + fangqu + "强拆报警");
                } else if (eventCode.equals("3341")) {
                   /* String outParamId = fenqu + "_" + fangqu + "_tamperAlarm";
                    DeviceMessage deviceMessage = super.deviceParamMap.get(outParamId);
                    if(deviceMessage != null){
                        deviceMessage.setValue("0"); //0为拆动报警恢复
                        sendMessage(deviceMessage);
                    }*/
                    log.info("防盗报警 分区防区号:" + fenqu + "_" + fangqu + "强拆报警恢复");
                } else if (eventCode.startsWith("11")) {
                    String outParamId = fangqu + "_isAlarm";
                    sendMsg(outParamId,"1");
                    log.info("防盗报警 分区防区号" + fenqu + "_" + fangqu + "  报警码：" + eventCode + "  报警信息：" + Alarm(eventCode));
                } else if ("1615,1465,3465,3642,3654,3344".contains(eventCode) || eventCode.startsWith("313") || eventCode
                        .startsWith("314") || eventCode.startsWith("315") || eventCode.startsWith("316")) {
                    String outParamId = fangqu + "_isAlarm";
                    sendMsg(outParamId,"0");
                    log.info("防盗报警 分区防区号" + fenqu + "_" + fangqu + "报警恢复");
                } else if (eventCode.equals("3400") || eventCode.equals("3401") || eventCode.equals("3456") || eventCode
                        .equals("1408") || eventCode.equals("1456") || eventCode.equals("1441") || eventCode
                        .startsWith("34")) {
                    String outParamId = fenqu + DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK;
                    sendMsg(outParamId,"1");
                    log.info("防盗报警 分区号:" + fenqu + "布防");
                } else if (eventCode.startsWith("140") && !eventCode.equals("1405") && !eventCode.equals("1406")) {
                    String outParamId = fenqu + DEPLOY_WITHDRAW_ALARM_SET_FEEDBACK;
                    sendMsg(outParamId,"0");
                    log.info("防盗报警 分区号：" + fenqu + "撤防");
                } else {
                    log.warn("未处理事件，中心IP：" + serviceIP + "，事件：" + time + "，账号：" + access + "，事件代码：" + eventCode + "，分区：" + fenqu + "，防区：" + fangqu);
                }
            }
        }
    }

    private String Alarm(String code) {
        String alarmName = "";
        if (code.equals("1100")) {
            alarmName = "个人救护报警";
        } else if (code.equals("1101")) {
            alarmName = "个人紧急救护警报";
        } else if (code.equals("1102")) {
            alarmName = "报到失败";
        } else if (code.equals("1103")) {
            alarmName = "救护警报";
        } else if (code.equals("1110")) {
            alarmName = "火警";
        } else if (code.equals("1111")) {
            alarmName = "烟感探头";
        } else if (code.equals("1112")) {
            alarmName = "燃烧";
        } else if (code.equals("1113")) {
            alarmName = "消防水流";
        } else if (code.equals("1114")) {
            alarmName = "热感探头";
        } else if (code.equals("1115")) {
            alarmName = "火警手动报警";
        } else if (code.equals("1116")) {
            alarmName = "空调槽烟感";
        } else if (code.equals("1117")) {
            alarmName = "火焰探头";
        } else if (code.equals("1118")) {
            alarmName = "接近警报";
        } else if (code.equals("1120")) {
            alarmName = "紧急求助报警";
        } else if (code.equals("1121")) {
            alarmName = "挟持报警";
        } else if (code.equals("1122")) {
            alarmName = "无声劫盗";
        } else if (code.equals("1123")) {
            alarmName = "有声劫盗";
        } else if (code.equals("1130")) {
            alarmName = "窃盗";
        } else if (code.equals("1131")) {
            alarmName = "跨越周界";
        } else if (code.equals("1132")) {
            alarmName = "内部防区";
        } else if (code.equals("1133")) {
            alarmName = "24小时防区";
        } else if (code.equals("1134")) {
            alarmName = "出/入防区";
        } else if (code.equals("1135")) {
            alarmName = "日/夜防区";
        } else if (code.equals("1136")) {
            alarmName = "室外";
        } else if (code.equals("1137")) {
            alarmName = "拆动报警";
        } else if (code.equals("1140")) {
            alarmName = "报警(用户防区被触发)";
        } else if (code.equals("1141")) {
            alarmName = "总线开路";
        } else if (code.equals("1142")) {
            alarmName = "总线短路";
        } else if (code.equals("1144")) {
            alarmName = "探头被拆动";
        } else if (code.equals("1145")) {
            alarmName = "扩展模型被拆";
        } else if (code.equals("1150")) {
            alarmName = "24小时非窃盗报警";
        } else if (code.equals("1151")) {
            alarmName = "气体检测";
        } else if (code.equals("1152")) {
            alarmName = "冷藏器";
        } else if (code.equals("1153")) {
            alarmName = "热量散失";
        } else if (code.equals("1154")) {
            alarmName = "漏水";
        } else if (code.equals("1155")) {
            alarmName = "箔片破损";
        } else if (code.equals("1156")) {
            alarmName = "日间防区故障";
        } else if (code.equals("1157")) {
            alarmName = "气体水平过低";
        } else if (code.equals("1158")) {
            alarmName = "温度过高";
        } else if (code.equals("1159")) {
            alarmName = "温度过低";
        } else if (code.equals("1161")) {
            alarmName = "空气流动";
        } else if (code.equals("1200")) {
            alarmName = "火警监视";
        } else if (code.equals("1202")) {
            alarmName = "二氧化碳过低";
        } else if (code.equals("1203")) {
            alarmName = "阀门感应";
        } else if (code.equals("1204")) {
            alarmName = "水平面过低";
        } else if (code.equals("1205")) {
            alarmName = "水泵开动";
        } else if (code.equals("1206")) {
            alarmName = "水泵故障";
        } else if (code.equals("1300")) {
            alarmName = "系统故障";
        } else if (code.equals("1301")) {
            alarmName = "无交流电源";
        } else if (code.equals("1302")) {
            alarmName = "电池电压过低";
        } else if (code.equals("1303")) {
            alarmName = "RAM校验和错误";
        } else if (code.equals("1304")) {
            alarmName = "ROM校验和错误";
        } else if (code.equals("1305")) {
            alarmName = "系统重新启动";
        } else if (code.equals("1306")) {
            alarmName = "主机编程被改动";
        } else if (code.equals("1307")) {
            alarmName = "自检失败";
        } else if (code.equals("1308")) {
            alarmName = "主机停机";
        } else if (code.equals("1381")) {
            alarmName = "无线终端监控故障";
        } else if (code.equals("1382")) {
            alarmName = "总线监控故障";
        } else if (code.equals("1383")) {
            alarmName = "探头被拆动";
        } else if (code.equals("1384")) {
            alarmName = "无线感应器电池过低";
        } else if (code.equals("1309")) {
            alarmName = "电池测试故障";
        } else if (code.equals("1310")) {
            alarmName = "接地故障";
        } else if (code.equals("1320")) {
            alarmName = "警号/继电器故障";
        } else if (code.equals("1321")) {
            alarmName = "警铃 #1故障";
        } else if (code.equals("1322")) {
            alarmName = "警铃 #2故障";
        } else if (code.equals("1323")) {
            alarmName = "警报继电器故障";
        } else if (code.equals("1324")) {
            alarmName = "故障继电器";
        } else if (code.equals("1325")) {
            alarmName = "逆转继电器";
        } else if (code.equals("1330")) {
            alarmName = "系统外部设备故障";
        } else if (code.equals("1333")) {
            alarmName = "扩展模块失败";
        } else if (code.equals("1334")) {
            alarmName = "转发器故障";
        }
        return alarmName;
    }


}

package com.wanda.epc;

import com.alibaba.fastjson.JSON;
import com.netsdk.lib.NetSDKLib;
import com.wanda.epc.device.BaseDevice;
import com.wanda.epc.device.CommonDevice;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.PingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;

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

    HashMap<String, NetSDKLib.LLong> userMap = new HashMap<>();
    @Resource
    ZoneArmModeDemo zoneArmModeDemo;
    @Resource
    private CommonDevice commonDevice;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        zoneArmModeDemo.initTest();
        zoneArmModeDemo.runTest();
    }
    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        log.info("SDK实例销毁!");
        zoneArmModeDemo.endTest();
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    public boolean processData() throws Exception {
        Queue<String> allIp = new LinkedList();
        deviceParamListMap.entrySet().forEach(entry -> {
            List<String> ipList = Arrays.asList(entry.getKey().split("_"));
            if (ipList.size() == 2) {
                String online = ipList.get(1);
                if ("onlineStatus".equals(online)) {
                    allIp.offer(ipList.get(0));
                }
            }
        });
        ping(allIp);
//        getArmMode();
        return true;
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
        String ip = param[0];
        NetSDKLib.LLong userIp = userMap.get(ip);
        int order = 1;
        if (!value.equals("1.0")) {
            order = 0;
        }
    }


    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    /**
     * ping
     *
     * @param allIp
     */
    private void ping(Queue<String> allIp) {
        log.info("开始采集在线离线状态,ip数量{}", allIp.size());
        PingUtil pingUtil = new PingUtil(allIp);
        pingUtil.setIpsOK("");
        pingUtil.setIpsNO("");
        pingUtil.startPing();
        String ipsOK = pingUtil.getIpsOK();
        String ipsNo = pingUtil.getIpsNO();
        if (StringUtils.isNotBlank(ipsOK)) {
            List<String> ipList = Arrays.asList(ipsOK.split(","));
            log.info("在线数量：{}", ipList.size());
            ipList.forEach(ip -> {
                sendMsg(ip.concat("_onlineStatus"), "1");
            });
        }
        if (StringUtils.isNotBlank(ipsNo)) {
            List<String> ipList = Arrays.asList(ipsNo.split(","));
            log.info("离线数量：{}", ipList.size());
            ipList.forEach(ip -> {
                sendMsg(ip.concat("_onlineStatus"), "0");
            });
        }
    }

    /**
     * 发送消息
     *
     * @param outParamId
     * @param value
     */
    private void sendMsg(String outParamId, String value) {
        List<DeviceMessage> deviceMessageList = deviceParamListMap.get(outParamId);
        if (!CollectionUtils.isEmpty(deviceMessageList)) {
            deviceMessageList.forEach(deviceMessage -> {
                deviceMessage.setValue(value);
                sendMessage(deviceMessage);
            });
        }
    }

}

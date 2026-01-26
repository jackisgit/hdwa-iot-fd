package com.wanda.epc.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IscAlarmDevice extends BaseDevice {

    public static final String DEPLOY_WITHDRAW_ALARM_STATUS = "_deployWithdrawAlarmStatus";
    public static final String ALARM_STATUS = "_alarmStatus";
    public static final String FAULT_STATUS = "_faultStatus";
    public static final String FANGCHAI_STATUS = "_fangchaiStatus";

    @Autowired
    private IscClient iscClient;

    /**
     * 主动查询防区状态（等价 alarmStatus）
     */
    private void queryZoneStatus() {
        log.info("========== IS&C 查询防区状态 ==========");

        List<String> zoneCodes = getAllZoneCodes();
        if (zoneCodes.isEmpty()) {
            return;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("zoneIndexCodes", zoneCodes);

        JSONObject resp = iscClient.post(
                "/api/ias/v1/alarm/zone/status",
                body
        );

        JSONArray data = resp.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject z = data.getJSONObject(i);
            String zone = z.getString("zoneIndexCode");

            sendMsg(zone + DEPLOY_WITHDRAW_ALARM_STATUS,
                    "ARMED".equals(z.getString("armState")) ? "1" : "0");

            sendMsg(zone + ALARM_STATUS,
                    "ALARM".equals(z.getString("alarmState")) ? "1" : "0");

            sendMsg(zone + FAULT_STATUS,
                    "FAULT".equals(z.getString("faultState")) ? "1" : "0");

            sendMsg(zone + FANGCHAI_STATUS,
                    "TAMPER".equals(z.getString("tamperState")) ? "1" : "0");
        }
    }

    /**
     * 布防
     */
    private void armZone(String zoneCode) {
        Map<String, Object> body = new HashMap<>();
        List<String> zoneCodes = new ArrayList<>();
        zoneCodes.add(zoneCode);
        body.put("zoneIndexCodes", zoneCodes);

        iscClient.post("/api/ias/v1/alarm/arm", body);
        log.info("防区 {} 布防成功", zoneCode);
    }

    /**
     * 撤防
     */
    private void disarmZone(String zoneCode) {
        Map<String, Object> body = new HashMap<>();
        List<String> zoneCodes = new ArrayList<>();
        zoneCodes.add(zoneCode);
        body.put("zoneIndexCodes", zoneCodes);

        iscClient.post("/api/ias/v1/alarm/disarm", body);
        log.info("防区 {} 撤防成功", zoneCode);
    }

    /**
     * 等价 processData
     */
    @Override
    public boolean processData() {
        queryZoneStatus();
        return true;
    }

    /**
     * 控制命令（等价 dispatchCommand）
     */
    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) {
        commonDevice.feedback(message);

        DeviceMessage dm = controlParamMap.get(meter + "-" + funcid);
        if (dm == null || !dm.getOutParamId().endsWith("deployWithdrawAlarmSet")) {
            return;
        }

        String zoneCode = dm.getOutParamId().split("_")[0];
        try {
            if ("1.0".equals(value)) {
                armZone(zoneCode);
            } else {
                disarmZone(zoneCode);
            }
        } catch (Exception e) {
            log.error("IS&C 防盗控制失败", e);
        }
    }

    /**
     * 取所有防区编码
     */
    private List<String> getAllZoneCodes() {
        List<String> list = new ArrayList<>();
        deviceParamListMap.forEach((k, v) -> {
            if (k.endsWith(DEPLOY_WITHDRAW_ALARM_STATUS)) {
                list.add(k.replace(DEPLOY_WITHDRAW_ALARM_STATUS, ""));
            }
        });
        return list;
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    @Override
    public boolean processData(String... obj) {
        return false;
    }
}

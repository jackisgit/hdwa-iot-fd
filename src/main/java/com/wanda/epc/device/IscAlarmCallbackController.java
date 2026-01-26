package com.wanda.epc.device;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isc/callback")
@Slf4j
public class IscAlarmCallbackController {

    @Autowired
    private IscAlarmDevice device;

    @PostMapping("/alarm")
    public void onAlarm(@RequestBody JSONObject event) {

        String zone = event.getString("zoneIndexCode");
        String type = event.getString("eventType");

        log.info("IS&C 报警事件: {}", event.toJSONString());

        switch (type) {
            case "INTRUSION":
                device.sendMsg(zone + "_alarmStatus", "1");
                break;
            case "TAMPER":
                device.sendMsg(zone + "_fangchaiStatus", "1");
                break;
            case "FAULT":
                device.sendMsg(zone + "_faultStatus", "1");
                break;
            case "ARM":
                device.sendMsg(zone + "_deployWithdrawAlarmStatus", "1");
                break;
            case "DISARM":
                device.sendMsg(zone + "_deployWithdrawAlarmStatus", "0");
                break;
            default:
                break;
        }
    }
}
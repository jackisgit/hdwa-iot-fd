package com.wanda.epc.device;

import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @program: iot_epc
 * @description: 变压器采集
 * @author: 孙率众
 * @create: 2022-11-08 17:07
 **/


@Slf4j
@Component
public class CommonTask {

    @Autowired
    CommonDevice commonDevice;
    @Autowired
    private BaseDevice baseDevice;

    @Scheduled(cron = "0/60 * * * * ?")
    public boolean processData() throws Exception {
        log.info("子系统不返回报警恢复指令，自动处理");
        Iterator<Map.Entry<String, List<DeviceMessage>>> iterator = baseDevice.deviceParamListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<DeviceMessage>> next = iterator.next();
            if (next.getKey().endsWith("_isAlarm")) {
                for (DeviceMessage deviceMessage : next.getValue()) {
                    deviceMessage.setValue("0");
                    commonDevice.sendMessage(deviceMessage);
                }
            }
        }
        return true;
    }

}


package com.wanda.epc.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CommonTask {

    @Autowired
    private IscAlarmDevice iscAlarmDevice;

    @Scheduled(cron = "${epc.cron:0/60 * * * * ?}")
    public boolean processData() {
        return iscAlarmDevice.processData();
    }
}

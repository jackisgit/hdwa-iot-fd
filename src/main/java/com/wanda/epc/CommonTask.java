package com.wanda.epc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CommonTask {

    @Autowired
    private DeviceHandler device;

    @Scheduled(cron = "${epc.cron:0/30 * * * * ?}")
    public boolean processData() throws Exception {
        return device.processData();
    }

}

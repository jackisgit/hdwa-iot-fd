package com.wanda.epc.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: 孙率众
 **/
@Configuration
@EnableScheduling
public class CommonTask {

    @Autowired
    private DeviceHandler deviceHandler;

    @Scheduled(cron = "${epc.cron:0/30 * * * * ?}")
    public void collect() throws Exception {
        deviceHandler.collect();
    }

    @Scheduled(cron = "40 10 * * * ?")
    public void getToken() throws Exception {
        deviceHandler.init();
    }
}

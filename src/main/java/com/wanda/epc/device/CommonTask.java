package com.wanda.epc.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author LianYanFei
 * @description 防盗系统
 * @date 2023/3/30
 */
@Configuration
@EnableScheduling
public class CommonTask {

    @Autowired
    private HikVisionEasDevice hikVisionEasDevice;

    @Scheduled(cron = "${epc.cron:0/60 * * * * ?}")
    public boolean processData() throws Exception {
        return hikVisionEasDevice.processData();
    }
}

package com.wanda.epc.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *@description 绍兴柯桥防盗系统
 *@author LianYanFei
 *@date 2023/3/30
 */
@Configuration
@EnableScheduling
public class CommonTask {

    @Autowired
    private HikVisionEasDevice hikVisionEasDevice;// opc

    @Scheduled(cron = "0/60 * * * * ?")
    public boolean processData() throws Exception {
        return hikVisionEasDevice.processData();
    }

}

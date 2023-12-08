package com.wanda.epc.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
public class CommonTask {

    @Resource
    private DhFD dhFD;

    @Scheduled(cron = "0/30 * * * * ?")
    public boolean processData() throws Exception {
        return dhFD.processData();
    }

}

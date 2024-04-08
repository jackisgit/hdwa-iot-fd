package com.wanda.epc;

import com.wanda.epc.device.HikVisionEasDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class IotEpaFDApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IotEpaFDApplication.class, args);
        String epcGroup = context.getEnvironment().getProperty("epc.group");
        String[] groups = epcGroup.split(",");
        if (groups.length == 0) {
            return;
        }
        for (String group : groups) {
            log.info("使能开始，子系统号：{}", group);
            HikVisionEasDevice.subsystemParamEx(Integer.valueOf(group));
            log.info("使能结束，子系统号：{}", group);
        }
    }

}

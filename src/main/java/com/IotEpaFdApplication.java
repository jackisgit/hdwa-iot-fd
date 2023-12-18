package com;

import com.wanda.epc.ZoneArmModeDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class IotEpaFdApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotEpaFdApplication.class, args);
//        ZoneArmModeDemo demo = new ZoneArmModeDemo();
//        demo.initTest();
//        demo.runTest();
//        demo.endTest();
    }

}

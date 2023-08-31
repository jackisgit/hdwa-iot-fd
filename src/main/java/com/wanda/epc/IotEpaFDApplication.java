package com.wanda.epc;

import com.wanda.epc.device.HikVisionEasDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class IotEpaFDApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotEpaFDApplication.class, args);
        log.info("使能开始，子系统号：{}", 1);
        HikVisionEasDevice.subsystemParamEx(1);
        log.info("使能结束，子系统号：{}", 1);
        log.info("使能开始，子系统号：{}", 2);
        HikVisionEasDevice.subsystemParamEx(2);
        log.info("使能结束，子系统号：{}", 2);
    }

}

package com.wanda.epc;

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
    }

}

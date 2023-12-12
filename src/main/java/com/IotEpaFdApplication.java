package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IotEpaFdApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotEpaFdApplication.class, args);
    }

}

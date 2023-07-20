package com.wanda.epc;

import com.wanda.epc.device.HikVisionEasDevice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IotEpaFDApplication {

    public static void main(String[] args)  {
        SpringApplication.run(IotEpaFDApplication.class, args);
        HikVisionEasDevice.subsystemParamEx(1);
        HikVisionEasDevice.subsystemParamEx(2);
    }

}

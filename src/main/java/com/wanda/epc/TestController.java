package com.wanda.epc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class TestController {

    @Resource
    private ZoneArmModeDemo zoneArmModeDemo;
    @Resource
    private DeviceHandler deviceHandler;

    @RequestMapping("control/{zoneNo}/{armMode}")
    public void control(@PathVariable int zoneNo, @PathVariable String armMode) {
        zoneArmModeDemo.setZoneArmMode(zoneNo, armMode);
    }

    @RequestMapping("collect")
    public void collect() {
        try {
            deviceHandler.processData();
        } catch (Exception e) {
            log.error("处理异常", e);
        }
    }
}

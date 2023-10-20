package com.wanda.epc.device;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private FdHandler fdHandler;

    @RequestMapping("/collect")
    public void collect() throws Exception {
        fdHandler.processData();
    }

    @RequestMapping("/control/{deviceId}/{deviceType}/{armingAction}")
    public void control(@PathVariable int deviceId, @PathVariable int deviceType, @PathVariable int armingAction) throws Exception {
        fdHandler.control(deviceId, deviceType, armingAction);
    }
}

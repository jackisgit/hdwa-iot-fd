package com.wanda.epc.controller;

import com.wanda.epc.service.TestService;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Devices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/testController")
@Slf4j
public class TestController {


    @Resource
    TestService testService;

    @GetMapping("/testBU")
    public int testBU() {
        return testService.testBU();
    }

    @GetMapping("/testCHE")
    public int testCHE() {
        return testService.testCHE();
    }

    @GetMapping("/testBUDevice")
    public int testBUDevice() {
        return testService.testBuDevice();
    }

    @GetMapping("/testCHEDevice")
    public int testCHEDevice() {
        return testService.testCheDevice();
    }

    @GetMapping("/getTree")
    public Devices getTree() throws Exception {
        log.info("------------------------------getTree进入controller------------------------");
        return testService.getTree();
    }

    @GetMapping("/getChannelInfo")
    public List<ChannelInfo> getChannelInfo() throws Exception {
        log.info("------------------------------getChannelInfo进入controller------------------------");
        return testService.getChannelInfo();
    }
}

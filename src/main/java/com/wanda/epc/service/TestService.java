package com.wanda.epc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Devices;
import com.wanda.epc.vo.Organization;

import java.util.List;

public interface TestService {
    int testBU();
    int testCHE();

    int testBuDevice();

    int testCheDevice();

    Devices getTree() throws Exception;

    List<ChannelInfo> getChannelInfo() throws Exception;
}

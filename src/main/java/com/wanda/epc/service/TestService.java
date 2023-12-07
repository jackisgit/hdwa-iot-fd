package com.wanda.epc.service;

import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Devices;

import java.util.List;

public interface TestService {
    int testBU();

    int testCHE();

    int testBuDevice();

    int testCheDevice();

    Devices getTree() throws Exception;

    List<ChannelInfo> getChannelInfo() throws Exception;
}

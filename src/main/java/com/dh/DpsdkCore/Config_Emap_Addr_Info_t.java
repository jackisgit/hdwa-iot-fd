package com.dh.DpsdkCore;

public class Config_Emap_Addr_Info_t {
    public byte[] szIP = new byte[dpsdk_constant_value.DPSDK_CORE_IP_LEN];            // 电子地图服务IP地址
    public int nPort;                                                                    // 电子地图服务端口号
};

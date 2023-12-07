package com.dh.DpsdkCore;

public class Control_Dev_Burner_Request_t {
    public byte[] deviceId = new byte[dpsdk_constant_value.DPSDK_CORE_DEV_ID_LEN];
    public int cmd;
    public int channelMask;
    public int burnerMask;
    public int emMode;
    public int emPack;
};

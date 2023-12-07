package com.dh.DpsdkCore;

public class Ptz_Single_Prepoint_Info_t {
    public int nCode;                                                    // 预置点编号
    public byte[] szName = new byte[dpsdk_constant_value.DPSDK_CORE_POINT_NAME_LEN];        // 名字
};

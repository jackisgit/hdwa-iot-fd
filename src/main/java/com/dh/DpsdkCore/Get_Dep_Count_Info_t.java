package com.dh.DpsdkCore;

public class Get_Dep_Count_Info_t {
    public byte[] szCoding = new byte[dpsdk_constant_value.DPSDK_CORE_DGROUP_DGPCODE_LEN];            // 节点code
    public int nDepCount;                                                            // 组织个数
    public int nDeviceCount;                                                        // 设备个数
    public int nChannelCount;                                                    // 通道个数
};

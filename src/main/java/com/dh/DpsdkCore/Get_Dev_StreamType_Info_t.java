package com.dh.DpsdkCore;

public class Get_Dev_StreamType_Info_t {
    public byte[] szDeviceId = new byte[dpsdk_constant_value.DPSDK_CORE_DEV_ID_LEN];        // 设备ID
    public int nUnitNo;                                        // 单元序号
    public int nStreamType;                                                    // 支持的码流类型,参考类dpsdk_stream_type_e定义
};

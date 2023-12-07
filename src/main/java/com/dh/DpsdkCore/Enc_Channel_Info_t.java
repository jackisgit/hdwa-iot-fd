package com.dh.DpsdkCore;

public class Enc_Channel_Info_t {
    public int nCameraType;                                                                    // 类型，参见CameraType_e
    public byte[] szId = new byte[dpsdk_constant_value.DPSDK_CORE_DEV_ID_LEN];                            // 通道ID:设备ID+通道号
    public byte[] szName = new byte[dpsdk_constant_value.DPSDK_CORE_DGROUP_DEVICENAME_LEN];                // 名称
};

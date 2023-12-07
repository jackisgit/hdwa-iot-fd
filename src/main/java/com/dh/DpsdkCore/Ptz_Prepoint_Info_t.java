package com.dh.DpsdkCore;

public class Ptz_Prepoint_Info_t {
    public byte[] szCameraId = new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];                            // 通道ID
    public int nCount;                                                    // 预置点数量
    public Ptz_Single_Prepoint_Info_t[] pPoints = new Ptz_Single_Prepoint_Info_t[dpsdk_constant_value.DPSDK_CORE_POINT_COUNT];                // 预置点信息
};

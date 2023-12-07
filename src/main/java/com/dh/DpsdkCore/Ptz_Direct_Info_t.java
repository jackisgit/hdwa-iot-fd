package com.dh.DpsdkCore;

public class Ptz_Direct_Info_t {
    public byte[] szCameraId = new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];        // 通道ID
    public byte[] szSN = new byte[dpsdk_constant_value.DPSDK_CORE_28181_SN_LENGTH];        // 通道SN(默认填写为空字符串)
    public int nDirect;                                                    // 云台方向控制命令,参考类dpsdk_ptz_direct_e定义
    public int nStep;                                                        // 步长
    public boolean bStop;                                                        // 是否停止，0表示不停止，1表示停止
};

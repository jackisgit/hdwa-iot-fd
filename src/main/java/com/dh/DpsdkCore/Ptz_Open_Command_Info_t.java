package com.dh.DpsdkCore;

public class Ptz_Open_Command_Info_t {
    public byte[] szCameraId = new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];            // 通道ID
    public boolean bOpen;                                            // 是否开启，0表示不开启,1表示开启
};

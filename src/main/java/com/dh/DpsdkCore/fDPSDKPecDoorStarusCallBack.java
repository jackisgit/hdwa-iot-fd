package com.dh.DpsdkCore;

public interface fDPSDKPecDoorStarusCallBack {
    public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus, int nTime);
}

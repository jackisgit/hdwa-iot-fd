package com.dh.DpsdkCore;

public interface fDPSDKNVRChnlStatusCallback {
    public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus);
}

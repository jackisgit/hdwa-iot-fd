package com.dh.DpsdkCore;

public interface fDPSDKRemoteDeviceSnapCallback {
    public void invoke(int nPDLLHandle, byte[] szCameraId, byte[] szFullPath);
}

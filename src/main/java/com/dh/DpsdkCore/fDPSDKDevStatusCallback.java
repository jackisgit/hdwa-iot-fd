package com.dh.DpsdkCore;

public interface fDPSDKDevStatusCallback {
    public void invoke(int nPDLLHandle, byte[] szDeviceId, int nStatus);
}

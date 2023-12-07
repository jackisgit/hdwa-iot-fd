package com.dh.DpsdkCore;

public interface fDPSDKDeviceChangeCallback {
    public void invoke(int nPDLLHandle, int nChangeType, byte[] szDeviceId, byte[] szDepCode, byte[] szNewOrgCode);
}

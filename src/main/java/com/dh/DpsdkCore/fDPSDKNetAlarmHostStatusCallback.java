package com.dh.DpsdkCore;

public interface fDPSDKNetAlarmHostStatusCallback {
    public void invoke(int nPDLLHandle, byte[] szDeviceId, int nRType, int nOperType, int nStatus);
}

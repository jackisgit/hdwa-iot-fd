package com.dh.DpsdkCore;

public interface fDPSDKVideoAlarmHostStatusCallback {
    public void invoke(int nPDLLHandle, byte[] szDeviceId, int nChannelNO, int nStatus);
}

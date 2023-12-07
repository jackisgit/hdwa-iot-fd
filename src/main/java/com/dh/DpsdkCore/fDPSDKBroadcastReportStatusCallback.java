package com.dh.DpsdkCore;

public interface fDPSDKBroadcastReportStatusCallback {
    public void invoke(int nPDLLHandle, byte[] szCameraId, int nRType, long nTime, int nStatus);
}

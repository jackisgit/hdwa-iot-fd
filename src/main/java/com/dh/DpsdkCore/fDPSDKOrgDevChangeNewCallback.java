package com.dh.DpsdkCore;

public interface fDPSDKOrgDevChangeNewCallback {
    public void invoke(int nPDLLHandle, int nChangeType, byte[] szOrgCode);
}

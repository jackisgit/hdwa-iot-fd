package com.dh.DpsdkCore;

public interface fMediaDataCallback {
    public void invoke(int nPDLLHandle, int nSeq, int nMediaType, byte[] szNodeId, int nParamVal, byte[] szData, int nDataLen);
}

package com.other;

import com.dh.DpsdkCore.fMediaDataCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DPSDKMediaDataCallback implements fMediaDataCallback {
    @Override
    public void invoke(int nPDLLHandle, int nSeq, int nMediaType, byte[] szNodeId, int nParamVal, byte[] szData, int nDataLen) {
        log.info("DPSDKMediaDataCallback nSeq = %d, nMediaType = %d, nDataLen = %d", nSeq, nMediaType, nDataLen);

    }
}

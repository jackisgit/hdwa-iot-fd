package com.dh.DpsdkCore;

public interface fDPSDKTalkParamCallback {
    public void invoke(int nPDLLHandle,
                       int nTalkType,
                       int nAudioType,
                       int nAudioBit,
                       int nSampleRate,
                       int nTransMode
    );
}

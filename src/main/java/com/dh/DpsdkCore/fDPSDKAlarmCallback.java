package com.dh.DpsdkCore;

public interface fDPSDKAlarmCallback {
    public void invoke(int nPDLLHandle, byte[] szAlarmId, int nDeviceType, byte[] szCameraId, byte[] szDeviceName, byte[] szChannelName, byte[] szCoding, byte[] szMessage, int nAlarmType, int nEventType, int nLevel, long nTime, byte[] pAlarmData, int nAlarmDataLen, byte[] pPicData, int nPicDataLen);
}

package com.other;

import com.dh.DpsdkCore.fDPSDKAlarmCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

/*
 * 报警回调
 * */
@Slf4j
public class DPSDKAlarmCallback implements fDPSDKAlarmCallback {
    int i = 0;

    public void invoke(int nPDLLHandle, byte[] szAlarmId, int nDeviceType, byte[] szCameraId, byte[] szDeviceName, byte[] szChannelName, byte[] szCoding, byte[] szMessage, int nAlarmType, int nEventType, int nLevel, long nTime, byte[] pAlarmData, int nAlarmDataLen, byte[] pPicData, int nPicDataLen) {
        i++;
        String strDevName = "";
        String strChnName = "";
        try {
            strDevName = new String(szDeviceName, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            strChnName = new String(szChannelName, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        log.info("序号=%d,nPDLLHandle=%d,szAlarmId=%s,nDeviceType=%d,szCameraId=%s,szDeviceName=%s,szChannelName=%s,szCoding=%s," +
                        "szMessage=%s,nAlarmType=%d,nEventType=%d,nLevel=%d,nTime=%d", i, nPDLLHandle, new String(szAlarmId), nDeviceType, new String(szCameraId),
                strDevName, strChnName, new String(szCoding), new String(szMessage), nAlarmType, nEventType, nLevel, nTime);


    }
}

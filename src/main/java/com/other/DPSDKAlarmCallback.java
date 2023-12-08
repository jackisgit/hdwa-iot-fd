package com.other;

import com.dh.DpsdkCore.fDPSDKAlarmCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

/**
 * 报警回调
 *
 * @author 孙率众
 */
@Slf4j
public class DPSDKAlarmCallback implements fDPSDKAlarmCallback {
    int i = 0;

    @Override
    public void invoke(int nPDLLHandle, byte[] szAlarmId, int nDeviceType, byte[] szCameraId, byte[] szDeviceName, byte[] szChannelName, byte[] szCoding, byte[] szMessage, int nAlarmType, int nEventType, int nLevel, long nTime, byte[] pAlarmData, int nAlarmDataLen, byte[] pPicData, int nPicDataLen) {
        i++;
        String strDevName = "";
        String strChnName = "";
        try {
            strDevName = new String(szDeviceName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage(), e);
        }
        try {
            strChnName = new String(szChannelName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage(), e);
        }

        log.info("序号={},nPDLLHandle={},szAlarmId={},nDeviceType={},szCameraId={},szDeviceName={},szChannelName={},szCoding={}," +
                        "szMessage={},nAlarmType={},nEventType={},nLevel={},nTime={}", i, nPDLLHandle, new String(szAlarmId), nDeviceType, new String(szCameraId),
                strDevName, strChnName, new String(szCoding), new String(szMessage), nAlarmType, nEventType, nLevel, nTime);


    }
}

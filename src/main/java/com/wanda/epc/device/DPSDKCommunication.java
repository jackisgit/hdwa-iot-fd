package com.wanda.epc.device;

import com.dh.DpsdkCore.*;
import com.other.DPSDKAlarmCallback;
import com.other.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Service
@Slf4j
public class DPSDKCommunication {

    public static int m_nDLLHandle = -1;

    public static boolean bDownloadFinish = false;
    public static String StrCarNum;
    public fDPSDKDevStatusCallback fDeviceStatus = new fDPSDKDevStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nStatus) {
            String status = "离线";
            if (nStatus == 1) {
                status = "在线";
                Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
                int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, szDeviceId, deviceInfo);
                if (deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_EVS || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_SMART_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_MATRIX_NVR6000) {
                    nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, szDeviceId, 10 * 1000);

                    if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
                        //log.info("查询NVR通道状态成功，deviceID = %s", new String(szDeviceId));
                    } else {
                        log.error("查询NVR通道状态失败，deviceID = %s, {}", new String(szDeviceId), nRet);
                    }
                    //
                }
            }
            //log.info("Device Status Report!, szDeviceId = %s, nStatus = %s", new String(szDeviceId),status);
            //
        }
    };
    public fDPSDKNVRChnlStatusCallback fNVRChnlStatus = new fDPSDKNVRChnlStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus) {
            String status = "离线";
            if (nStatus == 1) {
                status = "在线";
            }
            //log.info("NVR Channel Status Report!, szCameraId = %s, nStatus = %s", new String(szCameraId),status);
            //
        }
    };
    public fDPSDKGeneralJsonTransportCallback fGeneralJson = new fDPSDKGeneralJsonTransportCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szJson) {
            log.info("General Json Return, ReturnJson = %s", new String(szJson));

        }
    };
    //卡口过车数据回调
    public fDPSDKGetBayCarInfoCallbackEx fBayCarInfo = new fDPSDKGetBayCarInfoCallbackEx() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nDeviceIdLen, int nDevChnId, byte[] szChannelId, int nChannelIdLen, byte[] szDeviceName, int nDeviceNameLen, byte[] szDeviceChnName, int nChanNameLen, byte[] szCarNum, int nCarNumLen, int nCarNumType, int nCarNumColor, int nCarSpeed, int nCarType, int nCarColor, int nCarLen, int nCarDirect, int nWayId, long lCaptureTime, long lPicGroupStoreID, int nIsNeedStore, int nIsStoraged, byte[] szCaptureOrg, int nCaptureOrgLen, byte[] szOptOrg, int nOptOrgLen, byte[] szOptUser, int nOptUserLen, byte[] szOptNote, int nOptNoteLen, byte[] szImg0Path, int nImg0PathLen, byte[] szImg1Path, int nImg1PathLen, byte[] szImg2Path, int nImg2PathLen, byte[] szImg3Path, int nImg3PathLen, byte[] szImg4Path, int nImg4PathLen, byte[] szImg5Path, int nImg5PathLen, byte[] szImgPlatePath, int nImgPlatePathLen, int icarLog, int iPlateLeft, int iPlateRight, int iPlateTop, int iPlateBottom) {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("Bay Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(szDeviceId), new String(szChannelId), new String(szDeviceChnName), StrCarNum, new String(szImg0Path));

        }
    };
    //违章报警回调
    public fDPSDKTrafficAlarmCallback fTrafficAlarmCallback = new fDPSDKTrafficAlarmCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, byte[] nPtsIp, byte[] nPtsIpy, int nPicPort, int nPicPorty, int type,
                           byte[] szCarNum, int nLicentype, int nCarColor, int nCarLogo, int nWay, byte[] szPicUrl0, byte[] szPicUrl1, byte[] szPicUrl2,
                           byte[] szPicUrl3, byte[] szPicUrl4, byte[] szPicUrl5, int nPicGroupStoreID, int bNeedStore, int bStored, int nAlarmLevel, int nAlarmTime,
                           int nChannel, byte[] szDeviceId, byte[] szDeviceName, byte[] szDeviceChnName, int nCarType, int nCarSpeed, int nCarLen, int nCardirect, int nMaxSpeed,
                           int nMinSpeed, int nRtPlateleft, int nRtPlatetop, int nRtPlateright, int nRtPlatebottom, byte[] szMessage) {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("TrafficAlarm Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(szDeviceId).trim(), new String(szCameraId).trim(), new String(szDeviceName).trim(), StrCarNum.trim(), new String(szPicUrl0).trim());

        }
    };
    //区间测速回调
    public fDPSDKGetAreaSpeedDetectCallback fGetAreaSpeedDetectCallback = new fDPSDKGetAreaSpeedDetectCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szAreaId, byte[] szAreaName, byte[] szStartDevId, int nStartChnNum, byte[] szStartChnId, byte[] szStartDevName, byte[] szStartDevChnName,
                           long nStartCapTime, int nStartCarSpeed, byte[] szStartPosId, byte[] szStartPosName, byte[] szEndDevId, int nEndChnNum, byte[] szEndChnId, byte[] szEndDevName, byte[] szEndDevChnName,
                           long nEndCapTime, int nEndCarSpeed, byte[] szEndPosId, byte[] szEndPosName, int nAreaRange, int nMinSpeed, int nMaxSpeed, byte[] szCarNum, int nCarNumType, int nCarNumColor, int nCarColor,
                           int nCarType, int nCarLogo, int nCarAvgSpeed, int nIsIllegalSpeed, int nPicNum, byte[] szPicName0, byte[] szPicName1, byte[] szPicName2, byte[] szPicName3, byte[] szPicName4,
                           byte[] szPicName5, int nRtPlateleft, int nRtPlatetop, int nRtPlateright, int nRtPlatebottom, byte[] szCarPlatePicURL) {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("AreaSpeedDetectInfo Report, szCarNum=%s, szPicName0=%s", StrCarNum.trim(), new String(szPicName0).trim());

        }
    };
    public fDPSDKNetAlarmHostStatusCallback fNetAlarmHostStatus = new fDPSDKNetAlarmHostStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nRType, int nOperType, int nStatus) {
            String DeviceId = new String(szDeviceId);
            String strReportType = "";
            String strOperationType = "";
            String strStatus = "";
            switch (nRType) {
                case 1:
                    strReportType = "留守布防";
                    break;
                case 2:
                    strReportType = "防区旁路";
                    break;
                default:
                    strReportType = "未知";
                    break;
            }
            switch (nOperType) {
                case 1:
                    strOperationType = "设备 布/撤防";
                    break;
                case 2:
                    strOperationType = "通道 布/撤防";
                    break;
                case 3:
                    strOperationType = "报警输出通道操作";
                    break;
                default:
                    strOperationType = "未知";
                    break;
            }
            switch (nStatus) {
                case 1:
                    strStatus = "布防/旁路";
                    break;
                case 2:
                    strStatus = "撤防/取消旁路";
                    break;
                default:
                    strStatus = "未知";
                    break;
            }
            log.info("NetAlarmHostStatusCallback, szDeviceId[%s]nRType[%s]nOperType[%s]nStatus[%s]", DeviceId, strReportType, strOperationType, strStatus);

        }
    };
    //转码下载录像完成回调
    public fSaveRecordFinishedCallback fSaveRecordFinished = new fSaveRecordFinishedCallback() {
        @Override
        public void invoke(int nPDLLHandle, int nPlaybackSeq) {
            log.info("录像下载完成，nPlaybackSeq = %d", nPlaybackSeq);

            bDownloadFinish = true;
        }
    };
    //门状态上报回调
    public fDPSDKPecDoorStarusCallBack fDoorStatusCallback = new fDPSDKPecDoorStarusCallBack() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus, int nTime) {
            String StrCameraId = null;
            try {
                StrCameraId = new String(szCameraId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.info(e.getMessage(), e);
            }
            log.info("DoorStarusInfo Report, szCameraId=%s, nStatus=%d,nTime=%d", StrCameraId.trim(), nStatus, nTime);

        }
    };
    //
    public fDPSDKDeviceChangeCallback fDeviceChangeCallback = new fDPSDKDeviceChangeCallback() {
        @Override
        public void invoke(int nPDLLHandle, int nChangeType, byte[] szDeviceId, byte[] szDepCode, byte[] szNewOrgCode) {
            log.info("DeviceChange Report, nChangeType=[%d], szDeviceId=[%s] szDepCode=[%s] szNewOrgCode=[%s]",
                    nChangeType, new String(szDeviceId).trim(), new String(szDepCode).trim(), new String(szNewOrgCode).trim());

        }
    };
    Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
    DPSDKAlarmCallback m_AlarmCB = new DPSDKAlarmCallback();

    public void OnCreate() {
        log.info("log进入OnCreate方法");
        int nRet = -1;
        Return_Value_Info_t res = new Return_Value_Info_t();
        nRet = IDpsdkCore.DPSDK_Create(dpsdk_sdk_type_e.DPSDK_CORE_SDK_SERVER, res, ("com/dh/DpsdkCore/").getBytes());
        if (nRet != dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.error("创建DPSDK失败，{}", nRet);
            return;
        }

        m_nDLLHandle = res.nReturnValue;
        log.info("获取返回的m_nDLLHandle = " + m_nDLLHandle);
        String dpsdklog = "E:\\dhfdlog\\dhfdlog";
        nRet = IDpsdkCore.DPSDK_SetLog(m_nDLLHandle, dpsdklog.getBytes());
        String dumpfile = "E:\\dhfdlog\\dhfdlog";
        nRet = IDpsdkCore.DPSDK_StartMonitor(m_nDLLHandle, dumpfile.getBytes());
        if (m_nDLLHandle > 0) {
            //设置设备状态上报监听函数
            nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceStatusCallback(m_nDLLHandle, fDeviceStatus);
            //设置NVR通道状态上报监听函数
            nRet = IDpsdkCore.DPSDK_SetDPSDKNVRChnlStatusCallback(m_nDLLHandle, fNVRChnlStatus);
            //设置通用JSON回调
            nRet = IDpsdkCore.DPSDK_SetGeneralJsonTransportCallback(m_nDLLHandle, fGeneralJson);

            nRet = IDpsdkCore.DPSDK_SetDPSDKGetBayCarInfoCallbackEx(m_nDLLHandle, fBayCarInfo);

            nRet = IDpsdkCore.DPSDK_SetDPSDKTrafficAlarmCallback(m_nDLLHandle, fTrafficAlarmCallback);

            nRet = IDpsdkCore.DPSDK_SetDPSDKGetAreaSpeedDetectCallback(m_nDLLHandle, fGetAreaSpeedDetectCallback);

            nRet = IDpsdkCore.DPSDK_SetPecDoorStatusCallback(m_nDLLHandle, fDoorStatusCallback);

            //nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceChangeCallback(m_nDLLHandle, fDeviceChangeCallback );
        }

    }

    /*
     * 登录
     * */
    public void OnLogin(String m_strIp, int m_nPort, String m_strUser, String m_strPassword) {
        Login_Info_t loginInfo = new Login_Info_t();
        log.info("登录参数，m_strIp：" + m_strIp + " m_nPort:" + m_nPort + " m_strUser:" + m_strUser + " m_strPassword:" + m_strPassword);
        loginInfo.szIp = m_strIp.getBytes();
        loginInfo.nPort = m_nPort;
        loginInfo.szUsername = m_strUser.getBytes();
        loginInfo.szPassword = m_strPassword.getBytes();
        loginInfo.nProtocol = dpsdk_protocol_version_e.DPSDK_PROTOCOL_VERSION_II;
        loginInfo.iType = 1;

        int nRet = IDpsdkCore.DPSDK_Login(m_nDLLHandle, loginInfo, 10000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("登录成功，{}", nRet);
        } else {
            log.error("登录失败，{}", nRet);
        }

    }

    /*
     * 加载所有组织树
     * */
    public void LoadAllGroup() {
        int nRet = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000);

        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("加载所有组织树成功，{}， nDepCount = %d", nRet, nGroupLen.nReturnValue);
        } else {
            log.error("加载所有组织树失败，{}", nRet);
        }

    }

    /*
     * 获取所有组织树串
     * */
    public void GetGroupStr() {
        byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
        log.info("获取所有组织树串参数m_nDLLHandle：" + m_nDLLHandle + ";szGroupBuf：" + szGroupBuf + ";nGroupLen.nReturnValue：" + nGroupLen.nReturnValue);
        int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);

        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            String GroupBuf = "";
            try {
                GroupBuf = new String(szGroupBuf, "UTF-8");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("获取所有组织树串成功，{}， szGroupBuf = [%s]", nRet, GroupBuf);
            try {
                File file = new File("D:\\text.xml");
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream out = new FileOutputStream(file);
                out.write(szGroupBuf);
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else {
            log.error("获取所有组织树串失败，{}", nRet);
        }

    }


    /**
     * 设置报警回调
     */
    public void SetAlarm() {
        //设置报警监听函数
        int nRet = IDpsdkCore.DPSDK_SetDPSDKAlarmCallback(m_nDLLHandle, m_AlarmCB);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("开启报警监听成功，{}", nRet);
        } else {
            log.error("开启报警监听失败，{}", nRet);
        }
    }

    public void run() {
        Menu menu = new Menu();
        menu.run();
    }

    /**
     * 登出
     */
    public void OnLogout() {
        int nRet = IDpsdkCore.DPSDK_Logout(m_nDLLHandle, 10000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("登出成功，{}", nRet);
        } else {
            log.error("登出失败，{}", nRet);
        }

    }

    /*
     * 释放内存
     * */
    public void OnDestroy() {
        int nRet = IDpsdkCore.DPSDK_Destroy(m_nDLLHandle);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("释放内存成功，{}", nRet);
        } else {
            log.error("释放内存失败，{}", nRet);
        }

    }
}

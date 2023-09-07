package com.wanda.epc.device;

import com.dh.DpsdkCore.*;
import com.other.DPSDKAlarmCallback;
import com.other.DPSDKMediaDataCallback;
import com.other.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class DPSDKCommunication {

    public static int m_nDLLHandle = -1;
    public String   m_strRealCamareID			= "1000000$1$0$0";		//实时通道ID
    public String	m_strNVRDeviceID			= "1000000";

    public String 	m_strIp 		= "172.16.10.4";   //登录平台ip
    public int    	m_nPort 		= 9000;            //端口
    public String 	m_strUser 		= "system";        //用户名
    public String 	m_strPassword 	= "CSWd123456";    //密码

//    @Value("${fzcs.strIp}")
//    private String m_strIp;
//
//    @Value("${fzcs.nPort}")
//    private int m_nPort;
//
//    @Value("${fzcs.strUser}")
//    private String m_strUser;
//
//    @Value("${fzcs.strPassword}")
//    private String m_strPassword;

    public static boolean bDownloadFinish = false;
    public static String StrCarNum;
    Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
    DPSDKAlarmCallback m_AlarmCB = new DPSDKAlarmCallback();
    DPSDKMediaDataCallback m_MediaCB = new DPSDKMediaDataCallback();
//    @PostConstruct
    public void startComPort() {

        System.out.printf("log进入startComPort方法");
        System.out.print("System.out.print进入startComPort方法 ");
        DPSDKCommunication app=new DPSDKCommunication();
        app.OnCreate();//初始化
        app.OnLogin();//登陆
        app.LoadAllGroup();//加载组织结构
        app.GetGroupStr();//获取组织结构串
//        //加载组织结构之后，要延时5秒钟左右，等待与各服务模块取得连接
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        app.SetAlarm();//打开报警监听,加载组织结构后才能接收到报警信息
        app.run();
        app.OnLogout();
        app.OnDestroy();
    }
    public void OnCreate()
    {
        System.out.printf("log进入OnCreate方法");
        int nRet = -1;
        Return_Value_Info_t res = new Return_Value_Info_t();
        nRet = IDpsdkCore.DPSDK_Create(dpsdk_sdk_type_e.DPSDK_CORE_SDK_SERVER,res,("com/dh/DpsdkCore/").getBytes());
        if(nRet != dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("创建DPSDK失败，nRet = %d", nRet);
            System.out.println();
            return;
        }

        m_nDLLHandle = res.nReturnValue;
        System.out.println("获取返回的m_nDLLHandle = "+m_nDLLHandle);
        String dpsdklog = "D:\\dhfdlog";
        nRet = IDpsdkCore.DPSDK_SetLog(m_nDLLHandle, dpsdklog.getBytes());
        String dumpfile = "D:\\dhfdlog";
        nRet = IDpsdkCore.DPSDK_StartMonitor(m_nDLLHandle, dumpfile.getBytes());
        if(m_nDLLHandle > 0)
        {
            //设置设备状态上报监听函数
            nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceStatusCallback(m_nDLLHandle, fDeviceStatus);
            //设置NVR通道状态上报监听函数
            nRet =IDpsdkCore.DPSDK_SetDPSDKNVRChnlStatusCallback(m_nDLLHandle, fNVRChnlStatus);
            //设置通用JSON回调
            nRet = IDpsdkCore.DPSDK_SetGeneralJsonTransportCallback(m_nDLLHandle, fGeneralJson);

            nRet = IDpsdkCore.DPSDK_SetDPSDKGetBayCarInfoCallbackEx(m_nDLLHandle, fBayCarInfo);

            nRet = IDpsdkCore.DPSDK_SetDPSDKTrafficAlarmCallback(m_nDLLHandle, fTrafficAlarmCallback);

            nRet = IDpsdkCore.DPSDK_SetDPSDKGetAreaSpeedDetectCallback(m_nDLLHandle, fGetAreaSpeedDetectCallback);

            nRet = IDpsdkCore.DPSDK_SetPecDoorStatusCallback(m_nDLLHandle, fDoorStatusCallback);

            //nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceChangeCallback(m_nDLLHandle, fDeviceChangeCallback );
        }

        System.out.print("创建DPSDK, 返回 m_nDLLHandle = ");
        System.out.println(m_nDLLHandle);
    }
    public fDPSDKDevStatusCallback fDeviceStatus = new fDPSDKDevStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nStatus) {
            String status = "离线";
            if(nStatus == 1)
            {
                status = "在线";
                Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
                int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, szDeviceId, deviceInfo);
                if(deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_EVS || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_SMART_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_MATRIX_NVR6000)
                {
                    nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, szDeviceId, 10*1000);

                    if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
                    {
                        //System.out.printf("查询NVR通道状态成功，deviceID = %s", new String(szDeviceId));
                    }else
                    {
                        System.out.printf("查询NVR通道状态失败，deviceID = %s, nRet = %d", new String(szDeviceId), nRet);
                    }
                    //System.out.println();
                }
            }
            //System.out.printf("Device Status Report!, szDeviceId = %s, nStatus = %s", new String(szDeviceId),status);
            //System.out.println();
        }
    };

    public fDPSDKNVRChnlStatusCallback fNVRChnlStatus = new fDPSDKNVRChnlStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus) {
            String status = "离线";
            if(nStatus == 1)
            {
                status = "在线";
            }
            //System.out.printf("NVR Channel Status Report!, szCameraId = %s, nStatus = %s", new String(szCameraId),status);
            //System.out.println();
        }
    };

    public fDPSDKGeneralJsonTransportCallback fGeneralJson = new fDPSDKGeneralJsonTransportCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szJson) {
            System.out.printf("General Json Return, ReturnJson = %s", new String(szJson));
            System.out.println();
        }
    };
    //卡口过车数据回调
    public fDPSDKGetBayCarInfoCallbackEx fBayCarInfo = new fDPSDKGetBayCarInfoCallbackEx() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nDeviceIdLen, int nDevChnId, byte[] szChannelId, int nChannelIdLen, byte[] szDeviceName, int	nDeviceNameLen, byte[] szDeviceChnName, int	nChanNameLen, byte[] szCarNum, int nCarNumLen, int	nCarNumType, int nCarNumColor, int nCarSpeed,int nCarType, int	nCarColor, int	nCarLen, int	nCarDirect, int	nWayId, long lCaptureTime, long lPicGroupStoreID, int nIsNeedStore, int nIsStoraged, byte[] szCaptureOrg, int nCaptureOrgLen, byte[] szOptOrg, int nOptOrgLen, byte[] szOptUser, int nOptUserLen, byte[] szOptNote, int nOptNoteLen, byte[] szImg0Path, int nImg0PathLen, byte[] szImg1Path, int nImg1PathLen, byte[] szImg2Path, int nImg2PathLen, byte[] szImg3Path, int nImg3PathLen, byte[] szImg4Path, int nImg4PathLen, byte[] szImg5Path, int nImg5PathLen, byte[] szImgPlatePath, int nImgPlatePathLen, int icarLog, int iPlateLeft, int iPlateRight, int iPlateTop, int iPlateBottom) {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Bay Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(szDeviceId), new String(szChannelId), new String(szDeviceChnName), StrCarNum, new String(szImg0Path));
            System.out.println();
        }
    };

    //违章报警回调
    public fDPSDKTrafficAlarmCallback fTrafficAlarmCallback = new fDPSDKTrafficAlarmCallback() {
        @Override
        public void invoke(int nPDLLHandle,byte[] szCameraId,byte[] nPtsIp,byte[] nPtsIpy,int nPicPort,int nPicPorty,int type,
                           byte[] szCarNum, int nLicentype,int nCarColor,int nCarLogo,int nWay,byte[] szPicUrl0,byte[] szPicUrl1,byte[] szPicUrl2,
                           byte[] szPicUrl3,byte[] szPicUrl4,byte[] szPicUrl5,int nPicGroupStoreID,int bNeedStore,int bStored,int nAlarmLevel,int nAlarmTime,
                           int nChannel,byte[] szDeviceId,byte[] szDeviceName,byte[] szDeviceChnName,int nCarType,int nCarSpeed,int nCarLen,int nCardirect,int nMaxSpeed,
                           int nMinSpeed,int nRtPlateleft,int nRtPlatetop,int nRtPlateright,int nRtPlatebottom,byte[] szMessage)
        {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("TrafficAlarm Car Info Report, DeviceId=%s, szChannelId=%s, szDeviceChnName=%s, szCarNum=%s, szImg0Path=%s", new String(szDeviceId).trim(), new String(szCameraId).trim(), new String(szDeviceName).trim(), StrCarNum.trim(), new String(szPicUrl0).trim());
            System.out.println();
        }
    };

    //区间测速回调
    public fDPSDKGetAreaSpeedDetectCallback fGetAreaSpeedDetectCallback = new fDPSDKGetAreaSpeedDetectCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szAreaId,byte[] szAreaName,byte[] szStartDevId,int    nStartChnNum,byte[] szStartChnId,byte[] szStartDevName,byte[] szStartDevChnName,
                           long   nStartCapTime,int    nStartCarSpeed,byte[] szStartPosId,		byte[] szStartPosName,	byte[] szEndDevId,int    nEndChnNum,byte[] szEndChnId,byte[] szEndDevName,byte[] szEndDevChnName,
                           long   nEndCapTime,int    nEndCarSpeed,byte[] szEndPosId,byte[] szEndPosName,int    nAreaRange,int    nMinSpeed,int    nMaxSpeed,byte[] szCarNum,int    nCarNumType,int    nCarNumColor,int    nCarColor,
                           int    nCarType,int    nCarLogo,int    nCarAvgSpeed,int    nIsIllegalSpeed,int    nPicNum,byte[] szPicName0,byte[] szPicName1,byte[] szPicName2,byte[] szPicName3,byte[] szPicName4,
                           byte[] szPicName5,int nRtPlateleft,          int nRtPlatetop,           int nRtPlateright,         int nRtPlatebottom,    	byte[] szCarPlatePicURL)
        {
            try {
                StrCarNum = new String(szCarNum, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("AreaSpeedDetectInfo Report, szCarNum=%s, szPicName0=%s", StrCarNum.trim(), new String(szPicName0).trim());
            System.out.println();
        }
    };

    public fDPSDKNetAlarmHostStatusCallback fNetAlarmHostStatus = new fDPSDKNetAlarmHostStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nRType, int nOperType, int nStatus) {
            String DeviceId = new String(szDeviceId);
            String strReportType = "";
            String strOperationType = "";
            String strStatus = "";
            switch(nRType)
            {
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
            switch(nOperType)
            {
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
            switch(nStatus)
            {
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
            System.out.printf("NetAlarmHostStatusCallback, szDeviceId[%s]nRType[%s]nOperType[%s]nStatus[%s]",DeviceId, strReportType, strOperationType, strStatus);
            System.out.println();
        }
    };

    //转码下载录像完成回调
    public fSaveRecordFinishedCallback fSaveRecordFinished = new fSaveRecordFinishedCallback() {
        @Override
        public void invoke(int nPDLLHandle, int nPlaybackSeq) {
            System.out.printf("录像下载完成，nPlaybackSeq = %d", nPlaybackSeq);
            System.out.println();
            bDownloadFinish = true;
        }
    };

    //门状态上报回调
    public fDPSDKPecDoorStarusCallBack fDoorStatusCallback = new fDPSDKPecDoorStarusCallBack(){
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus, int nTime) {
            String StrCameraId = null;
            try {
                StrCameraId = new String(szCameraId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.printf("DoorStarusInfo Report, szCameraId=%s, nStatus=%d,nTime=%d", StrCameraId.trim(), nStatus, nTime);
            System.out.println();
        }
    };

    //
    public fDPSDKDeviceChangeCallback fDeviceChangeCallback = new fDPSDKDeviceChangeCallback()
    {
        @Override
        public void invoke(int nPDLLHandle, int nChangeType, byte[] szDeviceId, byte[] szDepCode, byte[] szNewOrgCode) {
            System.out.printf("DeviceChange Report, nChangeType=[%d], szDeviceId=[%s] szDepCode=[%s] szNewOrgCode=[%s]",
                    nChangeType, new String(szDeviceId).trim(), new String(szDepCode).trim(),new String(szNewOrgCode).trim());
            System.out.println();
        }
    };
    /*
     * 登录
     * */
    public void OnLogin()
    {
        Login_Info_t loginInfo = new Login_Info_t();
        System.out.printf("登录参数，m_strIp："+m_strIp+" m_nPort:"+m_nPort+" m_strUser:"+m_strUser+" m_strPassword:"+m_strPassword);
        loginInfo.szIp = m_strIp.getBytes();
        loginInfo.nPort = m_nPort;
        loginInfo.szUsername = m_strUser.getBytes();
        loginInfo.szPassword = m_strPassword.getBytes();
        loginInfo.nProtocol = dpsdk_protocol_version_e.DPSDK_PROTOCOL_VERSION_II;
        loginInfo.iType = 1;

        int nRet = IDpsdkCore.DPSDK_Login(m_nDLLHandle,loginInfo,10000);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("登录成功，nRet = %d", nRet);
        }else
        {
            System.out.printf("登录失败，nRet = %d", nRet);
        }
        System.out.println();
    }
    /*
     * 加载所有组织树
     * */
    public void LoadAllGroup()
    {
        int nRet = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000 );

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("加载所有组织树成功，nRet = %d， nDepCount = %d", nRet, nGroupLen.nReturnValue);
        }else
        {
            System.out.printf("加载所有组织树失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    /*
     * 获取所有组织树串
     * */
    public void GetGroupStr()
    {
        byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
        System.out.printf("获取所有组织树串参数m_nDLLHandle："+m_nDLLHandle+";szGroupBuf："+szGroupBuf+";nGroupLen.nReturnValue："+nGroupLen.nReturnValue);
        int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            String GroupBuf = "";
            try {
                GroupBuf = new String(szGroupBuf, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("获取所有组织树串成功，nRet = %d， szGroupBuf = [%s]", nRet, GroupBuf);
            try {
                File file = new File("D:\\text.xml");
                if(!file.exists())
                {
                    file.createNewFile();
                }

                FileOutputStream  out = new FileOutputStream(file);
                out.write(szGroupBuf);
                out.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }else
        {
            System.out.printf("获取所有组织树串失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    /*
     * 获取用户组织信息
     * */
    public void GetUserOrgInfo()
    {
        GetUserOrgInfo userOrgInfo = new GetUserOrgInfo();
        int nRet = IDpsdkCore.DPSDK_GetUserOrgInfo(m_nDLLHandle, userOrgInfo, 10000 );

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("获取用户组织信息成功，nRet = %d， UserOrgInfo = %s", nRet, userOrgInfo.strUserOrgInfo);
        }else
        {
            System.out.printf("获取用户组织信息失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    /*
     * 获取Ftp信息
     * */
    public void GetFtpInfo()
    {
        byte[] szFtpInfo = new byte[64];
        int nRet = IDpsdkCore.DPSDK_GetFTPInfo(m_nDLLHandle, szFtpInfo, 64);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            String FtpInfo = "";
            try {
                FtpInfo = new String(szFtpInfo, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("获取Ftp信息成功，nRet = %d， szFtpInfo = [%s]", nRet, FtpInfo);
        }else
        {
            System.out.printf("获取Ftp信息失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    public void GetGPSXML()
    {
        Return_Value_Info_t nGpsXMLLen = new Return_Value_Info_t();
        int nRet = IDpsdkCore.DPSDK_AskForLastGpsStatusXMLStrCount(m_nDLLHandle, nGpsXMLLen, 10*1000);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nGpsXMLLen.nReturnValue > 0)
        {
            byte[] LastGpsIStatus = new byte[nGpsXMLLen.nReturnValue - 1];
            nRet = IDpsdkCore.DPSDK_AskForLastGpsStatusXMLStr(m_nDLLHandle, LastGpsIStatus, nGpsXMLLen.nReturnValue);

            if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
            {
                System.out.printf("获取GPS XML成功，nRet = %d， LastGpsIStatus = [%s]", nRet, new String(LastGpsIStatus));
                try {
                    File file = new File("D:\\GPS.xml");
                    if(!file.exists())
                    {
                        file.createNewFile();
                    }

                    FileOutputStream  out = new FileOutputStream(file);
                    out.write(LastGpsIStatus);
                    out.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }else
            {
                System.out.printf("获取GPS XML失败，nRet = %d", nRet);
            }
        }else if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS && nGpsXMLLen.nReturnValue == 0)
        {
            System.out.printf("获取GPS XML  XMLlength = 0");
        }
        else
        {
            System.out.printf("获取GPS XML失败，nRet = %d", nRet);
        }
        System.out.println();
    }


    /*
     * 查询NVR设备的通道状态(主动查询接口，状态信息通过NVR通道状态回调获取)
     * */
    public void QureyNVRChannelStatus()
    {
        Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
        int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, m_strNVRDeviceID.getBytes(),deviceInfo);
        if(deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_EVS || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_SMART_NVR || deviceInfo.nDevType == dpsdk_dev_type_e.DEV_MATRIX_NVR6000)
        {
            nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, m_strNVRDeviceID.getBytes(), 10*1000);

            if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
            {
                System.out.printf("查询NVR通道状态成功，nRet = %d", nRet);
            }else
            {
                System.out.printf("查询NVR通道状态失败，nRet = %d", nRet);
            }
            System.out.println();
        }
    }

    /*
     * 分级加载组织树(根据参数nOperation控制获取所有还是分级获取)
     * */
    public void LoadGroup()
    {
        String strCoding="001";
        Load_Dep_Info_t depInfo = new Load_Dep_Info_t();
        depInfo.nOperation = dpsdk_getgroup_operation_e.DPSDK_CORE_GEN_GETGROUP_OPERATION_CHILD;
        depInfo.szCoding=strCoding.getBytes();
        Return_Value_Info_t nLen = new Return_Value_Info_t();
        int nRet = IDpsdkCore.DPSDK_LoadDGroupInfoLayered(m_nDLLHandle,depInfo,nLen,10000);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("加载组织树成功，nRet = %d， nGroupLen = %d", nRet,nLen.nReturnValue);
        }else
        {
            System.out.printf("加载组织树失败，nRet = %d", nRet);
        }
        System.out.println();

        Get_Dep_Count_Info_t depCountInfo = new Get_Dep_Count_Info_t();
        depCountInfo.szCoding=strCoding.getBytes();
        nRet = IDpsdkCore.DPSDK_GetDGroupCount(m_nDLLHandle,depCountInfo);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("获取子组织子设备个数成功，nRet = %d， nDepCount = %d, nDeviceCount= %d", nRet,depCountInfo.nDepCount,depCountInfo.nDeviceCount);
        }else
        {
            System.out.printf("获取子组织子设备失败，nRet = %d", nRet);
        }
        System.out.println();

        Get_Dep_Info_t getInfo = new Get_Dep_Info_t(depCountInfo.nDepCount,depCountInfo.nDeviceCount);
        getInfo.szCoding=strCoding.getBytes();
        nRet = IDpsdkCore.DPSDK_GetDGroupInfo(m_nDLLHandle, getInfo);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("获取子组织子设备信息成功,szCoding = %s, szId= %s", new  String(getInfo.pDepInfo[0].szCoding).trim(),
                    new String(getInfo.pDeviceInfo[0].szId).trim());
        }else
        {
            System.out.printf("获取子组织子设备信息失败,nRet = %d", nRet);
        }
        System.out.println();

    }

    int GetReal()
    {
        Return_Value_Info_t nRealSeq = new Return_Value_Info_t();
        Get_RealStream_Info_t getInfo = new Get_RealStream_Info_t();
        getInfo.szCameraId = m_strRealCamareID.getBytes();
        getInfo.nStreamType = dpsdk_stream_type_e.DPSDK_CORE_STREAMTYPE_MAIN;
        getInfo.nRight = dpsdk_check_right_e.DPSDK_CORE_NOT_CHECK_RIGHT; //不检查权限，请求视频流，无需加载组织结构
        getInfo.nMediaType = dpsdk_media_type_e.DPSDK_CORE_MEDIATYPE_VIDEO;
        getInfo.nTransType = dpsdk_trans_type_e.DPSDK_CORE_TRANSTYPE_TCP;
        getInfo.nTrackID = 701;

        int nRet = IDpsdkCore.DPSDK_GetRealStream(m_nDLLHandle, nRealSeq, getInfo, m_MediaCB, 10000);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("打开实时视频成功，nRet = %d， nSeq = %d", nRet, nRealSeq.nReturnValue);
        }else
        {
            System.out.printf("打开实时视频失败，nRet = %d", nRet);
        }
        System.out.println();
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
            return nRealSeq.nReturnValue;
        else
            return -1;
    }

    void CloseReal(int nRealSeq)
    {
        int nRet = IDpsdkCore.DPSDK_CloseRealStreamBySeq(m_nDLLHandle, nRealSeq, 10000);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("关闭实时视频成功，nRet = %d， nSeq = %d", nRet, nRealSeq);
        }else
        {
            System.out.printf("关闭实时视频失败，nRet = %d", nRet);
        }
        System.out.println();
    }


    void GetExternUrl()
    {
        Get_ExternalRealStreamUrl_Info_t pExternalRealStreamUrlInfo = new Get_ExternalRealStreamUrl_Info_t();
        pExternalRealStreamUrlInfo.szCameraId = m_strRealCamareID.getBytes();
        pExternalRealStreamUrlInfo.nMediaType = 1;
        pExternalRealStreamUrlInfo.nStreamType = 1;
        pExternalRealStreamUrlInfo.nTrackId = 8011;
        pExternalRealStreamUrlInfo.nTransType = 1;
        pExternalRealStreamUrlInfo.bUsedVCS = 0;
        pExternalRealStreamUrlInfo.nVcsbps = 0;
        pExternalRealStreamUrlInfo.nVcsfps = 0;
        pExternalRealStreamUrlInfo.nVcsResolution = 0;
        pExternalRealStreamUrlInfo.nVcsVideocodec = 0;
        int nRet = IDpsdkCore.DPSDK_GetExternalRealStreamUrl(m_nDLLHandle, pExternalRealStreamUrlInfo, 10000);

        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.println(new String(pExternalRealStreamUrlInfo.szUrl).trim());
        }else
        {
            System.out.printf("获取URL失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    /*
     * 设置报警回调
     * */
    public void SetAlarm()
    {
        int nRet = IDpsdkCore.DPSDK_SetDPSDKAlarmCallback(m_nDLLHandle,m_AlarmCB);//设置报警监听函数
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("开启报警监听成功，nRet = %d", nRet);
        }else
        {
            System.out.printf("开启报警监听失败，nRet = %d", nRet);
        }
        System.out.println();

        //Alarm_Enable_Info_t alarmInfo = new Alarm_Enable_Info_t(1);
        //alarmInfo.sources[0].szAlarmDevId = m_strAlarmCamareID.getBytes();
        //alarmInfo.sources[0].nVideoNo = 0;
        //alarmInfo.sources[0].nAlarmInput = 0;
        //alarmInfo.sources[0].nAlarmType = dpsdk_alarm_type_e.DPSDK_CORE_ALARM_TYPE_VIDEO_LOST;
        //int nRet =  IDpsdkCore.DPSDK_EnableAlarm(m_nDLLHandle, alarmInfo,10000);

        //if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        //{
        //	System.out.printf("报警布控成功，nRet = %d", nRet);
        //}else
        //{
        //	System.out.printf("报警布控失败，nRet = %d", nRet);
        //}
        //System.out.println();
    }
    public void run()
    {
        Menu menu = new Menu();
        menu.Run();
    }
    /*
     * 登出
     * */
    public void OnLogout()
    {
        int nRet = IDpsdkCore.DPSDK_Logout(m_nDLLHandle, 10000);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("登出成功，nRet = %d", nRet);
        }else
        {
            System.out.printf("登出失败，nRet = %d", nRet);
        }
        System.out.println();
    }

    /*
     * 释放内存
     * */
    public void OnDestroy()
    {
        int nRet = IDpsdkCore.DPSDK_Destroy(m_nDLLHandle);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS)
        {
            System.out.printf("释放内存成功，nRet = %d", nRet);
        }else
        {
            System.out.printf("释放内存失败，nRet = %d", nRet);
        }
        System.out.println();
    }
}

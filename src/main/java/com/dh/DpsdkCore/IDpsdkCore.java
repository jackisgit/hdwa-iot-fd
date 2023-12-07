package com.dh.DpsdkCore;

import com.dh.DpsdkCore.TvWall.*;

public class IDpsdkCore {
    static {
        try {
            System.loadLibrary("dslalien");
            System.loadLibrary("libdsl");
            System.loadLibrary("PicSDK");
            System.loadLibrary("Infra");
            System.loadLibrary("StreamParser");
            System.loadLibrary("StreamPackage");
            System.loadLibrary("StreamConvertor");
            System.loadLibrary("PlatformSDK");
            System.loadLibrary("DPSDK_Core");
            System.loadLibrary("DPSDK_Java");

        } catch (UnsatisfiedLinkError ulink) {
            ulink.printStackTrace();
        }
    }


    /************************************************************************
     ** 接口定义
     ***********************************************************************/
    static public native int DPSDK_Create(int nType, Return_Value_Info_t nDllHandle, byte szFilePath[]);

    static public native int DPSDK_Destroy(int nPDLLHandle);

    /**
     * 获取错误码信息.
     */
//	static public native  int	DpsdkGetLastError();
    static public native int DPSDK_SetLog(int nPDLLHandle, byte szFilename[]);

    static public native int DPSDK_StartMonitor(int nPDLLHandle, byte szFilename[]);

    static public native int DPSDK_SetDPSDKStatusCallback(int nPDLLHandle, fDPSDKStatusCallback statusCallback);

    static public native int DPSDK_SetDPSDKDeviceChangeCallback(int nPDLLHandle, fDPSDKDeviceChangeCallback deviceChangeCallback);

    static public native int DPSDK_SetDPSDKOrgDevChangeNewCallback(int nPDLLHandle, fDPSDKOrgDevChangeNewCallback fun);

    static public native int DPSDK_SetDPSDKDeviceStatusCallback(int nPDLLHandle, fDPSDKDevStatusCallback deviceStatusCallback);


    static public native int DPSDK_GetDeviceInfoExById(int nPDLLHanle, byte[] szDevId, Device_Info_Ex_t deviceInfoEx);

    static public native int DPSDK_QueryNVRChnlStatus(int nPDLLHandle, byte[] deviceId, int nTimeout);

    static public native int DPSDK_SetDPSDKNVRChnlStatusCallback(int nPDLLHandle, fDPSDKNVRChnlStatusCallback nvrChnlStatusCallback);

    static public native int DPSDK_SetDPSDKAlarmCallback(int nPDLLHandle, fDPSDKAlarmCallback alarmCallback);


    static public native int DPSDK_Login(int nPDLLHandle, Login_Info_t loginInfo, int nTimeout);

    static public native int DPSDK_Logout(int nPDLLHandle, int nTimeout);

    static public native int DPSDK_PreLoginWithEncryption(int nPDLLHandle, LoginWithEncryption_Info_t loginWithEncryptionInfo, int nTimeout);

    static public native int DPSDK_LoginWithEncryption(int nPDLLHandle, LoginWithEncryption_Info_t loginWithEncryptionInfo, int nTimeout);

    static public native int DPSDK_GetUserID(int nPDLLHandle, Return_Value_Info_t nUserId);

    static public native int DPSDK_ChangeUserPassword(int nPDLLHandle, byte szOldPsw[], byte szNewPsw[], int nTimeout);

    static public native int DPSDK_GetUserLevel(int nPDLLHandle, Return_Value_Info_t nUserLevel);

    static public native int DPSDK_GetMapAddrInfo(int nPDLLHandle, Config_Emap_Addr_Info_t mapAddrInfo, int nTimeout);

    static public native int DPSDK_SetCompressType(int nPDLLHandle, int nCompressType);

    static public native int DPSDK_LoadDGroupInfo(int nPDLLHandle, Return_Value_Info_t nGroupLen, int nTimeout);

    static public native int DPSDK_LoadOrgInfoByType(int nPDLLHandle, int type, Return_Value_Info_t nOrgCount, int nTimeout);

    static public native int DPSDK_LoadDGroupInfoLayered(int nPDLLHandle, Load_Dep_Info_t depInfo, Return_Value_Info_t nGroupLen, int nTimeout);

    static public native int DPSDK_GetDGroupStr(int nPDLLHandle, byte szGroupBuf[], int nGroupLen, int nTimeout);

    static public native int DPSDK_GetDGroupRootInfo(int nPDLLHandle, Dep_Info_t getInfo);

    static public native int DPSDK_GetDGroupCount(int nPDLLHandle, Get_Dep_Count_Info_t getInfo);

    static public native int DPSDK_GetDGroupInfo(int nPDLLHandle, Get_Dep_Info_t getInfo);

    static public native int DPSDK_GetChannelInfo(int nPDLLHandle, Get_Channel_Info_t getInfo);

    static public native int DPSDK_GetChannelInfoEx(int nPDLLHandle, Get_Channel_Info_Ex_t getInfo);

    static public native int DPSDK_GetEncChannelCount(int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nEncChannelCount);

    static public native int DPSDK_GetDevStreamType(int nPDLLHandle, Get_Dev_StreamType_Info_t getInfo);

    static public native int DPSDK_GetChnlType(int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nUnitType);

    static public native int DPSDK_GetChannelInfoById(int nPDLLHanle, byte szCameraId[], Enc_Channel_Info_Ex_t ChannelInfo);

    static public native boolean DPSDK_HasLogicOrg(int nPDLLHandle);

    static public native int DPSDK_GetLogicRootDepInfo(int nPDLLHandle, Dep_Info_Ex_t depInfoEx);

    static public native int DPSDK_GetLogicDepNodeNum(int nPDLLHandle, byte szDepCoding[], int nNodeType, Return_Value_Info_t nDepNodeNum);

    static public native int DPSDK_GetLogicSubDepInfoByIndex(int nPDLLHandle, byte szDepCoding[], int nIndex, Dep_Info_Ex_t depInfoEx);

    static public native int DPSDK_GetLogicID(int nPDLLHandle, byte szDepCoding[], int nIndex, boolean bChnl, Return_Value_ByteArray_t szCodeID);

    static public native int DPSDK_GetRealStream(int nPDLLHandle, Return_Value_Info_t nRealSeq, Get_RealStream_Info_t getInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);

    static public native int DPSDK_GetExternalRealStreamUrl(int nPDLLHandle, Get_ExternalRealStreamUrl_Info_t pExternalRealStreamUrlInfo, int nTimeout);

    static public native int DPSDK_GetRealStreamUrl(int nPDLLHandle, Get_RealStreamUrl_Info_t pRealStreamUrlInfo, int nTimeout);

    static public native int DPSDK_CloseRealStreamBySeq(int nPDLLHandle, int nRealSeq, int nTimeout);


    static public native int DPSDK_CloseRealStreamByCameraId(int nPDLLHandle, byte szCameraId[], int nTimeout);


    static public native int DPSDK_GetTalkStream(int nPDLLHandle, Return_Value_Info_t nTalkSeq, Get_TalkStream_Info_t getInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);

    static public native int DPSDK_CloseTalkStreamBySeq(int nPDLLHandle, int nRealSeq, int nTimeout);


    static public native int DPSDK_CloseTalkStreamByCameralId(int nPDLLHandle, byte szCameraId[], int nTimeout);

    static public native int DPSDK_GetSdkAudioCallbackInfo(int nPDLLHandle, Audio_Fun_Info_t audioFunInfo);

    static public native int DPSDK_SendAudioData(int nPDLLHandle, Send_Audio_Data_Info_t sendAudioDataInfo);

    static public native int DPSDK_QueryRecord(int nPDLLHandle, Query_Record_Info_t queryInfo, Return_Value_Info_t nRecordCount, int nTimeout);


    static public native int DPSDK_QueryAlarmRecord(int nPDLLHandle, byte szAlarmId[], Return_Value_Info_t nRecordCount, int nTimeout);

    static public native int DPSDK_QueryRecordDaysofTheMonth(int nPDLLHandle, byte szCameraId[], int nSource, int nRecordType, long nData, byte szDays[], Return_Value_Info_t nDaysLen, int nTimeout);

    static public native int DPSDK_QueryRecordByStreamType(int nPDLLHandle, Query_Record_Info_t queryInfo, int nStreamType, Return_Value_Info_t nRecordCount, int nTimeout);


    static public native int DPSDK_GetRecordInfo(int nPDLLHandle, Record_Info_t pRecords);

    static public native int DPSDK_GetRecordStreamByFile(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_File_Info_t recordInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);

    static public native int DPSDK_GetRecordStreamByTime(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);

    static public native int DPSDK_GetRecordStreamByStreamType(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int nStreamType, fMediaDataCallback cbMediaDataCallback, int nTimeout);

    static public native int DPSDK_PauseRecordStreamBySeq(int nPDLLHandle, int nPlaybackSeq, int nTimeout);


    static public native int DPSDK_ResumeRecordStreamBySeq(int nPDLLHandle, int nPlaybackSeq, int nTimeout);

    static public native int DPSDK_SetRecordStreamSpeed(int nPDLLHandle, int nPlaybackSeq, int nSpeed, int nTimeout);

    static public native int DPSDK_SeekRecordStreamBySeq(int nPDLLHandle, int nPlaybackSeq, long lSeekBegin, long lSeekEnd, int nTimeout);

    static public native int DPSDK_CloseRecordStreamBySeq(int nPDLLHandle, int nPlaybackSeq, int nTimeout);

    static public native int DPSDK_CloseRecordStreamByCameraId(int nPDLLHandle, byte szCameraId[], int nTimeout);

    static public native int DPSDK_PtzDirection(int nPDLLHandle, Ptz_Direct_Info_t directInfo, int nTimeout);

    static public native int DPSDK_PtzCameraOperation(int nPDLLHandle, Ptz_Operation_Info_t operationInfo, int nTimeout);


    static public native int DPSDK_PtzSit(int nPDLLHandle, Ptz_Sit_Info_t sitInfo, int nTimeout);

    static public native int DPSDK_QueryPtzSitInfo(int nPDLLHandle, Ptz_Sit_Info_t pSitInfo, int nTimeout);

    static public native int DPSDK_PtzLockCamera(int nPDLLHandle, Ptz_Lock_Info_t lockInfo, int nTimeout);

    static public native int DPSDK_PtzLightControl(int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout);

    static public native int DPSDK_PtzRainBrushControl(int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout);

    static public native int DPSDK_PtzInfraredControl(int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout);

    static public native int DPSDK_QueryPrePoint(int nPDLLHandle, Ptz_Prepoint_Info_t prePointInfo, int nTimeout);

    static public native int DPSDK_PtzPrePointOperation(int nPDLLHandle, Ptz_Prepoint_Operation_Info_t prePointOperation, int nTimeout);

    static public native int DPSDK_PtzExtendCommand(int nPDLLHandle, Ptz_Extend_Command_Info_t ptzExtCmd, int nTimeout);

    static public native int DPSDK_PtzCtrlOut(int nPDLLHandle, Ptz_Ctrl_Out_Info_t pCtrlOut, int nTimeout);

    static public native int DPSDK_QueryCruise(int nPDLLHandle, byte szCameraId[], int clientId, int nTimeout);

    static public native int DPSDK_GetCruiseCount(int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nCruiseCount);

    static public native int DPSDK_GetPrePointCountList(int nPDLLHandle, byte szCameraId[], Cruise_Prepoint_Count_List_t PrePointCountlist);

    static public native int DPSDK_GetCruiseInfo(int nPDLLHandle, byte szCameraId[], Cruise_Prepoint_Count_List_t PrePointCountlist, CruiseList_t cruiseList);

    static public native int DPSDK_AddCruise(int nPDLLHandle, byte szCameraId[], CruiseInfo_t struCruiseInfo, int nTimeout);

    static public native int DPSDK_DeleteCruise(int nPDLLHandle, byte szCameraId[], int id, int nTimeout);

    static public native int DPSDK_PtzCruiseOperation(int nPDLLHandle, byte szCameraId[], int cruiseId, int cmd, int nTimeout);

    static public native int DPSDK_GetDeviceListLen(int nPDLLHandle, Return_Value_Info_t nDevListLen, int nTimeout);

    static public native int DPSDK_GetDeviceListStr(int nPDLLHandle, byte szDevList[], int nDevListLen, int nTimeout);

    static public native int DPSDK_GetDevicesInfoLen(int nPDLLHandle, byte szDevicesId[][], int nDevicesCount, Return_Value_Info_t nDevInfoLen, int nTimeout);

    static public native int DPSDK_GetDevicesInfoStr(int nPDLLHandle, byte szDevicesInfo[], int nDevInfoLen, int nTimeout);

    static public native int DPSDK_SetDPSDKTalkParamCallback(int nPDLLHandle, fDPSDKTalkParamCallback fun);

    static public native int DPSDK_GetChannelStatus(int nPDLLHanle, byte szCameraId[], Return_Value_Info_t nStatus);

    static public native int DPSDK_GetDeviceTypeByDevId(int nPDLLHandle, byte szDevId[], Return_Value_Info_t nDeviceType);

    static public native int DPSDK_ControlVideoAlarmHost(int nPDLLHandle, byte szDeviceId[], int channelId, int controlType, int nTimeout);

    static public native int DPSDK_ControlNetAlarmHostCmd(int nPDLLHandle, byte szId[], int opttype, int controlType, long iStart, long iEnd, int nTimeout);

    static public native int DPSDK_SetDPSDKRemoteDeviceSnapCallback(int nPDLLHandle, fDPSDKRemoteDeviceSnapCallback fun);

    static public native int DPSDK_SendCammandToCMSByJson(int nPDLLHandle, byte[] szJson, byte[] szJsonResult, int nTimeout);

    static public native int DPSDK_SendCammandToDMSByJson(int nPDLLHandle, byte[] szJson, byte[] szCamId, byte[] szJsonResult, int nTimeout);

    static public native int DPSDK_GetUserInfo(int nPDLLHandle, DPSDK_UserInfo_t userInfo, int nTimeout);

    static public native int DPSDK_GetAlarmInChannelInfo(int nPDLLHandle, Get_AlarmInChannel_Info_t alarmInChnlInfo);

    static public native int DPSDK_QueryNetAlarmHostChannelCount(int nPDLLHandle, byte[] szDeviceId, Return_Value_Info_t nChannelcount, int nTimeout);

    static public native int DPSDK_QueryNetAlarmHostStatus(int nPDLLHandle, byte[] szDeviceId, int nChannelcount, dpsdk_AHostDefenceStatus_t[] defenceStatus, int nTimeout);

    static public native int DPSDK_SetVideoAlarmHostStatusCallback(int nPDLLHandle, fDPSDKVideoAlarmHostStatusCallback fun);

    static public native int DPSDK_SetNetAlarmHostStatusCallback(int nPDLLHandle, fDPSDKNetAlarmHostStatusCallback fun);

    static public native int DPSDK_SetBroadcastReportStatusCallback(int nPDLLHandle, fDPSDKBroadcastReportStatusCallback fun);

    static public native int DPSDK_PhoneSubscribeAlarm(int nPDLLHandle, dpsdk_phone_subscribe_alarm_t userParam, Return_Value_Info_t nIsSubscribe, int nTimeout);

    static public native int DPSDK_EnableAlarm(int nPDLLHandle, Alarm_Enable_Info_t sourceInfo, int nTimeout);

    static public native int DPSDK_EnableAlarmByDepartment(int nPDLLHandle, Alarm_Enable_By_Dep_Info_t sourceInfo, int nTimeout);

    static public native int DPSDK_DisableAlarm(int nPDLLHandle, int nTimeout);

    static public native int DPSDK_QueryAlarmCount(int nPDLLHandle, Alarm_Query_Info_t pQuery, Return_Value_Info_t nCount, int nTimeout);

    static public native int DPSDK_QueryAlarmInfo(int nPDLLHandle, Alarm_Query_Info_t pQuery, Alarm_Info_t pInfo, int nFirstNum, int nQueryCount, int nTimeout);

    static public native int DPSDK_SendOSDInfo(int nPDLLHandle, Send_OSDInfo_t pOpeInfo, int nTimeout);

    static public native int DPSDK_SetOSDInfo(int nPDLLHandle, OSD_Info_t struOSDInfo, int nTimeout);

    static public native int DPSDK_ShareVideo(int nPDLLHanle, ShareVideoInfo videoInfoArray[], int userIdArray[], byte[] szMsg, int nTimeout);

    static public native int DPSDK_GetUserOrgInfo(int nPDLLHandle, GetUserOrgInfo userOrgInfo, int nTimeout);

    static public native int DPSDK_SetGeneralJsonTransportCallback(int nPDLLHandle, fDPSDKGeneralJsonTransportCallback fun);

    static public native int DPSDK_GeneralJsonTransport(int nPDLLHandle, byte[] szJson, int mdltype, int trantype, int nTimeout);

    static public native int DPSDK_GetAlarmSchemeCount(int nPDLLHandle, Return_Value_Info_t nCount, int nTimeout);


    static public native int DPSDK_GetAlarmSchemeList(int nPDLLHandle, AlarmSchemeInfo_t[] alarmSchemeList, int nTimeout);

    static public native int DPSDK_GetSchemeFileDataLen(int nPDLLHandle, int nSchemeId, Return_Value_Info_t nLen, int nTimeout);

    static public native int DPSDK_GetSchemeFile(int nPDLLHandle, int nSchemeId, AlarmSchemeFileInfo_t alarmSchemeFileInfo, int nTimeout);

    static public native int DPSDK_SaveAlarmScheme(int nPDLLHandle, AlarmSchemeInfo_t alarmScheme, int nTimeout);

    static public native int DPSDK_SendScsMsg(int nPDLLHandle, byte[] szGroupId, byte[] szStrText, int nTimeout);


//////////////////////////////////////////////////////////////////////////
// 电视墙业务接口 开始

    static public native int DPSDK_GetTvWallListCount(int nPDLLHandle, Return_Value_Info_t nCount, int nTimeout);

    static public native int DPSDK_GetTvWallList(int nPDLLHandle, TvWall_List_Info_t pTvWallListInfo);

    static public native int DPSDK_GetTvWallLayoutCount(int nPDLLHandle, int nTvWallId, Return_Value_Info_t nCount, int nTimeout);

    static public native int DPSDK_GetTvWallLayout(int nPDLLHandle, TvWall_Layout_Info_t pTvWallLayoutInfo);

    static public native int DPSDK_SetTvWallScreenWindowSource(int nPDLLHandle, Set_TvWall_Screen_Window_Source_t pWindowSourceInfo, int nTimeout);

    static public native int DPSDK_CloseTvWallScreenWindowSource(int nPDLLHandle, TvWall_Screen_Close_Source_t pCloseSourceInfo, int nTimeout);

    static public native int DPSDK_SetTvWallScreenSplit(int nPDLLHandle, TvWall_Screen_Split_t pSplitInfo, int nTimeout);

    static public native int DPSDK_TvWallScreenOpenWindow(int nPDLLHandle, TvWall_Screen_Open_Window_t pOpenWindowInfo, int nTimeout);

    static public native int DPSDK_TvWallScreenCloseWindow(int nPDLLHandle, TvWall_Screen_Close_Window_t pCloseWindowInfo, int nTimeout);

    static public native int DPSDK_ClearTvWallScreen(int nPDLLHandle, int nTvWallId, int nTimeout);

    static public native int DPSDK_ClearTvWallScreenByDecodeId(int nPDLLHandle, int nTvWallId, byte[] szDecodeId, int nTimeout);

    static public native int DPSDK_GetTvWallTaskListCount(int nPDLLHandle, int nTVWallId, Return_Value_Info_t nCount, int nTimeout);

    static public native int DPSDK_GetTvWallTaskList(int nPDLLHandle, int nTVWallId, TvWall_Task_Info_List_t pTVWallTaskInfoList, int nTimeout);

    static public native int DPSDK_GetTvWallTaskInfoLen(int nPDLLHandle, int nTVWallId, int nTaskId, Return_Value_Info_t pTaskInfoLen, int nTimeout);

    static public native int DPSDK_GetTvWallTaskInfoStr(int nPDLLHandle, byte szTaskInfoBuf[], int nTaskInfoLen);

    static public native int DPSDK_MapTaskToTvWall(int nPDLLHandle, int nTVWallId, int nTaskId, int nTimeout);

    static public native int DPSDK_ClearSingleScreen(int nPDLLHandle, int nTvWallId, int nScreenId, int nTimeout);

// 电视墙业务接口 结束

    /** 根据设备ID获取报警输出通道个数
     nPDLLHandle		SDK句柄
     OUT	pstruUserInfo	用户信息结构体
     @return 函数返回错误类型，参考dpsdk_retval_e
     */
    //static public native int  DPSDK_GetAlarmOutChannelCount( int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nCount);

    /** 根据设备ID获取报警输出通道信息
     nPDLLHandle		SDK句柄
     OUT	pstruUserInfo	用户信息结构体
     @return 函数返回错误类型，参考dpsdk_retval_e
     @remark 1、pAlarmOutChannelnfo需要在外面创建好
     2、pAlarmOutChannelnfo的个数与DPSDK_GetAlarmOutChannelCount返回时有报警主机设备id和报警输入通道个数
     */
    //static public native int  DPSDK_GetAlarmOutChannelInfo( int nPDLLHandle, Get_AlarmOutChannel_Info_t pstruUserInfo);

    /**
     * 报警动作输出.
     * IN	nPDLLHandle		SDK句柄
     * IN	pPtzCtrlOutInfo 报警动作输出信息
     * nTimeout		超时时长，单位毫秒
     *
     * @return 函数返回错误类型，参考dpsdk_retval_e
     * @remark
     */
    //static public native int  DPSDK_PtzCtrlOut( int nPDLLHandle, Ptz_Ctrl_Out_Info_t pPtzCtrlOutInfo, int nTimeout );
    static public native int DPSDK_SetDoorCmd(int nPDLLHandle, SetDoorCmd_Request_t pRequest, int nTimeout);

    static public native int DPSDK_SetPecDoorStatusCallback(int nPDLLHandle, fDPSDKPecDoorStarusCallBack pFun);

    /**
     * 获取通道信息.
     * nPDLLHandle		SDK句柄
     * INOUT	pGetInfo		子通道信息
     *
     * @return 函数返回错误类型，参考dpsdk_retval_e
     * @remark 1、暂时只支持门禁通道的获取，其他类型后续扩展
     */
    //static public native int DPSDK_GetChannelBaseInfo( int nPDLLHandle, byte szCameraId[], ChannelBase_Info_t pGetInfo );
    static public native int DPSDK_QueryLinkResource(int nPDLLHandle, Return_Value_Info_t nLen, int nTimeout);

    static public native int DPSDK_GetLinkResource(int nPDLLHandle, GetLinkResource_Responce_t pResponce);

    /** 获取设备下编码器通道的信息.
     nPDLLHandle		SDK句柄
     INOUT	pGetInfo		子通道信息
     @return 函数返回错误类型，参考dpsdk_retval_e
     @remark 1、pEncChannelnfo需要在外面创建好
     2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
     */
    //static public native  int DPSDK_GetPosChannelCount( int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nPosChannelCount);

    /**
     * 获取设备下编码器通道的信息.
     * nPDLLHandle		SDK句柄
     * INOUT	pGetInfo		子通道信息
     *
     * @return 函数返回错误类型，参考dpsdk_retval_e
     * @remark 1、pEncChannelnfo需要在外面创建好
     * 2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
     */
    //static public native  int DPSDK_GetPosChannelInfo( int nPDLLHandle, Get_PosChannel_Info_t getInfo );
    static public native int DPSDK_SaveOptLog(int nPDLLHandle, byte szCameraId[], long optTime, int optType, byte optDesc[]);

    static public native int DPSDK_ControlDevBurner(int nPDLLHandle, Control_Dev_Burner_Request_t pControlDevBurnerRequest, int nTimeout);

    static public native int DPSDK_SetDevBurnerHeader(int nPDLLHandle, DevBurnerInfoHeader_t pInfoHeader, TrialFormAttrName_t pAttrName, int nTimeout);

    static public native int DPSDK_GetDeviceDiskInfoCount(int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nInfoCount, Return_Value_Info_t nSequence, int nTimeout);

    static public native int DPSDK_GetDeviceDiskInfo(int nPDLLHandle, int nSequence, Device_Disk_Info_t pDiskInfo);

    static public native int DPSDK_SendAlarmToServer(int nPDLLHandle, Client_Alarm_Info_t pAlarmInfo, int nTimeout);

    static public native int DPSDK_AskForLastGpsStatusXMLStrCount(int nPDLLHandle, Return_Value_Info_t nGpsXMLLen, int nTimeout);

    static public native int DPSDK_AskForLastGpsStatusXMLStr(int nPDLLHandle, byte LastGpsIStatus[], int nGpsXMLLen);

    static public native int DPSDK_SetDPSDKGetBayCarInfoCallbackEx(int nPDLLHandle, fDPSDKGetBayCarInfoCallbackEx BayCarInfoCallback);

    static public native int DPSDK_SubscribeBayCarInfo(int nPDLLHandle, Subscribe_Bay_Car_Info_t pGetInfo, int nTimeout);

    static public native int DPSDK_QueryPersonCount(int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nQuerySeq, Return_Value_Info_t totalCount, int nStartTime, int nEndTime, int nGranularity, int nTimeout);

    static public native int DPSDK_QueryPersonCountBypage(int nPDLLHandle, byte szCameraId[], int nQuerySeq, int nIndex, int nCount, Person_Count_Info_t pPersonInfo[], int nTimeout);

    static public native int DPSDK_StopQueryPersonCount(int nPDLLHandle, byte szCameraId[], int nQuerySeq, int nTimeout);

    static public native int DPSDK_GetFTPInfo(int nPDLLHandle, byte szFtpInfo[], int iSize);

    static public native int DPSDK_SetDPSDKTrafficAlarmCallback(int nPDLLHandle, fDPSDKTrafficAlarmCallback TrafficAlarmCallback);

    static public native int DPSDK_SubscribeAreaSpeedDetectInfo(int nPDLLHandle, int nSubscribeFlag, int nTimeout);

    static public native int DPSDK_SetDPSDKGetAreaSpeedDetectCallback(int nPDLLHandle, fDPSDKGetAreaSpeedDetectCallback fun);

    static public native int DPSDK_SaveRealStream(int nPDLLHandle, Return_Value_Info_t nRealSeq, Get_RealStream_Info_t getInfo, int scType, byte szFilePath[], int nTimeout);

    static public native int DPSDK_SaveRecordStreamByFile(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_File_Info_t recordInfo, int scType, byte szFilePath[], int nTimeout);

    static public native int DPSDK_SaveRecordStreamByTime(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int scType, byte szFilePath[], int nTimeout);

    static public native int DPSDK_SaveRecordStreamByStreamType(int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int scType, int nStreamType, byte szFilePath[], int nTimeout);

    static public native int DPSDK_SetDPSDKSaveRecordFinishedCallback(int nPDLLHandle, fSaveRecordFinishedCallback fun);

    static public native int DPSDK_StartPlatformReocrd(int nPDLLHandle, byte szCameraId[], int streamType, int nTimeout);

    static public native int DPSDK_StopPlatformReocrd(int nPDLLHandle, byte szCameraId[], int nTimeout);

    static public native int DPSDK_StartDeviceRecord(int nPDLLHandle, byte szDevId[], byte szSN[], int nTimeout);

    static public native int DPSDK_StopDeviceRecord(int nPDLLHandle, byte szDevId[], byte szSN[], int nTimeout);

    static public native int DPSDK_ClearDevAlarm(int nPDLLHandle, byte szCameraId[], int nAlarmType, int nTimeout);

    static public native int DPSDK_GetPlaybackByTimeUrl(int nPDLLHandle, Get_RecordStreamUrl_Time_Info_t pRecordStreamUrlInfo, int nTimeout);

    static public native int DPSDK_DecodeDevPwd(int nPDLLHandle, byte szEnDevPwd[], byte szDevPwd[]);
}



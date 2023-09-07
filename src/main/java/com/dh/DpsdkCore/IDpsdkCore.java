package com.dh.DpsdkCore;

import com.dh.DpsdkCore.TvWall.Set_TvWall_Screen_Window_Source_t;
import com.dh.DpsdkCore.TvWall.TvWall_Layout_Info_t;
import com.dh.DpsdkCore.TvWall.TvWall_List_Info_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Close_Source_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Split_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Open_Window_t;
import com.dh.DpsdkCore.TvWall.TvWall_Screen_Close_Window_t;
import com.dh.DpsdkCore.TvWall.TvWall_Task_Info_List_t;

public class IDpsdkCore 
{
    static {
    	try
    	{
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
    		
    	}
		catch(UnsatisfiedLinkError ulink)
		{    
	        ulink.printStackTrace();   
		}  
    }

   	
    /************************************************************************
     ** 接口定义
     ***********************************************************************/
	/** 创建SDK句柄.
	 nType			SDK类型
	 OUT	nDllHandle		返回SDK句柄，后续操作需要以此作为参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark									
	*/
	static public native  int	DPSDK_Create(int nType, Return_Value_Info_t nDllHandle, byte szFilePath[]);
	
	/** 删除SDK句柄.
	 nPDLLHandle		SDK句柄
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	 1、需要和DPSDK_Create成对使用
	*/
	static public native  int	DPSDK_Destroy( int nPDLLHandle );
	
	/** 获取错误码信息.
	*/
//	static public native  int	DpsdkGetLastError();
	
	/** 设置日志.
	 szFilename		文件名称
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark									
	*/
	static public native int DPSDK_SetLog(int nPDLLHandle, byte szFilename[]);
	
	/** 打开程序崩溃监听.
	 szFilename		程序崩溃后，自动生成的文件路径和文件名称
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark									
	*/
	static public native int DPSDK_StartMonitor(int nPDLLHandle, byte szFilename[]);

	/** 设置DPSDK状态回调.
	 nPDLLHandle				SDK句柄
	 statusCallback			回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_SetDPSDKStatusCallback( int nPDLLHandle, fDPSDKStatusCallback statusCallback);

	/** 设置DPSDK设备变更回调.
	 nPDLLHandle				SDK句柄
	 deviceChangeCallback	回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_SetDPSDKDeviceChangeCallback(int nPDLLHandle, fDPSDKDeviceChangeCallback deviceChangeCallback);
	
	/** 设置DPSDK新组织设备变更回调.
	param   IN	nPDLLHandle		SDK句柄
	fun				回调函数
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark
	*/
	static public native int DPSDK_SetDPSDKOrgDevChangeNewCallback( int nPDLLHandle, fDPSDKOrgDevChangeNewCallback fun);

	/** 设置DPSDK设备状态回调.
	 nPDLLHandle					SDK句柄
	 deviceStatusCallback		回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_SetDPSDKDeviceStatusCallback(int nPDLLHandle, fDPSDKDevStatusCallback deviceStatusCallback);
	
	
	/** 根据设备ID获取设备信息
	IN    nPDLLHandle     SDK句柄
	IN    szDevId		   设备ID
	OUT   pDeviceInfoEx   设备信息
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_GetDeviceInfoExById(int nPDLLHanle,  byte[] szDevId, Device_Info_Ex_t deviceInfoEx);
	
	/** 查询NVR通道状态
	IN    nPDLLHandle     SDK句柄
	IN    deviceId	       设备的ID
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_QueryNVRChnlStatus(int nPDLLHandle, byte[] deviceId, int nTimeout);

	/** 设置NVR通道状态回调
	nPDLLHandle		SDK句柄
	fun				回调函数
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 通道状态变化的时候会推送
	*/
	static public native int DPSDK_SetDPSDKNVRChnlStatusCallback(int nPDLLHandle, fDPSDKNVRChnlStatusCallback nvrChnlStatusCallback);
	
	/** 设置报警状态回调.
	 nPDLLHandle					SDK句柄
	 alarmCallback				回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
		1、需要和DPSDK_Create成对使用
	*/
	static public native int DPSDK_SetDPSDKAlarmCallback(int nPDLLHandle, fDPSDKAlarmCallback alarmCallback);

	
	/** 注册平台.
	 nPDLLHandle		SDK句柄
	 loginInfo		用户登录信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int  DPSDK_Login( int nPDLLHandle, Login_Info_t loginInfo, int nTimeout);
	
	/** 登出平台.
	 nPDLLHandle		SDK句柄
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int  DPSDK_Logout( int nPDLLHandle, int nTimeout);

	/** 认证登录预登录.
	 nPDLLHandle		SDK句柄
	 loginWithEncryptionInfo	认证登录信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int  DPSDK_PreLoginWithEncryption( int nPDLLHandle, LoginWithEncryption_Info_t loginWithEncryptionInfo, int nTimeout);
	
	/** 认证登录.
	 nPDLLHandle		SDK句柄
	 loginWithEncryptionInfo	认证登录信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int  DPSDK_LoginWithEncryption( int nPDLLHandle, LoginWithEncryption_Info_t loginWithEncryptionInfo, int nTimeout);
	
	/** 获取用户Id.
	 nPDLLHandle		SDK句柄
	 nUserId			用户Id
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetUserID( int nPDLLHandle,  Return_Value_Info_t nUserId);

	/** 更改用户密码.
	 nPDLLHandle		SDK句柄
	 szOldPsw		原有密码
	 szNewPsw		新密码
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_ChangeUserPassword( int nPDLLHandle, byte szOldPsw[], byte szNewPsw[], int nTimeout);

	/** 获取用户等级.
	 nPDLLHandle		SDK句柄
	 nUserLevel		用户等级
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetUserLevel( int nPDLLHandle, Return_Value_Info_t nUserLevel );
	
	/** 获取电子地图服务配置信息
	 nPDLLHandle		SDK句柄
	 mapAddrInfo		电子地图服务配置信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetMapAddrInfo( int nPDLLHandle, Config_Emap_Addr_Info_t mapAddrInfo, int nTimeout);

	/** 获取组织结构时，设置是否采用压缩格式获取.
	 nPDLLHandle			SDK句柄
	 nCompressType		是否采用压缩格式,参考dpsdk_get_devinfo_compress_type_e
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark									
	*/
	static public native  int DPSDK_SetCompressType( int nPDLLHandle, int nCompressType);
	
	/** 加载组织设备信息.
	 nPDLLHandle		SDK句柄
	 OUT	nGroupLen       组织结构信息长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_LoadDGroupInfo( int nPDLLHandle, Return_Value_Info_t nGroupLen, int nTimeout );

	/** 加载特定类型组织信息.
	 nPDLLHandle		SDK句柄
	 IN    type            通用节点类型
	 OUT	nOrgCount       返回组织信息的数量
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_LoadOrgInfoByType( int nPDLLHandle, int type, Return_Value_Info_t nOrgCount, int nTimeout );

	/** 分级加载组织设备信息.
	 nPDLLHandle		SDK句柄
	 depInfo			分级获取的节点信息 
	 OUT	nGroupLen       组织结构信息长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int  DPSDK_LoadDGroupInfoLayered( int nPDLLHandle, Load_Dep_Info_t depInfo, Return_Value_Info_t nGroupLen, int nTimeout );
	
	/** 获取组织设备信息串.
	 nPDLLHandle		SDK句柄
	 szGroupBuf		组织结构缓存,需要外部创建缓存，大小为nGroupLen+1
	 OUT	nGroupLen       组织结构信息长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、szGroupBuf需要在外面创建好
	 2、szGroupBuf的大小与nGroupLen需要一致或者大于nGroupLen
	*/
	static public native  int DPSDK_GetDGroupStr( int nPDLLHandle, byte szGroupBuf[], int nGroupLen, int nTimeout );

	/** 获取根节点信息.
	 nPDLLHandle		SDK句柄
	 OUT	getInfo			根节点信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native  int DPSDK_GetDGroupRootInfo(int nPDLLHandle,Dep_Info_t getInfo );

	/** 获取组织下子组织和子设备的个数.
	 nPDLLHandle		SDK句柄
	 INOUT	getInfo			获取组织个数请求信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native  int DPSDK_GetDGroupCount( int nPDLLHandle, Get_Dep_Count_Info_t getInfo );

	/** 获取组织下子组织和子设备的信息.
	 nPDLLHandle		SDK句柄
	 INOUT	getInfo			子组织子设备信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、pDepInfo、pDeviceInfo需要在外面创建好
	 2、pDepInfo、pDeviceInfo的大小与DPSDK_GetDGroupCount返回需要一致
	 */
	static public native  int DPSDK_GetDGroupInfo( int nPDLLHandle, Get_Dep_Info_t getInfo );

	/** 获取设备下子通道的信息.
	 nPDLLHandle		SDK句柄
	 INOUT	getInfo			子通道信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、pEncChannelnfo需要在外面创建好
	 2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
	 */
	static public native  int DPSDK_GetChannelInfo( int nPDLLHandle, Get_Channel_Info_t getInfo );

	/** 获取设备下子通道的信息(扩展).
	 nPDLLHandle		SDK句柄
	 INOUT	getInfo			子通道信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、pEncChannelnfo需要在外面创建好
	 2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
	 */
	static public native  int DPSDK_GetChannelInfoEx( int nPDLLHandle, Get_Channel_Info_Ex_t getInfo );

	/** 获取设备下子通道(编码通道)的个数
	 nPDLLHandle			SDK句柄
	 szDeviceId			设备ID
	 OUT	nEncChannelCount	编码通道个数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int DPSDK_GetEncChannelCount( int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nEncChannelCount);
	
	/** 获取设备支持的码流类型.
	 nPDLLHandle		SDK句柄
	 INOUT	getInfo			获取信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int DPSDK_GetDevStreamType( int nPDLLHandle, Get_Dev_StreamType_Info_t getInfo );

	/** 获取通道类型
	 nPDLLHandle			SDK句柄
	 szCameraId			设备ID
	 OUT	nUnitType			通道类型,参考dpsdk_dev_unit_type_e
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int DPSDK_GetChnlType( int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nUnitType);	

	/** 根据CemeraId获取通道信息
	IN    nPDLLHandle     SDK句柄
	IN    szCameraId      通道ID
	OUT   pChannelInfo    通道信息，参见Enc_Channel_Info_Ex_t
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native  int   DPSDK_GetChannelInfoById(int nPDLLHanle, byte szCameraId[], Enc_Channel_Info_Ex_t ChannelInfo);
	
  	/** 是否有业务树
	 nPDLLHandle		SDK句柄
	 @return  函数返回true表示含有业务树，否则表示没有业务树
	 @remark
	*/
	static public native boolean DPSDK_HasLogicOrg(int nPDLLHandle);	

	/** 获取业务树根节点信息.
	 nPDLLHandle		SDK句柄
	 OUT	depInfoEx		业务树根节点信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native  int DPSDK_GetLogicRootDepInfo(int nPDLLHandle,Dep_Info_Ex_t depInfoEx );

	/** 获取业务树指定节点下 节点/通道/设备的数目
	 nPDLLHandle						SDK句柄
	 IN    szDepCoding						节点Coding
	 IN    nNodeType						组织/通道/设备,参考dpsdk_node_type_e
	 OUT   nDepNodeNum						节点下 节点/通道/设备的数目
	 @return  函数返回错误类型，参考dpsdk_retval_e 
	 @remark 
	*/
	static public native  int DPSDK_GetLogicDepNodeNum(int nPDLLHandle, byte szDepCoding[], int nNodeType, Return_Value_Info_t nDepNodeNum);	

	/** 获取业务树指定节点下 节点信息
	 nPDLLHandle						SDK句柄
	 IN    szDepCoding						节点Coding
	 IN    nIndex							序号
	 OUT   depInfoEx						组织节点信息
	 @return  函数返回错误类型，参考dpsdk_retval_e 
	 @remark    
	*/
	static public native  int DPSDK_GetLogicSubDepInfoByIndex(int nPDLLHandle, byte szDepCoding[], int nIndex, Dep_Info_Ex_t depInfoEx);	
	
	/** 获取业务树指定节点下 设备或者通道ID
		nPDLLHandle						SDK句柄
		szDepCoding						节点Coding
		nIndex							序号
		bChnl							=true 为通道，否则是设备
	OUT	szCodeID						节点ID
	@return  函数返回错误类型，参考dpsdk_retval_e 
	@remark     
	*/
	static public native  int DPSDK_GetLogicID(int nPDLLHandle, byte szDepCoding[], int nIndex, boolean bChnl, Return_Value_ByteArray_t szCodeID);
	
	/** 获取实况码流.
	 nPDLLHandle				SDK句柄
	 OUT	nRealSeq				码流请求序号,可作为后续操作标识 
	 getInfo					码流请求信息 
	 IN    cbMediaDataCallback		码流回调函数				
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetRealStream( int nPDLLHandle, Return_Value_Info_t nRealSeq, Get_RealStream_Info_t getInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout );

	/** 获取实时流的外部URL.
	 IN	nPDLLHandle						SDK句柄
	 IN	pExternalRealStreamUrlInfo 		查询实时流Url信息 
	 nTimeout						超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetExternalRealStreamUrl( int nPDLLHandle, Get_ExternalRealStreamUrl_Info_t pExternalRealStreamUrlInfo, int nTimeout );
	
	/** 获取实时流的URL.
	 IN	nPDLLHandle						SDK句柄
	 IN	pRealStreamUrlInfo 				查询实时流Url信息 
	 nTimeout						超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetRealStreamUrl( int nPDLLHandle, Get_RealStreamUrl_Info_t pRealStreamUrlInfo, int nTimeout );
	
	/** 按请求序号关闭码流.
	 nPDLLHandle		SDK句柄
	 nRealSeq		码流请求序号
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_CloseRealStreamBySeq( int nPDLLHandle, int nRealSeq, int nTimeout );
	

	/** 按CameraId关闭码流.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道编号
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  关闭所有的已经打开的CameraId	
	*/
	static public native  int DPSDK_CloseRealStreamByCameraId( int nPDLLHandle, byte szCameraId[], int nTimeout );
	

	/** 获取语音码流
	 nPDLLHandle				SDK句柄
	 OUT	nTalkSeq				码流请求序号,可作为后续操作标识 
	 getInfo					码流请求信息 
	 IN    cbMediaDataCallback		码流回调函数				
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetTalkStream( int nPDLLHandle, Return_Value_Info_t nTalkSeq, Get_TalkStream_Info_t getInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);
	
	/** 按请求序列停止语音码流的获取
	 nPDLLHandle		SDK句柄
	 nRealSeq		码流请求序号,可作为后续操作标识 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_CloseTalkStreamBySeq( int nPDLLHandle, int nRealSeq, int nTimeout );
	
	
	/** 按CameralId停止语音码流获取.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道编号 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_CloseTalkStreamByCameralId( int nPDLLHandle, byte szCameraId[], int nTimeout );

	/** 获取语音采集回调信息
	 nPDLLHandle		SDK句柄
	 OUT	audioFunInfo	语音采集回调信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_GetSdkAudioCallbackInfo(int nPDLLHandle, Audio_Fun_Info_t audioFunInfo);
	
	/** 向设备端发送语音采集数据
	 nPDLLHandle			SDK句柄
	 OUT	sendAudioDataInfo	语音采集数据
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_SendAudioData(int nPDLLHandle, Send_Audio_Data_Info_t sendAudioDataInfo);	

	/** 查询录像.
	 nPDLLHandle		SDK句柄
	 queryInfo		查询信息
	 OUT	nRecordCount	录像记录个数
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	   1、nRecordCount最大5000个记录
	*/
	static public native  int DPSDK_QueryRecord( int nPDLLHandle, Query_Record_Info_t queryInfo, Return_Value_Info_t nRecordCount, int nTimeout);
	
	
	/** 查询报警录像. 播放时复用CameraId 字段 表示 AlarmID (使用DPSDK_QueryAlarmRecord接口查询的录像播放时对应的cameraId 要填写AlarmID)
	 nPDLLHandle		SDK句柄
	 szAlarmId		报警ID
	 OUT	nRecordCount	录像记录个数
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	   1、nRecordCount最大5000个记录
	*/
	static public native  int DPSDK_QueryAlarmRecord( int nPDLLHandle, byte szAlarmId[], Return_Value_Info_t nRecordCount, int nTimeout);
	
	/** 查询当月有录像的日期.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道编号
	 nSource			录像来源，1全部，2设备，3平台，参考dpsdk_recsource_type_e
	 nRecordType		录像类型，参考dpsdk_record_type_e
	 nData			日期（单位秒），可随意选择所要查询月份里的一个时间点，据1970年1月1日0时0分0秒的秒数
	 OUT	szDays			有录像的天，以逗号隔开,0代表当天没有录像，1代表有录像。上层输入参数推荐days = new char[128];
	 OUT	nDaysLen       	days字符串的长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、szGroupBuf需要在外面创建好
	 2、szGroupBuf的大小与nGroupLen需要一致或者大于nGroupLen
	*/
	static public native  int DPSDK_QueryRecordDaysofTheMonth( int nPDLLHandle, byte szCameraId[], int nSource, int nRecordType, long nData, byte szDays[], Return_Value_Info_t nDaysLen, int nTimeout );

	/** 通过码流类型查询录像.
	 nPDLLHandle				SDK句柄
	 queryInfo				查询信息
	 nStreamType				码流类型,请参考dpsdk_stream_type_e
	 OUT	nRecordCount			录像记录个数
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	   1、nRecordCount最大5000个记录
	*/
	static public native  int DPSDK_QueryRecordByStreamType( int nPDLLHandle, Query_Record_Info_t queryInfo, int nStreamType, Return_Value_Info_t nRecordCount, int nTimeout);
	
	
	/** 获取录像信息.
	 nPDLLHandle		SDK句柄
	 OUT	pRecords		录像信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	   1、必须先查询后获取
	   2、DPSDK_QueryRecord会返回记录个数,
	*/
	static public native  int DPSDK_GetRecordInfo( int nPDLLHandle, Record_Info_t pRecords );

	/** 按文件请求录像流.
	 IN	nPDLLHandle				SDK句柄
	 OUT	nPlaybackSeq			回放请求序号,作为后续操作标识  
	 IN	recordInfo				录像信息 
	 IN    cbMediaDataCallback		码流回调函数				
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_GetRecordStreamByFile( int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_File_Info_t recordInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout );

	/** 按时间请求录像流.
	 IN	nPDLLHandle				SDK句柄
	 OUT	nPlaybackSeq			回放请求序号,作为后续操作标识  
	 IN	recordInfo				录像信息 
	 IN    cbMediaDataCallback		码流回调函数				
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_GetRecordStreamByTime( int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, fMediaDataCallback cbMediaDataCallback, int nTimeout);

	/** 根据码流类型按时间请求录像流.
	 IN	nPDLLHandle					SDK句柄
	 OUT	nPlaybackSeq				回放请求序号,作为后续操作标识  
	 IN	recordInfo					录像信息
	 nStreamType					码流类型,请参考dpsdk_stream_type_e
	 IN    cbMediaDataCallback			码流回调函数				
	 nTimeout					超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_GetRecordStreamByStreamType( int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int nStreamType, fMediaDataCallback cbMediaDataCallback, int nTimeout );	
	
	/** 暂停录像流.
	 IN	nPDLLHandle		SDK句柄
	 nPlaybackSeq	回放请求序号 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PauseRecordStreamBySeq( int nPDLLHandle, int nPlaybackSeq, int nTimeout);
	

	/** 恢复录像流.
	 IN	nPDLLHandle		SDK句柄
	 nPlaybackSeq	回放请求序号 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_ResumeRecordStreamBySeq( int nPDLLHandle, int nPlaybackSeq, int nTimeout );

	/** 设置录像流速率.
	 IN	nPDLLHandle		SDK句柄
	 nPlaybackSeq	回放请求序号 
	 IN    nSpeed,         录像流回放速度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_SetRecordStreamSpeed( int nPDLLHandle, int nPlaybackSeq, int nSpeed, int nTimeout);

	/** 定位回放.
	 IN	nPDLLHandle		SDK句柄
	 nPlaybackSeq	回放请求序号 
	 IN    lSeekBegin,		定位起始值.文件模式时,是定位处的文件大小值;时间模式时,是定位处的时间值;
	 IN    lSeekEnd,		定位结束值.文件模式时,无意义;时间模式时,是期待的结束时间.
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark 
				lSeekBegin在文件模式下的计算方式可以是:(文件大小值)/100*(定位处相对文件的百分比)  
	*/
	static public native  int  DPSDK_SeekRecordStreamBySeq(int nPDLLHandle, int nPlaybackSeq, long lSeekBegin, long lSeekEnd, int nTimeout);

	/** 关闭录像流.
	 IN	nPDLLHandle		SDK句柄
	 nPlaybackSeq	回放请求序号 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_CloseRecordStreamBySeq( int nPDLLHandle, int nPlaybackSeq, int nTimeout );

	/** 按通道关闭录像流.
	 IN	nPDLLHandle		SDK句柄
	 szCameraId   	通道编号 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_CloseRecordStreamByCameraId( int nPDLLHandle, byte szCameraId[], int nTimeout );

	/** 云台方向控制.
	 IN	nPDLLHandle		SDK句柄
	 directInfo		云台方向控制信息 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzDirection( int nPDLLHandle, Ptz_Direct_Info_t directInfo, int nTimeout);

	/** 云台镜头控制.
	 IN	nPDLLHandle		SDK句柄
	 operationInfo	云台镜头控制信息 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzCameraOperation( int nPDLLHandle, Ptz_Operation_Info_t operationInfo, int nTimeout);


	/** 云台三维定位.
	 IN	nPDLLHandle		SDK句柄
	 sitInfo			云台三维定位信息 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzSit( int nPDLLHandle, Ptz_Sit_Info_t sitInfo, int nTimeout);
	
	/** 查询云台三维信息
	IN	   nPDLLHandle	SDK句柄
	INOUT pSitInfo		云台三维定位信息 
	   nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark  
	*/
	static public native int DPSDK_QueryPtzSitInfo( int nPDLLHandle, Ptz_Sit_Info_t pSitInfo, int nTimeout);

	/** 云台锁定控制.
	 IN	nPDLLHandle			SDK句柄
	 lockInfo	    	云台锁定控制信息
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzLockCamera( int nPDLLHandle, Ptz_Lock_Info_t lockInfo, int nTimeout);

	/** 云台灯光控制.
	 IN	nPDLLHandle			SDK句柄
	 IN	ptzOpenCmdInfo		云台灯光控制 
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzLightControl( int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout );

	/** 云台雨刷控制.
	 IN	nPDLLHandle			SDK句柄
	 IN	ptzOpenCmdInfo		云台雨刷控制
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzRainBrushControl( int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout);

	/** 云台红外控制.
	 IN	nPDLLHandle			SDK句柄
	 IN	ptzOpenCmdInfo		云台红外控制 
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzInfraredControl( int nPDLLHandle, Ptz_Open_Command_Info_t ptzOpenCmdInfo, int nTimeout );

	/** 云台查询预置点信息.
	 IN	nPDLLHandle			SDK句柄
	 INOUT	prePointInfo		预置点信息 
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_QueryPrePoint( int nPDLLHandle, Ptz_Prepoint_Info_t prePointInfo, int nTimeout);

	/** 云台预置点操作.
	 IN	nPDLLHandle				SDK句柄
	 prePointOperation		预置点操作信息
	 nTimeout				超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzPrePointOperation( int nPDLLHandle, Ptz_Prepoint_Operation_Info_t prePointOperation, int nTimeout );

	/** 云台扩展命令.
	 IN	nPDLLHandle			SDK句柄
	 IN	ptzExtCmd			扩展命令信息 
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_PtzExtendCommand( int nPDLLHandle, Ptz_Extend_Command_Info_t ptzExtCmd, int nTimeout );
	
	/** 报警动作输出
	cameraId		摄像头ID
	bOpen			打开标志：true-打开, false-关闭
	nCmd			控制命令。状态控制。1=开启，0=关闭     模式控制：0=关闭  1=自动 2=手动 3常闭模式下关闭，4常闭模式手动
	nType			控制类型。1状态控制 2 模式控制
	nTimeout		超时时长，单位毫秒
	@return						异步顺序码,用于事件回调时,与应答事件匹配
	*/
	static public native  int DPSDK_PtzCtrlOut(int nPDLLHandle,Ptz_Ctrl_Out_Info_t pCtrlOut, int nTimeout );
	
	/** 查询巡航线（巡航线信息通过接口DPSDK_GetCruiseInfo获取）
	IN    nPDLLHandle		SDK句柄
	IN    cameraId			摄像头ID
	IN    clientId			设0时表示不区分用户，设-1使用CMS登录后的client id
	IN    nTimeout			超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_QueryCruise(int nPDLLHandle, byte szCameraId[], int clientId, int nTimeout );
	
	/**
	@brief 获取当前摄像头的巡航线数量
	IN 	cameraId：		通道Id
	OUT	nCruiseCount	巡航线数量
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_GetCruiseCount(int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nCruiseCount);
	
	/** 
	@brief 获取当前摄像头每条巡航线中的预置点数量
	IN 	cameraId：		通道Id
	INOUT	PrePointCountlist		返回每条巡航线包含的预置点个数（需根据巡航线个数（iCruiseCount）分配空间）
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_GetPrePointCountList(int nPDLLHandle, byte szCameraId[], Cruise_Prepoint_Count_List_t PrePointCountlist);
	
	/** 
	@brief 获取当前摄像头所有的巡航线
	IN		cameraId：				通道Id
	IN		PrePointCountlist		每条巡航线包含的预置点个数（需根据巡航线个数（iCruiseCount）分配空间）
	OUT	cruiseList				巡航线列表(需要先调用接口DPSDK_GetCruiseCount获取巡航线个数,DPSDK_GetPrePointCountList获取每条巡航线中的预置点个数)
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_GetCruiseInfo(int nPDLLHandle, byte szCameraId[], Cruise_Prepoint_Count_List_t PrePointCountlist, CruiseList_t cruiseList);

	/**
	@brief 增加巡航线
	IN		cameraId：		通道Id
	IN		struCruiseInfo：单个巡航线信息
	    nTimeout		超时时长，单位毫秒
	@return	 函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_AddCruise(int nPDLLHandle, byte szCameraId[], CruiseInfo_t struCruiseInfo, int nTimeout );

	/**
	@brief			删除巡航线
	IN		cameraId		通道Id
	IN		id：			巡航线id
	    nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_DeleteCruise(int nPDLLHandle, byte szCameraId[],int id, int nTimeout);

	/** 开始或停止巡航
	IN     nPDLLHandle		SDK句柄
	IN	    cameraId		通道Id
	    cruiseId		巡航线ID
	    cmd				0开始，1停止
	    nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native  int DPSDK_PtzCruiseOperation(int nPDLLHandle, byte szCameraId[], int cruiseId, int cmd, int nTimeout);
	
	/** 获取设备列表信息长度.
	 nPDLLHandle			SDK句柄
	 OUT	nDevListLen       	设备列表信息长度
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_GetDeviceListLen(int nPDLLHandle, Return_Value_Info_t nDevListLen, int nTimeout);

	/** 获取设备列表信息.
	 nPDLLHandle			SDK句柄
	 OUT	szDevList       	设备列表xml字符串
	 nDevListLen       	设备列表信息长度
	 nTimeout			超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	     1、需要2次函数调用才能最终获取设备列表信息。
	     2、先使用DPSDK_GetDeviceListLen，获取nDevListLen的值。
		 3、获取到nDevListLen的值之后，创建一个长度为（nDevListLen+1）的char数组，并作为参数szDevList传入。
	*/
	static public native int DPSDK_GetDeviceListStr(int nPDLLHandle, byte szDevList[], int nDevListLen, int nTimeout);

	/** 获取多个设备详细信息长度.
	 nPDLLHandle		SDK句柄
	 szDevicesId     需要查询的多个设备ID字符串数组,大小应为byte[nDevicesCount][DpsdkCoreDefine.DPSDK_CORE_DEV_ID_LEN]
	 nDevicesCount   设备ID字符串数组的长度
	 OUT   nDevInfoLen     需要查询的多个设备详细信息组成的xml字符串的长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_GetDevicesInfoLen(int nPDLLHandle, byte szDevicesId[][], int nDevicesCount, Return_Value_Info_t nDevInfoLen, int nTimeout);

	/** 获取多个设备详细信息.
	 nPDLLHandle		SDK句柄
	 OUT	szDevicesInfo   需要查询的多个设备详细信息组成的xml字符串
	 nDevInfoLen     设备详细信息组成的xml字符串的长度
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	     1、需要2次函数调用才能最终获取设备列表信息。
	     2、先使用DPSDK_GetDevicesInfoLen获取nDevicesCount的值。
		 3、获取到nDevicesCount的值之后，创建一个长度为（nDevicesCount+1）的char数组，并作为参数szDevicesInfo传入。
	*/
	static public native int DPSDK_GetDevicesInfoStr(int nPDLLHandle, byte szDevicesInfo[], int nDevInfoLen, int nTimeout);
	
	/** 语音对讲失败后参数回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetDPSDKTalkParamCallback(int nPDLLHandle, fDPSDKTalkParamCallback fun);
	
	/** 根据CemeraId获取通道状态，不支持NVR通道状态获取
	IN    nPDLLHandle     SDK句柄
	IN    szCameraId      通道ID
	OUT   nStatus         状态值，参见dpsdk_dev_status_e
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_GetChannelStatus(int nPDLLHanle,  byte szCameraId[], Return_Value_Info_t nStatus);

	/** 根据设备ID获取设备类型
	IN    nPDLLHandle     SDK句柄
	IN    szDevId		   设备ID
	OUT   nDeviceType     设备类型
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_GetDeviceTypeByDevId(int nPDLLHandle, byte szDevId[], Return_Value_Info_t nDeviceType);
	
	/** 视频报警主机布撤防旁路.
	 IN    nPDLLHandle     SDK句柄
	 IN    szDeviceId      设备id
	 IN    channelId       通道号，-1表示对设备操作
	 IN    controlType     操作类型, 参考dpsdk_AlarmhostOperator_e
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  视频报警主机类型为:1101
	*/
	static public native int DPSDK_ControlVideoAlarmHost(int nPDLLHandle, byte szDeviceId[], int channelId, int controlType, int nTimeout);
	
	/** 网络报警主机布撤防旁路.
	 IN    nPDLLHandle     SDK句柄
	 IN    szId			设备id/通道id
	 IN    opttype			设备/通道操作,参考dpsdk_netalarmhost_operator_e
	 IN    controlType     操作类型, 参考dpsdk_AlarmhostOperator_e
	 IN    iStart			开始时间,默认为0
	 IN    iEnd			结束时间,默认为0
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  网络报警主机类型为:601
	*/
	static public native int DPSDK_ControlNetAlarmHostCmd(int nPDLLHandle, byte szId[], int opttype, int controlType, long iStart, long iEnd, int nTimeout);

	/** 设置远程设备抓图回调
	*/
	static public native int DPSDK_SetDPSDKRemoteDeviceSnapCallback(int nPDLLHandle, fDPSDKRemoteDeviceSnapCallback fun);

	/**通过Json协议发送命令给cms.
	IN    nPDLLHandle		SDK句柄
	IN    szJson			Json字符串,
	形如:"{ \"DevID\": \"1000000\",\"CameraId\":\"1000000$1$0$1\", \"DevChannel\": 1,\"PicNum\": 1,\"CourtTime\": \"2014-09-17 16:00:00\"}"
	OUT   szJsonResult		平台返回的Json字符串,
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_SendCammandToCMSByJson(int nPDLLHandle, byte[] szJson, byte[] szJsonResult, int nTimeout);

	/**通过Json协议发送命令给DMS.
	IN    nPDLLHandle		SDK句柄
	IN    szJson			Json字符串,
	形如:"{ \"DevID\": \"1000000\",\"CameraId\":\"1000000$1$0$1\", \"DevChannel\": 1,\"PicNum\": 1,\"CourtTime\": \"2014-09-17 16:00:00\"}"
	IN    szCamId			Json字符串,
	OUT   szJsonResult		平台返回的Json字符串,
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_SendCammandToDMSByJson(int nPDLLHandle, byte[] szJson, byte[] szCamId, byte[] szJsonResult, int nTimeout);

	/** 获取用户详情.以后有关用户的信息都在DPSDK_UserInfo_t里面增加
	 nPDLLHandle		SDK句柄
	 OUT	userInfo		用户信息结构体
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native int DPSDK_GetUserInfo(int nPDLLHandle, DPSDK_UserInfo_t userInfo, int nTimeout);

	/** 根据设备ID获取报警输入通道信息
	 nPDLLHandle		SDK句柄
	 OUT	alarmInChnlInfo	用户信息结构体
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	 1、alarmInChnlInfo需要在外面创建好
	 2、alarmInChnlInfo的个数与DPSDK_GetDGroupInfo返回时有报警主机设备id和报警输入通道个数
	*/
	static public native int DPSDK_GetAlarmInChannelInfo(int nPDLLHandle, Get_AlarmInChannel_Info_t alarmInChnlInfo);

	/** 查询网络报警主机包含的通道个数
	 nPDLLHandle		SDK句柄
	 szDeviceId		设备id
	 OUT	nChannelcount	通道个数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_QueryNetAlarmHostChannelCount(int nPDLLHandle, byte[] szDeviceId, Return_Value_Info_t nChannelcount, int nTimeout);
	/** 查询网络报警主机状态
	 nPDLLHandle		SDK句柄
	 szDeviceId		设备id
	 nChannelcount	通道个数
	 OUT	defenceStatus	通道信息结构体
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、defenceStatus需要在外面创建好，根据通道个数new
	*/
	static public native int DPSDK_QueryNetAlarmHostStatus(int nPDLLHandle, byte[] szDeviceId, int nChannelcount, dpsdk_AHostDefenceStatus_t[] defenceStatus, int nTimeout);

	/** 设置视频报警主机布撤防/旁路状态回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetVideoAlarmHostStatusCallback(int nPDLLHandle, fDPSDKVideoAlarmHostStatusCallback fun);

	/** 设置网络报警主机布撤防/旁路状态回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetNetAlarmHostStatusCallback(int nPDLLHandle, fDPSDKNetAlarmHostStatusCallback fun);
	
	/** XZ吉隆边防项目定制，设置业翔广播通道状态上报.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetBroadcastReportStatusCallback(int nPDLLHandle, fDPSDKBroadcastReportStatusCallback fun);

	/** 报警订阅设置-报警运营平台.
	 IN    nPDLLHandle     SDK句柄
	 IN    userParam       用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_PhoneSubscribeAlarm(int nPDLLHandle, dpsdk_phone_subscribe_alarm_t userParam, Return_Value_Info_t nIsSubscribe, int nTimeout);

	/** 报警布控.
	 IN	nPDLLHandle		SDK句柄
	 IN	sourceInfo		报警方案 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	 1、布控时需要明白不同的报警类型对应不同的参数
	*/
	static public native int DPSDK_EnableAlarm(int nPDLLHandle, Alarm_Enable_Info_t sourceInfo, int nTimeout);

	/** 报警布控(针对某个部门下的所有设备)
	 IN	nPDLLHandle		SDK句柄
	 IN	sourceInfo		报警方案 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	 1、布控时需要明白不同的报警类型对应不同的参数
	*/
	static public native int DPSDK_EnableAlarmByDepartment(int nPDLLHandle, Alarm_Enable_By_Dep_Info_t sourceInfo, int nTimeout);

	/** 报警撤控.
	 IN	nPDLLHandle		SDK句柄
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_DisableAlarm(int nPDLLHandle, int nTimeout);
	
	/** 查询报警个数.
	 IN	nPDLLHandle		SDK句柄
	 IN    pQuery          查询信息
	 OUT	nCount			报警个数返回 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_QueryAlarmCount(int nPDLLHandle, Alarm_Query_Info_t pQuery, Return_Value_Info_t nCount,int nTimeout);

	/** 查询报警信息.
	 IN	nPDLLHandle		SDK句柄
	 IN    pQuery          查询信息
	 		pInfo			报警信息 
	 IN	nFirstNum		从第几个开始获取 
	 IN	nQueryCount		获取多少个 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	 1、支持分批获取
	 2、此接口推荐和DPSDK_QueryAlarmCount一起使用
	*/
	static public native int  DPSDK_QueryAlarmInfo( int nPDLLHandle, Alarm_Query_Info_t pQuery, Alarm_Info_t pInfo, int nFirstNum, int nQueryCount, int nTimeout );
	
	/** 发送OSD信息.
	 IN	nPDLLHandle		SDK句柄
	  IN Send_OSDInfo_t     osd信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 nTimeout		超时时长，单位毫秒
	 @remark  
	*/
	static public native int DPSDK_SendOSDInfo(int nPDLLHandle, Send_OSDInfo_t pOpeInfo, int nTimeout );
	
	/** 摄像头码流叠加OSD信息
	IN    nPDLLHandle		SDK句柄
	IN    struOSDInfo		OSD信息结构体
	IN    nTimeout			超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native int DPSDK_SetOSDInfo(int nPDLLHandle, OSD_Info_t struOSDInfo, int nTimeout );
	
	/** 分享视频
	IN    nPDLLHandle		SDK句柄
	IN    videoInfoArray	分享的视频信息结构数组
	IN    userIdArray		分享对象用户ID数组
	IN    szMsg			附加的信息
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native int DPSDK_ShareVideo(int nPDLLHanle, ShareVideoInfo videoInfoArray[], int userIdArray[], byte[] szMsg, int nTimeout);
	
	/** 获取用户组织信息字符串
	 IN		nPDLLHandle     SDK句柄
	 OUT		szOrgInfo		用户组织信息
	@return	
	*/
	static public native int DPSDK_GetUserOrgInfo(int nPDLLHandle, GetUserOrgInfo userOrgInfo, int nTimeout);
	
	/** 设置Json协议回调.
	 nPDLLHandle		SDK句柄
	 fun				回调函数
	 @return  函数返回错误类型，参考{@link dpsdk_retval_e}	
	*/
	static public native int DPSDK_SetGeneralJsonTransportCallback(int nPDLLHandle, fDPSDKGeneralJsonTransportCallback fun);
	
	/**通过Json协议发送命令,返回结果通过DPSDK_SetGeneralJsonTransportCallback回调
	 IN    nPDLLHandle		SDK句柄
	 IN    szJson			Json字符串,
	 IN    mdltype			模块,参考dpsdk_mdl_type_e
	 IN    trantype		JSON 传输类型,参考generaljson_trantype_e
	 IN    nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考{@link dpsdk_retval_e}
	*/
	static public native int DPSDK_GeneralJsonTransport(int nPDLLHandle, byte[] szJson, int mdltype, int trantype, int nTimeout);
	
	/** 获取报警预案数量
	 IN    nPDLLHandle		SDK句柄
	 OUT   nCount			报警预案数量
	 nTimeout		超时时间
	 @return  函数返回错误类型，参考{@link dpsdk_retval_e}
	 @sample
		 Return_Value_Info_t nCount = new Return_Value_Info_t();
		 IDpsdkCore.DPSDK_GetAlarmSchemeCount(m_nPDLLHandle, nCount, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 int nAlarmCount = nCount.nReturnValue;
	*/
	static public native int DPSDK_GetAlarmSchemeCount(int nPDLLHandle, Return_Value_Info_t nCount, int nTimeout);


	/** 获取报警预案列表
	 IN		nPDLLHandle			SDK句柄
	 OUT		alarmSchemeList		报警预案
	 IN		nTimeout			超时时间
	 @return	函数返回错误类型，参考dpsdk_retval_e
	 @sample
		 Return_Value_Info_t nCount = new Return_Value_Info_t();
		 IDpsdkCore.DPSDK_GetAlarmSchemeCount(m_nPDLLHandle, nCount, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 int nAlarmCount = nCount.nReturnValue;
		 if (nAlarmCount > 0)
		 {
		 	AlarmSchemeInfo_t[] alarmSchemeList = new AlarmSchemeInfo_t[nAlarmCount];
		 	IDpsdkCore.DPSDK_GetAlarmSchemeList(m_nPDLLHandle, alarmSchemeList, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 	for (int i = 0; i < alarmSchemeList.length; ++i)
		 	{
		 		//do something
		 	}
		 }
	*/
	static public native int DPSDK_GetAlarmSchemeList(int nPDLLHandle, AlarmSchemeInfo_t[] alarmSchemeList, int nTimeout);

	/** 获取单个报警预案文件长度
	 IN    nPDLLHandle		SDK句柄
	 IN	nSchemeId		预案ID
	 OUT   nLen			预案文件数据长度
	 nTimeout		超时时间
	 @return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native int DPSDK_GetSchemeFileDataLen(int nPDLLHandle, int nSchemeId, Return_Value_Info_t nLen, int nTimeout);
	
	/** 获取单个报警预案
	 IN    nPDLLHandle		SDK句柄
	 IN	nSchemeId		预案ID
	 OUT   alarmSchemeFileInfo	预案信息
	 nTimeout		超时时间
	 @return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native int DPSDK_GetSchemeFile(int nPDLLHandle, int nSchemeId, AlarmSchemeFileInfo_t alarmSchemeFileInfo, int nTimeout);
	
	/** 保存报警预案
	 IN		nPDLLHandle			SDK句柄
	 IN		alarmScheme			报警预案
	 IN		nTimeout			超时时间
	 @return	函数返回错误类型，参考{@link dpsdk_retval_e}
	 	 @sample
		 Return_Value_Info_t nCount = new Return_Value_Info_t();
		 IDpsdkCore.DPSDK_GetAlarmSchemeCount(m_nPDLLHandle, nCount, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 int nAlarmCount = nCount.nReturnValue;
		 if (nAlarmCount > 0)
		 {
		 	AlarmSchemeInfo_t[] alarmSchemeList = new AlarmSchemeInfo_t[nAlarmCount];
		 	IDpsdkCore.DPSDK_GetAlarmSchemeList(m_nPDLLHandle, alarmSchemeList, dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 	for (int i = 0; i < alarmSchemeList.length; ++i)
		 	{
		 		alarmSchemeList[i].status = dpsdk_alarmScheme_status_e.ALARM_SCHEME_STATUS_OPEN;
		 		IDpsdkCore.DPSDK_SaveAlarmScheme(m_nPDLLHandle, alarmSchemeList[i], dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
		 	}
		 }
	*/
	static public native int DPSDK_SaveAlarmScheme(int nPDLLHandle, AlarmSchemeInfo_t alarmScheme, int nTimeout);
	
	/** 发送消息给scs服务
	 IN		nPDLLHandle			SDK句柄
	 IN		szGroupId			呼叫组ID
	 IN		szStrText			发送的文本内容
	 IN		nTimeout			超时时间
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @sample
		DPSDK_SendScsMsg(m_nPDLLHandle, "test".getBytes(), "test".getBytes(), dpsdk_constant_value.DPSDK_CORE_DEFAULT_TIMEOUT);
	*/
	static public native int DPSDK_SendScsMsg(int nPDLLHandle, byte[] szGroupId, byte[] szStrText, int nTimeout);
	
	
//////////////////////////////////////////////////////////////////////////
// 电视墙业务接口 开始

	/** 查询电视墙列表个数.
	 IN	nPDLLHandle		SDK句柄
	 OUT	nCount			返回个数
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetTvWallListCount(int nPDLLHandle, Return_Value_Info_t nCount,int nTimeout );
	
	/** 获取电视墙列表信息.
	 IN	nPDLLHandle		SDK句柄
	 INOUT	pInfo			电视墙列表信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetTvWallList( int nPDLLHandle, TvWall_List_Info_t pTvWallListInfo );
	
	/** 查询电视墙布局信息
	 IN	nPDLLHandle		SDK句柄
	 IN	nTvWallId		电视墙ID
	 OUT	nCount			返回个数
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetTvWallLayoutCount(int nPDLLHandle, int nTvWallId, Return_Value_Info_t nCount, int nTimeout );

	/** 获取电视墙布局信息.
	 IN	nPDLLHandle		SDK句柄
	 INOUT	pInfo			电视墙布局信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetTvWallLayout( int nPDLLHandle, TvWall_Layout_Info_t pTvWallLayoutInfo );
	
	/** 设置屏窗口视频源.
	 IN	nPDLLHandle		SDK句柄
	 IN	pWindowSourceInfo		电视墙屏窗口视频源信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_SetTvWallScreenWindowSource( int nPDLLHandle, Set_TvWall_Screen_Window_Source_t pWindowSourceInfo, int nTimeout );

	/** 关闭屏窗口视频源.
	 IN	nPDLLHandle		SDK句柄
	 IN	pCloseSourceInfo		电视墙屏窗口关闭视频源信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_CloseTvWallScreenWindowSource( int nPDLLHandle, TvWall_Screen_Close_Source_t pCloseSourceInfo, int nTimeout );
	
	/** 屏分割.
	 IN	nPDLLHandle		SDK句柄
	 IN	pSplitInfo		电视墙屏分割信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_SetTvWallScreenSplit( int nPDLLHandle, TvWall_Screen_Split_t pSplitInfo, int nTimeout );
	
	/** 屏开窗.
	 IN	nPDLLHandle		SDK句柄
	 INOUT	pOpenWindowInfo	电视墙屏开窗信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_TvWallScreenOpenWindow( int nPDLLHandle, TvWall_Screen_Open_Window_t pOpenWindowInfo, int nTimeout );
	
	/** 屏关窗.
	 IN	nPDLLHandle		SDK句柄
	 IN	pCloseWindowInfo电视墙屏关窗信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_TvWallScreenCloseWindow( int nPDLLHandle, TvWall_Screen_Close_Window_t pCloseWindowInfo, int nTimeout );
	
	/** 清空电视墙屏  
	 此接口存在问题，当电视墙绑定多个解码器id时，只能清除一个解码器id的屏，请使用接口DPSDK_ClearTvWallScreenByDecodeId
	 IN	nPDLLHandle		SDK句柄
	 IN	nTvWallId		电视墙ID
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_ClearTvWallScreen( int nPDLLHandle, int nTvWallId, int nTimeout );
	
	/** 根据解码器ID清空电视墙屏
	 IN	nPDLLHandle		SDK句柄
	 IN	nTvWallId		电视墙ID
	 IN	szDecodeId		解码器ID
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_ClearTvWallScreenByDecodeId(int nPDLLHandle, int nTvWallId, byte[] szDecodeId, int nTimeout);
	
	/** 获取电视墙任务列表总数   
	 <IN>		nPDLLHandle		SDK句柄
	 <IN>		nTvWallId		电视墙ID
	 <OUT>		nCount			返回个数 
	 <IN>		nTimeout		超时时长，单位毫秒
	 @return 函数返回错误类型，参考dpsdk_retval_e.
	*/
	static public native int DPSDK_GetTvWallTaskListCount( int nPDLLHandle, int nTVWallId, Return_Value_Info_t nCount, int nTimeout );

	/** 获取电视墙任务列表信息   
	 <IN>		nPDLLHandle		SDK句柄
	 <IN>		nTvWallId		电视墙ID
	 <OUT>		pTVWallTaskInfoList		电视墙任务列表信息 
	 <IN>		nTimeout		超时时长，单位毫秒
	 @return 函数返回错误类型，参考dpsdk_retval_e.
	*/
	static public native int DPSDK_GetTvWallTaskList( int nPDLLHandle, int nTVWallId, TvWall_Task_Info_List_t pTVWallTaskInfoList, int nTimeout );

	/** 获取电视墙任务信息长度    
	 <IN>		nPDLLHandle		SDK句柄
	 <IN>		nTvWallId		电视墙ID
	 <IN>		nTaskId		    电视墙任务ID
	 <OUT>		pTaskInfoLen	电视墙任务信息长度 
	 <IN>		nTimeout		超时时长，单位毫秒
	 @return 函数返回错误类型，参考dpsdk_retval_e.
	*/
	static public native int DPSDK_GetTvWallTaskInfoLen( int nPDLLHandle, int nTVWallId, int nTaskId, Return_Value_Info_t pTaskInfoLen, int nTimeout );

	/** 获取电视墙任务信息，需要先调用DPSDK_GetTvWallTaskInfoLen()   
	 <IN>		nPDLLHandle		SDK句柄
	 <OUT>		szTaskInfoBuf	电视墙任务信息 
	 <IN>		nTaskInfoLen	电视墙任务信息长度 
	 @return 函数返回错误类型，参考dpsdk_retval_e.
	*/
	static public native int DPSDK_GetTvWallTaskInfoStr( int nPDLLHandle, byte szTaskInfoBuf[], int nTaskInfoLen );

	/** 电视墙任务上墙    
	 <IN>		nPDLLHandle		SDK句柄
	 <IN>		nTvWallId		电视墙ID
	 <IN>		nTaskId		    电视墙任务ID
	 <IN>		nTimeout		超时时长，单位毫秒
	 @return 函数返回错误类型，参考dpsdk_retval_e.
	*/
	static public native int DPSDK_MapTaskToTvWall( int nPDLLHandle, int nTVWallId, int nTaskId, int nTimeout );
	
	/** 清单屏
	IN	nPDLLHandle		SDK句柄
	IN	nTvWallId		电视墙ID
	IN	szDecodeId		解码器ID
	nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark  
	*/
	static public native int DPSDK_ClearSingleScreen(int nPDLLHandle, int nTvWallId, int nScreenId, int nTimeout );
															   
// 电视墙业务接口 结束
	
	/** 根据设备ID获取报警输出通道个数
	 nPDLLHandle		SDK句柄
	 OUT	pstruUserInfo	用户信息结构体
	 @return  函数返回错误类型，参考dpsdk_retval_e
	*/
	//static public native int  DPSDK_GetAlarmOutChannelCount( int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nCount);

	/** 根据设备ID获取报警输出通道信息
	 nPDLLHandle		SDK句柄
	 OUT	pstruUserInfo	用户信息结构体
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	 1、pAlarmOutChannelnfo需要在外面创建好
	 2、pAlarmOutChannelnfo的个数与DPSDK_GetAlarmOutChannelCount返回时有报警主机设备id和报警输入通道个数
	*/
	//static public native int  DPSDK_GetAlarmOutChannelInfo( int nPDLLHandle, Get_AlarmOutChannel_Info_t pstruUserInfo);
	
	/** 报警动作输出.
	 IN	nPDLLHandle		SDK句柄
	 IN	pPtzCtrlOutInfo 报警动作输出信息 
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	//static public native int  DPSDK_PtzCtrlOut( int nPDLLHandle, Ptz_Ctrl_Out_Info_t pPtzCtrlOutInfo, int nTimeout );
	
	/** 门控制.
	 IN	nPDLLHandle		SDK句柄
	 IN	pRequest		请求信息
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetDoorCmd(int nPDLLHandle, SetDoorCmd_Request_t pRequest, int nTimeout );
	
	/** 设置门禁开关状态上报回调
	nPDLLHandle		SDK句柄
	fun				回调函数
	pUser			用户参数
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark		
	*/
	static public native int DPSDK_SetPecDoorStatusCallback(int nPDLLHandle, fDPSDKPecDoorStarusCallBack pFun);
	
	/** 获取通道信息.
	 nPDLLHandle		SDK句柄
	 INOUT	pGetInfo		子通道信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、暂时只支持门禁通道的获取，其他类型后续扩展
	 */
	//static public native int DPSDK_GetChannelBaseInfo( int nPDLLHandle, byte szCameraId[], ChannelBase_Info_t pGetInfo );
	
	/** 获取绑定视频资源.
	 nPDLLHandle		SDK句柄
	 OUT	pResponce		绑定视频资源XML
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native int DPSDK_QueryLinkResource( int nPDLLHandle, Return_Value_Info_t nLen, int nTimeout);

	/** 获取绑定视频资源.
	 nPDLLHandle		SDK句柄
	 OUT	pResponce		绑定视频资源XML
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native int DPSDK_GetLinkResource( int nPDLLHandle, GetLinkResource_Responce_t pResponce );
	
	/** 获取设备下编码器通道的信息.
	 nPDLLHandle		SDK句柄
	 INOUT	pGetInfo		子通道信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、pEncChannelnfo需要在外面创建好
	 2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
	 */
	//static public native  int DPSDK_GetPosChannelCount( int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nPosChannelCount);
	
	/** 获取设备下编码器通道的信息.
	 nPDLLHandle		SDK句柄
	 INOUT	pGetInfo		子通道信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、pEncChannelnfo需要在外面创建好
	 2、pEncChannelnfo的大小与DPSDK_GetDGroupInfo中通道个数返回需要一致
	 */
	//static public native  int DPSDK_GetPosChannelInfo( int nPDLLHandle, Get_PosChannel_Info_t getInfo );
	
	/** 保存操作员日志
	 nPDLLHandle		 SDK句柄
	 szCameraId		 通道编号
	 IN    optTime			 操作时间
	 IN    optType	         操作类型
	 IN    optDesc		     操作描述
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int  DPSDK_SaveOptLog(  int nPDLLHandle, byte  szCameraId[], long optTime, int optType, byte optDesc[]);
	
	/** 刻录控制.
	 nPDLLHandle		 SDK句柄
	 pControlDevBurnerRequest	请求信息
	 IN    nTimeout		 超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int  DPSDK_ControlDevBurner(  int nPDLLHandle, Control_Dev_Burner_Request_t pControlDevBurnerRequest, int nTimeout  );
	
	/** 刻录片头设置.
	 nPDLLHandle		 SDK句柄
	 pInfoHeader		 片头信息
	 pAttrName		 审讯表单属性名
	 IN    nTimeout		 超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int  DPSDK_SetDevBurnerHeader(  int nPDLLHandle, DevBurnerInfoHeader_t pInfoHeader, TrialFormAttrName_t pAttrName, int nTimeout  );
	
	/** 获取设备磁盘信息数量.
	 nPDLLHandle		 SDK句柄
	 szDeviceId		 设备ID
	 IN    nInfoCount		 磁盘信息数量
	 IN    nSequence		 异步顺序码
	 IN    nTimeout		 超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remarkReturn_Value_Info_t nCount
	 */
	static public native  int  DPSDK_GetDeviceDiskInfoCount(  int nPDLLHandle, byte szDeviceId[], Return_Value_Info_t nInfoCount, Return_Value_Info_t nSequence, int nTimeout  );
	
	/** 获取设备磁盘信息.
	 nPDLLHandle		 SDK句柄
	 nSequence		 异步顺序码，DPSDK_GetDeviceDiskInfoCount返回
	 controlDevBurnerRequest		 请求信息
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 */
	static public native  int  DPSDK_GetDeviceDiskInfo(  int nPDLLHandle, int nSequence, Device_Disk_Info_t pDiskInfo  );
	
	/** 发送报警到服务.
	IN	nPDLLHandle		SDK句柄
	IN    Client_Alarm_Info_t		报警信息
	nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark  
	*/
	static public native  int  DPSDK_SendAlarmToServer( int nPDLLHandle, Client_Alarm_Info_t pAlarmInfo, int nTimeout );
	
	/**获取最新GPS信息XML串长度.
	IN		nPDLLHandle				SDK句柄
	OUT		nGpsXMLLen				GPS XML的长度
	IN		nTimeout				超时时间
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native  int  DPSDK_AskForLastGpsStatusXMLStrCount(int nPDLLHandle, Return_Value_Info_t nGpsXMLLen, int nTimeout);

	/**获取最新GPS信息.
	IN		nPDLLHandle				SDK句柄
	OUT		LastGpsIStatus			GPS XML数据
	IN		nGpsXMLLen				GPS XML的长度,DPSDK_AskForLastGpsStatusXMLStrCount的输出参数值
	IN		nTimeout				超时时间
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark 
	*/
	static public native  int  DPSDK_AskForLastGpsStatusXMLStr(int nPDLLHandle, byte LastGpsIStatus[], int nGpsXMLLen);
	
	/** 设置卡口过车信息（不带图片）上报回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  设置回调函数以后再订阅DPSDK_SubscribeBayCarInfo
	*/
	static public native int DPSDK_SetDPSDKGetBayCarInfoCallbackEx(int nPDLLHandle, fDPSDKGetBayCarInfoCallbackEx BayCarInfoCallback);
	
	/** 订阅卡口过车信息上报.
	 IN    nPDLLHandle   SDK句柄
	 IN    pGetInfo      订阅卡口过车信息上报请求信息，参考Subscribe_Bay_Car_Info_t
                              注意: 如果订阅通道数nChnlCount为0(pEncChannelnfo要置NULL)，
                              表示所有通道的过车数据都上报.
							  只有订阅以后picSDK的回调才能起作用。DPSDK_SetDPSDKGetBayCarInfoCallback才能回调给上层
	 IN    nTimeout        超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SubscribeBayCarInfo(int nPDLLHandle, Subscribe_Bay_Car_Info_t pGetInfo,int nTimeout);
	
	/** 查询统计总数.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道ID
	 OUT	nQuerySeq		码流请求序号,可作为后续操作标识 
	 OUT	totalCount		统计总数
	 nStartTime		开始时间 
	 IN    nEndTime		结束时间				
	 IN    nGranularity	查询粒度，0:分钟,1:小时,2:日,3:周,4:月,5:季,6:年;
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native int DPSDK_QueryPersonCount(int nPDLLHandle, byte szCameraId[], Return_Value_Info_t nQuerySeq, Return_Value_Info_t totalCount, int nStartTime,  int nEndTime,  int nGranularity, int nTimeout);
	
	/** 分页查询统计结果.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道ID
	 nQuerySeq		码流请求序号,可作为后续操作标识 
	 nIndex			此次查询的开始值
	 IN    nCount			此次查询的数量		
	 OUT	pPersonInfo		详细的人数统计信息，new一个nCount大小的数组
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native int DPSDK_QueryPersonCountBypage(int nPDLLHandle, byte szCameraId[], int nQuerySeq, int nIndex, int nCount, Person_Count_Info_t pPersonInfo[], int nTimeout);
	
	/** 结束查询.
	 nPDLLHandle		SDK句柄
	 szCameraId		通道ID
	 nQuerySeq		码流请求序号,可作为后续操作标识 			
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native int DPSDK_StopQueryPersonCount(int nPDLLHandle, byte szCameraId[], int nQuerySeq, int nTimeout);
	
	/** 获取FTP信息.
	 nPDLLHandle		SDK句柄
	 OUT	szFtpInfo		存放FTP信息的缓冲区
	 IN	iSize			存放FTP信息的缓冲区大小
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  返回的FTP信息格式如： ftp://user:passwd@172.7.8.9
	*/
	static public native  int DPSDK_GetFTPInfo( int nPDLLHandle, byte szFtpInfo[], int iSize );
	
	/** 设置违章报警回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 IN    pUser           用户参数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	*/
	static public native int DPSDK_SetDPSDKTrafficAlarmCallback(int nPDLLHandle, fDPSDKTrafficAlarmCallback TrafficAlarmCallback);
	
	/** 订阅区间测速上报.
	 IN    nPDLLHandle     SDK句柄
	 IN    nSubscribeFlag  订阅标记:1订阅；0；取消订阅
	 IN    nTimeout        超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SubscribeAreaSpeedDetectInfo(int nPDLLHandle, int nSubscribeFlag, int nTimeout);
	
	/** 设置区间测速上报回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetDPSDKGetAreaSpeedDetectCallback(int nPDLLHandle, fDPSDKGetAreaSpeedDetectCallback fun);
	
	/** 获取实况码流，转换为指定码流保存到文件，只支持大华设备
	 nPDLLHandle		SDK句柄
	 OUT	nRealSeq		码流请求序号,可作为后续操作标识 
	 pGetInfo		码流请求信息
	 IN    scType          码流转换的目的类型
	 IN    szFilePath		文件保存路径
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark		
	*/
	static public native  int DPSDK_SaveRealStream( int nPDLLHandle, Return_Value_Info_t nRealSeq, Get_RealStream_Info_t getInfo, int scType, byte szFilePath[], int nTimeout );
	
	/** 按文件请求录像流，转换为指定码流保存到文件，只支持大华设备
	 IN	nPDLLHandle		SDK句柄
	 OUT	nPlaybackSeq	回放请求序号,作为后续操作标识  
	 IN	pRecordInfo		录像信息 
	 IN    scType          码流转换的目的类型
	 IN    szFilePath		文件保存路径
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native  int DPSDK_SaveRecordStreamByFile( int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_File_Info_t recordInfo, int scType, byte szFilePath[], int nTimeout );

	/** 按时间请求录像流，转换为指定码流保存到文件，只支持大华设备
	 IN	nPDLLHandle		SDK句柄
	 OUT	nPlaybackSeq	回放请求序号,作为后续操作标识  
	 IN	pRecordInfo		录像信息 
	 IN    scType          码流转换的目的类型
	 IN    szFilePath		文件保存路径
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SaveRecordStreamByTime( int nPDLLHandle, Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int scType, byte szFilePath[], int nTimeout);
	
	/** 按时间请求录像流，转换为指定码流保存到文件，只支持大华设备
	IN		nPDLLHandle		SDK句柄
	OUT	nPlaybackSeq	回放请求序号,作为后续操作标识  
	IN		pRecordInfo		录像信息 
		scType          码流转换的目的类型
		nStreamType		码流类型
		pFilePath		文件保存路径
		nTimeout		超时时长，单位毫秒
	@return  函数返回错误类型，参考dpsdk_retval_e
	@remark  
	*/
	static public native int DPSDK_SaveRecordStreamByStreamType(int nPDLLHandle,Return_Value_Info_t nPlaybackSeq, Get_RecordStream_Time_Info_t recordInfo, int scType,int nStreamType, byte szFilePath[], int nTimeout);
	
	/** 设置录像转码完成回调.
	 IN    nPDLLHandle     SDK句柄
	 IN    fun             回调函数
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_SetDPSDKSaveRecordFinishedCallback(int nPDLLHandle, fSaveRecordFinishedCallback fun);
	
	/** 开启平台录像
	 IN    nPDLLHandle     SDK句柄
	 IN    szCameraId      通道ID
	 IN    streamType      码流类型，1主码流，2辅码流
	 IN    nTimeout        超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_StartPlatformReocrd(int nPDLLHandle, byte szCameraId[], int streamType, int nTimeout);
	
	/** 停止平台录像
	 IN    nPDLLHandle     SDK句柄
	 IN    szCameraId      通道ID
	 IN    nTimeout        超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	*/
	static public native int DPSDK_StopPlatformReocrd(int nPDLLHandle, byte szCameraId[], int nTimeout);
	
	/** 手动启动设备录像
	 IN	nPDLLHandle		SDK句柄
	 IN	szDevId			设备ID
	 szSN			互联编码
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_StartDeviceRecord(int nPDLLHandle, byte szDevId[], byte szSN[], int nTimeout);

	/** 手动停止设备录像
	 IN	nPDLLHandle		SDK句柄
	 IN	szDevId			设备ID
	 szSN			互联编码
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_StopDeviceRecord(int nPDLLHandle, byte szDevId[], byte szSN[], int nTimeout);
	
	/** 清除设备报警消息
	 IN	nPDLLHandle		SDK句柄
	 IN	szCameraId		通道ID
	 nAlarmType		报警类型
	 nTimeout		超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int DPSDK_ClearDevAlarm(int nPDLLHandle, byte szCameraId[], int nAlarmType, int nTimeout);
	
	/** 获取回放视频的URL路径.
	 IN	nPDLLHandle						SDK句柄
	 IN	pRecordStreamUrlInfo 			查询录像Url信息 
	 nTimeout						超时时长，单位毫秒
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark  
	*/
	static public native int  DPSDK_GetPlaybackByTimeUrl( int nPDLLHandle, Get_RecordStreamUrl_Time_Info_t pRecordStreamUrlInfo, int nTimeout );
	
	/** 解码设备密码.
	 nPDLLHandle		SDK句柄
	 szEnDevPwd		加密设备密码
	 OUT	szDevPwd       	设备密码缓存,需要外部创建缓存
	 @return  函数返回错误类型，参考dpsdk_retval_e
	 @remark
	 1、szEnDevPwd需要在外面创建好
	*/
	static public native int DPSDK_DecodeDevPwd( int nPDLLHandle, byte szEnDevPwd[], byte szDevPwd[] );
}



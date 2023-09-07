package com.dh.DpsdkCore;

/** 报警类型
	@param     DPSDK_CORE_ALARM_TYPE_Unknown					= 0;		未知
	@param     DPSDK_CORE_ALARM_TYPE_VIDEO_LOST					= 1;		视频丢失
	@param     DPSDK_CORE_ALARM_TYPE_EXTERNAL_ALARM				= 2;		外部报警
	@param     DPSDK_CORE_ALARM_TYPE_MOTION_DETECT				= 3;		移动侦测
	@param     DPSDK_CORE_ALARM_TYPE_VIDEO_SHELTER				= 4;		视频遮挡
	@param     DPSDK_CORE_ALARM_TYPE_DISK_FULL					= 5;		硬盘满
	@param     DPSDK_CORE_ALARM_TYPE_DISK_FAULT					= 6;		硬盘故障
	@param     DPSDK_CORE_ALARM_TYPE_FIBER						= 7;		光纤报警
	@param     DPSDK_CORE_ALARM_TYPE_GPS						= 8;		GPS信息
	@param     DPSDK_CORE_ALARM_TYPE_3G							= 9;		3G
	@param     DPSDK_CORE_ALARM_TYPE_STATUS_RECORD				= 10;		设备录像状态
	@param     DPSDK_CORE_ALARM_TYPE_STATUS_DEVNAME				= 11;		设备名
	@param     DPSDK_CORE_ALARM_TYPE_STATUS_DISKINFO			= 12;		硬盘信息
	@param     DPSDK_CORE_ALARM_TYPE_IPC_OFF					= 13;		前端IPC断线

	@param     DPSDK_CORE_ALARM_DOOR_BEGIN				     	= 40;		门禁设备报警起始
	@param     DPSDK_CORE_ALARM_FORCE_CARD_OPENDOOR		 		= 41;		胁迫刷卡开门
	@param     DPSDK_CORE_ALARM_VALID_PASSWORD_OPENDOOR	 		= 42;		合法密码开门
	@param     DPSDK_CORE_ALARM_INVALID_PASSWORD_OPENDOOR	 	= 43;		非法密码开门
	@param     DPSDK_CORE_ALARM_FORCE_PASSWORD_OPENDOOR	 		= 44;		胁迫密码开门
	@param     DPSDK_CORE_ALARM_VALID_FINGERPRINT_OPENDOOR	 	= 45;		合法指纹开门
	@param     DPSDK_CORE_ALARM_INVALID_FINGERPRINT_OPENDOOR	= 46;		非法指纹开门
	@param     DPSDK_CORE_ALARM_FORCE_FINGERPRINT_OPENDOOR	 	= 47;		胁迫指纹开门

	@param     DPSDK_CORE_ALARM_TYPE_VALID_CARD_READ		 	= 51;		合法刷卡/开门
	@param     DPSDK_CORE_ALARM_TYPE_INVALID_CARD_READ		 	= 52;		非法刷卡/开门
	@param     DPSDK_CORE_ALARM_TYPE_DOOR_MAGNETIC_ERROR	 	= 53;		门磁报警
	@param     DPSDK_CORE_ALARM_TYPE_DOOR_BREAK   			 	= 54;		破门报警和开门超时报警
	@param     DPSDK_CORE_ALARM_TYPE_DOOR_ABNORMAL_CLOSED	 	= 55;		门非正常关闭
	@param     DPSDK_CORE_ALARM_TYPE_DOOR_NORMAL_CLOSED	 		= 56;		门正常关闭
	@param     DPSDK_CORE_ALARM_TYPE_DOOR_OPEN				 	= 57;		门打开

	@param     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_BEG			= 60;
	@param     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_END			= 70;

	@param     DPSDK_CORE_ALARM_TYPE_ALARM_CONTROL_ALERT		= 81;		报警主机报警
	@param     DPSDK_CORE_ALARM_TYPE_FIRE_ALARM					= 82;		火警
	@param     DPSDK_CORE_ALARM_TYPE_ZONE_DISABLED				= 83;		防区失效
	@param     DPSDK_CORE_ALARM_TYPE_BATTERY_EMPTY				= 84;		电池没电

	@param     DPSDK_CORE_ALARM_FILESYSTEM				    	= 100;		文件系统
	@param     DPSDK_CORE_ALARM_RAID_FAULT						= 101;		raid故障
	@param     DPSDK_CORE_ALARM_RECORDCHANNELFUNCTION_ABNORMAL 	= 102;		录像通道功能异常
	@param     DPSDK_CORE_SVR_HARDDISK_STATUS	= 103;						硬盘状态
	@param     DPSDK_CORE_ALARM_RECORD_REPAIR	= 104;						录像补全 -P3.0

	@param     DPSDK_CORE_ALARM_MOTOR_BEGIN						= 200;
	@param     DPSDK_CORE_ALARM_OVERSPEED_OCCURE				= 201; 		超速报警产生
	@param     DPSDK_CORE_ALARM_OVERSPEED_DISAPPEAR  			= 202;		超速报警消失
	@param     DPSDK_CORE_ALARM_DRIVEROUT_DRIVERALLOW			= 203;		驶出行区
	@param     DPSDK_CORE_ALARM_DRIVERIN_DRIVERALLOW			= 204;		驶入行区
	@param     DPSDK_CORE_ALARM_DRIVEROUT_FORBIDDRIVE			= 205;		驶出禁入区
	@param     DPSDK_CORE_ALARM_DRIVERIN_FORBIDDRIVE			= 206;		驶入禁入区
	@param     DPSDK_CORE_ALARM_DRIVEROUT_LOADGOODS				= 207;		驶出装货区
	@param     DPSDK_CORE_ALARM_DRIVERIN_LOADGOODS				= 208;		驶入装货区
	@param     DPSDK_CORE_ALARM_DRIVEROUT_UNLOADGOODS			= 209;		驶出卸货区
	@param     DPSDK_CORE_ALARM_DRIVERIN_UNLOADGOODS			= 210;		驶入卸货区
	@param     DPSDK_CORE_ALARM_CAR_OVER_LOAD					= 211;		超载
	@param     DPSDK_CORE_ALARM_SPEED_SOON_ZERO					= 212;		急刹车
	@param     DPSDK_CORE_ALARM_3GFLOW							= 213;		3G流量
	@param     DPSDK_CORE_ALARM_AAC_POWEROFF					= 214;		ACC断电报警
	@param     DPSDK_CORE_ALARM_SPEEDLIMIT_LOWERSPEED			= 215;		限速报警 LowerSpeed
	@param     DPSDK_CORE_ALARM_SPEEDLIMIT_UPPERSPEED			= 216;		限速报警 UpperSpeed 
	@param     DPSDK_CORE_ALARM_VEHICLEINFOUPLOAD_CHECKIN		= 217;		车载自定义信息上传 CheckIn
	@param     DPSDK_CORE_ALARM_VEHICLEINFOUPLOAD_CHECKOUT		= 218;		车载自定义信息上传 CheckOut
	@param     DPSDK_CORE_ALARM_MOTOR_END						= 299;

	@param     DPSDK_CORE_ALARM_IVS_ALARM_BEGIN					= 300;		智能设备报警类型在dhnetsdk.h基础上+300（DMS服务中添加）
	@param     DPSDK_CORE_ALARM_IVS_ALARM						= 301;		智能设备报警
	@param     DPSDK_CORE_ALARM_CROSSLINEDETECTION				= 302;		警戒线事件
	@param     DPSDK_CORE_ALARM_CROSSREGIONDETECTION 			= 303;		警戒区事件
	@param     DPSDK_CORE_ALARM_PASTEDETECTION					= 304;		贴条事件
	@param     DPSDK_CORE_ALARM_LEFTDETECTION					= 305;		物品遗留事件
	@param     DPSDK_CORE_ALARM_STAYDETECTION					= 306;		停留事件
	@param     DPSDK_CORE_ALARM_WANDERDETECTION					= 307;		徘徊事件
	@param     DPSDK_CORE_ALARM_PRESERVATION					= 308;		物品保全事件
	@param     DPSDK_CORE_ALARM_MOVEDETECTION					= 309;		移动事件
	@param     DPSDK_CORE_ALARM_TAILDETECTION					= 310;		尾随事件
	@param     DPSDK_CORE_ALARM_RIOTERDETECTION					= 311;		聚众事件
	@param     DPSDK_CORE_ALARM_FIREDETECTION					= 312;		火警事件
	@param     DPSDK_CORE_ALARM_SMOKEDETECTION					= 313;		烟雾报警事件
	@param     DPSDK_CORE_ALARM_FIGHTDETECTION					= 314;		斗殴事件
	@param     DPSDK_CORE_ALARM_FLOWSTAT						= 315;		流量统计事件
	@param     DPSDK_CORE_ALARM_NUMBERSTAT						= 316;		数量统计事件
	@param     DPSDK_CORE_ALARM_CAMERACOVERDDETECTION			= 317;		摄像头覆盖事件
	@param     DPSDK_CORE_ALARM_CAMERAMOVEDDETECTION			= 318;		摄像头移动事件
	@param     DPSDK_CORE_ALARM_VIDEOABNORMALDETECTION			= 319;		视频异常事件
	@param     DPSDK_CORE_ALARM_VIDEOBADDETECTION				= 320;		视频损坏事件
	@param     DPSDK_CORE_ALARM_TRAFFICCONTROL					= 321;		交通管制事件
	@param     DPSDK_CORE_ALARM_TRAFFICACCIDENT					= 322;		交通事故事件
	@param     DPSDK_CORE_ALARM_TRAFFICJUNCTION					= 323;		交通路口事件
	@param     DPSDK_CORE_ALARM_TRAFFICGATE						= 324;		交通卡口事件
	@param     DPSDK_CORE_ALARM_TRAFFICSNAPSHOT					= 325;		交通抓拍事件
	@param     DPSDK_CORE_ALARM_FACEDETECT						= 326;		RenLian检测事件 
	@param     DPSDK_CORE_ALARM_TRAFFICJAM						= 327;		交通拥堵事件

	@param     DPSDK_CORE_ALARM_TRAFFIC_RUNREDLIGHT				= 0x00000100 + 300;	  	交通违章-闯红灯事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_OVERLINE				= 0x00000101 + 300;	 	交通违章-压车道线事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_RETROGRADE				= 0x00000102 + 300;	  	交通违章-逆行事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_TURNLEFT				= 0x00000103 + 300;	  	交通违章-违章左转
	@param     DPSDK_CORE_ALARM_TRAFFIC_TURNRIGHT			    = 0x00000104 + 300;	  	交通违章-违章右转
	@param     DPSDK_CORE_ALARM_TRAFFIC_UTURN					= 0x00000105 + 300;	  	交通违章-违章掉头
	@param     DPSDK_CORE_ALARM_TRAFFIC_OVERSPEED				= 0x00000106 + 300;	  	交通违章-超速
	@param     DPSDK_CORE_ALARM_TRAFFIC_UNDERSPEED				= 0x00000107 + 300;	  	交通违章-低速
	@param     DPSDK_CORE_ALARM_TRAFFIC_PARKING					= 0x00000108 + 300;	  	交通违章-违章停车
	@param     DPSDK_CORE_ALARM_TRAFFIC_WRONGROUTE				= 0x00000109 + 300;	  	交通违章-不按车道行驶
	@param     DPSDK_CORE_ALARM_TRAFFIC_CROSSLANE				= 0x0000010A + 300;	  	交通违章-违章变道
	@param     DPSDK_CORE_ALARM_TRAFFIC_OVERYELLOWLINE			= 0x0000010B + 300;	  	交通违章-压黄线
	@param     DPSDK_CORE_ALARM_TRAFFIC_DRIVINGONSHOULDER		= 0x0000010C + 300;	  	交通违章-路肩行驶事件  
	@param     DPSDK_CORE_ALARM_TRAFFIC_YELLOWPLATEINLANE		= 0x0000010E + 300;	  	交通违章-黄牌车占道事件
	@param     DPSDK_CORE_ALARM_CROSSFENCEDETECTION				= 0x0000011F + 300;	  	翻越围栏事件
	@param     DPSDK_CORE_ALARM_ELECTROSPARKDETECTION			= 0X00000110 + 300;	  	电火花事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_NOPASSING				= 0x00000111 + 300;	  	交通违章-禁止通行事件
	@param     DPSDK_CORE_ALARM_ABNORMALRUNDETECTION			= 0x00000112 + 300;	  	异常奔跑事件
	@param     DPSDK_CORE_ALARM_RETROGRADEDETECTION				= 0x00000113 + 300;	  	人员逆行事件
	@param     DPSDK_CORE_ALARM_INREGIONDETECTION				= 0x00000114 + 300;	  	区域内检测事件
	@param     DPSDK_CORE_ALARM_TAKENAWAYDETECTION				= 0x00000115 + 300;	  	物品搬移事件
	@param     DPSDK_CORE_ALARM_PARKINGDETECTION				= 0x00000116 + 300;	  	非法停车事件
	@param     DPSDK_CORE_ALARM_FACERECOGNITION					= 0x00000117 + 300;	  	RenLian识别事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_MANUALSNAP				= 0x00000118 + 300;	  	交通手动抓拍事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_FLOWSTATE				= 0x00000119 + 300;	  	交通流量统计事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_STAY					= 0x0000011A + 300;	  	交通滞留事件
	@param     DPSDK_CORE_ALARM_TRAFFIC_VEHICLEINROUTE			= 0x0000011B + 300;	  	有车占道事件
	@param     DPSDK_CORE_ALARM_MOTIONDETECT					= 0x0000011C + 300;	  	视频移动侦测事件
	@param     DPSDK_CORE_ALARM_LOCALALARM						= 0x0000011D + 300;	  	外部报警事件
	@param     DPSDK_CORE_ALARM_PRISONERRISEDETECTION			= 0X0000011E + 300;	  	看守所囚犯起身事件
	@param     DPSDK_CORE_ALARM_IVS_B_ALARM_END					= 0X0000011E + 301;	  	以上报警都为IVS_B服务的报警类型，与SDK配合
	@param     DPSDK_CORE_ALARM_VIDEODIAGNOSIS					= 0X00000120 + 300;	  	视频诊断结果事件
	@param     DPSDK_CORE_ALARM_IVS_V_ALARM				        = 0X00000120 + 300;	  
	@param     DPSDK_CORE_ALARM_IVS_ALARM_END					= 1000;			 	  	智能设备报警类型的范围为300-1000
	@param     DPSDK_CORE_ALARM_OSD								= 1001;				  	osd信息
	@param     DPSDK_CORE_ALARM_CROSS_INFO						= 1002;				  	十字路口

	@param     DPSDK_CORE_ALARM_CLIENT_ALARM_BEGIN				= 1100;				  	客户端平台报警开始
	@param     DPSDK_CORE_ALARM_CLIENT_DERELICTION				= 1101;				  	遗留检测[交通事件-抛洒物]
	@param     DPSDK_CORE_ALARM_CLIENT_RETROGRADATION			= 1102;				  	逆行 [交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_OVERSPEED				= 1103;				  	超速  [交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_LACK_ALARM				= 1104;				  	欠速  [交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_FLUX_COUNT				= 1105;				  	流量统计[交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_PARKING					= 1106;				 	 停车检测[交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_PASSERBY					= 1107;				  	行人检测[交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_JAM						= 1108;				  	拥堵检测[交通事件]
	@param     DPSDK_CORE_ALARM_CLIENT_AREA_INBREAK				= 1109;				  	特殊区域入侵
	@param     DPSDK_CORE_ALARM_CLIENT_ALARM_END				= 1200;				  	客户端平台报警结束
*/

// 报警类型
public class dpsdk_alarm_type_e
{
	public static final int     DPSDK_CORE_ALARM_TYPE_Unknown					= 0;				     // 未知
	public static final int     DPSDK_CORE_ALARM_TYPE_VIDEO_LOST				= 1;				     // 视频丢失
	public static final int     DPSDK_CORE_ALARM_TYPE_EXTERNAL_ALARM			= 2;					 // 外部报警
	public static final int     DPSDK_CORE_ALARM_TYPE_MOTION_DETECT				= 3;					 // 移动侦测
	public static final int     DPSDK_CORE_ALARM_TYPE_VIDEO_SHELTER				= 4;					 // 视频遮挡
	public static final int     DPSDK_CORE_ALARM_TYPE_DISK_FULL					= 5;					 // 硬盘满
	public static final int     DPSDK_CORE_ALARM_TYPE_DISK_FAULT				= 6;					 // 硬盘故障
	public static final int     DPSDK_CORE_ALARM_TYPE_FIBER						= 7;					 // 光纤报警
	public static final int     DPSDK_CORE_ALARM_TYPE_GPS						= 8;					 // GPS信息
	public static final int     DPSDK_CORE_ALARM_TYPE_3G						= 9;					 // 3G
	public static final int     DPSDK_CORE_ALARM_TYPE_STATUS_RECORD				= 10;					 // 设备录像状态
	public static final int     DPSDK_CORE_ALARM_TYPE_STATUS_DEVNAME			= 11;					 // 设备名
	public static final int     DPSDK_CORE_ALARM_TYPE_STATUS_DISKINFO			= 12;					 // 硬盘信息
	public static final int     DPSDK_CORE_ALARM_TYPE_IPC_OFF					= 13;					 // 前端IPC断线
	public static final int     DPSDK_CORE_ALARM_TYPE_DEV_DISCONNECT			= 16;					 // 设备断线报警
	public static final int     DPSDK_CORE_ALARM_POWER_INTERRUPT				= 17;				// 市电中断报警 
	public static final int     DPSDK_CORE_ALARM_POWER_ENABLED					= 18;				// 市电启用报警 
	public static final int     DPSDK_CORE_ALARM_INFRARED_DETECT				= 19;				// 红外探测报警 
	public static final int     DPSDK_CORE_ALARM_GAS_OVER_SECTION				= 20;				// 燃气浓度超过上限 
	public static final int     DPSDK_CORE_ALARM_FLOW_OVER_SECTION				= 21;				// 瞬时流量超过上限 
	public static final int     DPSDK_CORE_ALARM_TEMPERATURE_OVER_SECTION		= 22;				// 温度超过上限 
	public static final int     DPSDK_CORE_ALARM_TEMPERATURE_UNDER_SECTION		= 23;				// 温度低于下限 
	public static final int     DPSDK_CORE_ALARM_PRESSURE_OVER_SECTION			= 24;				// 压力超过上限 
	public static final int     DPSDK_CORE_ALARM_PRESSURE_UNDER_SECTION			= 25;				// 压力低于下限

	public static final int     DPSDK_CORE_ALARM_STATIC_DETECTION				= 26;				// 静态检测
	public static final int     DPSDK_CORE_ALARM_REGULAR						= 27;				// 定时报警
	public static final int     DPSDK_CORE_ALARM_REMOTE_EXTERNAL_ALARM			= 28;				// 远程外部报警
	public static final int     DPSDK_CORE_ALARM_BUTTON_EXTERNAL_ALARM			= 29;				// 报警按钮外部报警
	public static final int     DPSDK_CORE_ALARM_POWER_INTERRUPT_EXTERNAL_ALARM = 30;				// 断电信号外部报警
	public static final int     DPSDK_CORE_ALARM_RECORD_LOSS 					= 31;		        // 录像丢失事件，指硬盘完好的情况下，发生误删等原因引起
	public static final int     DPSDK_CORE_ALARM_VIDEO_FRAME_LOSS				= 32;		        // 视频丢帧事件，比如网络不好或编码能力不足引起的丢帧
	public static final int     DPSDK_CORE_ALARM_RECORD_VOLUME_FAILURE          = 33;				   // 由保存录像的磁盘卷发生异常，引起的录像异常

	
	//门禁
	public static final int     DPSDK_CORE_ALARM_DOOR_BEGIN						= 40;		    	     // 门禁设备报警起始
	public static final int     DPSDK_CORE_ALARM_FORCE_CARD_OPENDOOR			= 41;				     // 胁迫刷卡开门
	public static final int     DPSDK_CORE_ALARM_VALID_PASSWORD_OPENDOOR		= 42;				     // 合法密码开门
	public static final int     DPSDK_CORE_ALARM_INVALID_PASSWORD_OPENDOOR		= 43;				     // 非法密码开门
	public static final int     DPSDK_CORE_ALARM_FORCE_PASSWORD_OPENDOOR		= 44;				     // 胁迫密码开门
	public static final int     DPSDK_CORE_ALARM_VALID_FINGERPRINT_OPENDOOR		= 45;			         // 合法指纹开门
	public static final int     DPSDK_CORE_ALARM_INVALID_FINGERPRINT_OPENDOOR	= 46;				 // 非法指纹开门
	public static final int     DPSDK_CORE_ALARM_FORCE_FINGERPRINT_OPENDOOR		= 47;				     // 胁迫指纹开门
	public static final int     DPSDK_CORE_ALARM_REMOTE_METHOD_OPENDOOR			= 48;				 // 远程开门:室内机开门/平台远程开门
	public static final int     DPSDK_CORE_ALARM_BUTTON_METHOD_OPENDOOR			= 49;				 // 按钮开门
	public static final int     DPSDK_CORE_ALARM_LOCKKEY_METHOD_OPENDOOR		= 50;				 // 钥匙开门

	public static final int     DPSDK_CORE_ALARM_TYPE_VALID_CARD_READ			= 51;				     // 合法刷卡/开门
	public static final int     DPSDK_CORE_ALARM_TYPE_INVALID_CARD_READ			= 52;					 // 非法刷卡/开门
	public static final int     DPSDK_CORE_ALARM_TYPE_DOOR_MAGNETIC_ERROR		= 53;					 // 门磁报警
	public static final int     DPSDK_CORE_ALARM_TYPE_DOOR_BREAK				= 54;					 // 破门报警和开门超时报警
	public static final int     DPSDK_CORE_ALARM_TYPE_DOOR_ABNORMAL_CLOSED		= 55;					 // 门非正常关闭
	public static final int     DPSDK_CORE_ALARM_TYPE_DOOR_NORMAL_CLOSED		= 56;					 // 门正常关闭
	public static final int     DPSDK_CORE_ALARM_TYPE_DOOR_OPEN					= 57;					 // 门打开
	public static final int     DPSDK_CORE_ALARM_TALK_REQUEST					= 59;				 // 门禁对讲请求报警
	
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_BEG			= 60;
	public static final int     DPSDK_CORE_ALARM_VALID_FACE_OPENDOOR			= 61;				 // 合法RenLian开门
	public static final int     DPSDK_CORE_ALARM_INVALID_FACE_OPENDOOR			= 62;				 // 非法RenLian开门
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_THIRD		= 63;				    // 超时等级三
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_FOUR		= 64;				    // 超时等级四
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_FIVE		= 65;				    // 超时等级五
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_ONE			= 66;				    // 超时等级一
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_SECOND		= 67;				    // 超时等级二

	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_FORCE				= 68;				 // 门强制打开 
	public static final int     DPSDK_CORE_ALARM_DOOR_OPEN_TIME_OUT_END			= 70;
	public static final int     DPSDK_CORE_ALARM_DOOR_INVALID_SECOND_OPEN		= 71;				 // 文教卫河东幼儿园需求，同一孩子绑定的第二张卡刷卡报警
	public static final int     DPSDK_CORE_ALARM_OPEN_DOOR_OVERTIME				= 72;				 // 开门超时
	public static final int     DPSDK_CORE_ALARM_LEGAL_CARD_ILLEGAL_TIME		= 73;				 // 合法卡非法时间
	public static final int     DPSDK_CORE_ALARM_LEGAL_CARD_ILLEGAL_AREA		= 74;				 // 合法卡非法区域
	public static final int     DPSDK_CORE_ALARM_SWING_CARD_RULE_OPEN			= 75;				 // 刷卡规则开门 = 75
	public static final int     DPSDK_CORE_ALARM_SWING_CARD_RULE_REQUEST_OPEN	= 76;				 // 刷卡规则请求开门 = 76
	public static final int     DPSDK_CORE_ALARM_DOOR_ENTRY						= 77;				 // 进门
	public static final int     DPSDK_CORE_ALARM_DOOR_EXIT						= 78;				 // 出门
	public static final int     DPSDK_CORE_ALARM_AB_DOOR_DOUBLE_OPEN			= 79;				 // AB门双

	//报警主机
	public static final int     ALARM_TYPE_ALARMHOST_BEGIN						= 80;
	public static final int     DPSDK_CORE_ALARM_TYPE_ALARM_CONTROL_ALERT		= 81;				     // 报警主机报警
	public static final int     DPSDK_CORE_ALARM_TYPE_FIRE_ALARM				= 82;					 // 火警
	public static final int     DPSDK_CORE_ALARM_TYPE_ZONE_DISABLED				= 83;					 // 防区失效
	public static final int     DPSDK_CORE_ALARM_TYPE_BATTERY_EMPTY				= 84;					 // 电池没电
	public static final int     ALARM_TYPE_AC_OFF								= 85;				 // 市电断开-设备报警
	//
	public static final int     ALARM_DALI_UP									= 86;				 // 上行报警 
	//ALARM_DALI_DOWN									= 87;				 // 下行报警
	public static final int     ALARM_TYPE_ALARMHOST_WIRE_SHOCK					= 87;				 // 报警主机电网触电报警，协议中87为下行报警，这里复用这个值作为报警主机电网触电报警

	public static final int     ALARM_PRISONERS_ESCAPE							= 88;				 // 犯人逃跑
	public static final int     ALARM_PRISONERS_RIOT							= 89;				 // 犯人暴狱
	public static final int     ALARM_TYPE_ALARMHOST_END						= 90;
	public static final int     DPSDK_CORE_ALARM_NATURAL_DISASTER				= 91;				 // 自然灾害
	public static final int     DPSDK_CORE_ALARM_ONEKEY_ALARM					= 92;				 // 一键报警
	public static final int     DPSDK_CORE_ALARM_EMERGENCY_BUTTON				= 93;				    // 紧急按钮
	public static final int     DPSDK_CORE_ALARM_BREAKIN_ALARM					= 94;				 // 两个防区同时入侵
	public static final int     DPSDK_CORE_ALARM_HOST_CHANNEL_OFFLINE			= 95;				 // 报警主机通道离线报警
	public static final int     DPSDK_CORE_ALARM_FLASH_LAMP_OFF					= 96;				 // 闪光灯离线报警

	public static final int     DPSDK_CORE_ALARM_DISABLE_ARM_ABNORMAL			= 97;				 // 撤防异常
	public static final int     DPSDK_CORE_ALARM_BYPASS_ABNORMAL				= 98;				 // 旁路异常
	public static final int     DPSDK_CORE_ALARM_ALARMHOST_EXTERNAL_ALARM		= 99;				 // 报警主机外部报警
	public static final int     DPSDK_CORE_ALARM_FILESYSTEM						= 100;					 // 文件系统
	public static final int     DPSDK_CORE_ALARM_RAID_FAULT						= 101;					 // raid故障
	public static final int     DPSDK_CORE_ALARM_RECORDCHANNELFUNCTION_ABNORMAL	= 102;			 // 录像通道功能异常
	public static final int     DPSDK_CORE_SVR_HARDDISK_STATUS					= 103;								 // 硬盘状态
	public static final int     DPSDK_CORE_ALARM_RECORD_REPAIR					= 104;								 // 录像补全 -P3.0

//begin电网报警类型
	public static final int     ELECTRIC_WIRE_SHOCK								= 109;				 // 电网触电
	public static final int     ELECTRIC_WIRE_INTERRUPT							= 110;				 // 电网断电
	public static final int     ELECTRIC_WIRE_SHORT_CIRCUIT						= 111;				 // 电网短路
	public static final int     ELECTRIC_WIRE_BREAKDOWN							= 112;				 // 电网故障
	public static final int     ELECTRIC_WIRE_VOLTAGE_LOW						= 113;				 // 电网电压低
	//end
	public static final int     ALARM_TYPE_RECORD_WRITE_FAIL					= 114;				 // 录像写入失败

	//电网报警类型新增begin add by hu_wenjuan
	public static final int     ELECTRIC_ALARM_BEGIN_EX							= 115; 
	public static final int     ELECTRIC_BREAK_CIRCUIT				 			= 115;				   // 电网开路 
	public static final int     ELECTRIC_SENSE_ALARM				 			= 116;				   // 电网传感报警 
	//重庆声光电项目
	public static final int     ALARM_GAS_CHROMA								= 120;				   // 气体浓度报警
	public static final int     ALARM_GAS_CHROMA_CHANGE							= 121;				   // 气体浓度变化异常报警
	public static final int     ALARM_GAS_DETECTOR_LEVEL1						= 122;				   // 气体探测器一级报警
	public static final int     ALARM_GAS_DETECTOR_LEVEL2						= 123;				   // 气体探测器二级报警
	//江苏油罐车车载设备电子铅封相关报警类型
	public static final int     ALARM_LOCK_OPEN									= 130;				   // 异常开锁
	public static final int     ALARM_LOCK_CLOSE								= 131;				   // 异常关锁
	public static final int     ALARM_LID_OPEN									= 132;				   // 异常开盖
	public static final int     ALARM_POWER_ON									= 133;				   // 异常上电
	public static final int     ELECTRIC_ALARM_END_EX				 			= 150;				 				         
	//电网报警类型新增end 
	public static final int     ALARM_FIRE_WARNING								= 151;				   // 火灾报警
	public static final int     ALARM_WATER_GAUGE								= 152;				 // 水尺报警
	public static final int     ALARM_SMOKE_DETECTION							= 153;				 // 烟感报警

	public static final int     ALARM_VTT_URGENCY								= 160;				   // VTT设备紧急按钮报警
	public static final int     ALARM_APPROVE_LEAVE								= 165;				 // 请销假批准外出报警
	public static final int     ALARM_DISAPPROVE_LEAVE							= 166; 				 // 请销假未批准外出报警
	public static final int     ALARM_NORMAL_BACK								= 167;				 // 请销假正常归队报警
	public static final int     ALARM_ABNORMAL_BACK								= 168;  			 // 请销假异常归队报警
	//
	public static final int     ALARM_LIGTHON_ALARM								= 169;          	 // 机房开灯
	public static final int     ALARM_HUMIDITY_ALARM							= 170;          	 // 湿度异常
	public static final int     ALARM_COMMUNICATION_ALARM						= 171;          	 // 通信状态告警
	public static final int     ALARM_DOOROPEN_ALARM							= 172;          	 // 机房门开
	public static final int     ALARM_WATEROUT_ALARM							= 173;          	 // 水浸告警  
	public static final int     ALARM_THEFT_ALARM								= 174;          	 // 防盗告警
	public static final int     ALARM_THALPOSISWARNING_ALARM					= 175;          	 // 温感告警
	public static final int     ALARM_THALPOSISFAULT_ALARM						= 176;          	 // 温感故障  
	public static final int     ALARM_SMOKEWARNING_ALARM						= 177;           	 // 烟感告警
	public static final int     ALARM_SMOKEFAULT_ALARM							= 178;          	 // 烟感故障
	public static final int     ALARM_BUZZERWARNING_ALARM						= 179;           	 // 讯响器告警
	public static final int     ALARM_INFRARED_ALARM							= 180;           	 // 红外告警
	public static final int     ALARM_HUMIDITYLOW_ALARM							= 181;           	 // 湿度过低
	public static final int     ALARM_RUNNINGSTATUS_ALARM						= 182;          	 // 运行状态提示
	public static final int     ALARM_TEMPERATURELOW_ALARM						= 183;          	 // 温度过低
	public static final int     ALARM_TEMPERATUREHIGH_ALARM						= 184;           	 // 温度过高
	public static final int     ALARM_FOG_ALARM									= 185;				   // 烟雾告警
	public static final int     ALARM_FIREINHOUSE_ALARM							= 186;           	 // 机房火警
	public static final int     ALARM_OUTDOORTHEFT_ALARM						= 187;           	 // 室外机被盗告警
	public static final int     ALARM_BUZZERFAULT_ALARM							= 188;           	 // 讯响器故障
	public static final int     ALARM_COMMUNICATEDSTATUS_ALARM					= 189;           	 // 通讯状况
	public static final int     ALARM_BUTTONFAULT_ALARM							= 190;           	 // 手动按钮故障
	public static final int     ALARM_BUTTONWARNING_ALARM						= 191;           	 // 手动按钮告警
	public static final int     ALARM_FIREDAMPERFAULT_ALARM						= 192;           	 // 防火阀故障
	public static final int     ALARM_FIREDAMPERWARNING_ALARM					= 193;           	 // 防火阀告警
	public static final int     ALARM_SMOKEDAMPERFAULT_ALARM					= 194;           	 // 排烟阀故障
	public static final int     ALARM_SMOKEDAMPERWARNING_ALARM					= 195;           	 // 排烟阀告警
	//
	public static final int     ALARM_VEHICLE_INVASION							= 196;				 // 车辆入侵报警
	public static final int     ALARM_CROSSLINE_DETECTION   					= 198;     			 // 绊线入侵报警 
	public static final int     ALARM_CROSSREGION_DETECTION 					= 199;     			 // 区域入侵报警

	//-M的相关报警在这里添加
	public static final int     DPSDK_CORE_ALARM_MOTOR_BEGIN					= 200;
	public static final int     DPSDK_CORE_ALARM_OVERSPEED_OCCURE				= 201; 			     // 超速报警产生
	public static final int     DPSDK_CORE_ALARM_OVERSPEED_DISAPPEAR  			= 202;				 // 超速报警消失
	public static final int     DPSDK_CORE_ALARM_DRIVEROUT_DRIVERALLOW			= 203;				 // 驶出行区
	public static final int     DPSDK_CORE_ALARM_DRIVERIN_DRIVERALLOW			= 204;				 // 驶入行区
	public static final int     DPSDK_CORE_ALARM_DRIVEROUT_FORBIDDRIVE			= 205;				 // 驶出禁入区
	public static final int     DPSDK_CORE_ALARM_DRIVERIN_FORBIDDRIVE			= 206;				 // 驶入禁入区
	public static final int     DPSDK_CORE_ALARM_DRIVEROUT_LOADGOODS			= 207;				 // 驶出装货区
	public static final int     DPSDK_CORE_ALARM_DRIVERIN_LOADGOODS				= 208;				 // 驶入装货区
	public static final int     DPSDK_CORE_ALARM_DRIVEROUT_UNLOADGOODS			= 209;				 // 驶出卸货区
	public static final int     DPSDK_CORE_ALARM_DRIVERIN_UNLOADGOODS			= 210;				 // 驶入卸货区
	public static final int     DPSDK_CORE_ALARM_CAR_OVER_LOAD					= 211;				 // 超载
	public static final int     DPSDK_CORE_ALARM_SPEED_SOON_ZERO				= 212;				 // 急刹车
	public static final int     DPSDK_CORE_ALARM_3GFLOW							= 213;				 // 3G流量
	public static final int     DPSDK_CORE_ALARM_AAC_POWEROFF					= 214;				 // ACC断电报警
	public static final int     DPSDK_CORE_ALARM_SPEEDLIMIT_LOWERSPEED			= 215;				 // 限速报警 LowerSpeed
	public static final int     DPSDK_CORE_ALARM_SPEEDLIMIT_UPPERSPEED			= 216;				 // 限速报警 UpperSpeed 
	public static final int     DPSDK_CORE_ALARM_VEHICLEINFOUPLOAD_CHECKIN		= 217;				 // 车载自定义信息上传 CheckIn
	public static final int     DPSDK_CORE_ALARM_VEHICLEINFOUPLOAD_CHECKOUT		= 218;				 // 车载自定义信息上传 CheckOut
	public static final int     ALARM_CAR_OPEN_DOOR 							= 219;				   // 开门报警
	public static final int     ALARM_URGENCY									= 220;				 // 紧急报警
	public static final int     ALARM_MOTOR_TURNOVER							= 221;				 // 侧翻报警
	public static final int     ALARM_MOTOR_COLLISION							= 222;				 // 撞车报警
	public static final int     ALARM_VEHICLE_CONFIRM							= 223;				 // 车辆上传信息事件(OSD下发 设备的回复通知)
	public static final int     ALARM_VEHICLE_LARGE_ANGLE						= 224;				 // 车载摄像头大角度扭转事件
	public static final int     ALARM_BATTERYLOWPOWER							= 225;				 // 电池电量低报警
	public static final int     ALARM_TEMPERATURE								= 226;				 // 温度过高报警     
	public static final int     ALARM_TALKING_INVITE							= 227;				 // 设备请求对方发起对讲事件 
	public static final int     ALARM_ALARM_EX2									= 228;				 // 扩展的本地报警事件 
	public static final int     ALARM_DEV_VOICE_EX     							= 229;    			 // 设备语音请求报警
	public static final int     ALARM_POWER_OFF_EX     							= 230;    			 // 断电报警
	public static final int     ALARM_ROUTE_OFFSET_EX  							= 231;    			 // 线路偏移报警
	public static final int     ALARM_TYRE_PRESSURE_EX 							= 232;    			 // 轮胎气压检测报警
	public static final int     ALARM_FATIGUE_DRIVING							= 233;				 // 疲劳驾驶报警
	public static final int     ALARM_DRIVER_CHECKIN							= 234;				 // 司机签入
	public static final int     ALARM_DRIVER_CHECHOUT							= 235;				 // 司机签出
	public static final int     DPSDK_CORE_ALARM_GAS_LOWLEVEL					= 236;				 // 油耗报警
	public static final int     ALARM_GAS_INFO									= 237;				 // 油耗信息
	public static final int     ALARM_GETIN_STATION								= 238;				 // 进站报警
	public static final int     ALARM_GETOUT_STATION							= 239;				 // 出站报警
	public static final int     ALARM_STATION_BEGIN_IN							= 240;				 // 始发站进站报警
	public static final int     ALARM_STATION_BEGIN_OUT							= 241;				 // 始发站出站报警
	public static final int     ALARM_STATION_END_IN							= 242;				 // 终点站进站报警
	public static final int     ALARM_STATION_END_OUT							= 243;				 // 终点站出站报警 <进出站类报警放在一起了>
	public static final int     ALARM_STAY_STATION_OVERTIME						= 244;				 // 停车超时报警
	public static final int     ALARM_RECOVER_RUNNING							= 245;				 // 恢复营运报警
	public static final int     ALARM_MEAL										= 246;				 // 吃饭报警
	public static final int     ALARM_BLOCK										= 247;				 // 路堵报警
	public static final int     ALARM_CALL										= 248;				 // 通话报警
	public static final int     ALARM_CAR_BREAKDOWN								= 249; 				 // 车坏报警
	public static final int     ALARM_STOP_RUNNING								= 250;				 // 停止营运报警
	public static final int     ALARM_ROBING									= 251; 				 // 盗抢报警
	public static final int     ALARM_DISPUTE									= 252; 				 // 纠纷报警
	public static final int     ALARM_ACCIDENT									= 253; 				 // 事故报警
	public static final int     ALARM_OVER_SPEED								= 254; 				 // 超速报警
	public static final int     ALARM_RENTAL									= 255; 				 // 包车报警
	public static final int     ALARM_MAINTENANCE								= 256; 				 // 车辆保养报警
	public static final int     ALARM_CLOSURE									= 257; 				 // 脱保停运报警
	public static final int     ALARM_OPEN_OR_CLOSE_DOOR						= 258;				 // 开关门报警
	public static final int     ALARM_ILLEGALIN_OVERSPEED						= 259;				 // 非法进入限速区报警
	public static final int     ALARM_ILLEGALOUT_OVERSPEED						= 260;				 // 非法驶出限速区报警
	public static final int     ALARM_ILLEGALIN_DRIVERALLOW						= 261;				 // 非法进入行使区报警
	public static final int     ALARM_ILLEGALOUT_DRIVERALLOW					= 262;				 // 非法驶出行使区报警
	public static final int     ALARM_ILLEGALIN_FORBIDDRIVE						= 263;				 // 非法进入禁入区报警
	public static final int     ALARM_ILLEGALOUT_FORBIDDRIVE					= 264;				 // 非法驶出禁入区报警
	public static final int     ALARM_ILLEGALIN_LOADGOODS						= 265;				 // 非法进入装货区报警
	public static final int     ALARM_ILLEGALOUT_LOADGOODS						= 266;				 // 非法驶出装货区报警
	public static final int     ALARM_ILLEGALIN_UNLOADGOODS						= 267;				 // 非法进入卸货区报警
	public static final int     ALARM_ILLEGALOUT_UNLOADGOODS					= 268;				 // 非法驶出卸货区报警
	public static final int     ALARM_ILLEGALIN_GETIN_STATION					= 269;				 // 非法进站报警
	public static final int     ALARM_ILLEGALIN_GETOUT_STATION					= 270;				 // 非法出站报警
	public static final int     ALARM_REONLINE_INFO								= 271;				 // 设备重新登录报警
	public static final int     ALARM_DETAINED									= 272;				   // 车辆滞留报警
	public static final int     ALARM_DELAY										= 273;				   // 托班报警，车辆班次拖延
	public static final int     ALARM_SHAP_TURNING			 					= 274;				 // 急转报警
	public static final int     ALARM_SHAP_SPEEDUP								= 275;				 // 急加速
	public static final int     ALARM_SHAP_SLOWDOWN								= 276;				 // 急减速
	public static final int     ALARM_STOP_OVERTIME								= 277;				 // 停车超时报警（辽宁油罐车项目）
	public static final int     ALARM_RUN_NONWOKINGTIME							= 278;				 // 非工作时间驾驶报警（辽宁油罐车项目）
	public static final int     ALARM_PASSENGER_CHECK_CARD       				= 279;				 // 乘客刷卡事件上报（黑龙江校车项目）

	//江苏油罐车设备添加
	public static final int     ALARM_ABNORMAL_PARK								= 280;
	public static final int     ALARM_BUS_LOW_OIL       						= 281;				 // 低油量报警事件
	public static final int     ALARM_BUS_CUR_OIL       						= 282;				 // 当前油耗情况事件

	public static final int     ALARM_SWIPE_OVERTIME							= 283;				   // 司机没有刷卡（泰国Usupply项目）
	public static final int     ALARM_DRIVING_WITHOUTCARD						= 284;				   // 司机无卡驾驶（泰国Usupply项目）
	public static final int     ALARM_NONPOWEROFF_CHECKOUT						= 285;				   // 司机签出没有关闭引擎（泰国Usupply项目)

	public static final int     ALARM_VEHICLE_TAMPER_ALARM						= 286;				 // 车载防拆报警

	public static final int     ALARM_CAR_UNMOVING								= 287;				 // 未清车报警
	public static final int     ALARM_CAR_OUTRULE_DRIVE							= 288;				 // 非规定时间行车报警
	public static final int     ALARM_SOS										= 289;				 // SOS求救报警
	public static final int     DPSDK_CORE_ALARM_MOTOR_END						= 299;

	//智能报警
	public static final int     DPSDK_CORE_ALARM_IVS_ALARM_BEGIN				= 300;				 // 智能设备报警类型在dhnetsdk.h基础上+300（DMS服务中添加）
	public static final int     DPSDK_CORE_ALARM_IVS_ALARM						= 301;				 // 智能设备报警
	public static final int     DPSDK_CORE_ALARM_CROSSLINEDETECTION				= 302;				 // 警戒线事件
	public static final int     DPSDK_CORE_ALARM_CROSSREGIONDETECTION 			= 303;				 // 警戒区事件
	public static final int     DPSDK_CORE_ALARM_PASTEDETECTION					= 304;				 // 贴条事件
	public static final int     DPSDK_CORE_ALARM_LEFTDETECTION					= 305;				 // 物品遗留事件
	public static final int     DPSDK_CORE_ALARM_STAYDETECTION					= 306;				 // 停留事件
	public static final int     DPSDK_CORE_ALARM_WANDERDETECTION				= 307;				 // 徘徊事件
	public static final int     DPSDK_CORE_ALARM_PRESERVATION					= 308;				 // 物品保全事件
	public static final int     DPSDK_CORE_ALARM_MOVEDETECTION					= 309;				 // 移动事件
	public static final int     DPSDK_CORE_ALARM_TAILDETECTION					= 310;				 // 尾随事件
	public static final int     DPSDK_CORE_ALARM_RIOTERDETECTION				= 311;				 // 聚众事件
	public static final int     DPSDK_CORE_ALARM_FIREDETECTION					= 312;				 // 火警事件
	public static final int     DPSDK_CORE_ALARM_SMOKEDETECTION					= 313;				 // 烟雾报警事件
	public static final int     DPSDK_CORE_ALARM_FIGHTDETECTION					= 314;				 // 斗殴事件
	public static final int     DPSDK_CORE_ALARM_FLOWSTAT						= 315;				 // 流量统计事件
	public static final int     DPSDK_CORE_ALARM_NUMBERSTAT						= 316;				 // 数量统计事件
	public static final int     DPSDK_CORE_ALARM_CAMERACOVERDDETECTION			= 317;				 // 摄像头覆盖事件
	public static final int     DPSDK_CORE_ALARM_CAMERAMOVEDDETECTION			= 318;				 // 摄像头移动事件
	public static final int     DPSDK_CORE_ALARM_VIDEOABNORMALDETECTION			= 319;				 // 视频异常事件
	public static final int     DPSDK_CORE_ALARM_VIDEOBADDETECTION				= 320;				 // 视频损坏事件
	public static final int     DPSDK_CORE_ALARM_TRAFFICCONTROL					= 321;				 // 交通管制事件
	public static final int     DPSDK_CORE_ALARM_TRAFFICACCIDENT				= 322;				 // 交通事故事件
	public static final int     DPSDK_CORE_ALARM_TRAFFICJUNCTION				= 323;				 // 交通路口事件
	public static final int     DPSDK_CORE_ALARM_TRAFFICGATE					= 324;				 // 交通卡口事件
	public static final int     DPSDK_CORE_ALARM_TRAFFICSNAPSHOT				= 325;				 // 交通抓拍事件
	public static final int     DPSDK_CORE_ALARM_FACEDETECT						= 326;				 // RenLian检测事件 
	public static final int     DPSDK_CORE_ALARM_TRAFFICJAM						= 327;				 // 交通拥堵事件
	public static final int     DPSDK_CORE_ALARM_STRANGE_FACEDETECT				= 328;				 // 陌生RenLian事件
	
	
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_RUNREDLIGHT			= 556;				 // 交通违章-闯红灯事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_OVERLINE				= 557;				 // 交通违章-压车道线事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_RETROGRADE				= 558;				 // 交通违章-逆行事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_TURNLEFT				= 559;				 // 交通违章-违章左转
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_TURNRIGHT			    = 560;				 // 交通违章-违章右转
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_UTURN					= 561;				 // 交通违章-违章掉头
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_OVERSPEED				= 562;				 // 交通违章-超速
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_UNDERSPEED				= 563;				 // 交通违章-低速
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_PARKING				= 564;				 // 交通违章-违章停车
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_WRONGROUTE				= 565;				 // 交通违章-不按车道行驶
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_CROSSLANE				= 566;				 // 交通违章-违章变道
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_OVERYELLOWLINE			= 567;				 // 交通违章-压黄线
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_DRIVINGONSHOULDER		= 568;				 // 交通违章-路肩行驶事件  
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_YELLOWPLATEINLANE		= 570;				 // 交通违章-黄牌车占道事件
	public static final int     DPSDK_CORE_ALARM_CROSSFENCEDETECTION			= 587;				 // 翻越围栏事件
	public static final int     DPSDK_CORE_ALARM_ELECTROSPARKDETECTION			= 572;				 // 电火花事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_NOPASSING				= 573;				 // 交通违章-禁止通行事件
	public static final int     DPSDK_CORE_ALARM_ABNORMALRUNDETECTION			= 574;				 // 异常奔跑事件
	public static final int     DPSDK_CORE_ALARM_RETROGRADEDETECTION			= 575;				 // 人员逆行事件
	public static final int     DPSDK_CORE_ALARM_INREGIONDETECTION				= 576;				 // 区域内检测事件
	public static final int     DPSDK_CORE_ALARM_TAKENAWAYDETECTION				= 577;				 // 物品搬移事件
	public static final int     DPSDK_CORE_ALARM_PARKINGDETECTION				= 578;				 // 非法停车事件
	public static final int     DPSDK_CORE_ALARM_FACERECOGNITION				= 579;				 // RenLian识别事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_MANUALSNAP				= 580;				 // 交通手动抓拍事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_FLOWSTATE				= 581;				 // 交通流量统计事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_STAY					= 582;				 // 交通滞留事件
	public static final int     DPSDK_CORE_ALARM_TRAFFIC_VEHICLEINROUTE			= 583;				 // 有车占道事件
	public static final int     DPSDK_CORE_ALARM_MOTIONDETECT					= 584;				 // 视频移动侦测事件
	public static final int     DPSDK_CORE_ALARM_LOCALALARM						= 585;				 // 外部报警事件
	public static final int     DPSDK_CORE_ALARM_PRISONERRISEDETECTION			= 586;				 // 看守所囚犯起身事件
	public static final int     DPSDK_CORE_ALARM_CORSSFENCE						= 587;				 // 穿越围栏
	public static final int     DPSDK_CORE_ALARM_IVS_B_ALARM_END				= 588;				 // 以上报警都为IVS_B服务的报警类型，与SDK配合
	public static final int     DPSDK_CORE_ALARM_VIDEODIAGNOSIS					= 588;				 // 视频诊断结果事件
	public static final int     DPSDK_CORE_ALARM_IVS_V_ALARM					= DPSDK_CORE_ALARM_VIDEODIAGNOSIS;	// 
	public static final int     DPSDK_CORE_ALARM_IVS_DENSITYDETECTION			= 589;    			 // 人员密集度检测事件 	

	public static final int     DPSDK_CORE_ALARM_IVS_QUEUEDETECTION				= 591;				 // 排队检测报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_VEHICLEINBUSROUTE	= 592;				 // 占用公交车道事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_BACKING			= 593;				 // 违章倒车事件
	//新增智能报警start
	public static final int     DPSDK_CORE_ALARM_IVS_AUDIO_ABNORMALDETECTION	= 594;				 // 声音异常检测
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_RUNYELLOWLIGHT		= 595;				 // 交通违章-闯黄灯事件
	public static final int     DPSDK_CORE_ALARM_CLIMB_UP						= 596;				 // 攀高检测 
	public static final int     DPSDK_CORE_ALARM_LEAVE_POST  					= 597;				 // 离岗检测
	//新增智能报警End
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PARKINGONYELLOWBOX	= 598;				 // 黄网格线抓拍事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PARKINGSPACEPARKING= 599;				 // 车位有车事件(对应 DEV_EVENT_TRAFFIC_PARKINGSPACEPARKING_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PARKINGSPACENOPARKING= 600;			 // 车位无车事件(对应 DEV_EVENT_TRAFFIC_PARKINGSPACENOPARKING_INFO)    
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PEDESTRAIN			= 601;				 // 交通行人事件(对应 DEV_EVENT_TRAFFIC_PEDESTRAIN_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_THROW				= 602;				 // 交通抛洒物品事件(对应 DEV_EVENT_TRAFFIC_THROW_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_IDLE				= 603;				 // 交通空闲事件
	public static final int     DPSDK_CORE_ALARM_VEHICLEACC						= 604;				 // 车载ACC断电报警事件 
	public static final int     DPSDK_CORE_ALARM_VEHICLE_TURNOVER				= 605;				 // 车辆侧翻报警事件
	public static final int     DPSDK_CORE_ALARM_VEHICLE_COLLISION				= 606;				 // 车辆撞车报警事件

	public static final int     DPSDK_CORE_ALARM_ALARM_VEHICLE_LARGE_ANGLE		= 607;				 // 车载摄像头大角度扭转事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PARKINGSPACEOVERLINE= 608;				 // 车位压线事件(对应 DEV_EVENT_TRAFFIC_PARKINGSPACEOVERLINE_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_MULTISCENESWITCH			= 609;				 // 多场景切换事件(对应 DEV_EVENT_IVS_MULTI_SCENE_SWICH_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_RESTRICTED_PLATE	= 610;				 // 受限车牌事件(对应 DEV_EVENT_TRAFFIC_RESTRICTED_PLATE)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_OVERSTOPLINE		= 611;				 // 压停止线事件(对应 DEV_EVENT_TRAFFIC_OVERSTOPLINE)

	
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_WITHOUT_SAFEBELT	= 612;				 // 交通未系安全带事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_DRIVER_SMOKING		= 613;				 // 驾驶员抽烟事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_DRIVER_CALLING		= 614;				 // 驾驶员打电话事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PEDESTRAINRUNREDLIGHT = 615;			 // 行人闯红灯事件(对应 DEV_EVENT_TRAFFIC_PEDESTRAINRUNREDLIGHT_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_PASSNOTINORDER		= 616;				 // 未按规定依次通行(对应DEV_EVENT_TRAFFIC_PASSNOTINORDER_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_OBJECT_DETECTION			= 621;				 // 物体特征检测事件

	public static final int     DPSDK_CORE_ALARM_ALARM_ANALOGALARM				= 636;				 // 模拟量报警通道的报警事件(对应DEV_EVENT_ALARM_ANALOGALRM_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_CROSSLINEDETECTION_EX		= 637;				 // 警戒线扩展事件
	public static final int     DPSDK_CORE_ALARM_ALARM_COMMON					= 638;				 // 普通录像
	public static final int     DPSDK_CORE_ALARM_VIDEOBLIND						= 639;				 // 视频遮挡事件(对应 DEV_EVENT_ALARM_VIDEOBLIND)
	public static final int     DPSDK_CORE_ALARM_ALARM_VIDEOLOSS				= 640;				 // 视频丢失事件
	public static final int     DPSDK_CORE_ALARM_IVS_GETOUTBEDDETECTION			= 641;				 // 看守所下床事件(对应 DEV_EVENT_GETOUTBED_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_PATROLDETECTION			= 642;				 // 巡逻检测事件(对应 DEV_EVENT_PATROL_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_ONDUTYDETECTION			= 643;				 // 站岗检测事件(对应 DEV_EVENT_ONDUTY_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_NOANSWERCALL				= 644;				 // 门口机呼叫未响应事件
	public static final int     DPSDK_CORE_ALARM_IVS_STORAGENOTEXIST			= 645;				 // 存储组不存在事件
	public static final int     DPSDK_CORE_ALARM_IVS_STORAGELOWSPACE			= 646;				 // 硬盘空间低报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_STORAGEFAILURE				= 647;				 // 存储错误事件
	public static final int     DPSDK_CORE_ALARM_IVS_PROFILEALARMTRANSMIT		= 648;				 // 报警传输事件
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOSTATIC				= 649;				 // 视频静态检测事件(对应 DEV_EVENT_ALARM_VIDEOSTATIC_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOTIMING				= 650;				 // 视频定时检测事件(对应 DEV_EVENT_ALARM_VIDEOTIMING_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_HEATMAP					= 651;				 // 热度图(对应 CFG_IVS_HEATMAP_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_CITIZENIDCARD				= 652;				 // 身份证信息读取事件(对应 DEV_EVENT_ALARM_CITIZENIDCARD_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_PICINFO					= 653;				 // 图片信息事件(对应 DEV_EVENT_ALARM_PIC_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_NETPLAYCHECK				= 654;				 // 上网登记事件(对应 DEV_EVENT_ALARM_NETPLAYCHECK_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_JAM_FORBID_INTO	= 655;				 // 车辆拥堵禁入事件(对应DEV_EVENT_ALARM_JAMFORBIDINTO_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_SNAPBYTIME					= 656;				 // 定时抓图事件
	public static final int     DPSDK_CORE_ALARM_IVS_PTZ_PRESET					= 657;				 // 云台转动到预置点事件
	public static final int     DPSDK_CORE_ALARM_IVS_RFID_INFO					= 658;				 // 红外线检测信息事件
	public static final int     DPSDK_CORE_ALARM_IVS_STANDUPDETECTION			= 659;				 // 人起立检测事件 
	public static final int     DPSDK_CORE_ALARM_IVS_QSYTRAFFICCARWEIGHT		= 660;				 // 交通卡口称重事件(对应 DEV_EVENT_QSYTRAFFICCARWEIGHT_INFO)
	public static final int     DPSDK_CORE_ALARM_IVS_SCENE_CHANGE				= 665;				 // 场景变更事件(对应 DEV_ALRAM_SCENECHANGE_INFO;CFG_VIDEOABNORMALDETECTION_INFO)

	public static final int     DPSDK_CORE_ALARM_IVS_NEAR_DISTANCE_DETECTION	= 672;				 // 近距离接触事件
	public static final int     DPSDK_CORE_ALARM_IVS_OBJECTSTRUCTLIZE_PERSON	= 673;				 // 行人特征检测事件
	public static final int     DPSDK_CORE_ALARM_IVS_OBJECTSTRUCTLIZE_NONMOTOR	= 674;				 // 非机动车特征检测事件
	public static final int     DPSDK_CORE_ALARM_IVS_TUMBLE_DETECTION		    = 675;				 // 倒地报警事件

	public static final int     DPSDK_CORE_ALARM_IVS_ALIEN_INVASION				= 677;				 // 外来人员入侵报警
	public static final int     DPSDK_CORE_ALARM_IVS_BLACKLIST					= 678;				 // 黑名单报警

	// 新增违章报警类型
	public static final int     DPSDK_CORE_ALARM_VEHICLE_INBUSROUTE				= 700;				 // 占用公交车道事件 41
	public static final int     DPSDK_CORE_ALARM_BACKING						= 701;				 // 违章倒车事件     42
	public static final int     DPSDK_CORE_ALARM_RUN_YELLOWLIGHT				= 702;				 // 闯黄灯事件       43
	public static final int     DPSDK_CORE_ALARM_PARKINGSPACE_PARKING			= 703;				 // 车位有车事件     44
	public static final int     DPSDK_CORE_ALARM_PARKINGSPACE_NOPARKING			= 704;				 // 车位无车事件     45
	public static final int     DPSDK_CORE_ALARM_COVERINGPLATE					= 705;
	public static final int     DPSDK_CORE_ALARM_PARKINGONYELLOWBOX				= 706;
	public static final int     DPSDK_CORE_ALARM_THROW							= 707;				 // 交通抛洒物事件	71
	public static final int     DPSDK_CORE_ALARM_PEDESTRAIN						= 708;				 // 交通行人事件		72

	public static final int     DPSDK_CORE_ALARM_IVS_LINKSD					    = 813;				 // 813:  球机轮训报警
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_TIREDPHYSIOLOGICAL = 819;				 // 生理疲劳驾驶事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_BUSSHARPTURN       = 820;				 // 车辆急转报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_TIREDLOWERHEAD     = 822;				 // 开车低头报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_DRIVERLOOKAROUND   = 823;				 // 开车左顾右盼事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_DRIVERLEAVEPOST    = 824;				 // 开车离岗事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_MAN_NUM_DETECTION  = 826;				 // 立体视觉区域内人数统计事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_DRIVERYAWN         = 828;				 // 开车打哈欠事件
	public static final int     DPSDK_CORE_ALARM_IVS_HUMANTRAIT					= 833;				 // 人体特征事件
	public static final int     DPSDK_CORE_ALARM_IVS_INSTALL_CARDREADER			= 844;				 // 安装读卡器事件
	public static final int     DPSDK_CORE_ALARM_IVS_XRAY_DETECTION				= 847;				 // X光检测事件 
	public static final int     DPSDK_CORE_ALARM_IVS_HIGHSPEED					= 855;				 // 车辆超速报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_CROWDDETECTION				= 856;				 // 人群密度检测事件
	public static final int     DPSDK_CORE_ALARM_IVS_TRAFFIC_WAITINGAREA		= 864;				 // 违章进入待行区事件						

	public static final int     DPSDK_CORE_ALARM_IVS_STEREO_FIGHTDETECTION		= 867;				 // ATM舱内打架事件
	public static final int     DPSDK_CORE_ALARM_IVS_STEREO_TAILDETECTION		= 868;				 // ATM舱内尾随事件
	public static final int     DPSDK_CORE_ALARM_IVS_STEREO_STEREOFALLDETECTION = 869;				 // ATM舱内跌倒事件
	public static final int     DPSDK_CORE_ALARM_IVS_STEREO_STAYDETECTION		= 870;				 // ATM舱内滞留事件
	public static final int     DPSDK_CORE_ALARM_IVS_BANNER_DETECTION			= 871;				 // 拉横幅事件
	public static final int     DPSDK_CORE_ALARM_IVS_ELEVATOR_ABNORMAL			= 873;				 // 电动扶梯运行异常事件
	public static final int     DPSDK_CORE_ALARM_IVS_NONMOTORDETECT				= 874;				 // 非机动车检测
	public static final int     DPSDK_CORE_ALARM_IVS_FIREWARNING				= 881;				 // 火警事件
	public static final int     DPSDK_CORE_ALARM_IVS_SHOPPRESENCE				= 882;				 // 商铺占道经营事件
	public static final int     DPSDK_CORE_ALARM_IVS_WASTEDUMPED				= 883;				 // 垃圾违章倾倒事件
	public static final int     DPSDK_CORE_ALARM_IVS_DISTANCE_DETECTION			= 886;				 // 距离异常事件
	public static final int     DPSDK_CORE_ALARM_IVS_FLOWBUSINESS				= 887;				 // 游摊小贩

	public static final int     DPSDK_CORE_ALARM_IVS_GARBAGE_EXPOSURE			= 890;				 // 暴露垃圾-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_HOLD_UMBRELLA				= 891;				 // 违规撑伞-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_DOOR_FRONT_DIRTY	 		= 892;				 // 门前脏乱-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_CITYPARKING_MOTOR			= 893;				 // 机动车违章停车-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_CITYPARKING_NOMOTOR		= 894;				 // 非机动车违章停车-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_DUSTBIN_OVER_FLOW 			= 895;				 // 垃圾桶满溢-智能城管报警
	public static final int     DPSDK_CORE_ALARM_IVS_LINKSD_CROSS_REGION		= 896;				 // NVR枪球联动报警

	public static final int     DPSDK_CORE_ALARM_IVS_ALARM_CAPTURPIC			= 897;				 // 报警抓图
	public static final int     DPSDK_CORE_ALARM_IVS_TIMING_CAPTURPIC			= 898;				 // 定时抓图
	public static final int     DPSDK_CORE_ALARM_IVS_CLIENT_CAPTURPIC			= 899;				 // 客户端抓图
	public static final int     DPSDK_CORE_ALARM_IVS_M_END						= 900;				 // _M3.0特殊的IVS报警结束

	public static final int     DPSDK_CORE_ALARM_IVS_ABNORMAL_FACEDETECT		= 901;				 // RenLian检测事件--智能报警，异常RenLian检测，放在区间300~1000内
	public static final int     DPSDK_CORE_ALARM_IVS_SIMILAR_FACEDETECT			= 902;				 // RenLian检测事件--相邻RenLian检测报警
	public static final int     DPSDK_CORE_ALARM_IVS_HIDENOSE_FACEDETECT		= 903;				 // 鼻子遮挡报警
	public static final int     DPSDK_CORE_ALARM_IVS_HIDEMOUTH_FACEDETECT		= 904;				 // 嘴部遮挡报警
	public static final int     DPSDK_CORE_ALARM_IVS_HIDEEYE_FACEDETECT			= 905;				 // 眼部遮挡报警

	public static final int     DPSDK_CORE_ALARM_DETECTIONAREA_PASTEDETECTION	= 920;				 // 检测区贴条检测
	public static final int     DPSDK_CORE_ALARM_KEYBOARDAREA_PASTEDETECTION	= 921;				 // 键盘区贴条检测
	public static final int     DPSDK_CORE_ALARM_SPIGOTAREA_PASTEDETECTION		= 922;				 // 插卡区贴条检测
	public static final int     DPSDK_CORE_ALARM_AUDIO_MUTATION_ALARM			= 923;				 // 声强突变报警

	public static final int     DPSDK_CORE_ALARM_AUDIO_DETECT_ALARM				= 924;				 // 音频检测报警
	public static final int     DPSDK_CORE_ALARM_AUDIO_ANOMALY_ALARM			= 925;				 // 音频异常报警
	public static final int     DPSDK_CORE_ALARM_TRAFFICJUNCTION_NON_MOTOR		= 926;				 // 非机动车报警
	public static final int     DPSDK_CORE_ALARM_CROSSREGION_ENTRY				= 927;				 // 进入区域
	public static final int     DPSDK_CORE_ALARM_CROSSREGION_EXIT				= 928;				 // 离开区域
	public static final int     DPSDK_CORE_ALARM_BLACKLIST_ALARM				= 929;				 // 黑名单报警
	public static final int     DPSDK_CORE_ALARM_STRANGER_ALARM					= 930;				 // 陌生人报警
	public static final int     DPSDK_CORE_ALARM_VIPCUSTOMER_ALARM				= 931;				 // VIP客户报警
	public static final int     DPSDK_CORE_ALARM_WHITELIST_ALARM				= 932;				 // 白名单报警
	public static final int     DPSDK_CORE_ALARM_EMPLOYEE_ALARM					= 933;				 // 员工库报警
	public static final int     DPSDK_CORE_ALARM_LEADER_ALARM					= 934;				 // 领导库报警

	// ---ALARM_VIDEOABNORMALDETECTION 报警子类型起始
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_SUBBEGIN		= 950;
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_LOST			= 950;				 // 视频异常事件:视频丢失
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_FREEZE		= 951;				 // 视频异常事件:视频冻结
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_SHELTER		= 952;				 // 视频异常事件:摄像头遮挡
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_MOTION		= 953;				 // 视频异常事件:摄像头移动
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_HIGHDARK		= 954;				 // 视频异常事件:过暗
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_HIGHBRIGHT	= 955;				 // 视频异常事件:过亮
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_COLORCAST	= 956;				 // 视频异常事件:图像偏色
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_NOISE		= 957;				 // 视频异常事件:噪声干扰
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_SCENE_CHANGE = 958;				 // 视频异常事件:场景变更
	public static final int     DPSDK_CORE_ALARM_IVS_VIDEOABNORMAL_SUBEND		= 960;
	public static final int     DPSDK_CORE_ALARM_IVS_BLACKLIST_FACE				= 961;				 // RenLian检测事件--黑名单告警
	public static final int     DPSDK_CORE_ALARM_CROSSLINEDETECTION_HUMAN		= 962;				 // 人穿越警戒线
	public static final int     DPSDK_CORE_ALARM_CROSSLINEDETECTION_VEHICLE		= 963;				 // 机动车穿越警戒线
	public static final int     DPSDK_CORE_ALARM_CROSSREGIONDETECTION_HUMAN		= 964;				 // 人穿越区域
	public static final int     DPSDK_CORE_ALARM_CROSSREGIONDETECTION_VEHICLE	= 965;				 // 机动车穿越区域
	public static final int     DPSDK_CORE_ALARM_IVS_HUMANTRAIT_WITH_SAFEHAT	= 966;				 // 带帽子报警
	public static final int     DPSDK_CORE_ALARM_IVS_HUMANTRAIT_WITHOUT_SAFEHAT	= 967;				 // 不带帽子报警
	public static final int     DPSDK_CORE_ALARM_TRAFFICJUNCTION_DISAPPEAR		= 968;				 // 交通路口事件：车辆离开
	public static final int     DPSDK_CORE_ALARM_CROSSLINEDETECTION_NonMotor	= 969;				 // 非机动车穿越警戒线
	public static final int     DPSDK_CORE_ALARM_CROSSREGIONDETECTION_NonMotor	= 970;				 // 非机动车穿越区域
	public static final int     DPSDK_CORE_ALARM_DRESS_ABNORMALITIES			= 971;				 // 着装异常
	public static final int     DPSDK_CORE_ALARM_WORKACTION_STATE_NO_WORKER		= 972;				 // 无人作业
	public static final int     DPSDK_CORE_ALARM_WORKACTION_STATE_SINGLE_WORKER	= 973;				 // 单人作业
	public static final int     DPSDK_CORE_ALARM_WORKACTION_STATE_NORED_VEST	= 974;				 // 没穿红马甲
	public static final int     DPSDK_CORE_ALARM_CROSSLINEDETECTION_ENGINEERING_VEHICLE = 975;		 // 工程车穿越警戒线
	public static final int     DPSDK_CORE_ALARM_CROSSREGIONDETECTION_ENGINEERING_VEHICLE = 976;	 // 工程车穿越区域
	public static final int     DPSDK_CORE_ALARM_STAYDETECTION_HUMAN			= 977;				 // 人停留事件
	public static final int     DPSDK_CORE_ALARM_STAYDETECTION_VEHICLE			= 978;				 // 机动车停留事件
	public static final int     DPSDK_CORE_ALARM_STAYDETECTION_ENGINEERING_VEHICLE  = 979;			 // 工程车停留事件
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NO_LADDER				= 990;				 // 无梯子报警
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NONINSULATED_LADDER	= 991;				 // 非绝缘梯子报警 
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NO_GLOVE				= 992; 				 // 没带手套报警 
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NONINSULATED_GLOVE	= 993;				 // 非绝缘手套报警
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NO_FENCE				= 994;				 // 无围栏报警
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NO_SIGNBOARD			= 995;				 // 无标识牌报警
	public static final int     DPSDK_CORE_ALARM_ELECTRIC_NO_CURTAIN			= 996; 				 // 无红布幔报警
	public static final int     DPSDK_CORE_ALARM_PERSON_FREQUENCY				= 997;			     // 人员频次报警
	public static final int     DPSDK_CORE_AlAEM_SMILE_FREQUENCY				= 998;				 // 微笑频次报警
	public static final int     DPSDK_CORE_ALARM_IVS_ALARM_END					= 1000;			 	 // 智能设备报警类型的范围为300-1000
	public static final int     DPSDK_CORE_ALARM_OSD							= 1001;				 // osd信息
	public static final int     DPSDK_CORE_ALARM_CROSS_INFO						= 1002;				 // 十字路口
	public static final int     DPSDK_CORE_ALARM_VTM_DISCONNETED				= 1003;				 // vtm离线报警
	public static final int     DPSDK_CORE_ALARM_PTS_FORTIFY_ILLEGALTIMEPASS	= 1004;				  // 布控在白名单内，非法时间段通过车辆
	public static final int     DPSDK_CORE_ALARM_PTS_FORTIFY_NOTINWHITELIST		= 1005;				  // 布控未在白名单内车辆
	public static final int     DPSDK_CORE_ALARM_PTS_FORTIFY_PLATERECOGNISEFAIL = 1006;				  // 布控车牌无法识别车辆 
	public static final int     DPSDK_CORE_ALARM_DEVICE_LATEST_SHUTDOWN			= 1007;				 // mtp300 设备新增最后一次关机后再开机报警
	public static final int     DPSDK_CORE_ALARM_TF_CARD_STORAGE_LOW_SPACE		= 1008;				 // mtp300 TF卡容量过低报警
	public static final int     DPSDK_CORE_ALARM_DEV_UNPLUG_DISK				= 1009;				 // 拔掉硬盘报警


	public static final int     DPSDK_CORE_ALARM_CLIENT_ALARM_BEGIN				= 1100;				 // 客户端平台报警开始
	public static final int     DPSDK_CORE_ALARM_CLIENT_DERELICTION				= 1101;				 // 遗留检测[交通事件-抛洒物]
	public static final int     DPSDK_CORE_ALARM_CLIENT_RETROGRADATION			= 1102;				 // 逆行 [交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_OVERSPEED				= 1103;				 // 超速  [交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_LACK_ALARM				= 1104;				 // 欠速  [交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_FLUX_COUNT				= 1105;				 // 流量统计[交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_PARKING					= 1106;				 // 停车检测[交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_PASSERBY				= 1107;				 // 行人检测[交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_JAM						= 1108;				 // 拥堵检测[交通事件]
	public static final int     DPSDK_CORE_ALARM_CLIENT_AREA_INBREAK			= 1109;				 // 特殊区域入侵
	public static final int     DPSDK_CORE_ALARM_USER							= 1122;				 // 用户报警
	public static final int     DPSDK_CORE_ALARM_USER_OVERSPEED					= 1123;				  //来自用户的超速报警
	public static final int     DPSDK_CORE_ALARM_USER_VTT_EMERGENCY				= 1124;           	 // 用户VTT紧急报警，由客户端上报
	public static final int     DPSDK_CORE_ALARM_USER_AH_EMERGENCY				= 1125;            	 // 用户报警主机紧急报警，由客户端上报 

	//RenLian检测事件细化事件
	public static final int     ALARM_TYPE_ALARM_FACEDETECT_NORMAL				= 1151;				 // RenLian检测事件中－正常RenLian
	public static final int     ALARM_TYPE_ALARM_FACEDETECT_UNNORMAL			= 1152;				 // RenLian检测事件中－异常RenLian

	public static final int     DPSDK_CORE_ALARM_CLIENT_ALARM_END				= 1200;				 // 客户端平台报警结束
	
	public static final int     ALARM_HOST_TEMPRATURER				= 1201;					// 主机温度过高
	public static final int     ALARM_RAID_LOAD						= 1202;					// raid降级
	public static final int     ALARM_SERVER_AUTO_MIGRATE			= 1203;					// 服务器自动迁移
	public static final int     ALARM_SERVER_MANUAL_MIGRATE			= 1204;					// 服务器手动迁移
	public static final int     ALARM_SERVER_STATUS_CHANGE			= 1205;					// 服务器状态变更 
	public static final int     ALARM_MASTER_TO_BACKUP				= 1206;					// 双机热备主机切备机
	public static final int     ALARM_BACKUP_TO_MASTER				= 1207;					// 双机热备备机切主机
	public static final int     ALARM_BACKUP_ABNORMAL				= 1208;					// 双机热备备机故障
	public static final int     ALARM_BACKUP_NORMAL					= 1209;					// 双机热备备机故障恢复
	public static final int     ALARM_STORAGE_WARNING				= 1210;					//存储到达（70，89）这个区间时的报警（单位：天）(confict!)
	public static final int     ALARM_STORAGE_STOP					= 1211;					//存储达到90天时，停止录像的报警10:42(confict!)
	public static final int     ALARM_POWER_ABNORMITY				= 1213;					//电源异常报警
	public static final int     ALARM_SYSTEM_POWER_OFF				= 1214;					//系统断电报警【市电断开】
	public static final int     ALARM_SYSTEM_POWER_ON				= 1215;					//系统电源恢复报警【市电恢复】
	public static final int     ALARM_DLT_TRANSFER_FAULT			= 1216;				     //DSS平台与DLT平台通信故障（泰国Usupply项目)
	public static final int     ALARM_SSD_LIFETIME_ABNORMAL			= 1217;					// SSD寿命异常
	public static final int     ALARM_SSD_STATUS_ABNORMAL			= 1218;					// SSD状态异常
	//哈牡客专平台管理满站铁科院检测需求
	public static final int     ALARM_CS_OFFLINE					=1219;				//111当检测到系统中的cs服务状态由正常变为离线时触发
	public static final int     ALARM_MDS_OFFLINE					=1220;				//108当检测到系统中的mds服务由正常变为离线时触发
	public static final int     ALARM_DATA_NODE_OFFLINE				=1221;				//103当存储节点的状态由正常变为离线时触发
	public static final int     ALARM_DISK_OFFLINE					=1222;				//104当节点上的某块磁盘状态由非离线变为离线状态时触发
	public static final int     ALARM_DISK_SLOW						=1223;				//105当磁盘状态由正常变为慢盘时触发
	public static final int     ALARM_DISK_BROKEN					=1224;				//106当磁盘的状态由正常变为损坏时触发
	public static final int     ALARM_DISK_ERR						=1225; 				//107当磁盘状态由正常变为错误盘时触发
	public static final int     ALARM_SYSTEM_DISK_FULL				=1226;				//服务器磁盘满报警 
	public static final int     ALARM_SYSTEM_END					= 1300;
	// -F 3.0 报警类型新增
	public static final int     ALARM_DOOR_VTO_AUTH					= 1394;				//VTO鉴权
	public static final int     ALARM_DOOR_PLATFORM_AUTH			= 1395;				//平台鉴权
	public static final int     ALARM_DOOR_LADDERCONTROL_AUTH		= 1396;				//梯控主机鉴权
	public static final int     ALARM_DOOR_LOCKED					= 1397;				// 门锁锁死     
	public static final int     ALARM_DOOR_UNLOCKED					= 1398;				// 门锁解除
	public static final int     ALARM_OUT_STAT_OPEN					= 1399;				// 报警输出打开状态    

	public static final int     ALARM_DOOR_SNAP_PIC					= 1400;					// 门禁报警抓图
	public static final int     ALARM_DEV_OFFLINE					= 1401;					// 将设备断线也当做报警，CMS->ADS
	public static final int     ALARM_IPSAN_OFFLINE					= 1402;					// 将IPSAN存储掉线(或卸载)都当做报警，SS->CMS->ADS
	public static final int     ALARM_ALARM_HOST_OFFLINE			= 1403;					// 报警主机断线当做报警，PES->CMS->ADS
	public static final int     ALARM_IP_DEV_IGNORE_TALK			= 1404;					// IP对讲网点取消对讲请求
	public static final int     ALARM_DEV_NEW_FILE					= 1405;					// 设备NewFile事件通知
	public static final int     ALARM_DEV_SNAP_UPLOAD				= 1406;					// 图片上传成功事件
	//ALARM_DOOR_LOCKED				     = 1404;				     // 门锁锁死
	//ALARM_DOOR_UNLOCKED				   = 1405;				     // 门锁解除
	//ALARM_OUT_STAT_OPEN				   = 1406;				     // 报警输出打开状态
	public static final int     ALARM_OUT_STAT_CLOSED				= 1407;				     // 报警输出关闭状态
	public static final int     ALARM_DOOR_OFFLINE					= 1408;					// 报警门禁离线当做报警
	public static final int     ALARM_IP_DEV_OFFLINE				= 1409;					// IP对讲设备离线当做报警
	public static final int     ALARM_MULTI_DEV_OFFLINE				= 1410;					// 批量设备离线报警
	//-F 门禁设备报警新增区间（40-70不够用了）
	public static final int     ALARM_DOOR_NEW_BEGIN				= 1411;
	public static final int     ALARM_DOOR_FORCE_LOCKED				= 1411;					// 门禁强锁报警
	public static final int     ALARM_DOOR_FORCE_OPEN				= 1412;					// 门禁强开报警
	public static final int     ALARM_DOOR_URGENCY_OPEN_ALL			= 1413;					// 门禁紧急全开报警
	public static final int     ALARM_DOOR_URGENCY_LOCKED_ALL		= 1414;					// 门禁紧急全关报警
	public static final int     ALARM_DOOR_KEY_OPEN_DOOR			= 1415;					// 门禁钥匙开门报警
	public static final int     ALARM_DOOR_LOCK_SHAKE				= 1416;					// 门禁锁体震动报警
	public static final int     ALARM_DOOR_SMOKE					= 1417;					// 烟雾报警（防撬门外接的探测器）
	public static final int     ALARM_DOOR_WALL_SHAKE				= 1418;					// 墙体震动报警（防撬门外接的探测器）
	public static final int     ALARM_DOOR_ATM_SHAKE				= 1419;					// 取款机震动报警（防撬门外接的探测器）
	public static final int     ALARM_DOOR_UNLOCK_OVERTIME			= 1420;					// 门禁超时未关门报警
	public static final int     ALARM_DOOR_RFID_ACTIVE				= 1421;					// 有源RFID门禁刷卡报警
	public static final int     ALARM_DOOR_RFID_PASSIVE				= 1422;					// 无源RFID门禁刷卡报警
	public static final int     ALARM_DOOR_MODE_ARMING				= 1423;					// 门禁布防设置
	public static final int     ALARM_DOOR_MODE_DISARMING			= 1424;					// 门禁撤防设置
	public static final int     ALARM_DOOR_CHASSIS_INTRUDED			= 1425;					// 门禁防拆报警
	public static final int     ALARM_DOOR_AUTH_REQUEST 			= 1426;					// 门禁请求授权
	public static final int     ALARM_DOOR_FINGERPRINT 				= 1427;					// 考勤系统指纹事件
	public static final int     ALARM_DOOR_VALID_CARD_OPENDOOR_IN	= 1428;					//对接海康设备 进门刷卡成功
	public static final int     ALARM_DOOR_VALID_CARD_OPENDOOR_OUT	= 1429;  				//对接海康设备 出门刷卡成功
	public static final int     ALARM_DOOR_BREAK_IN					= 1430;  				// 门禁闯入事件
	public static final int     ALARM_DOOR_ERR_NODOORRIGHT			= 1431;					// 该门没有权限
	public static final int     ALARM_DOOR_ERR_CARDRIGHT_PWDERR		= 1432;					// 卡号正确但是密码错误
	public static final int     ALARM_DOOR_BLACK_USER				= 1433;					//黑名单用户
	public static final int     ALARM_VALID_VRCODE_OPENDOOR			= 1434;					//合法二维码开门
	public static final int     ALARM_INVALID_VRCODE_OPENDOOR		= 1435;					//非法二维码开门
	public static final int     ALARM_VALID_IDCARD   				= 1436;					//人证合法开门	
	public static final int     ALARM_INVALID_IDCARD				= 1437;					//人证非法开门
	public static final int     ALARM_INVALID_IDCARD_AND_IC	 		= 1438;					//人证和身份证非法开门
	public static final int     ALARM_VALID_IDCARD_AND_IC			= 1439;					//人证和身份证合法开门
	public static final int     ALARM_PATROL_STATUS  				= 1440;					//巡更状态报警
	public static final int     ALARM_VALID_BT_OPENDOOR	 			= 1441;					//蓝牙合法开门
	public static final int     ALARM_INVALID_BT_OPENDOOR			= 1442;					//蓝牙非法开门
	public static final int     ALARM_DOOR_LOCAL_ALARM				= 1443;					//门禁外部报警
	public static final int     ALARM_DOOR_CHANL_MODEL				= 1444;					//通道模式
	public static final int     ALARM_DOOR_CHANL_AWAYS_STATUS		= 1445;					//通道常开，常关状态
	public static final int     ALARM_DOOR_MALICIOUT				= 1446;					//二代门禁非法卡超次报警
	public static final int     ALARM_DOOR_HEIGHTLIMIT				= 1447;				     // 门禁限高报警
	public static final int     ALARM_DOOR_RFID						= 1448;					// RFID感应报警
	public static final int     ALARM_DOOR_RFID_INVALID				= 1449;					// RFID非法感应报警
	public static final int     ALARM_DOOR_RFID_LOCAL				= 1450;					// RFID外部报警(按键报警)
	public static final int     ALARM_RFID_PEOPLE_UPPER_LIMIT		= 1451;					// RFID人数上限报警
	public static final int     ALARM_DOOR_FACEINFO_COLLECT			= 1452;					// RenLian信息录入事件
	public static final int     ALARM_DOOR_FAST_OPER_SCHEDULE		= 1453;					//快速下发(复核、下发)
	public static final int     ALARM_DOOR_RFID_POSITION_INVALID	= 1454;					//RFID非法位置报警
	public static final int     ALARM_VALID_METHOD_CARD_FIRST		= 1455;					//先刷卡后密码合法开门
	public static final int     ALARM_INVALID_METHOD_CARD_FIRST	 	= 1456;					//先刷卡后密码非法开门
	public static final int     ALARM_VALID_PWD_CARD_FINGERPRINT	= 1457;					//密码+刷卡+指纹组合合法开门
	public static final int     ALARM_INVALID_PWD_CARD_FINGERPRINT	= 1458;					//密码+刷卡+指纹组合非法开门
	public static final int     ALARM_VALID_PWD_FINGERPRINT			= 1459;					//密码+指纹组合合法开门
	public static final int     ALARM_INVALID_PWD_FINGERPRINT	 	= 1460;					//密码+指纹组合非法开门
	public static final int     ALARM_VALID_CARD_FINGERPRINT		= 1461;					//刷卡+指纹组合合法开门
	public static final int     ALARM_INVALID_CARD_FINGERPRINT	 	= 1462;					//刷卡+指纹组合非法开门
	public static final int     ALARM_VALID_PERSONS					= 1463;					//多人合法开门
	public static final int     ALARM_INVALID_PERSONS	 			= 1464;					//多人非法开门
	public static final int     ALARM_VALID_KEY						= 1465;					//钥匙合法开门
	public static final int     ALARM_INVALID_KEY 					= 1466;					//钥匙非法开门
	public static final int     ALARM_VALID_USERID_AND_PWD			= 1467;					//UserID+密码合法开门
	public static final int     ALARM_INVALID_USERID_AND_PWD	 	= 1468;					//UserID+密码非法开门
	public static final int     ALARM_VALID_FACE_AND_PWD			= 1469;					//RenLian+密码合法开门
	public static final int     ALARM_INVALID_FACE_AND_PWD	 		= 1470;					//RenLian+密码非法开门
	public static final int     ALARM_VALID_FINGERPRINT_AND_PWD		= 1471;					//指纹+密码合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_AND_PWD	= 1472;					//指纹+密码非法开门
	public static final int     ALARM_VALID_FINGERPRINT_AND_FACE	= 1473;					//指纹+RenLian合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_AND_FACE	= 1474;					//指纹+RenLian非法开门
	public static final int     ALARM_VALID_CARD_AND_FACE			= 1475;					//刷卡+RenLian合法开门
	public static final int     ALARM_INVALID_CARD_AND_FACE	 		= 1476;					//刷卡+RenLian非法开门
	public static final int     ALARM_VALID_FACE_OR_PWD				= 1477;					//RenLian或密码合法开门
	public static final int     ALARM_INVALID_FACE_OR_PWD	 		= 1478;					//RenLian或密码非法开门
	public static final int     ALARM_VALID_FINGERPRINT_OR_PWD		= 1479;					//指纹或密码合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_OR_PWD 	= 1480;					//指纹或密码非法开门
	public static final int     ALARM_VALID_FINGERPRINT_OR_FACE		= 1481;					//指纹或RenLian合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_OR_FACE	= 1482;					//指纹或RenLian非法开门
	public static final int     ALARM_VALID_CARD_OR_FACE			= 1483;					//刷卡或RenLian合法开门
	public static final int     ALARM_INVALID_CARD_OR_FACE	 		= 1484;					//刷卡或RenLian非法开门
	public static final int     ALARM_VALID_CARD_OR_FINGERPRINT		= 1485;					//刷卡或指纹合法开门
	public static final int     ALARM_INVALID_CARD_OR_FINGERPRINT	= 1486;					//刷卡或指纹非法开门
	public static final int     ALARM_VALID_FINGERPRINT_AND_FACE_AND_PWD	= 1487;					//指纹+RenLian+密码合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_AND_FACE_AND_PWD	= 1488;					//指纹+RenLian+密码非法开门
	public static final int     ALARM_VALID_CARD_AND_FACE_AND_PWD			= 1489;					//刷卡+RenLian+密码合法开门
	public static final int     ALARM_INVALID_CARD_AND_FACE_AND_PWD	 		= 1490;					//刷卡+RenLian+密码非法开门
	public static final int     ALARM_VALID_CARD_AND_FINGERPRINT_AND_PWD	= 1491;					//刷卡+指纹+密码合法开门
	public static final int     ALARM_INVALID_CARD_AND_FINGERPRINT_AND_PWD	= 1492;					//刷卡+指纹+密码非法开门
	public static final int     ALARM_VALID_CARD_AND_PWD_AND_FACE			= 1493;					//卡+指纹+RenLian组合合法开门
	public static final int     ALARM_INVALID_CARD_AND_PWD_AND_FACE			= 1494;					//卡+指纹+RenLian组合非法开门
	public static final int     ALARM_VALID_FINGERPRINT_OR_FACE_OR_PWD		= 1495;					//指纹或RenLian或密码合法开门
	public static final int     ALARM_INVALID_FINGERPRINT_OR_FACE_OR_PWD	= 1496;					//指纹或RenLian或密码非法开门
	public static final int     ALARM_VALID_CARD_OR_FACE_OR_PWD				= 1497;					//卡或RenLian或密码合法开门
	public static final int     ALARM_INVALID_CARD_OR_FACE_OR_PWD			= 1498;					//卡或RenLian或密码非法开门

	public static final int     ALARM_DOOR_NEW_END					= 1499;

	// -E 视频质量诊断 新增12种报警类型
	public static final int     ALARM_VQDS_VIDEO_LOST				= 1500;					// 视频质量诊断-视频丢失
	public static final int     ALARM_VQDS_HIGHBRIGHT				= 1501;					// 高亮度警告
	public static final int     ALARM_VQDS_HIGHBRIGHT_RED			= 1502;					// 高亮度红色报警
	public static final int     ALARM_VQDS_LOWBRIGHT				= 1503;					// 低亮度警告
	public static final int     ALARM_VQDS_LOWBRIGHT_RED			= 1504;					// 低亮度红色报警
	public static final int     ALARM_VQDS_CONTRAST					= 1505;					// 对比度警告
	public static final int     ALARM_VQDS_CONTRAST_RED				= 1506;					// 对比度红色报警
	public static final int     ALARM_VQDS_CLARITY					= 1507;					// 清晰度警告
	public static final int     ALARM_VQDS_CLARITY_RED				= 1508;					// 清晰度红色报警
	public static final int     ALARM_VQDS_COLOR_OFFSET				= 1509;					// 色彩偏差警告
	public static final int     ALARM_VQDS_COLOR_OFFSET_RED			= 1510;					// 偏色红色报警
	public static final int     ALARM_VQDS_DIAGNOSE_FAIL			= 1511;					// 视频质量诊断失败
	// 报警运营平台新增
	public static final int     ALARM_ALARMHOST_INBREAK				= 1598;					// 入侵报警
	public static final int     ALARM_ALARMHOST_FAULT				= 1599;					// 故障报警 
	public static final int     ALARM_ALARMHOST_EnableArm			= 1600;					// 报警主机布防 
	public static final int     ALARM_ALARMHOST_DisableArm			= 1601;					// 报警主机撤防 
	public static final int     ALARM_ALARMHOST_BYPASS_BYPASS		= 1602;					// 防区旁路 
	public static final int     ALARM_ALARMHOST_BYPASS_NORMAL		= 1603;					// 防区取消旁路

	public static final int     ALARM_ALARMHOST_MEDICAL				 = 1604;				 // 医疗报警
	public static final int     ALARM_ALARMHOST_URGENCY				 = 1605;				 // 报警主机紧急报警
	public static final int     ALARM_ALARMHOST_CATCH				 = 1606;				 // 挟持报警
	public static final int     ALARM_ALARMHOST_MENACE_SLIENCE       = 1607;				 // 无声威胁
	public static final int     ALARM_ALARMHOST_PERIMETER            = 1608;				 // 周界报警
	public static final int     ALARM_ALARMHOST_DEFENCEAREA_24H		 = 1609;				 // 24小时防区报警
	public static final int     ALARM_ALARMHOST_DEFENCEAREA_DELAY	 = 1610;				 // 延时防区报警
	public static final int     ALARM_ALARMHOST_DEFENCEAREA_INITIME  = 1611;				 // 及时防区报警
	public static final int     ALARM_ALARMHOST_BREAK				 = 1612;				 // 防拆
	public static final int     ALARM_ALARMHOST_AUX_OVERLOAD         = 1613;				     // AUX过流
	public static final int     ALARM_ALARMHOST_AC_POWDOWN			 = 1614;				     // 交流电掉电
	public static final int     ALARM_ALARMHOST_BAT_DOWN			 = 1615;				     // 电池欠压
	public static final int     ALARM_ALARMHOST_SYS_RESET			 = 1616;				     // 系统复位
	public static final int     ALARM_ALARMHOST_PROGRAM_CHG          = 1617;				     // 电池掉线
	public static final int     ALARM_ALARMHOST_BELL_CUT			 = 1618;				     // 警号被切断或短路
	public static final int     ALARM_ALARMHOST_PHONE_ILL			 = 1619;				     // 电话切断或失效
	public static final int     ALARM_ALARMHOST_MESS_FAIL			 = 1620;				 // 通讯失败
	public static final int     ALARM_ALARMHOST_WIRELESS_PWDOWN		 = 1621;				 // 无线探测器欠压
	public static final int     ALARM_ALARMHOST_SIGNIN_FAIL			 = 1622;				 // 登录失败
	public static final int     ALARM_ALARMHOST_ERR_CODE			 = 1623;				 // 错误密码登陆
	public static final int     ALARM_ALARMHOST_MANAUL_TEST			 = 1624;				 // 手动测试
	public static final int     ALARM_ALARMHOST_CYCLE_TEST			 = 1625;				 // 定期测试
	public static final int     ALARM_ALARMHOST_SVR_REQ				 = 1626;				 // 服务请求
	public static final int     ALARM_ALARMHOST_BUF_RST				 = 1627;				 // 报警缓冲复位
	public static final int     ALARM_ALARMHOST_CLR_LOG				 = 1628;				 // 清除日志
	public static final int     ALARM_ALARMHOST_TIME_RST			 = 1629;				 // 日期时间复位
	public static final int     ALARM_ALARMHOST_NET_FAIL			 = 1630;				 // 网络错误
	public static final int     ALARM_ALARMHOST_IP_CONFLICT			 = 1631;				 // IP冲突
	public static final int     ALARM_ALARMHOST_KB_BREAK			 = 1632;				 // 键盘防拆
	public static final int     ALARM_ALARMHOST_KB_ILL				   = 1633;				 // 键盘问题
	public static final int     ALARM_ALARMHOST_SENSOR_O			 = 1634;				 // 探测器开路
	public static final int     ALARM_ALARMHOST_SENSOR_C			 = 1635;				 // 探测器短路
	public static final int     ALARM_ALARMHOST_SENSOR_BREAK		 = 1636;				 // 探测器防拆
	public static final int     ALARM_FIRE_ALARM					 = 1637;				 // 报警主机火警

	//接警主机报警end
	public static final int     ALARM_POWER_MAJORTOSPARE			= 1640;					// 主电源切备用电源
	public static final int     ALARM_POWER_SPARETOMAJOR			= 1641;					// 备用电源切主电源
	public static final int     ALARM_ENCODER_ALARM					= 1642;					// 编码器故障报警
	public static final int     ALARM_DEVICE_REBOOT					= 1643;					// 设备重启报警
	public static final int     ALARM_DISK							= 1644;					// 硬盘报警
	public static final int     ALARM_NET_ABORT_WIRE				= 1645;					// 有线网络故障报警
	public static final int     ALARM_NET_ABORT_WIRELESS			= 1646;					// 无线网络故障报警
	public static final int     ALARM_NET_ABORT_3G					= 1647;					// 3G网络故障报警
	public static final int     ALARM_MAC_CONFLICT					= 1648;					// MAC冲突
	public static final int     ALARM_POWER_OFF_BACKUP				= 1649;					// 备用电源掉电
	public static final int     ALARM_CHASSISINTRUDED				= 1650;					// 机箱入侵	
	public static final int     ALARM_PSTN_BREAK_LINE				= 1651;					// PSTN掉线报警
	public static final int     ALARM_CALL_ALARM_HOST				= 1652;					// 电话报警主机设备报警
	public static final int     ALARM_CALL_ALARM_HOST_CHN			= 1653;					// 电话报警主机通道报警
	public static final int     ALARM_RCEMERGENCY_CALL				= 1654;					// 紧急救助未知事件
	public static final int     ALARM_RCEMERGENCY_CALL_KEYBOARD_FIRE = 1655;				// 紧急救助键盘区火警
	public static final int     ALARM_RCEMERGENCY_CALL_KEYBOARD_DURESS = 1656;				// 紧急救助键盘区胁迫
	public static final int     ALARM_RCEMERGENCY_CALL_KEYBOARD_ROBBER = 1657;				// 紧急救助键盘区匪警
	public static final int     ALARM_RCEMERGENCY_CALL_KEYBOARD_MEDICAL = 1658;				// 紧急救助键盘区医疗
	public static final int     ALARM_RCEMERGENCY_CALL_KEYBOARD_EMERGENCY = 1659;			// 紧急救助键盘区紧急
	public static final int     ALARM_RCEMERGENCY_CALL_WIRELESS_EMERGENCY = 1660;			// 紧急救助遥控器紧急
	public static final int     ALARM_LOCK_BREAK					= 1661;					// 撬锁
	public static final int     ALARM_ACCESS_CTL_OPEN				= 1662; 				// 视频报警主机异常开门
	// 报警运营平台新增end
	//DSS-H富士康项目报警主机报警类型
	public static final int     ALARM_ALARMHOST_SMOKE				= 1670;					// 报警主机烟感报警
	public static final int     ALARM_SAND_PICKING_BOATS			= 1671;					// 采砂船报警
	public static final int     ALARM_ARTIFICIAL_REPORT				= 1672;					// 人工上报 
	
	//动环(PE)报警-(SCS_ALARM_SWITCH_START 取名直接来自SCS动环文档)
	//系统工程动环增加报警类型public static final int     DPSDK_CORE_ALARM_SCS_BEGIN
	//开关量，不可控
	public static final int     DPSDK_CORE_ALARM_SCS_SWITCH_START				= 1800;
	public static final int     DPSDK_CORE_ALARM_SCS_INFRARED					= 1801;				// 红外对射告警
	public static final int     DPSDK_CORE_ALARM_SCS_SMOKE						= 1802;				// 烟感告警
	public static final int     DPSDK_CORE_ALARM_SCS_WATER						= 1803;				 // 水浸告警
	public static final int     DPSDK_CORE_ALARM_SCS_COMPRESSOR					= 1804;           	// 压缩机故障告警
	public static final int     DPSDK_CORE_ALARM_SCS_OVERLOAD					= 1805;				 // 过载告警
	public static final int     DPSDK_CORE_ALARM_SCS_BUS_ANOMALY				= 1806;          	// 母线异常
	public static final int     DPSDK_CORE_ALARM_SCS_LIFE						= 1807;				 // 寿命告警
	public static final int     DPSDK_CORE_ALARM_SCS_SOUND						= 1808;				 // 声音告警
	public static final int     DPSDK_CORE_ALARM_SCS_TIME						= 1809;				 // 时钟告警
	public static final int     DPSDK_CORE_ALARM_SCS_FLOW_LOSS					= 1810;            	// 气流丢失告警
	public static final int     DPSDK_CORE_ALARM_SCS_FUSING						= 1811;				 // 熔断告警
	public static final int     DPSDK_CORE_ALARM_SCS_BROWN_OUT					= 1812;            	// 掉电告警
	public static final int     DPSDK_CORE_ALARM_SCS_LEAKING					= 1813;				 // 漏水告警
	public static final int     DPSDK_CORE_ALARM_SCS_JAM_UP						= 1814;				 // 堵塞告警
	public static final int     DPSDK_CORE_ALARM_SCS_TIME_OUT					= 1815;				 // 超时告警
	public static final int     DPSDK_CORE_ALARM_SCS_REVERSE_ORDER				= 1816;        		// 反序告警
	public static final int     DPSDK_CORE_ALARM_SCS_NETWROK_FAILURE			= 1817;      		// 组网失败告警
	public static final int     DPSDK_CORE_ALARM_SCS_UNIT_CODE_LOSE				= 1818;       		// 机组码丢失告警
	public static final int     DPSDK_CORE_ALARM_SCS_UNIT_CODE_DISMATCH			= 1819;   			// 机组码不匹配告警
	public static final int     DPSDK_CORE_ALARM_SCS_FAULT						= 1820;				 // 故障告警
	public static final int     DPSDK_CORE_ALARM_SCS_UNKNOWN					= 1821;				 // 未知告警
	public static final int     DPSDK_CORE_ALARM_SCS_CUSTOM						= 1822;				 // 自定义告警
	public static final int     DPSDK_CORE_ALARM_SCS_NOPERMISSION				= 1823;         	// 无权限告警
	public static final int     DPSDK_CORE_ALARM_SCS_INFRARED_DOUBLE			= 1824;      		// 红外双鉴告警
	public static final int     DPSDK_CORE_ALARM_SCS_ELECTRONIC_FENCE			= 1825;     		// 电子围栏告警
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_MAINS					= 1826;            	// 市电正常市电异常
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_BATTERY				= 1827;          	// 电池正常电池异常
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_POWER_SUPPLY			= 1828;     		// UPS正常输出旁路供电
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_RUN_STATE				= 1829;        		// UPS正常UPS故障
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_LINE_STYLE				= 1830;       		// UPS类型为在线式UPS类  型为后备式
	public static final int     DPSDK_CORE_ALARM_SCS_XC							= 1831;				 // 小车
	public static final int     DPSDK_CORE_ALARM_SCS_DRQ						= 1832;				 // 断路器
	public static final int     DPSDK_CORE_ALARM_SCS_GLDZ						= 1833;				 // 隔离刀闸
	public static final int     DPSDK_CORE_ALARM_SCS_JDDZ						= 1834;				 // 接地刀闸
	public static final int     DPSDK_CORE_ALARM_SCS_IN_END						= 1835;				// 请注意这个值，不用把他作为判断值；只标记说“开关量，不可控”结束；
	//因为接下来的“开关量，可控”没有开始标记如public static final int     DPSDK_CORE_ALARM_SCS_DOOR_START
	public static final int     ALARM_SCS_COOL_WATER				= 1840;					// 冷水机组通道 
	public static final int     ALARM_SCS_ADD_HUMID					= 1841;					// 加湿器通道 
	public static final int     ALARM_SCS_HUMITURE					= 1842;					// 温湿度通道 
	public static final int     ALARM_SCS_PROSSURE					= 1843; 				// 压差通道 
	public static final int     ALARM_SCS_STS						= 1844;					// STS通道 
	public static final int     ALARM_SCS_ATS						= 1845;					// ATS通道 
	public static final int     ALARM_SCS_RPP						= 1846;					// RPP通道 
	public static final int     ALARM_SCS_KT						= 1847;					// KT通道 
	public static final int     ALARM_SCS_IN_END					= 1848;

	//开关量，可控，请注意接下来的public static final int     DPSDK_CORE_ALARM_SCS_DOOR_SWITCH这个不能作为BEGIN用
	public static final int     DPSDK_CORE_ALARM_SCS_DOOR_SWITCH				= 1850;					// 门禁控制器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_UPS_SWITCH					= 1851;					// UPS开关告警;
	public static final int     DPSDK_CORE_ALARM_SCS_DBCB_SWITCH				= 1852;          		// 配电柜开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_ACDT_SWITCH				= 1853;          		// 空调开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_DTPW_SWITCH				= 1854;          		// 直流电源开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_LIGHT_SWITCH				= 1855;         		// 灯光控制器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_FAN_SWITCH					= 1856;           		// 风扇控制器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_PUMP_SWITCH				= 1857;          		// 水泵开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_BREAKER_SWITCH				= 1858;       			// 刀闸开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_RELAY_SWITCH				= 1859;         		// 继电器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_METER_SWITCH				= 1860;        			// 电表开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_TRANSFORMER_SWITCH			= 1861;   				// 变压器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_SENSOR_SWITCH				= 1862;        			// 传感器开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_RECTIFIER_SWITCH			= 1863;     			// 整流器告警
	public static final int     DPSDK_CORE_ALARM_SCS_INVERTER_SWITCH			= 1864;      			// 逆变器告警
	public static final int     DPSDK_CORE_ALARM_SCS_PRESSURE_SWITCH			= 1865;      			// 压力开关告警
	public static final int     DPSDK_CORE_ALARM_SCS_SHUTDOWN_SWITCH			= 1866;      			// 关机告警
	public static final int     DPSDK_CORE_ALARM_SCS_WHISTLE_SWITCH				= 1867;	   				// 警笛告警
	public static final int     DPSDK_CORE_ALARM_SCS_SWITCH_END					= 1868;
	//模拟量
	public static final int     DPSDK_CORE_ALARM_SCS_ANALOG_START				= 1880;
	public static final int     DPSDK_CORE_ALARM_SCS_TEMPERATURE				= 1881;					// 温度告警
	public static final int     DPSDK_CORE_ALARM_SCS_HUMIDITY					= 1882;				 	// 湿度告警
	public static final int     DPSDK_CORE_ALARM_SCS_CONCENTRATION				= 1883;        			// 浓度告警
	public static final int     DPSDK_CORE_ALARM_SCS_WIND						= 1884;				     // 风速告警
	public static final int     DPSDK_CORE_ALARM_SCS_VOLUME						= 1885;				   	// 容量告警
	public static final int     DPSDK_CORE_ALARM_SCS_VOLTAGE					= 1886;				  	// 电压告警
	public static final int     DPSDK_CORE_ALARM_SCS_ELECTRICITY				= 1887;          		// 电流告警
	public static final int     DPSDK_CORE_ALARM_SCS_CAPACITANCE				= 1888;          		// 电容告警
	public static final int     DPSDK_CORE_ALARM_SCS_RESISTANCE					= 1889;           		// 电阻告警
	public static final int     DPSDK_CORE_ALARM_SCS_CONDUCTANCE				= 1890;          		// 电导告警
	public static final int     DPSDK_CORE_ALARM_SCS_INDUCTANCE					= 1891;           		// 电感告警
	public static final int     DPSDK_CORE_ALARM_SCS_CHARGE						= 1892;				   	// 电荷量告警
	public static final int     DPSDK_CORE_ALARM_SCS_FREQUENCY					= 1893;            		// 频率告警
	public static final int     DPSDK_CORE_ALARM_SCS_LIGHT_INTENSITY			= 1894;      			// 发光强度告警(坎)
	public static final int     DPSDK_CORE_ALARM_SCS_PRESS						= 1895;				    	// 力告警（如牛顿，千克力）
	public static final int     DPSDK_CORE_ALARM_SCS_PRESSURE					= 1896;				 	// 压强告警（帕，大气压）
	public static final int     DPSDK_CORE_ALARM_SCS_HEAT_TRANSFER				= 1897;        			// 导热告警（瓦每平米）
	public static final int     DPSDK_CORE_ALARM_SCS_THERMAL_CONDUCTIVITY		= 1898; 				// 热导告警（kcal/(m*h*℃)）
	public static final int     DPSDK_CORE_ALARM_SCS_VOLUME_HEAT				= 1899;          		// 比容热告（kcal/(kg*℃)）
	public static final int     DPSDK_CORE_ALARM_SCS_HOT_WORK					= 1900;				 	// 热功告警（焦耳）
	public static final int     DPSDK_CORE_ALARM_SCS_POWER						= 1901;				    	// 功率告警（瓦）
	public static final int     DPSDK_CORE_ALARM_SCS_PERMEABILITY				= 1902;         		// 渗透率告警（达西）
	public static final int     DPSDK_CORE_ALARM_SCS_PROPERTION					= 1903;					// 比例（包括电压电流变比，功率因素，负载单位为%） 
	public static final int     DPSDK_CORE_ALARM_SCS_ENERGY						= 1904;					// 电能（单位为J）
	public static final int     DPSDK_CORE_ALARM_SCS_ANALOG_END					= 1905;
	public static final int     ALARM_IP_DEV_TALK								= 1907;					// IP设备对讲报警

	public static final int     ALARM_TYPE_UNIFY_BEGIN							= 1908;					// 报警类型统一管理，不需要在EnumCenterRecType增加
	public static final int     ALARM_VOICE_EXCEPTION							= 1909;					// 音频异常报警	
	//ALARM_TYPE_UNIFY_END;										// 报警类型统一管理，不需要在EnumCenterRecType增加
	public static final int     ALARM_RECORD_EXCEPTION							= 1910;					// 录像异常报警
	public static final int     ALARM_VOICE_LOSE								= 1911;					// 音频丢失报警
	public static final int     ALARM_WIFITERM_FIND								= 1912;					// WIFI终端发现报警
	public static final int     ALARM_WIFITERM_SURVEY							= 1913;					// WIFI终端布控报警
	public static final int     ALARM_PTZ_DIAGNOSES								= 1914;					// 云台诊断信息
	public static final int     ALARM_SNAP_ALARM								= 1915;					// 通用抓图报警

	//EVS新增报警类型
	public static final int     ALARM_NO_DISK									= 1916;					// 无硬盘报警 
	public static final int     ALARM_DOUBLE_DEV_VERSION_ABNORMAL				= 1917;					// 双控设备主板与备板之间版本信息不一致异常事件 
	public static final int     ALARM_DCSSWITCH									= 1918;					// 主备切换事件/集群切换报警 
	public static final int     ALARM_DEV_RAID_FAILED							= 1919;					// 设备RAID错误报警 
	public static final int     ALARM_DEV_RAID_DEGRADED							= 1920;					// 设备RAID降级报警 
	public static final int     ALARM_BUF_DROP_FRAME							= 1921;					// 录像缓冲区丢帧报警
	public static final int     ALARM_WIFI_VIRTUALINFO_SEARCH					= 1922;					// WIFI终端MAC采集虚拟身份报警
	public static final int     ALARM_PATIENTDETECTION							= 1923;					// 监控病人活动状态报警事件
	public static final int     ALARM_STORAGE_ERROR_PATITION					= 1924;					// 存储分区错误
	public static final int     ALARM_RAID_STATE_EX								= 1925;					// RAID异常报警
	public static final int     ALARM_SERVER_ABNORMAL							= 1926;					// 设备本身服务异常报警 设备中使用iscsi，ftp，samba，nfs功能时，服务异常会给出报警

	public static final int     ALARM_WANDERDETECTION_EVENT 		= 1994;					// 徘徊报警
	public static final int     ALARM_RIOTERDETECTION_EVENT 		= 1995;					// 人员聚集报警
	public static final int     ALARM_SCENNE_CHANGE_ALARM			= 1996;					// 场景变更报警
	public static final int     ALARM_VIDEO_UNFOCUS					= 1997;					// 视频虚焦报警
	public static final int     ALARM_DEV_AUDIO_MUTATION			= 1998;					// 声强突变报警
	public static final int     ALARM_HEATIMG_TEMPER				= 1999;				     // 热成像测温点温度异常报警事件 

	public static final int     AE_ALARM_TYPE_BEGIN					= 2000;
	public static final int     ALARM_RFID_BATTERY_EMPTY			= 2010;						//射频设备低电量报警
	public static final int     ALARM_RFID_BUTTON					= 2011;						//射频设备按键报警
	public static final int     ALARM_RFID_DATA_EXCEPTION			= 2012;						//射频设备数据异常报警
	public static final int     ALARM_RFID_ENTER_RECEIVER			= 2013;						//射频设备接收器感应到手环报警
	public static final int     ALARM_RFID_ILLEGAL_ENTER			= 2014;						//非法进入
	public static final int     ALARM_RFID_ILLEGAL_LEAVE			= 2015;						//非法离开
	public static final int     ALARM_RFID_ILLEGAL_GATHER			= 2016;						//非法聚集
	public static final int     ALARM_RFID_WITHOUT_TUTELAGE			= 2017;						//无监护报警
	public static final int     ALARM_RFID_STAY						= 2018;						//滞留报警
	public static final int     ALARM_RFID_EXCEPTION				= 2019;						//异常报警
	public static final int     ALARM_RFID_CUTOFF_LABEL				= 2021;						//人员标签剪断
	public static final int     ALARM_RFID_GPS						= 2022;						//射频设备GPS上报
	public static final int     ALARM_RFID_APPROACH					= 2024;						//接近边界管理器
	public static final int     ALARM_RFID_LEAVEAWAY				= 2025;						//远离边界管理器
	public static final int     ALARM_RFID_OFFLINE					= 2026;						//离线超时报警
	public static final int     ALARM_RFID_SingleInterrogation		= 2027;				         //单人审讯报警
	public static final int     ALARM_RFID_WaitingRoomTimeOut		= 2028;				         //候问室超时报警
	public static final int     ALARM_RFID_Unattended				= 2029;				         //无人看管
	public static final int     ALARM_RFID_InterrogationTimeout		= 2030;				         //审讯超时
	public static final int     ALARM_RFID_Broken					= 2031;						//断开报警
	public static final int     ALARM_RFID_HeartBeat				= 2032;						//心率信息
	public static final int     ALARM_RFID_HeartBeatException		= 2033;						//心率异常报警
	public static final int     ALARM_RFID_VEHICLE_NOT_ARRIVE_TIMEOUT= 2035;					//车辆超时未达报警
	public static final int     ALARM_RFID_NEAR_DISTANCE_DETECTION	= 2036;				     //近距离接触定位报警
	public static final int     ALARM_RFID_RIOTERDETECTION			= 2037;				     //人员聚集定位报警
	public static final int     ALARM_RFID_CO_CASE_CONTACTS			= 2038;					//同案接触定位报警
	public static final int     ALARM_RFID_REVERSE					= 2100;						//逆向报警
	public static final int     ALARM_RFID_InterrogationBegin       = 2101;				         //开始审讯
	public static final int     ALARM_RFID_InterrogationEnd         = 2102;				         //结束审讯
	public static final int     ALARM_RFID_END						= 2150;						//射频报警结束	

	public static final int     ALARM_DOOR_MAGNETISM				= 2200;					// 门磁
	public static final int     ALARM_PASSIVE_INFRARED				= 2201;					// 被动红外
	public static final int     ALARM_GAS							= 2202;					// 气感
	public static final int     ALARM_INITIATIVE_INFRARED			= 2203;					// 主动红外
	public static final int     ALARM_GLASS_CRASH					= 2204;					// 玻璃破碎
	public static final int     ALARM_EXIGENCY_SWITCH				= 2205;					// 紧急开关
	public static final int     ALARM_SHAKE							= 2206;					// 震动
	public static final int     ALARM_BOTH_JUDGE					= 2207;					// 双鉴（红外+微波）
	public static final int     ALARM_THREE_TECHNIC					= 2208;					// 三技术
	public static final int     ALARM_CALL_BUTTON					= 2209;					// 呼叫按钮
	public static final int     ALARM_SENSE_OTHER					= 2210;					// 其他
	//模拟室内机报警类型
	public static final int     ALARM_SENSE_OTHER_ANALOG			= 2211;					// 模拟室内机报警类型“其他”
	public static final int     AE_ALARM_TYPE_END					= 2400;
	public static final int     ALARM_ID_CARD_COMPARE_OK			= 2401;					//人证对比成功结果上报
	public static final int     ALARM_ID_CARD_COMPARE_FAILED		= 2402;					//人证对比失败结果上报
	public static final int     ALARM_IVSS_STRANGER_ALARM			= 2403;					//IVSS陌生人报警事件（不同于陌生RenLian报警）
	public static final int     ALARM_VTO_QRCODE_CHECK				= 2404;					//二维码上报事件
	public static final int     ALARM_FACE_BLACK_LIST				= 2405;					//RenLian黑名单报警
	public static final int     ALARM_IVSS_VIP_ALARM				= 2406;					//招行项目-VIP客户报警
	public static final int     ALARM_ID_CARD_COMPARE_OK_TWO		= 2407;					//双人人证对比成功结果上报

	//防护舱报警类型 有人有锁，有人无锁，无人有锁，无人无锁，关锁超时
	public static final int     ALARM_PRC_TYPE_BEGIN				= 2500;
	public static final int     ALARM_PRC_MAN_AND_LOCK				= 2501;					// 有人有锁
	public static final int     ALARM_PRC_MAN_AND_NOLOCK			= 2502;					// 有人无锁
	public static final int     ALARM_PRC_NOMAN_AND_LOCK			= 2503;					// 无人有锁
	public static final int     ALARM_PRC_NOMAN_AND_NOLOCK			= 2504;					// 无人无锁
	public static final int     ALARM_PRC_LOCK_TIMEOUT				= 2505;					// 关锁超时
	public static final int     ALARM_PRC_EMERGENCY_CALL			= 2506;					// 紧急呼叫
	public static final int     ALARM_PRC_TYPE_END					= 2600; 

	//begin震动光纤报警类型
	public static final int     ALARM_TYPE_VIBRATIONFIBER_BEGIN     = 2601;					// 震动光纤1
	public static final int     ALARM_VIBRATIONFIBER_SNLALARM		= 2602;				     // 开关量报警 
	public static final int     ALARM_VIBRATIONFIBER_BOXALARM		= 2603;				     // 开关盒报警 
	public static final int     ALARM_VIBRATIONFIBER_INVALIDZONE	= 2604;				     // 防区失效1106 
	public static final int     ALARM_VIBRATIONFIBER_SIGNAL_OFF		= 2605;				     // 光纤信号源停止 
	public static final int     ALARM_VIBRATIONFIBER_FIBRE_BREAK	= 2606;				     // 光纤断开
	public static final int     ALARM_TALK_ALARM_IN					= 2699;					// 对讲报警输入通道报警
	public static final int     ALARM_TYPE_VIBRATIONFIBER_END		= 2700;					// 震动光纤5
	//end
	//巡更报警
	public static final int     ALARM_PATROL_BEGIN					 = 2701;
	public static final int     ALARM_PATROL_EXCEPTION				 = 2702;				// 巡更异常报警 
	public static final int     ALARM_PATROL_ROUTINE_REQUEST		 = 2703;				// 请求路线报警;巡更轨迹通知，GPS通知
	public static final int     ALARM_PATROL_LOCATION_REQUEST		 = 2704;				// 请求定位报警
	public static final int     ALARM_PATROL_PROMPTING				 = 2705;				// 巡更提醒
	public static final int     ALARM_PATROL_ROUTE_RESULT_NTF		 = 2706;				// 线路巡更结果通知
	public static final int     ALARM_PATROL_ROUTE_PROMPTING		 = 2707;				// 线路巡更提醒
	public static final int     ALARM_PATROL_REMIND_START_TASK		 = 2708;				// 巡更任务开始前提醒
	public static final int     ALARM_PATROL_REMIND_END_TASK		 = 2709;				// 巡更任务结束前提醒
	public static final int     ALARM_PATROL_END					 = 2800;
	public static final int     ALARM_ZKFINGER_BEGIN				= 2801;
	public static final int     ALARM_VALID_IDENTIFY				= 2802;					// 中控指纹 有效验证
	public static final int     ALARM_INVALID_IDENTIFY				= 2803;					// 中控指纹 无效验证
	public static final int     ALARM_ZKFINGER_END					= 2900;

	// 其他第三方设备报警类型
	public static final int     ALARM_OTHER_DEVICE_BEGIN			= 2901;		
	public static final int     ALARM_GOODSWEIGHT					= 2902;					// 货重信息报警
	public static final int     ALARM_FACE_GOODS					= 2910;					// 大江东RenLian物品信息
	public static final int     ALARM_FACE_PROOF					= 2911;					// 富士康 认证比对
	public static final int     ALARM_FACE_PROOF_FAILED				= 2912;					// 富士康 认证比对失败
	public static final int     ALARM_FLOOD_DOOR_OPEN				= 2913;					// 防汛门开门事件
	public static final int     ALARM_FINGER_PRINT					= 2914;					//指纹采集成功
	public static final int     ALARM_FINGER_PRINT_FAILED			= 2915;					//指纹采集失败
	public static final int     ALARM_OTHER_DEVICE_END				= 3000;	

	// 哨位箱报警类型
	public static final int     ALARM_SENTRY_BOX_BEGIN				= 3001;
	public static final int     ALARM_BREAK_PRISON					= 3002;					// 越狱报警
	public static final int     ALARM_FORCE_BREAK_PRISON			= 3003;					// 暴力越狱报警
	public static final int     ALARM_ATTACK						= 3004;					// 袭击报警
	public static final int     ALARM_DISASTER						= 3005;					// 灾害报警
	public static final int     ALARM_BULLET_BOX					= 3006;					// 子弹箱报警
	public static final int     ALARM_OTHERS						= 3007;					// 其他报警
	public static final int     ALARM_HIJACKED						= 3008;					// 劫持事件报警
	public static final int     ALARM_SENTRY_BOX_END				= 3100;
	// -F预留报警类型，自定义报警
	public static final int     ALARM_TYPE_USERDEFINE_BEGIN			 = 3101;
	public static final int     ALARM_TYPE_USERDEFINE_END			 = 3130;

	// 弘视智能设备报警类型begin
	public static final int     ALARM_HSIVS_ALARM_BEGIN				= 3131;
	public static final int     ALARM_VIRTUAL_WALL					= 3131;					// 虚拟墙
	public static final int     ALARM_ASSETS_PROTECT				= 3132;					// 财产保护
	public static final int     ALARM_VIDEO_QUALITY_CHECK			= 3133;					// 视质检测
	public static final int     ALARM_REGION_PROTECT				= 3134;					// 区域看防
	public static final int     ALARM_ONDUTY_CHECK					= 3135;					// 值岗检测
	public static final int     ALARM_CARNUM_RECOGNIZE				= 3136;					// 车牌识别
	public static final int     ALARM_ROUGH_MOTION_CHECK			= 3137;					// 剧烈运动检测
	public static final int     ALARM_DOUBLE_PERSON_ONDUTY			= 3138;					// 双人值岗
	public static final int     ALARM_PERSON_CAR_CLASSIFY			= 3139;					// 人车分类
	public static final int     ALARM_PERSON_NUM_COUNT				= 3140;					// 人数统计
	public static final int     ALARM_TURNVEDIO_DIAGNOSIS			= 3141;					// 轮视视频诊断
	public static final int     ALARM_EARTHWORKCAR_RECOGNIZE		= 3142;					// 土方车识别
	public static final int     ALARM_NONMOTORIZEDOBJECT_CHECK		= 3143;					// 非机动目标检测
	public static final int     ALARM_EPOLICE						= 3144;					// 电子警察
	public static final int     ALARM_IN_CROSSREGIONDETECTION		= 3145;					// 报警输入通道-警戒区事件
	public static final int     ALARM_IN_FACEDETECT					= 3146;					// 报警输入通道-RenLian检测和识别
	public static final int     ALARM_IN_PRISONERRISEDETECTION		= 3147;					// 报警输入通道-起身检测
	public static final int     ALARM_IN_CROSSLINEDETECTION			= 3148;					// 报警输入通道-警戒线事件
	public static final int     ALARM_IN_WANDERDETECTION			= 3149;					// 报警输入通道-徘徊事件
	public static final int     ALARM_IN_TRAFFIC_OVERYELLOWLINE		= 3150;					// 报警输入通道-压黄线检测
	public static final int     ALARM_IN_RETROGRADEDETECTION		= 3151;					// 报警输入通道-逆行检测
	public static final int     ALARM_IN_TRAFFIC_RUNREDLIGHT		= 3152;					// 报警输入通道-闯红灯检测
	public static final int     ALRAM_IN_PATROL_OVER_TIME			= 3153;					// 报警输入通道-巡更检测
	public static final int     ALARM_IN_TRAFFICGATE				= 3154;					// 报警输入通道-卡口检测
	public static final int     ALARM_HSIVS_ALARM_END				= 3200;					
	// 弘视智能设备报警类型end

	// 报警运营平台，扩展自定义报警类型
	public static final int     ALARM_TYPE_USERDEFINEEX_BEGIN		 = 3201;
	public static final int     ALARM_TYPE_USERDEFINEEX_END			 = 4200;

	public static final int     ALARM_NODE_ACTIVE					= 4201;					// 主从切换报警
	public static final int     ALARM_ISCSI_STATUS					= 4202;					// ISCSI存储状态变更报警
	public static final int     ALARM_OUTDOOR_STATIC				= 4203;

	public static final int     ALARM_FALLING						= 4204;					// 跌落事件报警 
	public static final int     ALARM_ITC_OUTSIDE_CARNUM			= 4205;					// 出入口外部车报警
	public static final int     ALARM_POS_TRANING_MODE				= 4206;					//POS机训练模式报警
	public static final int     ALARM_REFUND_OVER_QUOTA				= 4207;					//退货限额报警
	public static final int     ALARM_SWING_CARD_FREQUENTLY			= 4208;					//会员卡频繁出现报警
	public static final int     ALARM_SIGNLE_COST_OVER_QUOTA		= 4209;					//销售单笔超额报警

	//DSS-H可视对讲设备室内机新增传感器报警类型
	public static final int     ALARM_SENSE_BEGIN					= 4299;
	public static final int     ALARM_SENSE_DOOR					= 4300;				     //门磁
	public static final int     ALARM_SENSE_PASSIVEINFRA			= 4301;				     //被动红外
	public static final int     ALARM_SENSE_GAS						= 4302;				     //气感
	public static final int     ALARM_SENSE_SMOKING					= 4303;				     //烟感
	public static final int     ALARM_SENSE_WATER					= 4304;				     //水感
	public static final int     ALARM_SENSE_ACTIVEFRA				= 4305;				     //主动红外
	public static final int     ALARM_SENSE_GLASS					= 4306;				     //玻璃破碎
	public static final int     ALARM_SENSE_EMERGENCYSWITCH			= 4307;				     //紧急开关
	public static final int     ALARM_SENSE_SHOCK					= 4308;				     //震动
	public static final int     ALARM_SENSE_DOUBLEMETHOD			= 4309;				     //双鉴(红外+微波)
	public static final int     ALARM_SENSE_THREEMETHOD				= 4310;				     //三技术
	public static final int     ALARM_SENSE_TEMP					= 4311;				     //温度
	public static final int     ALARM_SENSE_HUMIDITY				= 4312;				     //湿度
	public static final int     ALARM_SENSE_WIND					= 4313;				     //风速
	public static final int     ALARM_SENSE_CALLBUTTON				= 4314;				     //呼叫按钮
	public static final int     ALARM_SENSE_GASPRESSURE				= 4315;				     //气体压力
	public static final int     ALARM_SENSE_GASCONCENTRATION		= 4316;				     //燃气浓度
	public static final int     ALARM_SENSE_GASFLOW					= 4317;				     //气体流量
	public static final int     ALARM_SENSE_OIL						= 4319;				     //油量检测，汽油、柴油等车辆用油检测
	public static final int     ALARM_SENSE_MILEAGE					= 4320;				     //里程数检测
	public static final int     ALARM_SENSE_URGENCYBUTTON			= 4321;				     //紧急按钮
	public static final int     ALARM_SENSE_STEAL					= 4322;				     //盗窃
	public static final int     ALARM_SENSE_PERIMETER				= 4323;				     //周界
	public static final int     ALARM_SENSE_PREVENTREMOVE			= 4324;				     //防拆
	public static final int     ALARM_SENSE_DOORBELL				= 4325;				     //门铃
	public static final int     ALARM_SENSE_LOCK_LOCKKEY			= 4326;				     //门锁钥匙报警
	public static final int     ALARM_SENSE_LOCK_LOWPOWER			= 4327;				     //门锁低电压报警
	public static final int     ALARM_SENSE_LOCK_PREVENTREMOVE		= 4328;				     //门锁防拆
	public static final int     ALARM_SENSE_LOCK_FORCE		        = 4329;				     //门锁胁迫报警
	public static final int     ALARM_SENSE_LOCK_OFFLINE			= 4330;					//门锁离线报警
	public static final int     ALARM_SENSE_FIRE					= 4331;					//火警
	public static final int     ALARM_SENSE_SIGNIN					= 4332;					//室内机签到报警
	public static final int     ALARM_SENSE_INFRARED				= 4333;					//红外报警
	public static final int     ALARM_SENSE_GATE_PREVENTREMOVE      = 4334;					//网关防拆
	public static final int     ALARM_SENSE_DELAY_ARMING			= 4335;					//延时布放
	public static final int     ALARM_SENSE_MODE_ARMING				= 4336;					//布防设置
	public static final int     ALARM_SENSE_MODE_DISARMING			= 4337;					//撤防设置
	public static final int     ALARM_SENSE_END						= 4399;

	public static final int     ALARM_STORAGE_BEGIN					= 4400;
	public static final int     ALARM_IO_QUEUE_FULL					= 4401;					// 磁盘读写高负荷
	public static final int     ALARM_DISK_DESTROY					= 4402;					// 磁盘异常
	public static final int     ALARM_IPSAN_OFF_LINE				= 4403;					// IPSan掉线
	public static final int     ALARM_NO_DISK_STORAGE				= 4404;					// 没有磁盘			
	public static final int     ALARM_GET_STREAM_ERROR				= 4405;					// 取码流错误
	public static final int     ALARM_STORAGE_END					= 4499;

	//DSSH出入口卡口黑名单报警类型新增
	public static final int     ALARM_TRAFFIC_SUSPICIOUSCAR         = 4501;

	//大华出入口控制机报警类型
	public static final int     ALARM_SLUICE_BEGIN								= 4502;
	public static final int     ALARM_SLUICE_IC_CARD_STATUS_LOWCARD				= 4503;					//卡箱少卡报警
	public static final int     ALARM_SLUICE_IC_CARD_STATUS_NOCARD				= 4504;					//卡箱无卡报警
	public static final int     ALARM_SLUICE_IC_CARD_STATUS_FULLCARDS			= 4505;					//卡箱卡满报警
	public static final int     ALARM_SLUICE_CAR_DETECTOR_STATE_OFFLINE			= 4506;					//车检器掉线报警
	public static final int     ALARM_SLUICE_CAR_DETECTOR_STATE_LOOPOFFLINE		= 4507;					//地感线圈掉线报警
	public static final int     ALARM_SLUICE_LED_DEV_STATE_OFFLINE				= 4508;					//LED掉线报警
	public static final int     ALARM_SLUICE_SWIPING_CARD_DEV_STATE_OFFLINE		= 4509;					//面板刷卡板掉线报警
	public static final int     ALARM_SLUICE_DELIVE_CARD_DEV_OFFLINE			= 4510;					//发卡刷卡板掉线报警
	public static final int     ALARM_SLUICE_SPEAK_DEV_STATUS					= 4511;					//对讲事件报警
	public static final int     ALARM_SLUICE_END								= 4550;

	//DSSH自助缴费机报警类型
	public static final int     ALARM_SELFPAY_BEGIN								= 4551;
	public static final int     ALARM_SELFPAY_NOPAPER							= 4552;//缺纸
	public static final int     ALARM_SELFPAY_NOCASH50							= 4553;
	public static final int     ALARM_SELFPAY_NOCASH20							= 4554;
	public static final int     ALARM_SELFPAY_NOCASH10							= 4555;
	public static final int     ALARM_SELFPAY_NOCASH1							= 4556;
	public static final int     ALARM_SELFPAY_NOCOIN							= 4557;
	public static final int     ALARM_SELFPAY_LOCKMONEY							= 4558;//卡币
	public static final int     ALARM_SELFPAY_DISMANTLE							= 4559;//防拆
	public static final int     ALARM_SELFPAY_UNPACK							= 4560;//开箱
	public static final int     ALARM_SELFPAY_UNKONWN							= 4561;//纸币不识别
	public static final int     ALARM_SELFPAY_CASHBOXOTHER						= 4562;					//钱箱识别器其他错误
	public static final int     ALARM_SELFPAY_PRINTERERR						= 4563;					//热敏打印机械故障
	public static final int     ALARM_SELFPAY_RECOGNITIONSELFCHECKERR			= 4564;					//硬币识别器自检错误
	public static final int     ALARM_SELFPAY_RECOGNITIONPOLLONLINE				= 4565;					//硬币识别器轮询在线
	public static final int     ALARM_SELFPAY_CHANGEONLINE						= 4566;					//硬币找零器是否在线
	public static final int     ALARM_SELFPAY_END								= 4580;
	public static final int     ALARM_ITC_BLACKLIST_CARNUM						= 4581;					//PES停车场模块黑名单车辆
	public static final int     ALARM_ITC_RESERVE_OCCUPY						= 4582;					//停车场手机预定车位被占用 
	//门禁设备扩展报警
	public static final int     ALARM_DOOREX_BEGIN								= 4600;
	public static final int     ALARM_VALID_CARD_OR_FINGERPRINT_OR_FACE			= 4601;				//卡或指纹或RenLian合法开门
	public static final int     ALARM_INVALID_CARD_OR_FINGERPRINT_OR_FACE		= 4602;				//卡或指纹或RenLian非法开门
	public static final int     ALARM_VALID_CARD_AND_FINGERPRINT_AND_FACE_AND_PWD		= 4603;		//卡+指纹+RenLian+密码组合合法开门
	public static final int     ALARM_INVALID_CARD_AND_FINGERPRINT_AND_FACE_AND_PWD	 	= 4604;		//卡+指纹+RenLian+密码组合非法开门
	public static final int     ALARM_VALID_CARD_OR_FINGERPRINT_OR_FACE_OR_PWD	= 4605;				//卡或指纹或RenLian或密码合法开门
	public static final int     ALARM_INVALID_CARD_OR_FINGERPRINT_OR_FACE_OR_PWD	= 4606;			//卡或指纹或RenLian或密码非法开门
	public static final int     ALARM_VALID_FACEIPCARDANDIDCARD_OR_CARD_OR_FACE		= 4607;			//(身份证+人证比对)或 刷卡 或 RenLian合法开门
	public static final int     ALARM_INVALID_FACEIPCARDANDIDCARD_OR_CARD_OR_FACE	= 4608;			//(身份证+人证比对)或 刷卡 或 RenLian非法开门
	public static final int     ALARM_VALID_FACEIDCARD_OR_CARD_OR_FACE			= 4609;				//人证比对 或 刷卡(二维码) 或 RenLian合法开门
	public static final int     ALARM_INVALID_FACEIDCARD_OR_CARD_OR_FACE		= 4610;				//人证比对 或 刷卡(二维码) 或 RenLian非法开门
	public static final int     ALARM_VALID_REMOTE_QRCODE						= 4611;				//远程二维码合法开门
	public static final int     ALARM_INVALID_REMOTE_QRCODE						= 4612;				//远程二维码非法开门
	public static final int     ALARM_VALID_REMOTE_FACE							= 4613;				//远程RenLian合法开门
	public static final int     ALARM_INVALID_REMOTE_FACE						= 4614;				//远程RenLian非法开门
	public static final int     ALARM_VALID_CITIZEN_FINGERPRINT					= 4615;				//人证比对(指纹)合法开门
	public static final int     ALARM_INVALID_CITIZEN_FINGERPRINT				= 4616;				//人证比对(指纹)非法开门
	public static final int     ALARM_RFID_PET_ABNORMAL_THROUGH					= 4617;				//宠物异常通行
	public static final int     ALARM_RFID_ELECTROMBILE_UNIT_ENTER				= 4618;				//电动车进出单元报警
	public static final int     ALARM_RFID_ELECTROMBILE_AREA_FORBID				= 4619;				//电动车区域禁停报警
	public static final int     ALARM_RFID_ABNORMAL_IN_AND_OUT					= 4620;				//人员异常出入预警
	public static final int     ALARM_VALID_PWD_FIRST							= 4621;				//先密码后刷卡合法开门
	public static final int     ALARM_INVALID_PWD_FIRST							= 4622;				//先密码后刷卡非法开门
	public static final int     ALARM_DOOROPEN_MALICE							= 4623;				//恶意开门事件
	public static final int     ALARM_RFID_NOT_IN_AND_OUT						= 4624;				//人员未出入预警
	public static final int     ALARM_ACTIVE_LOW_POWER							= 4625;				//有源RFID低电量报警
	public static final int     ALARM_VALID_HELMET_OPEN_DOOR					= 4626;				//RenLian安全帽合法开门
	public static final int     ALARM_INVALID_HELMET_OPEN_DOOR					= 4627;				//RenLian安全帽非法开门
	public static final int     ALARM_DISCONN_TIMEOUT							= 4628;				//离线超时报警
	public static final int     ALARM_TYPE_FREEZE								= 4629;				//冻结卡刷卡事件
	public static final int     ALARM_DOOREX_END								= 4699;

	//客户端IP对讲报警
	public static final int     ALARM_IP_DEV_BEGIN								= 4700;
	public static final int     ALARM_IP_DEV_CALLIN								= 4701;				//分机呼叫
	public static final int     ALARM_IP_DEV_CALLOUT							= 4702;				//拨打
	public static final int     ALARM_IP_DEV_END								= 4800;

	// -F;ATM防护舱报警类型
	public static final int     ALARM_DEFENCE_DISMANTLE_DESTORY					= 4801;					// ATM防护舱防拆防破坏报警
	public static final int     ALARM_URGENT_BUTTON_TRIGGER						= 4802;					// ATM防护舱紧急按钮触发报警
	public static final int     ALARM_CABIN_TWO_PERSONS							= 4803;					// ATM防护舱舱内两人报警
	public static final int     ALARM_CABIN_OUTSIDE								= 4804;					// ATM防护舱外部报警
	public static final int     ALARM_GATELOCK_ABNORMAL							= 4805;					// ATM防护舱门锁异常报警
	public static final int     ALARM_CABIN_INSIDE_SOMEONE_FALLDOWN				= 4806;
	public static final int     ALARM_INFRARED_INSPECTED						= 4807;					// 红外对射
	public static final int     ALARM_CLOSE_LOCK_IN_BUTTON						= 4808;					// 闭锁进门按钮
	public static final int     ALARM_CLOSE_LOCK_OUT_BUTTON						= 4809;					// 闭锁出门按钮
	public static final int     ALARM_IN_DOOR_BUTTON							= 4810;					// 进门按钮
	public static final int     ALARM_OUT_DOOR_BUTTON							= 4811;					// 出门按钮
	public static final int     ALARM_OPEN_LOCK_BREAKDOWN						= 4812;					// 开锁故障
	public static final int     ALARM_CLOSE_LOCK_BREAKDOWN						= 4813;					// 闭锁故障	
	public static final int     ALARM_CABIN_FACEDETECT_UNNORMAL					= 4814;				     // RenLian检测事件中－异常RenLian
	public static final int     ALARM_CABIN_FIGHTDETECTION						= 4815;				     // 斗殴事件
	public static final int     ALARM_CABIN_VIDEO_SHELTER						= 4816;				     // 视频遮挡
	public static final int     ALARM_CABIN_DOOR_ATM_SHAKE						= 4817;				     // 取款机震动报警（防撬门外接的探测器）
	public static final int     ALARM_CABIN_EXCEPTION_STAY						= 4818;				     //异常滞留
	public static final int     ALARM_ATM_MSG_TYPE_USER_TIMESLOT_USEDUP 		= 4819;					// ATM未按出门按钮人员感应失效
	public static final int     ALARM_ATM_MSG_TYPE_CABIN_IN 					= 4820;					// 进ATM防护舱
	public static final int     ALARM_ATM_MSG_TYPE_CABIN_OUT 					= 4821;					// 出ATM防护舱
	public static final int     ALARM_ATM_MSG_TYPE_CABIN_LOCK 					= 4822;					// 闭锁ATM防护舱
	public static final int     ALARM_ATM_MSG_TYPE_CABIN_UNLOCK 				= 4823;					// 解锁ATM防护舱
	public static final int     ALARM_ATM_MSG_TYPE_DOOR_CLOSE 					= 4824;					// ATM防护舱门关
	public static final int     ALARM_ATM_MSG_TYPE_DOOR_OPEN 					= 4825;					// ATM防护舱门开

	//-F报警
	public static final int     ALARM_OUT_STAT_OPENEX							= 4851;				     // 报警输出打开状态
	public static final int     ALARM_OUT_STAT_CLOSEDEX							= 4852;				     // 报警输出关闭状态重新定义
	public static final int     ALARM_ROBOT_GENERAL								= 4853;					// 机器人通用报警
	public static final int     ALARM_ENABLE_ARM_PROMPT							= 4854;					// 布防提示报警
	public static final int     ALARM_DISABLE_ARM_PROMPT						= 4855;					// 撤防提示报警
	public static final int     ALARM_DEV_CFG_ABNORMAL							= 4856;					// 设备参数异常报警（DMS自造）
	public static final int     ALARM_DEV_TIME_ABNORMAL							= 4857;					// 设备时间异常报警（DMS自造）

	//手机APP报警类型
	public static final int     ALARM_MOBILEAPP_BEGIN									= 4900;
	public static final int     ALARM_MOBILEAPP_GPS										= 4901;		//手机APP上传GPS
	public static final int     ALARM_MOBILEAPP_ONE_CLICK								= 4902;		//手机APP一键报警
	public static final int     ALARM_MOBILEAPP_MANUAL_ADD								= 4903;		//手机APP手动添加报警
	public static final int     ALARM_MOBILEAPP_END										= 5000;

	//APS人数统计报警
	public static final int     ALAMR_PEOPLE_COUNTING_BEGIN								= 5001;
	public static final int     ALARM_PEOPLE_UPPER_LIMIT								= 5002;		//人数上限
	public static final int     ALARM_PEOPLE_LOWER_LIMIT								= 5003;		//人数下限
	public static final int     ALARM_INFLUX_UPPER_LIMIT								= 5004;		//人流量超标（进）
	public static final int     ALARM_OUTFLUX_UPPER_LIMIT								= 5005;		//人流量超标（出）
	public static final int     ALARM_DENSITY_UPPER_LIMIT								= 5006;		//密度报警
	public static final int     ALARM_SCENE_EXCEPTION									= 5007;		//场景异常报警
	public static final int     ALARM_EXCEPTION_STAY									= 5008;		//异常滞留
	public static final int     ALARM_GUARD_LINE										= 5009;		//警卫线报警
	public static final int     ALARM_STAY_TIMEOUT										= 5010;		//超时滞留报警
	public static final int     ALAMR_PEOPLE_COUNTING_END								= 5100;

	public static final int     ALARM_THIRD_ACCESS  									= 5101;      //第三方接入设备报警
	public static final int     ALARM_PC_REPORT											= 5102;		 //智能设备上报人数统计报警
	public static final int     ALARM_THREE_IN_ONE										= 5103;		 //三台合一报警
	public static final int     ALARM_HUMAM_NUMBER_STATISTIC							= 5104;		 //人流量统计相机客流量超过阀值报警事件
	public static final int     ALARM_PERSON_COUNT_REPORT				 				= 5105;      //人流量统计（以报警方式上报人流量统计信息）
	public static final int     ALARM_MAN_NUM_DETECTION				 					= 5106;      //立体视觉区域内人数统计报警
	public static final int     ALARM_NUMBERSTAT_GROUP_SUMMARY				 			= 5107;      //按分组查询的人流量统计	
	public static final int     ALARM_DISTANCE_DETECTION								= 5108;		//立体行为分析间距异常/人员靠近检测报警
	public static final int     ALARM_TUMBLE_DETECTION									= 5109;		//立体行为分析跌倒检测报警

	// 热成像报警
	public static final int     ALARM_RADIOMETRY_HEATIMG_TEMPER							= 5120;          //热成像测温点温度异常报警
	public static final int     ALARM_RADIOMETRY_FIRE_WARNING							= 5121;          //热成像着火点报警
	public static final int     ALARM_RADIOMETRY_FIREWARNING_INFO         				= 5122;          //热成像火情报警信息上报
	public static final int     ALARM_RADIOMETRY_HOTSPOT_WARNING           				= 5123;          //热成像热点异常报警（高于温度阀值报警）
	public static final int     ALARM_RADIOMETRY_COLDSPOT_WARNING          				= 5124;          //热成像冷点异常报警（低于温度阀值报警）
	public static final int     ALARM_RADIOMETRY_BETWEENRULE_TEMP_DIFF					= 5125;          //热成像规则间温差异常报警
	public static final int     ALARM_RADIOMETRY_SMOKE_DETECTION         				= 5126;          //热成像烟雾报警
	public static final int     ALARM_RADIOMETRY_FACE_OVERHEATING           			= 5127;          //热成像人体发烧预警
	public static final int     ALARM_RADIOMETRY_HUMAN_OVERTEMP           			    = 5128;          //热成像人体高温报警
	public static final int     ALARM_RADIOMETRY_HUMAN_UNDERTEMP           			    = 5129;          //热成像人体低温报警
	public static final int     ALARM_REGULATOR_ABNORMAL           						= 5130;          //标准黑体源异常报警事件
	// 
	public static final int     ALARM_SIM_CARD_FLUX_REPORT								= 5140;			// 车载MDVR上月历史流量报警

	//
	public static final int     ALARM_OIL_4G_OVERFLOW									= 5160;			// 4G流量超额报警

	// 
	public static final int     ALARM_SUB_WAY_DOOR_STATE 								= 5170;		// 地铁车厢门报警
	public static final int     ALARM_SUB_WAY_PECE_SWITCH								= 5171;		// 地铁PECE柜门报警
	public static final int     ALARM_SUB_WAY_FIRE_ALARM								= 5172;		// 地铁火警报警
	public static final int     ALARM_SUB_WAY_EMER_HANDLE								= 5173;		// 地铁乘客紧急手柄动作报警
	public static final int     ALARM_SUB_WAY_CAB_COVER									= 5174;		// 地铁司机室盖板报警
	public static final int     ALARM_SUB_WAY_DERA_OBST									= 5175;		// 地铁检测到障碍物或脱轨报警
	public static final int     ALARM_SUB_WAY_PECU_CALL									= 5176;		// 地铁客室报警器报警


	//客户端机顶盒设备定制报警
	public static final int     ALARM_STB_BEGIN									= 5200;		
	public static final int     ALARM_STB_FIRE									= 5201;		//火警
	public static final int     ALARM_STB_CRIME									= 5202;		//匪警
	public static final int     ALARM_STB_EMERGENCY								= 5203;		//急救中心
	public static final int     ALARM_STB_OTHER									= 5204;		//其他报警
	public static final int     ALARM_STB_END									= 5250;

	public static final int     ALARM_SD_STORAGE_NOT_EXIST						=	5251;		//SD卡没有识别到
	public static final int     ALARM_SD_STORAGE_LOW_SPACE						=	5252;		//SD卡存储空间快满了
	public static final int     ALARM_SD_STORAGE_FAILURE_EX						=	5253;		//SD卡识别到了，但是坏了

	//-C/-P新增报警预留
	public static final int     ALARM_DSSC_BEGIN								=	5300;
	public static final int     ALARM_PATIENTDETECTION_TYPE_CROSS_REGION		=	ALARM_DSSC_BEGIN + 1;	// 警戒区域报警，可能是病人离开或者有其他靠近病人
	public static final int     ALARM_PATIENTDETECTION_TYPE_LIGHT_OFF			=	ALARM_DSSC_BEGIN + 2;	// 病房电灯被熄灭
	public static final int     ALARM_PATIENTDETECTION_TYPE_STOP_DETECTION		=	ALARM_DSSC_BEGIN + 3;	// 撤防，不再监控病人
	public static final int     ALARM_PATIENTDETECTION_TYPE_START_DETECTION		=	ALARM_DSSC_BEGIN + 4;	// 开始布防
	public static final int     ALARM_PATIENTDETECTION_TYPE_ESCAPE				=	ALARM_DSSC_BEGIN + 5;	// 病人在押解过程中逃跑
	public static final int     ALARM_PATIENTDETECTION_TYPE_SMOKE				=	ALARM_DSSC_BEGIN + 6;	// 烟感报警
	public static final int     ALARM_DSSC_END									=	5400;

	public static final int     ALARM_U700_BEGIN								= 5401;
	public static final int     ALARM_VTA_INSPECTION							= ALARM_U700_BEGIN + 1;		// VTA报警柱巡检报警
	public static final int     ALARM_VTA_OVERSPEED								= ALARM_U700_BEGIN + 2;		// VTA报警柱超速报警
	public static final int     ALARM_VTA_INSPECTION_SWING_CARD					= ALARM_U700_BEGIN + 3;		// VTA巡检刷卡
	public static final int     ALARM_VTA_PATROL_SWING_CARD						= ALARM_U700_BEGIN + 4;		// VTA巡更刷卡
	public static final int     ALARM_EXTERNAL_IVS								= ALARM_U700_BEGIN + 5;		// 外部智能报警
	public static final int     ALARM_U700_END = 5500;
	public static final int     ALARM_REMOTE_CAMERA_STATE						= 5501;				//卡口设备相机状态上报报警
	public static final int     ALARM_SHANGHAI_JIHENG							= 5502;					// 上海迹恒上报报警
	public static final int     ALARM_PATROL_REMIND								= 5503;				//巡更提醒报警
	public static final int     ALARM_VTO_ACCESSIDENTIFY						= 5504;				//门口机RenLian认证
	public static final int     ALARM_CAR_SURVEY								= 5505;				//卡口布控报警
	public static final int     ALARM_CHANNEL_TALK								= 5506;				//通道对讲报警
	public static final int     ALARM_HEARTRATE_DETECT							= 5507;				//心率侦测
	
	//人行道闸报警定义 5640- 5680
	public static final int     ALARM_ROADGATE_BEGIN									= 5640;

	public static final int     ALARM_ROADGATE_VALID_PASSWORD_OPENDOOR					= 5642;
	public static final int     ALARM_ROADGATE_INVALID_PASSWORD_OPENDOOR				= 5643;
	public static final int     ALARM_ROADGATE_REMOTE_OPENDOOR							= 5648;
	public static final int     ALARM_ROADGATE_VALID_CARD_OPENDOOR						= 5651;
	public static final int     ALARM_ROADGATE_INVALID_CARD_OPENDOOR					= 5652;
	public static final int     ALARM_ROADGATE_NORMAL_CLOSED							= 5656;
	public static final int     ALARM_ROADGATE_OPEN										= 5657;
	public static final int     ALARM_ROADGATE_OPEN_TIME_OUT_BEG						= 5660;
	public static final int     ALARM_ROADGATE_OPEN_TIME_OUT_END						= 5670;

	public static final int     ALARM_ROADGATE_END									= 5680;


	//-P 行业线 对接海康设备增加报警
	public static final int     ALARM_AUDIO_ABNORMALDETECTION							= 5700;		
	public static final int     ALARM_CLIMB_UP_DETECTION								= 5701;
	public static final int     ALARM_CROSSRE_DETECTION									= 5702;
	public static final int     ALARM_FIGHT_DETECTION									= 5703;
	//倍特卫视分析设备
	public static final int     ALARM_RAISE_UP_DETECTION								= 5705;
	public static final int     ALARM_WC_TIMEOUT_DETECTION								= 5706;
	public static final int     ALARM_DUTY_DETECTION									= 5707;
	public static final int     ALARM_OUTSIDE_STRANDED_DETECTION						= 5708;
	// 科大讯飞语音报警
	public static final int     ALARM_KVOICE_ALARM										= 5709;
	// 华为智能分析服务器设备
	public static final int     ALARM_KEY_PERSON_GETUP				 				         = 5710; // 重点人员起身报警
	public static final int     ALARM_CROSSING_LINE				 				            = 5711; // 越线报警
	public static final int     ALARM_LEAVE_REGION				 				             = 5712; // 离开区域
	public static final int     ALARM_PERSON_QUIET_SIT				 				         = 5713; // 人员静坐
	public static final int     ALARM_PERSON_NUM_ABNORMAL				 				      = 5714; // 人数异常
	public static final int     ALALM_PERSON_STAY_ALONE				 				        = 5715; // 单人独处
	public static final int     ALARM_FIGHT				 				 				       = 5716; // 打架斗殴 
	public static final int     ALARM_PERSON_LEAVE_BED				 				         = 5717; // 人员离床 
	public static final int     ALARM_OFF_DUTY				 				 				    = 5718; // 离岗检测
	public static final int     ALARM_SLEEP_DETECTION				 				          = 5719; // 睡岗检测
	public static final int     ALARM_GETUP				 				 				       = 5720; // 起身
	public static final int     ALARM_LINGER				 				 				      = 5721; // 徘徊
	public static final int     ALARM_EMERGENCY_EVENT				 				          = 5722; // 紧急事件
	public static final int     ALARM_PERSON_GATHER				 				            = 5723; // 人员聚集

	//老动环报警扩展定义报警区间段
	public static final int     ALARM_SCS_EXT_BEGIN										= 6000;
	public static final int     ALARM_SCS_EXT_NOISE_INTENSITY							= 6001;						//噪声告警
	public static final int     ALARM_SCS_EXT_END										= 6999;

	//雷达信息报警区间
	public static final int     ALARM_RADAR_BEGIN										= 7000;
	public static final int     ALARM_RADAR_TARGETINFO									= 7001;		// 雷达上传目标信息
	public static final int     ALARM_RADAR_ALARM										= 7002;		// 雷达报警上传
	public static final int     ALARM_RADAR_END											= 7100;

	public static final int     ALARM_TYPE_MAINTENANCE_OUT								=7200; 		//钥匙回转
	public static final int     ALARM_TYPE_MAINTENANCE_IN								=7201;		//钥匙打开
	public static final int     ALARM_TYPE_BATTERY_FAILURE								=7202;		//电池失效
	public static final int     ALARM_TYPE_POWER_FAILURE								=7203;		//POWER_FAILURE
	public static final int     ALARM_TYPE_SPEAKER_FAILURE								=7204;		//SPEAKER_FAILURE


	public static final int     ALARM_TYPE_FAN_SPEED									=7300;		//风扇异常


	//新动环报警定义报警区间段
	public static final int     ALARM_NEW_SCS_BEGIN										= 8000;
	public static final int     ALARM_NEW_SCS_PREVENTREMOVE 							= 9997;				//动环防拆
	public static final int     ALARM_NEW_SCS_PREVENT_SHORTCIRCUIT						= 9998;				//动环防短
	public static final int     ALARM_NEW_SCS_END										= 9999;
	//平台业务报警区间段
	public static final int     ALARM_BUSINESS_BEGIN									= 10001;
	public static final int     ALARM_BUSINESS_POLICE_PATROL							= 10002;			//民警巡视业务报警		
	public static final int     ALARM_BUSINESS_WAITING_ROOM_UNATTENDED					= 10003;			//候问室无人看管
	public static final int     ALARM_BUSINESS_WASHROOM_UNATTENDED						= 10004;			//卫生间无人跟随
	public static final int     ALARM_BUSINESS_MAN_NUM_DETECTION						= 10005;			//审讯室人数报警
	public static final int     ALARM_BUSINESS_NOCAP_DETECTION							= 10006;			//未带安全帽报警
	public static final int     ALARM_BUSINESS_END										= 10500;

	public static final int     ALARM_VEHICLE_SURVEY_EW									= 10501;			//车辆布控预警报警
	public static final int     ALARM_FACE_BLOCK										= 10502;			//RenLian卡口报警
	public static final int     ALARM_VEHICLE_SCORE_EW									= 10503;			//车辆积分预警报警
	//CMS平台报警
	public static final int     ALARM_DISTRIBUTE_SWITCHOVER								= 10600;			//N+M备份切换报警

	public static final int     ALARM_HBSZZ_APP_BUTTON									= 10601;			//河北省综治项目APP一键报警
	public static final int     ALARM_WIDE_VIEW_REGION_ALARM							= 10602;			// 全景区域报警，浙江二监定制
	public static final int     ALARM_HIGH_DECIBEL										= 10603;			// 声音高分贝检测报警
	public static final int     ALARM_SHAKE_DETECTION									= 10604;			// 摇晃检测报警
	public static final int     ALARM_BATTERY_LOW_POWER									= 10605;			// 电池电量低报警
	public static final int     ALARM_GZGDXL_TVAPP										= 10606;			//贵州广电XLiangTV APP报警

	public static final int     ALARM_LOWER_DOMAIN_DISCONNECT							= 10700;			//下级平台断开报警
	//PTS新增报警
	public static final int     ALARM_PTS_BEGIN											= 11000;
	//布控报警 begin
	public static final int     ALARM_FORTIFY_OVERSPEED									= 11001;	// 布控超速车辆
	public static final int     ALARM_FORTIFY_STOLEN									= 11002;	// 布控盗抢车辆
	public static final int     ALARM_FORTIFY_ACCIDENT									= 11003;	// 布控肇事车辆
	public static final int     ALARM_FORTIFY_SUSPICE									= 11004;	// 布控嫌疑车辆
	public static final int     ALARM_FORTIFY_HEADOFF									= 11005;	// 布控拦截车辆
	public static final int     ALARM_FORTIFY_CHECKED									= 11006;	// 布控检查盘查
	public static final int     ALARM_FORTIFY_FOLLOWED									= 11007;	// 布控观察跟踪
	public static final int     ALARM_FORTIFY_DANGER									= 11008;	// 布控高危车辆
	public static final int     ALARM_FORTIFY_STRANDING									= 11009;	// 布控滞留车辆
	public static final int     ALARM_FORTIFY_SPECIALEXCEPTION							= 11010;	// 特殊异常车辆
	public static final int     ALARM_FORTIFY_EXHAUST									= 11011;	// 布控黄标车
	public static final int     ALARM_FORTIFY_WHITELIST									= 11012;	// 布控白名单
	public static final int     ALARM_FORTIFY_BLACKLIST									= 11013;	// 布控黑名单
	public static final int     ALARM_FORTIFY_LASTNUMBER								= 11014;	// 布控尾号限行
	public static final int     ALARM_FORTIFY_GRIDLINE									= 11015;	// 网格布控（车辆经过网内任意两个卡点）
	public static final int     ALARM_FORTIFY_TIMEOUT									= 11016;	// 布控滞留超时车辆
	public static final int     ALARM_FORTIFY_ILLEGALTIMEPASS							= 11017;	// 布控在白名单内，非法时间段通过车辆(暂定上海浦东垃圾场定制)
	public static final int     ALARM_FORTIFY_NOTINWHITELIST							= 11018;	// 布控未在白名单内车辆(暂定上海浦东垃圾场定制)
	public static final int     ALARM_FORTIFY_RECOGNISEFAIL								= 11019;	// 布控车牌无法识别车辆(暂定上海浦东垃圾场定制)
	//布控报警 end

	public static final int     ALARM_NO_DRIVERROAD										= 11101;	//非机动车道
	public static final int     ALARM_OFFEND_INTERDICTORYSIGN							= 11102;	//机动车违反禁令标志指示	
	public static final int     ALARM_COVERING_PLATE									= 11103;	//遮挡号牌
	public static final int     ALARM_ROUND_ITS											= 11104;	//绕行卡口
	public static final int     ALARM_RESTRICT_DRIVING									= 11105;	//限行
	public static final int     ALARM_PEDESTRAIN_PRIORITY								= 11106;	//斑马线行人优先
	public static final int     ALARM_MNVR_PEC											= 11107;	//车辆黑名单事件
	public static final int     ALARM_COMPARE_PLATE										= 11108;	//车牌前后对比	
	public static final int     ALARM_TRAFFIC_CARWEIGHT									= 11109;	//超重
	public static final int     ALARM_TRANSFINITE_PECCANCY								= 11110;	//超限违章
	public static final int     ALARM_CHASSIS_CHECK										= 11111;	//底盘检查			
	public static final int     ALARM_PREILLEGALLY_PARKED								= 11112;	//预违停
	public static final int     ALARM_CAR_DETECTOR_FAULT								= 11113;	//线圈/车检器故障报警
	public static final int     ALARM_REMOTE_HOST										= 11114;	//远程主机报警
	public static final int     ALARM_TRAFFICLIGHTS_FAULT								= 11115;	//灯绿灯
	public static final int     ALARM_TRAFFIC_INTERRUPT									= 11116;	//交通中断
	public static final int     ALARM_DATABASE_FAULT									= 11117;	//数据库错误
	public static final int     ALARM_PTS_END											= 11500;

	// 报警事件类型 DH_EVENT_VIDEOABNORMALDETECTION (视频异常事件)对应的数据描述信息
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_LOST					= 11510;//视频丢失
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_SHELTER				= 11511;//视频遮挡
	public static final int     ALARM_VIDEOABNORMALDETECTION_PICTURE_FREEZE				= 11512;//画面冻结
	public static final int     ALARM_VIDEOABNORMALDETECTION_LIGHT						= 11513;//过亮
	public static final int     ALARM_VIDEOABNORMALDETECTION_DARK						= 11514;//过暗
	public static final int     ALARM_VIDEOABNORMALDETECTION_SCENE_CHANGE				= 11515;//场景变化
	public static final int     ALARM_VIDEOABNORMALDETECTION_STRIPE						= 11516;//条纹检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_NOISE						= 11517;//噪声检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_COLOUR_CAST				= 11518;//偏色检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_BLUR					= 11519;//视频模糊
	public static final int     ALARM_VIDEOABNORMALDETECTION_CONTRAST_ABNORMAL			= 11520;//对比度异常检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_MOVE					= 11521;//视频运动
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_TWINKLE				= 11522;//视频闪烁
	public static final int     ALARM_VIDEOABNORMALDETECTION_VIDEO_COLOUR				= 11523;//视频颜色
	public static final int     ALARM_VIDEOABNORMALDETECTION_FOCAL						= 11524;//虚焦检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_OVEREXPOSURE				= 11525;//过曝检测
	public static final int     ALARM_VIDEOABNORMALDETECTION_END						= 11530;

	// 微云报警
	public static final int     ALARM_MCS_GENERAL_CAPACITY_LOW							= 11600;// 微云常规容量事件
	public static final int     ALARM_MCS_DATA_NODE_OFFLINE								= 11601;// 微云存储节点下线事件
	public static final int     ALARM_MCS_DISK_OFFLINE									= 11602;// 微云磁盘下线事件
	public static final int     ALARM_MCS_DISK_SLOW										= 11603;// 微云磁盘变慢事件
	public static final int     ALARM_MCS_DISK_BROKEN									= 11604;// 微云磁盘损坏事件
	public static final int     ALARM_MCS_DISK_UNKNOW_ERROR								= 11605;// 微云磁盘未知错误事件
	public static final int     ALARM_MCS_METADATA_SERVER_ABNORMAL						= 11606;// 微云元数据服务器异常事件
	public static final int     ALARM_MCS_CATALOG_SERVER_ABNORMAL						= 11607;// 微云目录服务器异常事件
	public static final int     ALARM_MCS_GENERAL_CAPACITY_RESUME						= 11608;// 微云常规容量恢复事件
	public static final int     ALARM_MCS_DATA_NODE_ONLINE								= 11609;// 微云存储节点上线事件
	public static final int     ALARM_MCS_DISK_ONLINE									= 11610;// 微云磁盘上线事件
	public static final int     ALARM_MCS_METADATA_SLAVE_ONLINE							= 11611;// 微云元数据备机上线事件
	public static final int     ALARM_MCS_CATALOG_SERVER_ONLINE							= 11612;// 微云目录服务器上线事件

	public static final int     ALARM_VITAL_SIGNS_ABNORMAL								= 11700;// 生命体征异常报警

	//新增围栏报警 占用范围：12000-12200
	public static final int     ALARM_DRIVERIN_FLYAREA									=12000;			//驶入飞行区
	public static final int     ALARM_DRIVEROUT_FLYAREA									=12001;			//驶出飞行区
	public static final int     ALARM_DRIVERIN_MANUALBANFLYAREA							=12002;			//驶入禁飞区（手动配置）
	public static final int     ALARM_DRIVEROUT_MANUALBANFLYAREA						=12003;			//驶出禁飞区（手动配置）
	public static final int     ALARM_DRIVERIN_FIXEDBANFLYAREA							=12004;			//驶入禁飞区（不可配置）
	public static final int     ALARM_DRIVEROUT_FIXEDBANFLYAREA							=12005;			//驶出禁飞区（不可配置）
	public static final int     ALARM_DRIVERIN_FiXEDLIMITFLY							=12006;			//驶入限制飞行（不可配置）
	public static final int     ALARM_DRIVEROUT_FiXEDLIMITFLY							=12007;			//驶出限制飞行（不可配置）

	public static final int     ALARM_ILLEGALIN_FLYAREA									=12008;			// 非法进入飞行区报警
	public static final int     ALARM_ILLEGALOUT_FLYAREA								=12009;			// 非法驶出飞行区报警
	public static final int     ALARM_ILLEGALIN_MANUALBANFLYAREA						=12010;			// 非法进入禁飞区（手动配置）
	public static final int     ALARM_ILLEGALOUT_MANUALBANFLYAREA						=12011;			// 非法驶出禁飞区（手动配置）
	public static final int     ALARM_ILLEGALIN_FIXEDBANFLYAREA							=12012;			// 非法进入禁飞区（不可配置）
	public static final int     ALARM_ILLEGALOUT_FIXEDBANFLYAREA						=12013;			// 非法驶出禁飞区（不可配置）
	public static final int     ALARM_ILLEGALIN_FiXEDLIMITFLY							=12014;			// 非法进入限制飞行
	public static final int     ALARM_ILLEGALOUT_FiXEDLIMITFLY							=12015;			// 非法出驶出限制飞行

	//新增消防主机报警 占用范围：12300-12400
	public static final int     ALARM_FIREENGINE_BEGIN									= 12300;
	public static final int     ALARM_FIREENGINE_FIRE									= 12301;			//火警报警
	public static final int     ALARM_FIREENGINE_EQUIPMENT_FAILURE						= 12302;			//设备故障报警
	public static final int     ALARM_FIREENGINE_HOST_FAILURE							= 12303;			//主电故障
	public static final int     ALARM_FIREENGINE_BACKUP_FAILURE							= 12304;			//备电故障
	public static final int     ALARM_FIREENGINE_HOST_UNDERVOLTAGE						= 12305;			//主电欠压
	public static final int     ALARM_FIREENGINE_BACKUP_UNDERVOLTAGE					= 12306;			//备电欠压
	public static final int     ALARM_FIREENGINE_BUS_FAILURE							= 12307;			//总线故障
	public static final int     ALARM_FIREENGINE_HOST_OFFLINE							= 12308;			//主机离线
	public static final int     ALARM_FIREENGINE_MANUAL									= 12309;			//手动报警
	public static final int     ALARM_FIREENGINE_TEMPERATUAL							= 12310;			//温度报警
	public static final int     ALARM_FIREENGINE_LOW_WATERPRESSURE						= 12311;			//水压过低
	public static final int     ALARM_FIREENGINE_HIGH_WATERPRESSURE						= 12312;			//水压过高
	public static final int     ALARM_FIREENGINE_DETECTOR_FAULT							= 12313;			//探测器故障

	public static final int     ALARM_FIREENGINE_END									= 12400;

	//新增消控报警 占用范围：12401-12500
	public static final int     ALARM_FIRECONTROL_BEGIN				   = 12401;
	public static final int     ALARM_FIRECONTROL_NOBODY			   = 12402;				 //无人报警
	public static final int     ALARM_FIRECONTROL_UPPER_LIMIT          = 12403;				 //超过上限报警
	public static final int     ALARM_FIRECONTROL_LOWER_LIMIT		   = 12404;				 //少于下限报警
	public static final int     ALARM_FIRECONTROL_NOT_MATCH            = 12405;				 //计划不符报警
	public static final int     ALARM_FIRECONTROL_FIRE            	   = 12406;				 //火警
	public static final int     ALARM_FIRECONTROL_HIGH_CURRENT		   = 12407;				 //电流过高
	public static final int     ALARM_FIRECONTROL_LOW_CURRENT          = 12408;				 //电流过低
	public static final int     ALARM_FIRECONTROL_VALVE_OPEN		   = 12409;				 //阀门被打开
	public static final int     ALARM_FIRECONTROL_FALL            	   = 12410;				 //倾倒
	public static final int     ALARM_FIRECONTROL_HIGH_TEMPERATURE     = 12411;				 //温度过高
	public static final int     ALARM_FIRECONTROL_LOW_TEMPERATURE	   = 12412;				 //温度过低
	public static final int     ALARM_FIRECONTROL_HIGH_VOLTAGE         = 12413;				 //电压过高
	public static final int     ALARM_FIRECONTROL_LOW_VOLTAGE		   = 12414;				 //电压过低
	public static final int     ALARM_FIRECONTROL_HIGH_WATERPRESSURE   = 12415;				 //水压过高
	public static final int     ALARM_FIRECONTROL_LOW_WATERPRESSURE    = 12416;				 //水压过低	
	public static final int     ALARM_FIRECONTROL_NORMAL				   = 12417;				 //正常
	public static final int     ALARM_FIRECONTROL_HIGH_LIQUID		   = 12418;				 //液位过高
	public static final int     ALARM_FIRECONTROL_LOW_LIQUID           = 12419;				 //液位过低
	public static final int     ALARM_FIRECONTROL_GAS		     	   = 12420;				 //燃气
	public static final int     ALARM_FIRECONTROL_START            	   = 12421;				 //启动
	public static final int     ALARM_FIRECONTROL_FEEDBACK				 = 12422;				 //反馈
	public static final int     ALARM_FIRECONTROL_MANUAL			   = 12423;				 //手动
	public static final int     ALARM_FIRECONTROL_STEAL          	   = 12424;				 //智能充电桩防盗报警
	public static final int     ALARM_FIRECONTROL_SMOKING		   	   = 12425;				 //智能充电桩烟雾报警
	public static final int     ALARM_FIRECONTROL_CHARGINGWIRE_PULLOUT = 12426;				 //智能充电桩充电线被拔报警
	public static final int     ALARM_FIRECONTROL_DEV_FAULT    		   = 12427;				 //智能充电桩桩点故障
	public static final int     ALARM_FIRECONTROL_CHRGINGPILE_FAULT    = 12428;				 //智能充电桩充电桩故障		
	public static final int     ALARM_FIRECONTROL_POWER_OVERLOAED      = 12429;				 //智能充电桩功率过载
	public static final int     ALARM_FIRECONTROL_TEMPERATURE_UNNORMAL = 12430;				 //智能充电桩温度报警
	public static final int     ALARM_FIRECONTROL_OFFLINE_CARD 		   = 12431;				 //智能充电桩离线卡异常
	public static final int     ALARM_FIRECONTROL_REFUND    		   = 12432;				 //智能充电桩充电订单退款	
	public static final int     ALARM_FIRECONTROL_CHARGER_FAULT		   = 12433;				 //智能充电桩充电器故障
	public static final int     ALARM_FIRECONTROL_BATTERY_FAULT 	   = 12434;				 //智能充电桩电池故障
	public static final int     ALARM_FIRECONTROL_DISMANTLE    		   = 12435;				 //设备被拆除
	public static final int     ALARM_FIRECONTROL_HAPPEN				   = 12436;				 //告警	

	public static final int     ALARM_FIRECONTROL_END				   = 12500;

	//新增java层通过MQ发给ADS报警 占用范围：12700-12999
	public static final int     ALARM_WEBALARM_BEGIN								= 12700;
	public static final int     ALARM_WEBALARM_PERSON_CHANGED						= 12701;			//人员信息变动报警
	public static final int     ALARM_WEBALARM_INVALID_CARD							= 12702;			//刷卡无效报警
	public static final int     ALARM_WEBALARM_DOOR_STAYOPEN						= 12703;			//门保持常开报警
	public static final int     ALARM_DOOR_NOTCLOSED_LONGTIME						= 12704;			//门长时间未关报警
	public static final int     ALARM_LORA_DOOR_NOTCLOSED							= 12705;			//单元门未关
	public static final int     ALARM_LORA_LOWPOWER									= 12706;			//低电压
	public static final int     ALARM_LORA_WELLCOVER_MOVED							= 12707;			//井盖移动
	public static final int     ALARM_LORA_RUBBISH_FULL								= 12708;			//垃圾满
	public static final int     ALARM_LORA_DEVICE_FAULT								= 12709;			//设备故障
	public static final int     ALARM_LORA_SEWAGE_OVERLIMIT							= 12710;			//污水超限
	public static final int     ALARM_LORA_HIGH_TEMP_HUMIDITY						= 12711;			//温湿度过高
	public static final int     ALARM_LORA_LOW_TEMP_HUMIDITY						= 12712;			//温湿度过低
	public static final int     ALARM_LORA_VALUE_FAULT								= 12713;			//阀门故障
	public static final int     ALARM_PERSON_IDENTIFICATION_NOTSAME					= 12714;			//人证不一致报警
	public static final int     ALARM_WEBALARM_END									= 12999;

	public static final int     ALARM_TYRE_PRESSURE_ABNORMAL						=13000;				 //胎压异常
	public static final int     ALARM_BATTERY_POWER_EVENT							=13001;				 //电池电量定时通知事件
	public static final int     ALARM_VEHICLE_ACC									=13002;				 //车辆ACC报警事件
	public static final int     ALARM_RETROGRADATION_DETECT							=13003;				 //逆行检测
	public static final int     ALARM_TARGET_REMOVE_DETECT							=13004;				 //目标移除检测
	public static final int     ALARM_GPS_MODULE_LOST								=13005;				 //GPS异常
	public static final int     ALARM_WIFI_MODULE_LOST								=13006;				 //WIFI异常
	public static final int     ALARM_3G4G_MODULE_LOST								=13007;				 //3G/4G异常
	public static final int     ALARM_POLICE_CHECK									=13008;				 //单兵设备警员签到报警
	public static final int     ALARM_WIFI_MODULE_OFFLINE							=13009;				 //WIFI模块离线
	public static final int     ALARM_CHASSIS_INTRUSION								=13010;				 //报警柱防拆报警
	public static final int     ALARM_WIFI_MODULE_ONLINE							=13011;				 //WIFI模块在线
	public static final int     ALARM_DOOR_CONTROL									=13012;				 //报警输出联动开门事件
	public static final int     ALARM_DOOR_NOTCLOSED_FORLONGTIME					=13013;				 //门超长时间未关报警事件
	public static final int     ALARM_DOOR_ACCESS_CTL_STATUS						=13014;				 //门禁状态事件
	public static final int     ALARM_RECHARGE_BUSINESS_QUERY						=13015;				 //充值机查询事件
	public static final int     ALARM_RECHARGE_BUSINESS_RECHARGE					=13016;				 //充值机充值事件
	public static final int     ALARM_CUSTOMER_BLACKLIST							=13017;				 //访客黑名单事件
	public static final int     ALARM_VEHICLE_BRAKE_EX								=13018;				   //刹车 走外部报警

	//海外门禁报警新增:13100-13500
	public static final int     ALARM_DOOR_UNAUTHORIZE								=13100;					//未授权(无效刷卡)
	public static final int     ALARM_DOOR_LOST										=13101;					//卡挂失或注销(无效刷卡)
	public static final int     ALARM_DOOR_NO_PERMISSION							=13102;					//没有该门权限(无效刷卡)	
	public static final int     ALARM_DOOR_ERR_MODE									=13103;					//开门模式错误(无效刷卡)
	public static final int     ALARM_DOOR_ERR_VALIDITY								=13104;					//有效期错误(无效刷卡)
	public static final int     ALARM_DOOR_ERR_REPEATENTERROUTE						=13105;					//防反潜模式(无效刷卡)
	public static final int     ALARM_DOOR_FORCE_NOTOPEN							=13106;					//胁迫报警未打开(无效刷卡)
	public static final int     ALARM_DOOR_ALWAYS_CLOSED							=13107;					//门常闭状态(无效刷卡)
	public static final int     ALARM_DOOR_AB_CLOKED								=13108;					//AB互锁状态(无效刷卡)
	public static final int     ALARM_DOOR_PATROL_CARD								=13109;					//巡逻卡(无效刷卡)
	public static final int     ALARM_DOOR_IN_BROKEN								=13110;					//设备处于闯入报警状态(无效刷卡)
	public static final int     ALARM_DOOR_ERR_TIMESECTION							=13111;					//时间段错误(无效刷卡)
	public static final int     ALARM_DOOR_ERR_HOLIDAYTIMESECTION					=13112;					//假期内开门时间段错误(无效刷卡)
	public static final int     ALARM_DOOR_NEED_FIRSTCARD_PERMISSION				=13113;					//需要先验证有首卡权限的卡片(无效刷卡)
	public static final int     ALARM_DOOR_ERR_CARD_PASSWORD						=13114;					//卡片正确;输入密码错误(无效刷卡)
	public static final int     ALARM_DOOR_INPUTCARDPWD_TIMEOUT						=13115;					//卡片正确;输入密码超时(无效刷卡)
	public static final int     ALARM_DOOR_ERR_CARD_FINGERPRINT						=13116;					//卡片正确;输入指纹错误(无效刷卡)
	public static final int     ALARM_DOOR_INPUTCARDFINGERPRINT_TIMEOUT				=13117;					//卡片正确;输入指纹超时(无效刷卡)
	public static final int     ALARM_DOOR_ERR_FINGERPRINT_PASSWORD					=13118;					//指纹正确;输入密码错误(无效刷卡)
	public static final int     ALARM_DOOR_INPUTFINGERPRINTPWD_TIMEOUT				=13119;					//指纹正确;输入密码超时(无效刷卡)
	public static final int     ALARM_DOOR_ERR_GROUP								=13120;					//组合开门顺序错误(无效刷卡)
	public static final int     ALARM_DOOR_GROUPN_NEED_VERIFY						=13121;					//组合开门需要继续验证(无效刷卡)
	public static final int     ALARM_DOOR_CONSOLE_UNAUTHORIZE						=13122;					//验证通过;控制台未授权(无效刷卡)
	public static final int     ALARM_DOOR_CARD_PWD_OPENDOOR						=13123;					//卡加密码开门
	public static final int     ALARM_DOOR_CARD_FINGERPRINT_OPENDOOR				=13124;					//卡加指纹开门
	public static final int     ALARM_DOOR_REMOTE_CONFIRM							=13125;					//远程验证
	public static final int     ALARM_DOOR_GROUP_OPENDOOR_CONFIRM					=13126;					//组合开门验证通过
	public static final int     ALARM_DOOR_GROUP_MANY_DOOR_CONFIRM					=13127;					//多卡组合

	public static final int     ALARM_ATTENDANCESTATE_SIGNIN						=13130;
	public static final int     ALARM_ATTENDANCESTATE_SIGNOUT						=13131;
	public static final int     ALARM_ATTENDANCESTATE_WORK_OVERTIME_SIGNIN			=13132;
	public static final int     ALARM_ATTENDANCESTATE_WORK_OVERTIME_SIGNOUT			=13133;
	public static final int     ALARM_ATTENDANCESTATE_GOOUT							=13134;
	public static final int     ALARM_ATTENDANCESTATE_GOOUT_AND_RETRUN				=13135;

	public static final int     ALARM_WORK_CHECK_IN									=13200;
	public static final int     ALARM_WORK_CHECK_OUT								=13201;
	public static final int     ALARM_OVERTIME_CHECK_IN								=13202;
	public static final int     ALARM_OVERTIME_CHECK_OUT							=13203;
	public static final int     ALARM_GO_OUT										=13204;
	public static final int     ALARM_GO_BACK										=13205;
	public static final int     ALARM_CUSTOM_PASSWORD_OPEN							=13300;					// 个性化密码开门
	public static final int     ALARM_RECORD_DOWNLOADORPLAYBACK_END					=13501;					// 录像文件下载、回放结束
	public static final int     ALARM_TRAFFIC_JAM									=13502;					// 交通拥堵事件

	public static final int     ALARM_CUSTOMER_STATISTICIAN_OVERFLOW				=13600;					// 客流统计报警 - 流量超限报警
	public static final int     ALARM_CUSTOMER_STATISTICIAN_END						=13700;					// 客流统计报警 - 结束
	// 综合能源产品线 报警 开始
	// 14000 - 15000
	// 综合能源产品线 报警 结束

	//新增的车载报警类型  预留500个报警 15001 - 15500  其它请勿占用
	public static final int     ALARM_BUS_LANE_DEPARTURE_WARNNING					= 15001;				//车道偏离
	public static final int     ALARM_BUS_FORWARD_COLLISION_WARNNING				= 15002;				//前向碰撞预警
	public static final int     ALARM_VEHICLE_STATE_START				 			= 15003;				//车辆状态上报 开始运动
	public static final int     ALARM_VEHICLE_STATE_BEYOND_10						= 15004;				//车辆状态上报 车速大于10km/h
	public static final int     ALARM_VEHICLE_STATE_STOP 							= 15005;				//车辆状态上报 停止
	public static final int     ALARM_SERVICE_DISTANCE_BELOW_2000					= 15006;				// 车辆保养距离小于2000km报警

	public static final int     ALARM_NATIONAL_GRID_ACCIDENT						= 15345;				//-电网事故  
	public static final int     ALARM_RUN_ABNORMAL									= 15346;				//-运行异常  
	public static final int     ALARM_DEVICE_ACTION									= 15347;				//-设备动作
	public static final int     ALARM_REMOTE_LETTER_POSITIONING						= 15348;				//-遥信定位  									
	public static final int     ALARM_FAR_LETTER_CROSSLINE							= 15349;				//-遥测越限  
	public static final int     ALARM_NOTIFY_INFORMATION			 				= 15350;				//-信息告知
//新开辟的IVS智能报警 预留1500个 范围15500 - 17000
	public static final int     ALARM_IVS_ALARM_NEW_BEGIN						=15500;							// 智能设备报警类型在dhnetsdk.h基础上+15000（DMS服务中添加）;因为是从590+15000开始的

	public static final int     ALARM_IVS_YOUCHU_BIGDOOR						=0x00000001 + 15500;			// 邮储银行-智能设备-营业大门开门违规
	public static final int     ALARM_IVS_YOUCHU_DEVROOM						=0x00000002 + 15500;			// 邮储银行-智能设备-设备间巡查违规
	public static final int     ALARM_IVS_YOUCHU_ADDMONEY						=0x00000003 + 15500;			// 邮储银行-智能设备-加钞间违规
	public static final int     ALARM_IVS_YOUCHU_LINKDOOR						=0x00000004 + 15500;			// 邮储银行-智能设备-联动门开门违规
	public static final int     ALARM_IVS_YOUCHU_MONEYAREA						=0x00000005 + 15500;			// 邮储银行-智能设备-现金区尾款箱交接违规
	public static final int     ALARM_IVS_YOUCHU_CONNECTAREA					=0x00000006 + 15500;			// 邮储银行-智能设备-交接区未在指定区域交接违规
	public static final int     ALARM_IVS_YOUCHU_CONNECTAREA_MANNUM				=0x00000007 + 15500;			// 邮储银行-智能设备-交接区交接人数违规
	public static final int     ALARM_IVS_YOUCHU_COUPEAREA						=0x00000008 + 15500;			// 邮储银行-智能设备-双人区过夜现金库开门违规
	public static final int     ALARM_IVS_YOUCHU_OPENAREA_OPENDOOR				=0x00000009 + 15500;			// 邮储银行-智能设备-开门区过夜现金库开门输入密码违规
	public static final int     ALARM_IVS_YOUCHU_STATIONAWAY					=0x0000000A + 15500;			// 邮储银行-智能设备-脱岗违规(带人数上报)

	public static final int     ALARM_IVS_FLOW_BUSINESS							=0x0000024E + 15000;			// 流动摊贩事件 

	public static final int     DPSDK_CORE_ALARM_IVS_CITY_MOTORPARKING			= 0X0000024F + 15000;			// 城市机动车违停事件 15591
	public static final int     DPSDK_CORE_ALARM_IVS_CITY_NONMOTORPARKING		= 0X00000250 + 15000;			// 城市机非动车违停事件 15592
	public static final int     DPSDK_CORE_ALARM_IVS_LANEDEPARTURE_WARNNING		= 0X00000251 + 15000;			// 车道偏移预警 15593 
	public static final int     DPSDK_CORE_ALARM_IVS_FORWARDCOLLISION_WARNNING	= 0X00000252 + 15000;			// 前向碰撞预警 15594
	public static final int     DPSDK_CORE_ALARM_IVS_MATERIALSSTAY				= 0X00000253 + 15000;			// 物料堆放事件 15595
	public static final int     ALARM_CORE_ALARM_IVS_FLOATINGOBJECT_DETECTION	= 0X00000257 + 15000;			// 漂浮物检测事件 15599
	public static final int     ALARM_IVS_PHONECALL_DETECT						= 0x0000025A + 15000;			// 打电话报警
	public static final int     ALARM_IVS_SMOKING_DETECT						= 0x0000025B + 15000;			// 吸烟报警
	public static final int     ALARM_IVS_RADAR_SPEED_LIMIT_ALARM				= 0x0000025C + 15000;			// 雷达限速报警事件
	public static final int     ALARM_IVS_WATER_LEVEL_DETECTION					= 0x0000025D + 15000;			// 水位检测事件
	public static final int     ALARM_IVS_GARBAGE_EXPOSURE_NEW					= 0x0000025F + 15000;			// 垃圾暴露检测事件 
	public static final int     ALARM_IVS_DUSTBIN_OVER_FLOW_NEW					= 0x00000260 + 15000;			// 垃圾桶满溢检测事件
	public static final int     ALARM_IVS_DOOR_FRONT_DIRTY_NEW					= 0x00000261 + 15000;			// 门前脏乱检测事件
	public static final int     ALARM_IVS_QUEUESTAY_DETECTION					= 0X00000262 + 15000;			// 排队滞留时间报警事件
	public static final int     ALARM_IVS_QUEUENUM_DETECTION					= 0X00000263 + 15000;			// 排队人数异常报警事件
	public static final int     DPSDK_CORE_ALARM_IVS_HOTSPOT_WARNING			= 0X00000268 + 15000;			// 热点异常智能报警 15616 国网甘肃省电力公司智慧消防项目定制
	public static final int     ALARM_IVS_VEHICLE_DISTANCE_NEAR					= 0x0000026B + 15000;			// 安全驾驶车距过近报警事件
	public static final int     ALARM_IVS_TRAFFIC_DRIVER_ABNORMAL				= 0x0000026C + 15000;			// 驾驶员异常报警事件
	public static final int     ALARM_IVS_TRAFFIC_DRIVER_CHANGE					= 0x0000026D + 15000;			// 驾驶员变更报警事件
	public static final int     ALARM_IVS_WORKCLOTHES_DETECT					= 0x0000026E + 15000;			// 工装(安全帽/工作服等)检测事件
	public static final int     ALARM_IVS_SECURITYGATE_PERSONALARM				= 0x0000026F + 15000;			// 安检门人员报警
	public static final int     ALARM_IVS_STAY_ALONE_DETECTION					= 0x00000270 + 15000;			//	单人独处事件
	public static final int     ALARM_IVS_INFRAREDBLOCK							= 0x00000275 + 15000;			// 红外阻断事件
	public static final int     ALARM_IVS_PANORAMA_SHOT							= 0x00000278 + 15000;			// 全景抓拍事件
	public static final int     ALARM_IVS_TRAMCARSECTIONS_DETECTION				= 0x0000027E + 15000;			// 矿车超挂报警事件
	public static final int     ALARM_IVS_SHOP_WINDOW_POST						= 15644;						// 橱窗张贴事件
	public static final int     ALARM_IVS_SHOP_SIGN_ABNORMAL					= 15645;						// 店招异常事件
	public static final int     ALARM_IVS_ANIMAL_DETECTION						= 15646;						// 动物检测事件
	//ALARM_IVS_DREGS_UNCOVERED			  =		15647;         				// 渣土车未遮盖载货检测
	public static final int     ALARM_IVS_BREED_DETECTION						= 15649;						// 智慧养殖检测事件
	public static final int     ALARM_IVS_MAN_CAR_COEXISTANCE					= 0x0000028C + 15000;			// 人车共存事件
	public static final int     ALARM_IVS_HIGH_TOSS_DETECT						= 0x0000028D + 15000;			// 高空抛物检测事件
	public static final int     ALARM_IVS_ANATOMY_TEMP_DETECT					= 0x00000303 + 15000;			// 人体温智能检测事件
	public static final int     ALARM_IVS_BREAK_BUILDING_DETECT					= 0x0000030F + 15000;			// 违章建筑检测事件
	public static final int     ALARM_IVS_PIG_TEMP_ARRAY						= 16992;						//猪体温检测
	public static final int     ALARM_IVS_TAKE_OFF_DUTY							= 16993;						//民警脱岗
	public static final int     ALARM_IVS_LOW_WATER_LEVEL						= 16994;						//水位报警 低水位
	public static final int     ALARM_IVS_HIGH_WATER_LEVEL						= 16995;						//水位报警 高水位
	public static final int     ALARM_IVS_UNMANNED_INTERROGATE					= 16996;						// 无人审讯
	public static final int     ALARM_IVS_SIGNAL_INTERROGATE					= 16997;						// 单人审讯  
	public static final int     ALARM_IVS_TYPE_CROWD_DENSITY					= 16998;						//区域人群密度报警   报警拆分; 为不影响原报警的 300+netSDK值;的代码结构; 所以报警从后面来
	public static final int     ALARM_IVS_TYPE_NUMBER_EXCEED					= 16999;						//全域人群密度报警
	public static final int     ALARM_IVS_ALARM_NEW_END							= 17000;						// 智能设备报警类型在dhnetsdk.h基础上+15500（DMS服务中添加）
	public static final int     ALARM_CONGESTION_DETECTION_JAM					= 17001;						// 道路场景车辆拥堵报警
	public static final int     ALARM_CONGESTION_DETECTION_QUEUE				= 17002;						// 道路场景车辆排队报警
	public static final int     ALARM_VEHICLELIMIT_DETECTION					= 17003;						// 停车场场景下停车车辆上限报警
	public static final int     ALARM_IVS_ANATOMY_NOT_UPTEMP					= 17004;						// 人体温智能检测未超高温事件
	public static final int     ALARM_IVS_STATE_NOMASK							= 17005;						// 人体未戴口罩事件
	public static final int     ALARM_STRANGER_ALARM_DMS						= 17006;						//DMS陌生人报警
	public static final int     ALARM_PEDESTRIAN_COLLISION						= 17007;						//行人碰撞报警
	public static final int     ALARM_FREQUENT_LANE_CHANGE						= 17008;						//频繁变道报警
	public static final int     ALARM_ROAD_SIGN_OVERRUN							= 17009;						//道路标识超限报警

	//组合报警 预留100个 范围18000 - 18100
	public static final int     ALARM_GROUP_ALARM_BEGIN				= 18000;
	public static final int     ALARM_GROUP_IPC_PEOPLENUM_ONE		= 18001;			//组合报警-单人状态
	public static final int     ALARM_GROUP_ARM_DISABLE				= 18002;			//组合报警-撤防状态
	public static final int     ALARM_GROUP_ALARM_END				= 18100;

	public static final int     ALARM_PERSON_STAY					= 18101;			//人员滞留
	public static final int     ALARM_ZONE_STAY						= 18102;			//区域滞留
	public static final int     ALARM_SINGLE_STAY					= 18103;			//单个人员滞留
	public static final int     ALARM_VISITOR_STAY					= 18104;			//访客滞留
	public static final int     ALARM_ABNORMAL_PERSON				= 18105;			//异常人员
	public static final int     ALARM_ILLEGAL_ENTER					= 18106;			//非法进入
	public static final int     ALARM_FIREWARNING					= 18107;			//热成像着火点
	public static final int     ALARM_ALARM_FIREWARNING_INFO		= 18108;			//热成像火情报警信息报警
	//报警主机异常布撤防 预留100个 范围18200 - 18300
	public static final int     DEV_MSG_TYPE_DELAYED_ARM			=18200;				//延时布防


	// 增加的一些普通报警的报警值 19000~20000
	public static final int     ALARM_CROWD_DETECTION								=19000;				  //人群密度报警
	public static final int     ALARM_VEHICLE_LOAD									=19001;				  //车载NVR载重数据上传事件
	public static final int     ALARM_VEHICLE_OILLEVEL								=19002;				 //车载NVR油位数据上传事件
	public static final int     ALARM_IMSI_CHECK									=19003;				  //IMSI校验事件
	public static final int     ALARM_BLIND_ALARM									=19004;				  //进入盲区报警
	public static final int     ALARM_DISK_CHECK									=19005;				  //磁盘检测
	public static final int     ALARM_NAS_SERVER_STATE								=19006;				 //共享服务
	public static final int     ALARM_VOLUME_GROUP_FAULT							=19007;				 //存储池异常
	public static final int     ALARM_ANALOGALARM_EVENT_HARMFUL_GAS					=19008;				  //有毒气体
	public static final int     ALARM_BUS_EXPORT_SITE								=19009;				  //离站事件
	public static final int     ALARM_SMARTMOTION_HUMAN								=19010;				  //智能人动检
	public static final int     ALARM_SMARTMOTION_VEHICLE							=19011;				  //智能车动检
	public static final int     ALARM_SMARTMOTION_HUMAN_AND_VEHICLE					=19012;				  //智能人&&车动检
	public static final int     ALARM_COAXIAL_ALARM_LOCAL							=19013;				 //同轴开关量报警事件; 即xvr接cvi CoaxialAlarmLocal
	public static final int     ALARM_MOTION_RECORD_ALARM_NORMAL					=19014;				  //动检录像状态正常
	public static final int     ALARM_MOTION_RECORD_ALARM_ABNORMAL					=19015;				  //动检录像状态异常
	public static final int     ALARM_LAW_ENFORCEMENT_INFO_ALARM					=19016;				  //实时上报执法终端设备信息事件 gps信息并携带执法人id
	public static final int     ALARM_BOX						 					=19017;  			 //同轴开关量报警事件; 即nvr+报警盒子
	public static final int     ALARM_ENGINE_FAILURE_STATUS							=19018;				 //发动机故障报警
	public static final int     ALARM_LEAVE_AND_FIRE								=19019;				 //离岗火情报警
	public static final int     ALARM_VEHICLE_ACC_ON								=19020;				 //ACC ON报警
	public static final int     ALARM_VEHICLE_ACC_OFF								=19021;				 //ACC OFF报警
	public static final int     ALARM_CONGESTION_DETECTION							=19022;				 //道路场景车辆拥堵报警事件
	public static final int     ALARM_VEHICLELIMIT_DETECTION_NORMAL					=19023;				 //停车场场景下停车车辆上限报警
	public static final int     ALARM_STORAGE_HEALTH_ABNORMAL						=19024;				 //硬盘健康报警事件
	public static final int     ALARM_CLEAR_CAR						 				=19025;				 //清车报警
	public static final int     ALARM_NOT_CLEAR_CAR									=19026;				 //未清车报警
	public static final int     ALARM_PERPON_ILLEGAL_ENTER_AREA						=19027;				//人员非法入区
	public static final int     ALARM_PERPON_LEAVE_POSITION							=19028;				 //人员离位报警
	public static final int     ALARM_HIGH_TOSS_DETECT								=19029;				 //高空抛物检测事件
	public static final int     ALARM_SWIPCARD_FAILURE_FINGER_PRINT					=19030;				 //指纹一体机刷卡器故障
	public static final int     ALARM_SWIPCARD_FAILURE_CLEARCAR						=19031;				 //清车刷卡器故障
	public static final int     ALARM_TRAFFIC_LIGHT_STATE							=19032;				 //交通灯状态报警

	// 增加的一些普通报警的报警值 19000~20000

	//机器人报警  预留200个报警 20001 - 20200  其它请勿占用
	public static final int     RC_ALARM_COLLISION									= 20001;			 //碰撞事件
	public static final int     RC_ALARM_ROADBLOCKED								= 20002;			 //遇障
	public static final int     RC_ALARM_FAULT										= 20003;			 //机器人本地错误
	public static final int     RC_ALARM_BRAKE										= 20004;			 //紧急刹车
	public static final int     RC_ALARM_CHARGING_ERROR								= 20005;			 //充电错误
	public static final int     RC_ALARM_DERAILMENT									= 20006;			 //脱轨
	public static final int     RC_ALARM_PREVENT_FALLING							= 20007;			 //防跌落

	//MCD 加报警  22000~23000
	public static final int     ALARM_CALL_OUT										= 22000;				 //呼叫按钮报警
	public static final int     ALARM_CALL_THE_POLICE								= 22001;				 //报警按钮报警            

	//格力事件订阅
	public static final int     ALARM_ABNORMAL_RUN									= 23001;			//停线异常
	public static final int     ALARM_ABNORMAL_STOP									= 23002;			//点停异常

	// 车载ADAS报警，特殊定制，新增报警类型加以区分
	public static final int     ALARM_EM_ADAS_BEGIN									= 49700;			// begin
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DISTANCE_NEAR				= 49701;			// 车距过近
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_FORWARD_COLLISION			= 49702;			// 碰撞报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_LEFTLANE_DEPARTURE		= 49703;			// 左车道线报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_RIGHTLANE_DEPARTURE		= 49704;			// 右车道线报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_SUDDENLY_ACCELER			= 49705;			// 急加速
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_TRAFFIC_ADAS_BRAKES		= 49706;			// 急刹车
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_SHARP_TURN_LEFT			= 49707;			// 急左转弯
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_SHARP_TURN_RIGHT			= 49708;			// 急右转弯
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_TURN_OVER					= 49709;			// 侧翻
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_FRONT_SNAP				= 49710;			// 车前方抓拍
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_SNAP				= 49711;			// 驾驶员抓拍
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_LEAVE_POST			= 49712;			// 驾驶员离岗
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_MILD_TIRED				= 49713;			// 疲劳一级报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_TIRED						= 49714;			// 疲劳二级报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_VIDEO_BLIND				= 49715;			// 摄像头遮挡
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_CALLING			= 49716;			// 打电话
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_YAWN				= 49717;			// 打哈欠
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_SMOKING			= 49718;			// 抽烟
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_DRIVER_LOOKAROUND			= 49719;			// 左顾右盼
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_OVER_SPEED				= 49720;			// 超速
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_SUBSTITUTION				= 49721;			// 换人报警
	public static final int     ALARM_EM_ADAS_TIRED_DRIVE_ABNORMAL					= 49722;			// 驾驶员异常报警
	public static final int     ALARM_EM_ADAS_END									= 49799;			// end
	// 车载ADAS报警end
	public static final int     ALARM_SOUND_ALARM									= 49800;			// 有声报警
	public static final int     ALARM_HARDWARE_FAILURE								= 49801;			// 硬件故障报警
	public static final int     ALARM_AUTHENTICATION_EXCESS							= 49802;			// 认证超限报警
	public static final int     ALARM_ILLegal_CONSTRUCTION							= 49803;			// 违章建筑
	public static final int     ALARM_OPEN_BURNING									= 49804;			// 露天焚烧

	// 交通事件检测类型									//对应EnumCarRule中违章类型
	public static final int     ALARM_TRAFFIC_ACTION_BENGIN							= 50000;
	public static final int     ALARM_TRAFFIC_ACTION_PARKING						= 50001;			// 交通事件检测停车
	public static final int     ALARM_TRAFFIC_ACTION_PEDESTRAIN						= 50002;			// 交通事件检测行人
	public static final int     ALARM_TRAFFIC_ACTION_CONVERSE_RUN					= 50003;			// 交通事件检测逆行
	public static final int     ALARM_TRAFFIC_ACTION_JAM							= 50004;			// 交通事件检测拥堵
	public static final int     ALARM_TRAFFIC_ACTION_OMISSION						= 50005;			// 交通事件检测遗落物
	public static final int     ALARM_TRAFFIC_ACTION_FOG							= 50006;			// 交通事件检测烟雾
	public static final int     ALARM_TRAFFIC_ACTION_BLAZE 							= 50007;			// 交通事件检测火焰
	public static final int     ALARM_TRAFFIC_ACTION_SPEED							= 50008;			// 交通事件检测超速
	public static final int     ALARM_TRAFFIC_ACTION_LOWSPEED						= 50009;			// 交通事件检测低速
	public static final int     ALARM_TRAFFIC_ACTION_ONLINE							= 50010;			// 交通事件检测压线
	public static final int     ALARM_TRAFFIC_ACTION_SUDDEN_DECELE_RATION			= 50011;			// 交通事件检测突然减速
	public static final int     ALARM_TRAFFIC_ACTION_PASSERBY						= 50012;			// 交通事件检测行人穿越
	public static final int     ALARM_TRAFFIC_ACTION_BACK							= 50013;			// 交通事件检测倒车
	public static final int     ALARM_TRAFFIC_ACTION_RUN_FORBIDDEN_AREA				= 50014;			// 交通事件检测禁行区行驶
	public static final int     ALARM_TRAFFIC_ACTION_TRAIL_ANOMALY					= 50015;			// 交通事件检测轨迹异常
	public static final int     ALARM_TRAFFIC_ACTION_END							= 59999;
	public static final int     ALARM_VEHICLE_IMPORT_SITE							= 600000;			//车载进站报警
	public static final int     ALARM_VEHICLE_EXPORT_SITE							= 600001;			//车载离站报警
	public static final int     ALARM_VEHICLE_CIRCUIT_SHIFT							= 600002;			//车载路线偏移报警
	public static final int     ALARM_VEHICLE_ROUTE_OVERTIME						= 600003;			//车载路线超时报警
	public static final int     ALARM_BULLDOZE_FORCE								= 600004;			//强拆报警
	public static final int     ALARM_VAILID_FACE_OPEN								= 600005;			//合法RenLian开门报警
	public static final int     ALARM_INVAILID_FACE_OPEN							= 600006;			//非法RenLian开门报警
	public static final int     ALARM_CARD_AND_FACE_TIMEOUT							= 600011;			//卡和RenLian超时报警
	public static final int     ALARM_CARD_AND_FACE_ERROR							= 600012;			//卡和RenLian错误报警
	public static final int     ALARM_CARD_AND_FACE_OPEN							= 600013;			//卡和RenLian正确开门报警

	public static final int     ALARM_DOOR_FINGERPRINT_AND_PWD_OPENDOOR				=700000;				//指纹+密码开锁
	public static final int     ALARM_DOOR_FINGERPRINT_AND_FACE_OPENDOOR			=700001;				//指纹+RenLian开锁
	public static final int     ALARM_DOOR_FACE_AND_PWD_OPENDOOR					=700002;				//RenLian+密码开锁
	public static final int     ALARM_DOOR_CARD_AND_FINGERPRINT_AND_PWD_OPENDOOR	=700003;				//刷卡+指纹+密码开锁
	public static final int     ALARM_DOOR_CARD_AND_FINGERPRINT_AND_FACE_OPENDOOR	=700004;				//刷卡+指纹+RenLian开锁
	public static final int     ALARM_DOOR_FINGERPRINT_AND_FACE_AND_PWD_OPENDOOR	=700005;				//指纹+RenLian+密码
	public static final int     ALARM_DOOR_CARD_AND_FACE_AND_PWD_OPENDOOR			=700006;				//刷卡+RenLian+密码开锁
	public static final int     ALARM_DOOR_CARD_AND_FINGERPRINT_AND_FACE_AND_PWD_OPENDOOR			=700007;				//卡+指纹+RenLian+密码开锁
	//---预留到701000
	// 智能电源报警
	public static final int     ALARM_POWERSRC_SHORT_CIRCUIT						= 701001; //通道短路故障
	public static final int     ALARM_POWERSRC_MAIN_POWER_OUTPUT					= 701002; //主电源输出故障报警
	public static final int     ALARM_POWERSRC_MAINS_INPUT							= 701003; //市电输入故障报警
	public static final int     ALARM_POWERSRC_FAN									= 701004; //风扇故障报警	
	public static final int     ALARM_POWERSRC_TEMPERATURE							= 701005; // 温度告警
	public static final int     ALARM_POWERSRC_VOLTAGE								= 701006; // 电压告警
	public static final int     ALARM_POWERSRC_OFFLINE								= 701007; // 电源设备离线告警 
	public static final int     ALARM_HEARTRATE_TOOHIGHT 							= 701008; //心率过高报警
	public static final int     ALARM_HEARTRATE_TOOLOW 								= 701009; //心率过低报警
	public static final int     ALARM_BREATHRATE_TOOHIGHT 							= 701010; //呼吸过高报警
	public static final int     ALARM_BREATHRATE_TOOLOW 							= 701011; //呼吸过低报警
	public static final int     ALARM_NOTONBED_TOOLONG 								= 701012; //离床报警
	public static final int     ALARM_ONBED_TOOLONG									= 701013; //在床报警
	// 701014-701019门禁联动健康码使用
	public static final int     ALARM_NO_GAUZE_MASK									= 701014; //未带口罩
	public static final int     ALARM_HEALTH_CODE_FAILED							= 701015; //健康码获取失败
	public static final int     ALARM_YELLOW_CODE_NO_ACCESS							= 701016; //黄色健康码禁止通行
	public static final int     ALARM_RED_CODE_NO_ACCESS							= 701017; //红色健康码禁止通行
	public static final int     ALARM_HEALTH_CODE_INVALID							= 701018; //健康码无效
	public static final int     ALARM_GREEN_CODE_TO_ACCESS							= 701019; //绿码验证通过
	public static final int     ALARM_MAN_OVER_TEMPERATURE							= 701020; //人员温度过高
	public static final int     ALARM_STAY_ALARM									= 701021; //滞留
	public static final int     ALARM_CLIMB_OVER									= 701022; //翻越
	public static final int     ALARM_FOLLOWING										= 701023; //尾随
	public static final int     ALARM_INVERSE_BREAK_IN								= 701024; //反向闯入

	//新接带图片门禁
	//701500-702000
	public static final int     ALARM_FACE_DOOR_TYPE_ENABLEUSERCARD 								=701500; //合法卡
	public static final int     ALARM_FACE_DOOR_TYPE_ENABLEUSERCARD_VALID_FACE_OPEN 				=701501; //合法RenLian
	public static final int     ALARM_FACE_DOOR_TYPE_VALID_FINGERPRINT 								=701502; //合法指纹
	public static final int     ALARM_FACE_DOOR_TYPE_VALID_PASSWORD 								=701503; //合法密码

	public static final int     ALARM_FACE_DOOR_TYPE_NOCARD 										=701504; //非法卡
	public static final int     ALARM_FACE_DOOR_TYPE_ENABLEUSERCARD_INVALID_FACE 					=701505; //非法RenLian
	public static final int     ALARM_FACE_DOOR_TYPE_INVALID_FINGERPRINT 							=701506; //非法指纹
	public static final int     ALARM_FACE_DOOR_TYPE_INVALID_PASSWORD 								=701507; //非法密码

	public static final int     ALARM_FACE_DOOR_REMOTE_CONFIRM_OPEN									=701508; //远程验证		
};
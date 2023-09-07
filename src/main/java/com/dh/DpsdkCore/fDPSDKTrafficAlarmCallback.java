package com.dh.DpsdkCore;

/** 车辆违章上报回调函数定义
    @param IN                                    nPDLLHandle              SDK句柄
    @param IN                                    pUserParam               用户指针参数,由对应的DPSDK_SetxxxxCallBack传入
	@param IN szCameraId,			// 通道ID
	@param IN nPtsIp,				// pts内网
	@param IN nPtsIpy,             // pts外网
	@param IN nPicPort,               // pic内网port
	@param IN	nPicPorty,              // pic外网port
	@param IN	type,                   // 违章类型,参考dpsdk_alarm_type_e
	@param IN szCarNum,            // 车牌
	@param IN nLicentype,             // 车牌颜色类型
	@param IN nCarColor,              // 车身颜色
	@param IN nCarLogo,               // 车标类型
	@param IN nWay,                   // 车道号
	@param IN szPicUrl0,           // 图片URL0
	@param IN szPicUrl1,           // 图片URL1
	@param IN szPicUrl2,           // 图片URL2
	@param IN szPicUrl3,           // 图片URL3
	@param IN szPicUrl4,           // 图片URL4
	@param IN szPicUrl5,           // 图片URL5
	@param IN nPicGroupStoreID,       // 图片组存储ID
	@param IN bNeedStore,             // 是否需存盘 0：不需存盘 1：需存盘
	@param IN bStored,		        // 是否已存盘 0：未存盘 1：已存盘int
	@param IN nAlarmLevel,            // 报警级别
	@param IN nAlarmTime,	            // 报警发生时间,精度为秒，值为time(NULL)值
	@param IN nChannel,              // 通道
	@param IN szDeviceId,          // 设备ID
	@param IN szDeviceName,        // 设备名称
	@param IN szDeviceChnName,     // 设备通道名称
	@param IN nCarType,               // 车类型
	@param IN nCarSpeed,              // 车速
	@param IN nCarLen,                // 车身长度单位
	@param IN nCardirect,             // 行车方向
	@param IN nMaxSpeed,              // 限制速度
	@param IN nMinSpeed,              // 最低限制速度
	@param IN nRtPlateleft,           // 车牌坐标left
	@param IN nRtPlatetop,            // 车牌坐标top
	@param IN nRtPlateright,          // 车牌坐标right
	@param IN nRtPlatebottom,         // 车牌坐标bottom
	@param IN szMessage          	  // 报警信息
*/

public interface fDPSDKTrafficAlarmCallback{
	public void invoke(int nPDLLHandle, 
					byte[] szCameraId,		
					byte[] nPtsIp,			
					byte[] nPtsIpy,            
					int	nPicPort,              
					int	nPicPorty,             
					int	type,                  
					byte[] szCarNum,           
					int nLicentype,            
					int nCarColor,             
					int nCarLogo,              
					int nWay,                  
					byte[] szPicUrl0,          
					byte[] szPicUrl1,          
					byte[] szPicUrl2,          
					byte[] szPicUrl3,          
					byte[] szPicUrl4,          
					byte[] szPicUrl5,          
					int nPicGroupStoreID,      
					int bNeedStore,            
					int bStored,		       
					int nAlarmLevel,           
					int nAlarmTime,	           
					int nChannel,              
					byte[] szDeviceId,         
					byte[] szDeviceName,       
					byte[] szDeviceChnName,    
					int nCarType,              
					int nCarSpeed,             
					int nCarLen,               
					int nCardirect,            
					int nMaxSpeed,             
					int nMinSpeed,             
					int nRtPlateleft,          
					int nRtPlatetop,           
					int nRtPlateright,         
					int nRtPlatebottom,        
					byte[] szMessage);         
}
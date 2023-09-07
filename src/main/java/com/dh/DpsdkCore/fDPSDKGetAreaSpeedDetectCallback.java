package com.dh.DpsdkCore;

/** 区间测速上报回调函数定义
    @param IN                                    nPDLLHandle              SDK句柄
    @param IN                                    pUserParam               用户指针参数,由对应的DPSDK_SetxxxxCallBack传入
	@param IN szAreaId,							// 区间ID 50
	@param IN szAreaName,                       // 区间名称 256
	@param IN szStartDevId,                     // 起始设备ID
	@param IN nStartChnNum,                     // 起始点通道号
	@param IN szStartChnId,                     // 起始点通道ID 
	@param IN szStartDevName,                   // 起始点设备名,UTF8编码
	@param IN szStartDevChnName,                // 起始点通道名,UTF8编码
	@param IN nStartCapTime,                    // 起始点通过时间
	@param IN nStartCarSpeed,                   // 起始点通过速度
	@param IN szStartPosId,	                    // 起始点卡点ID
	@param IN szStartPosName,	                // 起始点卡点名
	@param IN szEndDevId,                       // 终止点设备ID
	@param IN nEndChnNum,                       // 终止点通道号
	@param IN szEndChnId,                       // 终止点通道ID 
	@param IN szEndDevName,                     // 终止点设备名,UTF8编码
	@param IN szEndDevChnName,                  // 终止点通道名,UTF8编码
	@param IN nEndCapTime,                      // 终止点通过时间
	@param IN nEndCarSpeed,                     // 终止点通过速度
	@param IN szEndPosId,                           // 终止点卡点ID
	@param IN szEndPosName,                         // 终止点卡点名
	@param IN nAreaRange,                       // 区间距离
	@param IN nMinSpeed,                        // 路段限速下限 
	@param IN nMaxSpeed,                        // 路段限速上限 
	@param IN szCarNum,                             // 车牌号码，UTF8编码
	@param IN nCarNumType,                      // 车牌类型
	@param IN nCarNumColor,                     // 车牌颜色
	@param IN nCarColor,                        // 车身颜色
	@param IN nCarType,                         // 车类型
	@param IN nCarLogo,                         // 车标类型
	@param IN nCarAvgSpeed,                     // 车辆平均速度
	@param IN nIsIllegalSpeed,                  // 是否超速或低速
	@param IN nPicNum,                          // 图片张数，最大支持6张
	@param IN szPicName0,                       // 图片文件命名0
	@param IN szPicName1,                       // 图片文件命名1
	@param IN szPicName2,                       // 图片文件命名2
	@param IN szPicName3,                       // 图片文件命名3
	@param IN szPicName4,                       // 图片文件命名4
	@param IN szPicName5,                       // 图片文件命名5
	@param IN nRtPlateleft,          			// 车牌坐标left
	@param IN nRtPlatetop,                      // 车牌坐标top
	@param IN nRtPlateright,                    // 车牌坐标right
	@param IN nRtPlatebottom,                   // 车牌坐标bottom
	@param IN szCarPlatePicURL					// 车牌小图片URL
*/  
public interface fDPSDKGetAreaSpeedDetectCallback{
	public void invoke(int nPDLLHandle,
				byte[] szAreaId,
				byte[] szAreaName,
	            byte[] szStartDevId,
	            int    nStartChnNum,
	            byte[] szStartChnId,
	            byte[] szStartDevName,
	            byte[] szStartDevChnName,
	            long   nStartCapTime,
	            int    nStartCarSpeed,
	            byte[] szStartPosId,		
	            byte[] szStartPosName,	
	            byte[] szEndDevId,
	            int    nEndChnNum,
	            byte[] szEndChnId,
	            byte[] szEndDevName,
	            byte[] szEndDevChnName,
	            long   nEndCapTime,
	            int    nEndCarSpeed,
	            byte[] szEndPosId,
	            byte[] szEndPosName,
	            int    nAreaRange,
	            int    nMinSpeed,
	            int    nMaxSpeed,
	            byte[] szCarNum,
	            int    nCarNumType,
	            int    nCarNumColor,
	            int    nCarColor,
	            int    nCarType,
	            int    nCarLogo,
	            int    nCarAvgSpeed,
	            int    nIsIllegalSpeed,
	            int    nPicNum,
	            byte[] szPicName0,
	            byte[] szPicName1,
	            byte[] szPicName2,
	            byte[] szPicName3,
	            byte[] szPicName4,
	            byte[] szPicName5,
	            int nRtPlateleft,          
				int nRtPlatetop,           
				int nRtPlateright,         
				int nRtPlatebottom,    	
	            byte[] szCarPlatePicURL);
}
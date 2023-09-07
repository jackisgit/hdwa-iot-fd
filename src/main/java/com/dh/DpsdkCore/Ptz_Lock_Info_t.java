package com.dh.DpsdkCore;

/** 云台锁定操作信息
@param   szCameraId					通道ID
@param   nLock						锁定/解锁类型,参考类dpsdk_ptz_direct_e定义
*/

public class Ptz_Lock_Info_t
{
	public byte[]	szCameraId	= new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];		// 通道ID
	public byte[]	szSN = new byte[dpsdk_constant_value.DPSDK_CORE_28181_SN_LENGTH];		// 通道SN(默认填写为空字符串)
	public int				nLock;															// 锁定/解锁类型,参考类dpsdk_ptz_direct_e定义
};
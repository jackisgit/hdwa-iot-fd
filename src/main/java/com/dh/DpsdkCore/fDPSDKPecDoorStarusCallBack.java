package com.dh.DpsdkCore;

/** 门禁状态上报接收函数定义. 
@param  szCamearId				门禁通道ID
@param  nStatus					数据长度 
@param  nTime					上报时间
*/
public interface fDPSDKPecDoorStarusCallBack {
	public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus, int nTime);
}

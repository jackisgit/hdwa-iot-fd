package com.other;

import com.dh.DpsdkCore.fMediaDataCallback;

public class DPSDKMediaDataCallback implements fMediaDataCallback 
{
	public void invoke(int nPDLLHandle, int nSeq, int nMediaType, byte[] szNodeId, int nParamVal, byte[] szData, int nDataLen)
	{
		System.out.printf("DPSDKMediaDataCallback nSeq = %d, nMediaType = %d, nDataLen = %d", nSeq, nMediaType, nDataLen);
		System.out.println();
	}
}

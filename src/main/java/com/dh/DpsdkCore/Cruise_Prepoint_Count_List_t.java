package com.dh.DpsdkCore;

/** 巡航线预置点个数信息
@param     nCruiseCount							巡航线个数
@param     pCountList							巡航线上的预置点个数列表
*/

public class Cruise_Prepoint_Count_List_t
{
	public int						nCruiseCount;												// 巡航线个数
	public int[]					pCountList;													// 每条巡航线上的预置点个数
	public Cruise_Prepoint_Count_List_t()
	{
		nCruiseCount = 0;	
	}
};
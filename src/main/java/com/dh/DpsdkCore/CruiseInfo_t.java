package com.dh.DpsdkCore;

/** 单个巡航线信息
@param   iCruiseId						巡航线Id
@param   strCruiseName					巡航线名称
@param   iPrePointCount					巡航线包含的预置点个数
@param   pPrePointList					巡航线上的预置点信息列表
*/

public class CruiseInfo_t
{
	public int							iCruiseId;																					// 巡航线Id
	public byte[]						strCruiseName= new byte[dpsdk_constant_value.DPSDK_CORE_CHAR_LEN_128];						// 巡航线名称
	public int							iPrePointCount;																				// 巡航线包含的预置点个数
	public Cruise_Prepoint_Info_t []	pPrePointInfolist = new Cruise_Prepoint_Info_t[dpsdk_constant_value.DPSDK_CORE_POINT_COUNT];		// 巡航线上的预置点信息列表（预先分配空间，根据返回的个数iPrePointCount读取）
	public CruiseInfo_t()
	{
		
	}
};
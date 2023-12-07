package com.dh.DpsdkCore;

public class Query_Record_Info_t {
    public byte[] szCameraId = new byte[dpsdk_constant_value.DPSDK_CORE_CHL_ID_LEN];                            // 通道ID
    public int nRight;                                                // 是否检测权限,参考类dpsdk_check_right_e定义
    public int nSource;                                            // 录像源类型,参考类dpsdk_recsource_type_e定义
    public int nRecordType;                                        // 录像类型,参考类dpsdk_record_type_e定义
    public long uBeginTime;                                            // 起始时间
    public long uEndTime;                                            // 结束时间
};

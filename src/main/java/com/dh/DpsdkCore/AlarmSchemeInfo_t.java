package com.dh.DpsdkCore;

public class AlarmSchemeInfo_t {
    public int status;                                                                    // 预案状态
    public int id;                                                                        // 预案数据库id
    public byte[] schemeName = new byte[dpsdk_constant_value.DPSDK_CORE_SCHEME_NAME_LEN];    // 预案名称
    public int templateId;                                                                // 预案时间模板ID
    public byte[] desc = new byte[dpsdk_constant_value.DPSDK_CORE_SCHEME_DESC_LEN];        // 预案描述
}

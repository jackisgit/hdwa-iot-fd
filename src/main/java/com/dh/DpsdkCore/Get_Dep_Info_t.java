package com.dh.DpsdkCore;

public class Get_Dep_Info_t {
    public byte[] szCoding = new byte[dpsdk_constant_value.DPSDK_CORE_DGROUP_DGPCODE_LEN];                    // 节点code
    public int nDepCount;                                                // 组织个数
    public Dep_Info_t pDepInfo[];                                                // 组织信息，在外部创建，如果为NULL则只返回个数
    public int nDeviceCount;                                            // 设备个数
    public Device_Info_Ex_t pDeviceInfo[];                                            // 设备信息

    public Get_Dep_Info_t(int nMaxDepCount, int nMaxDeviceCount) {
        nDepCount = nMaxDepCount;
        pDepInfo = new Dep_Info_t[nDepCount];
        for (int i = 0; i < nDepCount; i++) {
            pDepInfo[i] = new Dep_Info_t();
        }

        nDeviceCount = nMaxDeviceCount;
        pDeviceInfo = new Device_Info_Ex_t[nDeviceCount];
        for (int i = 0; i < nDeviceCount; i++) {
            pDeviceInfo[i] = new Device_Info_Ex_t();
        }
    }
};

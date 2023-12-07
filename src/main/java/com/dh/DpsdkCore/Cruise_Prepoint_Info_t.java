package com.dh.DpsdkCore;

public class Cruise_Prepoint_Info_t {
    public int iNum;                                        // 当前预置点在巡航中的编号
    public int iPrePointId;                                // 预置点Id
    public int iStayTime;                                    // 停留时间,单位:秒

    public Cruise_Prepoint_Info_t() {
        iNum = 0;
        iPrePointId = 0;
        iStayTime = 15;
    }
};

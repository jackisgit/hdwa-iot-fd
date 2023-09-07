package com.wanda.epc.vo;

import lombok.Data;

@Data
public class ChannelInfo {

    String id;
    int alarmStatus;// 0表示未报警 1104表示报警 1105表示火警 1106表示防
    int nUndefendAlarm;// 0表示没有未布防报警 83表示未布防报警（由于24小时防区会出现布防报警和未布防报警并发的情况 所以需要区分一下）
    Boolean bDefend;// true=布防, false=撤防
}

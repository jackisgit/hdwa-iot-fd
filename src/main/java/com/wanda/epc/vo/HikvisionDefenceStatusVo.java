package com.wanda.epc.vo;

import lombok.Data;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 防区状态vo
 * @date 2023/5/9 16:17:52
 */
@Data
public class HikvisionDefenceStatusVo {

    //防区编码
    private String defenceIndexCode;

    //-1: 未知
    //0: 离线
    //1: 正常
    //2: 故障
    //3: 报警
    //4: 旁路
    private Integer status;
}

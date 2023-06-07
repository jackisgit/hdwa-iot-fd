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
public class HikvisionsubSystemIStatusVo {

    //子系统编码
    private String subSystemIndexCode;

    //子系统状态，-1：未知，0：撤防，1：布防
    private Integer status;
}

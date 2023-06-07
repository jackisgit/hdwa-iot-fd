package com.wanda.epc.vo;

import lombok.Data;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 撤布防请求参数
 * @date 2023/5/9 17:01:22
 */
@Data
public class HikvisionSubSystemReqVo {

    //	子系统(subSys)编码
    //通过查询入侵报警主机通道列表v2获取返回参数indexCode
    private String subSystemIndexCode;

    //布撤防状态
    //0: 撤防
    //1: 布防
    private Integer status;
}

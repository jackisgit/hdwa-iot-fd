package com.wanda.epc.vo;

import lombok.Data;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 撤布防响应参数
 * @date 2023/5/9 17:03:15
 */
@Data
public class HikvisionSubSystemRspVo {

    //子系统编码
    private String indexCode;

    //操作结果
    //true：成功
    //false: 失败
    private boolean success;

    //错误码参考附录E.4.1 入侵报警错误码
    private String errorCode;
}

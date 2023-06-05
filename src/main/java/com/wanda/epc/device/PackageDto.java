package com.wanda.epc.device;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 防盗包DTO
 *
 * @author 孙率众
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDto {

    /**
     * 包序号
     */
    @JSONField(name = "PackageNo")
    private String packageNo;

    /**
     * 数据类型
     */
    @JSONField(name = "CmdType")
    private String cmdType;
    /**
     * 数据值1
     */
    @JSONField(name = "Data1")
    private String data1;
    /**
     * 数据值2
     */
    @JSONField(name = "Data2")
    private String data2;
    /**
     * 数据值3
     */
    @JSONField(name = "Data3")
    private String data3;
    /**
     * 数据值4
     */
    @JSONField(name = "Data4")
    private String data4;
    /**
     * 数据值5
     */
    @JSONField(name = "Data5")
    private String data5;
    /**
     * 数据值6
     */
    @JSONField(name = "Data6")
    private String data6;
    /**
     * 数据值7
     */
    @JSONField(name = "Data7")
    private String data7;
    /**
     * 数据值8
     */
    @JSONField(name = "Data8")
    private String data8;
    /**
     * 数据值9
     */
    @JSONField(name = "Data9")
    private String data9;
    /**
     * 数据值10
     */
    @JSONField(name = "Data10")
    private String data10;

}

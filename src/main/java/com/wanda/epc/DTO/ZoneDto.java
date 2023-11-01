package com.wanda.epc.DTO;

import lombok.Data;

/**
 * 防区DTO
 * @author 孙率众
 */
@Data
public class ZoneDto {

    /**
     * 防区 ID
     */
    private Integer zoneId;

    /**
     * 防区类型
     */
    private String zoneType;
    /**
     * 布防状态
     * 0、未知状态 1、外出布防 2、在家布防 3、撤防状态 4、布防延时
     */
    private Integer zoneArmingStatus;
    /**
     * 防区名称
     */
    private String zoneName;

    /**
     * 设备状态
     * 0、未知状态 1、在线状态 2、离线状态 3、报警状态 4、故障状态
     */
    private Integer zoneStatus;

}

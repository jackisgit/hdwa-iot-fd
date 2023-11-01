package com.wanda.epc.DTO;

import lombok.Data;

/**
 * 设备DTO
 * @author 孙率众
 */
@Data
public class AlarmDto {

    /**
     * 设备 Id
     */
    private Integer deviceId;
    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 防区编号
     */
    private Integer zoneId;
}

package com.wanda.epc.device;

import lombok.Data;

/**
 * @author 孙率众
 */
@Data
public class DeviceDto {

    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 布防状态
     * 0、未知状态 1、外出布防 2、在家布防 3、撤防状态 4、布防延时
     */
    private Integer armingStatus;
    /**
     * 设备 Id
     */
    private Integer deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备版本号
     */
    private Integer version;
    /**
     * 设备状态
     * 0、未知状态 1、在线状态 2、离线状态 3、报警状态 4、故障状态
     */
    private Integer deviceStatus;

}

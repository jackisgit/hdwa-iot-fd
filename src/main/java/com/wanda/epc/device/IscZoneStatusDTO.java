package com.wanda.epc.device;

import lombok.Data;

@Data
public class IscZoneStatusDTO {

    private String zoneIndexCode;

    /** ARMED / DISARMED */
    private String armState;

    /** ALARM / NORMAL */
    private String alarmState;

    /** TAMPER / NORMAL */
    private String tamperState;

    /** FAULT / NORMAL */
    private String faultState;
}

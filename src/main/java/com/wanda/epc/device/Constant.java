package com.wanda.epc.device;

/**
 * 静态常量
 *
 * @author 孙率众
 */
public class Constant {

    /**
     * 心跳（108000）
     */
    public static final int HEART = 108000;

    /**
     * 上传全量设备列表（108001）
     */
    public static final int DEVICE_LIST = 108001;

    /**
     * 新增设备通知（108002）
     */
    public static final int ADD_DEVICE = 108002;

    /**
     * 删除设备通知（108003）
     */
    public static final int DEL_DEVICE = 108003;

    /**
     * 设备状态改变通知（108004）
     */
    public static final int DEVICE_STATUS_CHANGE = 108004;
    /**
     * 获取设备的防区列表（108005）SERVER
     */
    public static final int DEVICE_DEFENCE_AREA = 108005;

    /**
     * 设备（防区）布/撤防（108006）SERVER
     */
    public static final int DISBAND_DEFENSE = 108006;

    /**
     * 设备消警（108007）SERVER
     */
    public static final int ALARM_ELIMINATION = 108007;
    /**
     * 报警事件通知（108008）
     */
    public static final int ALARM_RECEIVE = 108008;
    /**
     * 同步设备列表（108009）SERVER
     */
    public static final int SYNC_DEVICE = 108009;

}

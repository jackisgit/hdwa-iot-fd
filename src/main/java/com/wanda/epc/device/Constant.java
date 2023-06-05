package com.wanda.epc.device;

/**
 * 常量类
 *
 * @author 孙率众
 */
public class Constant {

    //=============================================以下为设备静态变量=========================================

    /**
     * 报警用户（分区）状态
     */
    public static final String AA_STATUS = "AA_Status";

    /**
     * 报警防区状态
     */
    public static final String AZ_STATUS = "AZ_Status";

    /**
     * 控制报警用户（分区），（布防和撤防）
     */
    public static final String ALARMCLIENT_CONTROL = "AlarmClient_Control";
    /**
     * 控制报警用户（分区）编号，（布防和撤防）
     */
    public static final String ALARMCLIENT_CONTROL_NO = "999";
    /**
     * 登录
     */
    public static final String LOGIN = "Login";
    /**
     * 登录编号
     */
    public static final String LOGIN_NO = "777";

    /**
     * 登录
     */
    public static final String LOGIN_USER = "API";

    /**
     * 密码
     */
    public static final String LOGIN_PWD = "API";

    /**
     * 心跳
     */
    public static final String KEEP_ALIVE = "KeepAlive";

    /**
     * 心跳编号
     */
    public static final String KEEP_ALIVE_NO = "888";


    //=============================================以上为设备静态变量=========================================

    //=============================================以下为平台静态变量=========================================
    /**
     * 撤布防设定
     */
    public static final String DEPLOY_WITH_DRAW_ALARM_SET = "deployWithdrawAlarmSet";
    /**
     * 报警状态
     */
    public static final String IS_ALARM = "isAlarm";
    /**
     * 撤布防反馈
     */
    public static final String DEPLOY_WITH_DRAW_ALARM_SET_FEEDBACK = "deployWithdrawAlarmSetFeedback";
    //=============================================以上为平台静态变量=========================================
}

package com.wanda.epc.device;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.PingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;


@Slf4j
@Component
public class HikVisionEasDevice extends BaseDevice {

    public static final String DEPLOY_WITHDRAW_ALARM_STATUS = "_deployWithdrawAlarmStatus";
    public static final String ALARM_STATUS = "_alarmStatus";
    public static final String FAULT_STATUS = "_faultStatus";
    public static final String FANGCHAI_STATUS = "_fangchaiStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_SET = "deployWithdrawAlarmSet";
    public static final String ONLINE_STATUS = "_onlineStatus";
    static HCNetSDK hCNetSDK;
    static int lUserID = -1;//用户句柄 实现对设备登录
    static int lListenHandle = -1;//报警监听句柄
    static FMSGCallBack fMSFCallBack = null;
    static HCNetSDK.FMSGCallBack_V31 fMSFCallBack_V31 = null;
    @Value("${hikVision.ip}")
    private String ip;

    @Value("${hikVision.port}")
    private short port;

    @Value("${hikVision.user}")
    private String user;

    @Value("${hikVision.pwd}")
    private String pwd;

    /**
     * 开启监听
     *
     * @param ip   监听IP
     * @param port 监听端口
     */
    public static void startListen(String ip, short port) {
        if (fMSFCallBack == null) {
            fMSFCallBack = new FMSGCallBack();
        }
        lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(ip, port, fMSFCallBack, null);
        if (lListenHandle == -1) {
            log.info("监听失败" + hCNetSDK.NET_DVR_GetLastError());
            return;
        } else {
            log.info("监听成功");
        }
    }

    /**
     * 动态库加载
     *
     * @return
     */
    private static boolean createSDKInstance() {
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                String strDllPath = "";
                try {
                    if (osSelect.isWindows()) {
                        //win系统加载库路径
                        strDllPath = System.getProperty("user.dir") + "\\lib\\HCNetSDK.dll";
                    } else if (osSelect.isLinux()) {
                        //Linux系统加载库路径
                        strDllPath = System.getProperty("user.dir") + "/lib/libhcnetsdk.so";
                    }
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
                } catch (Exception ex) {
                    log.info("loadLibrary: " + strDllPath + " Error: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 设备登录V40 与V30功能一致
     *
     * @param ip   设备IP
     * @param port SDK端口，默认设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     */
    private static void login_V40(String ip, short port, String user, String psw) {
        //注册
        HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
        HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息

        String m_sDeviceIP = ip;//设备ip地址
        m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(m_sDeviceIP.getBytes(), 0, m_strLoginInfo.sDeviceAddress, 0, m_sDeviceIP.length());

        String m_sUsername = user;//设备用户名
        m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(m_sUsername.getBytes(), 0, m_strLoginInfo.sUserName, 0, m_sUsername.length());

        String m_sPassword = psw;//设备密码
        m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(m_sPassword.getBytes(), 0, m_strLoginInfo.sPassword, 0, m_sPassword.length());

        m_strLoginInfo.wPort = port;
        m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是
        m_strLoginInfo.byLoginMode = 0;  //ISAPI登录
        m_strLoginInfo.write();

        lUserID = hCNetSDK.NET_DVR_Login_V40(m_strLoginInfo, m_strDeviceInfo);

        if (lUserID == -1) {
            log.info("登录失败，错误码为" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.info(ip + ":设备登录成功！");
        }
    }

    /**
     * 报警布防接口
     *
     * @param
     */
    public static void setAlarm(int num) {
        HCNetSDK.NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup = new HCNetSDK.NET_DVR_ALARMIN_SETUP();
        net_dvr_alarmin_setup.byAssiciateAlarmIn[num - 1] = 1; // 对防区布防
        net_dvr_alarmin_setup.write();
        Boolean aBoolean = hCNetSDK.NET_DVR_AlarmHostSetupAlarmChan(lUserID, net_dvr_alarmin_setup);
        if (!aBoolean) {
            log.error("对防区" + num + "布防失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
            if (hCNetSDK.NET_DVR_GetLastError() == 1209) {
                subsystemParamEx(1);
                subsystemParamEx(2);
            }
        } else {
            log.info("对防区" + num + "布防成功");
        }

    }

    /**
     * 子系统报警布防
     *
     * @param num 子系统号，子系统关联的防区将会全部布防，实现防区一键布防
     */
    private static void alarmHostSubSystemSetupAlarmChan(int num) {
        //报警撤防
        Boolean aBoolean1 = hCNetSDK.NET_DVR_AlarmHostSubSystemSetupAlarmChan(lUserID, num);
        if (!aBoolean1) {
            log.info("对子系统" + num + "布防失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.info("对子系统" + num + "布防成功");
        }
    }

    /**
     * 子系统报警撤防
     *
     * @param num
     */
    private static void alarmHostSubSystemCloseAlarmChan(int num) {
        //报警撤防
        Boolean aBoolean = hCNetSDK.NET_DVR_AlarmHostSubSystemCloseAlarmChan(lUserID, num);
        if (!aBoolean) {
            log.info("对子系统" + num + "撤防失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.info("对子系统" + num + "撤防成功");
        }
    }

    /**
     * 设备撤防，设备注销
     *
     * @param
     */
    public static void closeAlarmChan(int num) {
        HCNetSDK.NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup = new HCNetSDK.NET_DVR_ALARMIN_SETUP();
        net_dvr_alarmin_setup.byAssiciateAlarmIn[num - 1] = 1; // 对防区1布防
        net_dvr_alarmin_setup.write();
        Boolean aBoolean = hCNetSDK.NET_DVR_AlarmHostCloseAlarmChan(lUserID, net_dvr_alarmin_setup);
        if (!aBoolean) {
            log.error("对防区" + num + "撤防失败，错误码：" + hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.info("对防区" + num + "撤防成功");
        }

    }

    /**
     * 功能描述:子系统关联防区
     * 子系统关联参数设置规则：索引都默认为0代表关联1-8防区（subsystem_param_ex.byJointAlarmIn[0]） 1代表9-16防区，以此类推 subsystem_param_ex.byJointAlarmIn[1]）
     * 所设置的十进制值将会转换成2禁止，转成二进制1代表关联，0代表取消关联
     * 注意:防区从右到左依次递增
     * 例如：byJointAlarmIn[0] = 7; 7转换成2进制为：111，代表关联1，2，3三个防区
     * byJointAlarmIn[0] = 5; 5转换成2进制为：101，代表关联1，3两个个防区
     * 注意：由于byte最大值为127，如果8位二进制转成byte超出，直接强转成byte,或自动进行补位
     *
     * @param alarmsubsystem_code:子系统号
     */
    public static void subsystemParamEx(int alarmsubsystem_code) {
        log.info("使能开始，子系统号：{}", alarmsubsystem_code);
        /**************开启子系统使能**************/
        HCNetSDK.NET_DVR_ALARMSUBSYSTEMPARAM net_dvr_alarmsubsystemparam = new HCNetSDK.NET_DVR_ALARMSUBSYSTEMPARAM();
        net_dvr_alarmsubsystemparam.dwSize = net_dvr_alarmsubsystemparam.size();
        net_dvr_alarmsubsystemparam.write();
        IntByReference lpBytesReturned = new IntByReference(0);
        boolean b_GetAcsCfg = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, 2001, alarmsubsystem_code, net_dvr_alarmsubsystemparam.getPointer(), net_dvr_alarmsubsystemparam.size(), lpBytesReturned);
        if (b_GetAcsCfg) {
            net_dvr_alarmsubsystemparam.read();
            net_dvr_alarmsubsystemparam.bySubsystemEnable = 1;//子系统使能：0- 不启用，1- 启用
            net_dvr_alarmsubsystemparam.bySingleZoneSetupAlarmEnable = 1;//单防区布撤防使能：0-禁能，1-使能
            net_dvr_alarmsubsystemparam.byOneKeySetupAlarmEnable = 1;//一键布防使能：0-禁能，1-使能
            boolean b_SetAcsCfg = hCNetSDK.NET_DVR_SetDVRConfig(lUserID, 2002, alarmsubsystem_code, net_dvr_alarmsubsystemparam.getPointer(), net_dvr_alarmsubsystemparam.size());
            if (!b_SetAcsCfg) {
                log.error("设置子系统参数失败！ 错误码：" + hCNetSDK.NET_DVR_GetLastError());
            }
        } else {
            log.error("获取子系统参数失败！ 错误码：" + hCNetSDK.NET_DVR_GetLastError());
        }
        log.info("使能成功，子系统号：{}", alarmsubsystem_code);
    }

    @PostConstruct
    public void init() {
        if (hCNetSDK == null) {
            if (!createSDKInstance()) {
                System.out.println("Load SDK fail");
                return;
            }
        }
        //linux系统建议调用以下接口加载组件库
        if (osSelect.isLinux()) {
            HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
            HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
            //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
            String strPath1 = System.getProperty("user.dir") + "/lib/libcrypto.so.1.1";
            String strPath2 = System.getProperty("user.dir") + "/lib/libssl.so.1.1";

            System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
            ptrByteArray1.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(3, ptrByteArray1.getPointer());

            System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
            ptrByteArray2.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(4, ptrByteArray2.getPointer());

            String strPathCom = System.getProperty("user.dir") + "/lib/";
            HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
            System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
            struComPath.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(2, struComPath.getPointer());
        }

        /**初始化*/
        hCNetSDK.NET_DVR_Init();
        /**加载日志*/
        hCNetSDK.NET_DVR_SetLogToFile(3, "./sdklog", false);
        //设置报警回调函数
        if (fMSFCallBack_V31 == null) {
            fMSFCallBack_V31 = new FMSGCallBack_V31();
            Pointer pUser = null;
            if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                log.info("设置回调函数失败!");
                return;
            } else {
                log.info("设置回调函数成功!");
            }
        }
        /** 设备上传的报警信息是COMM_VCA_ALARM(0x4993)类型，
         在SDK初始化之后增加调用NET_DVR_SetSDKLocalCfg(enumType为NET_DVR_LOCAL_CFG_TYPE_GENERAL)设置通用参数NET_DVR_LOCAL_GENERAL_CFG的byAlarmJsonPictureSeparate为1，
         将Json数据和图片数据分离上传，这样设置之后，报警布防回调函数里面接收到的报警信息类型为COMM_ISAPI_ALARM(0x6009)，
         报警信息结构体为NET_DVR_ALARM_ISAPI_INFO（与设备无关，SDK封装的数据结构），更便于解析。*/

        HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG struNET_DVR_LOCAL_GENERAL_CFG = new HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG();
        struNET_DVR_LOCAL_GENERAL_CFG.byAlarmJsonPictureSeparate = 1;   //设置JSON透传报警数据和图片分离
        struNET_DVR_LOCAL_GENERAL_CFG.write();
        Pointer pStrNET_DVR_LOCAL_GENERAL_CFG = struNET_DVR_LOCAL_GENERAL_CFG.getPointer();
        hCNetSDK.NET_DVR_SetSDKLocalCfg(17, pStrNET_DVR_LOCAL_GENERAL_CFG);

        login_V40(ip, port, user, pwd);  //登录设备

        setupAlarmChan(lUserID, -1);//建立报警布防通道


    }

    @PreDestroy
    public void LogoutAndStopListen() {
        if (lListenHandle > -1) {
            if (hCNetSDK.NET_DVR_StopListen_V30(lListenHandle)) {
                log.info("停止监听成功");
            }
        }
        if (lUserID > -1) {
            if (hCNetSDK.NET_DVR_Logout(lUserID)) {
                log.info("注销成功");
            }
        }
        hCNetSDK.NET_DVR_Cleanup();
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    public boolean processData() throws Exception {
        deviceParamListMap.entrySet().forEach(entry -> {
            List<String> ipList = Arrays.asList(entry.getKey().split("_"));
            String ip = ipList.get(0);
            Queue<String> allIp = new LinkedList<String>();
            allIp.offer(ip);
            PingUtil pingUtil = new PingUtil(allIp);
            pingUtil.setIpsOK("");
            pingUtil.setIpsNO("");
            pingUtil.startPing();
            String ipsOK = pingUtil.getIpsOK();
            String ipsNo = pingUtil.getIpsNO();
            if (StringUtils.isNotBlank(ipsOK)) {
                sendMsg(ip.concat(ONLINE_STATUS), "1");
            }
            if (StringUtils.isNotBlank(ipsNo)) {
                sendMsg(ip.concat(ONLINE_STATUS), "0");
            }
        });
        alarmStatus();
        return false;
    }

    /**
     * 建立布防上传通道，用于传输数据
     *
     * @param lUserID      唯一标识符
     * @param lAlarmHandle 报警处理器
     */
    private int setupAlarmChan(int lUserID, int lAlarmHandle) {
        // 根据设备注册生成的lUserID建立布防的上传通道，即数据的上传通道
        if (lUserID == -1) {
            log.info("请先注册");
            return lUserID;
        }
        if (lAlarmHandle < 0) {
            // 设备尚未布防,需要先进行布防
            if (fMSFCallBack_V31 == null) {
                fMSFCallBack_V31 = new FMSGCallBack_V31();
                Pointer pUser = null;
                if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, pUser)) {
                    log.info("设置回调函数失败!", hCNetSDK.NET_DVR_GetLastError());
                }
            }
            // 这里需要对设备进行相应的参数设置，不设置或设置错误都会导致设备注册失败
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            // 智能交通布防优先级：0 - 一等级（高），1 - 二等级（中），2 - 三等级（低）
            m_strAlarmInfo.byLevel = 1;
            // 智能交通报警信息上传类型：0 - 老报警信息（NET_DVR_PLATE_RESULT）, 1 - 新报警信息(NET_ITS_PLATE_RESULT)
            m_strAlarmInfo.byAlarmInfoType = 1;
            // 布防类型(仅针对门禁主机、人证设备)：0 - 客户端布防(会断网续传)，1 - 实时布防(只上传实时数据)
            m_strAlarmInfo.byDeployType = 1;
            // 抓拍，这个类型要设置为 0 ，最重要的一点设置
            m_strAlarmInfo.byFaceAlarmDetection = 0;
            m_strAlarmInfo.write();
            // 布防成功，返回布防成功的数据传输通道号
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, m_strAlarmInfo);
            if (lAlarmHandle == -1) {
                log.info("设备布防失败，错误码=========={}", hCNetSDK.NET_DVR_GetLastError());
                // 注销 释放sdk资源
                hCNetSDK.NET_DVR_Logout(lUserID);
                return lAlarmHandle;
            } else {
                log.info("设备布防成功");
                return lAlarmHandle;
            }
        }
        return lAlarmHandle;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        String outParamId = deviceMessage.getOutParamId();
        log.info("接收到防盗报警撤布防指令 meter:{},funcId:{},value:{},deviceMessage:{}", meter, funcid, value, JSON.toJSONString(deviceMessage));
        if (ObjectUtils.isNotEmpty(deviceMessage) && StringUtils.isNotBlank(outParamId) && outParamId.endsWith(DEPLOY_WITHDRAW_ALARM_SET)) {
            try {
                if (redisUtil.hasKey(outParamId)) {
                    return;
                }
                redisUtil.set(outParamId, "0", 5);
                final String[] strings = outParamId.split("_");
                if ("1.0".equals(value)) {
                    alarmHostSubSystemSetupAlarmChan(Integer.valueOf(strings[0]));
                } else {
                    alarmHostSubSystemCloseAlarmChan(Integer.valueOf(strings[0]));
                }
            } catch (Exception e) {
                log.error("防盗报警控制命令下发失败：" + e.getMessage());
            }
        }
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    /**
     * @description 查询防盗报警撤布防状态
     * @author LianYanFei
     * @date 2023/5/24
     */
    private void alarmStatus() {
        log.info("开始主动查询防区状态信息");
        HCNetSDK.NET_DVR_ALARMHOST_MAIN_STATUS_V40 acsWorkStatus = new HCNetSDK.NET_DVR_ALARMHOST_MAIN_STATUS_V40();
        Pointer pAcsWorkStatus = acsWorkStatus.getPointer();
        if (!hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_ALARMHOST_MAIN_STATUS_V40, 0, pAcsWorkStatus,
                acsWorkStatus.size(), new IntByReference(0))) {
            log.error("Failed to get door status! Error code: " + hCNetSDK.NET_DVR_GetLastError());
            return;
        }
        acsWorkStatus.read();
        //撤布防状态
        log.info("bySetupAlarmStatus(撤布防状态):{}", acsWorkStatus.bySetupAlarmStatus.length);
        for (int i = 0; i < acsWorkStatus.bySetupAlarmStatus.length; i++) {
            byte b = acsWorkStatus.bySetupAlarmStatus[i];
            //0- 对应防区处于撤防状态，1- 对应防区处于布防状态
            sendMsg(i + 1 + DEPLOY_WITHDRAW_ALARM_STATUS, String.valueOf(b));
        }
        //防区报警状态
        log.info("byAlarmInStatus(防区报警状态):{}", acsWorkStatus.byAlarmInStatus.length);
        for (int i = 0; i < acsWorkStatus.byAlarmInStatus.length; i++) {
            byte b = acsWorkStatus.byAlarmInStatus[i];
            //0- 对应防区当前无报警，1- 对应防区当前有报警
            sendMsg(i + 1 + ALARM_STATUS, String.valueOf(b));
        }
        //防区故障状态
        log.info("byAlarmInFaultStatus(防区故障状态):{}", acsWorkStatus.byAlarmInFaultStatus.length);
        for (int i = 0; i < acsWorkStatus.byAlarmInFaultStatus.length; i++) {
            byte b = acsWorkStatus.byAlarmInFaultStatus[i];
            //0-对应防区处于正常状态，1-对应防区处于故障状态
            sendMsg(i + 1 + FAULT_STATUS, String.valueOf(b));
        }
        //防区防拆状态
        log.info("byAlarmInFaultStatus(防区防拆状态):{}", acsWorkStatus.byAlarmInTamperStatus.length);
        for (int i = 0; i < acsWorkStatus.byAlarmInTamperStatus.length; i++) {
            byte b = acsWorkStatus.byAlarmInTamperStatus[i];
            //0-对应防区当前无报警，1-对应防区当前有报警
            sendMsg(i + 1 + FANGCHAI_STATUS, String.valueOf(b));
        }
    }

    /**
     * 获取异常信息
     */
    public static class FExceptionCallBack_Imp implements HCNetSDK.FExceptionCallBack {
        private static Map<Integer, String> ipUserIdMap = new HashMap<>();

        public static void addUserId(Map map) {

            ipUserIdMap.putAll(map);
        }

        @Override
        public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
            switch (dwType) {
                case 0x8000: //用户交互时异常
                    log.info("用户交互时异常（注册心跳超时，心跳间隔为2分钟）!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8001:  //语音对讲异常
                    log.info("语音对讲异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8002: //报警异常
                    log.info("报警异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8003://网络预览异常
                    log.info("网络预览异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8004: //透明通道异常
                    log.info("透明通道异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8005:  //预览时重连
                    log.info("预览时重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8006: //报警时重连
                    log.info("报警时重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8007://透明通道重连
                    log.info("透明通道重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8008: //透明通道重连成功
                    log.info("透明通道重连成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8010: //回放异常
                    log.info("回放异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8011://硬盘格式化
                    log.info("硬盘格式化!!!" + lHandle);
                    break;
                case 0x8012: //被动解码异常
                    log.info("被动解码异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8013:  //邮件测试异常
                    log.info("邮件测试异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8014: //备份异常
                    log.info("备份异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8015://预览时重连成功
                    log.info("预览时重连成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8016: //报警时重连成功
                    log.info("报警时重连成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8017:  //网络预览时网络异常
                    log.info("用户交互恢复!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8018: //网络流量检测异常
                    log.info("网络流量检测异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8019://图片预览重连
                    log.info("图片预览重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8020: //图片预览重连成功
                    log.info("图片预览重连成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8021://图片预览异常
                    log.info("图片预览异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8022: //报警信息缓存已达上限
                    log.info("报警信息缓存已达上限!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8023:  //报警丢失
                    log.info("报警丢失!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8024: //被动转码重连
                    log.info("被动转码重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8025://被动转码重连成功
                    log.info("被动转码重连成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8026: //被动转码异常
                    log.info("被动转码异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8040: //用户重登陆
                    log.info("用户重登陆!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8041://用户重登陆成功
                    log.info("用户重登陆成功!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8042: //被动解码重连
                    log.info("被动解码重连!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8043:  //集群报警异常
                    log.info("集群报警异常!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8044: //重登陆失败，停止重登陆
                    log.info("重登陆失败，停止重登陆!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8045://关闭预览重连功能
                    log.info("关闭预览重连功能!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8046: //关闭报警重连功能
                    log.info("关闭报警重连功能!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8047:  //关闭透明通道重连功能
                    log.info("关闭透明通道重连功能!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8048: //关闭回显重连功能
                    log.info("关闭回显重连功能!!!" + "lUserID:" + lUserID);
                    break;
                case 0x8049://关闭被动解码重连功能
                    log.info("关闭被动解码重连功能!!!" + "lUserID:" + lUserID);
                    break;
                default:
                    log.info("异常" + "lUserID:" + lUserID);
                    break;

            }
        }
    }
}

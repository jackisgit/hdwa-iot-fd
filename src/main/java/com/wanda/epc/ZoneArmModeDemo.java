package com.wanda.epc;


import com.alibaba.fastjson.JSONObject;
import com.netsdk.lib.NetSDKLib;
import com.netsdk.lib.ToolKits;
import com.netsdk.lib.callback.impl.DefaultDisconnectCallback;
import com.netsdk.lib.callback.impl.DefaultHaveReconnectCallBack;
import com.netsdk.lib.callback.impl.MessCallBack;
import com.netsdk.lib.enumeration.*;
import com.netsdk.lib.structure.*;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.netsdk.lib.Utils.getOsPrefix;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2023/8/9 15:50
 */
@Component
@Slf4j
public class ZoneArmModeDemo {


    // 编码格式
    public static String encode;
    // 回调函数需要是静态的，防止被系统回收
    // 断线回调
    private static NetSDKLib.fDisConnect disConnectCB = DefaultDisconnectCallback.getINSTANCE();
    // 重连回调
    private static NetSDKLib.fHaveReConnect haveReConnectCB = DefaultHaveReconnectCallBack.getINSTANCE();

    static {
        String osPrefix = getOsPrefix();
        if (osPrefix.toLowerCase().startsWith("win32-amd64")) {
            encode = "GBK";
        } else if (osPrefix.toLowerCase().startsWith("linux-amd64")) {
            encode = "UTF-8";
        }
    }

    // SDk对象初始化
    public final NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;
    // 判断是否初始化
    private boolean bInit = false;
    // 判断log是否打开
    private boolean bLogOpen = false;
    // 设备信息
    private NetSDKLib.NET_DEVICEINFO_Ex deviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();
    // 登录句柄
    private NetSDKLib.LLong loginHandle = new NetSDKLib.LLong(0);
    // 配置登陆地址，端口，用户名，密码
    @Value("${ip}")
    private String m_strIpAddr;
    @Value("${port}")
    private int m_nPort;
    @Value("${user}")
    private String m_strUser;
    @Value("${password}")
    private String m_strPassword;

    /**
     * 按照指定格式，获取当前时间
     */
    public String getDate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDate.format(new Date()).replaceAll("[^0-9]", "-");
    }

    /**
     * 初始化SDK库
     */
    public boolean initSdk() {
        bInit = netsdk.CLIENT_Init(disConnectCB, null);// 进程启动时，初始化一次
        if (!bInit) {
            log.error("Initialize SDK failed");
            return false;
        }
        // 配置日志
        enableLog();
        // 设置断线重连回调接口, 此操作为可选操作，但建议用户进行设置
        netsdk.CLIENT_SetAutoReconnect(haveReConnectCB, null);
        // 设置登录超时时间和尝试次数，可选
        // 登录请求响应超时时间设置为3S
        int waitTime = 3000; //单位为ms
        // 登录时尝试建立链接 1 次
        int tryTimes = 1;
        // 设置连接设备超时时间和尝试次数
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);
        // 设置更多网络参数， NET_PARAM 的nWaittime ， nConnectTryNum 成员与 CLIENT_SetConnectTime
        // 接口设置的登录设备超时时间和尝试次数意义相同,可选
        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        // 登录时尝试建立链接的超时时间
        netParam.nConnectTime = 10000;
        // 设置子连接的超时时间
        netParam.nGetConnInfoTime = 3000;
        //设置登陆网络环境
        netsdk.CLIENT_SetNetworkParam(netParam);
        return true;
    }

    /**
     * 打开 sdk log
     */
    private void enableLog() {
        // SDK全局日志打印信息
        NetSDKLib.LOG_SET_PRINT_INFO setLog = new NetSDKLib.LOG_SET_PRINT_INFO();
        //设置日志路径
        File path = new File("sdklog/");
        //判断日志路径是否存在，若不存在则创建
        if (!path.exists()) {
            path.mkdir();
        }
        // 这里的log保存地址依据实际情况自己调整
        String logPath = path.getAbsoluteFile().getParent() + "\\sdklog\\" + "sdklog" + getDate() + ".log";
        //日志输出策略,0:输出到文件(默认); 1:输出到窗口,
        setLog.nPrintStrategy = 0;
        //是否重设日志路径, 取值0否 ,1是
        setLog.bSetFilePath = 1;
        //日志路径(默认"./sdk_log/sdk_log.log")
        byte[] szLogFilePath = setLog.szLogFilePath;
        //自定义log保存地址，将数据logPath数据copy到LOG_SET_PRINT_INFO-->szLogFilePath变量中
        System.arraycopy(logPath.getBytes(), 0, szLogFilePath, 0, logPath.getBytes().length);
        //是否重设日志打印输出策略 取值0否 ,1是
        setLog.bSetPrintStrategy = 1;
        // 打开日志功能
        bLogOpen = netsdk.CLIENT_LogOpen(setLog);
        if (!bLogOpen) {
            log.error("Failed to open NetSDK log " + ToolKits.getErrorCode());
        } else {
            log.info("Success to open NetSDK log ");
        }
    }

    /**
     * 高安全登录
     */
    public void loginWithHighLevel() {
        // 输入结构体参数
        NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstlnParam = new NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY() {
            {
                // IP
                szIP = m_strIpAddr.getBytes();
                // 端口
                nPort = m_nPort;
                // 用户名
                szUserName = m_strUser.getBytes();
                // 密码
                szPassword = m_strPassword.getBytes();
            }
        };
        // 登录的输出结构体参数
        NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam = new NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
        // 高安全级别登陆
        loginHandle = netsdk.CLIENT_LoginWithHighLevelSecurity(pstlnParam, pstOutParam);
        if (loginHandle.longValue() == 0) {  //登陆失败
            log.error("Login Device[{}] Port[{}]Failed. {}", m_strIpAddr, m_nPort, ToolKits.getErrorCode());

        } else { //登陆成功
            // 获取设备信息
            deviceInfo = pstOutParam.stuDeviceInfo;
            log.info("Login Success Device Address[{}] 设备包含[{}]个通道 \n", m_strIpAddr, deviceInfo.byChanNum);
        }
    }

    /**
     * 退出
     */
    public void logOut() {
        //判断是否已登录
        if (loginHandle.longValue() != 0) {
            netsdk.CLIENT_Logout(loginHandle);
            log.info("LogOut Success");
        }
    }

    /**
     * 清理sdk环境并退出
     */
    public void cleanAndExit() {
        //判断log是否打开
        if (bLogOpen) {
            // 关闭sdk日志打印
            netsdk.CLIENT_LogClose();
        }
        // 进程关闭时，调用一次
        netsdk.CLIENT_Cleanup();
        System.exit(0);
    }

    public Pointer getStringToPointer(String src, String charset) {
        Pointer pointer = null;
        try {
            byte[] b = src.getBytes(charset);

            pointer = new Memory(b.length + 1);
            pointer.clear(b.length + 1);

            pointer.write(0, b, 0, b.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pointer;
    }

    public void StringToByteArr(String src, byte[] dst, String charName) {
        try {
            byte[] GBKBytes = src.getBytes(charName);
            for (int i = 0; i < GBKBytes.length; i++) {
                dst[i] = (byte) GBKBytes[i];
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * IO输入端状态
     */
    public NET_OUT_GET_CHANNELS_STATE channelsState() {
        // 入参
        NET_IN_GET_CHANNELS_STATE stuIn = new NET_IN_GET_CHANNELS_STATE();
        /**
         通道类型 {@link EM_CHANNELS_STATE_TYPE}
         */
        stuIn.stuCondition.emType = 1;
        stuIn.write();
        // 出参
        NET_OUT_GET_CHANNELS_STATE stuOut = new NET_OUT_GET_CHANNELS_STATE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_GetAlarmRegionInfo(loginHandle, NET_EM_GET_ALARMREGION_INFO.NET_EM_GET_ALARMREGION_INFO_CHANNELSSTATE, stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("获取通道状态 失败：" + ToolKits.getErrorCode());
            return null;
        } else {
            stuOut.read();
            return stuOut;
        }
    }

    //警号状态
    public void getOutPutState() {
        // 入参
        NET_IN_GET_OUTPUT_STATE stuIn = new NET_IN_GET_OUTPUT_STATE();

        /**
         通道类型 {@link EM_OUTPUT_TYPE}
         */
        stuIn.emType = 1;
        stuIn.write();
        // 出参
        NET_OUT_GET_OUTPUT_STATE stuOut = new NET_OUT_GET_OUTPUT_STATE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_GetAlarmRegionInfo(loginHandle, NET_EM_GET_ALARMREGION_INFO.NET_EM_GET_ALARMREGION_INFO_OUTPUTSTATE, stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("获取输出状态 失败：" + ToolKits.getErrorCode());
            return;
        } else {
            stuOut.read();
            log.info("获取输出状态 成功");
            /**
             状态个数
             */
            int nStateRet = stuOut.nStateRet;

            log.info("状态个数:" + nStateRet);
            /**
             状态false (0):关闭true(1)打开
             */
            byte[] arrStates = stuOut.arrStates;

            /**
             状态个数扩展 超过82个使用这个字段
             */
            int nStateRetEx = stuOut.nStateRetEx;

            if (nStateRetEx > 82) {


                int[] arrStatesEx = stuOut.arrStatesEx;

                for (int i = 0; i < nStateRetEx; i++) {
                    log.info("[" + i + "]" + (arrStatesEx[i] == 0 ? false : true));
                }

            } else {
                for (int i = 0; i < nStateRet; i++) {
                    log.info("[" + i + "]" + (arrStates[i] == 0 ? false : true));
                }

            }


        }
    }

    //警号状态
    public void setOutPutState() {
        // 入参
        NET_IN_SET_OUTPUT_STATE stuIn = new NET_IN_SET_OUTPUT_STATE();

        /**
         通道类型 {@link EM_OUTPUT_TYPE}
         */
        stuIn.emType = EM_OUTPUT_TYPE.EM_OUTPUT_TYPE_ALARMOUT.getValue();
        /**
         * emType= EM_OUTPUT_TYPE_SIREN时表示警号号 emType= EM_OUTPUT_TYPE_ALARMOUT时表示通道号
         */
        stuIn.nChannel = 1;
        /**
         * 输出动作 false:关闭 true:打开
         */
        stuIn.action = 1;
        stuIn.write();
        // 出参
        NET_OUT_SET_OUTPUT_STATE stuOut = new NET_OUT_SET_OUTPUT_STATE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_SetAlarmRegionInfo(loginHandle, NET_EM_SET_ALARMREGION_INFO.NET_EM_SET_ALARMREGION_INFO_OUTPUTSTATE.getValue(), stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("下发输出状态 失败，错误码：" + ToolKits.getErrorCode());
            return;
        } else {
            stuOut.read();
            log.info("下发输出状态 成功");
        }
    }

    //获取旁路状态
    public void getPassMode() {

        // 入参
        NET_IN_GET_BYPASSMODE stuIn = new NET_IN_GET_BYPASSMODE();

        /**
         防区个数
         */
        stuIn.nZoneNum = 1;
        /**
         防区号, 从1开始
         */
        stuIn.arrZones[0] = 1;

        stuIn.write();
        // 出参
        NET_OUT_GET_BYPASSMODE stuOut = new NET_OUT_GET_BYPASSMODE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_GetAlarmRegionInfo(loginHandle, NET_EM_GET_ALARMREGION_INFO.NET_EM_GET_ALARMREGION_INFO_BYPASSMODE, stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("获取旁路状态 失败：" + ToolKits.getErrorCode());
            return;
        } else {
            stuOut.read();
            log.info("获取旁路状态 成功");
            /**
             防区个数
             */
            int nZoneRet = stuOut.nZoneRet;

            log.info("防区个数:" + nZoneRet);

            /**
             防区工作模式     {@link EM_BYPASSMODE_TYPE}
             */
            int[] arrModes = stuOut.arrModes;

            /**
             防区个数扩展
             */
            int nZoneRetEx = stuOut.nZoneRetEx;

            /**
             防区号扩展  超过72使用这个字段  {@link EM_BYPASSMODE_TYPE}
             */
            int[] arrModesEx = stuOut.arrModesEx;

            if (nZoneRetEx > 72) {
                for (int i = 0; i < nZoneRetEx; i++) {
                    log.info("[" + i + "]" + (EM_BYPASSMODE_TYPE.getNoteByValue(arrModesEx[i])));
                }
            } else {
                for (int i = 0; i < nZoneRet; i++) {
                    log.info("[" + i + "]" + (EM_BYPASSMODE_TYPE.getNoteByValue(arrModes[i])));
                }
            }
        }
    }

    //获取旁路状态
    public void setPassMode() {

        // 入参
        NET_IN_SET_BYPASSMODE stuIn = new NET_IN_SET_BYPASSMODE();
        /**
         * 旁路模式 {@link EM_BYPASSMODE_TYPE}
         */
        stuIn.emType = EM_BYPASSMODE_TYPE.EM_BYPASSMODE_TYPE_ACTIVE.getValue();
        /**
         防区个数
         */
        stuIn.nZoneNum = 1;
        /**
         防区号, 从1开始
         */
        stuIn.arrZones[0] = 1;
        /**
         * 密码
         */
        String szPwd = "admin123";
        System.arraycopy(szPwd.getBytes(), 0, stuIn.szPwd, 0, szPwd.getBytes().length);


        stuIn.write();
        // 出参
        NET_OUT_SET_BYPASSMODE stuOut = new NET_OUT_SET_BYPASSMODE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_SetAlarmRegionInfo(loginHandle, NET_EM_SET_ALARMREGION_INFO.NET_EM_SET_ALARMREGION_INFO_BYPASSMODE.getValue(), stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("下发旁路状态 失败：" + ToolKits.getErrorCode());
        } else {
            log.info("下发旁路状态 成功");
        }
    }

    /**
     * 防区分组状态
     */
    public void getArmMode() {
        // 入参
        NET_IN_GET_ALARMMODE stuIn = new NET_IN_GET_ALARMMODE();
        stuIn.write();

        // 出参
        NET_OUT_GET_ALARMMODE stuOut = new NET_OUT_GET_ALARMMODE();
        stuOut.write();
        Boolean bRet = netsdk.CLIENT_GetAlarmRegionInfo(loginHandle, NET_EM_GET_ALARMREGION_INFO.NET_EM_GET_ALARMREGION_INFO_ARMMODE, stuIn.getPointer(), stuOut.getPointer(), 3000);
        if (!bRet) {
            log.error("获取布防状态失败：" + ToolKits.getErrorCode());
            return;
        } else {
            stuOut.read();
            log.info("获取布防状态成功");
            log.info("布撤防状态个数:" + stuOut.nArmModeRetEx);
            NET_ARMMODE_INFO[] stuArmModeEx = stuOut.stuArmModeEx;
            for (int i = 0; i < stuOut.nArmModeRetEx; i++) {
                log.info("Area号:" + (i + 1));
                log.info("布撤防状态:" + EM_ARM_STATE.getNoteByValue(stuArmModeEx[i].emArmState));
            }

        }

    }

    // 设置布防模式
    public void setArmMode() throws UnsupportedEncodingException {
        NET_IN_SET_ALARMMODE stuInParam = new NET_IN_SET_ALARMMODE();
        stuInParam.emArmType = EM_ARM_TYPE.EM_ARM_TYPE_DISARMING.getValue();
        String szPwd = "admin123";
        System.arraycopy(szPwd.getBytes(), 0, stuInParam.szPwd, 0, szPwd.getBytes().length);

        stuInParam.nAreaNum = 1;
        stuInParam.arrAreas[0] = 1;
        stuInParam.stuDetail.stuArmOption.emSceneMode = NET_EM_SCENE_MODE.NET_EM_SCENE_MODE_INDOOR.getValue();
        stuInParam.stuDetail.stuArmOption.emAreaarmTriggerMode = EM_AREAARM_TRIGGERMODE.EM_AREAARM_TRIGGERMODE_REMOTE
                .getValue();
        stuInParam.stuDetail.stuArmOption.nId = 1;
        String szName = "test";
        System.arraycopy(szName.getBytes(encode), 0, stuInParam.stuDetail.stuArmOption.szName, 0,
                szName.getBytes(encode).length);
        String szClientAddress = "172.24.104.191";
        System.arraycopy(szClientAddress.getBytes(encode), 0, stuInParam.stuDetail.stuArmOption.szClientAddress, 0,
                szClientAddress.getBytes(encode).length);
        stuInParam.write();
        NET_OUT_SET_ALARMMODE stOutParam = new NET_OUT_SET_ALARMMODE();
        // 布防失败的细节扩展字段 布防个数大于8个使用此字段，pstuFailedDetailEx需要初始化,字段指针对应结构体{ @link
        // ARM_FAILED_DETAIL_EX}数组
        stOutParam.write();
        boolean bRet1 = netsdk.CLIENT_SetAlarmRegionInfo(loginHandle,
                NET_EM_SET_ALARMREGION_INFO.NET_EM_SET_ALARMREGION_INFO_ARMMODE.getValue(), stuInParam.getPointer(),
                stOutParam.getPointer(), 5000);
        if (bRet1) {
            log.info("下发布防状态成功");
            stOutParam.read();
            log.info(" 布防结果（ 0:成功 1:失败 ）：" + stOutParam.nArmResult);
        } else {
            log.error("下发布防状态失败，错误码:" + ToolKits.getErrorCode());
        }
    }

    // 获取单防区布撤防状态
    public NET_OUT_GET_ZONE_ARMODE_INFO getZoneArmMode() {
        NET_IN_GET_ZONE_ARMODE_INFO input = new NET_IN_GET_ZONE_ARMODE_INFO();
        //要操作的防区号列表个数
        input.nZoneNum = 1;
        // 防区号，数组中第一个元素为-1表示获取所有通道的布撤防状态
        input.nZones[0] = -1;
        input.write();
        NET_OUT_GET_ZONE_ARMODE_INFO outPut = new NET_OUT_GET_ZONE_ARMODE_INFO();
        outPut.write();
        boolean b = netsdk.CLIENT_GetZoneArmMode(loginHandle, input.getPointer(), outPut.getPointer(), 3000);
        if (!b) {
            log.error("获取单防区布撤防状态 fail：" + ToolKits.getErrorCode());
            return null;
        } else {
            log.info("获取单防区布撤防状态 success");
            outPut.read();
            return outPut;
        }
    }

    /**
     * 设置单防区布撤防状态
     *
     * @param zoneNo  防区号
     * @param armMode D是撤防，T是布防
     */
    public void setZoneArmMode(int zoneNo, String armMode) {
        NET_IN_SET_ZONE_ARMODE_INFO input = new NET_IN_SET_ZONE_ARMODE_INFO();
        input.nZoneNum = 1;
        input.nZones[0] = zoneNo;//17
        byte[] szPwd = input.szPwd;
        String pwd = "admin123456";// admin123456
        StringToByteArr(pwd, szPwd, encode);
        byte[] szArmMode = input.szArmMode;
        StringToByteArr(armMode, szArmMode, encode);
        NET_ARM_DETAIL_OPTIONS stuArmDetailOptions
                = input.stuArmDetailOptions;
        stuArmDetailOptions.emProfile = NET_EM_SCENE_MODE.NET_EM_SCENE_MODE_WHOLE.getValue();
        stuArmDetailOptions.emTriggerMode = EM_AREAARM_TRIGGERMODE.EM_AREAARM_TRIGGERMODE_USER.getValue();
        byte[] szName = stuArmDetailOptions.szName;
        String name = "admin";//admin
        StringToByteArr(name, szName, encode);
        byte[] szClientAddress = stuArmDetailOptions.szClientAddress;
        String clientAddress = "192.168.1.3";//实际客户端ip，慧云服务器ip
        StringToByteArr(clientAddress, szClientAddress, encode);
        input.stuArmDetailOptions.nID = 1;
        input.write();
        NET_OUT_SET_ZONE_ARMODE_INFO outPut = new NET_OUT_SET_ZONE_ARMODE_INFO();
        outPut.nAreaAbnormalNum = input.nZoneNum;
        NET_AREA_ABNORMAL_INFO[] stuAbnormalInfo = new NET_AREA_ABNORMAL_INFO[input.nZoneNum];
        for (int i = 0; i < input.nZoneNum; i++) {
            stuAbnormalInfo[i] = new NET_AREA_ABNORMAL_INFO();
        }
        long MemorySize = new NET_AREA_ABNORMAL_INFO().size() * input.nZoneNum;
        outPut.pstuAreaAbnormal = new Memory(MemorySize);
        outPut.pstuAreaAbnormal.clear(MemorySize);
        ToolKits.SetStructArrToPointerData(stuAbnormalInfo, outPut.pstuAreaAbnormal);
        outPut.write();
        boolean b = netsdk.CLIENT_SetZoneArmMode(loginHandle, input.getPointer(), outPut.getPointer(), 3000);
        if (!b) {
            log.error("设置单防区布撤防状态 fail：" + ToolKits.getErrorCode());
            outPut.read();
            if (outPut.nAreaAbnormalRetNum > 0) {
                ToolKits.GetPointerDataToStructArr(outPut.pstuAreaAbnormal, stuAbnormalInfo);
                for (int i = 0; i < outPut.nAreaAbnormalRetNum; i++) {
                    //此处打印各类错误信息
                    log.error(stuAbnormalInfo[i].toString());
                }
            }
        } else {
            log.info("设置单防区布撤防状态 success");

        }
    }

    //设置单防区布撤防状态
    public void setZoneArmMode() {

        NET_IN_SET_ZONE_ARMODE_INFO input = new NET_IN_SET_ZONE_ARMODE_INFO();

        /**
         要操作的防区号列表个数
         */
        input.nZoneNum = 1;
        /**
         要操作的防区号列表，从1开始。
         */
        input.nZones[0] = 17;//17
//        input.nZones[1] = 2;
        /**
         密码明文
         */
        byte[] szPwd = input.szPwd;

        String pwd = "123pwd";// admin123456

        StringToByteArr(pwd, szPwd, encode);

        byte[] szArmMode = input.szArmMode;

        String armMode = "T";

        StringToByteArr(armMode, szArmMode, encode);

        NET_ARM_DETAIL_OPTIONS stuArmDetailOptions
                = input.stuArmDetailOptions;
        /**
         情景模式 {@link NET_EM_SCENE_MODE}
         */
        stuArmDetailOptions.emProfile = NET_EM_SCENE_MODE.NET_EM_SCENE_MODE_WHOLE.getValue();
        /**
         触发方式 {@link EM_AREAARM_TRIGGERMODE}
         */
        stuArmDetailOptions.emTriggerMode = EM_AREAARM_TRIGGERMODE.EM_AREAARM_TRIGGERMODE_USER.getValue();


        byte[] szName
                = stuArmDetailOptions.szName;

        String name = "user1";//admin

        StringToByteArr(name, szName, encode);

        byte[] szClientAddress
                = stuArmDetailOptions.szClientAddress;

        String clientAddress = "10.33.121.74";//实际客户端ip，慧云服务器ip

        StringToByteArr(clientAddress, szClientAddress, encode);

        input.stuArmDetailOptions.nID = 1;

        input.write();

        NET_OUT_SET_ZONE_ARMODE_INFO outPut = new NET_OUT_SET_ZONE_ARMODE_INFO();

        outPut.nAreaAbnormalNum = input.nZoneNum;

        NET_AREA_ABNORMAL_INFO[] stuAbnormalInfo = new NET_AREA_ABNORMAL_INFO[input.nZoneNum];
        for (int i = 0; i < input.nZoneNum; i++) {
            stuAbnormalInfo[i] = new NET_AREA_ABNORMAL_INFO();
        }

        long MemorySize = new NET_AREA_ABNORMAL_INFO().size() * input.nZoneNum;
        outPut.pstuAreaAbnormal = new Memory(MemorySize);
        outPut.pstuAreaAbnormal.clear(MemorySize);

        ToolKits.SetStructArrToPointerData(stuAbnormalInfo, outPut.pstuAreaAbnormal);

        outPut.write();


        boolean b
                = netsdk.CLIENT_SetZoneArmMode(loginHandle, input.getPointer(), outPut.getPointer(), 3000);
        if (!b) {
            log.error("设置单防区布撤防状态 fail：" + ToolKits.getErrorCode());
            outPut.read();
            if (outPut.nAreaAbnormalRetNum > 0) {
                ToolKits.GetPointerDataToStructArr(outPut.pstuAreaAbnormal, stuAbnormalInfo);
                for (int i = 0; i < outPut.nAreaAbnormalRetNum; i++) {
                    //此处打印各类错误信息
                    log.error(stuAbnormalInfo[i].toString());
                }
            }

        } else {
            log.info("设置单防区布撤防状态 success");

        }
    }

    public void startListenAlarm() {
        /// Alarm CallBack
        netsdk.CLIENT_SetDVRMessCallBack(MessCallBack.getInstance(), null);

        boolean b
                = netsdk.CLIENT_StartListenEx(loginHandle);
        if (!b) {
            log.error("CLIENT_StartListenEx Failed." + ToolKits.getErrorCode());
        }
    }

    /**
     * StopListen
     */
    public void stopListenAlarm() {

        boolean b
                = netsdk.CLIENT_StopListen(loginHandle);
        if (!b) {
            log.error("CLIENT_StopListen Failed." + ToolKits.getErrorCode());
        }
    }

    /**
     * 全局消警
     */
    public void clearAlarmAll() {
        NetSDKLib.NET_CTRL_CLEAR_ALARM info = new NetSDKLib.NET_CTRL_CLEAR_ALARM();
        info.emAlarmType = NetSDKLib.NET_ALARM_TYPE.NET_ALARM_ALL; // 所有报警事件
        info.nChannelID = -1;


        info.write();
        boolean success = netsdk.CLIENT_ControlDevice(loginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLEAR_ALARM,
                info.getPointer(), 3000);
        info.read();
        if (!success) {
            log.error("Failed to clean alarm " + netsdk.CLIENT_GetLastError() + info);
        } else {
            log.info("Alarm clean successfully");
        }
    }

    /**
     * 子系统消警
     */
    public void clearAlarmSubsystem() {
        NetSDKLib.NET_CTRL_CLEAR_ALARM info = new NetSDKLib.NET_CTRL_CLEAR_ALARM();

        info.bEventType = 1;
        info.nEventType = NetSDKLib.NET_ALARM_ALARM_EX2; //本地报警事件
        // info.emAlarmType = 0;

        info.nChannelID = 2;// 子系统号

        info.bIsUsingName = 1;
        String name = "AlarmArea";
        System.arraycopy(name.getBytes(), 0, info.szName, 0, name.getBytes().length);

        info.write();
        boolean success = netsdk.CLIENT_ControlDevice(loginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLEAR_ALARM,
                info.getPointer(), 3000);
        info.read();
        if (!success) {
            log.error("Failed to clean alarm " + netsdk.CLIENT_GetLastError() + info);
        } else {
            log.info("Alarm clean successfully");
        }
    }

    /**
     * 单防区消警
     */
    public void clearAlarmEx() {
        NetSDKLib.NET_CTRL_CLEAR_ALARM info = new NetSDKLib.NET_CTRL_CLEAR_ALARM();
        info.bEventType = 1;
        info.nEventType = NetSDKLib.NET_ALARM_ALARM_EX2;//本地报警事件
        // info.emAlarmType = 0;

        info.nChannelID = 0;//防区号
        info.write();
        boolean success = netsdk.CLIENT_ControlDevice(loginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_CLEAR_ALARM,
                info.getPointer(), 3000);
        info.read();
        if (!success) {
            log.error("Failed to clean alarm " + netsdk.CLIENT_GetLastError() + info);
        } else {
            log.info("Alarm clean successfully");
        }
    }

    public void runTest() {
        log.info("Run Test");
        CaseMenu menu = new CaseMenu();
        menu.addItem(new CaseMenu.Item(this, "IO输入端状态", "channelsState"));

        menu.addItem(new CaseMenu.Item(this, "获取警号状态", "getOutPutState"));
        menu.addItem(new CaseMenu.Item(this, "下发警号状态", "setOutPutState"));

        menu.addItem(new CaseMenu.Item(this, "获取旁路状态", "getPassMode"));
        menu.addItem(new CaseMenu.Item(this, "下发旁路状态", "setPassMode"));

        menu.addItem(new CaseMenu.Item(this, "获取防区分组状态", "getArmMode"));
        menu.addItem(new CaseMenu.Item(this, "下发防区分组状态", "setArmMode"));

        menu.addItem(new CaseMenu.Item(this, "获取单防区布撤防状态", "getZoneArmMode"));
        menu.addItem(new CaseMenu.Item(this, "设置单防区布撤防状态", "setZoneArmMode"));

        menu.addItem(new CaseMenu.Item(this, "开启不带图事件监听（电话断线、键盘防拆、防区故障等）", "startListenAlarm"));
        menu.addItem(new CaseMenu.Item(this, "结束不带图事件监听", "stopListenAlarm"));

        menu.addItem(new CaseMenu.Item(this, "全局消警", "clearAlarmAll"));
        menu.addItem(new CaseMenu.Item(this, "子系统消警", "clearAlarmSubsystem"));
        menu.addItem(new CaseMenu.Item(this, "单防区消警", "clearAlarmEx"));

        menu.run();
    }

    /**
     * 初始化测试
     */
    public void initTest() {
        initSdk();

        this.loginWithHighLevel();
    }

    /**
     * 结束测试
     */
    public void endTest() {
        log.info("End Test");
        this.logOut(); // 登出设备
        log.info("See You...");
        cleanAndExit(); // 清理资源并退出
    }
}

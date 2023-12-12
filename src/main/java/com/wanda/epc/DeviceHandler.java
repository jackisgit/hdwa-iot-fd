package com.wanda.epc;

import com.alibaba.fastjson.JSON;
import com.netsdk.lib.NetSDKLib;
import com.netsdk.lib.ToolKits;
import com.netsdk.lib.enumeration.EM_ARM_STATE;
import com.netsdk.lib.enumeration.NET_EM_GET_ALARMREGION_INFO;
import com.netsdk.lib.structure.*;
import com.netsdk.module.ArmDisarmParamConfigDemo;
import com.netsdk.module.DefaultDisConnect;
import com.netsdk.module.DefaultHaveReconnect;
import com.netsdk.module.LoginModule;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.wanda.epc.device.BaseDevice;
import com.wanda.epc.device.CommonDevice;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.PingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 大华设备
 * @date 2023/4/11 09:55:13
 */
@Slf4j
@Service
public class DeviceHandler extends BaseDevice {


    @Autowired
    RedisTemplate redisTemplate;
    List<NetSDKLib.LLong> LoginHandleList = new ArrayList<>();
    HashMap<NetSDKLib.LLong, String> loginMap = new HashMap<>();
    HashMap<String, NetSDKLib.LLong> userMap = new HashMap<>();
    Set<String> ipSet = new HashSet<>();
    @Autowired
    private CommonDevice commonDevice;
    @Value("${m_strUser}")
    private String m_strUser;

    @Value("${m_strPassword}")
    private String m_strPassword;

    @Value("${m_nPort}")
    private int m_nPort;


    @PostConstruct
    public void init() {
        loginMap.clear();
        userMap.clear();
        LoginModule.init(DefaultDisConnect.GetInstance(), DefaultHaveReconnect.getINSTANCE());
        deviceParamListMap.entrySet().forEach(key -> {
            String ip = key.getKey();
            String[] param = ip.split("_");
            String doorIp = param[0];
            ipSet.add(doorIp);
        });
        devicesLogin(ipSet);
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
    }

    @Override
    public boolean processData() throws Exception {
        Queue<String> allIp = new LinkedList();
        deviceParamListMap.entrySet().forEach(entry -> {
            List<String> ipList = Arrays.asList(entry.getKey().split("_"));
            if (ipList.size() == 2) {
                String online = ipList.get(1);
                if ("onlineStatus".equals(online)) {
                    allIp.offer(ipList.get(0));
                }
            }
        });
        ping(allIp);
        getArmMode();
        return true;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接收到指令下发：{},状态：{}", JSON.toJSONString(deviceMessage), value);
        if (ObjectUtils.isEmpty(deviceMessage)) {
            return;
        }
        String outParamId = deviceMessage.getOutParamId();
        String[] param = outParamId.split("_");
        String ip = param[0];
        NetSDKLib.LLong userIp = userMap.get(ip);
        int order = 1;
        if (!value.equals("1.0")) {
            order = 0;
        }
        setBufangEx(userIp, order);
    }


    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    /**
     * 订阅报警信息
     */
    public void startListen(NetSDKLib.LLong loginHandler) {
        // 设置报警回调函数
        LoginModule.netsdk.CLIENT_SetDVRMessCallBack(fAlarmAccessDataCB.getInstance(), null);
        // 订阅报警
        boolean bRet = LoginModule.netsdk.CLIENT_StartListenEx(loginHandler);
        if (!bRet) {
            log.error("订阅报警失败! LastError = 0x%x\n" + LoginModule.netsdk.CLIENT_GetLastError());
        } else {
            log.info("订阅报警成功.");
        }
    }

    /**
     * 取消订阅报警信息
     *
     * @return
     */
    public void stopListen(NetSDKLib.LLong loginHandler) {
        // 停止订阅报警
        boolean bRet = LoginModule.netsdk.CLIENT_StopListen(loginHandler);
        if (bRet) {
            log.info("取消订阅报警信息.");
        }
    }

    /**
     * 下发布撤防操作
     */
    public void setBufangEx(NetSDKLib.LLong m_hLoginHandle, int emState) {
        CTRL_ARM_DISARM_PARAM_EX param = new CTRL_ARM_DISARM_PARAM_EX();
        CTRL_ARM_DISARM_PARAM_EX_IN in = new CTRL_ARM_DISARM_PARAM_EX_IN();
        // 布撤防状态    撤防 0  布防 1   强制布防 2  部分布防 3
        in.emState = emState;
        // 用户密码
        //Win下，将GBK String类型的转为Pointer ;Linux下 UTF-8
        Pointer szDevPwd = ToolKits.GetGBKStringToPointer(m_strPassword);
        in.szDevPwd = szDevPwd;
        // 情景模式  未知场景 0   外出模式 1 室内模式 2 全局模式 3 立即模式4  就寝模式 5 自定义模式 6
        in.emSceneMode = 1;
        param.stuIn = in;
        param.write();
        boolean flg = LoginModule.netsdk.CLIENT_ControlDevice(
                m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_ARMED_EX, param.getPointer(), 5000);
        if (flg) {
            log.info("下发布撤防操作成功");
            param.read();
            CTRL_ARM_DISARM_PARAM_EX_OUT stuOut = param.stuOut;
            log.info("有报警源输入布防失败的防区个数:" + stuOut.dwSourceNum);
            log.info("有联动报警布防失败的防区个数:" + stuOut.dwLinkNum);
        } else {
            log.error("下发布撤防操作失败:" + ToolKits.getErrorCodeShow());
        }
    }

    /**
     * 获取布防状态（三代主机）
     */
    private void getArmMode() {
        LoginHandleList.forEach(handle -> {
            // 入参
            NET_IN_GET_ALARMMODE stuIn = new NET_IN_GET_ALARMMODE();
            stuIn.write();
            // 出参
            NET_OUT_GET_ALARMMODE stuOut = new NET_OUT_GET_ALARMMODE();
            stuOut.write();
            Boolean bRet = LoginModule.netsdk.CLIENT_GetAlarmRegionInfo(handle, NET_EM_GET_ALARMREGION_INFO.NET_EM_GET_ALARMREGION_INFO_ARMMODE, stuIn.getPointer(), stuOut.getPointer(), 3000);
            if (!bRet) {
                log.error("获取布防状态失败：" + ToolKits.getErrorCodeShow());
                return;
            } else {
                stuOut.read();
                log.info("获取布防状态成功,布撤防状态个数:" + stuOut.nArmModeRetEx);
                NET_ARMMODE_INFO[] stuArmModeEx = stuOut.stuArmModeEx;
                for (int i = 0; i < stuOut.nArmModeRetEx; i++) {
                    log.info("Area号:{},布撤防状态:{}", (i + 1), EM_ARM_STATE.getNoteByValue(stuArmModeEx[i].emArmState));
                }
            }
        });
    }

    /**
     * 设备登录
     */
    private void devicesLogin(Set<String> devices) {
        log.info("登录信息：{}", devices.size());
        for (String ip : devices) {
            try {
                NetSDKLib.LLong loginHandle = LoginModule.login(ip, m_nPort, m_strUser, m_strPassword);
                boolean login = loginHandle.longValue() == 0 ? false : true;
                if (login) {
                    log.info("设备ip:{}登录成功 账号：{} 密码：{} 端口：{} 用户ID：{}", ip, m_strUser, m_strPassword, m_nPort, loginHandle);
                    NetSDKLib.LLong myKey = new NetSDKLib.LLong(loginHandle.longValue());
                    LoginHandleList.add(myKey);
                    loginMap.put(myKey, ip);
                    userMap.put(ip, myKey);
                    startListen(loginHandle);
                } else {
                    log.info("设备ip:{}登录失败 账号：{} 密码：{} 端口：{}", ip, m_strUser, m_strPassword, m_nPort);
                }
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 设备退出
     */
    private void devicesLogOut() {
        LoginHandleList.forEach(handle -> {
            try {
                LoginModule.logout(handle);
                stopListen(handle);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        log.info("SDK实例销毁!");
        devicesLogOut();
        LoginModule.cleanup();
    }

    /**
     * ping
     *
     * @param allIp
     */
    private void ping(Queue<String> allIp) {
        log.info("开始采集在线离线状态,ip数量{}", allIp.size());
        PingUtil pingUtil = new PingUtil(allIp);
        pingUtil.setIpsOK("");
        pingUtil.setIpsNO("");
        pingUtil.startPing();
        String ipsOK = pingUtil.getIpsOK();
        String ipsNo = pingUtil.getIpsNO();
        if (StringUtils.isNotBlank(ipsOK)) {
            List<String> ipList = Arrays.asList(ipsOK.split(","));
            log.info("在线数量：{}", ipList.size());
            ipList.forEach(ip -> {
                sendMsg(ip.concat("_onlineStatus"), "1");
            });
        }
        if (StringUtils.isNotBlank(ipsNo)) {
            List<String> ipList = Arrays.asList(ipsNo.split(","));
            log.info("离线数量：{}", ipList.size());
            ipList.forEach(ip -> {
                sendMsg(ip.concat("_onlineStatus"), "0");
            });
        }
    }

    /**
     * 发送消息
     *
     * @param outParamId
     * @param value
     */
    private void sendMsg(String outParamId, String value) {
        List<DeviceMessage> deviceMessageList = deviceParamListMap.get(outParamId);
        if (!CollectionUtils.isEmpty(deviceMessageList)) {
            deviceMessageList.forEach(deviceMessage -> {
                deviceMessage.setValue(value);
                sendMessage(deviceMessage);
            });
        }
    }

    /**
     * 报警事件回调
     */
    private static abstract class fAlarmAccessDataCB implements NetSDKLib.fMessCallBack {
        private static ArmDisarmParamConfigDemo.fAlarmAccessDataCB instance = new ArmDisarmParamConfigDemo.fAlarmAccessDataCB();

        private fAlarmAccessDataCB() {
        }

        public static ArmDisarmParamConfigDemo.fAlarmAccessDataCB getInstance() {
            return instance;
        }

        @Override
        public boolean invoke(int lCommand, NetSDKLib.LLong lLoginID, Pointer pStuEvent, int dwBufLen, String strDeviceIP,
                              NativeLong nDevicePort, Pointer dwUser) {
            log.info(">> Event invoke. alarm command 0x" + Integer.toHexString(lCommand));
            switch (lCommand) {
                case NetSDKLib.NET_ALARM_ALARM_EX2: {
                    // 本地报警事件
                    NetSDKLib.ALARM_ALARM_INFO_EX2 msg = new NetSDKLib.ALARM_ALARM_INFO_EX2();
                    ToolKits.GetPointerData(pStuEvent, msg);
                    log.info("Event: ALARM_ALARM_INFO_EX2" + msg);
                    break;
                }
                case NetSDKLib.NET_ALARM_ARMMODE_CHANGE_EVENT: {
                    // 设备布防模式变化事件
                    NetSDKLib.ALARM_ARMMODE_CHANGE_INFO msg = new NetSDKLib.ALARM_ARMMODE_CHANGE_INFO();
                    ToolKits.GetPointerData(pStuEvent, msg);
                    log.info("Event: NET_ALARM_ARMMODE_CHANGE_EVENT" + msg);
                    break;
                }
                case NetSDKLib.NET_ALARM_ALARMCLEAR: {
                    // 消警报警
                    NetSDKLib.ALARM_ALARMCLEAR_INFO msg = new NetSDKLib.ALARM_ALARMCLEAR_INFO();
                    ToolKits.GetPointerData(pStuEvent, msg);
                    log.info("Event: ALARAM CLEAR." + msg);
                    break;
                }
                case NetSDKLib.NET_ALARM_ALARM_EX: {
                    // 持续的报警事件 ,用户可以设置开关选择是否消警
                    new Thread(() -> {
                        //device.clearAlarm(NetSDKLib.NET_ALARM_ALARM_EX);
                    }).start();
                }
                default:
                    break;
            }
            return true;
        }
    }
}

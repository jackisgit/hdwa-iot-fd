package com.wanda.epc.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author 孙率众
 */
@Service
@Slf4j
public class DeviceHandler extends BaseDevice {

    /**
     * 撤布防设定
     */
    public static final String DEPLOY_WITH_DRAW_ALARM_SET = "_deployWithdrawAlarmSet";
    /**
     * 报警状态
     */
    public static final String IS_ALARM = "_isAlarm";
    /**
     * 撤布防反馈
     */
    public static final String DEPLOY_WITH_DRAW_ALARM_SET_FEEDBACK = "_deployWithdrawAlarmSetFeedback";
    /**
     * 在线状态后缀
     */
    public static final String ONLINE_STATUS = "_onlineStatus";
    /**
     * 故障状态后缀
     */
    public static final String FAULT_STATUS = "_faultStatus";

    /**
     * 接口前缀
     */
    private static final String ARTEMIS_PATH = "/artemis";

    /**
     * 子系统集合
     */
    Set<String> subSystemSet = new HashSet<>();
    /**
     * 防区集合
     */
    Set<String> defenceSet = new HashSet<>();

    @Value("${epc.host}")
    private String host;
    @Value("${epc.appKey}")
    private String appKey;
    @Value("${epc.appSecret}")
    private String appSecret;
    @Value("${epc.search}")
    private String search;
    @Value("${epc.control}")
    private String control;
    @Value("${epc.subSysStatus}")
    private String subSysStatus;
    @Value("${epc.defenceStatus}")
    private String defenceStatus;

    /**
     * https://ip:port/artemis/api/resource/v1/org/orgList
     * 通过查阅AI Cloud开放平台文档或网关门户的文档可以看到获取组织列表的接口定义,该接口为POST请求的Rest接口, 入参为JSON字符串，接口协议为https。
     * ArtemisHttpUtil工具类提供了doPostStringArtemis调用POST请求的方法，入参可传JSON字符串, 请阅读开发指南了解方法入参，没有的参数可传null
     */
    public String sendHttps(String url, Map<String, Object> paramMap) {
        try {
            ArtemisConfig config = new ArtemisConfig();
            // 代理API网关nginx服务器ip端口
            config.setHost(host);
            // 秘钥appkey
            config.setAppKey(appKey);
            // 秘钥appSecret
            config.setAppSecret(appSecret);
            final String getCamsApi = ARTEMIS_PATH + url;
            String body = JSON.toJSON(paramMap).toString();
            Map<String, String> path = new HashMap<String, String>(2) {
                {
                    put("https://", getCamsApi);
                }
            };
            return ArtemisHttpUtil.doPostStringArtemis(config, path, body, null, null, "application/json");
        } catch (Exception e) {
            log.error("调用接口:{}，参数:{}异常", url, JSONObject.toJSONString(paramMap), e);
            return null;
        }
    }

    @PostConstruct
    public void init() {
        // post请求Form表单参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNo", "1");
        paramMap.put("pageSize", "200");
        String result = sendHttps(search, paramMap);
        log.info("接口:{},参数:{},返回:{}", search, JSONObject.toJSONString(paramMap), result);
        deviceParamListMap.forEach((key, value) -> {
            if (key.contains(DEPLOY_WITH_DRAW_ALARM_SET)) {
                subSystemSet.add(key.split("_")[0]);
            } else {
                defenceSet.add(key.split("_")[0]);
            }
        });
    }

    @Override
    public boolean processData() {
        get();
        get2();
        return true;
    }

    private void get() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("defenceIndexCodes", defenceSet);
        String result = sendHttps(defenceStatus, paramMap);
        log.info("接口:{},参数:{},返回:{}", defenceStatus, JSONObject.toJSONString(paramMap), result);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JSONPath.read(result, "$.data.list");
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (Map<String, Object> map : list) {
            String defenceIndexCode = String.valueOf(map.get("defenceIndexCode"));
            //防区状态：-1: 未知 0: 离线 1: 正常 2: 故障 3: 报警 4: 旁路
            String status = String.valueOf(map.get("status"));
            String onlineStatus = "1", faultStatus = "0", isAlarm = "0";
            if ("0".equals(status)) {
                onlineStatus = "0";
            } else if ("2".equals(status)) {
                faultStatus = "1";
            } else if ("3".equals(status)) {
                isAlarm = "1";
            }
            sendMsg(defenceIndexCode + ONLINE_STATUS, onlineStatus);
            sendMsg(defenceIndexCode + FAULT_STATUS, faultStatus);
            sendMsg(defenceIndexCode + IS_ALARM, isAlarm);
        }
    }

    private void get2() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("subSysIndexCodes", subSystemSet);
        String result = sendHttps(subSysStatus, paramMap);
        log.info("接口:{},参数:{},返回:{}", subSysStatus, JSONObject.toJSONString(paramMap), result);
        List<Map<String, Object>> list = (List<Map<String, Object>>) JSONPath.read(result, "$.data.list");
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (Map<String, Object> map : list) {
            String subSystemIndexCode = String.valueOf(map.get("subSystemIndexCode"));
            String status = String.valueOf(map.get("status"));
            sendMsg(subSystemIndexCode + DEPLOY_WITH_DRAW_ALARM_SET_FEEDBACK, status);
        }
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        if (ObjectUtils.isNotEmpty(deviceMessage) && StringUtils.isNotBlank(deviceMessage.getOutParamId())
                && deviceMessage.getOutParamId().endsWith(DEPLOY_WITH_DRAW_ALARM_SET)) {
            String outParamId = deviceMessage.getOutParamId();
            if (redisUtil.hasKey(outParamId)) {
                return;
            }
            redisUtil.set(outParamId, "0", 5);
            final String[] strings = deviceMessage.getOutParamId().split("_");
            control(strings[0], value);
        }
    }

    public void control(String doorIndexCode, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("doorIndexCodes", doorIndexCode);
        int status = 0;
        if ("1.0".equals(value)) {
            status = 1;
        }
        map.put("status", status);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("subSystemList", list);
        String result = sendHttps(control, paramMap);
        log.info("接口:{},参数:{},返回:{}", control, JSONObject.toJSONString(paramMap), result);
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

}

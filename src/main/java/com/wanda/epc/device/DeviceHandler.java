package com.wanda.epc.device;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.wanda.epc.DTO.AlarmDTO;
import com.wanda.epc.DTO.StatusDTO;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孙率众
 */
@Service
@Slf4j
public class DeviceHandler extends BaseDevice {

    /**
     * 获取公钥URI
     */
    private static final String publicKeyURI = "/evo-apigw/evo-oauth/{0}/oauth/public-key";
    /**
     * 认证（获取token）URI
     */
    private static final String tokenURI = "/evo-apigw/evo-oauth/{0}/oauth/extend/token";
    /**
     * 报警订阅事件URI（设置报警回调采集器的URL地址）
     */
    private static final String mqinfoURI = "/evo-apigw/evo-event/{0}/subscribe/mqinfo";
    /**
     * 子系统/防区状态查询URI（获取在离线、报警、撤布防、开关状态） Get请求 拼接防区号
     */
    private static final String statusURI = "/evo-apigw/evo-alarm/{0}/alarmhosts/one/";
    public static final String ALARM_STATUS = "_alarmStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_SET = "_deployWithdrawAlarmSet";
    public static final String ONLINE_STATUS = "_onlineStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_STATUS = "_deployWithdrawAlarmStatus";
    /**
     * 报警主机操作控制URI（获取在离线、报警、撤布防、开关状态） Get请求 fangqu(防区) operate(1布防2撤防)
     */
    private static String operateURI = "/evo-apigw/evo-alarm/{0}/alarmhosts/operate/?nodeCode={1}&operate={2}";
    @Resource
    private CommonDevice commonDevice;
    private Map<String, String> header = new HashMap<>();
    @Value("${epc.host}")
    private String host;
    @Value("${epc.clientId}")
    private String clientId;
    @Value("${epc.clientSecret}")
    private String clientSecret;
    @Value("${epc.grantType}")
    private String grantType;
    @Value("${epc.username}")
    private String username;
    @Value("${epc.password}")
    private String password;
    @Value("${epc.version}")
    private String version;
    @Value("${epc.service}")
    private String service;
    @Value("${epc.localhost}")
    private String localhost;
    @Value("${server.port}")
    private String serverPort;
    @Value("${epc.machineId}")
    private String machineId;
    private String publicKey;
    private String encryptedText;
    private String Authorization;

    public static void main(String[] args) {
        String format = MessageFormat.format(operateURI, 11, 22);
        System.out.println(format);
    }


    /**
     * 初始化获取公钥、token、注册回调地址
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        String url = host + MessageFormat.format(publicKeyURI, version);
        log.info("接口:{}", url);
        String publicKeyResult = HttpRequest.get(url).timeout(2000).execute().body();
        log.info("接口:{},返回值:{}", url, publicKeyResult);
        publicKey = String.valueOf(JSONPath.read(publicKeyResult, "$.data.publicKey"));
        encryptedText = baseEncrypt(publicKey, password);
        getToken();
        String monitor = "http://" + localhost + ":" + serverPort + "/receive";
        String subsystemName = localhost + "_" + serverPort;
        String subsystemMagic = localhost + "_" + serverPort;
        String mqinfo = "{\"param\":{\"monitors\":[{\"monitor\":\"" + monitor + "\",\"monitorType\":\"url\",\"events\":" +
                "[{\"category\":\"alarm\",\"subscribeAll\":1,\"domainSubscribe\":2},{\"category\":\"business\",\"subscribeAll\":1,\"domainSubscribe\":2}" +
                ",{\"category\":\"state\",\"subscribeAll\":1,\"domainSubscribe\":2},{\"category\":\"perception\",\"subscribeAll\":1,\"domainSubscribe\":2}]}]" +
                ",\"subsystem\":{\"subsystemType\":0,\"name\":\"" + subsystemName + "\",\"magic\":\"" + subsystemMagic + "\"}}}";
        String url2 = host + MessageFormat.format(mqinfoURI, version);
        log.info("接口:{},参数:{}", url2, mqinfo);
        String result = HttpRequest.post(url2).body(mqinfo).addHeaders(header).timeout(2000).execute().body();
        log.info("接口:{},返回值:{}", url2, result);
    }

    /**
     * 获取token并设置请求头
     */
    public void getToken() {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("client_id", clientId);
        tokenMap.put("client_secret", clientSecret);
        tokenMap.put("grant_type", grantType);
        tokenMap.put("username", username);
        tokenMap.put("password", encryptedText);
        tokenMap.put("public_key", publicKey);
        String tokenMapStr = JSONObject.toJSONString(tokenMap);
        String url = host + MessageFormat.format(tokenURI, version);
        log.info("接口:{},参数:{}", url, tokenMapStr);
        String tokenResult = HttpUtil.post(url, tokenMapStr, 2000);
        log.info("接口:{},返回值:{}", url, tokenResult);
        Authorization = "bearer " + JSONPath.read(tokenResult, "$.data.access_token");
        header.put("Authorization", Authorization);
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    @Override
    public boolean processData() throws Exception {
        return true;
    }

    /**
     * 采集
     *
     * @throws Exception
     */
    public void collect() throws Exception {
        String[] strings = machineId.split(",");
        String url = host + MessageFormat.format(statusURI, version);
        for (String hostId : strings) {
            log.info("接口:{},头:{}", url + hostId, JSONObject.toJSONString(header));
            String statusResult = HttpRequest.get(url + hostId).addHeaders(header).timeout(2000).execute().body();
            log.info("接口:{},返回值:{}", url + hostId, statusResult);
            Object read = JSONPath.read(statusResult, "$.data.value[0].defenceAreaList");
            List<StatusDTO> statusDTOS = JSONObject.parseArray(String.valueOf(read), StatusDTO.class);
            for (StatusDTO statusDTO : statusDTOS) {
                String status = statusDTO.getStatus();
                String defenceAreaId = statusDTO.getDefenceAreaId();
                String isOnline = statusDTO.getIsOnline();
                sendMsg(defenceAreaId + ONLINE_STATUS, isOnline);
                if ("5".equals(status)) {
                    sendMsg(defenceAreaId + ALARM_STATUS, "1");
                } else {
                    sendMsg(defenceAreaId + ALARM_STATUS, "0");
                    if ("1".equals(status)) {
                        sendMsg(defenceAreaId + DEPLOY_WITHDRAW_ALARM_STATUS, "1");
                    } else if ("2".equals(status)) {
                        sendMsg(defenceAreaId + DEPLOY_WITHDRAW_ALARM_STATUS, "0");
                    } else if ("6".equals(status)) {
                        sendMsg(defenceAreaId + ONLINE_STATUS, "1");
                    } else if ("7".equals(status)) {
                        sendMsg(defenceAreaId + ONLINE_STATUS, "0");
                    }
                }
            }
        }
    }

    /**
     * 收到报警
     *
     * @param alarmDTO
     */
    public void receive(AlarmDTO alarmDTO) {
        String nodeCode = alarmDTO.getInfo().getNodeCode();
        String alarmStat = alarmDTO.getInfo().getAlarmStat();
        if (!"1".equals(alarmStat)) {
            alarmStat = "0";
        }
        sendMsg(nodeCode + ALARM_STATUS, alarmStat);
    }


    /**
     * 控制
     *
     * @param meter
     * @param funcid
     * @param value
     * @param message
     * @throws Exception
     */
    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        if (ObjectUtils.isNotEmpty(deviceMessage) && StringUtils.isNotBlank(deviceMessage.getOutParamId())
                && deviceMessage.getOutParamId().endsWith(DEPLOY_WITHDRAW_ALARM_SET)) {
            String outParamId = deviceMessage.getOutParamId();
            if (redisUtil.hasKey(outParamId)) {
                return;
            }
            redisUtil.set(outParamId, "0", 5);
            final String[] strings = deviceMessage.getOutParamId().split("_");
            int command;
            if ("1.0".equals(value)) {
                command = 1;
            } else {
                command = 2;
            }
            String url = host + MessageFormat.format(operateURI, version, strings[0], command);
            log.info("接口:{},头:{}", url, JSONObject.toJSONString(header));
            String result = HttpRequest.get(url).addHeaders(header).timeout(2000).execute().body();
            log.info("接口:{},返回值:{}", url, result);
            Object read = JSONPath.read(result, "$.success");
            if (ObjectUtils.isNotEmpty(read) && !"false".equals(String.valueOf(read))) {
                sendMsg(strings[0] + DEPLOY_WITHDRAW_ALARM_STATUS, value);
            }
        }
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    /**
     * 加密密码
     *
     * @param publicKey
     * @param password
     * @return
     * @throws Exception
     */
    private String baseEncrypt(String publicKey, String password) throws Exception {
        byte[] decoded = Base64.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        //**此处Base64编码，开发者可以使用自己的库**
        return Base64.encode(cipher.doFinal(password.getBytes("UTF-8")));
    }

}

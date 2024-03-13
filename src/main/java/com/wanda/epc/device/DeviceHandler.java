package com.wanda.epc.device;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.wanda.epc.DTO.AlarmDTO;
import com.wanda.epc.DTO.Root;
import com.wanda.epc.DTO.defenceAreaDTO;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孙率众
 */
@Service
@Slf4j
public class DeviceHandler extends BaseDevice {

    public static final String ALARM_STATUS = "_alarmStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_SET = "_deployWithdrawAlarmSet";
    public static final String ONLINE_STATUS = "_onlineStatus";
    public static final String DEPLOY_WITHDRAW_ALARM_STATUS = "_deployWithdrawAlarmStatus";
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
        String statusResult = "{\"success\":true,\"data\":{\"value\":[{\"subsystemId\":\"1002202$25$0$0\",\"subSystemName\":\"店铺_残卫手报\",\"status\":2,\"defenceAreaList\":[{\"defenceAreaId\":\"1002202$3$0$70\",\"defenceAreaName\":\"周六福手报\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$160\",\"defenceAreaName\":\"香港六福珠宝手报\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$161\",\"defenceAreaName\":\"中国黄金手报\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$162\",\"defenceAreaName\":\"周大福手报\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$163\",\"defenceAreaName\":\"老凤祥手报\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$192\",\"defenceAreaName\":\"三层1号残卫报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$193\",\"defenceAreaName\":\"二层1号残卫报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$194\",\"defenceAreaName\":\"一层1号残卫报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$196\",\"defenceAreaName\":\"二层6号残卫报警\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$197\",\"defenceAreaName\":\"三层6号残卫报警\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2}]},{\"subsystemId\":\"1002202$25$0$1\",\"subSystemName\":\"玻璃破碎报警\",\"status\":1,\"defenceAreaList\":[{\"defenceAreaId\":\"1002202$3$0$64\",\"defenceAreaName\":\"5号弱电井外橱窗2玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$65\",\"defenceAreaName\":\"5号弱电井外橱窗2玻璃破碎防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$66\",\"defenceAreaName\":\"5号弱电井外橱窗1玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$67\",\"defenceAreaName\":\"5号弱电井外橱窗1玻璃破碎防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$112\",\"defenceAreaName\":\"一层1036铺后玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$113\",\"defenceAreaName\":\"一层1036铺后玻璃破碎防拆\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$114\",\"defenceAreaName\":\"一层1F-3玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$115\",\"defenceAreaName\":\"一层1F-3玻璃破碎防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$136\",\"defenceAreaName\":\"一层橱窗1玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$137\",\"defenceAreaName\":\"一层橱窗1玻璃破碎防拆\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$138\",\"defenceAreaName\":\"一层橱窗2玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$139\",\"defenceAreaName\":\"一层橱窗2玻璃破碎防拆\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$184\",\"defenceAreaName\":\"一层5号井橱窗1玻璃破碎\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2}]},{\"subsystemId\":\"1002202$25$0$2\",\"subSystemName\":\"通道红外报警\",\"status\":2,\"defenceAreaList\":[{\"defenceAreaId\":\"1002202$3$0$16\",\"defenceAreaName\":\"一层3号通道1_报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$17\",\"defenceAreaName\":\"一层3号通道1_报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$18\",\"defenceAreaName\":\"一层厕所报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$19\",\"defenceAreaName\":\"一层厕所报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$20\",\"defenceAreaName\":\"一层3号通道2_报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$21\",\"defenceAreaName\":\"一层3号通道2_报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$22\",\"defenceAreaName\":\"一层1_货梯厅报警\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$23\",\"defenceAreaName\":\"一层1_货梯厅报警防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$24\",\"defenceAreaName\":\"二层3号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$25\",\"defenceAreaName\":\"二层3号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$26\",\"defenceAreaName\":\"二层4号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$27\",\"defenceAreaName\":\"二层4号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$28\",\"defenceAreaName\":\"三层4号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$29\",\"defenceAreaName\":\"三层4号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$30\",\"defenceAreaName\":\"三层5号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$31\",\"defenceAreaName\":\"三层5号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$32\",\"defenceAreaName\":\"一层4号通道报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$33\",\"defenceAreaName\":\"一层4号通道报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$34\",\"defenceAreaName\":\"一层4号通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$35\",\"defenceAreaName\":\"一层4号通道报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$40\",\"defenceAreaName\":\"二层5号通道报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$41\",\"defenceAreaName\":\"二层5号通道报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$42\",\"defenceAreaName\":\"二层5号通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$43\",\"defenceAreaName\":\"二层5号通道报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$44\",\"defenceAreaName\":\"三层6号通道报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$45\",\"defenceAreaName\":\"三层6号通道报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$46\",\"defenceAreaName\":\"三层6号通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$47\",\"defenceAreaName\":\"三层6号通道报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$48\",\"defenceAreaName\":\"一层2号门报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$49\",\"defenceAreaName\":\"一层2号门报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$50\",\"defenceAreaName\":\"一层2号门报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$51\",\"defenceAreaName\":\"一层2号门报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$52\",\"defenceAreaName\":\"一层2号门报警器3\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$53\",\"defenceAreaName\":\"一层2号门报警器3防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$58\",\"defenceAreaName\":\"一层5号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$59\",\"defenceAreaName\":\"一层5号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$60\",\"defenceAreaName\":\"二层6号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$61\",\"defenceAreaName\":\"二层6号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$62\",\"defenceAreaName\":\"三层7号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$63\",\"defenceAreaName\":\"三层7号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$72\",\"defenceAreaName\":\"一层5号弱电井楼梯间报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$73\",\"defenceAreaName\":\"一层5号弱电井楼梯间报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$74\",\"defenceAreaName\":\"一层6号通道报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$75\",\"defenceAreaName\":\"一层6号通道报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$76\",\"defenceAreaName\":\"一层6号通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$77\",\"defenceAreaName\":\"一层6号通道报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$78\",\"defenceAreaName\":\"一层6号通道报警器3\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$79\",\"defenceAreaName\":\"一层6号通道报警器3防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$80\",\"defenceAreaName\":\"一层1035铺后报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$81\",\"defenceAreaName\":\"一层1035铺后报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$82\",\"defenceAreaName\":\"一层1035铺后报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$83\",\"defenceAreaName\":\"一层1035铺后报警器2防拆\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$84\",\"defenceAreaName\":\"一层5号电井外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$85\",\"defenceAreaName\":\"一层5号电井外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$88\",\"defenceAreaName\":\"二层1号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$89\",\"defenceAreaName\":\"二层1号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$90\",\"defenceAreaName\":\"三层1号通道报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$91\",\"defenceAreaName\":\"三层1号通道报警器防拆\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$100\",\"defenceAreaName\":\"一层106通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$101\",\"defenceAreaName\":\"一层106通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$102\",\"defenceAreaName\":\"一层106通道右报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$103\",\"defenceAreaName\":\"一层106通道右报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$104\",\"defenceAreaName\":\"一层1号门报警器3\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$105\",\"defenceAreaName\":\"一层1号门报警器3防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$106\",\"defenceAreaName\":\"一层1-3通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$107\",\"defenceAreaName\":\"一层1-3通道报警器3防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$108\",\"defenceAreaName\":\"一层1-3通道报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$109\",\"defenceAreaName\":\"一层1-3通道报警器3防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$110\",\"defenceAreaName\":\"一层1-3通道报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$111\",\"defenceAreaName\":\"一层1-3通道报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$120\",\"defenceAreaName\":\"一层1号门报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$121\",\"defenceAreaName\":\"一层1号门报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$122\",\"defenceAreaName\":\"一层1F-3报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$123\",\"defenceAreaName\":\"一层1F-3报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$126\",\"defenceAreaName\":\"一层1号门报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$127\",\"defenceAreaName\":\"一层1号门报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$128\",\"defenceAreaName\":\"一层3号门报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$129\",\"defenceAreaName\":\"一层3号门报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$130\",\"defenceAreaName\":\"一层3号门报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$131\",\"defenceAreaName\":\"一层3号门报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$132\",\"defenceAreaName\":\"一层1号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$133\",\"defenceAreaName\":\"一层1号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$134\",\"defenceAreaName\":\"一层7_电井外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$135\",\"defenceAreaName\":\"一层7_电井外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$140\",\"defenceAreaName\":\"三层3号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$141\",\"defenceAreaName\":\"三层3号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$142\",\"defenceAreaName\":\"二层2号通道报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$143\",\"defenceAreaName\":\"二层2号通道报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$168\",\"defenceAreaName\":\"香港六福珠宝红外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$169\",\"defenceAreaName\":\"香港六福珠宝红外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$170\",\"defenceAreaName\":\"中国黄金红外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$171\",\"defenceAreaName\":\"中国黄金红外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$172\",\"defenceAreaName\":\"周大福红外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$173\",\"defenceAreaName\":\"周大福红外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$174\",\"defenceAreaName\":\"老凤祥红外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$175\",\"defenceAreaName\":\"老凤祥红外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$176\",\"defenceAreaName\":\"一层5号井外报警1\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2}]},{\"subsystemId\":\"1002202$25$0$3\",\"subSystemName\":\"水浸报警\",\"status\":2,\"defenceAreaList\":[{\"defenceAreaId\":\"1002202$3$0$116\",\"defenceAreaName\":\"B1车库入口水浸报警\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$152\",\"defenceAreaName\":\"水泵房水浸报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$153\",\"defenceAreaName\":\"制冷站水浸报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2}]},{\"subsystemId\":\"1002202$25$0$4\",\"subSystemName\":\"店铺红外报警\",\"status\":2,\"defenceAreaList\":[{\"defenceAreaId\":\"1002202$3$0$54\",\"defenceAreaName\":\"一层1028铺后报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$55\",\"defenceAreaName\":\"一层1028铺后报警器\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$68\",\"defenceAreaName\":\"周六福红外报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$69\",\"defenceAreaName\":\"周六福红外报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$96\",\"defenceAreaName\":\"星巴克报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$97\",\"defenceAreaName\":\"星巴克报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$98\",\"defenceAreaName\":\"星巴克报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$99\",\"defenceAreaName\":\"星巴克报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$124\",\"defenceAreaName\":\"必胜客报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$125\",\"defenceAreaName\":\"必胜客报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$144\",\"defenceAreaName\":\"一层主力店A报警器1\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$145\",\"defenceAreaName\":\"一层主力店A报警器1防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$146\",\"defenceAreaName\":\"一层主力店A报警器2\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$147\",\"defenceAreaName\":\"一层主力店A报警器2防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$148\",\"defenceAreaName\":\"一层德克士报警器\",\"status\":8,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2},{\"defenceAreaId\":\"1002202$3$0$149\",\"defenceAreaName\":\"一层德克士报警器防拆\",\"status\":2,\"isOnline\":1,\"byPass\":0,\"defenceAreaType\":2}]}]},\"code\":\"0\",\"errMsg\":\"\"}\n";
        Root root = JSONObject.parseObject(statusResult, Root.class);
        log.info(JSONObject.toJSONString(root));
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
            Root root = JSONObject.parseObject(statusResult, Root.class);
            List<com.wanda.epc.DTO.Value> values = root.getData().getValue();
            if (CollectionUtil.isEmpty(values)) {
                return;
            }
            List<defenceAreaDTO> defenceAreaDTOS = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                List<defenceAreaDTO> defenceAreaList = values.get(i).getDefenceAreaList();
                defenceAreaDTOS.addAll(defenceAreaList);
            }
            for (defenceAreaDTO defenceAreaDTO : defenceAreaDTOS) {
                String status = defenceAreaDTO.getStatus();
                String defenceAreaId = defenceAreaDTO.getDefenceAreaId();
                String isOnline = defenceAreaDTO.getIsOnline();
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

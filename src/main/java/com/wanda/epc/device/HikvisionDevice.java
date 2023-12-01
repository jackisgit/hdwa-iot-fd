package com.wanda.epc.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.ConvertUtil;
import com.wanda.epc.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 海康防盗
 * @date 2023/5/9 11:39:21
 */
@Slf4j
@Service
public class HikvisionDevice extends BaseDevice {

    @Value("${appKey}")
    private String appKey;

    @Value("${host}")
    private String host;

    @Value("${appSecret}")
    private String appSecret;

    @Value("${type}")
    private String type;

    @Value("${iasChannelUrl}")
    private String iasChannelUrl;

    @Value("${defenceUrl}")
    private String defenceUrl;

    @Value("${subsystemUrl}")
    private String subsystemUrl;

    @Value("${artemisPath}")
    private String artemisPath;

    @Value("${subSysStatusUrl}")
    private String subSysStatusUrl;

    private List<String> sysSubIndexCodeList = new ArrayList<>();
    private List<String> defenceIndexCodeList = new ArrayList<>();

    @PostConstruct
    public void init() {
        sysSubIndexCodeList = getIndexCode("subSys");
        log.info("初始化sysSubIndexCodeList数据大小：{}", sysSubIndexCodeList.size());
        defenceIndexCodeList = getIndexCode("defence");
        log.info("初始化defenceIndexCodeList数据大小：{}", defenceIndexCodeList.size());
    }


    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        //反馈到iot-project
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接受到防盗报警撤布防指令 meter:{},funcid:{},value:{},deviceMessage:{}", meter, funcid, value, JSON.toJSONString(deviceMessage));
        String outParamId = deviceMessage.getOutParamId();
        String[] split = outParamId.split("_");
        String indexCode = split[0];
        Integer command;
        if ("1.0".equals(value)) {
            log.info("布防操作");
            command = 1;
        } else {
            log.info("撤防操作");
            command = 0;
        }
        List<HikvisionSubSystemReqVo> subSystemReqVoList = new ArrayList<>();
        HikvisionSubSystemReqVo hikvisionSubSystemReqVo = new HikvisionSubSystemReqVo();
        hikvisionSubSystemReqVo.setSubSystemIndexCode(indexCode);
        hikvisionSubSystemReqVo.setStatus(command);
        subSystemReqVoList.add(hikvisionSubSystemReqVo);
        HikvisionSubSystemRspVo hikvisionSubSystemRspVo = subSystem(subSystemReqVoList);
        log.info("防盗报警撤布防指令结束：{}", JSON.toJSONString(hikvisionSubSystemRspVo));
        if (Objects.nonNull(hikvisionSubSystemRspVo) && hikvisionSubSystemRspVo.isSuccess()) {
            if (command == 1) {
                sendMsg(hikvisionSubSystemRspVo.getIndexCode().concat("_defenceStatus"), "1");
                sendMsg(hikvisionSubSystemRspVo.getIndexCode().concat("_deployWithdrawAlarmSetFeedback"), "1");
            } else {
                sendMsg(hikvisionSubSystemRspVo.getIndexCode().concat("_defenceStatus"), "0");
                sendMsg(hikvisionSubSystemRspVo.getIndexCode().concat("_deployWithdrawAlarmSetFeedback"), "0");
            }
        }
    }

    @Override
    public boolean processData() throws Exception {
        if (CollectionUtils.isEmpty(defenceIndexCodeList)) {
            defenceIndexCodeList = getIndexCode("defence");
        }
        if (CollectionUtils.isEmpty(sysSubIndexCodeList)) {
            sysSubIndexCodeList = getIndexCode("subSys");
        }
        //1.采集防区状态
        List<HikvisionDefenceStatusVo> defenceStatus = getDefenceStatus(defenceIndexCodeList);
        if (!CollectionUtils.isEmpty(defenceStatus)) {
            //防区状态：-1: 未知 0: 离线1: 正常2: 故障3: 报警4: 旁路
            defenceStatus.forEach(defence -> {
                //1.离线状态
                if (defence.getStatus() == 0) {
                    sendMsg(defence.getDefenceIndexCode().concat("_onLineStatus"), "0");
                } else {
                    sendMsg(defence.getDefenceIndexCode().concat("_onLineStatus"), "1");
                }
                //2.报警状态
                if (defence.getStatus() == 3) {
                    sendMsg(defence.getDefenceIndexCode().concat("_alarmStatus"), "1");
                } else {
                    sendMsg(defence.getDefenceIndexCode().concat("_alarmStatus"), "0");
                }
                //3.故障状态
                if (defence.getStatus() == 2) {
                    sendMsg(defence.getDefenceIndexCode().concat("_faultStatus"), "1");
                } else {
                    sendMsg(defence.getDefenceIndexCode().concat("_faultStatus"), "0");
                }
                //3.旁路状态
                if (defence.getStatus() == 4) {
                    sendMsg(defence.getDefenceIndexCode().concat("_pangluStatus"), "1");
                } else {
                    sendMsg(defence.getDefenceIndexCode().concat("_pangluStatus"), "0");
                }
            });
        }
        //2.查询子系统状态
        List<HikvisionsubSystemIStatusVo> subSysStatus = getSubSysStatus(sysSubIndexCodeList);
        if (!CollectionUtils.isEmpty(subSysStatus)) {
            //子系统状态 子系统状态，-1：未知，0：撤防，1：布防
            subSysStatus.forEach(subSys -> {
                //1.撤防状态
                if (subSys.getStatus() == 0) {
                    sendMsg(subSys.getSubSystemIndexCode().concat("_defenceStatus"), "0");
                } else {
                    sendMsg(subSys.getSubSystemIndexCode().concat("_deployWithdrawAlarmSetFeedback"), "0");
                }
                //1：布防状态
                if (subSys.getStatus() == 1) {
                    sendMsg(subSys.getSubSystemIndexCode().concat("_defenceStatus"), "1");
                } else {
                    sendMsg(subSys.getSubSystemIndexCode().concat("_deployWithdrawAlarmSetFeedback"), "1");
                }

            });
        }
        return true;
    }


    public void sendMsg(String point, String value) {
        List<DeviceMessage> deviceMessageList = deviceParamListMap.get(point);
        if (!CollectionUtils.isEmpty(deviceMessageList)) {
            deviceMessageList.forEach(deviceMessage -> {
                if (Objects.nonNull(deviceMessage)) {
                    deviceMessage.setValue(value);
                    sendMessage(deviceMessage);
                }
            });
        }
    }


    /**
     * @description 查询入侵报警主机通道列表v2获取indexCode
     * @author LianYanFei
     * @date 2023/5/9
     */
    public List<String> getIndexCode(String resourceType) {
        log.info("开始调用海康威视查询入侵报警主机通道列表v2");
        //设置OpenAPI接口的上下文
        final String ARTEMIS_PATH = artemisPath;
        //设置接口的URI地址
        final String previewURLsApi = ARTEMIS_PATH.concat(iasChannelUrl);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        // artemis网关服务器ip端口
        ArtemisConfig.host = host;
        // 秘钥appKey
        ArtemisConfig.appKey = appKey;
        // 秘钥appSecret
        ArtemisConfig.appSecret = appSecret;
        //设置参数提交方式
        String contentType = type;
        //组装请求参数
        JSONObject jsonBody = new JSONObject();
        //资源类型，subSys：入侵报警子系统通道，defence：入侵报警防区通道
        jsonBody.put("resourceType", resourceType);
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 1000);
        String body = jsonBody.toJSONString();
        //调用接口
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null,
                contentType, null);
        log.info("获取入侵报警主机通道列表v2结果集：{}", result);
        if (StringUtils.isNotBlank(result)) {
            String code = (String) JSONPath.read(result, "$.code");
            if ("0".equals(code)) {
                JSONObject jsonObject = (JSONObject) JSONPath.read(result, "$.data");
                JSONArray dlpList = jsonObject.getJSONArray("list");
                List<HikvisionIasChannelVo> hikvisionIasChannelVoList = JSON.parseArray(JSON.toJSONString(dlpList), HikvisionIasChannelVo.class);
                if (!CollectionUtils.isEmpty(hikvisionIasChannelVoList)) {
                    List<String> indexCodList = hikvisionIasChannelVoList.stream().map(hikvision -> hikvision.getIndexCode()).collect(Collectors.toList());
                    return indexCodList;
                }

            }
        }
        return null;
    }


    /**
     * @description 查询防区状态
     * @author LianYanFei
     * @date 2023/5/9
     */
    public List<HikvisionDefenceStatusVo> getDefenceStatus(List<String> defenceIndexCodes) {
        log.info("开始调用海康威视查询查询防区状态");
        //设置OpenAPI接口的上下文
        final String ARTEMIS_PATH = artemisPath;
        //设置接口的URI地址
        final String previewURLsApi = ARTEMIS_PATH.concat(defenceUrl);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        // artemis网关服务器ip端口
        ArtemisConfig.host = host;
        // 秘钥appKey
        ArtemisConfig.appKey = appKey;
        // 秘钥appSecret
        ArtemisConfig.appSecret = appSecret;
        //设置参数提交方式
        String contentType = type;
        //组装请求参数
        JSONObject jsonBody = new JSONObject();
        //资源类型，subSys：入侵报警子系统通道，defence：入侵报警防区通道
        jsonBody.put("defenceIndexCodes", defenceIndexCodes);
        String body = jsonBody.toJSONString();
        //调用接口
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null,
                contentType, null);
        log.info("获取防区状态结果集：{}", result);
        if (StringUtils.isNotBlank(result)) {
            String code = (String) JSONPath.read(result, "$.code");
            if ("0".equals(code)) {
                JSONObject jsonObject = (JSONObject) JSONPath.read(result, "$.data");
                JSONArray dlpList = jsonObject.getJSONArray("list");
                List<HikvisionDefenceStatusVo> hikvisionDefenceStatusVoList = JSON.parseArray(JSON.toJSONString(dlpList), HikvisionDefenceStatusVo.class);
                return hikvisionDefenceStatusVoList;
            }
        }
        return null;
    }

    /**
     * @description 查询子系统状态
     * @author LianYanFei
     * @date 2023/5/10
     */
    public List<HikvisionsubSystemIStatusVo> getSubSysStatus(List<String> defenceIndexCodes) {
        log.info("开始调用海康威视查询子系统状态");
        //设置OpenAPI接口的上下文
        final String ARTEMIS_PATH = artemisPath;
        //设置接口的URI地址
        final String previewURLsApi = ARTEMIS_PATH.concat(subSysStatusUrl);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        // artemis网关服务器ip端口
        ArtemisConfig.host = host;
        // 秘钥appKey
        ArtemisConfig.appKey = appKey;
        // 秘钥appSecret
        ArtemisConfig.appSecret = appSecret;
        //设置参数提交方式
        String contentType = type;
        //组装请求参数
        JSONObject jsonBody = new JSONObject();
        //资源类型，subSys：入侵报警子系统通道，defence：入侵报警防区通道
        jsonBody.put("subSysIndexCodes", defenceIndexCodes);
        String body = jsonBody.toJSONString();
        //调用接口
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null,
                contentType, null);
        log.info("获取子系统状态结果集：{}", result);
        if (StringUtils.isNotBlank(result)) {
            String code = (String) JSONPath.read(result, "$.code");
            if ("0".equals(code)) {
                JSONObject jsonObject = (JSONObject) JSONPath.read(result, "$.data");
                JSONArray dlpList = jsonObject.getJSONArray("list");
                List<HikvisionsubSystemIStatusVo> hikvisionDefenceStatusVoList = JSON.parseArray(JSON.toJSONString(dlpList), HikvisionsubSystemIStatusVo.class);
                return hikvisionDefenceStatusVoList;
            }
        }
        return null;
    }

    /**
     * @description 撤布防操作
     * @author LianYanFei
     * @date 2023/5/9
     */
    public HikvisionSubSystemRspVo subSystem(List<HikvisionSubSystemReqVo> subSystemReqVoList) {
        log.info("开始调用海康威视撤布防接口");
        //设置OpenAPI接口的上下文
        final String ARTEMIS_PATH = artemisPath;
        //设置接口的URI地址
        final String previewURLsApi = ARTEMIS_PATH.concat(subsystemUrl);
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        // artemis网关服务器ip端口
        ArtemisConfig.host = host;
        // 秘钥appKey
        ArtemisConfig.appKey = appKey;
        // 秘钥appSecret
        ArtemisConfig.appSecret = appSecret;
        //设置参数提交方式
        String contentType = type;
        //组装请求参数
        JSONObject jsonBody = new JSONObject();
        //资源类型，subSys：入侵报警子系统通道，defence：入侵报警防区通道
        jsonBody.put("subSystemList", subSystemReqVoList);
        String body = jsonBody.toJSONString();
        //调用接口
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null,
                contentType, null);
        log.info("获取防区撤布防结果集：{}", result);
        if (StringUtils.isNotBlank(result)) {
            String code = (String) JSONPath.read(result, "$.code");
            if ("0".equals(code)) {
                JSONObject jsonObject = (JSONObject) JSONPath.read(result, "$.data");
                JSONArray dlpList = jsonObject.getJSONArray("list");
                List<HikvisionSubSystemRspVo> hikvisionSubSystemRspVoList = JSON.parseArray(JSON.toJSONString(dlpList), HikvisionSubSystemRspVo.class);
                if (!CollectionUtils.isEmpty(hikvisionSubSystemRspVoList)) {
                    return hikvisionSubSystemRspVoList.get(0);
                }
            }
        }
        return null;

    }
}

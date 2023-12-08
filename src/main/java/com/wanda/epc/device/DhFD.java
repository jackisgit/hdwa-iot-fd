package com.wanda.epc.device;

import cn.hutool.core.util.ObjectUtil;
import com.dh.DpsdkCore.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wanda.epc.common.RedisUtil;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.vo.Channel;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DhFD extends BaseDevice {

    public static int m_nDLLHandle = 1;
    //通道布撤防
    public static int operateChannel = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_CHANNEL;
    //设备布撤防
    public static int operateDevice = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_DEVICE;
    //布防类型
    public static int buType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_ARM;
    //撤防类型
    public static int cheType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_DISARM;
    @Resource
    CommonDevice commonDevice;
    Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
    @Resource
    private RedisUtil redisUtil;
    @Value("${epc.DeviceId}")
    private String DeviceId;
    @Value("${epc.UnitNodesType}")
    private String UnitNodesType;

    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    private String documentToString(Document doc) throws Exception {
        // Create a transformer to convert Document to XML string
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String xmlString = writer.getBuffer().toString();
        return xmlString;
    }

    public Device getTree() throws Exception {
        IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000);
        byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
        int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(szGroupBuf);
            // Parse input stream into Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(inputStream));
            // Convert Document object to XML string
            String xmlString = documentToString(doc);
            xmlString = getDeviceString(xmlString);
            return parse(xmlString);
        }
        return null;
    }

    public String getDeviceString(String xml) {
        String startTag = "<Devices>";
        String endTag = "</Devices>";
        int startIndex = xml.indexOf(startTag);
        int endIndex = xml.indexOf(endTag) + endTag.length();
        String result = xml.substring(startIndex, endIndex);
        return result;
    }

    public Device parse(String xml) throws Exception {
        // Parse XML string into Document object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        // Find root element and parse it recursively
        Element root = doc.getDocumentElement();
        List<Device> deviceList = parseDepartment(root);
        Device res = null;
        for (Device device : deviceList) {
            if (DeviceId.equals(device.getId())) {
                res = device;
            }
        }
        return res;
    }

    private List<Device> parseDepartment(Element el) {
        //获取Device节点
        NodeList deviceNodes = el.getElementsByTagName("Device");
        List<Device> deviceNodesList = new ArrayList<>();
        for (int k = 0; k < deviceNodes.getLength(); k++) {
            Element deviceNode = (Element) deviceNodes.item(k);
            String deviceId = deviceNode.getAttribute("id");
            String deviceType = deviceNode.getAttribute("type");
            String deviceIp = deviceNode.getAttribute("deviceIp");
            Device device = new Device(deviceId, deviceType, deviceIp);
            deviceNodesList.add(device);
        }
        //获取Channel节点
        NodeList channelNodes = el.getElementsByTagName("Channel");
        List<Channel> ChannelNodesList = new ArrayList<>();
        for (int j = 0; j < channelNodes.getLength(); j++) {
            Element channelNode = (Element) channelNodes.item(j);
            String channelId = channelNode.getAttribute("id");
            Channel channelVo = new Channel(channelId);
            ChannelNodesList.add(channelVo);
        }
        for (int i = 0; i < deviceNodesList.size(); i++) {
            List<Channel> channelList = new ArrayList<>();
            for (int j = 0; j < ChannelNodesList.size(); j++) {
                if (ChannelNodesList.get(j).getId().startsWith(deviceNodesList.get(i).getId())) {
                    channelList.add(ChannelNodesList.get(j));
                }
            }
            deviceNodesList.get(i).setChannels(channelList);
        }
        return deviceNodesList;
    }

    public List<ChannelInfo> getChannelInfos() throws Exception {
        byte[] szDeviceId = getChannelByte(DeviceId);
        Device device = getTree();
        List<Channel> channelList = device.getChannels();
        int size = channelList.size();
        dpsdk_AHostDefenceStatus_t[] hostDefenceStatusArray = new dpsdk_AHostDefenceStatus_t[size];
        for (int i = 0; i < size; i++) {
            dpsdk_AHostDefenceStatus_t defenceStatus = new dpsdk_AHostDefenceStatus_t();
            hostDefenceStatusArray[i] = defenceStatus;
        }
        int nRet = IDpsdkCore.DPSDK_QueryNetAlarmHostStatus(m_nDLLHandle, szDeviceId, channelList.size(), hostDefenceStatusArray, 1000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // 可选，用于美化输出的 JSON 字符串
            List<ChannelInfo> res = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ChannelInfo channelInfo = new ChannelInfo();
                dpsdk_AHostDefenceStatus_t defenceStatus = hostDefenceStatusArray[i];
                channelInfo.setId(transfString(defenceStatus.szNodeID));
                channelInfo.setBDefend(defenceStatus.bDefend);
                channelInfo.setAlarmStatus(defenceStatus.nAlarm);
                channelInfo.setNUndefendAlarm(defenceStatus.nUndefendAlarm);
                res.add(channelInfo);
            }
            return res;
        }
        return null;
    }

    public String transfString(byte[] channelId) {
        int length = 0;
        for (int i = 0; i < channelId.length; i++) {
            if (channelId[i] != 0) {
                length++;
            } else {
                break;
            }
        }
        byte[] nonZeroBytes = new byte[length];
        System.arraycopy(channelId, 0, nonZeroBytes, 0, length);
        // 将字节数组转换为字符串
        String res = new String(nonZeroBytes);
        Pattern pattern = Pattern.compile("\\$(\\d+)$");
        Matcher matcher = pattern.matcher(res);
        String extractedNumber = "";
        if (matcher.find()) {
            extractedNumber = matcher.group(1);
        }
        return extractedNumber;
    }

    @Override
    public boolean processData() throws Exception {
        List<ChannelInfo> channelList = getChannelInfos();
        for (ChannelInfo info : channelList) {
            int alarmStatus = info.getAlarmStatus();
            Boolean defendStatus = info.getBDefend();
            List<DeviceMessage> alarmMessages = deviceParamListMap.get(info.getId().concat("_alarmStatus"));
            if (!CollectionUtils.isEmpty(alarmMessages) && ObjectUtil.isNotEmpty(alarmStatus)) {
                alarmMessages.forEach(deviceMessage -> {
                    //0表示未报警
                    if (0 == alarmStatus) {
                        deviceMessage.setValue("0");
                    } else {
                        deviceMessage.setValue("1");
                    }
                    sendMessage(deviceMessage);
                });

            }
            List<DeviceMessage> defendMessages = deviceParamListMap.get(info.getId().concat("_deployWithdrawAlarmStatus"));
            if (!CollectionUtils.isEmpty(defendMessages) && ObjectUtil.isNotEmpty(defendStatus)) {
                defendMessages.forEach(deviceMessage -> {
                    //1表示布防，0表示撤防
                    if (defendStatus) {
                        deviceMessage.setValue("1");
                    } else {
                        deviceMessage.setValue("0");
                    }
                    sendMessage(deviceMessage);
                });

            }
            List<DeviceMessage> onlineMessages = deviceParamListMap.get(info.getId().concat("_onlineStatus"));
            String channleId = DeviceId + "$" + UnitNodesType + "$" + info.getId();
            byte[] cameraId = getChannelByte(channleId);
            Return_Value_Info_t nStatus = new Return_Value_Info_t();
            int nRet = IDpsdkCore.DPSDK_GetChannelStatus(m_nDLLHandle, cameraId, nStatus);
            if (!CollectionUtils.isEmpty(onlineMessages)) {
                onlineMessages.forEach(deviceMessage -> {
                    deviceMessage.setValue(Integer.toString(nStatus.nReturnValue));
                    sendMessage(deviceMessage);
                });
            }
        }
        return true;
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) throws Exception {
        //我收到消息
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        log.info("接收到防盗报警撤布防指令：meter:{},funcId：{},value:{},deviceMessage:{}", meter, funcid, value, message);
        if (deviceMessage != null && deviceMessage.getOutParamId() != null && deviceMessage.getOutParamId().endsWith("deployWithdrawAlarmSet")) {
            if (redisUtil.hasKey(deviceMessage.getOutParamId())) {
                commonDevice.feedback(message);
                return;
            }
//            redisUtil.set(deviceMessage.getOutParamId(), "0", 5);
            try {
                String outParamId = deviceMessage.getOutParamId();
                String[] split = outParamId.split("_");
                //防区号或者分区ip
                String firstParam = split[0];
                String type = split[1];
                //type=0防区撤布防；type=1分区撤布防需要将分区ip替换成分区id
                if ("1".equals(type)) {
                    Device device = getTree();
                    if (firstParam.equals(device.getDeviceIp())) {
                        firstParam = device.getId();
                    }
                }
                log.info("开始调用子系统做撤布防操作====outParamId:{}", outParamId);
                if ("1.0".equals(value)) {
                    defence(firstParam, type, outParamId);
                } else {
                    noDefence(firstParam, type, outParamId);
                }
            } catch (Exception e) {
                log.error("防盗报警控制命令下发失败：" + e.getMessage());
            }
        }
    }

    public int defence(String channleId, String type, String outParamId) {
        log.info("------------------------------进入布防的方法------------------------");
        //type=0防区撤布防；type=1分区撤布防
        if ("0".equals(type)) {
            channleId = Integer.toString(Integer.parseInt(channleId) + 1);
            channleId = DeviceId + "$" + UnitNodesType + "$0$" + channleId;
        }
        byte[] ids = getChannelByte(channleId);
        int nRet;
        if ("1".equals(type)) {
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operateDevice, buType, 0, 0, 100000);
        } else {
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operateChannel, buType, 0, 0, 100000);
        }
        log.info("布防结果，nRet为{}", nRet);
        return nRet;
    }

    public int noDefence(String channleId, String type, String outParamId) {
        log.info("------------------------------进入撤防的方法------------------------");
        //type=0防区撤布防；type=1分区撤布防
        if ("0".equals(type)) {
            channleId = Integer.toString(Integer.parseInt(channleId) + 1);
            channleId = DeviceId + "$" + UnitNodesType + "$0$" + channleId;
        }
        byte[] ids = getChannelByte(channleId);
        int nRet;
        if ("1".equals(type)) {
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operateDevice, cheType, 0, 0, 100000);
        } else {
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operateChannel, cheType, 0, 0, 100000);
        }
        log.info("撤防结果，nRet为{}", nRet);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            redisUtil.set(outParamId, "0", 5);
            log.info("撤防成功，通道id为{}", channleId);
        } else {
            log.error("撤防失败，通道id为{}", channleId);
        }
        return nRet;
    }

    /*
     *通道id（channleId）转数组
     */
    public byte[] getChannelByte(String channleId) {
        // 创建一个与字符串长度相同的 byte 数组
        byte[] byteArray = channleId.getBytes();
        return byteArray;
    }

}

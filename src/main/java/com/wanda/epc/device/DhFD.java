package com.wanda.epc.device;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.dh.DpsdkCore.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.wanda.epc.common.RedisUtil;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.ConvertUtil;
import com.wanda.epc.vo.Channel;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Device;
import com.wanda.epc.vo.Devices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class DhFD extends BaseDevice {

    @Autowired
    CommonDevice commonDevice;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${epc.gatewayId}")
    private String gatewayId;

    @Value("${epc.gcId}")
    private String gcId;

    @Value("${epc.DeviceId}")
    private String DeviceId;

    @Value("${epc.UnitNodesType}")
    private String UnitNodesType;

    public static int m_nDLLHandle = 1;
    //通道布撤防
    public static int operatChannel = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_CHANNEL;
    //设备布撤防
    public static int operatDevice = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_DEVICE;
    //布防类型
    public static int buType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_ARM;
    //撤防类型
    public static int cheType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_DISARM;
    Return_Value_Info_t nGroupLen = new Return_Value_Info_t();

    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    private  String documentToString(Document doc) throws Exception {
        // Create a transformer to convert Document to XML string
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        String xmlString = writer.getBuffer().toString();
        return xmlString;
    }

    public Device getTree() throws Exception {
        System.out.println("------------------------------开始查询组织树------------------------");
        int nRet1 = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000 );
        byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
        System.out.printf("获取所有组织树串参数m_nDLLHandle："+m_nDLLHandle+";szGroupBuf："+szGroupBuf+";nGroupLen.nReturnValue："+nGroupLen.nReturnValue);
        int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);
        System.out.println("------------------------------是否查询成功------------------------"+nRet);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(szGroupBuf);
            // Parse input stream into Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(inputStream));
            // Convert Document object to XML string
            String xmlString = documentToString(doc);
            System.out.println("获取组织树： "+xmlString);
            xmlString=getDeviceString(xmlString);
            return parse(xmlString);
        }
        return null;
    }

    public String getDeviceString(String xml){
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
        List<Device> deviceList=parseDepartment(root);
        Device res=null;
        for(Device device:deviceList){
            if(DeviceId.equals(device.getId())){
                res=device;
            }
        }
        int channelNum = res.getChannels().size();

        // 创建Gson对象
        Gson gson = new Gson();
        // 将对象转换为JSON字符串
        String json = gson.toJson(res);
        System.out.println("获取json组织树： "+res);
        return res;
    }
    private List<Device> parseDepartment(Element el) {
        //获取Device节点
        NodeList deviceNodes = el.getElementsByTagName("Device");
        List<Device> deviceNodesList=new ArrayList<>();
        for (int k = 0; k < deviceNodes.getLength(); k++) {
            Element deviceNode = (Element) deviceNodes.item(k);
            String deviceId = deviceNode.getAttribute("id");
            String deviceType = deviceNode.getAttribute("type");
            String deviceIp = deviceNode.getAttribute("deviceIp");
            Device device = new Device(deviceId,deviceType,deviceIp);
            deviceNodesList.add(device);
        }
//        //获取UnitNodes节点
//        NodeList unitNodes = el.getElementsByTagName("UnitNodes");
//        List<UnitNodes> unitNodesList=new ArrayList<>();
//        for (int i = 0; i < unitNodes.getLength(); i++) {
//            UnitNodes unitNode = new UnitNodes();
//            Element node = (Element) unitNodes.item(i);
//            String unitNodetype = node.getAttribute("type");
//            unitNode.setType(unitNodetype);
//            unitNode.setChannel(ChannelNodesList);
//            unitNodesList.add(unitNode);
//        }
        //获取Channel节点
        NodeList channelNodes = el.getElementsByTagName("Channel");
        List<Channel> ChannelNodesList=new ArrayList<>();
        for (int j = 0; j < channelNodes.getLength(); j++) {
            Element channelNode = (Element) channelNodes.item(j);
            String channelId = channelNode.getAttribute("id");
            Channel channelVo = new Channel(channelId);
            ChannelNodesList.add(channelVo);
        }
        for (int i= 0; i<deviceNodesList.size(); i++){
            List<Channel> channelList=new ArrayList<>();
            for (int j= 0; j<ChannelNodesList.size(); j++){
                if(ChannelNodesList.get(j).getId().startsWith(deviceNodesList.get(i).getId())){
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
        for(int i=0;i<size;i++){
            dpsdk_AHostDefenceStatus_t defenceStatus =new dpsdk_AHostDefenceStatus_t();
            hostDefenceStatusArray[i] = defenceStatus;
        }
        System.out.println("==============查询channel信息接口参数m_nDLLHandle："+ m_nDLLHandle);
        System.out.println("==============查询channel信息接口参数szDeviceId："+ szDeviceId);
        System.out.println("==============查询channel信息接口参数channelList.size()："+ channelList.size());
        System.out.println("==============查询channel信息接口参数hostDefenceStatusArray："+ hostDefenceStatusArray);
        int nRet = IDpsdkCore.DPSDK_QueryNetAlarmHostStatus(m_nDLLHandle, szDeviceId, channelList.size(), hostDefenceStatusArray, 1000);
        System.out.println("==============查询channel信息接口===="+ nRet);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // 可选，用于美化输出的 JSON 字符串
            List<ChannelInfo> res=new ArrayList<>();
            for(int i=0;i<size;i++){
                ChannelInfo channelInfo=new ChannelInfo();
                dpsdk_AHostDefenceStatus_t defenceStatus =hostDefenceStatusArray[i];
                channelInfo.setId(transfString(defenceStatus.szNodeID));
                channelInfo.setBDefend(defenceStatus.bDefend);
                channelInfo.setAlarmStatus(defenceStatus.nAlarm);
                channelInfo.setNUndefendAlarm(defenceStatus.nUndefendAlarm);
                res.add(channelInfo);
            }
            // 将 hostDefenceStatusArray 转换为 JSON 字符串
            String json = objectMapper.writeValueAsString(res);
            return res;
        }
        return null;
    }
    public String transfString(byte[] channelId){
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
//        // 找到最后一个$符号的索引位置
//        int lastIndex = res.lastIndexOf("$");
//        // 在最后一个$符号前插入$0
//        String modifiedString = res.substring(0, lastIndex) + "$0" + res.substring(lastIndex);
        Pattern pattern = Pattern.compile("\\$(\\d+)$");
        Matcher matcher = pattern.matcher(res);
        String extractedNumber = "";
        if (matcher.find()) {
            extractedNumber = matcher.group(1);
        }
        System.out.println(extractedNumber);
        return extractedNumber;
    }
    @Override
    public boolean processData() throws Exception {
        List<ChannelInfo> channelList = getChannelInfos();
        for(ChannelInfo info:channelList){
            System.out.println("获取通道数据为："+ JSON.toJSON(info));
            String id=info.getId();
            int alarmStatus=info.getAlarmStatus();
            Boolean defendStatus=info.getBDefend();
            DeviceMessage alarmMessage = deviceParamMap.get(info.getId().concat("_alarmStatus"));
            if (Objects.nonNull(alarmMessage) && ObjectUtil.isNotEmpty(alarmStatus)) {
                //0表示未报警
                if (0 == alarmStatus) {
                    alarmMessage.setValue("0");
                    System.out.println("门禁采集器发送防区报警状态数据："+ JSON.toJSON(alarmMessage));
                } else {
                    alarmMessage.setValue("1");
                    System.out.println("门禁采集器发送防区报警状态数据："+ JSON.toJSON(alarmMessage));
                }
                alarmMessage.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
                sendMessage(alarmMessage);
            }
            DeviceMessage defendMessage = deviceParamMap.get(info.getId().concat("_deployWithdrawAlarmStatus"));
            System.out.println("获取redis的撤布防信息defendMessage："+ JSON.toJSON(defendMessage));
            if (Objects.nonNull(defendMessage) && ObjectUtil.isNotEmpty(defendStatus)) {
                //1表示布防，0表示撤防
                if (defendStatus) {
                    defendMessage.setValue("1");
                    System.out.println("门禁采集器发送防区撤布防状态数据："+ JSON.toJSON(defendMessage));
                } else {
                    defendMessage.setValue("0");
                    System.out.println("门禁采集器发送防区撤布防状态数据："+ JSON.toJSON(defendMessage));
                }
                defendMessage.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
                sendMessage(defendMessage);
            }
            DeviceMessage onlineMessage = deviceParamMap.get(info.getId().concat("_onlineStatus"));
            String channleId=DeviceId+"$"+UnitNodesType+"$"+info.getId();
            byte[] cameraId=getChannelByte(channleId);
            Return_Value_Info_t nStatus=new Return_Value_Info_t();
            int nRet=IDpsdkCore.DPSDK_GetChannelStatus(m_nDLLHandle, cameraId, nStatus);
            System.out.println("门禁采集器发送cameraId："+ channleId);
            System.out.println("门禁采集器获取在线状态："+ JSON.toJSON(nStatus.nReturnValue));
            if (Objects.nonNull(onlineMessage)) {
                onlineMessage.setValue(Integer.toString(nStatus.nReturnValue));
                onlineMessage.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
                System.out.println("门禁采集器发送防区在线状态数据："+ JSON.toJSON(onlineMessage));
                sendMessage(onlineMessage);
            }
//        Device device = getTree();
//        List<Channel> channelList = device.getChannels();
//        log.info("==============定时任务开始执行==============");
//        System.out.println("==============获取到的channelList===="+ JSON.toJSON(channelList));
//        Iterator<Map.Entry<String, DeviceMessage>> iterator = super.deviceParamMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, DeviceMessage> next = iterator.next();
//            String outParamId = next.getKey();
//            DeviceMessage deviceMessage = next.getValue();
//            if (StringUtils.isEmpty(deviceMessage.getValue())) {
//                String[] split = outParamId.split("_");
//                String param = split[0];
//                if (outParamId.endsWith("_deployWithdrawAlarmStatus")) {
//                    for(Channel channel:channelList){
//                        if(param.equals(channel.getId())){
//                            deviceMessage.setValue(channel.getStatus());
//                        }
//                    }
//                } else if (outParamId.endsWith("_onlineStatus")) {
//                    deviceMessage.setValue(device.getStatus());
//                }
//            }
//            deviceMessage.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
//            System.out.println("==============待发送的deviceMessage===="+ JSON.toJSON(deviceMessage));
//            sendMessage(deviceMessage);
//            log.info("发送" + outParamId + "数据值为：" + deviceMessage.getValue());
        }
        log.info("==============定时任务执行完成==============");
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
                if("1".equals(type)){
                    Device device=getTree();
                    if(firstParam.equals(device.getDeviceIp())){
                        firstParam=device.getId();
                    }
                }
                String desc = "";
                log.info("开始调用子系统做撤布防操作====");
                log.info("outParamId===="+outParamId);
                if ("1.0".equals(value)) {
                    desc = "布防";
                    defence(firstParam,type,outParamId);
                } else {
                    desc = "撤防";
                    noDefence(firstParam,type,outParamId);
                }
            } catch (Exception e) {
                log.info("防盗报警控制命令下发失败：" + e.getMessage());
            }
        }
    }

    public int defence(String channleId,String type,String outParamId){
        System.out.printf("------------------------------进入布防的方法------------------------");
        //type=0防区撤布防；type=1分区撤布防
        if("0".equals(type)){
            channleId=Integer.toString(Integer.parseInt(channleId)+1);
            channleId=DeviceId+"$"+UnitNodesType+"$0$"+channleId;
        }
        byte[] ids = getChannelByte(channleId);
//        long startTime = 1698870059000L;
//        long endTime = 1698950399000L;
        System.out.println("布防参数为：");
        System.out.println(m_nDLLHandle);
        System.out.println(ids);
        System.out.println(operatChannel);
        System.out.println(buType);
//        System.out.println(startTime);
//        System.out.println(endTime);
        int nRet;
        if("1".equals(type)){
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle,ids,operatDevice,buType,0,0,100000);
        }else{
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle,ids,operatChannel,buType,0,0,100000);
        }
        System.out.println("布防结果，nRet为"+nRet);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS){
            redisUtil.set(outParamId, "1", 5);
            System.out.printf("布防成功，通道id为", channleId);
        }else{
            System.out.printf("布防失败，通道id为", channleId);
        }
        return nRet;
    }
    public int noDefence(String channleId,String type,String outParamId) {
        System.out.printf("------------------------------进入撤防的方法------------------------");
        //type=0防区撤布防；type=1分区撤布防
        if("0".equals(type)){
            channleId=Integer.toString(Integer.parseInt(channleId)+1);
            channleId=DeviceId+"$"+UnitNodesType+"$0$"+channleId;
        }
        byte[] ids = getChannelByte(channleId);
//        long startTime = 1698870059000L;
//        long endTime = 1698950399000L;
        System.out.println("撤防参数为：");
        System.out.println(m_nDLLHandle);
        System.out.println(ids);
        System.out.println(operatChannel);
        System.out.println(cheType);
//        System.out.println(startTime);
//        System.out.println(endTime);
        int nRet;
        if("1".equals(type)){
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle,ids,operatDevice,cheType,0,0,100000);
        }else{
            nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle,ids,operatChannel,cheType,0,0,100000);
        }
        System.out.println("撤防结果，nRet为"+nRet);
        if(nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS){
            redisUtil.set(outParamId, "0", 5);
            System.out.printf("撤防成功，通道id为", channleId);
        }else{
            System.out.printf("撤防失败，通道id为", channleId);
        }
        return nRet;
    }
    /*
    *通道id（channleId）转数组
     */
    public byte[] getChannelByte(String channleId){
        // 创建一个与字符串长度相同的 byte 数组
        byte[] byteArray = channleId.getBytes();
        return byteArray;
    }
    public long getStartTime(){
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis;
    }
    public long getEndTime(){
        LocalDate currentDate = LocalDate.now();
        LocalTime time = LocalTime.of(23, 59, 59);
        LocalDateTime dateTime = LocalDateTime.of(currentDate, time);
        long timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return timestamp;
    }
}

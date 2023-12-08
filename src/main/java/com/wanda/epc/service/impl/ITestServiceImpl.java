package com.wanda.epc.service.impl;

import com.alibaba.fastjson.JSON;
import com.dh.DpsdkCore.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.wanda.epc.service.TestService;
import com.wanda.epc.vo.Channel;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Device;
import com.wanda.epc.vo.Devices;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ITestServiceImpl implements TestService {

    public static int m_nDLLHandle = 1;
    //通道布撤防
    public static int operatChannel = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_CHANNEL;
    //设备布撤防
    public static int operatDevice = dpsdk_netalarmhost_operator_e.AHOST_OPERATE_DEVICE;
    //布防类型
    public static int buType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_ARM;
    //撤防类型
    public static int cheType = dpsdk_AlarmhostOperator_e.CONTROL_DEV_DISARM;
    public static String channelId = "1000012$3$0$3";
    public static String deviceId = "1000012";
    public fDPSDKDevStatusCallback fDeviceStatus = new fDPSDKDevStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szDeviceId, int nStatus) {
            log.info("回调函数fDeviceStatus的返回值，szDeviceId：" + new String(szDeviceId));
            log.info("回调函数fDeviceStatus的返回值，nStatus：" + nStatus);
            Device_Info_Ex_t deviceInfo = new Device_Info_Ex_t();
            String status = "离线";
            if (nStatus == 1) {
                status = "在线";
                log.info("DPSDK_GetDeviceInfoExById之前的参数，deviceInfo：" + JSON.toJSON(deviceInfo));
                //查询设备信息
                int nRet = IDpsdkCore.DPSDK_GetDeviceInfoExById(m_nDLLHandle, szDeviceId, deviceInfo);
                log.info("DPSDK_GetDeviceInfoExById之后的参数，deviceInfo：" + JSON.toJSON(deviceInfo));
                if (deviceInfo.nDevType == dpsdk_dev_type_e.DEV_TYPE_NVR) {
                    //查询NVR通道信息
                    nRet = IDpsdkCore.DPSDK_QueryNVRChnlStatus(m_nDLLHandle, szDeviceId, 10 * 1000);
                    log.info("DPSDK_QueryNVRChnlStatus之后的参数，deviceInfo：" + JSON.toJSON(deviceInfo));
                    if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
                        log.info("查询NVR通道状态成功，deviceID = %s", new String(szDeviceId));
                    } else {
                        log.error("查询NVR通道状态失败，deviceID = %s, {}", new String(szDeviceId), nRet);
                    }
                    //
                }
            }
            //log.info("Device Status Report!, szDeviceId = %s, nStatus = %s", new String(szDeviceId),status);
            //
        }
    };
    public fDPSDKNVRChnlStatusCallback fNVRChnlStatus = new fDPSDKNVRChnlStatusCallback() {
        @Override
        public void invoke(int nPDLLHandle, byte[] szCameraId, int nStatus) {
            log.info("回调函数的返回值，szDeviceId：" + new String(szCameraId));
            log.info("回调函数的返回值，nStatus：" + nStatus);
            String status = "离线";
            if (nStatus == 1) {
                status = "在线";
            }
            //log.info("NVR Channel Status Report!, szCameraId = %s, nStatus = %s", new String(szCameraId),status);
            //
        }
    };
    Return_Value_Info_t nGroupLen = new Return_Value_Info_t();
    @Value("${epc.DeviceId}")
    private String DeviceId;

    //    public static void main(String[] args) throws Exception {
//        String xmlString = "<Organization>\n" +
//                "\t<Department coding=\"001\" name=\"仓山万达商管\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"0\" chargebooth=\"0\" OrgNum=\"\">\n" +
//                "\t\t<Device id=\"1000005\" />\n" +
//                "\t\t<Device id=\"1000006\" />\n" +
//                "\t\t<Channel id=\"1000005$2$0$0\" />\n" +
//                "\t\t<Channel id=\"1000005$2$0$1\" />\n" +
//                "\t\t<Department coding=\"001001\" name=\"室内步行街\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"1\" chargebooth=\"0\" OrgNum=\"\">\n" +
//                "\t\t\t<Device id=\"1000000\" />\n" +
//                "\t\t\t<Device id=\"1000001\" />\n" +
//                "\t\t\t<Channel id=\"1000000$1$0$0\" />\n" +
//                "\t\t\t<Channel id=\"1000000$1$0$1\" />\n" +
//                "\t\t</Department>\n" +
//                "\t\t<Department coding=\"001002\" name=\"地下室\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"2\" chargebooth=\"0\" OrgNum=\"\">\n" +
//                "\t\t\t<Device id=\"1000002\" />\n" +
//                "\t\t\t<Device id=\"1000010\" />\n" +
//                "\t\t\t<Channel id=\"1000002$1$0$0\" />\n" +
//                "\t\t\t<Channel id=\"1000002$1$0$1\" />\n" +
//                "\t\t</Department>\n" +
//                "\t\t<Department coding=\"001003\" name=\"外广场\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"3\" chargebooth=\"0\" OrgNum=\"\">\n" +
//                "\t\t\t<Device id=\"1000003\" />\n" +
//                "\t\t\t<Channel id=\"1000003$1$0$0\" />\n" +
//                "\t\t\t<Channel id=\"1000003$1$0$1\" />\n" +
//                "\t\t</Department>\n" +
//                "\t</Department>\n" +
//                "\t<Devices>\n" +
//                "\t\t<Device id=\"1000000\" type=\"1\" name=\"EVS-1\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.1\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.1\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
//                "\t\t\t<UnitNodes index=\"0\" channelnum=\"109\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
//                "\t\t\t\t<Channel id=\"1000000$1$0$0\" name=\"一层12号货梯前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"10001\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4zNTIuNTYzNTUxNDAxODc8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+MTMyLjcwMjE4MDY4NTM2PC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"10001\" latitude=\"132.70218068536\" longitude=\"352.56355140187\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t\t<Channel id=\"1000000$1$0$1\" name=\"开闭所操作区\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"10002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4yODIuMjc3NTcwMDkzNDY8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+LTIzNS4zNTQ1MTcxMzM5NjwvTGF0aXR1ZGU+CjwvQ2FzY2FkZUV4dGVuc2lvbj4=\" cameraType=\"2\" CtrlId=\"10002\" latitude=\"-235.35451713396\" longitude=\"282.27757009346\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t</UnitNodes>\n" +
//                "\t\t</Device>\n" +
//                "\t\t<Device id=\"1000001\" type=\"1\" name=\"EVS-2\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.2\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.2\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
//                "\t\t\t<UnitNodes index=\"0\" channelnum=\"106\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
//                "\t\t\t\t<Channel id=\"1000001$1$0$0\" name=\"2F9-10#手扶梯前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"20001\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4xNzYuNDkwNjQ5MDY0OTE8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+NjAuOTk1MDQ5NTA0OTUxPC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"20001\" latitude=\"60.995049504951\" longitude=\"176.49064906491\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t\t<Channel id=\"1000001$1$0$1\" name=\"2F中部连廊苏宁联通口\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"20002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4xMjcuNTAyNzUwMjc1MDM8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+NzguNDQyNzk0Mjc5NDI4PC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"20002\" latitude=\"78.442794279428\" longitude=\"127.50275027503\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t</UnitNodes>\n" +
//                "\t\t</Device>\n" +
//                "\t\t<Device id=\"1000002\" type=\"1\" name=\"EVS-3\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.3\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.3\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
//                "\t\t\t<UnitNodes index=\"0\" channelnum=\"109\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
//                "\t\t\t\t<Channel id=\"1000002$1$0$0\" name=\"空\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"40001\" rights=\"0\" cameraType=\"2\" CtrlId=\"40001\" latitude=\"\" longitude=\"\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t\t<Channel id=\"1000002$1$0$1\" name=\"F005车位前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"40002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4yMDMuNzg4MTU1NjczNDc8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+LTMyLjI2MzkwMjM1NDAxNTwvTGF0aXR1ZGU+CjwvQ2FzY2FkZUV4dGVuc2lvbj4=\" cameraType=\"2\" CtrlId=\"40002\" latitude=\"-32.263902354015\" longitude=\"203.78815567347\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
//                "\t\t\t</UnitNodes>\n" +
//                "\t\t</Device>\n" +
//                "\t</Devices>\n" +
//                "</Organization>";
//        xmlString=getDeviceString(xmlString);
//        parse(xmlString);
//    }
    public static String getDeviceString(String xml) {
        String startTag = "<Devices>";
        String endTag = "</Devices>";
        int startIndex = xml.indexOf(startTag);
        int endIndex = xml.indexOf(endTag) + endTag.length();
        String result = xml.substring(startIndex, endIndex);
        return result;
    }

    public static Devices parse(String xml) throws Exception {
        // Parse XML string into Document object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        // Find root element and parse it recursively
        Element root = doc.getDocumentElement();
        List<Device> deviceList = parseDepartment(root);
        Devices devices = new Devices();
        devices.setDevice(deviceList);
        // 创建Gson对象
        Gson gson = new Gson();
        // 将对象转换为JSON字符串
        String json = gson.toJson(devices);
        log.info("获取json组织树： " + json);
        return devices;
    }

    private static List<Device> parseDepartment(Element el) {
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

    public static long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        long timestamp = date.getTime();
        return timestamp;
    }

    public static long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        long timestamp = date.getTime();
        return timestamp;
    }

    @Override
    public Devices getTree() throws Exception {
        log.info("------------------------------开始查询组织树------------------------");
        int nRet1 = IDpsdkCore.DPSDK_LoadDGroupInfo(m_nDLLHandle, nGroupLen, 180000);
        byte[] szGroupBuf = new byte[nGroupLen.nReturnValue];
        log.info("获取所有组织树串参数m_nDLLHandle：" + m_nDLLHandle + ";szGroupBuf：" + szGroupBuf + ";nGroupLen.nReturnValue：" + nGroupLen.nReturnValue);
        int nRet = IDpsdkCore.DPSDK_GetDGroupStr(m_nDLLHandle, szGroupBuf, nGroupLen.nReturnValue, 10000);
        log.info("------------------------------是否查询成功------------------------" + nRet);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(szGroupBuf);
            // Parse input stream into Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(inputStream));
            // Convert Document object to XML string
            String xmlString = documentToString(doc);
            log.info("获取组织树： " + xmlString);
            xmlString = getDeviceString(xmlString);
            //设置设备状态上报监听函数
            Devices res = parse(xmlString);
            nRet = IDpsdkCore.DPSDK_SetDPSDKDeviceStatusCallback(m_nDLLHandle, fDeviceStatus);
            //设置NVR通道状态上报监听函数
            nRet = IDpsdkCore.DPSDK_SetDPSDKNVRChnlStatusCallback(m_nDLLHandle, fNVRChnlStatus);
            return res;
        }
        return null;
    }

    @Override
    public List<ChannelInfo> getChannelInfo() throws Exception {
        byte[] szDeviceId = getChannelByte(DeviceId);
        Devices device = getTree();
        List<Device> deviceList = device.getDevice();
        List<Channel> channelList = new ArrayList<>();
        for (Device vo : deviceList) {
            if (DeviceId.equals(vo.getId())) {
                channelList = vo.getChannels();
            }
        }
        int size = channelList.size();
        dpsdk_AHostDefenceStatus_t[] hostDefenceStatusArray = new dpsdk_AHostDefenceStatus_t[size];
        for (int i = 0; i < size; i++) {
            dpsdk_AHostDefenceStatus_t defenceStatus = new dpsdk_AHostDefenceStatus_t();
            hostDefenceStatusArray[i] = defenceStatus;
        }
        log.info("==============查询channel信息接口参数m_nDLLHandle：" + m_nDLLHandle);
        log.info("==============查询channel信息接口参数szDeviceId：" + szDeviceId);
        log.info("==============查询channel信息接口参数channelList.size()：" + channelList.size());
        log.info("==============查询channel信息接口参数hostDefenceStatusArray：" + hostDefenceStatusArray);
        int nRet = IDpsdkCore.DPSDK_QueryNetAlarmHostStatus(m_nDLLHandle, szDeviceId, channelList.size(), hostDefenceStatusArray, 1000);
        log.info("==============查询channel信息接口====" + nRet);
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
            // 将 hostDefenceStatusArray 转换为 JSON 字符串
            String json = objectMapper.writeValueAsString(res);
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
        // 找到最后一个$符号的索引位置
        int lastIndex = res.lastIndexOf("$");
        // 在最后一个$符号前插入$0
        String modifiedString = res.substring(0, lastIndex) + "$0" + res.substring(lastIndex);
        log.info(modifiedString);
        return res;
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

    @Override
    public int testBU() {
        log.info("------------------------------进入testBU方法------------------------");
        byte[] ids = getChannelByte(channelId);
        long startTime = getStartTime();
        long endTime = getEndTime();
        int nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operatChannel, buType, startTime, endTime, 100000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("布防成功，通道id为", channelId);
        } else {
            log.error("布防失败，通道id为", channelId);
        }
        return nRet;
    }

    @Override
    public int testCHE() {
        log.info("------------------------------testCHE------------------------");
        byte[] ids = getChannelByte(channelId);
        long startTime = getStartTime();
        long endTime = getEndTime();
        int nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operatChannel, cheType, startTime, endTime, 100000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("撤防成功，通道id为", channelId);
        } else {
            log.error("撤防失败，通道id为", channelId);
        }
        return nRet;
    }

    @Override
    public int testBuDevice() {
        log.info("------------------------------进入testBU方法------------------------");
        byte[] ids = getDeviceByte();
        long startTime = 1698870059000L;
        long endTime = 1698950399000L;
        int nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operatDevice, buType, 0, 0, 100000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("布防成功，设备id为", deviceId);
        } else {
            log.error("布防失败，设备id为", deviceId);
        }
        return nRet;
    }

    @Override
    public int testCheDevice() {
        log.info("------------------------------testCHE------------------------");
        byte[] ids = getDeviceByte();
        long startTime = 1698870059000L;
        long endTime = 1698950399000L;
        int nRet = IDpsdkCore.DPSDK_ControlNetAlarmHostCmd(m_nDLLHandle, ids, operatDevice, cheType, 0, 0, 100000);
        if (nRet == dpsdk_retval_e.DPSDK_RET_SUCCESS) {
            log.info("撤防成功，设备id为", deviceId);
        } else {
            log.error("撤防失败，设备id为", deviceId);
        }
        return nRet;
    }

    public byte[] getChannelByte(String channelId) {
        // 创建一个与字符串长度相同的 byte 数组
        byte[] byteArray = channelId.getBytes();
        return byteArray;
    }

    public byte[] getDeviceByte() {
        // 创建一个与字符串长度相同的 byte 数组
        byte[] byteArray = deviceId.getBytes();
        return byteArray;
    }
}

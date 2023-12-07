package com.wanda.epc.controller;

import com.wanda.epc.service.TestService;
import com.wanda.epc.vo.ChannelInfo;
import com.wanda.epc.vo.Devices;
import com.wanda.epc.vo.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/testController")
@Slf4j
public class TestController {


    @Autowired
    TestService testService;

    public static void main(String[] args) {
        String xmlData = "<Organization>\n" +
                "\t<Department coding=\"001\" name=\"仓山万达商管\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"0\" chargebooth=\"0\" OrgNum=\"\">\n" +
                "\t\t<Device id=\"1000005\" />\n" +
                "\t\t<Device id=\"1000006\" />\n" +
                "\t\t<Channel id=\"1000005$2$0$0\" />\n" +
                "\t\t<Channel id=\"1000005$2$0$1\" />\n" +
                "\t\t\n" +
                "\t\t<Department coding=\"001001\" name=\"室内步行街\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"1\" chargebooth=\"0\" OrgNum=\"\">\n" +
                "\t\t\t<Device id=\"1000000\" />\n" +
                "\t\t\t<Device id=\"1000001\" />\n" +
                "\t\t\t<Channel id=\"1000000$1$0$0\" />\n" +
                "\t\t\t<Channel id=\"1000000$1$0$1\" />\n" +
                "\t\t</Department>\n" +
                "\t\t<Department coding=\"001002\" name=\"地下室\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"2\" chargebooth=\"0\" OrgNum=\"\">\n" +
                "\t\t\t<Device id=\"1000002\" />\n" +
                "\t\t\t<Device id=\"1000010\" />\n" +
                "\t\t\t<Channel id=\"1000002$1$0$0\" />\n" +
                "\t\t\t<Channel id=\"1000002$1$0$1\" />\n" +
                "\t\t</Department>\n" +
                "\t\t<Department coding=\"001003\" name=\"外广场\" modifytime=\"\" sn=\"\" memo=\"\" deptype=\"1\" depsort=\"3\" chargebooth=\"0\" OrgNum=\"\">\n" +
                "\t\t\t<Device id=\"1000003\" />\n" +
                "\t\t\t<Channel id=\"1000003$1$0$0\" />\n" +
                "\t\t\t<Channel id=\"1000003$1$0$1\" />\n" +
                "\t\t</Department>\n" +
                "\t\t\n" +
                "\t</Department>\n" +
                "\t\n" +
                "\t<Devices>\n" +
                "\t\t<Device id=\"1000000\" type=\"1\" name=\"EVS-1\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.1\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.1\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
                "\t\t\t<UnitNodes index=\"0\" channelnum=\"109\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
                "\t\t\t\t<Channel id=\"1000000$1$0$0\" name=\"一层12号货梯前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"10001\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4zNTIuNTYzNTUxNDAxODc8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+MTMyLjcwMjE4MDY4NTM2PC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"10001\" latitude=\"132.70218068536\" longitude=\"352.56355140187\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t\t<Channel id=\"1000000$1$0$1\" name=\"开闭所操作区\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"10002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4yODIuMjc3NTcwMDkzNDY8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+LTIzNS4zNTQ1MTcxMzM5NjwvTGF0aXR1ZGU+CjwvQ2FzY2FkZUV4dGVuc2lvbj4=\" cameraType=\"2\" CtrlId=\"10002\" latitude=\"-235.35451713396\" longitude=\"282.27757009346\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t</UnitNodes>\n" +
                "\t\t</Device>\n" +
                "\t\t<Device id=\"1000001\" type=\"1\" name=\"EVS-2\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.2\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.2\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
                "\t\t\t<UnitNodes index=\"0\" channelnum=\"106\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
                "\t\t\t\t<Channel id=\"1000001$1$0$0\" name=\"2F9-10#手扶梯前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"20001\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4xNzYuNDkwNjQ5MDY0OTE8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+NjAuOTk1MDQ5NTA0OTUxPC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"20001\" latitude=\"60.995049504951\" longitude=\"176.49064906491\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t\t<Channel id=\"1000001$1$0$1\" name=\"2F中部连廊苏宁联通口\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"20002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4xMjcuNTAyNzUwMjc1MDM8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+NzguNDQyNzk0Mjc5NDI4PC9MYXRpdHVkZT4KPC9DYXNjYWRlRXh0ZW5zaW9uPg==\" cameraType=\"2\" CtrlId=\"20002\" latitude=\"78.442794279428\" longitude=\"127.50275027503\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t</UnitNodes>\n" +
                "\t\t</Device>\n" +
                "\t\t<Device id=\"1000002\" type=\"1\" name=\"EVS-3\" manufacturer=\"1\" model=\"\" ip=\"172.16.10.3\" port=\"37777\" user=\"admin\" password=\"jGZu8XhIWJcltKgk4WrXHA==\" desc=\"\" status=\"1\" logintype=\"\" registDeviceCode=\"\" proxyport=\"0\" unitnum=\"0\" deviceCN=\"\" deviceSN=\"\" deviceIp=\"172.16.10.3\" devicePort=\"37777\" devMaintainer=\"\" devMaintainerPh=\"\" deviceLocation=\"\" deviceLocPliceStation=\"\" baudRate=\"\" comCode=\"\" VideoType=\"\" shopName=\"\" address=\"\" firstOwner=\"\" firstPosition=\"\" firstPhone=\"\" firstTel=\"\" serviceType=\"0\" ownerGroup=\"\" belong=\"\" role=\"0\" callNumber=\"\" rights=\"0\">\n" +
                "\t\t\t<UnitNodes index=\"0\" channelnum=\"109\" streamType=\"801\" subType=\"0\" type=\"1\" zeroChnEncode=\"0\">\n" +
                "\t\t\t\t<Channel id=\"1000002$1$0$0\" name=\"空\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"40001\" rights=\"0\" cameraType=\"2\" CtrlId=\"40001\" latitude=\"\" longitude=\"\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t\t<Channel id=\"1000002$1$0$1\" name=\"F005车位前\" desc=\"\" status=\"0\" channelType=\"1\" channelSN=\"40002\" rights=\"0\" cmsXmlExt=\"PENhc2NhZGVFeHRlbnNpb24+CiAgPExvbmdpdHVkZT4yMDMuNzg4MTU1NjczNDc8L0xvbmdpdHVkZT4KICA8TGF0aXR1ZGU+LTMyLjI2MzkwMjM1NDAxNTwvTGF0aXR1ZGU+CjwvQ2FzY2FkZUV4dGVuc2lvbj4=\" cameraType=\"2\" CtrlId=\"40002\" latitude=\"-32.263902354015\" longitude=\"203.78815567347\" viewDomain=\"\" cameraFunctions=\"0\" multicastIp=\"\" multicastPort=\"0\" NvrChnlIp=\"\" channelRemoteType=\"\" subMulticastIp=\"\" subMulticastPort=\"0\" />\n" +
                "\t\t\t</UnitNodes>\n" +
                "\t\t</Device>\n" +
                "\t</Devices>\n" +
                "\t\n" +
                "</Organization>";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Organization.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Organization organization = (Organization) unmarshaller.unmarshal(new StringReader(xmlData));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/testBU")
    public int testBU() {
        log.info("------------------------------进入controller------------------------");
        return testService.testBU();
    }

    @GetMapping("/testCHE")
    public int testCHE() {
        log.info("------------------------------进入controller------------------------");
        return testService.testCHE();
    }

    @GetMapping("/testBUDevice")
    public int testBUDevice() {
        log.info("------------------------------进入controller------------------------");
        return testService.testBuDevice();
    }

    @GetMapping("/testCHEDevice")
    public int testCHEDevice() {
        log.info("------------------------------进入controller------------------------");
        return testService.testCheDevice();
    }

    @GetMapping("/getTree")
    public Devices getTree() throws Exception {
        log.info("------------------------------getTree进入controller------------------------");
        return testService.getTree();
    }

    @GetMapping("/getChannelInfo")
    public List<ChannelInfo> getChannelInfo() throws Exception {
        log.info("------------------------------getChannelInfo进入controller------------------------");
        return testService.getChannelInfo();
    }
}

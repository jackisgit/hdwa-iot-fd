package com.wanda.epc.device;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 博世防盗设备对接
 */
@Slf4j
@Service
public class SerialCommunication implements SerialPortEventListener {
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    static int BUFFER_SIZE = 4096;
    byte[] m_msgBuffer = new byte[BUFFER_SIZE];
    private int readmax = 100;
    @Autowired
    private BoshiFD boshiFD;

    @Value("${serial.serialNumber}")
    private String serialNumber;

    @Value("${serial.baudRate}")
    private int baudRate;

    @Value("${serial.checkoutBit}")
    private int checkoutBit;

    @Value("${serial.dataBit}")
    private int dataBit;

    @Value("${serial.stopBit}")
    private int stopBit;

    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
                log.info("有可读取数据");
                try {
                    int availableBytes = serialPort.getInputStream().available();
                    if (availableBytes > 0) {
                        byte[] buff = SerialTool.readFromPort(serialPort);
                        boshiFD.dataArrive(buff);
                    }
                } catch (Exception e) {
                    log.error("读取流信息异常！" + e);
                }

            default:
                break;

        }
    }

    /**
     * 通过程序打开COM4串口，设置监听器以及相关的参数
     *
     * @return 返回1 表示端口打开成功，返回 0表示端口打开失败
     */
    @PostConstruct
    public int startComPort() {
        // 通过串口通信管理类获得当前连接上的串口列表
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        if (!portList.hasMoreElements()) {
            log.error("未查到串口列表");
            return 0;
        } else {
            List<String> portNameList = new ArrayList<String>();
            while (portList.hasMoreElements()) {
                String portName = portList.nextElement().getName();
                portNameList.add(portName);
            }
            log.info("可用串口列表,{}", JSONObject.toJSONString(portNameList));
        }
        CommPortIdentifier portIdentifier = null;
        try {
            portIdentifier = CommPortIdentifier.getPortIdentifier(serialNumber);
        } catch (Exception e) {
            log.error("获取串口失败", e);
            return 0;
        }
        try {
            // 打开串口名字为COM_4(名字任意),延迟为2毫秒
            serialPort = (SerialPort) portIdentifier.open(serialNumber, 2000);
        } catch (Exception e) {
            log.error("串口打开失败", e);
            return 0;
        }
        // 设置当前串口的输入输出流
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (Exception e) {
            log.error("串口获取输入/输出流失败", e);
            return 0;
        }
        // 给当前串口添加一个监听器
        try {
            serialPort.addEventListener(this);
            serialPort.setInputBufferSize(1024);
        } catch (Exception e) {
            log.error("串口添加监视器失败", e);
            return 0;
        }
        // 设置监听器生效，即：当有数据时通知
        serialPort.notifyOnDataAvailable(true);
        // 设置串口的一些读写参数
        try {
            // 比特率、数据位、停止位、奇偶校验位
            serialPort.setSerialPortParams(baudRate, dataBit, stopBit, checkoutBit);
        } catch (Exception e) {
            log.error("串口设置串口的一些读写参数失败", e);
            return 0;
        }
        return 1;
    }

}

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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 博世防盗设备对接
 */
@Slf4j
@Service
public class SerialCommunication implements SerialPortEventListener {
    /**
     * 从串口来的输入流
     */
    InputStream inputStream;
    /**
     * 向串口输出的流
     */
    static OutputStream outputStream;
    /**
     * 串口的引用
     */
    static SerialPort serialPort;

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
     * 发送数组集合，线程安全
     */
    List<String> list = Collections.synchronizedList(new ArrayList<>());

    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    int availableBytes = serialPort.getInputStream().available();
                    if (availableBytes <= 0) {
                        return;
                    }
                    byte[] buff = readFromPort(serialPort);
                    String hex = toHex(buff, 0, buff.length);
                    log.info("收到防盗报警数据，16进制为:{}", hex);
                    for (int i = 0; i < buff.length; i++) {
                        //如果buff是以133（16进制的85开头），则需要清空当前数组
                        byte b = buff[i];
                        if (b == (byte) 0x85) {
                            list.clear();
                        }
                        //转换当前buff为16进制字符串并添加到待解析的数组中
                        list.add(String.valueOf(b));
                        if (list.size() == 4) {
                            log.info("待发送的10进制数据为：{}", JSONObject.toJSONString(list));
                            boshiFD.dataArrive(list);
                        }
                    }
                } catch (Exception e) {
                    log.error("读取流信息异常！", e);
                }
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:

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
            // 打开串口(名字任意),延迟为2毫秒
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

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    private byte[] readFromPort(SerialPort serialPort) throws Exception {
        InputStream in = null;
        byte[] bytes = null;
        try {
            if (serialPort != null) {
                in = serialPort.getInputStream();
            } else {
                return null;
            }
            // 获取buffer里的数据长度
            int bufflenth = in.available();
            while (bufflenth != 0) {
                // 初始化byte数组为buffer中数据的长度
                bytes = new byte[bufflenth];
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                log.error("IO异常", e);
            }
        }
        return bytes;
    }

    /**
     * 进制转换
     *
     * @param data
     * @param off
     * @param length
     * @return
     */
    private static String toHex(byte[] data, int off, int length) {
        StringBuffer buf = new StringBuffer(data.length * 2);
        for (int i = off; i < length; i++) {
            if ((data[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString((data[i] & 0xFF), 16));
            if (i < data.length - 1) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }

}

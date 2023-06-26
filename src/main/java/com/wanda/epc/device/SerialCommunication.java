package com.wanda.epc.device;

import gnu.io.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * 博世防盗设备对接
 */
@Slf4j
@Service
public class SerialCommunication implements SerialPortEventListener {
    // 监听器,我的理解是独立开辟一个线程监听串口数据
    static CommPortIdentifier portId; // 串口通信管理类
    static Enumeration<?> portList; // 有效连接上的端口的枚举
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    static int BUFFER_SIZE = 4096;
    byte[] m_msgBuffer = new byte[BUFFER_SIZE];
    private int readmax = 100;
    @Autowired
    private BoshiFD boshiFD;

    @Value("${epc.serialNumber}")
    private String serialNumber;

    @Value("${epc.baudRate}")
    private int baudRate;

    @Value("${epc.checkoutBit}")
    private int checkoutBit;

    @Value("${epc.dataBit}")
    private int dataBit;

    @Value("${epc.stopBit}")
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
                int len = -1;
                try {
                    Thread.sleep(300L);
                    if (this.readmax == 0)
                        this.readmax = 100;
                    len = inputStream.read(this.m_msgBuffer, 0, this.readmax);
                    byte[] buff = new byte[len];
                    System.arraycopy(this.m_msgBuffer, 0, buff, 0, len);
                    boshiFD.dataArrive(buff);
                } catch (Exception e) {
                    log.error("数据读取错误 error:" + e.getMessage());
                }
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
        portList = CommPortIdentifier.getPortIdentifiers();
        log.info("查到串口列表[{}]", portList);
        while (portList.hasMoreElements()) {
            // 获取相应串口对象
            portId = (CommPortIdentifier) portList.nextElement();
            log.info("获取相应串口对象[{}]", portId);
            // 判断端口类型是否为串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                try {
                    // 打开串口名字为COM_4(名字任意),延迟为2毫秒
                    serialPort = (SerialPort) portId.open(serialNumber, 2000);
                } catch (PortInUseException e) {
                    log.error("串口打开失败", e);
                    return 0;
                }
                // 设置当前串口的输入输出流
                try {
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                } catch (IOException e) {
                    log.error("串口获取输入/输出流失败", e);
                    return 0;
                }
                // 给当前串口添加一个监听器
                try {
                    serialPort.addEventListener(this);
                    serialPort.setInputBufferSize(1024);
                } catch (TooManyListenersException e) {
                    log.error("串口添加监视器失败", e);
                    return 0;
                }
                // 设置监听器生效，即：当有数据时通知
                serialPort.notifyOnDataAvailable(true);
                // 设置串口的一些读写参数
                try {
                    // 比特率、数据位、停止位、奇偶校验位
                    serialPort.setSerialPortParams(baudRate, dataBit, stopBit, checkoutBit);
                } catch (UnsupportedCommOperationException e) {
                    log.error("串口设置串口的一些读写参数失败", e);
                    return 0;
                }
                return 1;
            }
        }
        return 0;
    }


}

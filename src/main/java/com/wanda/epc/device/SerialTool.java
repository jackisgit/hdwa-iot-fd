package com.wanda.epc.device;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import purejavacomm.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

@Component
@Slf4j
public class SerialTool {

	public static final ArrayList<String> findPorts() {
		// 获得当前所有可用串口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portNameList = new ArrayList<String>();
		// 将可用串口名添加到List并返回该List
		while (portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();
			portNameList.add(portName);
		}
		return portNameList;
	}

    public static void main(String[] args) {
        ArrayList<String> ports = findPorts();
        log.info("111111");
    }

	/**
	 * 打开串口
	 *
	 * @param portName
	 *            端口名称
	 * @param baudrate
	 *            波特率
	 * @return 串口对象
	 * @throws Exception
	 * @throws SerialPortParameterFailure
	 *             设置串口参数失败
	 * @throws NotASerialPort
	 *             端口指向设备不是串口类型
	 * @throws NoSuchPort
	 *             没有该端口对应的串口设备
	 * @throws PortInUse
	 *             端口已被占用
	 */
	public static SerialPort openPort(String portName, Integer baudrate, Integer dataBits, Integer stopBits,
			Integer parity) throws Exception {

		try {

			// 通过端口名识别端口
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

			// 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
			CommPort commPort = portIdentifier.open(portName, 2000);

			// 判断是不是串口
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				try {
					// 设置一下串口的波特率等参数
					serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
					log.info("串口" + portName + "打开成功");
				} catch (UnsupportedCommOperationException e) {
					log.error("设置串口" + portName + "参数失败：" + e.getMessage());
					throw e;
				}

				return serialPort;

			} else {
				log.error("不是串口" + portName);
				// 不是串口
				throw new Exception();
			}
		} catch (NoSuchPortException e1) {
			log.error("无此串口" + portName);
			throw e1;
		} catch (PortInUseException e2) {
			log.error("串口使用中" + portName);
			throw e2;
		} catch (Exception e) {
			throw e;
		}
	}

	public static byte[] HexString2Bytes(String src) {
		if (null == src || 0 == src.length()) {
			return null;
		}
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < (tmp.length / 2); i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	// byte类型数据，转成十六进制形式；
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 关闭串口
	 *
	 * @throws IOException
	 */
	public static synchronized void closePort(SerialPort serialPort) throws IOException {
		if (serialPort != null) {
			serialPort.close();
			log.info("串口" + serialPort.getName() + "已关闭");
		}
	}

	/**
	 * 往串口发送数据
	 *
	 * @param order
	 *            待发送数据
	 * @throws SendDataToSerialPortFailure
	 *             向串口发送数据失败
	 * @throws SerialPortOutputStreamCloseFailure
	 *             关闭串口对象的输出流出错
	 */
	public static void sendToPort(byte[] order, SerialPort serialPort) throws IOException {

		OutputStream out = null;

		try {

			out = serialPort.getOutputStream();
			out.write(order);
			out.flush();
			log.info("发送数据成功" + serialPort.getName());
		} catch (IOException e) {
			log.error("发送数据失败" + serialPort.getName());
			throw e;
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				log.error("关闭串口对象的输出流出错");
				throw e;
			}
		}

	}

	/**
	 * 从串口读取数据
	 *
	 * @param serialPort
	 *            当前已建立连接的SerialPort对象
	 * @return 读取到的数据
	 * @throws ReadDataFromSerialPortFailure
	 *             从串口读取数据时出错
	 * @throws SerialPortInputStreamCloseFailure
	 *             关闭串口对象输入流出错
	 */
	public static byte[] readFromPort(SerialPort serialPort) throws Exception {

		InputStream in = null;
		byte[] bytes = null;

		try {
			if (serialPort != null) {
				in = serialPort.getInputStream();
			} else {
				return null;
			}
			int bufflenth = in.available(); // 获取buffer里的数据长度
			while (bufflenth != 0) {
				bytes = new byte[bufflenth]; // 初始化byte数组为buffer中数据的长度
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
				throw e;
			}

		}

		return bytes;

	}

	/**
	 * 添加监听器
	 *
	 * @param port
	 *            串口对象
	 * @param listener
	 *            串口监听器
	 * @throws TooManyListeners
	 *             监听类对象过多
	 */
	public static void addListener(SerialPortEventListener listener, SerialPort serialPort) throws TooManyListenersException {

		try {

			// 给串口添加监听器
			serialPort.addEventListener(listener);
			// 设置当有数据到达时唤醒监听接收线程
			serialPort.notifyOnDataAvailable(true);
			// 设置当通信中断时唤醒中断线程
			serialPort.notifyOnBreakInterrupt(true);

		} catch (TooManyListenersException e) {
			throw e;
		}
	}

}


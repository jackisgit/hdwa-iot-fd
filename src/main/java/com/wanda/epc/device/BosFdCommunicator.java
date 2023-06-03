package com.wanda.epc.device;
import com.sun.jna.Pointer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Slf4j
@Data
@Component
public class BosFdCommunicator implements Runnable {

    private Vector<BostFdDataArriveListener> eventRepository = new Vector<>();

    private Pointer pObject;

    private String rcvAddress = "10.11.80.85|7700";

    private String pCtlAddress = "10.11.80.84|7700";

    private String serviceIp = "10.11.80.224|7700";

    private boolean interrupted = false;

    public static Long sysTime = 0l;

    private boolean isRevice = true;

    public void openReceived() {
        pObject = BoschSecurity.BS7400CtlDLL.instance.New_Object(); // 实例化panel
        BoschSecurity.BS7400CtlDLL.instance.ArrangeRcvAddress(pObject, rcvAddress, pCtlAddress);

        int dwResult = BoschSecurity.BS7400CtlDLL.instance.OpenReceiver(pObject,
                new BoschSecurity.Trandataproc() {
                    @Override
                    public void invoke(Pointer pPara, String sRcvData,
                                       int iDataLen) {
                        // 将监听读取到的数据放入监听类，返回给BGM
                        for (BostFdDataArriveListener listener : eventRepository) {
                            // 将数据放入此队列
                            listener.dataArrive(sRcvData);
                        }
                        System.out.println("re:" + sRcvData);//事件数据
                        System.out.println("re:" + iDataLen);//事件数据长度
                    }

                }, pObject);
        if (dwResult == 0) {
            System.out.println("打开接收事件/发送控制功能的IP地址成功打开");
//			logger.debug("打开接收事件/发送控制功能的IP地址成功打开");
        } else if (dwResult == 7) {
            System.out.println("接收事件的IP地址成功打开");
        } else if (dwResult == 11) {
            System.out.println("发送控制指令IP地址成功打开");
        } else {
            System.out.println("接收事件的IP地址以及发送控制的IP地址无效");
//			logger.debug("接收事件的IP地址以及发送控制的IP地址无效");
        }
    }


    public void write(BosFdData data) {
        boolean canControl = BoschSecurity.BS7400CtlDLL.instance.CanControlPanel(this.pObject);
        log.info("当前发送控制功能是否可用" + canControl);
        int result = BoschSecurity.BS7400CtlDLL.instance.Execute(this.pObject, this.serviceIp, data.getCommand(), data.getsPara(), data.getType());
        String info = "发送命令：命令描述" + data.getDesc() + "，主机的IP地址=" + this.serviceIp + "，命令="+ data.getCommand() + "，参数="+ data.getsPara() + ",主机类型" + data.getType();
        if (0 == result) {
            info = info + "，返回结果，已成功发送控制命令";
        } else if (-3 == result) {
            info = info + "，返回结果，控制命令参数错误";
        } else {
            info = info + "，返回结果，发送控制命令失败";
        }
        log.info(info);
    }

    public void close() {
        BoschSecurity.BS7400CtlDLL.instance.Delete_Object(this.pObject);
    }

    @Override
    public void run() {
        openReceived();
    }

    public void addDataArriveListener(BostFdDataArriveListener listener) {
        this.eventRepository.add(listener);
    }


    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
}

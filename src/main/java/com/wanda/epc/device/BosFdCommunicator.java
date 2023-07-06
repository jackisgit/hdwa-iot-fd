package com.wanda.epc.device;

import com.sun.jna.Pointer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Slf4j
@Data
@Component
public class BosFdCommunicator implements Runnable {

    private Vector<BostFdDataArriveListener> eventRepository = new Vector<>();

    private Pointer pObject;

    @Value("${epc.rcvAddress}")
    private String rcvAddress;

    @Value("${epc.pCtlAddress}")
    private String pCtlAddress;

    @Value("${epc.serviceIp}")
    private String serviceIp;

    private boolean interrupted = false;

    public static Long sysTime = 0l;

    private boolean isRevice = true;

    public void openReceived() {
        // 实例化panel
        pObject = BoschSecurity.BS7400CtlDLL.instance.New_Object();
        BoschSecurity.BS7400CtlDLL.instance.ArrangeRcvAddress(pObject, rcvAddress, pCtlAddress);

        int dwResult = BoschSecurity.BS7400CtlDLL.instance.OpenReceiver(pObject,
                (pPara, sRcvData, iDataLen) -> {
                    // 将监听读取到的数据放入监听类，返回给BGM
                    for (BostFdDataArriveListener listener : eventRepository) {
                        // 将数据放入此队列
                        listener.dataArrive(sRcvData);
                    }
                    //事件数据
                    log.info("事件数据:{},时间数据长度:{}" + sRcvData, iDataLen);
                }, pObject);
        if (dwResult == 0) {
            log.info("打开接收事件/发送控制功能的IP地址成功打开");
        } else if (dwResult == 7) {
            log.info("接收事件的IP地址成功打开");
        } else if (dwResult == 11) {
            log.info("发送控制指令IP地址成功打开");
        } else {
            log.error("接收事件的IP地址以及发送控制的IP地址无效");
        }
    }


    public void write(BosFdData data) {
        boolean canControl = BoschSecurity.BS7400CtlDLL.instance.CanControlPanel(this.pObject);
        log.info("当前发送控制功能是否可用" + canControl);
        int result = BoschSecurity.BS7400CtlDLL.instance.Execute(this.pObject, this.serviceIp, data.getCommand(), data.getsPara(), data.getType());
        String info = "发送命令：命令描述" + data.getDesc() + "，主机的IP地址=" + this.serviceIp + "，命令=" + data.getCommand() + "，参数=" + data.getsPara() + ",主机类型" + data.getType();
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

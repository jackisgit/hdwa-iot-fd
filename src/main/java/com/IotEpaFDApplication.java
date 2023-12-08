package com;

import com.sun.jna.NativeLibrary;
import com.wanda.epc.device.DPSDKCommunication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
@EnableAsync
@Slf4j
public class IotEpaFDApplication {

    public static void main(String[] args) {
        log.info("参数为:{}", Arrays.toString(args));
        String ip = args[0];
        String port = args[1];
        String user = args[2];
        String password = args[3];
        SpringApplication.run(IotEpaFDApplication.class, args);
        // 设置JNI库的搜索路径
//        String libraryPath = "D:\\studycode\\dll";
        String libraryPath = "E:\\wanda\\iepc\\iepc_fd\\dll";
        NativeLibrary.addSearchPath("DPSDK_Core", libraryPath);
        NativeLibrary.addSearchPath("DPSDK_Java", libraryPath);
        NativeLibrary.addSearchPath("dslalien", libraryPath);
        NativeLibrary.addSearchPath("Infra", libraryPath);
        NativeLibrary.addSearchPath("libdsl", libraryPath);
        NativeLibrary.addSearchPath("PicSDK", libraryPath);
        NativeLibrary.addSearchPath("PlatformSDK", libraryPath);
        NativeLibrary.addSearchPath("StreamConvertor", libraryPath);
        NativeLibrary.addSearchPath("StreamPackage", libraryPath);
        NativeLibrary.addSearchPath("StreamParser", libraryPath);
        DPSDKCommunication app = new DPSDKCommunication();
        app.OnCreate();//初始化
        app.OnLogin(ip, Integer.parseInt(port), user, password);//登陆
        app.LoadAllGroup();//加载组织结构
        app.GetGroupStr();//获取组织结构串
//        //加载组织结构之后，要延时5秒钟左右，等待与各服务模块取得连接
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        app.SetAlarm();//打开报警监听,加载组织结构后才能接收到报警信息
        app.run();
        app.OnLogout();
        app.OnDestroy();
    }

}

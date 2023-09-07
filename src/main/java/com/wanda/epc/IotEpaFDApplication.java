package com.wanda.epc;

import com.sun.jna.NativeLibrary;
import com.wanda.epc.device.DPSDKCommunication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAsync
public class IotEpaFDApplication {



    public static void main(String[] args)  {
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

        DPSDKCommunication app=new DPSDKCommunication();
        app.OnCreate();//初始化
        app.OnLogin();//登陆
        app.LoadAllGroup();//加载组织结构
        app.GetGroupStr();//获取组织结构串
//        //加载组织结构之后，要延时5秒钟左右，等待与各服务模块取得连接
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        app.SetAlarm();//打开报警监听,加载组织结构后才能接收到报警信息
        app.run();
        app.OnLogout();
        app.OnDestroy();

    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}

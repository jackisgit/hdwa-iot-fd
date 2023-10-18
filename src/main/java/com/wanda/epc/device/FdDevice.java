package com.wanda.epc.device;


import com.alibaba.fastjson.JSONObject;
import com.wanda.epc.param.DeviceMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 孙率众
 */
@Slf4j
@Component
@EnableScheduling
public class FdDevice extends BaseDevice {

    /**
     * 发送报文分隔符
     */
    private final static String str = "00";
    /**
     * 重连频率，单位：秒
     */
    private static final Integer RECONNECT_SECONDS = 20;
    @Resource
    CommonDevice commonDevice;
    @Value("${tcp.serverIP}")
    private String serverHost;
    @Value("${tcp.port}")
    private Integer serverPort;
    @Resource
    private ClientChannelInitializer nettyClientHandlerInitializer;
    /**
     * 线程组，用于客户端对服务端的链接、数据读写
     */
    private EventLoopGroup eventGroup = new NioEventLoopGroup();
    /**
     * Netty Client Channel
     */
    private volatile Channel channel;

    @Scheduled(cron = "0/10 * * * * ?")
    public void sendHeartMessage() {
        if (!channel.isOpen()) {
            log.error("失去连接，暂时不发送心跳");
        } else {
            //发送心跳包
            PackageDto packageDto = PackageDto.builder().packageNo(Constant.KEEP_ALIVE_NO).cmdType(Constant.KEEP_ALIVE).build();
            String heartStr = StringUtil.str2HexStr(JSONObject.toJSONString(packageDto)) + str;
            log.info("发送心跳包：{},{}", JSONObject.toJSONString(packageDto), heartStr);
            channel.writeAndFlush(heartStr);
        }
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        commonDevice.sendMessage(dm);
    }

    @Override
    public boolean processData() {
        return false;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) {
        commonDevice.feedback(message);
        DeviceMessage deviceMessage = controlParamMap.get(meter + "-" + funcid);
        if (deviceMessage != null && deviceMessage.getOutParamId() != null && deviceMessage.getOutParamId().endsWith(Constant.DEPLOY_WITH_DRAW_ALARM_SET)) {
            String outParamId = deviceMessage.getOutParamId();
            if (redisUtil.hasKey(outParamId)) {
                return;
            }
            redisUtil.set(outParamId, "0", 5);
            try {
                String[] split = outParamId.split("_");
                //分区
                String zone = split[0];
                String command;
                //1: 外出布防 2: 留守布防 3: 撤防 4: 旁路未准备防区
                if ("1.0".equals(value)) {
                    command = "1";
                } else {
                    command = "3";
                }
                PackageDto controlPackageDto = PackageDto.builder().packageNo(Constant.ALARMCLIENT_CONTROL_NO)
                        .cmdType(Constant.ALARMCLIENT_CONTROL).data1(zone).data2(command).build();
                PackageDto loginPackageDto = PackageDto.builder().packageNo(Constant.LOGIN_NO).cmdType(Constant.LOGIN)
                        .data1(Constant.LOGIN_USER).data2(Constant.LOGIN_PWD).build();
                String loginPackageStr = StringUtil.str2HexStr(JSONObject.toJSONString(loginPackageDto)) + str;
                String controlPackageStr = StringUtil.str2HexStr(JSONObject.toJSONString(controlPackageDto)) + str;
                log.info("发送登录,指令为：{}，{}", JSONObject.toJSONString(loginPackageDto), loginPackageStr);
                channel.writeAndFlush(loginPackageStr);
                log.info("发送控制,指令为：{}，{}", JSONObject.toJSONString(controlPackageDto), controlPackageStr);
                channel.writeAndFlush(controlPackageStr);
            } catch (Exception e) {
                log.error("防盗报警控制命令下发失败", e);
            }
        }
    }

    @Override
    public boolean processData(String... obj) {
        return false;
    }

    /**
     * 启动 Netty Client
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // 创建 Bootstrap 对象，用于 Netty Client 启动
        Bootstrap bootstrap = new Bootstrap();
        // 设置 Bootstrap 的各种属性。
        bootstrap.group(eventGroup) // 设置一个 EventLoopGroup 对象
                .channel(NioSocketChannel.class)  // 指定 Channel 为客户端 NioSocketChannel
                .remoteAddress(serverHost, serverPort) // 指定链接服务器的地址
                .option(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .option(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                .handler(nettyClientHandlerInitializer);
        // 链接服务器，并异步等待成功，即启动客户端
        bootstrap.connect().addListener((ChannelFutureListener) future -> {
            // 连接失败
            if (!future.isSuccess()) {
                log.error("[start][Netty Client 连接服务器({}:{}) 失败]", serverHost, serverPort);
                reconnect();
                return;
            }
            // 连接成功
            channel = future.channel();
            PackageDto packageDto = PackageDto.builder().packageNo(Constant.RE_CONNECT_NO).cmdType(Constant.GET_ALLDOT_STATUS).build();
            String reconnectStr = StringUtil.str2HexStr(JSONObject.toJSONString(packageDto)) + str;
            log.info("发送重连包：{},{}", JSONObject.toJSONString(packageDto), reconnectStr);
            channel.writeAndFlush(reconnectStr);
            log.info("[start][Netty Client 连接服务器({}:{}) 成功]", serverHost, serverPort);
        });
    }

    public void reconnect() {
        eventGroup.schedule(() -> {
            log.info("[reconnect][开始重连]");
            try {
                start();
            } catch (InterruptedException e) {
                log.error("[reconnect][重连失败]", e);
            }
        }, RECONNECT_SECONDS, TimeUnit.SECONDS);
        log.info("[reconnect][{} 秒后将发起重连]", RECONNECT_SECONDS);
    }


}

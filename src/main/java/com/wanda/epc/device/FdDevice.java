package com.wanda.epc.device;


import com.alibaba.fastjson.JSONObject;
import com.wanda.epc.param.DeviceMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 孙率众
 */
@Slf4j
@Component
@EnableScheduling
public class FdDevice extends BaseDevice implements ApplicationRunner {

    @Autowired
    CommonDevice commonDevice;

    @Value("${tcp.serverIP}")
    private String host;

    @Value("${tcp.port}")
    private Integer port;

    private SocketChannel socketChannel;

    private EventLoopGroup group;

    /**
     * 发送报文分隔符
     */
    private final static String str = "00";

    @Scheduled(cron = "0/10 * * * * ?")
    public void sendHeartMessage() {
        //发送心跳包
        PackageDto packageDto = PackageDto.builder().packageNo(Constant.KEEP_ALIVE_NO).cmdType(Constant.KEEP_ALIVE).build();
        String heartStr = StringUtil.str2HexStr(JSONObject.toJSONString(packageDto)) + str;
        log.info("发送心跳包：{},{}", JSONObject.toJSONString(packageDto), heartStr);
        socketChannel.writeAndFlush(heartStr);
    }

    @Override
    public void sendMessage(DeviceMessage dm) {
        if (dm != null) {
            commonDevice.sendMessage(dm);
        }
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
                socketChannel.writeAndFlush(loginPackageStr);
                log.info("发送控制,指令为：{}，{}", JSONObject.toJSONString(controlPackageDto), controlPackageStr);
                socketChannel.writeAndFlush(controlPackageStr);
            } catch (Exception e) {
                log.info("防盗报警控制命令下发失败：" + e.getMessage());
            }
        }
    }

    @Override
    public boolean processData(String... obj) {
        return false;
    }

    /**
     * 项目初始化时执行
     *
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        //配置服务端的NIO线程组
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 绑定线程池
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());
            ChannelFuture f = b.connect(host, port);
            f.addListener(future -> {
                if (future.isSuccess()) {
                    socketChannel = (SocketChannel) f.channel();
                    log.info("与服务器{}:{}连接建立成功", host, port);
                } else {
                    log.error("与服务器{}:{}连接建立失败", host, port);
                }
            });
            // 关闭服务器通道
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("与服务器{}:{}连接出现异常", host, port);
        } finally {
            group.shutdownGracefully();

        }
    }

    static void connect() {
        b.connect().addListener(future -> {
            if (future.cause() != null) {
                handler.startTime = -1;
                handler.println("建立连接失败: " + future.cause());
            }
        });
    }
}

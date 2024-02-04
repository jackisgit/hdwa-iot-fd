package com.wanda.epc.device;

import com.wanda.epc.common.RedisUtil;
import com.wanda.epc.param.DeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @program: iot_epc
 * @description: 变压器采集
 * @author: 孙率众
 * @create: 2022-11-08 17:07
 **/


@Slf4j
@Component
public class CommonTask {

    @Autowired
    CommonDevice commonDevice;

    @Resource
    RedisUtil redisUtil;

    @Scheduled(cron = "${epc.cron:0/60 * * * * ?}")
    public boolean processData() throws Exception {
        Iterator<Map.Entry<String, List<DeviceMessage>>> iterator = BaseDevice.deviceParamListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<DeviceMessage>> next = iterator.next();
            //如果点位是以是否报警结束则设置成未报警
            if (next.getKey().endsWith(BoschSecurityDeviceSdk.IS_ALARM)) {
                for (DeviceMessage deviceMessage : next.getValue()) {
                    deviceMessage.setValue("0");
                    commonDevice.sendMessage(deviceMessage);
                }
            }
            //如果点位是以在线状态结束则判断redis中的时间点位是否存在，如果不存则设置未离线
            if (next.getKey().endsWith(BoschSecurityDeviceSdk.ONLINE_STATUS)) {
                Object obj = redisUtil.get(next.getKey());
                if (!ObjectUtils.isEmpty(obj)) {
                    continue;
                }
                for (DeviceMessage deviceMessage : next.getValue()) {
                    deviceMessage.setValue("0");
                    commonDevice.sendMessage(deviceMessage);
                }
            }
        }

        return true;
    }

}


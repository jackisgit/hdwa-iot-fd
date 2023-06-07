package com.wanda.epc.vo;

import lombok.Data;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 入侵报警主机通道列表vo
 * @date 2023/5/9 14:58:30
 */
@Data
public class HikvisionIasChannelVo {
    //资源类型，subSys：入侵报警子系统通道，defence：入侵报警防区通道
    private String resourceType;

    //资源唯一编码
    private String indexCode;

    //资源名称
    private String name;

    //父级资源编号
    private String parentIndexCode;

    //通道号
    private String channelNo;

    //通道类型，subSys：入侵报警子系统通道,defence:入侵报警- 防区通道
    private String channelType;

    //所属区域
    private String regionIndexCode;

    //所属区域路径,以@符号分割，包含本节点
    private String regionPath;

    //显示顺序
    private String sort;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //描述
    private String description;
}

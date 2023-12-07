package com.wanda.epc.vo;

import lombok.Data;

import java.util.List;

@Data
public class UnitNodes {

    List<com.wanda.epc.vo.Channel> Channel;
    String index;
    String channelnum;
    String streamType;
    String subType;
    String type;
    String zeroChnEncode;

}

package com.wanda.epc.vo;

import lombok.Data;

@Data
public class Channel {

    String id;
    String name;
    String desc;
    String status;
    String channelType;
    String channelSN;
    String rights;
    String cmsXmlExt;
    String cameraType;
    String CtrlId;
    String latitude;
    String longitude;
    String viewDomain;
    String cameraFunctions;
    String multicastIp;
    String multicastPort;
    String NvrChnlIp;
    String channelRemoteType;
    String subMulticastIp;
    String subMulticastPort;
    public Channel(String id) {
        this.id = id;
    }
}

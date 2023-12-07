package com.wanda.epc.vo;

import lombok.Data;

import java.util.List;

@Data
public class Device {

    List<Channel> channels;
    String id;
    String type;
    String name;
    String manufacturer;
    String model;
    String ip;
    String port;
    String user;
    String password;
    String desc;
    String status;
    String logintype;
    String registDeviceCode;
    String proxyport;
    String unitnum;
    String deviceCN;
    String deviceSN;
    String deviceIp;
    String devicePort;
    String devMaintainer;
    String devMaintainerPh;
    String deviceLocation;
    String deviceLocPliceStation;
    String baudRate;
    String comCode;
    String VideoType;
    String shopName;
    String address;
    String firstOwner;
    String firstPosition;
    String firstPhone;
    String firstTel;
    String serviceType;
    String ownerGroup;
    String belong;
    String role;
    String callNumber;
    String rights;

    public Device(String id, String type, String deviceIp) {
        this.id = id;
        this.type = type;
        this.deviceIp = deviceIp;

    }

}

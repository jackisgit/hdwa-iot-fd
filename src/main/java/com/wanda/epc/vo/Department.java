package com.wanda.epc.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Department {

    List<Device> devices;
    List<Channel> channels;
    List<Department> children;
    private String coding;
    private String name;
    private String modifytime;
    private String sn;
    private String memo;
    private String deptype;
    private String depsort;
    private String chargebooth;
    private String OrgNum;

    public Department(String coding, String name) {
        this.coding = coding;
        this.name = name;
        this.devices = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

//    public void addDevice(String deviceId) {
//        devices.add(new Device(deviceId));
//    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void addChannel(String channelId) {
        channels.add(new Channel(channelId));
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

    public void addChild(Department childDept) {
        children.add(childDept);
    }
}

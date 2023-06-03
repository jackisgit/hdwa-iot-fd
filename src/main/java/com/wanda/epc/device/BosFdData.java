package com.wanda.epc.device;

public class BosFdData {
    private String ipAddr;

    private String command;

    private String desc;

    private String sPara;

    private int type;

    public static final int TYPE_IP74ooXI = 0;

    public static final int TYPE_DS7240V2 = 1;

    public BosFdData() {}

    public BosFdData(String ipAddr, String command, String sPara) {
        this.ipAddr = ipAddr;
        this.command = command;
        this.sPara = sPara;
        this.type = 0;
    }

    public BosFdData(String ipAddr, String command, String sPara, int type) {
        this.ipAddr = ipAddr;
        this.command = command;
        this.sPara = sPara;
        this.type = type;
    }

    public BosFdData(String ipAddr, String command, String sPara, int type, String desc) {
        this.ipAddr = ipAddr;
        this.command = command;
        this.sPara = sPara;
        this.type = type;
        this.desc = desc;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getsPara() {
        return this.sPara;
    }

    public void setsPara(String sPara) {
        this.sPara = sPara;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

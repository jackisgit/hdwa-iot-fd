package com.wanda.epc.DTO;

import java.util.List;

public class Value {
    private String subsystemId;
    private String subSystemName;
    private int status;
    private List<defenceAreaDTO> defenceAreaList;

    public String getSubsystemId() {
        return this.subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubSystemName() {
        return this.subSystemName;
    }

    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<defenceAreaDTO> getDefenceAreaList() {
        return this.defenceAreaList;
    }

    public void setDefenceAreaList(List<defenceAreaDTO> defenceAreaList) {
        this.defenceAreaList = defenceAreaList;
    }
}



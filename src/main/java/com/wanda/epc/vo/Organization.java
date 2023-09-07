package com.wanda.epc.vo;

import lombok.Data;

@Data
public class Organization {

    Department department;
    Device device;

    public Organization(Department department, Device device) {
        this.department = department;
        this.device = device;
    }
}

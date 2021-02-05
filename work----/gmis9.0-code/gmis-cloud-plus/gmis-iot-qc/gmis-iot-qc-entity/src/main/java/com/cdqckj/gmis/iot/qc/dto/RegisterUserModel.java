package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserModel implements Serializable {
    public String domainId;
    public String deviceType;
    public DeviceData deviceData;
}

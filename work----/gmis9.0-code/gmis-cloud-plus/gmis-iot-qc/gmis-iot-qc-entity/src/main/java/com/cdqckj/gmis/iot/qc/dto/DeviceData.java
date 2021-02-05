package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DeviceData implements Serializable {
    public String deviceId;
    public String factoryId;
    public String versionNo;
    public String baseType;
    public String controlMode;
    public String customerCode;
    public String customerName;
    public String telephone;
    public Integer customerType;
    public BigDecimal baseNumber;
    public Integer intakeDrection;
    public String userCode;
    public Long installationTime;
    public Long ignitionTime;
    public String address;
    public GISModel GIS;
}

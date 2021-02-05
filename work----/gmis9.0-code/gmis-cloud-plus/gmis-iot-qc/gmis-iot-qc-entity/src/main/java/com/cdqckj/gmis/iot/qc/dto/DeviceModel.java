package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceModel implements Serializable {
    private String domainId;
    private String deviceType;
    private List<DeviceDetailModel> data  = new ArrayList<>();
}

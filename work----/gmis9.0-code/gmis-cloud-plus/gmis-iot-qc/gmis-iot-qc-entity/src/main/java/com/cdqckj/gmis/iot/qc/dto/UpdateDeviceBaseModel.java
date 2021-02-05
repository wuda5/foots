package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateDeviceBaseModel implements Serializable {

    /**
     * 唯一标识
     */
    private String domainId;

    /**
     * 档案id
     */
    private String archiveId;

    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备数据
     */
    private UpdateDeviceModel deviceData;
}

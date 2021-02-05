package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetUpParamsterReading implements Serializable {
    /**
     * 开始时间
     */
    public String startDate;
    /**
     * 补抄天数
     */
    public Integer days;
}

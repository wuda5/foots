package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ParamsVO  implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号",required = true)
    private String gasMeterNumber;
    /**
     * 气表厂家编码
     */
    @ApiModelProperty(value = "气表厂家编码",required = true)
    private String gasMeterFactoryCode;
    /**
     * 厂家唯一标识
     */
    @ApiModelProperty(value = "唯一标识,不用传",required = false)
    private String domainId;
    /**
     * 开始日期
     */
    private LocalDateTime startTime;
    /**
     * 补抄天数
      */
    private Integer days;
}

package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DayDataParamsVO implements Serializable {
    @ApiModelProperty(value = "分页参数当前页号",required = true)
    public Integer pageNo;
    @ApiModelProperty(value = "分页参数当前页记录数",required = true)
    public Integer pageSize;
    @ApiModelProperty(value = "气表编号")
    private String gasMeterNumber;
    @ApiModelProperty(value = "客户姓名")
    private String customerName;
    @ApiModelProperty(value = "表类型(0-表端,1-中心计费",required = true)
    private Integer meterType;
    @ApiModelProperty(value = "执行时间")
    private List<LocalDateTime> uploadTime;
    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;
}

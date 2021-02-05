package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandVO implements Serializable {
    @ApiModelProperty(value = "分页参数当前页号")
    public Integer pageNo;
    @ApiModelProperty(value = "分页参数当前页记录数")
    public Integer pageSize;
    @ApiModelProperty(value = "流水号")
    private String transactionNo;
    @ApiModelProperty(value = "表编号")
    private String meterNo;
    @ApiModelProperty(value = "指令类型")
    private String commandType;
    @ApiModelProperty(value = "执行结果")
    private Integer executeResult;
    @ApiModelProperty(value = "指令状态")
    private Integer commandStatus;
    @ApiModelProperty(value = "创建见时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "执行时间")
    private List<LocalDate> executeTime;
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;
    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;
    /**
     * 查询使用 表具编号列表
     */
    private List<String> gasMeterCodeList;
}

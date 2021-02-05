package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommandEchoVO implements Serializable {
    @ApiModelProperty(value = "指令id")
    private Long commandId;
    @ApiModelProperty(value = "流水号")
    private String transactionNo;
    @ApiModelProperty(value = "表钢号")
    private String meterNo;
    @ApiModelProperty(value = "指令类型")
    private String commandType;
    @ApiModelProperty(value = "指令阶段")
    private Integer commandStage;
    @ApiModelProperty(value = "执行结果(0-未执行，1-执行成功，-1-执行失败)")
    private Integer executeResult;
    @ApiModelProperty(value = "指令执行状态")
    private Integer commandStatus;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "执行时间")
    private LocalDateTime executeTime;
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;
    @ApiModelProperty(value = "缴费编号")
    private String customerChargeNo;
    @ApiModelProperty(value = "异常描述")
    private String errorDesc;
}

package com.cdqckj.gmis.bizcenter.temp.counter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hc
 * @Date 2020/11/17
 */
@Data
public class UseGasTypeChangeVO {

    @ApiModelProperty("变更时间")
    private Date changeTime;
    @ApiModelProperty("变更前用气类型名")
    private String oldGasTypeName;
    @ApiModelProperty("变更后用气类型名")
    private String newGasTypeName;
    @ApiModelProperty("周期量控制")
    private Integer cycleSumControl;
    @ApiModelProperty("操作员")
    private String operator;
    @ApiModelProperty("营业厅")
    private String businessBallName;
}

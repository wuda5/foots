package com.cdqckj.gmis.charges.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/12/29 09:53
 * @remark: 柜台人员统计
 */
@Data
@ApiModel(value = "StsCounterStaffVo", description = "柜台人员统计")
public class StsCounterStaffVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户的名字")
    private String name;

    @ApiModelProperty(value = "服务次数")
    private Integer serviceCount;

    @ApiModelProperty(value = "收费")
    private BigDecimal changeMoney;

    @ApiModelProperty(value = "发票的统计")
    private Integer invoiceCount;

}

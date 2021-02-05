package com.cdqckj.gmis.entity.vo;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户缴费详情VO
 * @auther hc
 * @date 2020/10/19
 */
@Data
@ApiModel(value = "CusFeesInfoVO",description = "客户缴费详情VO")
public class CusFeesInfoVO extends CusDetailVO{

    @ApiModelProperty("气表编号")
    private String gasCode;
    @ApiModelProperty(value = "安装/用气地址")
    private String gasMeterAddress;
    @ApiModelProperty("账户余额")
    private BigDecimal accountBalance;
    @ApiModelProperty("欠费总额")
    private BigDecimal arrearage;
    @ApiModelProperty("表身号")
    private String gasMeterNumber;
    @ApiModelProperty("缴费编号")
    private String customerChargeNo;

    @ApiModelProperty("收费项")
    private List<ChargeItemRecord> itemList;
}



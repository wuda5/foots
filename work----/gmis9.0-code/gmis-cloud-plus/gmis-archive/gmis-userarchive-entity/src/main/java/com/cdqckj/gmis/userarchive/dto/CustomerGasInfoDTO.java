package com.cdqckj.gmis.userarchive.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class CustomerGasInfoDTO {

    @ApiModelProperty("客户code")
    private String customerCode;
    @ApiModelProperty("客户姓名")
    private String customerName;
    @ApiModelProperty("电话号码")
    private String telphone;
    @ApiModelProperty("气表编号")
    private String gasCode;
    @ApiModelProperty("客户缴费编号")
    private String customerChargeNo;
    @ApiModelProperty("气表型号id")
    private String gasMeterModelId;
    @ApiModelProperty("气表型号名")
    private String gasMeterModelName;
    @ApiModelProperty("气表类型code")
    private String gasMeterTypeCode;
    @ApiModelProperty("气表类型名称")
    private String gasMeterTypeName;
    @ApiModelProperty("安装地址")
    private String moreGasMeterAddress;
    @ApiModelProperty("用气类型id")
    private String useGasTypeId;

    @ApiModelProperty("表身号")
    private String gasMeterNumber;

    @ApiModelProperty("调压箱号")
    private String nodeNumber;
    @ApiModelProperty("开户时间")
    private Date openAccountTime;
    @ApiModelProperty("累计用气量")
    private BigDecimal  totalUseGas;


    @ApiModelProperty(value = "订单来源名称:IC_RECHARGE(IC卡充值（IC卡表),READMETER_PAY(抄表缴费(普表)),REMOTE_READMETER(物联网中心计费后付费表(远程抄表)),REMOTE_RECHARGE(物联网表端计费表),CENTER_RECHARGE(物联网中心计费预付费表)")
    private String orderSourceName;

    @ApiModelProperty(value = "金额标志：GAS(气量),MONEY(金额)")
    private String amountMark;
}

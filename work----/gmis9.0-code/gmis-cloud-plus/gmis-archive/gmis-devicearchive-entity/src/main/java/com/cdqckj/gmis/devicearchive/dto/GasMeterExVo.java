package com.cdqckj.gmis.devicearchive.dto;

import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 气表信息附加类
 * @author  hc\
 */

@Data
public class GasMeterExVo extends GasMeter {

    @ApiModelProperty("用气总量")
    private BigDecimal totalUseGas;

    @ApiModelProperty("客户code")
    private String customerCode;

    @ApiModelProperty("客户名字")
    private String customerName;

    @ApiModelProperty("订单来源名称")
    private String orderSourceName;

    @ApiModelProperty("金额标志")
    private String amountMark;
}

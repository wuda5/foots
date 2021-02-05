package com.cdqckj.gmis.statistics.vo;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/20 14:25
 * @remark: 请输入类说明
 */
@Data
@ApiModel(value = "GasFeiNowTypeVo", description = "用气类型统计")
public class FeiDayByPayTypeVo implements SeparateKey, Serializable {

    @ApiModelProperty(value = "收费方式编码")
    private String chargeMethodCode;

    @ApiModelProperty(value = "收费方式名称")
    private String chargeMethodName;

    @ApiModelProperty(value = "收费")
    private BigDecimal chargeFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "退费")
    private BigDecimal refundFee = BigDecimal.ZERO;

    @Override
    public String indexKey() {
        return chargeMethodCode;
    }
}

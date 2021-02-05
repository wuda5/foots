package com.cdqckj.gmis.calculate.vo;

import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "CalculateVO", description = "燃气费计算VO")
public class CalculateVO implements Serializable {
    /**
     * 本次结算历史气量
     */
    @ApiModelProperty(value = "本次结算历史气量")
    private BigDecimal historyGas;
    /**
     * 本次结算当前气量
     */
    @ApiModelProperty(value = "本次结算当前气量")
    private BigDecimal currentGas;
    /**
     * 本次结算价格方案
     */
    @ApiModelProperty(value = "本次结算价格方案")
    private PriceScheme priceScheme;

    /**
     * 是否清零
     */
    private Boolean isClear;

}

package com.cdqckj.gmis.calculate.domain.cal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/9/25 16:13
 * @remark: 价格的梯度
 */
@Data
public class GasGradient {
    /**
     * 气量
     */
    @ApiModelProperty(value = "气量")
    private BigDecimal gas;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
}

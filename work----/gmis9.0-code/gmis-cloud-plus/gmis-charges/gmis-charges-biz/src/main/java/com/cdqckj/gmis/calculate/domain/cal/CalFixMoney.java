package com.cdqckj.gmis.calculate.domain.cal;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/9/27 13:29
 * @remark: 计算固定价格的金额
 */
public class CalFixMoney implements GasMeterCalMoney {

    @ApiModelProperty(value = "固定价格")
    private BigDecimal fixPrice;

    public CalFixMoney(BigDecimal fixPrice) {
        this.fixPrice = fixPrice;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/27 13:32
     * @remark C*p
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {
        return cGas.multiply(fixPrice);
    }
}

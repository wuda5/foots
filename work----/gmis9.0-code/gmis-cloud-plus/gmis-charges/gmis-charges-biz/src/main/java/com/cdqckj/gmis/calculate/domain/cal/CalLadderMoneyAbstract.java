package com.cdqckj.gmis.calculate.domain.cal;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 16:50
 * @remark: 计算的抽象类
 */
public abstract class CalLadderMoneyAbstract implements GasMeterCalMoney {

    @ApiModelProperty(value = "当前的参数")
    List<GasGradient> gasGradientList;

    public CalLadderMoneyAbstract(List<GasGradient> gasGradientList) {
        this.gasGradientList = gasGradientList;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 17:04
     * @remark 获取g的值
     */
    protected BigDecimal g(int i){
        return gasGradientList.get(--i).getGas();
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 17:05
     * @remark 获取p的值
     */
    protected BigDecimal p(int i){
        return gasGradientList.get(--i).getPrice();
    }
}

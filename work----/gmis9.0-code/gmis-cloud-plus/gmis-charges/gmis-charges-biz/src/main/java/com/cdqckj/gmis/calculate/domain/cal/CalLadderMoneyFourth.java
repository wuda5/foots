package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 15:48
 * @remark: 请输入类说明
 */
public class CalLadderMoneyFourth extends CalLadderMoneyAbstract{

    public CalLadderMoneyFourth(List<GasGradient> gasGradientList) {
        super(gasGradientList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 15:41
     * @remark (H+C-G3)*P4 + (G3-G2)*P3 + (G2-G1)*p2 + (G1-H)*P1
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {

        BigDecimal money1 = hGas.add(cGas).subtract(g(3)).multiply(p(4));
        BigDecimal money2 = g(3).subtract(g(2)).multiply(p(3));
        BigDecimal money3 = g(2).subtract(g(1)).multiply(p(2));
        BigDecimal money4 = g(1).subtract(hGas).multiply(p(1));
        return money1.add(money2).add(money3).add(money4);
    }
}

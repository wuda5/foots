package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 15:33
 * @remark: 请输入类说明
 */
public class CalLadderMoneyThird extends CalLadderMoneyAbstract {

    public CalLadderMoneyThird(List<GasGradient> gasGradientList) {
        super(gasGradientList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 15:41
     * @remark (H+C-G2)*P3 + (G2-G1)*P2 + (G1-H)*p1
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {

        BigDecimal money1 = hGas.add(cGas).subtract(g(2)).multiply(p(3));
        BigDecimal money2 = g(2).subtract(g(1)).multiply(p(2));
        BigDecimal money3 = g(1).subtract(hGas).multiply(p(1));
        return money1.add(money2).add(money3);
    }
}

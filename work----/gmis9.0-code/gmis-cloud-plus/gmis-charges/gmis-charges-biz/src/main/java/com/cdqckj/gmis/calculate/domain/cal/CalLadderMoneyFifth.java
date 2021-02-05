package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 15:55
 * @remark: 请输入类说明
 */
public class CalLadderMoneyFifth extends CalLadderMoneyAbstract {

    public CalLadderMoneyFifth(List<GasGradient> gasGradientList) {
        super(gasGradientList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 15:41
     * @remark (H+C-G4)*P5 + (G4-G3)*P4 + (G3-G2)*p3 + (G2-G1)*P2 + (G1-H)*P1
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {

        BigDecimal money1 = hGas.add(cGas).subtract(g(4)).multiply(p(5));
        BigDecimal money2 = g(4).subtract(g(3)).multiply(p(4));
        BigDecimal money3 = g(3).subtract(g(2)).multiply(p(3));
        BigDecimal money4 = g(2).subtract(g(1)).multiply(p(2));
        BigDecimal money5 = g(1).subtract(hGas).multiply(p(1));
        return money1.add(money2).add(money3).add(money4).add(money5);
    }
}

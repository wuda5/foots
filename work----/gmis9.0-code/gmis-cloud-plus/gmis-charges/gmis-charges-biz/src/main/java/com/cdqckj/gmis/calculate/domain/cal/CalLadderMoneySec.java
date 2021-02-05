package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 14:38
 * @remark: 请输入类说明
 */
public class CalLadderMoneySec extends CalLadderMoneyAbstract  {

    public CalLadderMoneySec(List<GasGradient> gasGradientList) {
        super(gasGradientList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 15:34
     * @remark (H+C-G1)*P2 + (G1-H)*P1
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {

        BigDecimal money1 = hGas.add(cGas).subtract(g(1)).multiply(p(2));
        BigDecimal money2 = g(1).subtract(hGas).multiply(p(2));
        return money1.add(money2);
    }
}

package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/25 13:45
 * @remark: 计算价格
 */
public class CalLadderMoneyFirst extends CalLadderMoneyAbstract {

    public CalLadderMoneyFirst(List<GasGradient> gasGradientList) {
        super(gasGradientList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/25 15:33
     * @remark C*P
     */
    @Override
    public BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas) {

        BigDecimal p1 = p(1);
        return cGas.multiply(p1);
    }
}

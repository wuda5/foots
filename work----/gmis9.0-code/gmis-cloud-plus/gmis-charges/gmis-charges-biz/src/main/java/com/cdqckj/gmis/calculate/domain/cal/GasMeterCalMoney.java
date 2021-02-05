package com.cdqckj.gmis.calculate.domain.cal;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/9/25 13:47
 * @remark: 计算燃气表的费用
 */
public interface GasMeterCalMoney {

    /**
     * @auth lijianguo
     * @date 2020/9/25 13:47
     * @remark 计算用户用气的价格 一种固定价格 和 六种阶梯价格
     */
    BigDecimal calMoney(BigDecimal hGas, BigDecimal cGas);

}

package com.cdqckj.gmis.calculate.domain.util;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/9/28 11:23
 * @remark: 数值计算的工具类
 */
public class BigDecimalUtil {

    /**
     * @auth lijianguo
     * @date 2020/9/28 11:23
     * @remark 初始化为0
     */
    public static BigDecimal intZero(){
        return BigDecimal.ZERO;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/28 11:26
     * @remark 如果为空就初始化为0
     */
    public static BigDecimal nullInitZero(BigDecimal value){
        if (value == null) {
            return intZero();
        }else {
            return value;
        }
    }
}

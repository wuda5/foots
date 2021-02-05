package com.cdqckj.gmis.operateparam.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/12/04 14:52
 * @remark: 这里把数据取整
 */
public class CalRoundUtil {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 14:56
    * @remark 取整数
    */
    public static BigDecimal bigDecimalRound(BigDecimal bigDecimal){
        // 1向上取整 0向下取整
        return bigDecimalRound(bigDecimal, 4);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/4 16:19
    * @remark 取整数
    */
    public static BigDecimal bigDecimalRound(BigDecimal bigDecimal, Integer num){
        // 1向上取整 0向下取整
        if (GmisSysSettingUtil.getRounding() == 0){
            return bigDecimal.setScale(num, BigDecimal.ROUND_DOWN);
        }else {
            return bigDecimal.setScale(num, BigDecimal.ROUND_UP);
        }
    }

}

package com.cdqckj.gmis.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: lijianguo
 * @time: 2020/9/28 11:23
 * @remark: 数值计算的工具类
 */
public class BigDecimalUtils {

    /**
     * 判断num1是否小于num2
     *
     * @param num1
     * @param num2
     * @return num1小于num2返回true
     */
    public static   boolean lessThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == -1;
    }


    /**
     * 判断num1是否小于等于num2
     *
     * @param num1
     * @param num2
     * @return num1小于或者等于num2返回true
     */
    public static boolean lessEqual(BigDecimal num1, BigDecimal num2) {
        return (num1.compareTo(num2) == -1) || (num1.compareTo(num2) == 0);
    }

    /**
     * 判断num1是否大于num2
     *
     * @param num1
     * @param num2
     * @return num1大于num2返回true
     */
    public static boolean greaterThan(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 1;
    }

    /**
     * 判断num1是否大于等于num2
     *
     * @param num1
     * @param num2
     * @return num1大于或者等于num2返回true
     */
    public static boolean greaterEqual(BigDecimal num1, BigDecimal num2) {
        return (num1.compareTo(num2) == 1) || (num1.compareTo(num2) == 0);
    }

    /**
     * 判断num1是否等于num2
     *
     * @param num1
     * @param num2
     * @return num1等于num2返回true
     */
    public static boolean equal(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) == 0;

    }
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

    /**
    * @Author: lijiangguo
    * @Date: 2021/2/1 15:25
    * @remark 保留两位小数
    */
    public static BigDecimal left2(BigDecimal value){
        if (value == null){
            value = BigDecimal.ZERO;
        }
        BigDecimal left2 = value.setScale(2, RoundingMode.HALF_UP);
        return left2;
    }
}

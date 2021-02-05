package com.cdqckj.gmis.charges.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ChargeUtils {
    /**
     * 判断是否是负数
     * @param val
     */
    public static boolean isNegate(BigDecimal val){
        BigDecimal zero=new BigDecimal("0.00");
        if(val!=null && val.compareTo(zero)<0) return true;
        return false;
    }

    /**
     * 校验输入数据BigDecimal字段是否有为负值
     * @param obj
     */
    public static boolean  isHasNagate(Object obj){
        if(obj==null) return true;
        Field[] fields=obj.getClass().getDeclaredFields();
        Method m;
        BigDecimal zero = new BigDecimal("0.00");
        try {
            Class type;
            for (Field field : fields) {
                type = field.getType();
                field.setAccessible(true);
                if (type == BigDecimal.class) {
                    if (field.get(obj) != null ) {
                        BigDecimal temp=(BigDecimal) field.get(obj);
                        if(temp.compareTo(zero)<0)
                            return true;
                    }
                }
            }

        }catch (Exception e){}
        return false;
    }

    /**
     * 保留两位小数
     * @param obj
     */
    public static void  decimalFormat2D(Object obj){
        if(obj==null) return ;
        Field[] fields=obj.getClass().getDeclaredFields();
        BigDecimal zero = new BigDecimal("0.00");
        try {
            Class type;
            for (Field field : fields) {
                type = field.getType();
                field.setAccessible(true);
                if (type == BigDecimal.class) {
                    if (field.get(obj) != null ) {
                        BigDecimal temp=(BigDecimal) field.get(obj);
                        temp=temp.setScale(2,BigDecimal.ROUND_HALF_UP);
                        field.set(obj,temp);
                    }else{
                        field.set(obj,zero);
                    }
                }
            }

        }catch (Exception e){}
    }

    /**
     * 将空字段统一转0，便于计算
     * @param obj
     */
    public static void  setNullFieldAsZero(Object obj){
        if(obj==null){
            return ;
        }
        Field[] fields=obj.getClass().getDeclaredFields();
        Method m;
        BigDecimal zero = new BigDecimal("0.00");
        try {
            Class type ;
            for (Field field : fields) {
                type = field.getType();
                field.setAccessible(true);
                if (type == BigDecimal.class) {
                    if (field.get(obj) == null ) {
                        field.set(obj,zero);
                    }
                }
            }

        }catch (Exception e){e.printStackTrace();}

    }
}

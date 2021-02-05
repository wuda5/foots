package com.cdqckj.gmis.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.log4j.Log4j2;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author: lijianguo
 * @time: 2020/9/28 8:50
 * @remark: 反射的工具类
 */
@Log4j2
public class ReflectionUtil<T> {

    /**
     * @auth lijianguo
     * @date 2020/9/28 8:51
     * @remark 获取属性的值
     */
    public static <T> T getFieldValueByFieldName(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (T)field.get(object);
        } catch (Exception e) {
            log.error("获取错误的值是 {} {}",fieldName, object);
            log.error(e);
            throw new RuntimeException("获取值错误");
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/28 8:51
     * @remark 设置属性的值
     */
    public static void setFieldValueByFieldName(Object object, String fieldName, Object value) {
        try {
            // 获取obj类的字节文件对象
            Class c = object.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(object, value);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("等一下替换，赋值错误");
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 13:56
     * @remark 设置值
     */
    public static void setFieldValueByFieldName(Object object, Class c, String fieldName, Object value) {
        try {
            // 给变量赋值
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            if (value == null){
                f.set(object, null);
                return;
            }
            Class type = f.getType();
            String str = (String)value;
            if (type.isEnum()){
                value = Enum.valueOf(type, str);
            }else {
                switch (type.getName()) {
                    case "java.time.LocalDateTime":
                        value = castToLocalDateTime(str);
                        break;
                    case "java.time.LocalDate":
                        value = castToLocalDateTime(str).toLocalDate();
                        break;
                    case "java.util.Date":
                        value = Date.from(castToLocalDateTime(str).atZone(ZoneId.systemDefault()).toInstant());
                        break;
                    case "java.math.BigDecimal":
                        Long longValue = NumberUtil.parseLong(str);
                        value = new BigDecimal(longValue);
                        break;
                    case "java.lang.Long":
                        value = NumberUtil.parseLong(str);
                        break;
                    case "java.lang.Integer":
                        value = NumberUtil.parseInt(str);
                        break;
                    case "java.lang.String":
                        value = str;
                        break;
                    case "java.lang.Object":
                        break;
                    default:
                        throw new RuntimeException("没有这个值的替换,请添加这个: " + type.getName());
                }
            }
            Object obj = type.cast(value);
            f.set(object, obj);
        } catch (Exception e) {
            log.error(e);
            log.error("covert is wrong {} {} {}", object, fieldName, value);
            throw new RuntimeException("等一下替换，赋值错误");
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/4 16:50
     * @remark String 转换为 LocalDateTime
     */
    private static LocalDateTime castToLocalDateTime(String strValue){

        LocalDateTime localDateTime = null;
        if (strValue.length() == 10){
            localDateTime = DateUtil.parseLocalDateTime(strValue,"yyyy-MM-dd");
        }else {
            localDateTime = DateUtil.parseLocalDateTime(strValue);
        }
        return localDateTime;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 10:25
     * @remark 生成对象
     */
    public static <T> T createClassObject(Class<T> c){
        return createClassObject(c.getName());
    }

    /**
     * @auth lijianguo
     * @date 2020/11/5 11:18
     * @remark 生成对象
     */
    public static <T> T createClassObject(String className){
        try {
            T t = (T) Class.forName(className).newInstance();
            return t;
        } catch (InstantiationException e) {
            log.error(e);
        } catch (IllegalAccessException e) {
            log.error(e);
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
        return null;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/11 17:14
    * @remark 设置值
    */
    public static void setFieldValue(Object object, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            log.error(e);
            throw new BizException(e.getMessage());
        }
    }

}

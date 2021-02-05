package com.cdqckj.gmis.base.utils;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cdqckj.gmis.log.annotation.ExcelSelf;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExcelLangUtils {
    private static final int VALUE_SIZE = 2;

    private ExcelLangUtils() {
    }

    public static Class chooseLang(Class<?> pojoClass, Integer lang)
            throws Exception {
        //获取实体类中所有字段
        Field[] fields = pojoClass.getDeclaredFields();

        for (Field field : fields) {
            // 获取字段上的注解
            Excel excel = field.getAnnotation(Excel.class);
            ExcelSelf excelSelf = field.getAnnotation(ExcelSelf.class);
            if (excelSelf != null) {
                Map<String, Object> memberValues = getAnnotationMap(excelSelf);
                Map<String, Object> values = getAnnotationMap(excel);
                // 获取属性值
                String excelValue = (String) memberValues.get("name");
                if (StringUtils.isNotBlank(excelValue)) {
                    //根据传入的语言标识，重新设置属性值
                    List<String> valueList = Arrays.asList(excelValue.split(","));
                    if (valueList.size() == VALUE_SIZE) {
                        values.put("name", valueList.get(lang-1));
                    }
                }
                /*Class clazz = (Class) memberValues.get("clazz");
                Boolean bool = clazz.isEnum();
                if(bool){
                    Object[] objects = clazz.getEnumConstants();
                    Method setDesc = clazz.getMethod("setDesc",String.class);
                    Method coinAddressAddress = clazz.getMethod("getDesc");
                    for (Object obj : objects) {
                        String str = coinAddressAddress.invoke(obj).toString();
                        if(str.contains(",")){
                            String s = coinAddressAddress.invoke(obj).toString().split(",")[lang-1];
                            setDesc.invoke(obj,s);
                        }
                    }
                }*/
            }
        }
        return pojoClass;
    }

    public static Map<String, Object> getAnnotationMap(Object anoExcel) throws Exception {
        // 获取代理处理器
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(anoExcel);
        // 获取私有 memberValues 属性
        Field f = invocationHandler.getClass().getDeclaredField("memberValues");
        f.setAccessible(true);
        // 获取实例的属性map
        return (Map<String, Object>) f.get(invocationHandler);
    }
}

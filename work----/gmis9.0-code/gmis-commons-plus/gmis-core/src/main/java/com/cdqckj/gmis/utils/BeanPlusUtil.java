package com.cdqckj.gmis.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean增强类工具类
 *
 * <p>
 * 把一个拥有对属性进行set和get方法的类，我们就可以称之为JavaBean。
 * </p>
 *
 * @author gmis
 * @since 3.1.2
 */
public class BeanPlusUtil extends BeanUtil {

    /**
     * 转换 list
     *
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<T> toBeanList(Collection<E> sourceList, Class<T> destinationClass) {
        if (sourceList == null || sourceList.isEmpty() || destinationClass == null) {
            return Collections.emptyList();
        }
        return sourceList.parallelStream()
                .filter(item -> item != null)
                .map((source) -> toBean(source, destinationClass))
                .collect(Collectors.toList());
    }

    /**
     * 判断一个类是否存在某个属性
     * @param field
     * @param obj
     * @return
     */
    public static Boolean isExistField(String field, Object obj) {
        if (obj == null || StringUtils.isEmpty(field)) {
            return null;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        return jsonObj.containsKey(field);
    }

    /**
     * 判断一个类是否存在某个属性并判断是否为空
     * @param field
     * @param obj
     * @return
     */
    public static Boolean isNullField(String field, Object obj) {
        if (obj == null || StringUtils.isEmpty(field)) {
            return null;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        if(jsonObj.containsKey(field)){
            return (jsonObj.get(field) == null || jsonObj.get(field) == "");
        }
        return false;
    }

//    /**
//     * 转化Page 对象
//     *
//     * @param page
//     * @param destinationClass
//     * @param <T>
//     * @param <E>
//     * @return
//     */
//    public static <T, E> IPage<T> toBeanPage(IPage<E> page, Class<T> destinationClass) {
//        if (page == null || destinationClass == null) {
//            return null;
//        }
//        IPage<T> newPage = new Page<>(page.getCurrent(), page.getSize());
//        newPage.setPages(page.getPages());
//        newPage.setTotal(page.getTotal());
//
//        List<E> list = page.getRecords();
//        if (CollUtil.isEmpty(list)) {
//            return newPage;
//        }
//
//        List<T> destinationList = toBeanList(list, destinationClass);
//
//        newPage.setRecords(destinationList);
//        return newPage;
//    }

}

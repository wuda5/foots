package com.cdqckj.gmis.statistics.domain.log.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: lijianguo
 * @time: 2020/11/3 9:51
 * @remark: 主要做一些关于实体的反射的工具类
 */
@Log4j2
public class EntityFieldUtil {

    @ApiModelProperty(value = "mysql数据的type")
    private static final List<String> EXCLUDE_FIELD_LIST = Arrays.asList("serialVersionUID");

    @ApiModelProperty(value = "这个field的class")
    private static final Cache<String,Class> FIELD_CLASS_G_CACHE = CacheBuilder.newBuilder().maximumSize(10000) .expireAfterWrite(1, TimeUnit.HOURS) .build();

    @ApiModelProperty(value = "缓存一个小时的field")
    private static final Cache<String,String> FIELD_NAME_CACHE = CacheBuilder.newBuilder().maximumSize(10000) .expireAfterWrite(1, TimeUnit.HOURS) .build();

    @ApiModelProperty(value = "类的字段")
    private static final Cache<String,Boolean> CLASS_FIELD_NAME_CACHE = CacheBuilder.newBuilder().maximumSize(10000) .expireAfterWrite(1, TimeUnit.DAYS) .build();

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:08
     * @remark 生成查找的sql
     */
    public static<T> String searchSameRecordSql(T t, List<String> excludeFieldList){
        excludeFieldList = camelCaseFieldList(excludeFieldList);
        StringBuilder sb = new StringBuilder();
        sb.append("WHERE 1 = 1");
        Field[] fieldList = t.getClass().getDeclaredFields();
        for (Field field: fieldList){
            String filedName = field.getName();
            if (excludeFieldList.contains(filedName) || EXCLUDE_FIELD_LIST.contains(filedName)){
                continue;
            }
            String dataBaseName = StrUtil.toSymbolCase(filedName,'_');
            Object value = ReflectionUtil.getFieldValueByFieldName(t, filedName);
            if (value == null){
                sb.append(" AND ").append(dataBaseName).append(" IS NULL");
            }else {
                if (field.getType().equals(String.class)){
                    sb.append(" AND ").append(dataBaseName).append(" = '").append(value).append("'");
                }else if(field.getType().equals(LocalDateTime.class)){
                    value = DateUtil.formatLocalDateTime((LocalDateTime) value);
                    sb.append(" AND ").append(dataBaseName).append(" = '").append(value).append("'");
                }else {
                    sb.append(" AND ").append(dataBaseName).append(" = ").append(value);
                }
            }
        }
        sb.append(" LIMIT 1");
        log.info("build sql is {}", sb.toString());
        return sb.toString();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/16 10:18
     * @remark 转换为驼峰命名
     */
    public static List<String> camelCaseFieldList(List<String> fieldNameList){

        List<String> resultList = new ArrayList<>(fieldNameList.size());
        for (String str: fieldNameList){
            String camelCase = fieldToCamelCase(str);
            resultList.add(camelCase);
        }
        return resultList;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/5 9:44
     * @remark 这个属性的class
     */
    public static Class getFiledClass(String tableName, String fieldName, Class c){
        try {
            String key = tableName + ":" + fieldName;
            Class fc = EntityFieldUtil.FIELD_CLASS_G_CACHE.get(key, () -> getFiledClassReal(fieldName, c));
            return fc;
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/5 9:30
     * @remark 得到这个实体的 属性所属于的 class
     */
    private static Class getFiledClassReal(String fieldName, Class c){

        List<Field> fields = Arrays.asList(c.getDeclaredFields());
        List<String> fieldNameList = fields.stream().map(Field::getName).collect(Collectors.toList());
        if (fieldNameList.contains(fieldName)){
            return c;
        }
        Class sup = c.getSuperclass();
        if (sup.equals(Object.class)){
            return c;
        }else {
            return getFiledClassReal(fieldName, sup);
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 10:35
     * @remark 将字段名字转换为实体的名字
     */
    public static String fieldToCamelCase(String field){
        try {
            String name = FIELD_NAME_CACHE.get(field, () -> StrUtil.toCamelCase(field));
            return name;
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/5 11:22
    * @remark 这个类是不是有这个字段
    */
    public static Boolean classFieldExist(Class c, String filedName){

        try {
            String key = c.getName() + ":" + filedName;
            Boolean exist = CLASS_FIELD_NAME_CACHE.get(key, () -> {
                Field[] fields = c.getDeclaredFields();
                for (Field field: fields){
                    String className = field.getName();
                    if (className.equals(filedName)){
                        return true;
                    }
                }
                log.info("字段不存在 {} {}", c.getName(), filedName);
                return false;
            });
            return exist;
        } catch (ExecutionException e) {
            log.error(e);
            throw new RuntimeException(e.getMessage());
        }
    }

}

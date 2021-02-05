package com.cdqckj.gmis.base;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.cdqckj.gmis.utils.MapHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 枚举类型基类
 *
 * @author gmis
 * @date 2019/07/26
 */
public interface BaseEnum extends IEnum<String> {
    /**
     * 将制定的枚举集合转成 map
     * key -> name
     * value -> desc
     *
     * @param list
     * @return
     */
    static Map<String, String> getMap(BaseEnum[] list) {
        return MapHelper.uniqueIndex(Arrays.asList(list), BaseEnum::getCode, BaseEnum::getDesc);
    }

    /**
     * 编码重写
     *
     * @return
     */
    default String getCode() {
        return toString();
    }

    /**
     * 描述
     *
     * @return
     */
    String getDesc();

    /**
     * 枚举值
     *
     * @return
     */
    @Override
    @JsonIgnore
    default String getValue() {
        return getCode();
    }


    static String[] getDescStr(BaseEnum[] array){
        Object[]  obj = Arrays.stream(array).map(BaseEnum::getDesc).toArray();
        return Arrays.copyOf(obj, obj.length, String[].class);
    }

    static BaseEnum getDescMap(BaseEnum[] array,String key) {
        // todo 如何从redis获取当前语言环境
        Map<String, BaseEnum> map = new LinkedHashMap<>();
        for (BaseEnum t : array) {
            map.put(t.getDesc(),t);
        }
        if(map.containsKey(key)){
            return map.get(key);
        }else{
            return null;
        }
    }
}

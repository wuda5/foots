package com.cdqckj.gmis.base;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public interface BaseSelfEnum extends BaseEnum{
    String getLanguage();

    void setDesc(String desc);

    //根据语言生成map（0-所有，1-中文，2-英文）
    static Map<String, String> getDescMap(BaseSelfEnum[] array, Integer langType) {
        // todo 如何从redis获取当前语言环境
        Map<String, String> map = new LinkedHashMap<>();
        for (BaseSelfEnum t : array) {
            String[] descs = t.getLanguage().split(",");
            if(langType>0){
                map.put(descs[langType-1],t.getCode());
            }else{
                for(String s: descs){
                    map.put(s,t.getCode());
                }
            }
        }
        return map;
    }

    /*static String[] getDescStr(BaseSelfEnum[] array){
        Object[]  obj = Arrays.stream(array).map(BaseSelfEnum::getDesc).toArray();
        return Arrays.copyOf(obj, obj.length, String[].class);
    }*/
}

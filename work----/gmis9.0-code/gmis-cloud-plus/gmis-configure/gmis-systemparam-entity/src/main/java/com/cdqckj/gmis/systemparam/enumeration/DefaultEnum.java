package com.cdqckj.gmis.systemparam.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum DefaultEnum {
    /**
     * 默认模板
     */
    NOT_REVIEWED(0,"默认模板"),
    /**
     * 审核通过
     */
    APPROVED(1,"非默认");
    private Integer status;
    private String desc;
    DefaultEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus(){
        return this.status;
    }
    public String getDesc(){
        return this.desc;
    }

    private static Map<Integer, DefaultEnum> map = new HashMap<>();

    static{
        for(DefaultEnum enums: DefaultEnum.values()){
            map.put(enums.status,enums);
        }
    }

    public static DefaultEnum getType(Integer status){
        return map.get(status);
    }
}

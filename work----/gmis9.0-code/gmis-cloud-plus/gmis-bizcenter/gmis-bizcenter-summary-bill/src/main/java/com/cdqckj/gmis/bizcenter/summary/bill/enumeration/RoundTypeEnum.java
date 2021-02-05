package com.cdqckj.gmis.bizcenter.summary.bill.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum RoundTypeEnum {
    /**
     * 向上取整
     */
    ROUND_HALF_UP(1,"向上取整"),
    /**
     * 向下取整
     */
    ROUND_HALF_DOWN(0,"向下取整");
    private Integer status;
    private String desc;
    RoundTypeEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }
    public Integer getStatus(){
        return this.status;
    }
    public String getDesc(){
        return this.desc;
    }
    private static Map<Integer, RoundTypeEnum> map = new HashMap<>();
    static{
        for(RoundTypeEnum enums: RoundTypeEnum.values()){
            map.put(enums.status,enums);
        }
    }
    public static RoundTypeEnum getType(Integer status){
        return map.get(status);
    }
}

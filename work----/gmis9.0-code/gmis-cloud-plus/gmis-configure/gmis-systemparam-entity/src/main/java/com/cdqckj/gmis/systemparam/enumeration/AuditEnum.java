package com.cdqckj.gmis.systemparam.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum AuditEnum {
    /**
     * 未提交
     */
    NOT_SUBMITTED(-2,"未提交"),
    /**
     * 驳回
     */
    REJECT(-1,"驳回"),
    /**
     * 未审核
     */
    NOT_REVIEWED(0,"未审核"),
    /**
     * 审核通过
     */
    APPROVED(1,"审核通过");
    private Integer status;
    private String desc;
    AuditEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus(){
        return this.status;
    }
    public String getDesc(){
        return this.desc;
    }

    private static Map<Integer,AuditEnum> map = new HashMap<>();

    static{
        for(AuditEnum enums:AuditEnum.values()){
            map.put(enums.status,enums);
        }
    }

    public static AuditEnum getType(Integer status){
        return map.get(status);
    }
}

package com.cdqckj.gmis.systemparam.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum InterfaceModeEnum {
    /**
     * 本地接口
     */
    LOCAL_INTERFACE(0,"本地接口", "nativePay"),
    /**
     * 成都支付通
     */
    CHENGDU_ALIPAY(1,"成都支付通", "thiredPay");
    private Integer status;
    private String desc;
    private String code;
    InterfaceModeEnum(Integer status, String desc, String code){
        this.status = status;
        this.desc = desc;
        this.code = code;
    }

    public Integer getStatus(){
        return this.status;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getCode(){
        return this.code;
    }

    private static Map<Integer, InterfaceModeEnum> map = new HashMap<>();

    static{
        for(InterfaceModeEnum enums: InterfaceModeEnum.values()){
            map.put(enums.status,enums);
        }
    }

    public static InterfaceModeEnum getType(Integer status){
        return map.get(status);
    }
}

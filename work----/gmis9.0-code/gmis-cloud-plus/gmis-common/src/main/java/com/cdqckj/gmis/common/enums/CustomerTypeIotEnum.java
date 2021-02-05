package com.cdqckj.gmis.common.enums;

/**
 * 客户类型IOT转化枚举
 */
public enum CustomerTypeIotEnum {

    RESIDENT("RESIDENT",0,"居民"),
    INDUSTRY("INDUSTRY",2,"工业"),
    BUSINESS("BUSINESS",1,"商业"),
    PUBLIC_WELFARE("PUBLIC_WELFARE",3,"公福");
    CustomerTypeIotEnum(String code,Integer iotCode,String desc){
        this.code = code;
        this.iotCode = iotCode;
        this.desc = desc;
    }

    private String code;
    private Integer iotCode;
    private String desc;

    public static CustomerTypeIotEnum getByCode(String code) {
        if(null == code) {
            return CustomerTypeIotEnum.RESIDENT;
        }
        for (CustomerTypeIotEnum enm : CustomerTypeIotEnum.values()) {
            if(enm.code.equals(code)){
                return enm;
            }
        }
        return CustomerTypeIotEnum.RESIDENT;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIotCode() {
        return iotCode;
    }

    public void setIotCode(Integer iotCode) {
        this.iotCode = iotCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

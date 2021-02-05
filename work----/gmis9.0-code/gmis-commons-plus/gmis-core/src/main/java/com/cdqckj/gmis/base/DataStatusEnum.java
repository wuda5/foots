package com.cdqckj.gmis.base;

public enum DataStatusEnum {
    NORMAL(1,"启用，正常"),
    NOT_AVAILABLE(0,"禁用，作废"),
    LOCK(2,"锁定");
    /**
         * 类型值
         */
    private int value;
    /**
         * 类型描述
         */
    private String desc;
    DataStatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public int getValue(){
        return this.value;
    }
    public String getDesc(){
        return this.desc;
    }

}

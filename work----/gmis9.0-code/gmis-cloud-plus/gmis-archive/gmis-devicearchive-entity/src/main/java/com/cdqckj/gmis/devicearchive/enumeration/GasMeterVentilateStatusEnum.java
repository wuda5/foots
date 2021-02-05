package com.cdqckj.gmis.devicearchive.enumeration;

public enum GasMeterVentilateStatusEnum {
    VENTILATE(1,"通气"),
    NOT_VENTILATE(0,"没通气");
    /*
         * 类型值
         */
    private int value;
    /**
         * 类型描述
         */
    private String desc;
    GasMeterVentilateStatusEnum(int value, String desc) {
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

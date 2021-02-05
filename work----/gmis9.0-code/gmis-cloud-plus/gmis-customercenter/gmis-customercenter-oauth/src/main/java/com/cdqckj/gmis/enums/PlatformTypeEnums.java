package com.cdqckj.gmis.enums;

/**
 * 平台类型枚举值
 * @auther hc
 * @date 2020/10/30
 */
public enum PlatformTypeEnums {

    IOS("ios","ios"),
    ANDROID("android","安卓"),
    MP_WEIXIN("mp-weixin","微信小程序"),
    MP_ALIPAY("mp-alipay","支付包小程序");

    private String key;
    private String desc;

    PlatformTypeEnums(String key , String desc){
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}

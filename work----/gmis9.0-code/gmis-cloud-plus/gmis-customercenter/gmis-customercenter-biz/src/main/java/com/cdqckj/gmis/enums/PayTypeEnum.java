package com.cdqckj.gmis.enums;

/**
 * 支付枚举类
 * @auther hc
 * @date 2020/10/26
 */
public enum PayTypeEnum {

    WX_PAY("WX_PAY","微信支付"),
    ALI_PAY("ALI_PAY","支付宝支付");



    PayTypeEnum(String key,String desc){
        this.key = key;
        this.desc = desc;
    }

    private String key;
    private String desc;

    /**
     * 根据key获取气表类型
     * @author HC
     * @param key 气表key
     * @return
     */
    public PayTypeEnum getByKeyEnum(String key){
        for(PayTypeEnum smsTemplate : PayTypeEnum.values()){
            if(smsTemplate.key == key){

                return smsTemplate;
            }
        }
        throw new IllegalArgumentException("No element mathches:" + key);
    }

    /**
     * 根据type获取模板
     * @author HC
     * @param key 验证码类型
     * @return
     */
    public String getByTypeDesc(String key){
        for(PayTypeEnum smsTemplate : PayTypeEnum.values()){
            if(smsTemplate.key == key){

                return smsTemplate.getDesc();
            }
        }
        throw new IllegalArgumentException("No element mathches:" + key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.cdqckj.gmis.enums;

/**
 * 表具类型枚举
 * @auther hc
 */
public enum GasMeterTypeEnum {

    GENERAL_GASMETER("GENERAL_GASMETER","普表"),
    CARD_GASMETER("CARD_GASMETER","卡表"),
    INTERNET_GASMETER("INTERNET_GASMETER","物联网IOC表");


     GasMeterTypeEnum(String key,String desc){
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
    public GasMeterTypeEnum getByKeyEnum(String key){
        for(GasMeterTypeEnum smsTemplate : GasMeterTypeEnum.values()){
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
        for(GasMeterTypeEnum smsTemplate : GasMeterTypeEnum.values()){
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

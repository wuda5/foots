package com.cdqckj.gmis.biztemporary.enums;

/**
 * 过户业务状态枚举值
 * @author  hc
 * @date 2020/11/06
 */
public enum TransferAccountStatusEnum {

    CANCEL(0,"取消"),
    SUCCESS(1,"完成"),
    WAIT_PAY(2,"待支付"),
    PROCESSING(3,"处理中");

    private Integer key;
    private String desc;

    TransferAccountStatusEnum(Integer key, String desc){
        this.key = key;
        this.desc = desc;
    }

    public static String getDescByKey(Integer key){
        for(TransferAccountStatusEnum item : TransferAccountStatusEnum.values()){
            if(item.key.equals(key)){
                return item.desc;
            }
        }

        return "";
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

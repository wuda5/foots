package com.cdqckj.gmis.base;

public enum BizTypeEnum {
    /**
     * 公司资质
     */
    COMPANY_QUALIFICATION("COMPANY_QUALIFICATION","公司资质"),
    /**
     * 临时文件(每天会删除，所以不要存放重要文件)
     */
    TEMPORARY_DOCUMENTS("TEMPORARY_DOCUMENTS","临时文件"),
    /**
     * 模板文件
     */
    TEMPLATE_FILE("TEMPLATE_FILE","模板文件"),

    /**
     * 签名证书密钥库文件
     */
    KEYSTORE_FILE("KEYSTORE_FILE", "签名证书密钥库"),

    /**
     * 签名证书密钥库文件
     */
    CUSTOMER_MATERIAL("CUSTOMER_MATERIAL", "客户档案"),

    /**
     * 报装安检资料
     */
    INATALL_CHECK_FILE("INATALL_CHECK_FILE", "报装安检资料库");

    private String code;
    private String desc;
    BizTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public String getCode(){
        return this.code;
    }
    public String getDesc(){
        return this.desc;
    }
}

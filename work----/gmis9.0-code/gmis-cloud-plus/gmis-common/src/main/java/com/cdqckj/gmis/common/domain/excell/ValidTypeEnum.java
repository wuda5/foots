package com.cdqckj.gmis.common.domain.excell;

public enum ValidTypeEnum {

    // 身份证
    REGEX_ID_CARD( 1),
    // 电话号码
    REGEX_MOBILE(2),
    // 数字验证
    NUM(3),
  // 日期
    DATE(4),
    //客户名称
    customerName(5),
    // 不验证
    NOT_VALID(100);

    private Integer value;

    ValidTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}

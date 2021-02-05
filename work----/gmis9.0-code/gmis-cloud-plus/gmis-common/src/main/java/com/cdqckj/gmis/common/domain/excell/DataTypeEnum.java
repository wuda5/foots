package com.cdqckj.gmis.common.domain.excell;

/**
 * @author: lijianguo
 * @time: 2020/12/11 15:02
 * @remark: 请添加类说明
 */
public enum DataTypeEnum {

    ALL("所有的数据"),
    SUC("该行全部成功的数据"),
    FAIL("该行有失败的数据");

    private String describe;

    DataTypeEnum(String describe) {
        this.describe = describe;
    }
}

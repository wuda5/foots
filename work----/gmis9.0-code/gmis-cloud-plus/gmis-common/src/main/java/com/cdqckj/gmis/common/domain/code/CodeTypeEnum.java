package com.cdqckj.gmis.common.domain.code;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: lijianguo
 * @time: 2020/12/07 13:19
 * @remark: 编码方式
 */
public enum CodeTypeEnum {

    /** 随机编码_类型一" **/
    RANDOM_TYPE_ONE(1),

    /** 自增编码_类型一 **/
    INC_TYPE_ONE(2);

    CodeTypeEnum(Integer type) {
        this.type = type;
    }

    @ApiModelProperty(value = "编码类型")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public static CodeTypeEnum match(Integer type) {
        for (CodeTypeEnum enm : CodeTypeEnum.values()) {
            if (enm.getType() == type){
                return enm;
            }
        }
        throw new RuntimeException("类型不存在");
    }

    public static CodeTypeEnum get(Integer type) {
        return match(type);
    }

}

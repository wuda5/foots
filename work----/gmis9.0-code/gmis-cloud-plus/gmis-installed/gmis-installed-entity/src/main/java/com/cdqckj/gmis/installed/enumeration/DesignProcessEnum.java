package com.cdqckj.gmis.installed.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DesignProcessEnum", description = "报装设计流程-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DesignProcessEnum implements BaseEnum {
    /**
     * 待审核
     */
    TO_BE_REVIEWED,
    /**
     * 提审
     */
    SUBMIT_FOR_REVIEW,

    /*审核驳回
    * */
    REVIEW_REJECTED,

    /* 审核通过
    * */
    APPROVED;

    @ApiModelProperty(value = "描述")
    private String desc;

    public static DesignProcessEnum match(String val, DesignProcessEnum def) {
        for (DesignProcessEnum enm : DesignProcessEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DesignProcessEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DesignProcessEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "TO_BE_REVIEWED,SUBMIT_FOR_REVIEW,REVIEW_REJECTED,APPROVED", example = "TO_BE_REVIEWED")
    public String getCode() {
        return this.name();
    }
}

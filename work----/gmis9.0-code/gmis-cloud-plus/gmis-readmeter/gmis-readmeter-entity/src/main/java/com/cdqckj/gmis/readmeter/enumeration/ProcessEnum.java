package com.cdqckj.gmis.readmeter.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 抄表数据审核状态
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProcessEnum", description = "状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProcessEnum implements BaseEnum {

    /**
     * 待审核
     */
    TO_BE_REVIEWED("待提审"),
    /**
     * 提审
     */
    SUBMIT_FOR_REVIEW("待审核"),
    /**
     * 审核通过
     */
    APPROVED("审核通过（待结算）"),
    /**
     * 审核通过
     */
    SETTLED("结算完成"),
    /**
     * 审核驳回
     */
    REVIEW_REJECTED("审核驳回")
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static ProcessEnum match(String val, ProcessEnum def) {
        for (ProcessEnum enm : ProcessEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ProcessEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ProcessEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "DEFAULT,COMPANY", example = "DEFAULT")
    public String getCode() {
        return this.name();
    }

}

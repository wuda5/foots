package com.cdqckj.gmis.charges.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 扎帐状态
 *
 * @author gmis
 * @date 2018/12/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "SummaryBillStatusEnum", description = "扎帐状态")
public enum SummaryBillStatusEnum {
    /**
     * 未扎帐
     */
    UNBILL("未扎帐"),
    /**
     * 已扎帐
     */
    BILLED("已扎帐");

    @ApiModelProperty(value = "描述")
    private String desc;

    public static SummaryBillStatusEnum match(String val, SummaryBillStatusEnum def) {
        for (SummaryBillStatusEnum enm : SummaryBillStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static SummaryBillStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(SummaryBillStatusEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value = "编码")
    public String getCode() {
        return this.name();
    }
}

package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 补卡方式
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RepCardMethodEnum", description = "补卡方式")
public enum RepCardMethodEnum {
    /**
     * 补上次充值
     */
    REP_PRE_RECHARGE("补上次充值"),
    /**
     * 不补金额气量
     */
    NOT_REP_RECHARGE("不补金额气量");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RepCardMethodEnum match(String val, RepCardMethodEnum def) {
        for (RepCardMethodEnum enm : RepCardMethodEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RepCardMethodEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RepCardMethodEnum val) {
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

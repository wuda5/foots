package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 补卡类型
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RepCardTypeEnum", description = "补卡类型")
public enum RepCardTypeEnum {
    /**
     * 补新用户卡
     */
    REP_NEW_USER_CARD("补新用户卡"),
    /**
     * 补老用户卡
     */
    REP_OLD_USER_CARD("补老用户卡");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RepCardTypeEnum match(String val, RepCardTypeEnum def) {
        for (RepCardTypeEnum enm : RepCardTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RepCardTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RepCardTypeEnum val) {
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

package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 补卡状态
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "RepCardStatusEnum", description = "补卡状态")
public enum RepCardStatusEnum {
//    /**
//     * 待发卡
//     */
//    WAITE_ISSUE_CARD("待发卡"),
    /**
     * 待收费
     */
    WAITE_CHARGE("待收费"),
    /**
     * 待写卡
     */
    WAITE_WRITE_CARD("待写卡"),
    /**
     * 已补卡
     */
    REP_CARD_SUCCESS("已补卡");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static RepCardStatusEnum match(String val, RepCardStatusEnum def) {
        for (RepCardStatusEnum enm : RepCardStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static RepCardStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(RepCardStatusEnum val) {
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

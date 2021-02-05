package com.cdqckj.gmis.card.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 发卡状态
 *
 * @author tp
 * @date 2020/11/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@ApiModel(value = "CardStatusEnum", description = "发卡状态")
public enum CardStatusEnum {
    /**
     * 待发卡
     */
    WAITE_ISSUE_CARD("待发卡"),
//    /**
//     * 待收费
//     */
//    WAITE_OPER("待收费"),
    /**
     * 第三方支付回调
     */
    THIRED_PAY_CALLBACK("第三方支付回调"),
    /**
     * 待写卡
     */
    WAITE_WRITE_CARD("待写卡"),
    /**
     * 已发卡
     */
    ISSUE_CARD("已发卡");
   

    @ApiModelProperty(value = "描述")
    private String desc;

    public static CardStatusEnum match(String val, CardStatusEnum def) {
        for (CardStatusEnum enm : CardStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CardStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CardStatusEnum val) {
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

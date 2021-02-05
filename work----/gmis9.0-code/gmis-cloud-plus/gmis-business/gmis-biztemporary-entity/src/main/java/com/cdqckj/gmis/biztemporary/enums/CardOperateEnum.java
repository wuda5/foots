package com.cdqckj.gmis.biztemporary.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 卡操作状态
 *
 * @author hp
 */

@Getter
@NoArgsConstructor
@ApiModel(value = "CardOperateEnum", description = "卡操作状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CardOperateEnum {

    /**
     * 待写卡
     */
    WAIT_WRITE_CARD("待写卡"),
    /**
     * 待上表
     */
    WAIT_TO_METER("待上表"),
    /**
     * 补气完成
     */
    SUCCESS("完成");

    @ApiModelProperty(value = "描述")
    private String desc;

    CardOperateEnum(String desc) {
        this.desc = desc;
    }

    public static CardOperateEnum match(String val, CardOperateEnum def) {
        for (CardOperateEnum enm : CardOperateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CardOperateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CardOperateEnum val) {
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

package com.cdqckj.gmis.biztemporary.enums;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ChangeTypeEnum", description = "换表类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChangeSceneEnum implements BaseEnum {

    GENERAL_TO_GENERAL("GENERAL_TO_GENERAL", "普表换普表"),
    GENERAL_TO_GAS_CARD("GENERAL_TO_GAS_CARD", "普表换卡表(气表)"),
    GAS_CARD_TO_GENERAL("GAS_CARD_TO_GENERAL", "卡表(气表)换普表"),
    GAS_CARD_TO_GAS_CARD("GAS_CARD_TO_GAS_CARD", "卡表(气表换卡表(气表)"),
    GENERAL_TO_MONEY_CARD("GENERAL_TO_MONEY_CARD", "普表换卡表(金额)"),
    GENERAL_TO_INTERNET_PRE("GENERAL_TO_INTERNET_PRE", "普表换物联网表预付费"),
    GAS_CARD_TO_INTERNET_PRE("GAS_CARD_TO_INTERNET_PRE", "卡表(气表)换物联网表预付费"),
    GAS_CARD_TO_MONEY_CARD("GAS_CARD_TO_MONEY_CARD", "卡表(气表)换卡表(金额)"),
    MONEY_CARD_TO_MONEY_CARD("MONEY_CARD_TO_MONEY_CARD", "卡表(金额)换卡表(金额)"),
    MONEY_CARD_TO_INTERNET_PRE("MONEY_CARD_TO_INTERNET_PRE", "卡表(金额)换物联网表预付费"),
    INTERNET_PRE_TO_INTERNET_PRE("INTERNET_PRE_TO_INTERNET_PRE", "物联网表预付费换物联网表预付费"),
    INTERNET_PRE_TO_MONEY_CARD("INTERNET_PRE_TO_MONEY_CARD", "物联网表预付费换卡表(金额 )"),
    MONEY_CARD_TO_GAS_CARD("MONEY_CARD_TO_GAS_CARD", "卡表(金额 )换卡表(气表)"),
    MONEY_CARD_TO_GENERAL("MONEY_CARD_TO_GENERAL", "卡表(金额 )换普表"),
    INTERNET_PRE_TO_GENERAL("INTERNET_PRE_TO_GENERAL", "物联网表预付费换普表"),
    INTERNET_PRE_TO_GAS_CARD("INTERNET_PRE_TO_GAS_CARD", "物联网表预付费换卡表(气表)");

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 描述
     *
     * @return
     */
    @Override
    public String getDesc() {
        return null;
    }

    public static ChangeSceneEnum match(String val, ChangeSceneEnum def) {
        for (ChangeSceneEnum enm : ChangeSceneEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ChangeSceneEnum getByCode(String code) {
        for (ChangeSceneEnum enm : ChangeSceneEnum.values()) {
            if (enm.getCode().equalsIgnoreCase(code)) {
                return enm;
            }
        }
        return null;
    }

    public boolean eq(String val) {
        return getCode().equalsIgnoreCase(val);
    }

    public static ChangeSceneEnum get(String val) {
        return match(val, null);
    }
}

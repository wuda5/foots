package com.cdqckj.gmis.systemparam.enumeration;

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
@ApiModel(value = "TolltemSceneEnum", description = "场景收费-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TolltemSceneEnum implements BaseEnum {
//    INSTALL("报装"),
    OPEN_ACCOUNT("开户"),
    INSURANCE_FEE("保险费"),
    GAS_FEE("燃气费"),
    ISSUE_CARD("发卡"),
    CARD_REPLACEMENT("补卡"),
    CHANGE_METER("换表"),
    DIS_METER("拆表"),
    TRANSFER("过户"),
    GAS_COMPENSATION("调价补差"),
//    SALES_OF_GOODS("商品销售"),
    OTHER("附加费"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static TolltemSceneEnum match(String val, TolltemSceneEnum def) {
        for (TolltemSceneEnum enm : TolltemSceneEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static TolltemSceneEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TolltemSceneEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "INSTALL,OPEN_ACCOUNT,IC_CARD_CHARGE,IOT_CHARGE,READ_METER_CHARGE,CHANGE_METER,TRANSFER,Gas_COMPENSATION,SALES_OF_GOODS,OTHER", example = "INSTALL")
    public String getCode() {
        return this.name();
    }

}

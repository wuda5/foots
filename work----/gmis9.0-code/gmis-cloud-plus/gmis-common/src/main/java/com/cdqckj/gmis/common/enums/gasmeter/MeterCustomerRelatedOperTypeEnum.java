package com.cdqckj.gmis.common.enums.gasmeter;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 气表客户关系操作类型
 * @author  tp
 * @date 2020-11-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MeterCustomerRelatedOperTypeEnum", description = "气表客户关系操作类型-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MeterCustomerRelatedOperTypeEnum implements BaseEnum {
    /**
     * OPEN_ACCOUNT="开户"
     */
    OPEN_ACCOUNT("开户"),
    /**
     * TRANS_ACCOUNT="过户"
     */
    TRANS_ACCOUNT("过户"),
    /**
     * DIS_ACCOUNT="销户"
     */
    DIS_ACCOUNT("销户"),
    /**
     * REMOVE_METER="拆表"
     */
    REMOVE_METER("拆表"),
    /**
     * CHANGE_METER="换表"
     */
    CHANGE_METER("换表");

    @ApiModelProperty(value = "描述")
    private String desc;


    public static MeterCustomerRelatedOperTypeEnum match(String val, MeterCustomerRelatedOperTypeEnum def) {
        for (MeterCustomerRelatedOperTypeEnum enm : MeterCustomerRelatedOperTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static MeterCustomerRelatedOperTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(MeterCustomerRelatedOperTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "OPEN_ACCOUNT,TRANS_ACCOUNT,DIS_ACCOUNT,CHANGE_METER", example = "OPEN_ACCOUNT")
    public String getCode() {
        return this.name();
    }

}

package com.cdqckj.gmis.devicearchive.enumeration;

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
 * 气表档案
 * </p>
 *
 * @author gmis
 * @date 2020-08-14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SendCardState", description = "发卡状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SendCardState implements BaseEnum {

    /**
     *待发卡
     */
    WAITE_SEND("待发卡"),
    /**
     * 已发卡
     */
    SENDED("已发卡")
    ;

    @ApiModelProperty(value = "描述")
    private String desc;


    public static SendCardState match(String val, SendCardState def) {
        for (SendCardState enm : SendCardState.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static SendCardState get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(SendCardState val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "WAIT_OPEN_CARD,OPENED_CARD", example = "OPENED_CARD")
    public String getCode() {
        return this.name();
    }

}

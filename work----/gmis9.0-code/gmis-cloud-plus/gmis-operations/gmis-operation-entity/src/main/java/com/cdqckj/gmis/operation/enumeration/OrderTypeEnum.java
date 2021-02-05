package com.cdqckj.gmis.operation.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "OrderTypeEnum", description = "工单类型状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderTypeEnum {

    /**
     * 报装工单"
     */
    INSTALL_ORDER("报装工单",1),
    /**
     * 安检工单"
     */
    SECURITY_ORDER("安检工单",2),
    /**
     * 运维工单"
     */
    ORPERATION_ORDER("运维工单",3),
    /**
     * 客服工单"
     */
    SERVICE_ORDER("客服工单",4);

    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private OrderTypeEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static OrderTypeEnum match(String val, OrderTypeEnum def) {
        for (OrderTypeEnum enm : OrderTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OrderTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(OrderTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}

package com.cdqckj.gmis.operation.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "OrderStateEnum", description = "工单状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStateEnum {


    /**
     * WAIT_RECEIVE_ORDER="待接单"
     */
    WAIT_RECEIVE("待接单",0),
    /**
     *
     */
    RECEIVE("已接单",1),

    /**
     * MAINTAINING="运维中"
     */
    REJECT("已拒单",2),

    /**
     * COMPLETE="已结单"
     */
    COMPLETE("已结单",3),

    /**
     * TERMINATION="已取消"
     */
    TERMINATION("已取消",4);




    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private OrderStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static OrderStateEnum match(String val, OrderStateEnum def) {
        for (OrderStateEnum enm : OrderStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OrderStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(OrderStateEnum val) {
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

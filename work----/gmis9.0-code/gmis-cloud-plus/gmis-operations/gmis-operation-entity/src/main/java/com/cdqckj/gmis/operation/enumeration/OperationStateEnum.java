package com.cdqckj.gmis.operation.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "OperationPlanStateEnum", description = "运行维护计划状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationStateEnum {
    /**
     * PENDING_INITIATE_REVIEW="待提审"
     */
    PENDING_INITIATE_REVIEW("待提审",0),
    /**
     * PENDING_APPROVAL="待审核"
     */
    PENDING_APPROVAL("待审核",1),
    /**
     * WAIT_RECEIVE_ORDER="待接单"
     */
    WAIT_RECEIVE_ORDER("待接单",2),
    /**
     * TO_BE_MAINTAINED="待运维"
     */
    TO_BE_MAINTAINED("待运维",3),
    /**
     * MAINTAINING="运维中"
     */
    MAINTAINING("运维中",4),
    /**
     * TERMINATION="终止"
     */
    TERMINATION("终止",5),
    /**
     * CANCELLATION="作废"
     */
    CANCELLATION("作废",6),
    /**
     * END_ORDER="结单"
     */
    END_ORDER("结单",7);


    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private OperationStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static OperationStateEnum match(String val, OperationStateEnum def) {
        for (OperationStateEnum enm : OperationStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OperationStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(OperationStateEnum val) {
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

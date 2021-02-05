package com.cdqckj.gmis.operation.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "OperationPlanProcessStateEnum", description = "运行维护计划状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationProcessStateEnum {
    /**
     * PENDING_INITIATE_REVIEW="提审"
     */
    PENDING_INITIATE_REVIEW("提审",0),
    /**
     * PENDING_APPROVAL="审核"
     */
    PENDING_APPROVAL("审核",1),
    /**
     * TO_BE_ASSIGNED="派单"
     */
    TO_BE_ASSIGNED("派单",2),
    /**
     * REJECT="驳回"
     */
    REJECT("驳回",3),
    /**
     * WITHDRAW="撤回"
     */
    WITHDRAW("撤回",4),
    /**
     * CONFIRM_ORDER_RECEIVE="接单确认"
     */
    CONFIRM_ORDER_RECEIVE("接单确认",5),
    /**
     * REJECT_ORDER="拒单"
     */
    REJECT_ORDER("拒单",6),
    /**
     * BACK_ORDER="退单"
     */
    BACK_ORDER("退单",7),
    /**
     * TRANSFOR_ORDER="转单"
     */
    TRANSFOR_ORDER("转单",8),
    /**
     * APPOINT="预约"
     */
    APPOINT("预约",9),
    /**
     * VISIT="上门"
     */
    VISIT("上门",10),
    /**
     * END_ORDER="结单"
     */
    END_ORDER("结单",11),
    /**
     * TERMINATION="终止"
     */
    TERMINATION("终止",12);

    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private OperationProcessStateEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static OperationProcessStateEnum match(String val, OperationProcessStateEnum def) {
        for (OperationProcessStateEnum enm : OperationProcessStateEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static OperationProcessStateEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(OperationProcessStateEnum val) {
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

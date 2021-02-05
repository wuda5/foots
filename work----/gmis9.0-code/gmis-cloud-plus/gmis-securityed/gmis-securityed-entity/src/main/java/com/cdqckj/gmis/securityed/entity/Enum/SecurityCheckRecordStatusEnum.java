package com.cdqckj.gmis.securityed.entity.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "SecurityCheckRecordStatusEnum", description = "安检计划状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SecurityCheckRecordStatusEnum {
    /**
     * PENDING_INITIATE_REVIEW="待提审"
     */
    PENDING_INITIATE_REVIEW("待提审",0),
    /**
     * PENDING_APPROVAL="待审核"
     */
    PENDING_APPROVAL("待审核",1),
    /**
     * TO_BE_ASSIGNED="待派工"
     */
    TO_BE_ASSIGNED("待派工",2),
    /**
     * WAITING_FOR_CHECK="待安检"
     */
    WAITING_FOR_CHECK("待安检",3),
    /**
     * SECURITY_CHECKING="安检中"
     */
    SECURITY_CHECKING("安检中",4),
    /**
     * ABNORMAL="异常"
     */
    ABNORMAL("异常",5),
    /**
     * END_ORDERS="结单"
     */
    END_ORDERS("结单",6),
    /**
     * TERMINATION="终止"
     */
    TERMINATION("终止",7);

    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private SecurityCheckRecordStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static SecurityCheckRecordStatusEnum match(String val, SecurityCheckRecordStatusEnum def) {
        for (SecurityCheckRecordStatusEnum enm : SecurityCheckRecordStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static SecurityCheckRecordStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(SecurityCheckRecordStatusEnum val) {
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

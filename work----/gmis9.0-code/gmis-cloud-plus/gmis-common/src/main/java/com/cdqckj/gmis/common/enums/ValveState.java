package com.cdqckj.gmis.common.enums;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 阀门状态枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/16  21:10
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ValveState", description = "阀门状态-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ValveState implements BaseEnum {
    /**
     * 权限关阀
     */
    PowerClose("关",0),
    /**
     * 开阀
     */
    Open("开",1),
    /**
     * 异常
     */
    Exception("异常",2),
    /**
     * 无阀门状态
     */
    None("无阀门状态",3),
    /**
     * 普关
     */
    Close("普关",4),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "值")
    private Integer valveCode;


    public static ValveState match(String val, ValveState def) {
        for (ValveState enm : ValveState.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ValveState get(String val) {
        return match(val, null);
    }

    public static ValveState get(Integer val) {
        for (ValveState enm : ValveState.values()) {
            if (enm.getValveCode().equals(val)) {
                return enm;
            }
        }
        return null;
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ValveState val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "PowerClose,Open,Exception,None,Close", example = "PowerClose")
    public String getCode() {
        return this.name();
    }
}

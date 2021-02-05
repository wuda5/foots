package com.cdqckj.gmis.iot.qc.enumeration;

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
 * @Description: 物联网对指令执行状态枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/19  20:10
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@ApiModel(value = "ExecuteState", description = "指令执行状态-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExecuteState implements BaseEnum {
    /**
     * 已取消
     */
    Cancel("已取消",106),
    /**
     * 未执行
     */
    UnExecute("未执行",0),
    /**
     * 操作成功
     */
    Success("操作成功",1),
    /**
     * 已充值
     */
    ReCharged("已充值",2),
    /**
     * 操作错误
     */
    Error("操作错误",11),
    ;
    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "值")
    private Integer codeValue;

    public static CommandState match(String val, CommandState def) {
        for (CommandState enm : CommandState.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static CommandState get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(CommandState val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "Cancel,UnExecute,Success,ReCharged,Error", example = "Cancel")
    public String getCode() {
        return this.name();
    }
}

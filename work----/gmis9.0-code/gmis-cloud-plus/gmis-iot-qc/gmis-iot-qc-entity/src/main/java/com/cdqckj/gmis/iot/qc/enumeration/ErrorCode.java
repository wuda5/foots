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
 * @Description: 响应编码枚举
 * @author: 秦川物联网科技
 * @date: 2020/10/16  20:56
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ErrorCode", description = "执行结果编码-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements BaseEnum {
    /**
     * 交易成功
     */
    SUCCESS("交易成功","0000"),
    /**
     * 表具编号不存在
     */
    METERNO_NOTEXIST("表具编号不存在","1004"),
    /**
     * 表具编号已经存在，不能新增表具
     */
    METERNO_EXIST("表具编号已经存在，不能新增表具","1002"),
    ;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "编码")
    private String value;

    public static ErrorCode match(String val, ErrorCode def) {
        for (ErrorCode enm : ErrorCode.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ErrorCode get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(ErrorCode val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "SUCCESS,METERNO_NOTEXIST,METERNO_EXIST", example = "SUCCESS")
    public String getCode() {
        return this.name();
    }
}

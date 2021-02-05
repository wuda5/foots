package com.cdqckj.gmis.base;

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
    SUCCESS("执行成功","0"),
    /**
     * 表具编号已经存在，不能新增表具
     */
    METERNO_EXIST("表具编号已经存在，不能新增表具","-1"),
    /**
     * 表身号已存在，不能新增表具
     */
    METERNUM_EXIST("表身号已存在，不能新增表具","-1"),
    /**
     * 表具编号不存在
     */
    METERNO_NOTEXIST("表具编号不存在","-1"),
    /**
     * 表身号不存在
     */
    METERNO_NUMTEXIST("表身号不存在","-1"),
    /**
     * 表型号号不存在
     */
    METERTYPE_NOTEXIST("表型号号不存在","-1"),
    /**
     * 逻辑域id不能为空
     */
    DOMAIN_ID_NOTEXIST("厂家唯一标识不能为空","-1"),
    /**
     * 表具不符合注册条件，请检查表具状态
     */
    DEVICE_STATE_REGISTER_ERROR("表具不符合注册条件，请检查表具状态","-1"),
    /**
     * 表具未开户不能进行充值，请先开户
     */
    DEVICE_STATE_OPENACCOUNT_ERROR("表具未开户不能进行充值，请先开户","-1"),
    /**
     * 阶梯价格方案不正确,从1起记，最大支持5阶
     */
    DEVICE_PRICE_SCHEME_ERROR("阶梯价格方案不正确,从1起记，最大支持5阶","-1"),
    /**
     * 表具不是在用状态，拆除失败
     */
    DEVICE_STAGE_IS_STOP("表具不是在用状态，拆除失败","-1"),
    /**
     * 表具状态是未注册或拆除，阀控失败
     */
    DEVICE_VALVE_IS_FAIL("表具状态是未注册或拆除，阀控失败","-1")
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

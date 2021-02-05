package com.cdqckj.gmis.systemparam.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 支付配置
 * </p>
 *
 * @author gmis
 * @since 2020-07-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "PayInfoPageDTO", description = "支付配置")
public class PayInfoPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 账号等级（0-总公司，1-分公司）
     */
    @ApiModelProperty(value = "账号等级（0-总公司，1-分公司）")
    private Integer accountLevel;
    /**
     * 使用总公司账号
     */
    @ApiModelProperty(value = "使用总公司账号")
    private Integer usedParentPay;
    /**
     * 支付类型编码
     */
    @ApiModelProperty(value = "支付类型编码")
    @Length(max = 64, message = "支付类型编码长度不能超过64")
    private String payCode;
    /**
     * 支付类型名称
     */
    @ApiModelProperty(value = "支付类型名称")
    @Length(max = 64, message = "支付类型名称长度不能超过64")
    private String payType;
    /**
     * 秦川分配的商户号
     */
    @ApiModelProperty(value = "秦川分配的商户号")
    @Length(max = 100, message = "秦川分配的商户号长度不能超过100")
    private String nativeMerchantNo;
    /**
     * 成都支付通分配的商户号
     */
    @ApiModelProperty(value = "成都支付通分配的商户号")
    @Length(max = 100, message = "成都支付通分配的商户号长度不能超过100")
    private String thirdMerchantNo;
    /**
     * APPID
     */
    @ApiModelProperty(value = "APPID")
    @Length(max = 100, message = "APPID长度不能超过100")
    private String appId;
    /**
     * APPSECRET
     */
    @ApiModelProperty(value = "APPSECRET")
    @Length(max = 100, message = "APPSECRET长度不能超过100")
    private String appSecret;
    /**
     * API秘钥
     */
    @ApiModelProperty(value = "API秘钥")
    @Length(max = 100, message = "API秘钥长度不能超过100")
    private String apiSecret;
    /**
     * 商户私钥
     */
    @ApiModelProperty(value = "商户私钥")
    @Length(max = 2000, message = "商户私钥长度不能超过2000")
    private String merchantPrivateKey;

    /**
     * 支付宝公钥
     */
    @ApiModelProperty(value = "支付宝公钥")
    @Length(max = 400, message = "支付宝公钥长度不能超过400")
    private String alipayPublicKey;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    private String remark;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer payStatus;

    /**
     * 删除标识1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识1：存在 0：删除")
    private Integer deleteStatus;

    /**
     * 是否启用手续费比例（0-启用，1-禁用)
     */
    @ApiModelProperty(value = "是否启用手续费比例（0-启用，1-禁用）")
    private Integer serviceStatus;

    /**
     * 提现用户承担手续费比例
     */
    @ApiModelProperty(value = "提现用户承担手续费比例")
    private BigDecimal serviceRatio;

    /**
     * 是否启用:1 启用；0 不启用
     */
    @ApiModelProperty(value = "是否启用:1 启用；0 不启用")
    private Integer dataStatus;

    /**
     * 接口调用方式:0 本地调用；1 成都支付通
     */
    @ApiModelProperty(value = "接口调用方式:0 本地调用；1 成都支付通")
    private Integer interfaceMode;
}

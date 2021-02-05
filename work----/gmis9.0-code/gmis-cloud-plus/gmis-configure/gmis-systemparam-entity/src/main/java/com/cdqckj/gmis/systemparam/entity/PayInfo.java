package com.cdqckj.gmis.systemparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_pay_info")
@ApiModel(value = "PayInfo", description = "支付配置")
@AllArgsConstructor
public class PayInfo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    @Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 账号等级（0-总公司，1-分公司）
     */
    @ApiModelProperty(value = "账号等级（0-总公司，1-分公司）")
    @TableField("account_level")
    @Excel(name = "账号等级（0-总公司，1-分公司）")
    private Integer accountLevel;

    /**
     * 使用总公司账号
     */
    @ApiModelProperty(value = "使用总公司账号")
    @TableField("used_parent_pay")
    @Excel(name = "使用总公司账号")
    private Integer usedParentPay;

    /**
     * 支付类型编码
     */
    @ApiModelProperty(value = "支付类型编码")
    @Length(max = 64, message = "支付类型编码长度不能超过64")
    @TableField(value = "pay_code", condition = LIKE)
    @Excel(name = "支付类型编码")
    private String payCode;

    /**
     * 支付类型名称
     */
    @ApiModelProperty(value = "支付类型名称")
    @Length(max = 64, message = "支付类型名称长度不能超过64")
    @TableField(value = "pay_type", condition = LIKE)
    @Excel(name = "支付类型名称")
    private String payType;

    /**
     * 秦川分配的商户号
     */
    @ApiModelProperty(value = "秦川分配的商户号")
    @Length(max = 100, message = "秦川分配的商户号长度不能超过100")
    @TableField(value = "native_merchant_no", condition = LIKE)
    @Excel(name = "秦川分配的商户号")
    private String nativeMerchantNo;

    /**
     * 成都支付通分配的商户号
     */
    @ApiModelProperty(value = "成都支付通分配的商户号")
    @Length(max = 100, message = "成都支付通分配的商户号长度不能超过100")
    @TableField(value = "third_merchant_no", condition = LIKE)
    @Excel(name = "成都支付通分配的商户号")
    private String thirdMerchantNo;

    /**
     * APPID
     */
    @ApiModelProperty(value = "APPID")
    @Length(max = 100, message = "APPID长度不能超过100")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "APPID")
    private String appId;

    /**
     * APPSECRET
     */
    @ApiModelProperty(value = "APPSECRET")
    @Length(max = 100, message = "APPSECRET长度不能超过100")
    @TableField(value = "app_secret", condition = LIKE)
    @Excel(name = "APPSECRET")
    private String appSecret;

    /**
     * API秘钥
     */
    @ApiModelProperty(value = "API秘钥")
    @Length(max = 100, message = "API秘钥长度不能超过100")
    @TableField(value = "api_secret", condition = LIKE)
    @Excel(name = "API秘钥")
    private String apiSecret;

    /**
     * 商户私钥
     */
    @ApiModelProperty(value = "商户私钥")
    @Length(max = 2000, message = "商户私钥长度不能超过2000")
    @TableField(value = "merchant_private_key", condition = LIKE)
    @Excel(name = "商户私钥")
    private String merchantPrivateKey;

    /**
     * 支付宝公钥
     */
    @ApiModelProperty(value = "支付宝公钥")
    @Length(max = 400, message = "支付宝公钥长度不能超过400")
    @TableField(value = "alipay_public_key", condition = LIKE)
    @Excel(name = "支付宝公钥")
    private String alipayPublicKey;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("pay_status")
    @Excel(name = "状态")
    private Integer payStatus;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    /**
     * 是否启用手续费比例（0-启用，1-禁用)
     */
    @ApiModelProperty(value = "是否启用手续费比例（0-启用，1-禁用）")
    @TableField("service_status")
    private Integer serviceStatus;

    /**
     * 是否启用:1 启用；0 不启用
     */
    @ApiModelProperty(value = "是否启用:1 启用；0 不启用")
    @TableField("data_status")
    private Integer dataStatus;

    /**
     * 接口调用方式:0 本地调用；1 成都支付通
     */
    @ApiModelProperty(value = "接口调用方式:0 本地调用；1 成都支付通")
    @TableField("interface_mode")
    private Integer interfaceMode;

    /**
     * 提现用户承担手续费比例
     */
    @ApiModelProperty(value = "提现用户承担手续费比例")
    @TableField("service_ratio")
    private BigDecimal serviceRatio;

    @Builder
    public PayInfo(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, String alipayPublicKey,
                    String companyCode, String companyName, Long orgId, String orgName, Integer accountLevel, String merchantPrivateKey,
                    Integer usedParentPay, String payCode, String payType, String nativeMerchantNo, String thirdMerchantNo, String appId, String appSecret,
                    String apiSecret, String remark, Integer payStatus ,Integer deleteStatus, Integer serviceStatus,BigDecimal serviceRatio,
                   Integer dataStatus, Integer interfaceMode) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.accountLevel = accountLevel;
        this.usedParentPay = usedParentPay;
        this.payCode = payCode;
        this.payType = payType;
        this.nativeMerchantNo = nativeMerchantNo;
        this.thirdMerchantNo = thirdMerchantNo;
        this.appId = appId;
        this.appSecret = appSecret;
        this.apiSecret = apiSecret;
        this.remark = remark;
        this.payStatus = payStatus;
        this.deleteStatus = deleteStatus;
        this.alipayPublicKey = alipayPublicKey;
        this.merchantPrivateKey = merchantPrivateKey;
        this.serviceStatus = serviceStatus;
        this.serviceRatio = serviceRatio;
        this.dataStatus = dataStatus;
        this.interfaceMode = interfaceMode;
    }

}

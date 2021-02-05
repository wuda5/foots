package com.cdqckj.gmis.pay.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-06-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("m_wx_app")
@ApiModel(value = "WxApp", description = "")
@AllArgsConstructor
public class WxApp extends TreeEntity<WxApp, String> {

    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号appid
     */
    @ApiModelProperty(value = "微信公众号appid")
    @Length(max = 50, message = "微信公众号appid长度不能超过50")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "微信公众号appid")
    private String appId;

    /**
     * 微信公众号appsecret
     */
    @ApiModelProperty(value = "微信公众号appsecret")
    @Length(max = 50, message = "微信公众号appsecret长度不能超过50")
    @TableField(value = "app_secret", condition = LIKE)
    @Excel(name = "微信公众号appsecret")
    private String appSecret;

    /**
     * 微信公众号token
     */
    @ApiModelProperty(value = "微信公众号token")
    @Length(max = 50, message = "微信公众号token长度不能超过50")
    @TableField(value = "token", condition = LIKE)
    @Excel(name = "微信公众号token")
    private String token;

    /**
     * 微信支付商户号
     */
    @ApiModelProperty(value = "微信支付商户号")
    @Length(max = 50, message = "微信支付商户号长度不能超过50")
    @TableField(value = "pay_mach_id", condition = LIKE)
    @Excel(name = "微信支付商户号")
    private String payMachId;

    /**
     * 微信支付key
     */
    @ApiModelProperty(value = "微信支付key")
    @Length(max = 50, message = "微信支付key长度不能超过50")
    @TableField(value = "pay_key", condition = LIKE)
    @Excel(name = "微信支付key")
    private String payKey;

    /**
     * 微信支付回调url
     */
    @ApiModelProperty(value = "微信支付回调url")
    @Length(max = 200, message = "微信支付回调url长度不能超过200")
    @TableField(value = "notify_url", condition = LIKE)
    @Excel(name = "微信支付回调url")
    private String notifyUrl;

    /**
     * 证书地址
     */
    @ApiModelProperty(value = "证书地址")
    @Length(max = 500, message = "证书地址长度不能超过500")
    @TableField(value = "pay_sslcert_path", condition = LIKE)
    @Excel(name = "证书地址")
    private String paySslcertPath;

    /**
     * 证书密钥
     */
    @ApiModelProperty(value = "证书密钥")
    @Length(max = 500, message = "证书密钥长度不能超过500")
    @TableField(value = "pay_sslcert_key", condition = LIKE)
    @Excel(name = "证书密钥")
    private String paySslcertKey;

    /**
     * 公众号备注
     */
    @ApiModelProperty(value = "公众号备注")
    @Length(max = 100, message = "公众号备注长度不能超过100")
    @TableField(value = "notes", condition = LIKE)
    @Excel(name = "公众号备注")
    private String notes;

    /**
     * 支付客户ip
     */
    @ApiModelProperty(value = "支付客户ip")
    @Length(max = 50, message = "支付客户ip长度不能超过50")
    @TableField(value = "pay_ip", condition = LIKE)
    @Excel(name = "支付客户ip")
    private String payIp;

    /**
     * 功能支持
     */
    @ApiModelProperty(value = "功能支持")
    @Length(max = 100, message = "功能支持长度不能超过100")
    @TableField(value = "fun_id", condition = LIKE)
    @Excel(name = "功能支持")
    private String funId;

    /**
     * APP渠道(1:微信公众平台 , 2:微信开放平台)
     */
    @ApiModelProperty(value = "APP渠道(1:微信公众平台 , 2:微信开放平台)")
    @TableField("channel")
    @Excel(name = "APP渠道(1:微信公众平台 , 2:微信开放平台)")
    private Integer channel;


    @Builder
    public WxApp(String id, 
                    String appId, String appSecret, String token, String payMachId, String payKey, 
                    String notifyUrl, String paySslcertPath, String paySslcertKey, String notes, String payIp, String funId, Integer channel) {
        this.id = id;
        this.appId = appId;
        this.appSecret = appSecret;
        this.token = token;
        this.payMachId = payMachId;
        this.payKey = payKey;
        this.notifyUrl = notifyUrl;
        this.paySslcertPath = paySslcertPath;
        this.paySslcertKey = paySslcertKey;
        this.notes = notes;
        this.payIp = payIp;
        this.funId = funId;
        this.channel = channel;
    }
}

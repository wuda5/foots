package com.cdqckj.gmis.pay.dto;

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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "WxAppSaveDTO", description = "")
public class WxAppSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号appid
     */
    @ApiModelProperty(value = "微信公众号appid")
    @Length(max = 50, message = "微信公众号appid长度不能超过50")
    private String appId;
    /**
     * 微信公众号appsecret
     */
    @ApiModelProperty(value = "微信公众号appsecret")
    @Length(max = 50, message = "微信公众号appsecret长度不能超过50")
    private String appSecret;
    /**
     * 微信公众号token
     */
    @ApiModelProperty(value = "微信公众号token")
    @Length(max = 50, message = "微信公众号token长度不能超过50")
    private String token;
    /**
     * 微信支付商户号
     */
    @ApiModelProperty(value = "微信支付商户号")
    @Length(max = 50, message = "微信支付商户号长度不能超过50")
    private String payMachId;
    /**
     * 微信支付key
     */
    @ApiModelProperty(value = "微信支付key")
    @Length(max = 50, message = "微信支付key长度不能超过50")
    private String payKey;
    /**
     * 微信支付回调url
     */
    @ApiModelProperty(value = "微信支付回调url")
    @Length(max = 200, message = "微信支付回调url长度不能超过200")
    private String notifyUrl;
    /**
     * 证书地址
     */
    @ApiModelProperty(value = "证书地址")
    @Length(max = 500, message = "证书地址长度不能超过500")
    private String paySslcertPath;
    /**
     * 证书密钥
     */
    @ApiModelProperty(value = "证书密钥")
    @Length(max = 500, message = "证书密钥长度不能超过500")
    private String paySslcertKey;
    /**
     * 公众号备注
     */
    @ApiModelProperty(value = "公众号备注")
    @Length(max = 100, message = "公众号备注长度不能超过100")
    private String notes;
    /**
     * 支付客户ip
     */
    @ApiModelProperty(value = "支付客户ip")
    @Length(max = 50, message = "支付客户ip长度不能超过50")
    private String payIp;
    /**
     * 功能支持
     */
    @ApiModelProperty(value = "功能支持")
    @Length(max = 100, message = "功能支持长度不能超过100")
    private String funId;
    /**
     * APP渠道(1:微信公众平台 , 2:微信开放平台)
     */
    @ApiModelProperty(value = "APP渠道(1:微信公众平台 , 2:微信开放平台)")
    private Integer channel;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 255, message = "名称长度不能超过255")
    protected String label;

    @ApiModelProperty(value = "父ID")
    protected String parentId;

    @ApiModelProperty(value = "排序号")
    protected Integer sortValue;
}

package com.cdqckj.gmis.sms.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.sms.enumeration.ProviderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 短信模板
 * </p>
 *
 * @author gmis
 * @since 2019-11-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SmsTemplatepageDTO", description = "短信模板")
public class SmsTemplatePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 模板类型
     */
    @ApiModelProperty(value = "模板类型ID")
    @NotNull(message = "模板类型ID不能为空")
    private Long templateTypeId;

    /**
     * 模板类型名称
     */
    @ApiModelProperty(value = "模板类型名称")
    @Length(max = 255, message = "模板类型名称长度不能超过255")
    private String templateTypeName;

    /**
     * 供应商类型
     * #ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}
     */
    @ApiModelProperty(value = "供应商类型")
    //@NotNull(message = "供应商类型不能为空")
    private ProviderType providerType;
    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    //@NotEmpty(message = "应用ID不能为空")
    @Length(max = 255, message = "应用ID长度不能超过255")
    private String appId;
    /**
     * 应用密码
     */
    @ApiModelProperty(value = "应用密码")
    //@NotEmpty(message = "应用密码不能为空")
    @Length(max = 255, message = "应用密码长度不能超过255")
    private String appSecret;
    /**
     * SMS服务域名
     * 百度、其他厂商会用
     */
    @ApiModelProperty(value = "SMS服务域名")
    @Length(max = 255, message = "SMS服务域名长度不能超过255")
    private String url;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    @Length(max = 255, message = "模板名称长度不能超过255")
    private String name;
    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容")
    @NotEmpty(message = "模板内容不能为空")
    @Length(max = 255, message = "模板内容长度不能超过255")
    private String content;
    /**
     * 模板CODE
     */
    @ApiModelProperty(value = "模板CODE")
    @NotEmpty(message = "模板CODE不能为空")
    @Length(max = 400, message = "模板CODE长度不能超过50")
    private String templateCode;
    /**
     * 模板签名名称
     */
    @ApiModelProperty(value = "模板签名名称")
    @Length(max = 100, message = "模板签名名称长度不能超过100")
    private String signName;
    /**
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述")
    @Length(max = 255, message = "模板描述长度不能超过255")
    private String templateDescribe;

    /**
     * 模板状态
     */
    @ApiModelProperty(value = "模板状态")
    private Integer templateStatus;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private Integer examineStatus;

    /**
     * 模板描述
     */
    @ApiModelProperty(value = "审核未通过原因")
    @Length(max = 255, message = "审核未通过原因长度不能超过255")
    private String reviewReply;

    /**
     * 短信类型
     */
    @ApiModelProperty(value = "短信类型，0表示普通短信, 1表示营销短信。")
    private Integer smsType;

    /**
     * 是否国际短信
     */
    @ApiModelProperty(value = "是否国际/港澳台短信")
    private Integer internatType;
}

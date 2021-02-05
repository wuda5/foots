package com.cdqckj.gmis.sms.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.sms.enumeration.ProviderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_template")
@ApiModel(value = "SmsTemplate", description = "短信模板")
public class SmsTemplate extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板类型ID
     */
    @ApiModelProperty(value = "模板类型ID")
    @NotNull(message = "模板类型ID")
    @TableField("template_type_id")
    @Excel(name = "模板类型ID")
    private Long templateTypeId;

    @ApiModelProperty(value = "模板类型名称")
    @Length(max = 255, message = "模板类型名称长度不能超过255")
    @TableField(value = "template_type_name", condition = LIKE)
    @Excel(name = "模板类型名称")
    private String templateTypeName;

    /**
     * 供应商类型
     * #ProviderType{ALI:OK,阿里云短信;TENCENT:0,腾讯云短信;BAIDU:1000,百度云短信}
     */
    @ApiModelProperty(value = "供应商类型")
    //@NotNull(message = "供应商类型不能为空")
    @TableField("provider_type")
    @Excel(name = "供应商类型", replace = {"阿里云_ALI", "腾讯云_TENCENT", "百度云_BAIDU", "_null"})
    private ProviderType providerType;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    //@NotEmpty(message = "应用ID不能为空")
    @Length(max = 255, message = "应用ID长度不能超过255")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "应用ID")
    private String appId;

    /**
     * 应用密码
     */
    @ApiModelProperty(value = "应用密码")
    //@NotEmpty(message = "应用密码不能为空")
    @Length(max = 255, message = "应用密码长度不能超过255")
    @TableField(value = "app_secret", condition = LIKE)
    private String appSecret;

    /**
     * SMS服务域名
     * 百度、其他厂商会用
     */
    @ApiModelProperty(value = "SMS服务域名")
    @Length(max = 255, message = "SMS服务域名长度不能超过255")
    @TableField(value = "url", condition = LIKE)
    @Excel(name = "SMS服务域名")
    private String url;

    /**
     * 模板编码
     * 用于api发送
     */
    @ApiModelProperty(value = "模板编码")
    @Length(max = 20, message = "模板编码长度不能超过20")
    @TableField(value = "custom_code", condition = LIKE)
    @Excel(name = "模板编码")
    private String customCode;

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    @Length(max = 255, message = "模板名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "模板名称")
    private String name;

    /**
     * 模板内容
     */
    @ApiModelProperty(value = "模板内容")
    @NotEmpty(message = "模板内容不能为空")
    @Length(max = 255, message = "模板内容长度不能超过255")
    @TableField(value = "content", condition = LIKE)
    @Excel(name = "模板内容")
    private String content;

    /**
     * 模板参数
     */
    @ApiModelProperty(value = "模板参数")
    @NotEmpty(message = "模板参数不能为空")
    @Length(max = 255, message = "模板参数长度不能超过255")
    @TableField(value = "template_params", condition = LIKE)
    @Excel(name = "模板参数")
    private String templateParams;

    /**
     * 模板CODE
     */
    @ApiModelProperty(value = "模板CODE")
    @NotEmpty(message = "模板CODE不能为空")
    @Length(max = 400, message = "模板CODE长度不能超过50")
    @TableField(value = "template_code", condition = LIKE)
    @Excel(name = "模板CODE")
    private String templateCode;

    /**
     * 模板签名
     */
    @ApiModelProperty(value = "模板签名名称")
    @Length(max = 100, message = "模板签名名称长度不能超过100")
    @TableField(value = "sign_name", condition = LIKE)
    @Excel(name = "模板签名名称")
    private String signName;

    /**
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述")
    @Length(max = 255, message = "模板描述长度不能超过255")
    @TableField(value = "template_describe", condition = LIKE)
    @Excel(name = "模板描述", width = 30)
    private String templateDescribe;

    /**
     * 模板启用状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("template_status")
    @Excel(name = "状态")
    private Integer templateStatus;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    @TableField("delete_status")
    @Excel(name = "删除标识")
    private Integer deleteStatus;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    @TableField("examine_status")
    @Excel(name = "审核状态")
    private Integer examineStatus;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "短信类型，0表示普通短信, 1表示营销短信。")
    @TableField("sms_type")
    @Excel(name = "短信类型")
    private Integer smsType;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "是否国际/港澳台短信： 0：表示国内短信。 1：表示国际/港澳台短信。")
    @TableField("internat_type")
    @Excel(name = "是否国际")
    private Integer internatType;

    /**
     * 审核未通过原因
     */
    @ApiModelProperty(value = "审核未通过原因")
    @Length(max = 255, message = "审核未通过原因长度不能超过255")
    @TableField(value = "review_reply", condition = LIKE ,updateStrategy = FieldStrategy.IGNORED)
    @Excel(name = "审核未通过原因", width = 30)
    private String reviewReply;

    @Builder
    public SmsTemplate(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                       ProviderType providerType, String appId, String appSecret, String url, String customCode,
                       String name, String content, String templateParams, String templateCode, String signName, String templateDescribe,
                       Long templateTypeId,String templateTypeName,Integer templateStatus,Integer deleteStatus,
                       Integer examineStatus,String reviewReply, Integer smsType,Integer internatType) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.providerType = providerType;
        this.appId = appId;
        this.appSecret = appSecret;
        this.url = url;
        this.customCode = customCode;
        this.name = name;
        this.content = content;
        this.templateParams = templateParams;
        this.templateCode = templateCode;
        this.signName = signName;
        this.templateDescribe = templateDescribe;
        this.templateTypeId = templateTypeId;
        this.templateTypeName = templateTypeName;
        this.templateStatus = templateStatus;
        this.deleteStatus = deleteStatus;
        this.smsType= smsType;
        this.internatType = internatType;
        this.examineStatus = examineStatus;
        this.reviewReply = reviewReply;
    }

}

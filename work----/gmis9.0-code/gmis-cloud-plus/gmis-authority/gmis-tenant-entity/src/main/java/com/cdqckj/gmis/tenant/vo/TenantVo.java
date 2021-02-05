package com.cdqckj.gmis.tenant.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 企业
 * </p>
 *
 * @author gmis
 * @since 2019-10-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TenantVo extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 企业编码
     */
    @ApiModelProperty(value = "企业编码")
    @Length(max = 20, message = "企业编码长度不能超过20")
    @TableField(value = "code", condition = LIKE)
    private String code;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    @Length(max = 255, message = "企业名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    private String name;

    /**
     * 公司简称
     */
    @ApiModelProperty(value = "公司简称")
    @Length(max = 30, message = "公司简称长度不能超过30")
    @TableField(value = "company_abbreviation", condition = LIKE)
    private String companyAbbreviation;

    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    @Length(max = 255, message = "公司地址长度不能超过255")
    @TableField(value = "company_address", condition = LIKE)
    private String companyAddress;

    /**
     * 法人代表
     */
    @ApiModelProperty(value = "法人代表")
    @Length(max = 50, message = "法人代表长度不能超过50")
    @TableField(value = "duty", condition = LIKE)
    private String duty;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 255, message = "联系人长度不能超过255")
    @TableField(value = "contacts", condition = LIKE)
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    @TableField(value = "telphone", condition = LIKE)
    private String telphone;

    /**
     * 客服电话
     */
    @ApiModelProperty(value = "客服电话")
    @Length(max = 20, message = "客服电话长度不能超过20")
    @TableField(value = "consumer_hotline", condition = LIKE)
    private String consumerHotline;

    /**
     * 维修电话
     */
    @ApiModelProperty(value = "维修电话")
    @Length(max = 20, message = "维修电话长度不能超过20")
    @TableField(value = "repair_phone", condition = LIKE)
    private String repairPhone;

    /**
     * 有效期
     * 为空表示永久
     */
    @ApiModelProperty(value = "有效期")
    @TableField("expiration_time")
    private LocalDateTime expirationTime;

    /**
     * 入口访问URL
     */
    @ApiModelProperty(value = "入口访问URL")
    @Length(max = 255, message = "入口访问URL长度不能超过255")
    @TableField(value = "url", condition = LIKE)
    private String url;

    /**
     * 类型
     * #{CREATE:创建;REGISTER:注册}
     */
    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    /**
     * 公司状态
     * #{NORMAL:正常;FORBIDDEN:禁用;WAITING:待审核;REFUSE:拒绝}
     */
    @ApiModelProperty(value = "公司状态")
    @TableField("status")
    private String status;

    /**
     * 1初始中 2成功 3失败
     */
    @ApiModelProperty(value = "1初始中 2成功 3失败")
    @NotNull(message = "1初始中 2成功 3失败不能为空")
    @TableField("init_status")
    private Integer initStatus;

    /**
     * 初始化失败的说明
     */
    @ApiModelProperty(value = "初始化失败的说明")
    @Length(max = 255, message = "初始化失败的说明长度不能超过255")
    @TableField(value = "init_fail_msg", condition = LIKE)
    private String initFailMsg;

    /**
     * 是否内置
     */
    @ApiModelProperty(value = "是否内置")
    @TableField("readonly")
    private Boolean readonly;

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址")
    @Length(max = 255, message = "logo地址长度不能超过255")
    @TableField(value = "logo", condition = LIKE)
    private String logo;

    /**
     * 企业简介
     */
    @ApiModelProperty(value = "企业简介")
    @Length(max = 255, message = "企业简介长度不能超过255")
    @TableField(value = "describe_", condition = LIKE)
    private String describe;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user_id")
    private Long deleteUserId;

    /**
     * 语言类型(1-中文，2-英文)
     */
    @ApiModelProperty(value = "语言类型(1-中文，2-英文)")
    @TableField("lang_type")
    private Integer langType;

}
package com.cdqckj.gmis.tenant.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.enumeration.TenantTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

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
@TableName("d_tenant")
@ApiModel(value = "Tenant", description = "企业")
public class Tenant extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 企业编码
     */
    @ApiModelProperty(value = "企业编码")
    @Length(max = 20, message = "企业编码长度不能超过20")
    @TableField(value = "code", condition = LIKE)
    @Excel(name = "企业编码")
    private String code;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    @Length(max = 255, message = "企业名称长度不能超过255")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "企业名称")
    private String name;

    /**
     * 公司简称
     */
    @ApiModelProperty(value = "公司简称")
    @Length(max = 30, message = "公司简称长度不能超过30")
    @TableField(value = "company_abbreviation", condition = LIKE)
    @Excel(name = "公司简称")
    private String companyAbbreviation;

    /**
     * 公司地址
     */
    @ApiModelProperty(value = "公司地址")
    @Length(max = 255, message = "公司地址长度不能超过255")
    @TableField(value = "company_address", condition = LIKE)
    @Excel(name = "公司地址")
    private String companyAddress;

    /**
     * 法人代表
     */
    @ApiModelProperty(value = "法人代表")
    @Length(max = 50, message = "法人代表长度不能超过50")
    @TableField(value = "duty", condition = LIKE)
    @Excel(name = "法人代表")
    private String duty;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 255, message = "联系人长度不能超过255")
    @TableField(value = "contacts", condition = LIKE)
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 客服电话
     */
    @ApiModelProperty(value = "客服电话")
    @Length(max = 20, message = "客服电话长度不能超过20")
    @TableField(value = "consumer_hotline", condition = LIKE)
    @Excel(name = "客服电话")
    private String consumerHotline;

    /**
     * 维修电话
     */
    @ApiModelProperty(value = "维修电话")
    @Length(max = 20, message = "维修电话长度不能超过20")
    @TableField(value = "repair_phone", condition = LIKE)
    @Excel(name = "维修电话")
    private String repairPhone;

    /**
     * 有效期
     * 为空表示永久
     */
    @ApiModelProperty(value = "有效期")
    @TableField("expiration_time")
    @Excel(name = "有效期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime expirationTime;

    /**
     * 入口访问URL
     */
    @ApiModelProperty(value = "入口访问URL")
    @Length(max = 255, message = "入口访问URL长度不能超过255")
    @TableField(value = "url", condition = LIKE)
    @Excel(name = "入口访问URL")
    private String url;

    /**
     * 类型
     * #{CREATE:创建;REGISTER:注册}
     */
    @ApiModelProperty(value = "类型")
    @TableField("type")
    @Excel(name = "类型", replace = {"创建_CREATE", "注册_REGISTER", "_null"})
    private TenantTypeEnum type;

    /**
     * 公司状态
     * #{NORMAL:正常;FORBIDDEN:禁用;WAITING:待审核;REFUSE:拒绝}
     */
    @ApiModelProperty(value = "公司状态")
    @TableField("status")
    @Excel(name = "公司状态", replace = {"正常_NORMAL", "禁用_FORBIDDEN", "待审核_WAITING", "拒绝_REFUSE", "_null"})
    private TenantStatusEnum status;

    /**
     * 1初始中 2成功 3失败
     */
    @ApiModelProperty(value = "1初始中 2成功 3失败")
    @NotNull(message = "1初始中 2成功 3失败不能为空")
    @TableField("init_status")
    @Excel(name = "1初始中 2成功 3失败")
    private Integer initStatus;

    /**
     * 初始化失败的说明
     */
    @ApiModelProperty(value = "初始化失败的说明")
    @Length(max = 255, message = "初始化失败的说明长度不能超过255")
    @TableField(value = "init_fail_msg", condition = LIKE)
    @Excel(name = "初始化失败的说明")
    private String initFailMsg;

    /**
     * 是否内置
     */
    @ApiModelProperty(value = "是否内置")
    @TableField("readonly")
    @Excel(name = "是否内置", replace = {"是_true", "否_false", "_null"})
    private Boolean readonly;

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址")
    @Length(max = 255, message = "logo地址长度不能超过255")
    @TableField(value = "logo", condition = LIKE)
    @Excel(name = "logo地址")
    private String logo;

    /**
     * 企业简介
     */
    @ApiModelProperty(value = "企业简介")
    @Length(max = 255, message = "企业简介长度不能超过255")
    @TableField(value = "describe_", condition = LIKE)
    @Excel(name = "企业简介")
    private String describe;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user_id")
    @Excel(name = "删除人")
    private Long deleteUserId;

    /**
     * 语言类型(1-中文，2-英文)
     */
    @ApiModelProperty(value = "语言类型(1-中文，2-英文)")
    @TableField("lang_type")
    @Excel(name = "语言类型(1-中文，2-英文)")
    private Integer langType;


    @Builder
    public Tenant(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                  String code, String name, String companyAbbreviation, String companyAddress, String duty,
                  String contacts, String telphone, String consumerHotline, String repairPhone, LocalDateTime expirationTime, String url,
                  TenantTypeEnum type, TenantStatusEnum status, Integer initStatus, String initFailMsg, Boolean readonly, String logo,
                  String describe, LocalDateTime deleteTime, Long deleteUserId, Integer langType) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.code = code;
        this.name = name;
        this.companyAbbreviation = companyAbbreviation;
        this.companyAddress = companyAddress;
        this.duty = duty;
        this.contacts = contacts;
        this.telphone = telphone;
        this.consumerHotline = consumerHotline;
        this.repairPhone = repairPhone;
        this.expirationTime = expirationTime;
        this.url = url;
        this.type = type;
        this.status = status;
        this.initStatus = initStatus;
        this.initFailMsg = initFailMsg;
        this.readonly = readonly;
        this.logo = logo;
        this.describe = describe;
        this.deleteTime = deleteTime;
        this.deleteUserId = deleteUserId;
        this.langType = langType;
    }
}
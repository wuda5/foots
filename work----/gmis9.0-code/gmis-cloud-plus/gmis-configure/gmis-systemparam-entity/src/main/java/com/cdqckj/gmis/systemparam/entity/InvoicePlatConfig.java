package com.cdqckj.gmis.systemparam.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigKeyStore;
import com.cdqckj.gmis.systemparam.enumeration.InvoicePlatEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 电子发票服务平台配置表
 * </p>
 *
 * @author gmis
 * @since 2020-11-17
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pz_invoice_plat_config")
@ApiModel(value = "InvoicePlatConfig", description = "电子发票服务平台配置表")
@AllArgsConstructor
public class InvoicePlatConfig extends Entity<Long> {

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
     * 电子发票服务平台编码
     */
    @ApiModelProperty(value = "电子发票服务平台编码")
    @Length(max = 30, message = "电子发票服务平台编码长度不能超过30")
    @TableField(value = "plat_code", condition = LIKE)
    @Excel(name = "电子发票服务平台编码")
    private String platCode;

    /**
     * 电子发票平台名称
     */
    @ApiModelProperty(value = "电子发票平台名称")
    @Length(max = 100, message = "电子发票平台名称长度不能超过100")
    @TableField(value = "plat_name", condition = LIKE)
    @Excel(name = "电子发票平台名称")
    private String platName;

    /**
     * 应用标识：发票服务平台授权的应用唯一标识
     */
    @ApiModelProperty(value = "应用标识：发票服务平台授权的应用唯一标识")
    @Length(max = 100, message = "应用标识：发票服务平台授权的应用唯一标识长度不能超过100")
    @TableField(value = "open_id", condition = LIKE)
    @Excel(name = "应用标识：发票服务平台授权的应用唯一标识")
    private String openId;

    /**
     * 应用密码：发票平台授权的密码
     */
    @ApiModelProperty(value = "应用密码：发票平台授权的密码")
    @Length(max = 100, message = "应用密码：发票平台授权的密码长度不能超过100")
    @TableField(value = "app_secret", condition = LIKE)
    @Excel(name = "应用密码：发票平台授权的密码")
    private String appSecret;

    /**
     * 其它参数：使用json格式保存
     */
    @ApiModelProperty(value = "其它参数：使用json格式保存")
    @Length(max = 500, message = "其它参数：使用json格式保存长度不能超过500")
    @TableField(value = "other_param", condition = LIKE)
    @Excel(name = "其它参数：使用json格式保存")
    private String otherParam;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    @Length(max = 100, message = "请求路径长度不能超过100")
    @TableField(value = "url", condition = LIKE)
    @Excel(name = "请求路径")
    private String url;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 20, message = "联系人长度不能超过20")
    @TableField(value = "contacts", condition = LIKE)
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    @TableField(value = "telephone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telephone;

    /**
     * 附件名称
     */
    @ApiModelProperty(value = "附件名称")
    @Length(max = 30, message = "附件名称长度不能超过30")
    @TableField(value = "fileName", condition = LIKE)
    @Excel(name = "附件名称")
    private String fileName;

    /**
     * 附件保存地址
     */
    @ApiModelProperty(value = "附件保存地址")
    @Length(max = 300, message = "附件保存地址长度不能超过100")
    @TableField(value = "fileUrl", condition = LIKE)
    @Excel(name = "附件保存地址")
    private String fileUrl;

    /**
     * 是否启用状态 0：未启用, 1:启用
     */
    @ApiModelProperty(value = "是否启用状态 0：未启用, 1:启用")
    @TableField("data_status")
    @Excel(name = "是否启用状态 0：未启用, 1:启用")
    private Integer dataStatus;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("delete_status")
    @Excel(name = "状态")
    private Integer deleteStatus;

    @ApiModelProperty(value = "证书信息：当配置平台选择瑞宏网时填写")
    @TableField(exist = false)
    private InvoicePlatConfigKeyStore keyStore;

    public InvoicePlatConfigKeyStore getKeyStore() {
        if (InvoicePlatEnum.RUI_HONG.name().equals(platCode)
                && !StringUtils.isEmpty(otherParam) && JSONUtil.isJson(otherParam)) {
            return JSONUtil.toBean(otherParam, InvoicePlatConfigKeyStore.class);
        }
        return keyStore;
    }


    @Builder
    public InvoicePlatConfig(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                             String companyCode, String companyName, Long orgId, String orgName, String platCode,
                             String platName, String openId, String appSecret, String otherParam, String url, String contacts,
                             String telephone, String fileName, String fileUrl, Integer dataStatus, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.platCode = platCode;
        this.platName = platName;
        this.openId = openId;
        this.appSecret = appSecret;
        this.otherParam = otherParam;
        this.url = url;
        this.contacts = contacts;
        this.telephone = telephone;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
    }

}

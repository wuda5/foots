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
 * 开票参数配置
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
@TableName("pz_invoice_param")
@ApiModel(value = "InvoiceParam", description = "开票参数配置")
@AllArgsConstructor
public class InvoiceParam extends Entity<Long> {

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
     * 销方名称
     */
    @ApiModelProperty(value = "销方名称")
    @Length(max = 100, message = "销方名称长度不能超过100")
    @TableField(value = "name", condition = LIKE)
    @Excel(name = "销方名称")
    private String name;

    /**
     * 销方纳税人编号
     */
    @ApiModelProperty(value = "销方纳税人编号")
    @Length(max = 100, message = "销方纳税人编号长度不能超过100")
    @TableField(value = "taxpayer_no", condition = LIKE)
    @Excel(name = "销方纳税人编号")
    private String taxpayerNo;

    /**
     * 销方地址
     */
    @ApiModelProperty(value = "销方地址")
    @Length(max = 300, message = "销方地址长度不能超过300")
    @TableField(value = "address", condition = LIKE)
    @Excel(name = "销方地址")
    private String address;

    /**
     * 销方电话
     */
    @ApiModelProperty(value = "销方电话")
    @Length(max = 20, message = "销方电话长度不能超过20")
    @TableField(value = "telephone", condition = LIKE)
    @Excel(name = "销方电话")
    private String telephone;

    /**
     * 销方开户行名称
     */
    @ApiModelProperty(value = "销方开户行名称")
    @Length(max = 100, message = "销方开户行名称长度不能超过100")
    @TableField(value = "bank_name", condition = LIKE)
    @Excel(name = "销方开户行名称")
    private String bankName;

    /**
     * 销方银行账户
     */
    @ApiModelProperty(value = "销方银行账户")
    @Length(max = 60, message = "销方银行账户长度不能超过60")
    @TableField(value = "bank_account", condition = LIKE)
    @Excel(name = "销方银行账户")
    private String bankAccount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("invoice_status")
    @Excel(name = "状态")
    private Integer invoiceStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 300, message = "备注长度不能超过300")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 1：存在 0：删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public InvoiceParam(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Integer accountLevel, 
                    String name, String taxpayerNo, String address, String telephone, String bankName, String bankAccount, 
                    Integer invoiceStatus, String remark ,Integer deleteStatus) {
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
        this.name = name;
        this.taxpayerNo = taxpayerNo;
        this.address = address;
        this.telephone = telephone;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.invoiceStatus = invoiceStatus;
        this.remark = remark;
        this.deleteStatus = deleteStatus;
    }

}

package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
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
 * 银行代扣记录
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_bank_withhold_record")
@ApiModel(value = "BankWithholdRecord", description = "银行代扣记录")
@AllArgsConstructor
public class BankWithholdRecord extends Entity<Long> {

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 气表类型id
     */
    @ApiModelProperty(value = "气表类型id")
    @Length(max = 32, message = "气表类型id长度不能超过32")
    @TableField(value = "gas_meter_type_id", condition = LIKE)
    @Excel(name = "气表类型id")
    private String gasMeterTypeId;

    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "气表类型名称")
    private String gasMeterTypeName;

    /**
     * 气表安装地址
     */
    @ApiModelProperty(value = "气表安装地址")
    @Length(max = 200, message = "气表安装地址长度不能超过200")
    @TableField(value = "gas_meter_address", condition = LIKE)
    @Excel(name = "气表安装地址")
    private String gasMeterAddress;

    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    @Length(max = 30, message = "持卡人长度不能超过30")
    @TableField(value = "cardholder", condition = LIKE)
    @Excel(name = "持卡人")
    private String cardholder;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 100, message = "银行账号长度不能超过100")
    @TableField(value = "bank_account", condition = LIKE)
    @Excel(name = "银行账号")
    private String bankAccount;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("amount")
    @Excel(name = "金额")
    private BigDecimal amount;

    /**
     * 导入状态
     */
    @ApiModelProperty(value = "导入状态")
    @TableField("import_status")
    @Excel(name = "导入状态")
    private Integer importStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public BankWithholdRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasMeterCode, String gasMeterName, String gasMeterTypeId, String gasMeterTypeName, String gasMeterAddress, 
                    String cardholder, String bankAccount, BigDecimal amount, Integer importStatus, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasMeterCode = gasMeterCode;
        this.gasMeterName = gasMeterName;
        this.gasMeterTypeId = gasMeterTypeId;
        this.gasMeterTypeName = gasMeterTypeName;
        this.gasMeterAddress = gasMeterAddress;
        this.cardholder = cardholder;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.importStatus = importStatus;
        this.remark = remark;
    }

}

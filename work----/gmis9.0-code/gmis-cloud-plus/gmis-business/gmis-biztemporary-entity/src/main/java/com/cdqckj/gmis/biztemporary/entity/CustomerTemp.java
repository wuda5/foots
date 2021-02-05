package com.cdqckj.gmis.biztemporary.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
 * 
 * </p>
 *
 * @author songyz
 * @since 2021-01-22
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_customer_temp")
@ApiModel(value = "CustomerTemp", description = "")
@AllArgsConstructor
public class CustomerTemp extends Entity<Long> {

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
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
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
    @Length(max = 30, message = "客户编号长度不能超过30")
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
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型ID")
    @Length(max = 32, message = "客户类型ID长度不能超过32")
    @TableField(value = "customer_type_code", condition = LIKE)
    @Excel(name = "客户类型ID")
    private String customerTypeCode;

    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    @Length(max = 100, message = "居民 商福 工业 低保长度不能超过100")
    @TableField(value = "customer_type_name", condition = LIKE)
    @Excel(name = "居民 商福 工业 低保")
    private String customerTypeName;

    /**
     * 性别  MAN表示男 ，WOMEN表示女
     */
    @ApiModelProperty(value = "性别  MAN表示男 ，WOMEN表示女")
    @Length(max = 10, message = "性别  MAN表示男 ，WOMEN表示女长度不能超过10")
    @TableField(value = "sex", condition = LIKE)
    @Excel(name = "性别  MAN表示男 ，WOMEN表示女")
    private String sex;

    /**
     * 证件类型ID
     */
    @ApiModelProperty(value = "证件类型ID")
    @Length(max = 10, message = "证件类型ID长度不能超过10")
    @TableField(value = "id_type_code", condition = LIKE)
    @Excel(name = "证件类型ID")
    private String idTypeCode;

    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    @Length(max = 100, message = "证件类型名称长度不能超过100")
    @TableField(value = "id_type_name", condition = LIKE)
    @Excel(name = "证件类型名称")
    private String idTypeName;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 30, message = "证件号码长度不能超过30")
    @TableField(value = "id_number", condition = LIKE)
    @Excel(name = "证件号码")
    private String idNumber;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    @Length(max = 20, message = "身份证号码长度不能超过20")
    @TableField(value = "id_card", condition = LIKE)
    @Excel(name = "身份证号码")
    private String idCard;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 12, message = "联系电话长度不能超过12")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 家庭住址/单位地址
     */
    @ApiModelProperty(value = "家庭住址/单位地址")
    @Length(max = 100, message = "家庭住址/单位地址长度不能超过100")
    @TableField(value = "contact_address", condition = LIKE)
    @Excel(name = "家庭住址/单位地址")
    private String contactAddress;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "")
    private String remark;

    /**
     * 客户状态 0.预建档  1. 在用   2.销户
     */
    @ApiModelProperty(value = "客户状态 0.预建档  1. 在用   2.销户")
    @TableField("customer_status")
    @Excel(name = "客户状态 0.预建档  1. 在用   2.销户")
    private Integer customerStatus;

    /**
     * 预存总额
     */
    @ApiModelProperty(value = "预存总额")
    @TableField("pre_store_money")
    @Excel(name = "预存总额")
    private BigDecimal preStoreMoney;

    /**
     * 预存次数
     */
    @ApiModelProperty(value = "预存次数")
    @TableField("pre_store_count")
    @Excel(name = "预存次数")
    private Integer preStoreCount;

    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    @TableField("balance")
    @Excel(name = "账户余额")
    private BigDecimal balance;

    /**
     * 代扣合同编号
     */
    @ApiModelProperty(value = "代扣合同编号")
    @Length(max = 30, message = "代扣合同编号长度不能超过30")
    @TableField(value = "contract_number", condition = LIKE)
    @Excel(name = "代扣合同编号")
    private String contractNumber;

    /**
     * 代扣签约状态
     */
    @ApiModelProperty(value = "代扣签约状态")
    @TableField("contract_status")
    @Excel(name = "代扣签约状态", replace = {"是_true", "否_false", "_null"})
    private Boolean contractStatus;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    @Length(max = 50, message = "开户行长度不能超过50")
    @TableField(value = "bank", condition = LIKE)
    @Excel(name = "开户行")
    private String bank;

    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    @Length(max = 100, message = "持卡人长度不能超过100")
    @TableField(value = "cardholder", condition = LIKE)
    @Excel(name = "持卡人")
    private String cardholder;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 30, message = "银行账号长度不能超过30")
    @TableField(value = "bank_account", condition = LIKE)
    @Excel(name = "银行账号")
    private String bankAccount;

    /**
     * 保险编号
     */
    @ApiModelProperty(value = "保险编号")
    @Length(max = 60, message = "保险编号长度不能超过60")
    @TableField(value = "insurance_no", condition = LIKE)
    @Excel(name = "保险编号")
    private String insuranceNo;

    /**
     * 保险到期时间
     */
    @ApiModelProperty(value = "保险到期时间")
    @TableField("insurance_end_time")
    @Excel(name = "保险到期时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime insuranceEndTime;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @Length(max = 100, message = "发票抬头长度不能超过100")
    @TableField(value = "invoice_title", condition = LIKE)
    @Excel(name = "发票抬头")
    private String invoiceTitle;

    /**
     * 发票纳税人识别
     */
    @ApiModelProperty(value = "发票纳税人识别")
    @Length(max = 30, message = "发票纳税人识别长度不能超过30")
    @TableField(value = "TIN", condition = LIKE)
    @Excel(name = "发票纳税人识别")
    private String tin;

    /**
     * 发票开户行及账号
     */
    @ApiModelProperty(value = "发票开户行及账号")
    @Length(max = 30, message = "发票开户行及账号长度不能超过30")
    @TableField(value = "invoice_bank_account", condition = LIKE)
    @Excel(name = "发票开户行及账号")
    private String invoiceBankAccount;

    /**
     * 发票公司地址
     */
    @ApiModelProperty(value = "发票公司地址")
    @Length(max = 100, message = "发票公司地址长度不能超过100")
    @TableField(value = "invoice_address", condition = LIKE)
    @Excel(name = "发票公司地址")
    private String invoiceAddress;

    /**
     * 发票电子邮箱
     */
    @ApiModelProperty(value = "发票电子邮箱")
    @Length(max = 50, message = "发票电子邮箱长度不能超过50")
    @TableField(value = "invoice_email", condition = LIKE)
    @Excel(name = "发票电子邮箱")
    private String invoiceEmail;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    @TableField("delete_status")
    @Excel(name = "逻辑删除")
    private Integer deleteStatus;

    /**
     * 黑名单状态
     */
    @ApiModelProperty(value = "黑名单状态")
    @TableField("black_status")
    @Excel(name = "黑名单状态")
    private Integer blackStatus;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user")
    @Excel(name = "删除人")
    private Long deleteUser;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;


    @Builder
    public CustomerTemp(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String customerTypeCode, String customerTypeName, String sex, String idTypeCode, String idTypeName, 
                    String idNumber, String idCard, String telphone, String contactAddress, String remark, Integer customerStatus, 
                    BigDecimal preStoreMoney, Integer preStoreCount, BigDecimal balance, String contractNumber, Boolean contractStatus, String bank, 
                    String cardholder, String bankAccount, String insuranceNo, LocalDateTime insuranceEndTime, String invoiceTitle, String tin, 
                    String invoiceBankAccount, String invoiceAddress, String invoiceEmail, Integer deleteStatus, Integer blackStatus, Long deleteUser, LocalDateTime deleteTime) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerTypeCode = customerTypeCode;
        this.customerTypeName = customerTypeName;
        this.sex = sex;
        this.idTypeCode = idTypeCode;
        this.idTypeName = idTypeName;
        this.idNumber = idNumber;
        this.idCard = idCard;
        this.telphone = telphone;
        this.contactAddress = contactAddress;
        this.remark = remark;
        this.customerStatus = customerStatus;
        this.preStoreMoney = preStoreMoney;
        this.preStoreCount = preStoreCount;
        this.balance = balance;
        this.contractNumber = contractNumber;
        this.contractStatus = contractStatus;
        this.bank = bank;
        this.cardholder = cardholder;
        this.bankAccount = bankAccount;
        this.insuranceNo = insuranceNo;
        this.insuranceEndTime = insuranceEndTime;
        this.invoiceTitle = invoiceTitle;
        this.tin = tin;
        this.invoiceBankAccount = invoiceBankAccount;
        this.invoiceAddress = invoiceAddress;
        this.invoiceEmail = invoiceEmail;
        this.deleteStatus = deleteStatus;
        this.blackStatus = blackStatus;
        this.deleteUser = deleteUser;
        this.deleteTime = deleteTime;
    }

}

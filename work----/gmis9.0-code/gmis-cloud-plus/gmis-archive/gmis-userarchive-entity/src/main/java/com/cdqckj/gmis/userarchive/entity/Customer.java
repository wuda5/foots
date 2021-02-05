package com.cdqckj.gmis.userarchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.List;

import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.common.domain.excell.RowFailDescribe;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import com.google.common.base.Joiner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-02
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_customer")
@ApiModel(value = "Customer", description = "")
@AllArgsConstructor
public class Customer extends Entity<Long>  {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
/*    @Excel(name = "公司编码")
    @ExcelSelf(name = "公司编码,code")*/
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
/*    @Excel(name = "公司名称")
    @ExcelSelf(name = "公司名称,companyName")*/
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
/*    @Excel(name = "组织名称")
    @ExcelSelf(name = "组织名称,orgName")*/
    private String orgName;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 30, message = "客户编号长度不能超过30")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 225, message = "客户名称长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称(必填)")
    @ExcelSelf(name = "客户名称(必填),cutomerName")
    private String customerName;

    /**
     * 客户类型ID
     */
    @ApiModelProperty(value = "客户类型Code")
    @TableField("customer_type_code")
    private String customerTypeCode;

    /**
     * 居民 商福 工业 低保
     */
    @ApiModelProperty(value = "居民 商福 工业 低保")
    @Length(max = 100, message = "居民 商福 工业 低保长度不能超过100")
    @TableField(value = "customer_type_name", condition = LIKE)
    @Excel(name = "客户类型名称(必填)")
    @ExcelSelf(name = "客户类型名称(必填),customerTypeName")
    private String customerTypeName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("sex")
    @Excel(name = "性别(必填)")
    @ExcelSelf(name = "性别(必填),sex")
    private String sex;

    /**
     * 证件类型ID
     */
    @ApiModelProperty(value = "证件类型编号")
    @Length(max = 10, message = "证件类型ID长度不能超过10")
    @TableField(value = "id_type_code", condition = LIKE)
    private String idTypeCode;



    @ApiModelProperty(value = "身份证号码")
    @Excel(name = "身份证号码(必填)")
    @Length(max = 30, message = "身份证长度不能超过16")
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话(必填)")
    @ExcelSelf(name = "联系电话(必填),telphone")
    private String telphone;
    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    @Length(max = 100, message = "证件类型名称长度不能超过100")
    @TableField(value = "id_type_name", condition = LIKE)
   /* @Excel(name = "证件类型名称")
    @ExcelSelf(name = "证件类型名称,idTypeName")*/
    private String idTypeName;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 18, message = "证件号码长度不能超过38")
    @TableField(value = "id_number", condition = LIKE)
    /*@Excel(name = "证件号码")
    @ExcelSelf(name = "证件号码,idNumber")*/
    private String idNumber;



    /**
     * 家庭住址/单位地址
     */
    @ApiModelProperty(value = "家庭住址/单位地址")
    @Length(max = 100, message = "家庭住址/单位地址长度不能超过100")
    @TableField(value = "contact_address", condition = LIKE)
    @Excel(name = "家庭住址/单位地址")
    @ExcelSelf(name = "家庭住址/单位地址,contactAddress")
    private String contactAddress;



    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "长度不能超过100")
    @Excel(name = "备注")
    @TableField(value = "remark", condition = LIKE)
    private String remark;

    /**
     * 客户状态 预建档 有效 销户
     */
    @ApiModelProperty(value = "客户状态 预建档 有效 销户")
    @TableField("customer_status")
    private Integer customerStatus;

    /**
     * 预存总额
     */
    @ApiModelProperty(value = "预存总额")
    @TableField("pre_store_money")
    private BigDecimal preStoreMoney;

    /**
     * 预存次数
     */
    @ApiModelProperty(value = "预存次数")
    @TableField("pre_store_count")
    private Integer preStoreCount;

    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    @TableField("balance")
    private BigDecimal balance;

    /**
     * 代扣合同编号
     */
    @ApiModelProperty(value = "代扣合同编号")
    @Length(max = 30, message = "代扣合同编号长度不能超过30")
    @TableField(value = "contract_number", condition = LIKE)
    private String contractNumber;

    /**
     * 代扣签约状态
     */
    @ApiModelProperty(value = "代扣签约状态")
    @TableField("contract_status")
    private Boolean contractStatus;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    @Length(max = 50, message = "开户行长度不能超过50")
    @TableField(value = "bank", condition = LIKE)
    private String bank;

    /**
     * 持卡人
     */
    @ApiModelProperty(value = "持卡人")
    @Length(max = 100, message = "持卡人长度不能超过100")
    @TableField(value = "cardholder", condition = LIKE)
    private String cardholder;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 30, message = "银行账号长度不能超过30")
    @TableField(value = "bank_account", condition = LIKE)
    private String bankAccount;

    /**
     * 保险编号
     */
    @ApiModelProperty(value = "保险编号")
    @Length(max = 60, message = "保险编号长度不能超过60")
    @TableField(value = "insurance_no", condition = LIKE)
    private String insuranceNo;

    /**
     * 保险到期时间
     */
    @ApiModelProperty(value = "保险到期时间")
    @TableField("insurance_end_time")
    private LocalDateTime insuranceEndTime;

    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @Length(max = 100, message = "发票抬头长度不能超过100")
    @TableField(value = "invoice_title", condition = LIKE)
    private String invoiceTitle;

    /**
     * 发票纳税人识别
     */
    @ApiModelProperty(value = "发票纳税人识别")
    @Length(max = 30, message = "发票纳税人识别长度不能超过30")
    @TableField(value = "TIN", condition = LIKE)
    private String tin;

    /**
     * 发票开户行及账号
     */
    @ApiModelProperty(value = "发票开户行及账号")
    @Length(max = 30, message = "发票开户行及账号长度不能超过30")
    @TableField(value = "invoice_bank_account", condition = LIKE)
    private String invoiceBankAccount;

    /**
     * 发票公司地址
     */
    @ApiModelProperty(value = "发票公司地址")
    @Length(max = 100, message = "发票公司地址长度不能超过100")
    @TableField(value = "invoice_address", condition = LIKE)
    private String invoiceAddress;

    /**
     * 发票电子邮箱
     */
    @ApiModelProperty(value = "发票电子邮箱")
    @Length(max = 50, message = "发票电子邮箱长度不能超过50")
    @TableField(value = "invoice_email", condition = LIKE)
    private String invoiceEmail;

    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    @TableField("delete_user")
    private Long deleteUser;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    @ApiModelProperty(value = "黑名单状态")
    @TableField("black_status")
    private Integer blackStatus;

    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    private Integer deleteStatus;




    @Builder
    public Customer(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName,String idCard,
                    String customerCode, String customerName, String customerTypeCode, String customerTypeName, String sex,
                    String idTypeCode, String idTypeName, String idNumber, String telphone, String contactAddress, String remark,
                    Integer customerStatus,  BigDecimal preStoreMoney, Integer preStoreCount, BigDecimal balance, String contractNumber,
                    Boolean contractStatus, String bank, String cardholder, String bankAccount, String insuranceNo, LocalDateTime insuranceEndTime, 
                    String invoiceTitle, String tin, String invoiceBankAccount, String invoiceAddress, String invoiceEmail, Long deleteUser, LocalDateTime deleteTime,Integer blackstatus,Integer deleteStatus) {

       this.id = id;
        this.createTime = createTime;
        this.idCard=idCard;
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
        this.deleteUser = deleteUser;
        this.deleteTime = deleteTime;
        this.blackStatus = blackstatus;
        this.deleteStatus=deleteStatus;
    }


}

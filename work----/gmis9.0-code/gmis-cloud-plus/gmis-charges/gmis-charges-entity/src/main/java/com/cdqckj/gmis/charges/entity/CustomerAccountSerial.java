package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 账户流水
 * </p>
 *
 * @author tp
 * @since 2020-09-29
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_customer_account_serial")
@ApiModel(value = "CustomerAccountSerial", description = "账户流水")
@AllArgsConstructor
public class CustomerAccountSerial extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
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
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    @TableField(value = "business_hall_name", condition = LIKE)
    @Excel(name = "营业厅名称")
    private String businessHallName;

    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    @Length(max = 32, message = "账户号长度不能超过32")
    @TableField(value = "account_code", condition = LIKE)
    @Excel(name = "账户号")
    private String accountCode;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = LIKE)
    @Excel(name = "客户编号")
    private String customerCode;


    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_meter_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasMeterCode;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    @Length(max = 32, message = "流水号长度不能超过32")
    @TableField(value = "serial_no", condition = LIKE)
    @Excel(name = "流水号")
    private String serialNo;

    /**
     * 记费记录号( 根据各个场景记录不同的编号)
     *             例如：收费扣款或者收费预存存放 对应单号。
     */
    @ApiModelProperty(value = "记费记录号( 根据各个场景记录不同的编号)")
    @Length(max = 32, message = "记费记录号( 根据各个场景记录不同的编号)长度不能超过32")
    @TableField(value = "bill_no", condition = LIKE)
    @Excel(name = "记费记录号( 根据各个场景记录不同的编号)")
    private String billNo;

    /**
     * 收入支出类型：
     *             预存抵扣
     *             预存
     *             抵扣退费
     *             预存退费
     *             
     */
    @ApiModelProperty(value = "收入支出类型：")
    @Length(max = 32, message = "收入支出类型：长度不能超过32")
    @TableField(value = "bill_type", condition = LIKE)
    @Excel(name = "收入支出类型：")
    private String billType;

    /**
     * 记账金额
     */
    @ApiModelProperty(value = "记账金额")
    @TableField("book_money")
    @Excel(name = "记账金额")
    private BigDecimal bookMoney;

    /**
     * 记账前余额
     */
    @ApiModelProperty(value = "记账前余额")
    @TableField("book_pre_money")
    @Excel(name = "记账前余额")
    private BigDecimal bookPreMoney;

    /**
     * 记账后余额
     */
    @ApiModelProperty(value = "记账后余额")
    @TableField("book_after_money")
    @Excel(name = "记账后余额")
    private BigDecimal bookAfterMoney;

    /**
     * 赠送记账金额
     */
    @ApiModelProperty(value = "赠送记账金额")
    @TableField("give_book_money")
    @Excel(name = "赠送记账金额")
    private BigDecimal giveBookMoney;

    /**
     * 赠送记账前余额
     */
    @ApiModelProperty(value = "赠送记账前余额")
    @TableField("give_book_pre_money")
    @Excel(name = "赠送记账前余额")
    private BigDecimal giveBookPreMoney;

    /**
     * 赠送记账后余额
     */
    @ApiModelProperty(value = "赠送记账后余额")
    @TableField("give_book_after_money")
    @Excel(name = "赠送记账后余额")
    private BigDecimal giveBookAfterMoney;

    /**
     * 流水说明
     */
    @ApiModelProperty(value = "流水说明")
    @Length(max = 400, message = "流水说明长度不能超过400")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "流水说明")
    private String remark;


    @Builder
    public CustomerAccountSerial(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String accountCode, String customerCode, String serialNo, String billNo, String billType, 
                    BigDecimal bookMoney, BigDecimal bookPreMoney, BigDecimal bookAfterMoney, BigDecimal giveBookMoney, BigDecimal giveBookPreMoney, BigDecimal giveBookAfterMoney, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.accountCode = accountCode;
        this.customerCode = customerCode;
        this.serialNo = serialNo;
        this.billNo = billNo;
        this.billType = billType;
        this.bookMoney = bookMoney;
        this.bookPreMoney = bookPreMoney;
        this.bookAfterMoney = bookAfterMoney;
        this.giveBookMoney = giveBookMoney;
        this.giveBookPreMoney = giveBookPreMoney;
        this.giveBookAfterMoney = giveBookAfterMoney;
        this.remark = remark;
    }

}

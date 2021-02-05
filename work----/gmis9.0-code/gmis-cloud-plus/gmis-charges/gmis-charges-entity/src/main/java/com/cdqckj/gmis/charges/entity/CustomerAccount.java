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
 * 账户表
 * </p>
 *
 * @author tp
 * @since 2020-08-25
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_customer_account")
@ApiModel(value = "CustomerAccount", description = "账户表")
@AllArgsConstructor
public class CustomerAccount extends Entity<Long> {

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
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 32, message = "客户名称长度不能超过32")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 账户金额
     */
    @ApiModelProperty(value = "账户金额")
    @TableField("account_money")
    @Excel(name = "账户金额")
    private BigDecimal accountMoney;

    /**
     * 赠送金额
     */
    @ApiModelProperty(value = "赠送金额")
    @TableField("give_money")
    @Excel(name = "赠送金额")
    private BigDecimal giveMoney;

    /**
     * 结算锁
     *             1 结算中已锁
     *             0 未锁定
     */
    @ApiModelProperty(value = "结算锁")
    @TableField("settlement_lock")
    @Excel(name = "结算锁")
    private Integer settlementLock;

    /**
     * 账户状态
     *             0 未激活
     *             1 激活
     *             2 冻结
     */
    @ApiModelProperty(value = "账户状态")
    @TableField("status")
    @Excel(name = "账户状态")
    private Integer status;

    /**
     * 删除状态
     *             1 删除
     *             0 正常
     */
    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    @Excel(name = "删除状态")
    private Integer deleteStatus;


    @Builder
    public CustomerAccount(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                    String businessHallName, String accountCode, String customerCode, String customerName, BigDecimal accountMoney, BigDecimal giveMoney, 
                    Integer settlementLock, Integer status, Integer deleteStatus) {
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
        this.customerName = customerName;
        this.accountMoney = accountMoney;
        this.giveMoney = giveMoney;
        this.settlementLock = settlementLock;
        this.status = status;
        this.deleteStatus = deleteStatus;
    }

}

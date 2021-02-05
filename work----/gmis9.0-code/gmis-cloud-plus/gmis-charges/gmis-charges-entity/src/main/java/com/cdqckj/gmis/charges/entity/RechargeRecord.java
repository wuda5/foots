package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
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
 * 充值记录
 * </p>
 *
 * @author tp
 * @since 2020-09-07
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_recharge_record")
@ApiModel(value = "RechargeRecord", description = "充值记录")
@AllArgsConstructor
public class RechargeRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = SqlCondition.EQUAL)
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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    @TableField(value = "customer_code", condition = SqlCondition.EQUAL)
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
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = SqlCondition.EQUAL)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gasmeter_code", condition = SqlCondition.EQUAL )
    @Excel(name = "气表编号")
    private String gasmeterCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gasmeter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasmeterName;

    /**
     * 气表类型编码
     */
    @ApiModelProperty(value = "气表类型编码")
    @Length(max = 32, message = "气表类型编码长度不能超过32")
    @TableField(value = "gas_meter_type_code", condition = SqlCondition.EQUAL)
    @Excel(name = "气表类型编码")
    private String gasMeterTypeCode;

    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    @TableField(value = "gas_meter_type_name", condition = LIKE)
    @Excel(name = "气表类型名称")
    private String gasMeterTypeName;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    @Excel(name = "用气类型名称")
    private String useGasTypeName;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    @TableField("recharge_money")
    @Excel(name = "充值金额")
    private BigDecimal rechargeMoney;

    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    @TableField("recharge_gas")
    @Excel(name = "充值气量")
    private BigDecimal rechargeGas;

    /**
     * 充值赠送气量
     */
    @ApiModelProperty(value = "充值赠送气量")
    @TableField("recharge_give_gas")
    @Excel(name = "充值赠送气量")
    private BigDecimal rechargeGiveGas;

    /**
     * 充值赠送金额
     */
    @ApiModelProperty(value = "充值赠送金额")
    @TableField("recharge_give_money")
    @Excel(name = "充值赠送金额")
    private BigDecimal rechargeGiveMoney;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @TableField("total_money")
    @Excel(name = "合计金额")
    private BigDecimal totalMoney;

    /**
     * 合计气量
     */
    @ApiModelProperty(value = "合计气量")
    @TableField("total_gas")
    @Excel(name = "合计气量")
    private BigDecimal totalGas;

    /**
     * 金额流转状态
     *             waite_isuue: 待下发
     *             waite_write_card: 待写卡
     *             success:  下发成功 或 上卡成功
     *             failure：写卡失败 或 下发失败
     */
    @ApiModelProperty(value = "金额流转状态")
    @Length(max = 32, message = "金额流转状态长度不能超过32")
    @TableField(value = "money_flow_status", condition = SqlCondition.EQUAL)
    @Excel(name = "金额流转状态")
    private String moneyFlowStatus;

    /**
     * 金额流转序列号
     */
    @ApiModelProperty(value = "金额流转序列号")
    @Length(max = 32, message = "金额流转序列号长度不能超过32")
    @TableField(value = "money_flow_serial_no", condition = SqlCondition.EQUAL)
    @Excel(name = "金额流转序列号")
    private String moneyFlowSerialNo;


    /**
     * 收费状态：
     *             UNCHARGE: 待收费
     *             CHARGED: 已收费
     *             REFUND: 已退费
     *             CHARGE_FAILURE: 收费失败
     */
    @ApiModelProperty(value = "收费状态：")
    @Length(max = 32, message = "收费状态：长度不能超过32")
    @TableField(value = "charge_status", condition = SqlCondition.EQUAL)
    @Excel(name = "收费状态：")
    private String chargeStatus;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "")
    private String remark;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    @TableField("data_status")
    @Excel(name = "数据状态:")
    private Integer dataStatus;

    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    @TableField("create_user_id")
    @Excel(name = "收费员ID")
    private Long createUserId;

    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    @Length(max = 100, message = "收费员名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "收费员名称")
    private String createUserName;


    @Builder
    public RechargeRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                    String businessHallName, String customerCode, String customerName, String chargeNo, String gasmeterCode, String gasmeterName, 
                    String gasMeterTypeCode, String gasMeterTypeName, Long useGasTypeId, String useGasTypeName, BigDecimal rechargeMoney, BigDecimal rechargeGas, 
                    BigDecimal rechargeGiveGas, BigDecimal rechargeGiveMoney, BigDecimal totalMoney, BigDecimal totalGas, String moneyFlowStatus,String moneyFlowSerialNo, String chargeStatus,
                    String remark, Integer dataStatus, Long createUserId, String createUserName) {
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
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.chargeNo = chargeNo;
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.gasMeterTypeCode = gasMeterTypeCode;
        this.gasMeterTypeName = gasMeterTypeName;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.rechargeMoney = rechargeMoney;
        this.rechargeGas = rechargeGas;
        this.rechargeGiveGas = rechargeGiveGas;
        this.rechargeGiveMoney = rechargeGiveMoney;
        this.totalMoney = totalMoney;
        this.totalGas = totalGas;
        this.moneyFlowStatus = moneyFlowStatus;
        this.moneyFlowSerialNo=moneyFlowSerialNo;
        this.chargeStatus = chargeStatus;
        this.remark = remark;
        this.dataStatus = dataStatus;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
    }

}

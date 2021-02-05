package com.cdqckj.gmis.charges.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.scale.annotation.FieldNoScale;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @since 2020-10-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_charge_item_record")
@ApiModel(value = "ChargeItemRecord", description = "缴费项记录")
@AllArgsConstructor
public class ChargeItemRecord extends Entity<Long> {

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
    @TableField(value = "gasmeter_code", condition = LIKE)
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
     * 收费项场景编码
     */
    @ApiModelProperty(value = "收费项场景编码")
    @Length(max = 32, message = "收费项场景编码长度不能超过32")
    @TableField(value = "toll_item_scene", condition = SqlCondition.EQUAL)
    @Excel(name = "收费项场景编码")
    private String tollItemScene;

    /**
     * 收费项ID
     */
    @TableField(value = "toll_item_id")
    @Excel(name = "收费项ID")
    @ApiModelProperty(value = "收费项ID")
    private Long tollItemId;

    /**
     * 收费项名称
     */
    @ApiModelProperty(value = "收费项名称")
    @Length(max = 100, message = "收费项名称长度不能超过100")
    @TableField(value = "charge_item_name", condition = LIKE)
    @Excel(name = "收费项名称")
    private String chargeItemName;

    /**
     * 收费项产生时间
     */
    @ApiModelProperty(value = "收费项产生时间")
    @TableField("charge_item_time")
    @Excel(name = "收费项产生时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeItemTime;

    /**
     * 收费项来源编码
     */
    @ApiModelProperty(value = "收费项来源编码")
    @Length(max = 32, message = "收费项来源编码长度不能超过32")
    @TableField(value = "charge_item_source_code", condition = LIKE)
    @Excel(name = "收费项来源编码")
    private String chargeItemSourceCode;

    /**
     * 收费项来源名称
     */
    @ApiModelProperty(value = "收费项来源名称")
    @Length(max = 100, message = "收费项来源名称长度不能超过100")
    @TableField(value = "charge_item_source_name", condition = LIKE)
    @Excel(name = "收费项来源名称")
    private String chargeItemSourceName;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 收费项来源ID
     */
    @ApiModelProperty(value = "收费项来源ID")
    @Length(max = 32, message = "收费项来源ID长度不能超过32")
    @TableField(value = "charge_item_source_id", condition = LIKE)
    @Excel(name = "收费项来源ID")
    private String chargeItemSourceId;

    /**
     * 收费项金额
     */
    @ApiModelProperty(value = "收费项金额")
    @TableField("charge_item_money")
    @Excel(name = "收费项金额")
    private BigDecimal chargeItemMoney;

    /**
     * 用气金额
     */
    @ApiModelProperty(value = "用气金额")
    @TableField(exist = false)
    private BigDecimal useGasMoney;

    /**
     * 收费项气量
     */
    @ApiModelProperty(value = "收费项气量")
    @TableField("charge_item_gas")
    @Excel(name = "收费项气量")
    private BigDecimal chargeItemGas;

    /**
     * 收费频次
     */
    @ApiModelProperty(value = "收费频次")
    @Length(max = 32, message = "收费频次长度不能超过32")
    @TableField("charge_frequency")
    @Excel(name = "收费频次")
    private String chargeFrequency;

    /**
     * 金额方式
     *             fixed： 固定金额   可以输入金额
     *             unfixed： 不固定金额
     *             默认固定金额
     *             
     */
    @ApiModelProperty(value = "金额方式")
    @Length(max = 32, message = "金额方式长度不能超过32")
    @TableField(value = "money_method", condition = LIKE)
    @Excel(name = "金额方式")
    private String moneyMethod;

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
     * 是否是减免项
     */
    @ApiModelProperty(value = "是否是减免项")
    @TableField("is_reduction_item")
    @Excel(name = "是否是减免项", replace = {"是_true", "否_false", "_null"})
    private Boolean isReductionItem;

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

    /**
     * 备注/理由
     */
    @ApiModelProperty(value = "备注/理由")
    @Length(max = 100, message = "备注/理由长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注/理由")
    private String remark;

    /**
     * 数据状态: 1: 正常 0: 作废 
     */
    @ApiModelProperty(value = "数据状态: 1: 正常 0: 作废 ")
    @TableField("data_status")
    @Excel(name = "数据状态: 1: 正常 0: 作废 ")
    private Integer dataStatus;

    /**
     * 收费项数量
     */
    @ApiModelProperty(value = "收费项数量")
    @TableField("total_count")
    @Excel(name = "收费项数量")
    private Integer totalCount;

    /**
     * 收费项单价
     */
    @ApiModelProperty(value = "收费项单价")
    @TableField("price")
    @Excel(name = "收费项单价")
    @FieldNoScale
    private BigDecimal price;

    /**
     * 是否阶梯计费
     */
    @ApiModelProperty(value = "是否阶梯计费")
    @TableField("is_ladder_price")
    @Excel(name = "是否阶梯计费")
    private Boolean isLadderPrice;

    /**
     * 阶梯明细[{ladder:1,price:xx,gas:xx,totalMoney:xx}]
     */
    @ApiModelProperty(value = "阶梯明细")
    @TableField("ladder_price_detail")
    @Excel(name = "阶梯明细")
    private String leadderPriceDetail;

    @Builder
    public ChargeItemRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId, 
                    String businessHallName, String customerCode, String customerName, String gasmeterCode, String gasmeterName, String chargeItemName, 
                    String tollItemScene,Long tollItemId,LocalDateTime chargeItemTime, String chargeItemSourceCode, String chargeItemSourceName, String chargeNo, String chargeItemSourceId, BigDecimal chargeItemMoney,
                    BigDecimal chargeItemGas, String moneyMethod, BigDecimal rechargeGiveGas, BigDecimal rechargeGiveMoney, Boolean isReductionItem, Long createUserId, 
                    String createUserName, String remark, Integer dataStatus) {
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
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.tollItemScene=tollItemScene;
        this.tollItemId=tollItemId;
        this.chargeItemName = chargeItemName;
        this.chargeItemTime = chargeItemTime;
        this.chargeItemSourceCode = chargeItemSourceCode;
        this.chargeItemSourceName = chargeItemSourceName;
        this.chargeNo = chargeNo;
        this.chargeItemSourceId = chargeItemSourceId;
        this.chargeItemMoney = chargeItemMoney;
        this.chargeItemGas = chargeItemGas;
        this.moneyMethod = moneyMethod;
        this.rechargeGiveGas = rechargeGiveGas;
        this.rechargeGiveMoney = rechargeGiveMoney;
        this.isReductionItem = isReductionItem;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.remark = remark;
        this.dataStatus = dataStatus;
    }

}

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
 * 客户场景费用单
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
@TableName("gt_customer_scene_charge_order")
@ApiModel(value = "CustomerSceneChargeOrder", description = "客户场景费用单")
@AllArgsConstructor
public class CustomerSceneChargeOrder extends Entity<Long> {

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
     * 场景费用单编号
     */
    @ApiModelProperty(value = "场景费用单编号")
    @Length(max = 32, message = "场景费用单编号长度不能超过32")
    @TableField(value = "scene_charge_no", condition = LIKE)
    @Excel(name = "场景费用单编号")
    private String sceneChargeNo;

    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 收费项场景
     */
    @ApiModelProperty(value = "收费项场景")
    @TableField(value = "toll_item_scene")
    @Length(max = 32, message = "收费项场景长度不能超过32")
    @Excel(name = "收费项场景")
    private String tollItemScene;

    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @TableField(value = "toll_item_id")
    @Excel(name = "收费项ID")
    private Long tollItemId;

    /**
     * 收费项名称
     */
    @ApiModelProperty(value = "收费项名称")
    @TableField(value = "toll_item_name")
    @Length(max = 32, message = "收费项名称长度不能超过32")
    @Excel(name = "收费项名称")
    private String tollItemName;


    /**
     * 收费项频次
     */
    @ApiModelProperty(value = "收费项频次")
    @TableField(value = "toll_item_frequency")
    @Length(max = 32, message = "收费项频次长度不能超过32")
    @Excel(name = "收费项频次")
    private String tollItemFrequency;


    /**
     * 业务编号
     */
    @ApiModelProperty(value = "业务编号")
    @Length(max = 32, message = "业务编号长度不能超过32")
    @TableField(value = "business_no", condition = LIKE)
    @Excel(name = "业务编号")
    private String businessNo;

    /**
     * 缴费金额
     */
    @ApiModelProperty(value = "缴费金额")
    @TableField("charge_money")
    @Excel(name = "缴费金额")
    private BigDecimal chargeMoney;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    @TableField("discount_money")
    @Excel(name = "优惠金额")
    private BigDecimal discountMoney;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @TableField("total_money")
    @Excel(name = "合计金额")
    private BigDecimal totalMoney;

    /**
     * 收费状态：
     *             UNCHARGE: 待收费
     *             CHARGED: 已收费
     *             CHARGE_FAILURE: 收费失败
     */
    @ApiModelProperty(value = "收费状态：")
    @Length(max = 32, message = "收费状态：长度不能超过32")
    @TableField(value = "charge_status", condition = LIKE)
    @Excel(name = "收费状态：")
    private String chargeStatus;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    @TableField("data_status")
    @Excel(name = "数据状态:")
    private Integer dataStatus;

    @Builder
    public CustomerSceneChargeOrder(Long id, Long createUser, LocalDateTime createTime, Long updateUser, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasmeterCode, String gasmeterName, Long useGasTypeId, String useGasTypeName, String sceneChargeNo, 
                    String chargeNo, Long tollItemId, String tollItemName, String tollItemScene, String businessNo, BigDecimal chargeMoney,
                    BigDecimal discountMoney, BigDecimal totalMoney, String chargeStatus, Integer dataStatus) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasmeterCode = gasmeterCode;
        this.gasmeterName = gasmeterName;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.sceneChargeNo = sceneChargeNo;
        this.chargeNo = chargeNo;
        this.tollItemId = tollItemId;
        this.tollItemScene = tollItemScene;
        this.tollItemName = tollItemName;
        this.businessNo = businessNo;
        this.chargeMoney = chargeMoney;
        this.discountMoney = discountMoney;
        this.totalMoney = totalMoney;
        this.chargeStatus = chargeStatus;
        this.dataStatus = dataStatus;
    }

}

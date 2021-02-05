package com.cdqckj.gmis.charges.dto;

import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "RechargeRecordUpdateDTO", description = "充值记录")
public class RechargeRecordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    private Long businessHallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    @Length(max = 100, message = "营业厅名称长度不能超过100")
    private String businessHallName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    private String chargeNo;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasmeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasmeterName;
    /**
     * 气表类型编码
     */
    @ApiModelProperty(value = "气表类型编码")
    @Length(max = 32, message = "气表类型编码长度不能超过32")
    private String gasMeterTypeCode;
    /**
     * 气表类型名称
     */
    @ApiModelProperty(value = "气表类型名称")
    @Length(max = 100, message = "气表类型名称长度不能超过100")
    private String gasMeterTypeName;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 100, message = "用气类型名称长度不能超过100")
    private String useGasTypeName;
    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeMoney;
    /**
     * 充值气量
     */
    @ApiModelProperty(value = "充值气量")
    private BigDecimal rechargeGas;
    /**
     * 充值赠送气量
     */
    @ApiModelProperty(value = "充值赠送气量")
    private BigDecimal rechargeGiveGas;
    /**
     * 充值赠送金额
     */
    @ApiModelProperty(value = "充值赠送金额")
    private BigDecimal rechargeGiveMoney;
    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalMoney;
    /**
     * 合计气量
     */
    @ApiModelProperty(value = "合计气量")
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
    private String moneyFlowStatus;

    /**
     * 金额流转序列号
     */
    @ApiModelProperty(value = "金额流转序列号")
    @Length(max = 32, message = "金额流转序列号长度不能超过32")
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
    private String chargeStatus;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String remark;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;
    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    private Long createUserId;
    /**
     * 收费员名称
     */
    @ApiModelProperty(value = "收费员名称")
    @Length(max = 100, message = "收费员名称长度不能超过100")
    private String createUserName;
}

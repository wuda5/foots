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
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 第三方支付记录明细
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
@TableName("gt_charge_record_pay")
@ApiModel(value = "ChargeRecordPay", description = "第三方支付记录明细")
@AllArgsConstructor
public class ChargeRecordPay extends Entity<Long> {

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
     * 费用记录类型
     *             recharge充值
     *             charge缴费
     *             precharge预存
     */
    @ApiModelProperty(value = "费用记录类型")
    @Length(max = 32, message = "费用记录类型长度不能超过32")
    @TableField(value = "fee_record_type", condition = LIKE)
    @Excel(name = "费用记录类型")
    private String feeRecordType;

    /**
     * 费用记录编号：
     *             充值记录编号
     *             缴费记录编号
     *             预存记录编号
     */
    @ApiModelProperty(value = "费用记录编号：")
    @Length(max = 32, message = "费用记录编号：长度不能超过32")
    @TableField(value = "fee_record_no", condition = LIKE)
    @Excel(name = "费用记录编号：")
    private String feeRecordNo;

    /**
     * 支付类型
     *             支付宝
     *             微信支付
     *             现金
     *             POS
     */
    @ApiModelProperty(value = "支付类型")
    @Length(max = 32, message = "支付类型长度不能超过32")
    @TableField(value = "payment_type", condition = LIKE)
    @Excel(name = "支付类型")
    private String paymentType;

    /**
     * 网络支付服务商
     *             微信
     *             支付宝
     */
    @ApiModelProperty(value = "网络支付服务商")
    @Length(max = 32, message = "网络支付服务商长度不能超过32")
    @TableField(value = "service_provider", condition = LIKE)
    @Excel(name = "网络支付服务商")
    private String serviceProvider;

    /**
     * 网络支付方式
     *             扫码支付
     *             被扫扣款
     */
    @ApiModelProperty(value = "网络支付方式")
    @Length(max = 32, message = "网络支付方式长度不能超过32")
    @TableField(value = "payment_method", condition = LIKE)
    @Excel(name = "网络支付方式")
    private String paymentMethod;

    /**
     * 费用金额
     */
    @ApiModelProperty(value = "费用金额")
    @TableField("fee_record_money")
    @Excel(name = "费用金额")
    private BigDecimal feeRecordMoney;

    /**
     * 费用记录描述
     */
    @ApiModelProperty(value = "费用记录描述")
    @Length(max = 100, message = "费用记录描述长度不能超过100")
    @TableField(value = "fee_record_description", condition = LIKE)
    @Excel(name = "费用记录描述")
    private String feeRecordDescription;

    /**
     * 订单有效期
     */
    @ApiModelProperty(value = "订单有效期")
    @TableField("expiry")
    @Excel(name = "订单有效期")
    private Integer expiry;

    /**
     * 二维码
     */
    @ApiModelProperty(value = "二维码")
    @Length(max = 1000, message = "二维码长度不能超过1000")
    @TableField(value = "qr_code", condition = LIKE)
    @Excel(name = "二维码")
    private String qrCode;

    /**
     * 服务商订单号
     */
    @ApiModelProperty(value = "服务商订单号")
    @Length(max = 50, message = "服务商订单号长度不能超过50")
    @TableField(value = "service_order_number", condition = LIKE)
    @Excel(name = "服务商订单号")
    private String serviceOrderNumber;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 100, message = "创建人名称长度不能超过100")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "创建人名称")
    private String createUserName;

    /**
     * 支付状态：
     *             未发起支付
     *             已发起支付
     *             支付成功
     *             支付失败
     */
    @ApiModelProperty(value = "支付状态：")
    @Length(max = 32, message = "支付状态：长度不能超过32")
    @TableField(value = "payment_status", condition = LIKE)
    @Excel(name = "支付状态：")
    private String paymentStatus;

    /**
     * 支付完成时间
     */
    @ApiModelProperty(value = "支付完成时间")
    @TableField("payment_time")
    @Excel(name = "支付完成时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime paymentTime;

    /**
     * 支付结果
     *             返回内容记录
     */
    @ApiModelProperty(value = "支付结果")
    @Length(max = 100, message = "支付结果长度不能超过100")
    @TableField(value = "payment_result", condition = LIKE)
    @Excel(name = "支付结果")
    private String paymentResult;

    /**
     * 对账状态
     *             未支付成功
     *             待对账
     *             已对账
     */
    @ApiModelProperty(value = "对账状态")
    @TableField("proof_status")
    @Excel(name = "对账状态")
    private Integer proofStatus;

    /**
     * 对账时间
     */
    @ApiModelProperty(value = "对账时间")
    @TableField("proof_time")
    @Excel(name = "对账时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime proofTime;

    /**
     * 对账结果
     *             对账通过
     *             差异
     */
    @ApiModelProperty(value = "对账结果")
    @Length(max = 100, message = "对账结果长度不能超过100")
    @TableField(value = "proof_result", condition = LIKE)
    @Excel(name = "对账结果")
    private String proofResult;

    /**
     * 人工校对人ID
     */
    @ApiModelProperty(value = "人工校对人ID")
    @TableField("manual_proof_usre_id")
    @Excel(name = "人工校对人ID")
    private Long manualProofUsreId;

    /**
     * 人工校对人名称
     */
    @ApiModelProperty(value = "人工校对人名称")
    @Length(max = 100, message = "人工校对人名称长度不能超过100")
    @TableField(value = "manual_proof_usre_name", condition = LIKE)
    @Excel(name = "人工校对人名称")
    private String manualProofUsreName;


    @Builder
    public ChargeRecordPay(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                    String businessHallName, String feeRecordType, String feeRecordNo, String paymentType, String serviceProvider, String paymentMethod, 
                    BigDecimal feeRecordMoney, String feeRecordDescription, Integer expiry, String qrCode, String serviceOrderNumber, String createUserName, 
                    String paymentStatus, LocalDateTime paymentTime, String paymentResult, Integer proofStatus, LocalDateTime proofTime, String proofResult, 
                    Long manualProofUsreId, String manualProofUsreName) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.feeRecordType = feeRecordType;
        this.feeRecordNo = feeRecordNo;
        this.paymentType = paymentType;
        this.serviceProvider = serviceProvider;
        this.paymentMethod = paymentMethod;
        this.feeRecordMoney = feeRecordMoney;
        this.feeRecordDescription = feeRecordDescription;
        this.expiry = expiry;
        this.qrCode = qrCode;
        this.serviceOrderNumber = serviceOrderNumber;
        this.createUserName = createUserName;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.paymentResult = paymentResult;
        this.proofStatus = proofStatus;
        this.proofTime = proofTime;
        this.proofResult = proofResult;
        this.manualProofUsreId = manualProofUsreId;
        this.manualProofUsreName = manualProofUsreName;
    }

}

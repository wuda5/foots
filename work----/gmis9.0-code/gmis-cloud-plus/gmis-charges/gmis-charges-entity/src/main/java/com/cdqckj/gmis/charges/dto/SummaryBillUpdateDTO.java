package com.cdqckj.gmis.charges.dto;

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
import com.cdqckj.gmis.base.entity.SuperEntity;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author songyz
 * @since 2020-12-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SummaryBillUpdateDTO", description = "")
public class SummaryBillUpdateDTO implements Serializable {

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
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    private BigDecimal totalAmount;
    /**
     * 现金金额
     */
    @ApiModelProperty(value = "现金金额")
    private BigDecimal cashAmount;
    /**
     * 银行转账金额
     */
    @ApiModelProperty(value = "银行转账金额")
    private BigDecimal bankTransferAmount;
    /**
     * 支付宝金额
     */
    @ApiModelProperty(value = "支付宝金额")
    private BigDecimal alipayAmount;
    /**
     * 微信金额
     */
    @ApiModelProperty(value = "微信金额")
    private BigDecimal wechatAmount;
    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退费金额")
    private BigDecimal refundAmount;
    /**
     * 预存抵扣
     */
    @ApiModelProperty(value = "预存抵扣")
    private BigDecimal preDepositDeduction;
    /**
     * 合计发票数
     */
    @ApiModelProperty(value = "合计发票数")
    private Integer receiptInvoiceTotalNum;
    /**
     * 票据数
     */
    @ApiModelProperty(value = "票据数")
    private Integer receiptTotalNum;
    /**
     * 蓝票数
     */
    @ApiModelProperty(value = "蓝票数")
    private Integer blueInvoiceTotalNum;
    /**
     * 红票数
     */
    @ApiModelProperty(value = "红票数")
    private Integer redInvoiceTotalNum;
    /**
     * 废票数
     */
    @ApiModelProperty(value = "废票数")
    private Integer invalidInvoiceTotalNum;
    /**
     * 合计发票金额
     */
    @ApiModelProperty(value = "合计发票金额")
    private BigDecimal receiptInvoiceTotalAmount;
    /**
     * 操作员编号
     */
    @ApiModelProperty(value = "操作员编号")
    @Length(max = 32, message = "操作员编号长度不能超过32")
    private String operatorNo;
    /**
     * 操作员姓名
     */
    @ApiModelProperty(value = "操作员姓名")
    @Length(max = 100, message = "操作员姓名长度不能超过100")
    private String operatorName;
    /**
     * 扎帐开始日期
     */
    @ApiModelProperty(value = "扎帐开始日期")
    private LocalDateTime summaryStartDate;
    /**
     * 扎帐结束始日期
     */
    @ApiModelProperty(value = "扎帐结束始日期")
    private LocalDateTime summaryEndDate;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
    /**
     * 扎帐标识：UNBILL 未扎帐,BILLED 已扎帐
     */
    @ApiModelProperty(value = "扎帐标识：UNBILL 未扎帐,BILLED 已扎帐")
    @Length(max = 32, message = "扎帐标识：UNBILL 未扎帐,BILLED 已扎帐长度不能超过32")
    private String summaryBillStatus;
}

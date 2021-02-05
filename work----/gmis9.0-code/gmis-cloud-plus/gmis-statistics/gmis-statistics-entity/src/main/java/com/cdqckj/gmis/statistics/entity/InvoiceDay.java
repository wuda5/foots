package com.cdqckj.gmis.statistics.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.entity.TreeEntity;
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
 * @author gmis
 * @since 2020-11-02
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sts_invoice_day")
@ApiModel(value = "InvoiceDay", description = "")
@AllArgsConstructor
public class InvoiceDay extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 营业厅ID
     */
    @ApiModelProperty(value = "营业厅ID")
    @TableField("business_hall_id")
    @Excel(name = "营业厅ID")
    private Long businessHallId;

    /**
     * 收费员ID
     */
    @ApiModelProperty(value = "收费员ID")
    @TableField("create_user_id")
    @Excel(name = "收费员ID")
    private Long createUserId;

    /**
     * 发票的数量
     */
    @ApiModelProperty(value = "发票的数量")
    @NotNull(message = "发票的数量不能为空")
    @TableField("amount")
    @Excel(name = "发票的数量")
    private Integer amount;

    /**
     * 合计金额
     */
    @ApiModelProperty(value = "合计金额")
    @NotNull(message = "合计金额不能为空")
    @TableField("total_amount")
    @Excel(name = "合计金额")
    private BigDecimal totalAmount;

    /**
     * 合计税额
     */
    @ApiModelProperty(value = "合计税额")
    @NotNull(message = "合计税额不能为空")
    @TableField("total_tax")
    @Excel(name = "合计税额")
    private BigDecimal totalTax;

    /**
     * 收费的时间是哪一天
     */
    @ApiModelProperty(value = "收费的时间是哪一天")
    @NotNull(message = "收费的时间是哪一天不能为空")
    @TableField("sts_day")
    @Excel(name = "收费的时间是哪一天", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stsDay;

    /**
     * 发票种类
     *             0红票
     *             1蓝票
     *             2废票
     */
    @ApiModelProperty(value = "发票种类")
    @NotEmpty(message = "发票种类不能为空")
    @Length(max = 10, message = "发票种类长度不能超过10")
    @TableField(value = "invoice_kind", condition = LIKE)
    @Excel(name = "发票种类")
    private String invoiceKind;

    /**
     * 发票类型
     *             007普票
     *             004专票
     *             026电票
     */
    @ApiModelProperty(value = "发票类型")
    @NotEmpty(message = "发票类型不能为空")
    @Length(max = 30, message = "发票类型长度不能超过30")
    @TableField(value = "invoice_type", condition = LIKE)
    @Excel(name = "发票类型")
    private String invoiceType;


    @Builder
    public InvoiceDay(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                      String companyCode, Long orgId, Long businessHallId, Long createUserId, Integer amount,
                      BigDecimal totalAmount, BigDecimal totalTax, LocalDateTime stsDay, String invoiceKind, String invoiceType) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.orgId = orgId;
        this.businessHallId = businessHallId;
        this.createUserId = createUserId;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.stsDay = stsDay;
        this.invoiceKind = invoiceKind;
        this.invoiceType = invoiceType;
    }

}

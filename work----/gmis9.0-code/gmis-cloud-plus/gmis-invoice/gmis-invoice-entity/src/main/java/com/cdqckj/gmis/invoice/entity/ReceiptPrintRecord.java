package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @since 2020-10-22
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_receipt_print_record")
@ApiModel(value = "ReceiptPrintRecord", description = "票据打印记录")
@AllArgsConstructor
public class ReceiptPrintRecord extends Entity<Long> {

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
     * 缴费编号
     */
    @ApiModelProperty(value = "缴费编号")
    @Length(max = 32, message = "缴费编号长度不能超过32")
    @TableField(value = "charge_no", condition = LIKE)
    @Excel(name = "缴费编号")
    private String chargeNo;

    /**
     * 打印日期
     */
    @ApiModelProperty(value = "打印日期")
    @TableField("print_date")
    @Excel(name = "打印日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate printDate;

    /**
     * 操作人id
     */
    @ApiModelProperty(value = "操作人id")
    @TableField("operator_id")
    @Excel(name = "操作人id")
    private Long operatorId;

    /**
     * 操作人姓名
     */
    @ApiModelProperty(value = "操作人姓名")
    @Length(max = 100, message = "操作人姓名长度不能超过100")
    @TableField(value = "operator_name", condition = LIKE)
    @Excel(name = "操作人姓名")
    private String operatorName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("delete_status")
    @Excel(name = "状态")
    private Integer deleteStatus;


    @Builder
    public ReceiptPrintRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                              String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                              String businessHallName, String customerCode, String customerName, String chargeNo, LocalDate printDate, Long operatorId,
                              String operatorName, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessHallId = businessHallId;
        this.businessHallName = businessHallName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.chargeNo = chargeNo;
        this.printDate = printDate;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.deleteStatus = deleteStatus;
    }

}

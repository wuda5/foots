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
 * 电子发票推送记录表
 * </p>
 *
 * @author gmis
 * @since 2020-11-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_invoice_push_record")
@ApiModel(value = "InvoicePushRecord", description = "电子发票推送记录表")
@AllArgsConstructor
public class InvoicePushRecord extends Entity<Long> {

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
     * 推送的平台
     */
    @ApiModelProperty(value = "推送的平台")
    @Length(max = 50, message = "推送的平台长度不能超过50")
    @TableField(value = "push_plat", condition = LIKE)
    @Excel(name = "推送的平台")
    private String pushPlat;

    /**
     * 推送的url
     */
    @ApiModelProperty(value = "推送的url")
    @Length(max = 100, message = "推送的url长度不能超过100")
    @TableField(value = "push_url", condition = LIKE)
    @Excel(name = "推送的url")
    private String pushUrl;

    /**
     * 推送的接口执行的操作
     */
    @ApiModelProperty(value = "推送的接口执行的操作")
    @Length(max = 50, message = "推送的接口执行的操作长度不能超过50")
    @TableField(value = "push_cmd_name", condition = LIKE)
    @Excel(name = "推送的接口执行的操作")
    private String pushCmdName;

    /**
     * 推送的操作流水号
     */
    @ApiModelProperty(value = "推送的操作流水号")
    @Length(max = 50, message = "推送的操作流水号长度不能超过50")
    @TableField(value = "serial_no", condition = LIKE)
    @Excel(name = "推送的操作流水号")
    private String serialNo;

    /**
     * 推送的发票id
     */
    @ApiModelProperty(value = "推送的发票id")
    @TableField("invoice_id")
    @Excel(name = "推送的发票id")
    private Long invoiceId;

    /**
     * 推送的发票编号
     */
    @ApiModelProperty(value = "推送的发票编号")
    @Length(max = 30, message = "推送的发票编号长度不能超过30")
    @TableField(value = "invoice_number", condition = LIKE)
    @Excel(name = "推送的发票编号")
    private String invoiceNumber;

    /**
     * 推送的数据
     */
    @ApiModelProperty(value = "推送的数据")
    @Length(max = 3000, message = "推送的数据长度不能超过3000")
    @TableField(value = "push_data", condition = LIKE)
    @Excel(name = "推送的数据")
    private String pushData;

    /**
     * 推送日期
     */
    @ApiModelProperty(value = "推送日期")
    @TableField("push_date")
    @Excel(name = "推送日期", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDate pushDate;

    /**
     * 推送结果code
     */
    @ApiModelProperty(value = "推送结果code")
    @Length(max = 10, message = "推送结果code长度不能超过10")
    @TableField(value = "push_result_code", condition = LIKE)
    @Excel(name = "推送结果code")
    private String pushResultCode;

    /**
     * 推送结果
     */
    @ApiModelProperty(value = "推送结果")
    @Length(max = 100, message = "推送结果长度不能超过100")
    @TableField(value = "push_result", condition = LIKE)
    @Excel(name = "推送结果")
    private String pushResult;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("delete_status")
    @Excel(name = "状态")
    private Integer deleteStatus;


    @Builder
    public InvoicePushRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                             String companyCode, String companyName, Long orgId, String orgName, Long businessHallId,
                             String businessHallName, String pushPlat, String pushUrl, String pushCmdName, String serialNo, Long invoiceId,
                             String invoiceNumber, String pushData, LocalDate pushDate, String pushResultCode, String pushResult, Integer deleteStatus) {
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
        this.pushPlat = pushPlat;
        this.pushUrl = pushUrl;
        this.pushCmdName = pushCmdName;
        this.serialNo = serialNo;
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.pushData = pushData;
        this.pushDate = pushDate;
        this.pushResultCode = pushResultCode;
        this.pushResult = pushResult;
        this.deleteStatus = deleteStatus;
    }

}

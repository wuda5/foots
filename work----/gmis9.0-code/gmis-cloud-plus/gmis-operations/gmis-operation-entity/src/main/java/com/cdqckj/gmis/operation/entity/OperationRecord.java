package com.cdqckj.gmis.operation.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 运行维护工单
 * </p>
 *
 * @author gmis
 * @since 2020-08-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_operation_record")
@ApiModel(value = "OperationRecord", description = "运行维护工单")
@AllArgsConstructor
public class OperationRecord extends Entity<Long> {

    private static final long serialVersionUID = OperationRecord.class.hashCode();

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
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 300, message = "公司名称长度不能超过300")
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
    @Length(max = 60, message = "客户名称长度不能超过60")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    @TableField(value = "gas_code", condition = LIKE)
    @Excel(name = "气表编号")
    private String gasCode;

    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    @TableField(value = "gas_meter_name", condition = LIKE)
    @Excel(name = "气表名称")
    private String gasMeterName;

    /**
     * 气表厂家ID
     */
    @ApiModelProperty(value = "气表厂家ID")
    @TableField("gas_meter_factory_id")
    @Excel(name = "气表厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    @TableField("use_gas_type_id")
    @Excel(name = "用气类型ID")
    private Long useGasTypeId;

    /**
     * 运维受理编号
     */
    @ApiModelProperty(value = "运维受理编号")
    @Length(max = 32, message = "运维受理编号长度不能超过32")
    @TableField(value = "accept_no", condition = LIKE)
    @Excel(name = "运维受理编号")
    private String acceptNo;

    /**
     * 接单人ID
     */
    @ApiModelProperty(value = "接单人ID")
    @TableField("receive_user_id")
    @Excel(name = "接单人ID")
    private Long receiveUserId;

    /**
     * 接单人名称
     */
    @ApiModelProperty(value = "接单人名称")
    @Length(max = 100, message = "接单人名称长度不能超过100")
    @TableField(value = "receive_user_name", condition = LIKE)
    @Excel(name = "接单人名称")
    private String receiveUserName;

    /**
     * 接单人电话
     */
    @ApiModelProperty(value = "接单人电话")
    @Length(max = 20, message = "接单人电话长度不能超过20")
    @TableField(value = "receive_user_phone", condition = LIKE)
    @Excel(name = "接单人电话")
    private String receiveUserPhone;

    /**
     * 接单人时间
     */
    @ApiModelProperty(value = "接单人时间")
    @TableField("receive_user_time")
    @Excel(name = "接单人时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime receiveUserTime;

    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间")
    @TableField("book_operation_time")
    @Excel(name = "预约时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime bookOperationTime;

    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    @TableField("review_user_id")
    @Excel(name = "审批人ID")
    private Long reviewUserId;

    /**
     * 审批人名称
     */
    @ApiModelProperty(value = "审批人名称")
    @Length(max = 100, message = "审批人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    @Excel(name = "审批人名称")
    private String reviewUserName;

    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    @TableField("review_time")
    @Excel(name = "审批时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    @Length(max = 100, message = "审批意见长度不能超过100")
    @TableField(value = "review_objection", condition = LIKE)
    @Excel(name = "审批意见")
    private String reviewObjection;

    /**
     * 派单人ID
     */
    @ApiModelProperty(value = "派单人ID")
    @TableField("distribution_user_id")
    @Excel(name = "派单人ID")
    private Long distributionUserId;

    /**
     * 派单人名称
     */
    @ApiModelProperty(value = "派单人名称")
    @Length(max = 100, message = "派单人名称长度不能超过100")
    @TableField(value = "distribution_user_name", condition = LIKE)
    @Excel(name = "派单人名称")
    private String distributionUserName;

    /**
     * 派单时间
     */
    @ApiModelProperty(value = "派单时间")
    @TableField("distribution_time")
    @Excel(name = "派单时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime distributionTime;

    /**
     * 结单人ID
     */
    @ApiModelProperty(value = "结单人ID")
    @TableField("end_job_user_id")
    @Excel(name = "结单人ID")
    private Long endJobUserId;

    /**
     * 结单人名称
     */
    @ApiModelProperty(value = "结单人名称")
    @Length(max = 100, message = "结单人名称长度不能超过100")
    @TableField(value = "end_job_user_name", condition = LIKE)
    @Excel(name = "结单人名称")
    private String endJobUserName;

    /**
     * 结单时间
     */
    @ApiModelProperty(value = "结单时间")
    @TableField("end_job_time")
    @Excel(name = "结单时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime endJobTime;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    @Length(max = 1000, message = "驳回原因长度不能超过1000")
    @TableField(value = "reject_reason", condition = LIKE)
    @Excel(name = "驳回原因")
    private String rejectReason;

    /**
     * 终止人ID
     */
    @ApiModelProperty(value = "终止人ID")
    @TableField("terminate_user_id")
    @Excel(name = "终止人ID")
    private Long terminateUserId;

    /**
     * 终止人名称
     */
    @ApiModelProperty(value = "终止人名称")
    @Length(max = 100, message = "终止人名称长度不能超过100")
    @TableField(value = "terminate_user_name", condition = LIKE)
    @Excel(name = "终止人名称")
    private String terminateUserName;

    /**
     * 终止时间
     */
    @ApiModelProperty(value = "终止时间")
    @TableField("terminate_time")
    @Excel(name = "终止时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime terminateTime;

    /**
     * 终止原因
     */
    @ApiModelProperty(value = "终止原因")
    @Length(max = 1000, message = "终止原因长度不能超过1000")
    @TableField(value = "stop_desc", condition = LIKE)
    @Excel(name = "终止原因")
    private String stopDesc;

    /**
     * 工单类型
     *             0 普通运行维护
     *             1 点火通气
     */
    @ApiModelProperty(value = "工单类型")
    @TableField("order_type")
    @Excel(name = "工单类型")
    private Integer orderType;

    /**
     * 紧急程度
     *             0 正常
     *             1 紧急
     *             2 非常紧急
     */
    @ApiModelProperty(value = "紧急程度")
    @TableField("urgency_level")
    @Excel(name = "紧急程度")
    private Integer urgencyLevel;

    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容")
    @Length(max = 1000, message = "工单内容长度不能超过1000")
    @TableField(value = "order_content", condition = LIKE)
    @Excel(name = "工单内容")
    private String orderContent;

    /**
     * 运维状态:
     *              0 待提审
     *              1 待审核
     *              2 待接单
     *              3 待运维
     *              4 运维中
     *              5 终止
     *              6 作废
     *              7 结单
     */
    @ApiModelProperty(value = "运维状态:")
    @TableField("operation_status")
    @Excel(name = "运维状态:")
    private Integer operationStatus;

    /**
     * 运维流程状态
     *             0 提审
     *             1 审核
     *             2 派单
     *             3 驳回
     *             4 撤回
     *             5 接单确认
     *             6 拒单
     *             7 退单
     *             8 转单
     *             9 预约
     *             10 上门
     *             11 结单
     *             12 终止
     */
    @ApiModelProperty(value = "运维流程状态")
    @TableField("operation_process_state")
    @Excel(name = "运维流程状态")
    private Integer operationProcessState;

    /**
     * 转单ID
     */
    @ApiModelProperty(value = "转单ID")
    @TableField("transfer_id")
    @Excel(name = "转单ID")
    private Long transferId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public OperationRecord(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasCode, String gasMeterName, Long gasMeterFactoryId, Long useGasTypeId, String acceptNo, 
                    Long receiveUserId, String receiveUserName, String receiveUserPhone, LocalDateTime receiveUserTime, LocalDateTime bookOperationTime, Long reviewUserId, 
                    String reviewUserName, LocalDateTime reviewTime, String reviewObjection, Long distributionUserId, String distributionUserName, LocalDateTime distributionTime, 
                    Long endJobUserId, String endJobUserName, LocalDateTime endJobTime, String rejectReason, Long terminateUserId, String terminateUserName, 
                    LocalDateTime terminateTime, String stopDesc, Integer orderType, Integer urgencyLevel, String orderContent, Integer operationStatus, 
                    Integer operationProcessState, Long transferId, String remark) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gasCode = gasCode;
        this.gasMeterName = gasMeterName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.useGasTypeId = useGasTypeId;
        this.acceptNo = acceptNo;
        this.receiveUserId = receiveUserId;
        this.receiveUserName = receiveUserName;
        this.receiveUserPhone = receiveUserPhone;
        this.receiveUserTime = receiveUserTime;
        this.bookOperationTime = bookOperationTime;
        this.reviewUserId = reviewUserId;
        this.reviewUserName = reviewUserName;
        this.reviewTime = reviewTime;
        this.reviewObjection = reviewObjection;
        this.distributionUserId = distributionUserId;
        this.distributionUserName = distributionUserName;
        this.distributionTime = distributionTime;
        this.endJobUserId = endJobUserId;
        this.endJobUserName = endJobUserName;
        this.endJobTime = endJobTime;
        this.rejectReason = rejectReason;
        this.terminateUserId = terminateUserId;
        this.terminateUserName = terminateUserName;
        this.terminateTime = terminateTime;
        this.stopDesc = stopDesc;
        this.orderType = orderType;
        this.urgencyLevel = urgencyLevel;
        this.orderContent = orderContent;
        this.operationStatus = operationStatus;
        this.operationProcessState = operationProcessState;
        this.transferId = transferId;
        this.remark = remark;
    }

}

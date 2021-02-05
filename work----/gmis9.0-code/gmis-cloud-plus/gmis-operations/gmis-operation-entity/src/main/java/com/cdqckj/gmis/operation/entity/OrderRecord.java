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
 * 客户工单记录
 * </p>
 *
 * @author gmis
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_order_record")
@ApiModel(value = "OrderRecord", description = "客户工单记录")
@AllArgsConstructor
public class OrderRecord extends Entity<Long> {

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
    @Length(max = 100, message = "客户编号长度不能超过100")
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
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    @Length(max = 100, message = "客户电话长度不能超过100")
    @TableField(value = "customer_mobile", condition = LIKE)
    @Excel(name = "客户名称")
    private String customerMobile;

    /**
     * 客户详细地址
     */
    @ApiModelProperty(value = "客户详细地址")
    @Length(max = 200, message = "客户详细地址")
    @TableField(value = "address_detail", condition = LIKE)
    @Excel(name = "客户详细地址")
    private String addressDetail;

    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 100, message = "表具编号长度不能超过100")
    @TableField(value = "meter_code", condition = LIKE)
    @Excel(name = "表具编号")
    private String meterCode;


    /**
     * 业务类型
     *             1  报装工单
     *             2  安检工单
     *             3  运维工单
     *             4  客服工单
     */
    @ApiModelProperty(value = "业务类型")
    @TableField("business_type")
    @Excel(name = "业务类型")
    private Integer businessType;

    /**
     * 业务订单编号
     */
    @ApiModelProperty(value = "业务订单编号")
    @Length(max = 32, message = "业务订单编号长度不能超过32")
    @TableField(value = "business_order_number", condition = LIKE)
    @Excel(name = "业务订单编号")
    private String businessOrderNumber;

    /**
     * 工单类型
     *             survey: 踏勘
     *             install: 施工
     */
    @ApiModelProperty(value = "工单类型")
    @Length(max = 32, message = "工单类型长度不能超过32")
    @TableField(value = "work_order_type", condition = LIKE)
    @Excel(name = "工单类型")
    private String workOrderType;

    /**
     * 工单编号
     */
    @ApiModelProperty(value = "工单编号")
    @Length(max = 32, message = "工单编号长度不能超过32")
    @TableField(value = "work_order_no", condition = LIKE)
    @Excel(name = "工单编号")
    private String workOrderNo;

    /**
     * 紧急程度
     *             normal:正常
     *             urgent:紧急
     *             very_urgent:非常紧急
     */
    @ApiModelProperty(value = "紧急程度")
    @Length(max = 20, message = "紧急程度长度不能超过20")
    @TableField(value = "urgency", condition = LIKE)
    @Excel(name = "紧急程度")
    private String urgency;

    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容")
    @Length(max = 1000, message = "工单内容长度不能超过1000")
    @TableField(value = "work_order_desc", condition = LIKE)
    @Excel(name = "工单内容")
    private String workOrderDesc;

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
     * 接单时间
     */
    @ApiModelProperty(value = "接单时间")
    @TableField("receive_time")
    @Excel(name = "接单时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime receiveTime;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    @TableField(value = "receive_user_mobile", condition = LIKE)
    @Excel(name = "联系电话")
    private String receiveUserMobile;

    @ApiModelProperty(value = "")
    @TableField("review_user_id")
    @Excel(name = "")
    private Long reviewUserId;

    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    @Excel(name = "审核人名称")
    private String reviewUserName;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("review_time")
    @Excel(name = "审核时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 1000, message = "审核意见长度不能超过1000")
    @TableField(value = "review_desc", condition = LIKE)
    @Excel(name = "审核意见")
    private String reviewDesc;

    /**
     * 预约处理时间
     */
    @ApiModelProperty(value = "预约处理时间")
    @TableField("book_operation_time")
    @Excel(name = "预约处理时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime bookOperationTime;

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
    @TableField(value = "reject_desc", condition = LIKE)
    @Excel(name = "驳回原因")
    private String rejectDesc;

    /**
     * 终止原因：业务终止
     */
    @ApiModelProperty(value = "终止原因：业务终止")
    @Length(max = 1000, message = "终止原因：业务终止长度不能超过1000")
    @TableField(value = "stop_desc", condition = LIKE)
    @Excel(name = "终止原因：业务终止")
    private String stopDesc;

    /**
     * 工单取消原因：转单，拒单，退单原因描述
     */
    @ApiModelProperty(value = "工单取消原因：转单，拒单，退单原因描述")
    @Length(max = 1000, message = "工单取消原因：转单，拒单，退单原因描述长度不能超过1000")
    @TableField(value = "invalid_desc", condition = LIKE)
    @Excel(name = "工单取消原因：转单，拒单，退单原因描述")
    private String invalidDesc;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 200, message = "备注长度不能超过200")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 工单状态
     *             0: 待接单
     *             1: 已接单
     *             2: 已拒单
     *             3: 已结单
     *             4: 已取消
     *             5: 驳回
     */
    @ApiModelProperty(value = "工单状态")
    @TableField("order_status")
    @Excel(name = "工单状态")
    private Integer orderStatus;

    /**
     * 转单ID
     */
    @ApiModelProperty(value = "转单ID")
    @TableField("turn_id")
    @Excel(name = "转单ID")
    private Long turnId;


    @Builder
    public OrderRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Integer businessType, 
                    String businessOrderNumber, String workOrderType, String workOrderNo, String urgency, String workOrderDesc, Long receiveUserId, 
                    String receiveUserName, LocalDateTime receiveTime, String receiveUserMobile, Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, 
                    String reviewDesc, LocalDateTime bookOperationTime, Long endJobUserId, String endJobUserName, LocalDateTime endJobTime, String rejectDesc, 
                    String stopDesc, String invalidDesc, String remark, Integer orderStatus, Long turnId) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.businessType = businessType;
        this.businessOrderNumber = businessOrderNumber;
        this.workOrderType = workOrderType;
        this.workOrderNo = workOrderNo;
        this.urgency = urgency;
        this.workOrderDesc = workOrderDesc;
        this.receiveUserId = receiveUserId;
        this.receiveUserName = receiveUserName;
        this.receiveTime = receiveTime;
        this.receiveUserMobile = receiveUserMobile;
        this.reviewUserId = reviewUserId;
        this.reviewUserName = reviewUserName;
        this.reviewTime = reviewTime;
        this.reviewDesc = reviewDesc;
        this.bookOperationTime = bookOperationTime;
        this.endJobUserId = endJobUserId;
        this.endJobUserName = endJobUserName;
        this.endJobTime = endJobTime;
        this.rejectDesc = rejectDesc;
        this.stopDesc = stopDesc;
        this.invalidDesc = invalidDesc;
        this.remark = remark;
        this.orderStatus = orderStatus;
        this.turnId = turnId;
    }

}

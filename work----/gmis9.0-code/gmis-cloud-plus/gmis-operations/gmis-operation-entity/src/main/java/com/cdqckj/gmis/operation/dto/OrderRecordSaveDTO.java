package com.cdqckj.gmis.operation.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "OrderRecordSaveDTO", description = "客户工单记录")
public class OrderRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @Length(max = 100, message = "客户编号长度不能超过100")
    private String customerCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    @Length(max = 100, message = "客户电话长度不能超过100")
    @Excel(name = "客户名称")
    private String customerMobile;
    /**
     * 客户详细地址
     */
    @ApiModelProperty(value = "客户详细地址")
    @Length(max = 200, message = "客户详细地址")
    @Excel(name = "客户详细地址")
    private String addressDetail;
    /**
     * 表具编号
     */
    @ApiModelProperty(value = "表具编号")
    @Length(max = 100, message = "表具编号长度不能超过100")
    private String meterCode;

    /**
     * 业务类型
     *             1  报装工单
     *             2  安检工单
     *             3  运维工单
     *             4  客服工单
     */
    @ApiModelProperty(value = "业务类型")
    private Integer businessType;
    /**
     * 业务订单编号
     */
    @ApiModelProperty(value = "业务订单编号")
    @Length(max = 32, message = "业务订单编号长度不能超过32")
    private String businessOrderNumber;
    /**
     * 工单类型
     *             survey: 踏勘
     *             install: 施工
     */
    @ApiModelProperty(value = "工单类型")
    @Length(max = 32, message = "工单类型长度不能超过32")
    private String workOrderType;
    /**
     * 工单编号
     */
    @ApiModelProperty(value = "工单编号")
    @Length(max = 32, message = "工单编号长度不能超过32")
    private String workOrderNo;
    /**
     * 紧急程度
     *             normal:正常
     *             urgent:紧急
     *             very_urgent:非常紧急
     */
    @ApiModelProperty(value = "紧急程度")
    @Length(max = 20, message = "紧急程度长度不能超过20")
    private String urgency;
    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容")
    @Length(max = 1000, message = "工单内容长度不能超过1000")
    private String workOrderDesc;
    /**
     * 接单人ID
     */
    @ApiModelProperty(value = "接单人ID")
    private Long receiveUserId;
    /**
     * 接单人名称
     */
    @ApiModelProperty(value = "接单人名称")
    @Length(max = 100, message = "接单人名称长度不能超过100")
    private String receiveUserName;
    /**
     * 接单时间
     */
    @ApiModelProperty(value = "接单时间")
    private LocalDateTime receiveTime;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    private String receiveUserMobile;

    @ApiModelProperty(value = "")
    private Long reviewUserId;
    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    private String reviewUserName;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 1000, message = "审核意见长度不能超过1000")
    private String reviewDesc;
    /**
     * 预约处理时间
     */
    @ApiModelProperty(value = "预约处理时间")
    private LocalDateTime bookOperationTime;
    /**
     * 结单人ID
     */
    @ApiModelProperty(value = "结单人ID")
    private Long endJobUserId;
    /**
     * 结单人名称
     */
    @ApiModelProperty(value = "结单人名称")
    @Length(max = 100, message = "结单人名称长度不能超过100")
    private String endJobUserName;
    /**
     * 结单时间
     */
    @ApiModelProperty(value = "结单时间")
    private LocalDateTime endJobTime;
    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    @Length(max = 1000, message = "驳回原因长度不能超过1000")
    private String rejectDesc;
    /**
     * 终止原因：业务终止
     */
    @ApiModelProperty(value = "终止原因：业务终止")
    @Length(max = 1000, message = "终止原因：业务终止长度不能超过1000")
    private String stopDesc;
    /**
     * 工单取消原因：转单，拒单，退单原因描述
     */
    @ApiModelProperty(value = "工单取消原因：转单，拒单，退单原因描述")
    @Length(max = 1000, message = "工单取消原因：转单，拒单，退单原因描述长度不能超过1000")
    private String invalidDesc;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 200, message = "备注长度不能超过200")
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
    private Integer orderStatus;
    /**
     * 转单ID
     */
    @ApiModelProperty(value = "转单ID")
    private Long turnId;

}

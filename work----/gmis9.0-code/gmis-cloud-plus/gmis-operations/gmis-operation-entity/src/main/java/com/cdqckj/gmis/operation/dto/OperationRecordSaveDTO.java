package com.cdqckj.gmis.operation.dto;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "OperationRecordSaveDTO", description = "运行维护工单")
public class OperationRecordSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @NotEmpty(message = "公司CODE不能为空")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 300, message = "公司名称长度不能超过300")
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
    @Length(max = 32, message = "客户编号长度不能超过32")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 60, message = "客户名称长度不能超过60")
    private String customerName;
    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 气表厂家ID
     */
    @ApiModelProperty(value = "气表厂家ID")
    private Long gasMeterFactoryId;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型ID")
    private Long useGasTypeId;
    /**
     * 运维受理编号
     */
    @ApiModelProperty(value = "运维受理编号")
    @Length(max = 32, message = "运维受理编号长度不能超过32")
    private String acceptNo;
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
     * 接单人电话
     */
    @ApiModelProperty(value = "接单人电话")
    @Length(max = 20, message = "接单人电话长度不能超过20")
    private String receiveUserPhone;
    /**
     * 接单人时间
     */
    @ApiModelProperty(value = "接单人时间")
    private LocalDateTime receiveUserTime;
    /**
     * 预约时间
     */
    @ApiModelProperty(value = "预约时间")
    private LocalDateTime bookOperationTime;
    /**
     * 审批人ID
     */
    @ApiModelProperty(value = "审批人ID")
    private Long reviewUserId;
    /**
     * 审批人名称
     */
    @ApiModelProperty(value = "审批人名称")
    @Length(max = 100, message = "审批人名称长度不能超过100")
    private String reviewUserName;
    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    private LocalDateTime reviewTime;
    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    @Length(max = 100, message = "审批意见长度不能超过100")
    private String reviewObjection;
    /**
     * 派单人ID
     */
    @ApiModelProperty(value = "派单人ID")
    private Long distributionUserId;
    /**
     * 派单人名称
     */
    @ApiModelProperty(value = "派单人名称")
    @Length(max = 100, message = "派单人名称长度不能超过100")
    private String distributionUserName;
    /**
     * 派单时间
     */
    @ApiModelProperty(value = "派单时间")
    private LocalDateTime distributionTime;
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
    private String rejectReason;
    /**
     * 终止人ID
     */
    @ApiModelProperty(value = "终止人ID")
    private Long terminateUserId;
    /**
     * 终止人名称
     */
    @ApiModelProperty(value = "终止人名称")
    @Length(max = 100, message = "终止人名称长度不能超过100")
    private String terminateUserName;
    /**
     * 终止时间
     */
    @ApiModelProperty(value = "终止时间")
    private LocalDateTime terminateTime;
    /**
     * 终止原因
     */
    @ApiModelProperty(value = "终止原因")
    @Length(max = 1000, message = "终止原因长度不能超过1000")
    private String stopDesc;
    /**
     * 工单类型
     *             0 普通运行维护
     *             1 点火通气
     */
    @ApiModelProperty(value = "工单类型")
    private Integer orderType;
    /**
     * 紧急程度
     *             0 正常
     *             1 紧急
     *             2 非常紧急
     */
    @ApiModelProperty(value = "紧急程度")
    private Integer urgencyLevel;
    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容")
    @Length(max = 1000, message = "工单内容长度不能超过1000")
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
    private Integer operationProcessState;
    /**
     * 转单ID
     */
    @ApiModelProperty(value = "转单ID")
    private Long transferId;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;

}

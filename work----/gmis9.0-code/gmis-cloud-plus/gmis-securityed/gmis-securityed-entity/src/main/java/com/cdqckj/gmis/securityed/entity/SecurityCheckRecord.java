package com.cdqckj.gmis.securityed.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 安检计划记录
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_security_check_record")
@ApiModel(value = "SecurityCheckRecord", description = "安检计划记录")
@AllArgsConstructor
public class SecurityCheckRecord extends Entity<Long> {

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
     * 安检编号
     */
    @ApiModelProperty(value = "安检编号")
    @Length(max = 32, message = "安检编号长度不能超过32")
    @TableField(value = "sc_no", condition = LIKE)
    @Excel(name = "安检编号")
    private String scNo;

    /**
     * 计划安检员ID
     */
    @ApiModelProperty(value = "计划安检员ID")
    @TableField("plan_security_check_user_id")
    @Excel(name = "计划安检员ID")
    private Long planSecurityCheckUserId;

    /**
     * 计划安检员名称
     */
    @ApiModelProperty(value = "计划安检员名称")
    @Length(max = 100, message = "计划安检员名称长度不能超过100")
    @TableField(value = "plan_security_check_user_name", condition = LIKE)
    @Excel(name = "计划安检员名称")
    private String planSecurityCheckUserName;

    /**
     * 计划安检时间
     */
    @ApiModelProperty(value = "计划安检时间")
    @TableField("plan_security_check_time")
    @Excel(name = "计划安检时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime planSecurityCheckTime;

    /**
     * 安检员ID
     */
    @ApiModelProperty(value = "安检员ID")
    @TableField("security_check_user_id")
    @Excel(name = "安检员ID")
    private Long securityCheckUserId;

    /**
     * 安检员名称
     */
    @ApiModelProperty(value = "安检员名称")
    @Length(max = 100, message = "安检员名称长度不能超过100")
    @TableField(value = "security_check_user_name", condition = LIKE)
    @Excel(name = "安检员名称")
    private String securityCheckUserName;

    /**
     * 安检时间
     */
    @ApiModelProperty(value = "安检时间")
    @TableField("security_check_time")
    @Excel(name = "安检时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime securityCheckTime;

    /**
     * 安检内容
     */
    @ApiModelProperty(value = "安检内容")
    @Length(max = 1000, message = "安检内容长度不能超过1000")
    @TableField(value = "security_check_content", condition = LIKE)
    @Excel(name = "安检内容")
    private String securityCheckContent;

    /**
     * 安检结果
     */
    @ApiModelProperty(value = "安检结果")
    @TableField("security_check_result")
    @Excel(name = "安检结果")
    private Integer securityCheckResult;

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
     * 派工人ID
     */
    @ApiModelProperty(value = "派工人ID")
    @TableField("distribution_user_id")
    @Excel(name = "派工人ID")
    private Long distributionUserId;

    /**
     * 派工人名称
     */
    @ApiModelProperty(value = "派工人名称")
    @Length(max = 100, message = "派工人名称长度不能超过100")
    @TableField(value = "distribution_user_name", condition = LIKE)
    @Excel(name = "派工人名称")
    private String distributionUserName;

    /**
     * 派人时间
     */
    @ApiModelProperty(value = "派人时间")
    @TableField("distribution_time")
    @Excel(name = "派人时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
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

    @ApiModelProperty(value = "驳回id")
    @TableField(value = "reject_user_id")
    @Excel(name = "驳回id")
    private Long rejectUserId;

    @ApiModelProperty(value = "驳回人姓名")
    @Length(max = 1000, message = "驳回人姓名长度不能超过1000")
    @TableField(value = "reject_user_name", condition = LIKE)
    @Excel(name = "驳回人姓名")
    private String rejectUserName;

    @ApiModelProperty(value = "驳回时间")
    @TableField(value = "reject_time", condition = LIKE)
    @Excel(name = "驳回时间")
    private LocalDateTime rejectTime;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    @Length(max = 1000, message = "驳回原因长度不能超过1000")
    @TableField(value = "reject_desc", condition = LIKE)
    @Excel(name = "驳回原因")
    private String rejectDesc;

    /**
     * 终止人ID
     */
    @ApiModelProperty(value = "终止人ID")
    @TableField("stop_user_id")
    @Excel(name = "终止人ID")
    private Long stopUserId;

    /**
     * 终止人名称
     */
    @ApiModelProperty(value = "终止人名称")
    @Length(max = 100, message = "终止人名称长度不能超过100")
    @TableField(value = "stop_user_name", condition = LIKE)
    @Excel(name = "终止人名称")
    private String stopUserName;

    /**
     * 终止时间
     */
    @ApiModelProperty(value = "终止时间")
    @TableField("stop_time")
    @Excel(name = "终止时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stopTime;

    /**
     * 终止原因
     */
    @ApiModelProperty(value = "终止原因")
    @Length(max = 1000, message = "终止原因长度不能超过1000")
    @TableField(value = "stop_desc", condition = LIKE)
    @Excel(name = "终止原因")
    private String stopDesc;

    /**
     * 安检状态:
     *             0 待提审
     *             1 待审核
     *             2 待派工
     *             3 待安检
     *             4 待回访
     *             5  异常
     *             6  已结单
     *             7  终止 
     *             
     */
    @ApiModelProperty(value = "安检状态:")
    @TableField("data_status")
    @Excel(name = "安检状态:")
    private Integer dataStatus;

    /**
     * 工单状态
     *             0: 待接单
     *             1: 已接单
     *             2: 已拒单
     *             3: 已结单
     *             4: 已取消
     */
    @ApiModelProperty(value = "工单状态")
    @TableField("order_state")
    @Excel(name = "工单状态")
    private Integer orderState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;



    @Builder
    public SecurityCheckRecord(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String customerCode, 
                    String customerName, String gasCode, String gasMeterName, Long gasMeterFactoryId, Long useGasTypeId, String scNo, 
                    Long planSecurityCheckUserId, String planSecurityCheckUserName, LocalDateTime planSecurityCheckTime, Long securityCheckUserId, String securityCheckUserName, LocalDateTime securityCheckTime, 
                    String securityCheckContent, Integer securityCheckResult, Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, String reviewObjection, 
                    Long distributionUserId, String distributionUserName, LocalDateTime distributionTime, Long endJobUserId, String endJobUserName, LocalDateTime endJobTime, 
                    String rejectDesc, Long stopUserId, String stopUserName, LocalDateTime stopTime, String stopDesc, Integer dataStatus, 
                    Integer orderState, String remark,Long rejectUserId,String rejectUserName,LocalDateTime rejectTime) {
        this.id = id;
        this.rejectTime=rejectTime;
        this.rejectUserId=rejectUserId;
        this.rejectUserName=rejectUserName;
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
        this.scNo = scNo;
        this.planSecurityCheckUserId = planSecurityCheckUserId;
        this.planSecurityCheckUserName = planSecurityCheckUserName;
        this.planSecurityCheckTime = planSecurityCheckTime;
        this.securityCheckUserId = securityCheckUserId;
        this.securityCheckUserName = securityCheckUserName;
        this.securityCheckTime = securityCheckTime;
        this.securityCheckContent = securityCheckContent;
        this.securityCheckResult = securityCheckResult;
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
        this.rejectDesc = rejectDesc;
        this.stopUserId = stopUserId;
        this.stopUserName = stopUserName;
        this.stopTime = stopTime;
        this.stopDesc = stopDesc;
        this.dataStatus = dataStatus;
        this.orderState = orderState;
        this.remark = remark;
    }

}

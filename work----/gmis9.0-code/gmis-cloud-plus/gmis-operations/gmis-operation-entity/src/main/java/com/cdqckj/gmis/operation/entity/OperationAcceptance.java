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
 * 运行维护受理
 * </p>
 *
 * @author gmis
 * @since 2020-08-03
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_operation_acceptance")
@ApiModel(value = "OperationAcceptance", description = "运行维护受理")
@AllArgsConstructor
public class OperationAcceptance extends Entity<Long> {

    private static final long serialVersionUID = OperationAcceptance.class.hashCode();

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
     * 报警人编号
     */
    @ApiModelProperty(value = "报警人编号")
    @Length(max = 32, message = "报警人编号长度不能超过32")
    @TableField(value = "alarm_customer_code", condition = LIKE)
    @Excel(name = "报警人编号")
    private String alarmCustomerCode;

    /**
     * 报警人名称
     */
    @ApiModelProperty(value = "报警人名称")
    @Length(max = 60, message = "报警人名称长度不能超过60")
    @TableField(value = "alarm_customer_name", condition = LIKE)
    @Excel(name = "报警人名称")
    private String alarmCustomerName;

    /**
     * 报警人电话
     */
    @ApiModelProperty(value = "报警人电话")
    @Length(max = 20, message = "报警人电话长度不能超过20")
    @TableField(value = "alarm_customer_phone", condition = LIKE)
    @Excel(name = "报警人电话")
    private String alarmCustomerPhone;

    /**
     * 报警内容
     */
    @ApiModelProperty(value = "报警内容")
    @Length(max = 1000, message = "报警内容长度不能超过1000")
    @TableField(value = "alarm_content", condition = LIKE)
    @Excel(name = "报警内容")
    private String alarmContent;

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
    @TableField(value = "oper_accept_no", condition = LIKE)
    @Excel(name = "运维受理编号")
    private String operAcceptNo;

    /**
     * 计划处警人ID
     */
    @ApiModelProperty(value = "计划处警人ID")
    @Length(max = 32, message = "计划处警人ID长度不能超过32")
    @TableField(value = "plan_handle_user_id", condition = LIKE)
    @Excel(name = "计划处警人ID")
    private String planHandleUserId;

    /**
     * 计划处警人名称
     */
    @ApiModelProperty(value = "计划处警人名称")
    @Length(max = 100, message = "计划处警人名称长度不能超过100")
    @TableField(value = "plan_handle_user_name", condition = LIKE)
    @Excel(name = "计划处警人名称")
    private String planHandleUserName;

    /**
     * 计划处警时间
     */
    @ApiModelProperty(value = "计划处警时间")
    @TableField("plan_handle_time")
    @Excel(name = "计划处警时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime planHandleTime;

    /**
     * 处警人ID
     */
    @ApiModelProperty(value = "处警人ID")
    @TableField("handle_user_id")
    @Excel(name = "处警人ID")
    private Long handleUserId;

    /**
     * 处警人名称
     */
    @ApiModelProperty(value = "处警人名称")
    @Length(max = 100, message = "处警人名称长度不能超过100")
    @TableField(value = "handle_user_name", condition = LIKE)
    @Excel(name = "处警人名称")
    private String handleUserName;

    /**
     * 处警时间
     */
    @ApiModelProperty(value = "处警时间")
    @TableField("handle_time")
    @Excel(name = "处警时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime handleTime;

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
     * 受理状态:
     *              0 待处理
     *              1 已处理
     *              2 未处理
     */
    @ApiModelProperty(value = "受理状态:")
    @TableField("accept_status")
    @Excel(name = "受理状态:")
    private Integer acceptStatus;

    @ApiModelProperty(value = "")
    @TableField("accept_process_state")
    @Excel(name = "")
    private Integer acceptProcessState;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public OperationAcceptance(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String alarmCustomerCode, 
                    String alarmCustomerName, String alarmCustomerPhone, String alarmContent, String gasCode, String gasMeterName, Long gasMeterFactoryId, 
                    Long useGasTypeId, String operAcceptNo, String planHandleUserId, String planHandleUserName, LocalDateTime planHandleTime, Long handleUserId, 
                    String handleUserName, LocalDateTime handleTime, Long terminateUserId, String terminateUserName, LocalDateTime terminateTime, String stopDesc, 
                    Integer acceptStatus, Integer acceptProcessState, String remark) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.alarmCustomerCode = alarmCustomerCode;
        this.alarmCustomerName = alarmCustomerName;
        this.alarmCustomerPhone = alarmCustomerPhone;
        this.alarmContent = alarmContent;
        this.gasCode = gasCode;
        this.gasMeterName = gasMeterName;
        this.gasMeterFactoryId = gasMeterFactoryId;
        this.useGasTypeId = useGasTypeId;
        this.operAcceptNo = operAcceptNo;
        this.planHandleUserId = planHandleUserId;
        this.planHandleUserName = planHandleUserName;
        this.planHandleTime = planHandleTime;
        this.handleUserId = handleUserId;
        this.handleUserName = handleUserName;
        this.handleTime = handleTime;
        this.terminateUserId = terminateUserId;
        this.terminateUserName = terminateUserName;
        this.terminateTime = terminateTime;
        this.stopDesc = stopDesc;
        this.acceptStatus = acceptStatus;
        this.acceptProcessState = acceptProcessState;
        this.remark = remark;
    }

}

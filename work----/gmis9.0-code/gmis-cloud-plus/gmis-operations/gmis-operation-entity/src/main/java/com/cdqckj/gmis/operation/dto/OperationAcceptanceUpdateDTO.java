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
 * 运行维护受理
 * </p>
 *
 * @author gmis
 * @since 2020-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "OperationAcceptanceUpdateDTO", description = "运行维护受理")
public class OperationAcceptanceUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

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
     * 报警人编号
     */
    @ApiModelProperty(value = "报警人编号")
    @Length(max = 32, message = "报警人编号长度不能超过32")
    private String alarmCustomerCode;
    /**
     * 报警人名称
     */
    @ApiModelProperty(value = "报警人名称")
    @Length(max = 60, message = "报警人名称长度不能超过60")
    private String alarmCustomerName;
    /**
     * 报警人电话
     */
    @ApiModelProperty(value = "报警人电话")
    @Length(max = 20, message = "报警人电话长度不能超过20")
    private String alarmCustomerPhone;
    /**
     * 报警内容
     */
    @ApiModelProperty(value = "报警内容")
    @Length(max = 1000, message = "报警内容长度不能超过1000")
    private String alarmContent;
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
    private String operAcceptNo;
    /**
     * 计划处警人ID
     */
    @ApiModelProperty(value = "计划处警人ID")
    @Length(max = 32, message = "计划处警人ID长度不能超过32")
    private String planHandleUserId;
    /**
     * 计划处警人名称
     */
    @ApiModelProperty(value = "计划处警人名称")
    @Length(max = 100, message = "计划处警人名称长度不能超过100")
    private String planHandleUserName;
    /**
     * 计划处警时间
     */
    @ApiModelProperty(value = "计划处警时间")
    private LocalDateTime planHandleTime;
    /**
     * 处警人ID
     */
    @ApiModelProperty(value = "处警人ID")
    private Long handleUserId;
    /**
     * 处警人名称
     */
    @ApiModelProperty(value = "处警人名称")
    @Length(max = 100, message = "处警人名称长度不能超过100")
    private String handleUserName;
    /**
     * 处警时间
     */
    @ApiModelProperty(value = "处警时间")
    private LocalDateTime handleTime;
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
     * 受理状态:
     *              0 待处理
     *              1 已处理
     *              2 未处理
     */
    @ApiModelProperty(value = "受理状态:")
    private Integer acceptStatus;
    @ApiModelProperty(value = "")
    private Integer acceptProcessState;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;
}

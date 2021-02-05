package com.cdqckj.gmis.securityed.dto;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SecurityCheckRecordUpdateDTO", description = "安检计划记录")
public class SecurityCheckRecordUpdateDTO implements Serializable {

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
     * 安检编号
     */
    @ApiModelProperty(value = "安检编号")
    @Length(max = 32, message = "安检编号长度不能超过32")
    private String scNo;
    /**
     * 计划安检员ID
     */
    @ApiModelProperty(value = "计划安检员ID")
    private Long planSecurityCheckUserId;
    /**
     * 计划安检员名称
     */
    @ApiModelProperty(value = "计划安检员名称")
    @Length(max = 100, message = "计划安检员名称长度不能超过100")
    private String planSecurityCheckUserName;
    /**
     * 计划安检时间
     */
    @ApiModelProperty(value = "计划安检时间")
    private LocalDateTime planSecurityCheckTime;
    /**
     * 安检员ID
     */
    @ApiModelProperty(value = "安检员ID")
    private Long securityCheckUserId;
    /**
     * 安检员名称
     */
    @ApiModelProperty(value = "安检员名称")
    @Length(max = 100, message = "安检员名称长度不能超过100")
    private String securityCheckUserName;
    /**
     * 安检时间
     */
    @ApiModelProperty(value = "安检时间")
    private LocalDateTime securityCheckTime;
    /**
     * 安检内容
     */
    @ApiModelProperty(value = "安检内容")
    @Length(max = 1000, message = "安检内容长度不能超过1000")
    private String securityCheckContent;
    /**
     * 安检结果
     */
    @ApiModelProperty(value = "安检结果")
    private Integer securityCheckResult;
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
     * 派工人ID
     */
    @ApiModelProperty(value = "派工人ID")
    private Long distributionUserId;
    /**
     * 派工人名称
     */
    @ApiModelProperty(value = "派工人名称")
    @Length(max = 100, message = "派工人名称长度不能超过100")
    private String distributionUserName;
    /**
     * 派人时间
     */
    @ApiModelProperty(value = "派人时间")
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

    @ApiModelProperty(value = "驳回id")
    private Long rejectUserID;

    @ApiModelProperty(value = "驳回人姓名")
    @Length(max = 1000, message = "驳回人姓名长度不能超过1000")
    private String rejectUserName;

    @ApiModelProperty(value = "驳回时间")
    @TableField(value = "reject_time", condition = LIKE)
    private LocalDateTime rejectTime;
    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因")
    @Length(max = 1000, message = "驳回原因长度不能超过1000")
    private String rejectDesc;
    /**
     * 终止人ID
     */
    @ApiModelProperty(value = "终止人ID")
    private Long stopUserId;
    /**
     * 终止人名称
     */
    @ApiModelProperty(value = "终止人名称")
    @Length(max = 100, message = "终止人名称长度不能超过100")
    private String stopUserName;
    /**
     * 终止时间
     */
    @ApiModelProperty(value = "终止时间")
    private LocalDateTime stopTime;
    /**
     * 终止原因
     */
    @ApiModelProperty(value = "终止原因")
    @Length(max = 1000, message = "终止原因长度不能超过1000")
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
    private Integer orderState;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;
}

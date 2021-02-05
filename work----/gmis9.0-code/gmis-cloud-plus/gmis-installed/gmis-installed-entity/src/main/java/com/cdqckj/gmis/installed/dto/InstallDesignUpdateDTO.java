package com.cdqckj.gmis.installed.dto;

import java.math.BigDecimal;
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
 * 报装设计及预算
 * </p>
 *
 * @author tp
 * @since 2020-11-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InstallDesignUpdateDTO", description = "报装设计及预算")
public class InstallDesignUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
//    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 报装编码
     */
    @ApiModelProperty(value = "报装编码")
    @Length(max = 32, message = "报装编码长度不能超过32")
    private String installNumber;
    /**
     * 草图地址
     */
    @ApiModelProperty(value = "草图地址")
    @Length(max = 255, message = "草图地址长度不能超过255")
    private String sketchUrl;
    /**
     * 草图设计人
     */
    @ApiModelProperty(value = "草图设计人")
    private Long sketchUser;
    /**
     * 草图设计人
     */
    @ApiModelProperty(value = "草图设计人名称")
    private String sketchUserName;
    /**
     * 设计图地址
     */
    @ApiModelProperty(value = "设计图地址")
    private String designUrl;
    /**
     * 设计图设计人
     */
    @ApiModelProperty(value = "设计图设计人")
    private Long designUser;


    /**
     * 安装方案设计人名称
     */
    @ApiModelProperty(value = "安装方案设计人名称")
    @Length(max = 100, message = "安装方案设计人名称长度不能超过100")
    private String designUserName;
    /**
     * 预算金额
     */
    @ApiModelProperty(value = "预算金额")
    private BigDecimal budget;
    /**
     * 预算人
     */
    @ApiModelProperty(value = "预算人")
    private Long budgetUser;
    /**
     * 预算时间
     */
    @ApiModelProperty(value = "预算时间")
    private LocalDateTime budgetTime;
    /**
     * 施工工作量描述
     */
    @ApiModelProperty(value = "施工工作量描述")
    @Length(max = 65535, message = "施工工作量描述长度不能超过65,535")
    private String workload;
    /**
     * 流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)
     */
    @ApiModelProperty(value = "流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)")
    @Length(max = 255, message = "流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)长度不能超过255")
    private String processStatus;
    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    private Long reviewUserId;
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
    private String reviewObjection;
    /**
     * 数据状态1-有效，0-无效
     */
    @ApiModelProperty(value = "数据状态1-有效，0-无效")
    private Integer dataStatus;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 65535, message = "描述长度不能超过65,535")
    private String remark;
}

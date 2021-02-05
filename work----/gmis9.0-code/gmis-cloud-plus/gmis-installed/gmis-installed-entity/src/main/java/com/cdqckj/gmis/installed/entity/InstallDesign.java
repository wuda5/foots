package com.cdqckj.gmis.installed.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * 报装设计及预算
 * </p>
 *
 * @author tp
 * @since 2020-11-10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_install_design")
@ApiModel(value = "InstallDesign", description = "报装设计及预算")
@AllArgsConstructor
public class InstallDesign extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 报装编码
     */
    @ApiModelProperty(value = "报装编码")
    @Length(max = 32, message = "报装编码长度不能超过32")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "报装编码")
    private String installNumber;

    /**
     * 草图地址
     */
    @ApiModelProperty(value = "草图地址")
    @Length(max = 255, message = "草图地址长度不能超过255")
    @TableField(value = "sketch_url", condition = LIKE)
    @Excel(name = "草图地址")
    private String sketchUrl;

    /**
     * 草图设计人
     */
    @ApiModelProperty(value = "草图设计人")
    @TableField("sketch_user")
    @Excel(name = "草图设计人")
    private Long sketchUser;

    /**
     * 设计图地址
     */
    @ApiModelProperty(value = "设计图地址")
    @TableField(value = "design_url", condition = LIKE)
    @Excel(name = "设计图地址")
    private String designUrl;

    /**
     * 设计图设计人
     */
    @ApiModelProperty(value = "设计图设计人")
    @TableField("design_user")
    @Excel(name = "设计图设计人")
    private Long designUser;

    /**
     * 安装方案设计人名称
     */
    @ApiModelProperty(value = "安装方案设计人名称")
    @Length(max = 100, message = "安装方案设计人名称长度不能超过100")
    @TableField(value = "design_user_name", condition = LIKE)
    @Excel(name = "安装方案设计人名称")
    private String designUserName;

    /**
     * 预算金额
     */
    @ApiModelProperty(value = "预算金额")
    @TableField("budget")
    @Excel(name = "预算金额")
    private BigDecimal budget;

    /**
     * 预算人
     */
    @ApiModelProperty(value = "预算人")
    @TableField("budget_user")
    @Excel(name = "预算人")
    private Long budgetUser;

    /**
     * 预算时间
     */
    @ApiModelProperty(value = "预算时间")
    @TableField("budget_time")
    @Excel(name = "预算时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime budgetTime;

    /**
     * 施工工作量描述
     */
    @ApiModelProperty(value = "施工工作量描述")
    @Length(max = 65535, message = "施工工作量描述长度不能超过65535")
    @TableField("workload")
    @Excel(name = "施工工作量描述")
    private String workload;

    /**
     * 流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)
     */
    @ApiModelProperty(value = "流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)")
    @Length(max = 255, message = "流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)长度不能超过255")
    @TableField(value = "process_status", condition = LIKE)
    @Excel(name = "流程状态(TO_BE_REVIEWED-待审核,SUBMIT_FOR_REVIEW-提审,REVIEW_REJECTED-审核驳回,APPROVED-审核通过)")
    private String processStatus;

    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    @TableField("review_user_id")
    @Excel(name = "审核人id")
    private Long reviewUserId;

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
    @TableField(value = "review_objection", condition = LIKE)
    @Excel(name = "审核意见")
    private String reviewObjection;

    /**
     * 数据状态1-有效，0-无效
     */
    @ApiModelProperty(value = "数据状态1-有效，0-无效")
    @TableField("data_status")
    @Excel(name = "数据状态1-有效，0-无效")
    private Integer dataStatus;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 65535, message = "描述长度不能超过65535")
    @TableField("remark")
    @Excel(name = "描述")
    private String remark;


    @Builder
    public InstallDesign(Long id, Long updateUser, LocalDateTime updateTime, Long createUser, LocalDateTime createTime, 
                    String installNumber, String sketchUrl, Long sketchUser, String designUrl, Long designUser, 
                    BigDecimal budget, Long budgetUser, LocalDateTime budgetTime, String workload, String processStatus, Long reviewUserId, 
                    LocalDateTime reviewTime, String reviewObjection, Integer dataStatus, String remark) {
        this.id = id;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.createTime = createTime;
        this.installNumber = installNumber;
        this.sketchUrl = sketchUrl;
        this.sketchUser = sketchUser;
        this.designUrl = designUrl;
        this.designUser = designUser;
        this.budget = budget;
        this.budgetUser = budgetUser;
        this.budgetTime = budgetTime;
        this.workload = workload;
        this.processStatus = processStatus;
        this.reviewUserId = reviewUserId;
        this.reviewTime = reviewTime;
        this.reviewObjection = reviewObjection;
        this.dataStatus = dataStatus;
        this.remark = remark;
    }

}

package com.cdqckj.gmis.securityed.entity;

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
 * 隐患列表
 * </p>
 *
 * @author gmis
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_security_check_items")
@ApiModel(value = "SecurityCheckItems", description = "隐患列表")
@AllArgsConstructor
public class SecurityCheckItems extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否选中
     */
    @ApiModelProperty(value = " 是否选中")
    @TableField(exist=false)
    private Boolean selected = false;

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
     * 安检计划编号
     */
    @ApiModelProperty(value = "安检计划编号")
    @TableField("sc_no")
    @Excel(name = "安检计划编号")
    private String scNo;

    /**
     * 隐患等级
     */
    @ApiModelProperty(value = "隐患等级")
    @TableField("danger_leve")
    @Excel(name = "隐患等级")
    private String dangerLeve;

    /**
     * 安检结果id
     */
    @ApiModelProperty(value = "安检结果id")
    @TableField("sc_result_id")
    @Excel(name = "安检结果id")
    private Long scResultId;

    /**
     * 安安检项Code检项ID
     */
    @ApiModelProperty(value = "安检项Code")
    @Length(max = 32, message = "安检项ID长度不能超过32")
    @TableField(value = "sc_term_code", condition = LIKE)
    @Excel(name = "安检项Code")
    private String scTermCode;

    /**
     * 安检项名称
     */
    @ApiModelProperty(value = "安检项名称")
    @Length(max = 100, message = "安检项名称长度不能超过100")
    @TableField(value = "sc_term_name", condition = LIKE)
    @Excel(name = "安检项名称")
    private String scTermName;

    @ApiModelProperty(value = "")
    @TableField("sc_term_items_id")
    @Excel(name = "")
    private Long scTermItemsId;

    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    @TableField(value = "sc_term_items_name", condition = LIKE)
    @Excel(name = "")
    private String scTermItemsName;

    /**
     * 安检隐患
     */
    @ApiModelProperty(value = "安检隐患")
    @Length(max = 1000, message = "安检隐患长度不能超过1000")
    @TableField(value = "hidden_danger", condition = LIKE)
    @Excel(name = "安检隐患")
    private String hiddenDanger;

    /**
     * 整改信息
     */
    @ApiModelProperty(value = "整改信息")
    @Length(max = 1000, message = "整改信息长度不能超过1000")
    @TableField(value = "handle_info", condition = LIKE)
    @Excel(name = "整改信息")
    private String handleInfo;

    /**
     * 隐患处理状态
     */
    @ApiModelProperty(value = "隐患处理状态:0 未处理， 1 已处理（在输入对应的隐患整改信息时，完成修改为 1 已处理 ）")
    @TableField("handle_status")
    @Excel(name = "隐患处理状态")
    private Integer handleStatus;

    /**
     * 隐患处理登记人ID
     */
    @ApiModelProperty(value = "隐患处理登记人ID")
    @TableField("handle_user_id")
    @Excel(name = "隐患处理登记人ID")
    private Long handleUserId;

    /**
     * 隐患处理登记时间
     */
    @ApiModelProperty(value = "隐患处理登记时间")
    @TableField("handle_time")
    @Excel(name = "隐患处理登记时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime handleTime;

    /**
     * 附件信息
     */
    @ApiModelProperty(value = "附件信息")
    @Length(max = 1000, message = "附件信息长度不能超过1000")
    @TableField(value = "attache_info", condition = LIKE)
    @Excel(name = "附件信息")
    private String attacheInfo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public SecurityCheckItems(Long id, LocalDateTime createTime, Long createUser, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, Long scResultId, String dangerLeve,
                    String scTermCode, String scTermName, Long scTermItemsId, String scTermItemsName, String hiddenDanger, String handleInfo, 
                    Integer handleStatus, Long handleUserId, LocalDateTime handleTime, String attacheInfo, String remark) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.scResultId = scResultId;
        this.scTermCode = scTermCode;
        this.scTermName = scTermName;
        this.scTermItemsId = scTermItemsId;
        this.scTermItemsName = scTermItemsName;
        this.hiddenDanger = hiddenDanger;
        this.handleInfo = handleInfo;
        this.handleStatus = handleStatus;
        this.handleUserId = handleUserId;
        this.handleTime = handleTime;
        this.attacheInfo = attacheInfo;
        this.remark = remark;
        this.dangerLeve=dangerLeve;
    }

}

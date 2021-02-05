package com.cdqckj.gmis.operation.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 运行维护流程操作记录
 * </p>
 *
 * @author gmis
 * @since 2020-08-04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_operation_process")
@ApiModel(value = "OperationProcess", description = "运行维护流程操作记录")
@AllArgsConstructor
public class OperationProcess extends Entity<Long> {

    private static final long serialVersionUID = OperationProcess.class.hashCode();

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
     * 运行维护受理ID
     */
    @ApiModelProperty(value = "运行维护受理ID")
    @TableField("accept_id")
    @Excel(name = "运行维护受理ID")
    private String acceptId;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    @Length(max = 32, message = "流程状态长度不能超过32")
    @TableField(value = "process_state", condition = LIKE)
    @Excel(name = "流程状态")
    private String processState;

    /**
     * 操作状态
     */
    @ApiModelProperty(value = "操作状态")
    @Length(max = 32, message = "操作状态长度不能超过32")
    @TableField(value = "oper_process_state", condition = LIKE)
    @Excel(name = "操作状态")
    private String operProcessState;

    /**
     * 操作备注
     */
    @ApiModelProperty(value = "操作备注")
    @Length(max = 32, message = "操作备注长度不能超过32")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "操作备注")
    private String remark;


    @Builder
    public OperationProcess(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String acceptId,
                    String processState, String operProcessState, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.acceptId = acceptId;
        this.processState = processState;
        this.operProcessState = operProcessState;
        this.remark = remark;
    }

}

package com.cdqckj.gmis.installed.entity;

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
 * 报装流程操作记录
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
@TableName("gc_install_process_record")
@ApiModel(value = "InstallProcessRecord", description = "报装流程操作记录")
@AllArgsConstructor
public class InstallProcessRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

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
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @TableField("install_number")
    @Excel(name = "报装编号")
    private String installNumber;

    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    @TableField("process_state")
    @Excel(name = "流程状态")
    private Integer processState;

    /**
     * 操作人员ID
     */
    @ApiModelProperty(value = "操作人员ID")
    @TableField("process_user_id")
    @Excel(name = "操作人员ID")
    private Long processUserId;

    /**
     * 操作人员名称
     */
    @ApiModelProperty(value = "操作人员名称")
    @Length(max = 32, message = "操作人员名称长度不能超过32")
    @TableField(value = "process_user_name", condition = LIKE)
    @Excel(name = "操作人员名称")
    private String processUserName;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @TableField("process_time")
    @Excel(name = "操作时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime processTime;

    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    @Length(max = 1000, message = "操作描述长度不能超过1000")
    @TableField(value = "process_desc", condition = LIKE)
    @Excel(name = "操作描述")
    private String processDesc;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Length(max = 32, message = "创建人名称长度不能超过32")
    @TableField(value = "create_user_name", condition = LIKE)
    @Excel(name = "创建人名称")
    private String createUserName;


    @Builder
    public InstallProcessRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String companyCode, String companyName, Long orgId, String orgName, String installNumber,
                    Integer processState, Long processUserId, String processUserName, LocalDateTime processTime, String processDesc, String createUserName) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.installNumber = installNumber;
        this.processState = processState;
        this.processUserId = processUserId;
        this.processUserName = processUserName;
        this.processTime = processTime;
        this.processDesc = processDesc;
        this.createUserName = createUserName;
    }

}

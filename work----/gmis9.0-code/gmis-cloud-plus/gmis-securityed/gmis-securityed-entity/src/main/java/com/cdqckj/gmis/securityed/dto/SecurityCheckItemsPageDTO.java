package com.cdqckj.gmis.securityed.dto;

import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 隐患列表
 * </p>
 *
 * @author gmis
 * @since 2020-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SecurityCheckItemsPageDTO", description = "隐患列表")
public class SecurityCheckItemsPageDTO implements Serializable {

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
     * 隐患等级
     */
    @ApiModelProperty(value = "隐患等级")
    @Length(max = 100, message = "隐患等级长度不能超过100")
    private String dangerLeve;

    /**
     * 安检计划编号
     */
    @ApiModelProperty(value = "安检计划编号")
    @TableField("sc_no")
    @Excel(name = "安检计划编号")
    private String scNo;
    /**
     * 安检结果id
     */
    @ApiModelProperty(value = "安检结果id")
    private Long scResultId;
    /**
     * 安检项ID
     */
    @ApiModelProperty(value = "安检项ID")
    @Length(max = 32, message = "安检项ID长度不能超过32")
    private String scTermCode;
    /**
     * 安检项名称
     */
    @ApiModelProperty(value = "安检项名称")
    @Length(max = 100, message = "安检项名称长度不能超过100")
    private String scTermName;
    @ApiModelProperty(value = "")
    private Long scTermItemsId;
    @ApiModelProperty(value = "")
    @Length(max = 100, message = "长度不能超过100")
    private String scTermItemsName;
    /**
     * 安检隐患
     */
    @ApiModelProperty(value = "安检隐患")
    @Length(max = 1000, message = "安检隐患长度不能超过1000")
    private String hiddenDanger;
    /**
     * 整改信息
     */
    @ApiModelProperty(value = "整改信息")
    @Length(max = 1000, message = "整改信息长度不能超过1000")
    private String handleInfo;
    /**
     * 隐患处理状态
     */
    @ApiModelProperty(value = "隐患处理状态:0 未处理， 1 已处理（在输入对应的隐患整改信息时，完成修改为 1 已处理 ）")
    private Integer handleStatus;
    /**
     * 隐患处理登记人ID
     */
    @ApiModelProperty(value = "隐患处理登记人ID")
    private Long handleUserId;
    /**
     * 隐患处理登记时间
     */
    @ApiModelProperty(value = "隐患处理登记时间")
    private LocalDateTime handleTime;
    /**
     * 附件信息
     */
    @ApiModelProperty(value = "附件信息")
    @Length(max = 1000, message = "附件信息长度不能超过1000")
    private String attacheInfo;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 1000, message = "备注长度不能超过1000")
    private String remark;

}

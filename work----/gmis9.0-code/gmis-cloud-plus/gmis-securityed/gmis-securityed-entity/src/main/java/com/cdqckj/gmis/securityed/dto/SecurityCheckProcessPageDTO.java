package com.cdqckj.gmis.securityed.dto;

import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 安检流程操作记录
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
@ApiModel(value = "SecurityCheckProcessPageDTO", description = "安检流程操作记录")
public class SecurityCheckProcessPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
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
     * 安检计划编号
     */
    @ApiModelProperty(value = "安检计划编号")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String securityCheckNunber;
    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    @Length(max = 32, message = "流程状态长度不能超过32")
    private Integer processState;
    /**
     * 操作人员ID
     */
    @ApiModelProperty(value = "操作人员ID")
    @Length(max = 32, message = "操作人员ID长度不能超过32")
    private String processUserId;
    /**
     * 操作人员名称
     */
    @ApiModelProperty(value = "操作人员名称")
    @Length(max = 32, message = "操作人员名称长度不能超过32")
    private String processUserName;
    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    @Length(max = 1000, message = "操作描述长度不能超过1000")
    private String processDesc;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime processTime;

}

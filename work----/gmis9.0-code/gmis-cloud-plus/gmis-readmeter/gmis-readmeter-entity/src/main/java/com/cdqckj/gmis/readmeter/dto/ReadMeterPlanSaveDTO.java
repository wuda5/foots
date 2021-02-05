package com.cdqckj.gmis.readmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 实体类
 * 抄表计划
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReadMeterPlanSaveDTO", description = "抄表计划")
public class ReadMeterPlanSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 抄表年份
     */
    @ApiModelProperty(value = "抄表年份")
    private Integer readMeterYear;
    /**
     * 抄表月份
     */
    @ApiModelProperty(value = "抄表月份")
    private Integer readMeterMonth;
    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划开始时间")
    private LocalDate planStartTime;
    /**
     * 计划结束时间
     */
    @ApiModelProperty(value = "计划结束时间")
    private LocalDate planEndTime;
    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    private Integer totalReadMeterCount;
    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    private Integer readMeterCount;
    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    private Integer reviewCount;
    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    private Integer settlementCount;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer dataStatus;

}

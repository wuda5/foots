package com.cdqckj.gmis.charges.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 周期收费项最后一次缴费
 * </p>
 *
 * @author tp
 * @since 2020-08-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "TollItemCycleLastChargeRecordSaveDTO", description = "周期收费项最后一次缴费")
public class TollItemCycleLastChargeRecordSaveDTO implements Serializable {

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
    private String companName;
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
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    @Length(max = 32, message = "气表编号长度不能超过32")
    private String gasmeterCode;
    /**
     * 收费项ID
     */
    @ApiModelProperty(value = "收费项ID")
    @NotNull(message = "收费项ID不能为空")
    private Long tollItemId;
    /**
     * 计费截至日期
     */
    @ApiModelProperty(value = "计费截至日期")
    private LocalDate billingDeadline;
    /**
     * 缴费时间
     */
    @ApiModelProperty(value = "缴费时间")
    private LocalDateTime chargeTime;

    /**
     * 收费项原始开始时间
     */
    @ApiModelProperty(value = "收费项原始开始时间")
    private LocalDate itemStartDate;

    /**
     * 数据状态:
     *             1: 正常
     *             0: 作废
     */
    @ApiModelProperty(value = "数据状态:")
    private Integer dataStatus;
}

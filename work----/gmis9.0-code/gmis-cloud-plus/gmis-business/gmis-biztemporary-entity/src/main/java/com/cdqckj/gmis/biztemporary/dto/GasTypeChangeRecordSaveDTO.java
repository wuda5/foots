package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 客户用气类型变更记录
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
@ApiModel(value = "GasTypeChangeRecordSaveDTO", description = "客户用气类型变更记录")
public class GasTypeChangeRecordSaveDTO implements Serializable {

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
     * 营业厅id
     */
    @ApiModelProperty(value = "营业厅id")
    private Long businessBallId;
    /**
     * 营业厅名称
     */
    @ApiModelProperty(value = "营业厅名称")
    private String businessBallName;
    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Length(max = 50, message = "客户编码长度不能超过50")
    private String customerCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Length(max = 100, message = "客户名称长度不能超过100")
    private String customerName;
    /**
     * 气表编码
     */
    @ApiModelProperty(value = "气表编码")
    @Length(max = 50, message = "气表编码长度不能超过50")
    private String gasMeterCode;
    /**
     * 气表名称
     */
    @ApiModelProperty(value = "气表名称")
    @Length(max = 100, message = "气表名称长度不能超过100")
    private String gasMeterName;
    /**
     * 变更时间
     */
    @ApiModelProperty(value = "变更时间")
    private LocalDateTime changeTime;
    /**
     * 变更前用气类型名称
     */
    @ApiModelProperty(value = "变更前用气类型名称")
    @Length(max = 60, message = "变更前用气类型名称长度不能超过60")
    private String oldGasTypeName;
    /**
     * 变更前用气类型id
     */
    @ApiModelProperty(value = "变更前用气类型id")
    private Long oldGasTypeId;
    /**
     * 变更后用气类型名称
     */
    @ApiModelProperty(value = "变更后用气类型名称")
    @Length(max = 60, message = "变更后用气类型名称长度不能超过60")
    private String newGasTypeName;
    /**
     * 变更后用气类型id
     */
    @ApiModelProperty(value = "变更后用气类型id")
    private Long newGasTypeId;
    /**
     * 变更前价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案开始时间")
    private LocalDate startTimeNew;
    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案结束时间")
    private LocalDate endTimeNew;


    @ApiModelProperty(value = "变更前价格方案开始时间")
    private LocalDate startTimeOld;
    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更前价格方案结束时间")
    private LocalDate endTimeOld;

    /**
     * 变更前嘉庚方案号
     */
    @ApiModelProperty(value = "变更前嘉庚方案号")
    private Integer oldPriceNum;
    /**
     * 变更后价格方案号
     */
    @ApiModelProperty(value = "变更后价格方案号")
    private Integer newPriceNum;
    /**
     * 周期量控制（是否清零1-清零，0-不清零）
     */
    @ApiModelProperty(value = "周期量控制（是否清零1-清零，0-不清零）")
    @Deprecated
    private Integer cycleSumControl;

    /**
     * 变更前价格方案id
     */
    @ApiModelProperty(value = "变更前价格方案id")
    private Long oldPriceId;

    /**
     * 变更后价格方案id
     */
    @ApiModelProperty(value = "变更后价格方案id")
    private Long newPriceId;
}

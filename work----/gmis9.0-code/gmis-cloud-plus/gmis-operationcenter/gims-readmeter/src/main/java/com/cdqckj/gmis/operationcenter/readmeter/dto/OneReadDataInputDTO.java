package com.cdqckj.gmis.operationcenter.readmeter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 实体类
 * 抄表数据
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
@ApiModel(value = "AppReadDataInputDTO", description = "单条抄表数据录入dto")
public class OneReadDataInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表编号")
    private String gasMeterCode;

    @ApiModelProperty(value = "抄表年")
    @NotNull(message = "抄表年")
    private Integer readMeterYear;


    @ApiModelProperty(value = "抄表月份")
    @NotNull(message = "抄表月份")
    private Integer readMeterMonth;
    /**
     *  本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @NotNull(message = "本期止数不能为空")
    @DecimalMin(value = "0",message = "本期止数必须正数")
    private BigDecimal currentTotalGas;

//    /**
//     * 上期止数
//     */
//    @ApiModelProperty(value = "上期止数")
//    @NotNull(message = "上期止数不能为空")
//    @DecimalMin(value = "0",message = "上期止数必须正数")
//    private BigDecimal lastTotalGas;
//
//
//
//    /**
//     * 本期用量
//     */
//    @ApiModelProperty(value = "本期用量")
//    @DecimalMin(value ="0",message = "本期用量必须正数")
//    private BigDecimal monthUseGas;


}

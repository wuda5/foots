package com.cdqckj.gmis.calculate.vo;

import com.cdqckj.gmis.common.enums.ConversionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ConversionVO", description = "计费换算")
public class ConversionVO implements Serializable {

    /**
     * 需要换算的气量或金额
     */
    @ApiModelProperty(value = "换算需要的气量或金额")
    @NotEmpty
    private BigDecimal conversionValue;

    /**
     * 换算需要的用气类型
     */
    @ApiModelProperty(value = "换算需要的用气类型id")
    private Long useGasTypeId;

    /**
     * 换算需要的气表编号
     */
    @ApiModelProperty(value = "换算需要的气表编号")
    @NotEmpty
    private String gasMeterCode;

    /**
     * 换算类型(MONEY-金额换算成气量,GAS-气量换算成金额)
     */
    @ApiModelProperty(value = "换算类型(MONEY-金额换算成气量,GAS-气量换算成金额)")
    @NotEmpty
    private ConversionType conversionType;
}

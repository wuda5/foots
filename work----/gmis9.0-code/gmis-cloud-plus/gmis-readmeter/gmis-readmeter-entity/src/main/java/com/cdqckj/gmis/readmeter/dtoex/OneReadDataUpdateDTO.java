package com.cdqckj.gmis.readmeter.dtoex;

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
@ApiModel(value = "OneReadDataUpdateDTO", description = "单条抄表数据纠正 dto")
public class OneReadDataUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 气表编号
     */
    @ApiModelProperty(value = "气表数据主键id")
    private Long id;

    /**
     *  本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @NotNull(message = "本期止数不能为空")
    @DecimalMin(value = "0",message = "本期止数必须正数")
    private BigDecimal currentTotalGas;




}

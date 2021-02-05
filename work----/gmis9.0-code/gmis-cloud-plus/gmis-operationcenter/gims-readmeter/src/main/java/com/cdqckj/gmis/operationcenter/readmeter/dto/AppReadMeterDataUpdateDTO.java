package com.cdqckj.gmis.operationcenter.readmeter.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
@ApiModel(value = "ReadMeterDataUpdateDTO", description = "抄表数据")
public class AppReadMeterDataUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 上期止数
     */
    @ApiModelProperty(value = "上期止数")
    @NotNull(message = "上期止数不能为空")
    @DecimalMin(value = "0",message = "上期止数必须正数")
    private BigDecimal lastTotalGas;

    /**
      *  本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @NotNull(message = "本期止数不能为空")
    @DecimalMin(value = "0",message = "本期止数必须正数")
    private BigDecimal currentTotalGas;
    /**
     * 本期用量
     */
    @ApiModelProperty(value = "本期用量")
    @DecimalMin(value ="0",message = "本期用量必须正数")
    private BigDecimal monthUseGas;


}

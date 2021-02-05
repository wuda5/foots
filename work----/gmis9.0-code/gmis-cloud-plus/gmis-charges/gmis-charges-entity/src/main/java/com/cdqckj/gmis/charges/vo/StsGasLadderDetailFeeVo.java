package com.cdqckj.gmis.charges.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2021/01/13 10:09
 * @remark: 请添加类说明
 */
@Data
@ApiModel(value = "StsCounterStaffVo", description = "统计分析报表-售气收费看板-阶梯方案统计")
public class StsGasLadderDetailFeeVo implements Serializable{

    @ApiModelProperty(value = "id")
    private Long gasLadderId;

    @ApiModelProperty(value = "名字")
    private String gasLadderName;

    @ApiModelProperty(value = "用气类型id")
    private Long gasTypeId;

    @ApiModelProperty(value = "用气类型的名字")
    private String gasTypeName;

    private Long people1 = 0L;
    private BigDecimal gas1 = BigDecimal.ZERO;
    private BigDecimal money1 = BigDecimal.ZERO;

    private Long people2 = 0L;
    private BigDecimal gas2 = BigDecimal.ZERO;
    private BigDecimal money2 = BigDecimal.ZERO;

    private Long people3 = 0L;
    private BigDecimal gas3 = BigDecimal.ZERO;
    private BigDecimal money3 = BigDecimal.ZERO;

    private Long people4 = 0L;
    private BigDecimal gas4 = BigDecimal.ZERO;
    private BigDecimal money4 = BigDecimal.ZERO;

    private Long people5 = 0L;
    private BigDecimal gas5 = BigDecimal.ZERO;
    private BigDecimal money5 = BigDecimal.ZERO;

    private Long people6 = 0L;
    private BigDecimal gas6 = BigDecimal.ZERO;
    private BigDecimal money6 = BigDecimal.ZERO;

}

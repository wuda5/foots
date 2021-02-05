package com.cdqckj.gmis.common.domain.sts;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2021/01/21 15:18
 * @remark: 统计的年月日的数据
 */
@Data
public class StsDateVo implements Serializable {

    @ApiModelProperty(value = "今日")
    private BigDecimal todayNum = BigDecimal.ZERO;

    @ApiModelProperty(value = "本月")
    private BigDecimal monthNum = BigDecimal.ZERO;

    @ApiModelProperty(value = "今年")
    private BigDecimal yearNum = BigDecimal.ZERO;

    @ApiModelProperty(value = "所有")
    private BigDecimal allNum = BigDecimal.ZERO;
}

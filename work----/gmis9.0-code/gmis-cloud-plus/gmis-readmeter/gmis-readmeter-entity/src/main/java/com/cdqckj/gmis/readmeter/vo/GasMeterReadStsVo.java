package com.cdqckj.gmis.readmeter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2021/01/07 13:31
 * @remark: 抄表的统计
 */
@Data
public class GasMeterReadStsVo implements Serializable {

    @ApiModelProperty(value = "抄表数")
    private Integer readCount = 0;

    @ApiModelProperty(value = "遗漏数")
    private Integer loseCount = 0;

    @ApiModelProperty(value = "结算数")
    private Integer settlementCount = 0;

    @ApiModelProperty(value = "审核数")
    private Integer reviewCount = 0;

    @ApiModelProperty(value = "总数")
    private Integer totalCount = 0;
}

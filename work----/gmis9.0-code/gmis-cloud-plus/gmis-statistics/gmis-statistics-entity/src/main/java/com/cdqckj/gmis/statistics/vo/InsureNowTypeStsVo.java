package com.cdqckj.gmis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/11/17 16:48
 * @remark: 请输入类说明
 */
@Data
@ApiModel(value = "InsureNowTypeVo", description = "保险购买分类的统计")
public class InsureNowTypeStsVo {

    @ApiModelProperty(value = "新增")
    private Integer newBuy= 0;

    @ApiModelProperty(value = "续保")
    private Integer reBuy = 0;

    @ApiModelProperty(value = "未购买")
    private Integer noBuy = 0;
}

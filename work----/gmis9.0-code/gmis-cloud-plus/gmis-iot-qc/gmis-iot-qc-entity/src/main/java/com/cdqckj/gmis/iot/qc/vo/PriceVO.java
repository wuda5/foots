package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 价格方案vo
 * @author: 秦川物联网科技
 * @date: 2020/10/20  08:20
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "PriceVO", description = "价格方案")
public class PriceVO implements Serializable {
    /**
     * 阶梯量
     */
    @ApiModelProperty(value = "阶梯量",required = true)
    private Integer tieredValue;
    /**
     * 阶梯价
     */
    @ApiModelProperty(value = "阶梯价",required = true)
    private Double tieredPrice;
    /**
     * 第几阶梯
     */
    @ApiModelProperty(value = "第几阶梯",required = true)
    private Integer tieredNum;
}

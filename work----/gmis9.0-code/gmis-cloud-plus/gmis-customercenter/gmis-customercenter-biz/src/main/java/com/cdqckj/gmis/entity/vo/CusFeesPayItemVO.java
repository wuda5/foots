package com.cdqckj.gmis.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 缴费明细item
 * @auther hc
 * @date 2020/10/20
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CusFeesPayItemVO",description = "费用明细item")
public class CusFeesPayItemVO {

    @ApiModelProperty("费用大类code")
    private String chargeTypeCode;

    @ApiModelProperty("费用大类名称")
    private String chargeTypeName;

    @ApiModelProperty("费用明细项")
    private List<Object> chargeItem;

    @ApiModelProperty("费用总和")
    private BigDecimal chargeTotal;
}

package com.cdqckj.gmis.bizcenter.interest.concerns.vo;

import com.cdqckj.gmis.operateparam.entity.UseGasType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ASUS
 */
@Data
@ApiModel(value = "气价方案", description = "气价方案页面展示对象")
public class UseGasPriceSchemeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用气类型
     */
    @ApiModelProperty(value = "用气类型")
    private UseGasType useGasType;

    /**
     * 价格方案
     */
    @ApiModelProperty(value = "价格方案列表")
    private List<PriceSchemeVO> priceSchemeList;

}

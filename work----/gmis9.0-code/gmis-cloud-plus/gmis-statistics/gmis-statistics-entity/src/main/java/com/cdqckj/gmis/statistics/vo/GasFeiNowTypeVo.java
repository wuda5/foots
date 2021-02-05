package com.cdqckj.gmis.statistics.vo;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: lijianguo
 * @time: 2020/11/19 17:14
 * @remark: 请输入类说明
 */
@Data
@ApiModel(value = "GasFeiNowTypeVo", description = "用气类型统计")
public class GasFeiNowTypeVo implements SeparateKey, Serializable {

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "名字")
    private String typeName;

    @ApiModelProperty(value = "总量")
    private BigDecimal gasAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "总价")
    private BigDecimal feiAmount = BigDecimal.ZERO;

    @Override
    public String indexKey() {
        return type;
    }
}

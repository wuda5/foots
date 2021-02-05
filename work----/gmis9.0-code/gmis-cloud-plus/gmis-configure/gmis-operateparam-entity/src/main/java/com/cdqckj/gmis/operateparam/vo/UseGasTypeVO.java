package com.cdqckj.gmis.operateparam.vo;

import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "气价方案", description = "气价方案页面展示对象")
public class UseGasTypeVO implements Serializable {
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
    private List<PriceScheme> priceSchemeList;

}

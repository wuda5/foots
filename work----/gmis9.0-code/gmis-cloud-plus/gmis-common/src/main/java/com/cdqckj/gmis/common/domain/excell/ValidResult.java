package com.cdqckj.gmis.common.domain.excell;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/30 8:57
 * @remark: 验证的结果
 */
@Data
public class ValidResult {

    @ApiModelProperty(value = "数据所在的行")
    private int row;

    @ApiModelProperty(value = "数据所在的开始的列号")
    private int col;

    @ApiModelProperty(value = "要验证的列的数目默认1列 验证1列")
    private int colSum;

    @ApiModelProperty(value = "要验证所有的列的数据")
    private List<String> colDataList;

    @ApiModelProperty(value = "0错误 1正确")
    private Boolean status;

    @ApiModelProperty(value = "验证错误的描述")
    private String invalidDesc;

}

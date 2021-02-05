package com.cdqckj.gmis.bizcenter.temp.counter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 过户验证响应实体
 * @author HC
 */
@Data
public class TransferAccountCheckDTO {


    @ApiModelProperty(value = "是否有未完成的业务")
    private Boolean flag;

    @ApiModelProperty(value = "业务步骤:1、新建；2、代缴费；3、缴费处理中")
    private Integer step;

    @ApiModelProperty(value = "业务单数据")
    private Object busData;

    @ApiModelProperty(value = "是否需要人工输入缴费编号")
    private Boolean openChargeFlag;

}

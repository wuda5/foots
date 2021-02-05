package com.cdqckj.gmis.biztemporary.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 业务验证结果
 *
 * @param <T>
 * @author ASUS
 */
@Data
public class BusinessCheckResultDTO<T> {


    @ApiModelProperty(value = "是否有未完成的业务")
    private Boolean flag;

    @ApiModelProperty(value = "业务步骤:1、新建；2、代缴费；3、缴费处理中")
    private Integer step;

    @ApiModelProperty(value = "业务单数据")
    private T busData;

    @ApiModelProperty(value = "是否需要支付页面")
    private Boolean needToPay;

    @ApiModelProperty(value = "欠费记录Id列表")
    private List<String> arrearsDetailIds;
}

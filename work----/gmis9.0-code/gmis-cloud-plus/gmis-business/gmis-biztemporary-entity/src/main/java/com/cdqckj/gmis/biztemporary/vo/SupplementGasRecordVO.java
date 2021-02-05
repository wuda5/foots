package com.cdqckj.gmis.biztemporary.vo;

import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 页面展示对象
 *
 * @author hp
 */
@Data
public class SupplementGasRecordVO extends SupplementGasRecord {

    @ApiModelProperty("表具金额标识")
    private String amountMark;

    @ApiModelProperty("表身号")
    private String gasMeterNumber;
}

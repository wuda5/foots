package com.cdqckj.gmis.biztemporary.vo;

import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GasTypeChangeRecordVO extends GasTypeChangeRecord {

    @ApiModelProperty("表身号")
    private String gasMeterNumber;

}

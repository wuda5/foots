package com.cdqckj.gmis.biztemporary.vo;

import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RemoveMeterRecordVO extends RemoveMeterRecord {
    /**
     * 收费金额
     */
    @ApiModelProperty("收费金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("表身号")
    private String gasMeterNumber;
}

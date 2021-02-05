package com.cdqckj.gmis.biztemporary.vo;

import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeMeterRecordVO extends ChangeMeterRecord {
    /**
     * 收费金额
     */
    @ApiModelProperty("收费金额")
    private BigDecimal totalMoney;
}

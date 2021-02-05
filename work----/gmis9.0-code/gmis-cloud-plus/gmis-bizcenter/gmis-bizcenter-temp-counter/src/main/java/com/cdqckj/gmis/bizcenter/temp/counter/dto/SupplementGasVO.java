package com.cdqckj.gmis.bizcenter.temp.counter.dto;

import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hp
 * @Date 2020/12/10
 */
@Data
public class SupplementGasVO {

    /**
     * 订单来源名称
     */
    @ApiModelProperty(value = "订单来源名称")
    private String orderSourceName;

    @ApiModelProperty(value = "金额标志")
    private String amountMark;

    @ApiModelProperty("气表使用信息")
    private GasMeterInfo gasMeterInfo;

    @ApiModelProperty("换气记录")
    private SupplementGasRecord supplementGasRecord;

}

package com.cdqckj.gmis.bizcenter.temp.counter.dto;


import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hp
 * @Date 2020/12/10
 */
@Data
public class SettlementResult {

    /**
     * 场景数据生成的抄表数据ID
     */
    private Long readMeterDataId;

    /**
     * 结算生成的欠费记录
     */
    private List<GasmeterArrearsDetail> arrearsDetails;


    /**
     * 户表账户余额
     */
    @ApiModelProperty(value = "户表账户余额")
    private BigDecimal gasMeterBalance;

    /**
     * 周期累计使用量
     */
    @ApiModelProperty(value = "周期累计使用量")
    private BigDecimal cycleUseGas;

}

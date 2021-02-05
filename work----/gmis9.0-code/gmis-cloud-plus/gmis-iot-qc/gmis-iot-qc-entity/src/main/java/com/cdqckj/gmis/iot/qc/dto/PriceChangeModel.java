package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class PriceChangeModel extends BaseCommandModel implements Serializable {
    /**
     * 业务数据
     */
    private PriceChangeBus businessData;
}

package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RetryModel implements Serializable {
    /**
     * l逻辑域id
     */
    private String domainId;
    /**
     * 流水号
     */
    private String transactionNo;
}

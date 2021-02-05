package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessStageModel implements Serializable {
    private String domainId;
    private String transactionNo;
}

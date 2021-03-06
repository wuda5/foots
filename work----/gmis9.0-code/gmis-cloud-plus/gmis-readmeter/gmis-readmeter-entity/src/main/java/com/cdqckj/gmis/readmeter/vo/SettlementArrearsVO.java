package com.cdqckj.gmis.readmeter.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SettlementArrearsVO implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;
    private String gasMeterCode;
    private String customerCode;
}

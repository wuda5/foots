package com.cdqckj.gmis.biztemporary.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PriceChangeVO implements Serializable {
    private String gasMeterCode;
    private LocalDate recordTime;
    private Long priceId;
}

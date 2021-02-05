package com.cdqckj.gmis.calculate.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LadderDetailVO implements Serializable {
    private int ladder;
    private BigDecimal price;
    private BigDecimal gas;
    private BigDecimal totalMoney;
}

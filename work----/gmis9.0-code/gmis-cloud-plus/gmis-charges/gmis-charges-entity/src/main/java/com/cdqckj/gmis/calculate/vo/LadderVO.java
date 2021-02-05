package com.cdqckj.gmis.calculate.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LadderVO implements Serializable {
     private List<LadderDetailVO> list;
     private BigDecimal currentPrice;
}

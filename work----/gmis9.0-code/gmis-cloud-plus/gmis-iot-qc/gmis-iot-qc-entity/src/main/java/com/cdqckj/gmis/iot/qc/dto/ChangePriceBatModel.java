package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChangePriceBatModel implements Serializable {
    private String domainId;
    private String businessType;
    private List<ChangePriceBatVO> Data;
}

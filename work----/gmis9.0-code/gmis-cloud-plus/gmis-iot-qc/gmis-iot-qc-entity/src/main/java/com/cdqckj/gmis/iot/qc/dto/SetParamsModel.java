package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetParamsModel extends BaseCommandModel implements Serializable {
    private  SetUpParamster<String> businessData;
}

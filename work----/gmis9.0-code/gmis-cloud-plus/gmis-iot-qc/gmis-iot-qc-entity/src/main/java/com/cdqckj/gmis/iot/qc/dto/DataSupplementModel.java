package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DataSupplementModel extends BaseCommandModel implements Serializable {
    /**
     *
     */
    private SetUpParamsterModel<SetUpParamsterReading> businessData;
}

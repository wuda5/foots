package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClearModel extends BaseCommandModel implements Serializable {
    private ClearBusModel businessData;
}

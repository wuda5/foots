package com.cdqckj.gmis.iot.qc.qnms.utils;

import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import lombok.Data;

import java.io.Serializable;

@Data
public class UploadDataVO implements Serializable {
    private ReadMeterDataIot igd;
    private ReadMeterDataIot previous;
    private Boolean isBc;
}

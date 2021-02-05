package com.cdqckj.gmis.iot.qc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MeterCacheVO  implements Serializable {
    /**
     * 表身号数组
     */
    private List<String> meterList;

    private Long gasMeterFactoryId;
}

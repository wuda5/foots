package com.cdqckj.gmis.executor.jobhandler.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class JobParamsVO implements Serializable {
    /**
     * 租户code
     */
    private String tenantCode;
    /**
     * 执行日期
     */
    private String executeDate;
}

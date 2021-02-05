package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;

import java.util.List;

public interface MeterCacheService {
    /**
     * 匹配表是否在缓存
     * @param meterCacheVO
     */
    IotR matchMeterCache(MeterCacheVO meterCacheVO);
}

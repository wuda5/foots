package com.cdqckj.gmis.calculate;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
public interface AdjustCalculationRecordService extends SuperService<AdjustCalculationRecord> {
    public Map getCaculationTaskInfo(LocalDateTime startTime, LocalDateTime endTime);
}

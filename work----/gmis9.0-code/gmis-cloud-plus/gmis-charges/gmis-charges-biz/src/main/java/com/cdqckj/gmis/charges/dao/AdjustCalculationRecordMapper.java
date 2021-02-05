package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Repository
public interface AdjustCalculationRecordMapper extends SuperMapper<AdjustCalculationRecord> {
    List<AdjustCalculationRecord> getCaculationTaskInfo(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}

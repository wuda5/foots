package com.cdqckj.gmis.calculate.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.calculate.AdjustCalculationRecordService;
import com.cdqckj.gmis.charges.dao.AdjustCalculationRecordMapper;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.charges.enums.AdjustCalculationTaskStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AdjustCalculationRecordServiceImpl extends SuperServiceImpl<AdjustCalculationRecordMapper, AdjustCalculationRecord> implements AdjustCalculationRecordService {
    @Override
    public Map getCaculationTaskInfo(LocalDateTime startTime, LocalDateTime endTime) {
        List<AdjustCalculationRecord> adjustCalculationRecords = baseMapper.getCaculationTaskInfo(startTime, endTime);
        long totalNum = adjustCalculationRecords.size();
        long succTotal = adjustCalculationRecords.stream().filter(s->s.getDataStatus().equals(AdjustCalculationTaskStateEnum.CACULATED.getCode())).count();
        long failTotal = adjustCalculationRecords.stream().filter(s->s.getDataStatus().equals(AdjustCalculationTaskStateEnum.CACULATE_FAILED.getCode())).count();
        Map returnMap = new HashMap();
        returnMap.put("totalNum", totalNum);
        returnMap.put("succTotal", succTotal);
        returnMap.put("failTotal", failTotal);
        return returnMap;
    }
}

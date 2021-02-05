package com.cdqckj.gmis.iot.qc.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.iot.qc.dao.IotGasMeterDayDataMapper;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterDayData;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterDayDataService;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataParamsVO;
import com.cdqckj.gmis.iot.qc.vo.DayDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotGasMeterDayDataServiceImpl extends SuperServiceImpl<IotGasMeterDayDataMapper, IotGasMeterDayData> implements IotGasMeterDayDataService {
    @Override
    public Page<DayDataVO> queryDayData(DayDataParamsVO params) {
        Page<DayDataVO> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.queryDayData(page, params);
    }
}

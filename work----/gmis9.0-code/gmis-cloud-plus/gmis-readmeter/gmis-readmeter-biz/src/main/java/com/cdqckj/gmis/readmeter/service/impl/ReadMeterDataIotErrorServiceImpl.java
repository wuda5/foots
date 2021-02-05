package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.readmeter.dao.ReadMeterDataIotErrorMapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotErrorService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-23
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterDataIotErrorServiceImpl extends SuperServiceImpl<ReadMeterDataIotErrorMapper, ReadMeterDataIotError> implements ReadMeterDataIotErrorService {
    @Override
    public ReadMeterDataIotError queryByGasMeterNumber(String gasMeterNumber) {
        LbqWrapper<ReadMeterDataIotError> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIotError::getGasMeterNumber,gasMeterNumber);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<ReadMeterDataIotError> findPage(IPage<ReadMeterDataIotError> page, LbqWrapper<ReadMeterDataIotError> wrapper) {
        return baseMapper.selectPage(page,wrapper);
    }

    @Override
    public List<ReadMeterDataIotError> queryByMeterNo(List<String> meterNoList) {
        LbqWrapper<ReadMeterDataIotError> wrapper = new LbqWrapper<>();
        wrapper.in(ReadMeterDataIotError::getGasMeterNumber,meterNoList);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Boolean updateBathMeter(List<ReadMeterDataIotError> list) {
        return super.updateBatchById(list);
    }
}

package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.dao.GasMeterInfoMapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 气表使用情况
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterInfoServiceImpl extends SuperServiceImpl<GasMeterInfoMapper, GasMeterInfo> implements GasMeterInfoService {
    @Override
    @Deprecated
    public GasMeterInfo getByGasMeterCode(String gascode) {
        LbqWrapper<GasMeterInfo> wrapper = new LbqWrapper<>();
        wrapper.eq(GasMeterInfo::getGasmeterCode,gascode);
        wrapper.eq(GasMeterInfo::getDataStatus,DataStatusEnum.NORMAL.getValue());
        return getBaseMapper().selectOne(wrapper);
    }

    @Override
    public GasMeterInfo getByMeterAndCustomerCode(String gasMeterCode,String customerCode) {
        LbqWrapper<GasMeterInfo> wrapper = new LbqWrapper<>();
        wrapper.eq(GasMeterInfo::getGasmeterCode,gasMeterCode);
        wrapper.eq(GasMeterInfo::getCustomerCode,customerCode);
        wrapper.eq(GasMeterInfo::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return getBaseMapper().selectOne(wrapper);
    }


    @Override
    public R<Integer> updateByPriceId(MeterInfoVO meterInfoVO) {
        return  R.success(baseMapper.updateByPriceId(meterInfoVO));
    }

    @Override
    public R<Integer> updateCycleByPriceId(MeterInfoVO meterInfoVO) {
        return  R.success(baseMapper.updateCycleByPriceId(meterInfoVO));
    }

}

package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.PriceSchemeMapper;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PriceSchemeServiceImpl extends SuperServiceImpl<PriceSchemeMapper, PriceScheme> implements PriceSchemeService {
    @Autowired
    private UseGasTypeService useGasTypeService;
    @Override
    public PriceScheme queryRecentRecord(Long useGasTypeId) {
        return getBaseMapper().queryRecentRecord(useGasTypeId);
    }

    @Override
    public PriceScheme queryRecentHeatingRecord(Long useGasTypeId) {
        return getBaseMapper().queryRecentHeatingRecord(useGasTypeId);
    }

    @Override
    public PriceScheme queryRecentRecordByTime(Long useGasTypeId, LocalDate activateDate) {
        return getBaseMapper().queryRecentRecordByTime(useGasTypeId,activateDate);
    }

    @Override
    public PriceScheme queryPrePriceScheme(Long useGasTypeId) {
        return baseMapper.queryPrePriceScheme(useGasTypeId,LocalDate.now());
    }

    @Override
    public PriceScheme queryPreHeatingPriceScheme(Long useGasTypeId) {
        return baseMapper.queryPreHeatingPriceScheme(useGasTypeId,LocalDate.now());
    }

    @Override
    public List<PriceScheme> queryAllPriceScheme() {
        //查询所有价格方案
        return baseMapper.selectList(null);
    }

    @Override
    public int updateByPriceId(PriceScheme priceScheme) {
        return baseMapper.updateById(priceScheme);
    }

    @Override
    public int updatePriceStatus() {
        return baseMapper.updatePriceStatus();
    }


    @Override
    public List<PriceScheme> queryRecordNum(Long useGasTypeId) {
        LbqWrapper<PriceScheme> wrapper = new LbqWrapper();
        wrapper.eq(PriceScheme::getDataStatus,1);
        wrapper.eq(PriceScheme::getUseGasTypeId,useGasTypeId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public PriceScheme queryPriceByTime(Long useGasTypeId, LocalDate activateDate) {
        return baseMapper.queryPriceByTime(useGasTypeId,activateDate);
    }

    @Override
    public PriceScheme queryPrePrice(Long useGasTypeId){
        return getOne(new LbqWrapper<PriceScheme>().eq(PriceScheme::getUseGasTypeId,useGasTypeId)
                .gt(PriceScheme::getStartTime, LocalDateTime.now())
                .last(" limit 1")
        );
    }

    @Override
    public PriceScheme queryPriceHeatingByTime(Long useGasTypeId, LocalDate activateDate) {
        return baseMapper.queryPriceHeatingByTime(useGasTypeId,activateDate);
    }

    @Override
    public PriceScheme getAdvancePriceScheme(Long useGasTypeId, LocalDate now) {
        return this.baseMapper.getAdvancePriceScheme(useGasTypeId, now);
    }

    @Override
    public List<PriceScheme> getPriceSchemeDuring(StsSearchParam stsSearchParam) {
        String priceType = null;
        if (stsSearchParam.getDataMap() != null){
            priceType = (String) stsSearchParam.getDataMap().get("priceType");
        }
        return this.baseMapper.getPriceSchemeDuring(stsSearchParam, priceType);
    }
}

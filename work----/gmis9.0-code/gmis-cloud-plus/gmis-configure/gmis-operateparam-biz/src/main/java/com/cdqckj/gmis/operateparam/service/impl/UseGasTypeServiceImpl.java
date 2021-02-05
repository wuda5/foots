package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.UseGasTypeMapper;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class UseGasTypeServiceImpl extends SuperServiceImpl<UseGasTypeMapper, UseGasType> implements UseGasTypeService {

    @Autowired
    PriceSchemeService priceSchemeService;

    @Override
    public R<UseGasType> getUseGasType(String useGasTypeName) {
        LbqWrapper<UseGasType> lbqWrapper = new LbqWrapper<>();
        lbqWrapper.eq(UseGasType::getUseGasTypeName, useGasTypeName);
        return R.success(getBaseMapper().selectOne(lbqWrapper));
    }

    @Override
    public UseGasType queryRentUseGasType() {
        return baseMapper.queryRentUseGasType();
    }

    /**
     * 通过id查询用气类型及价格方案
     *
     * @param id 用气类型id
     * @return 气价方案
     */
    @Override
    public UseGasTypeVO queryUseGasTypeAndPrice(Long id) {
        UseGasTypeVO result = UseGasTypeVO.builder().build();
        UseGasType useGasType = baseMapper.selectById(id);
        if (Objects.isNull(useGasType)) {
            return result;
        }
        LbqWrapper<PriceScheme> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(PriceScheme::getUseGasTypeId, useGasType.getId())
                .eq(PriceScheme::getDataStatus, DataStatusEnum.NORMAL.getValue());
        List<PriceScheme> list = priceSchemeService.list(lbqWrapper);
        result.setUseGasType(useGasType);
        result.setPriceSchemeList(list);
        return result;
    }
}

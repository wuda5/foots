package com.cdqckj.gmis.operateparam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dao.DetailedAddressMapper;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressPageDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.service.BatchDetailedAddressService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-18
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BatchDetailedAddressServiceImpl extends SuperServiceImpl<DetailedAddressMapper, BatchDetailedAddress> implements BatchDetailedAddressService {
    @Override
    public Boolean check(BatchDetailedAddressUpdateDTO detailedAddressUpdateDTO) {
        return super.count(Wraps.<BatchDetailedAddress>lbQ().eq(BatchDetailedAddress::getFlag,detailedAddressUpdateDTO.getFlag()).
                ne(BatchDetailedAddress::getId,detailedAddressUpdateDTO.getId()).
                eq(BatchDetailedAddress::getBuilding,detailedAddressUpdateDTO.getBuilding()).
                eq(BatchDetailedAddress::getUnit,detailedAddressUpdateDTO.getUnit()).
                eq(BatchDetailedAddress::getDetailAddress,detailedAddressUpdateDTO.getDetailAddress()))>0;
    }

    @Override
    public IPage findPage(IPage<BatchDetailedAddress> page, BatchDetailedAddressPageDTO model) {
        BatchDetailedAddress batchDetailedAddress = BeanUtil.toBean(model, BatchDetailedAddress.class);
        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<BatchDetailedAddress> wrapper = Wraps.lbQ();
        wrapper.like(BatchDetailedAddress::getMoreDetailAddress, batchDetailedAddress.getMoreDetailAddress());
        wrapper.eq(BatchDetailedAddress::getCommunityId,batchDetailedAddress.getCommunityId());
        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.eq(BatchDetailedAddress::getDeleteStatus, batchDetailedAddress.getDeleteStatus());
        wrapper.eq(BatchDetailedAddress::getDataStatus, batchDetailedAddress.getDataStatus());
        return baseMapper.findPage(page, wrapper, null);
    }
}

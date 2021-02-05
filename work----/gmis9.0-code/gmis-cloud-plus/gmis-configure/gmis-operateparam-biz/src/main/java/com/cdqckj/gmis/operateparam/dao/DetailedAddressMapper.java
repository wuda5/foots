package com.cdqckj.gmis.operateparam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;

import com.cdqckj.gmis.operateparam.entity.Community;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-18
 */
@Repository
public interface DetailedAddressMapper extends SuperMapper<BatchDetailedAddress> {

    IPage<BatchDetailedAddress> findPage(IPage<BatchDetailedAddress> page, @Param(Constants.WRAPPER) Wrapper<BatchDetailedAddress> wrapper, DataScope dataScope);
}

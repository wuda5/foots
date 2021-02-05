package com.cdqckj.gmis.operateparam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.operateparam.entity.Street;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Repository
public interface StreetMapper extends SuperMapper<Street> {
    /**
     * 街道数据权限查询列表
     * @param page
     * @param wrapper
     * @param dataScope
     * @return
     */
    IPage<Street> findPage(IPage<Street> page, @Param(Constants.WRAPPER) Wrapper<Street> wrapper, DataScope dataScope);
}

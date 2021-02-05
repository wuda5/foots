package com.cdqckj.gmis.operateparam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.operateparam.entity.Community;
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
 * @date 2020-06-22
 */
@Repository
public interface CommunityMapper extends SuperMapper<Community> {
    /**
     * 小区数据权限查询列表
     * @param page
     * @param wrapper
     * @param dataScope
     * @return
     */
    IPage<Community> findPage(IPage<Community> page, @Param(Constants.WRAPPER) Wrapper<Community> wrapper, DataScope dataScope);
}

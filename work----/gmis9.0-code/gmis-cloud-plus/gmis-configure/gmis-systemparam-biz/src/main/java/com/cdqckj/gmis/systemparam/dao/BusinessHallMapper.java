package com.cdqckj.gmis.systemparam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 营业厅信息表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Repository
public interface BusinessHallMapper extends SuperMapper<BusinessHall> {
    /**
     * 带数据权限的分页查询
     *
     * @param page
     * @param wrapper
     * @param dataScope
     * @return
     */
    IPage<BusinessHall> findPage(IPage<BusinessHall> page, @Param(Constants.WRAPPER) Wrapper<BusinessHall> wrapper, DataScope dataScope);
}

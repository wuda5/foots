package com.cdqckj.gmis.authority.dao.core;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.authority.dto.core.StationPageDTO;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 岗位
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Repository
public interface StationMapper extends SuperMapper<Station> {
    /**
     * 分页查询岗位信息（含角色）
     *
     * @param page
     * @param queryWrapper
     * @param dataScope
     * @return
     */
    IPage<Station> findStationPage(IPage page, @Param(Constants.WRAPPER) Wrapper<Station> queryWrapper, DataScope dataScope);

    /**
     * 岗位分页查询方法
     * @auther hc
     * @date 2020/09/04
     * @param page
     * @param dataScope
     * @return
     */
    IPage<Station> findStationPageEx(IPage<Station> page, @Param("pageDTO") StationPageDTO pageDTO, DataScope dataScope);
}

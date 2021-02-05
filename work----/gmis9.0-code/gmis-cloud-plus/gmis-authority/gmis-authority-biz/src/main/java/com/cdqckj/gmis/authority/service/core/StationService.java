package com.cdqckj.gmis.authority.service.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.dto.core.StationPageDTO;
import com.cdqckj.gmis.authority.dto.core.UpdateStatusVO;
import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCacheService;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口
 * 岗位
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
public interface StationService extends SuperCacheService<Station> {
    /**
     * 按权限查询岗位的分页信息
     *
     * @param page
     * @param data
     * @return
     */
    IPage<Station> findStationPage(IPage page, StationPageDTO data);

    /**
     * 根据id 查询
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findStationByIds(Set<Serializable> ids);

    /**
     * 根据id 查询 岗位名称
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findStationNameByIds(Set<Serializable> ids);

    /**
     * 岗位分页查询Ex
     * @auther hc
     * @date 2020/09/04
     * @param params
     * @return
     */
    IPage<Station> findStationPageEx(PageParams<StationPageDTO> params);

    /**
     * 岗位重复性校验接口
     * @auther hc
     * @param orgId
     * @param name
     * @return
     */
    Boolean checkStation(Long orgId, String name);

    /**
     * 批量修改岗位状态
     * @auther hc
     * @return
     */
    Boolean reviseStatus(UpdateStatusVO updateStatusVO);
}

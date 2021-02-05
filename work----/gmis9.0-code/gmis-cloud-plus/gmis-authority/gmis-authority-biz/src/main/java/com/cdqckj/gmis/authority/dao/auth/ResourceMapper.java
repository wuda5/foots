package com.cdqckj.gmis.authority.dao.auth;

import com.cdqckj.gmis.authority.dto.auth.ResourceQueryDTO;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Repository
public interface ResourceMapper extends SuperMapper<Resource> {
    /**
     * 查询 拥有的资源
     *
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    /**
     * 根据唯一索引 保存或修改资源
     *
     * @param resource
     * @return
     */
    int saveOrUpdateUnique(Resource resource);

    List<Long> findMenuIdByResourceId(@Param("resourceIdList") List<Long> resourceIdList);

    /**
     * @auth lijianguo
     * @date 2020/9/29 15:06
     * @remark 获取这个菜单下面的所有的资源
     */
    List<Resource> getMenuResource(@Param("menuId") Long menuId);

    /**
     * @auth lijianguo
     * @date 2020/10/10 16:56
     * @remark 获取所有的菜单下面的资源
     */
    List<Resource> getMulMenuResource(@Param("list") List<Long> menuIdList);

    /**
     * @auth lijianguo
     * @date 2020/10/16 15:46
     * @remark 所有的资源
     */
    List<Resource> getAllResource();



}

























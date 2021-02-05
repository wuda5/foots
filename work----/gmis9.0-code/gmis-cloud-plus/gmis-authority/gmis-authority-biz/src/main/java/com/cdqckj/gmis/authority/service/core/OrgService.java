package com.cdqckj.gmis.authority.service.core;

import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.base.service.SuperCacheService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 业务接口
 * 组织
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
public interface OrgService extends SuperCacheService<Org> {
    /**
     * 查询指定id集合下的所有子集
     *
     * @param ids
     * @return
     */
    List<Org> findChildren(List<Long> ids);

    /**
     * 批量删除以及删除其子节点
     *
     * @param ids
     * @return
     */
    boolean remove(List<Long> ids);

    /**
     * 根据 id 查询组织，并转换成Map结构
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findOrgByIds(Set<Serializable> ids);

    /**
     * 根据 id 查询名称，并转换成Map结构
     *
     * @param ids
     * @return
     */
    Map<Serializable, Object> findOrgNameByIds(Set<Serializable> ids);

    /**
     * 查询组织列表
     * @param name
     * @param status
     * @return
     */
    List<Org> findOrgPage(String name, Boolean status);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 8:55
    * @remark 所有的机构
    */
    List<Org> allOnlineOrg();

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 13:55
    * @remark 用户的所有的机构
    */
    List<Org> userAllOrg(Long userId);
}

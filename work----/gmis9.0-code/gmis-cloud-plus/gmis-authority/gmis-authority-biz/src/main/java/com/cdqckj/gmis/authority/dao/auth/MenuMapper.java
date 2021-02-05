package com.cdqckj.gmis.authority.dao.auth;

import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 菜单
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Repository
public interface MenuMapper extends SuperMapper<Menu> {

    /**
     * 查询用户可用菜单
     *
     * @param userId
     * @return
     */
    List<Menu> findVisibleMenu(@Param("userId") Long userId);

    /**
     * @auth lijianguo
     * @date 2020/9/29 14:01
     * @remark 获取所有的菜单列表
     * @param isEnable
     */
    List<Menu> getAllMenuList(@Param("isEnable") Boolean isEnable);

    /**
     * @auth lijianguo
     * @date 2020/10/10 16:42
     * @remark 这个和子的菜单
     */
    List<Menu> getMenuAndSon(@Param("menuId") Long menuId);

    /**
     * @auth lijianguo
     * @date 2020/10/12 14:26
     * @remark 查询所有的Id
     */
    List<Menu> getAllMenuAndSon(@Param("list") List<Long> menuIdList);
}

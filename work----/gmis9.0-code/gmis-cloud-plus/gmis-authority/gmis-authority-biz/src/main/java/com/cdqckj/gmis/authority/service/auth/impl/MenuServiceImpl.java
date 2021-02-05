package com.cdqckj.gmis.authority.service.auth.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.MenuMapper;
import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.authority.service.auth.MenuService;
import com.cdqckj.gmis.authority.service.auth.ResourceService;
import com.cdqckj.gmis.authority.service.auth.RoleAuthorityService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.common.constant.CacheKey;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.common.constant.CacheKey.MENU;
import static com.cdqckj.gmis.utils.StrPool.DEF_PARENT_ID;

/**
 * <p>
 * 业务实现类
 * 菜单
 * </p>
 *
 * @author gmis
 * @date 2019-07-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MenuServiceImpl extends SuperCacheServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MulTenantProcess mulTenantProcess;

    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Override
    protected String getRegion() {
        return MENU;
    }

    /**
     * 查询用户可用菜单
     * 1，查询缓存中存放的当前用户拥有的所有菜单列表 [menuId,menuId...]
     * 2，缓存&DB为空则返回
     * 3，缓存总用户菜单列表 为空，但db存在，则将list便利set到缓存，并直接返回
     * 4，缓存存在用户菜单列表，则根据菜单id遍历去缓存查询菜单。
     * 5，过滤group后，返回
     *
     * <p>
     * 注意：什么地方需要清除 USER_MENU 缓存
     * 给用户重新分配角色时， 角色重新分配资源/菜单时
     *
     * @param group
     * @param userId
     * @return
     */
    @Override
    public List<Menu> findVisibleMenu(String group, Long userId) {
        String key = key(userId);
        List<Menu> visibleMenu = new ArrayList<>();
        List<Menu> menuList = baseMapper.findVisibleMenu(userId);
        visibleMenu.addAll(menuList);
        // 注释掉缓存，这里可以不用的
//        CacheObject cacheObject = cacheChannel.get(CacheKey.USER_MENU, key, (k) -> {
//            List<Menu> menuList = baseMapper.findVisibleMenu(userId);
//            visibleMenu.addAll(menuList);
//            return visibleMenu.stream().mapToLong(Menu::getId).boxed().collect(Collectors.toList());
//        });

//        if (cacheObject.getValue() == null) {
//            return Collections.emptyList();
//        }

//        if (!visibleMenu.isEmpty()) {
//            // TODO 异步性能 更加
//            visibleMenu.forEach((menu) -> {
//                String menuKey = key(menu.getId());
//                cacheChannel.set(MENU, menuKey, menu);
//            });
//
//            return menuListFilterGroup(group, visibleMenu);
//        }

//        List<Long> list = (List<Long>) cacheObject.getValue();
//
//        List<Menu> menuList = list.stream().map(this::getByIdCache)
//                .filter(Objects::nonNull).collect(Collectors.toList());

        return menuListFilterGroup(group, menuList);
    }

    private List<Menu> menuListFilterGroup(String group, List<Menu> visibleMenu) {
//        Locale locale = LocaleContextHolder.getLocale();
//        String language = locale.getLanguage();
//        List<Menu> visibleMenuEn = new ArrayList<>();
//        if(L18nEnum.EN_US.getL18nDesc().equalsIgnoreCase(language)){
//            for(int i=0;i<visibleMenu.size();i++){
//                for(Map.Entry<String, Object> entry : L18nMenuContainer.L18N_EN_MENU_MAP.entrySet()){
//                    String key = entry.getKey();
//                    String value = (String)entry.getValue();
//                    if(key.equals((visibleMenu.get(i).getId().toString()))){
//                        Menu menu = visibleMenu.get(i);
//                        menu.setLabel(value);
//                        visibleMenuEn.add(menu);
//                        break;
//                    }
//                }
//            }
//            return userBackMenu(visibleMenuEn, group);
//        }
        return userBackMenu(visibleMenu, group);
    }

    private List<Menu> userBackMenu(List<Menu> menus,String group){
        if (StrUtil.isEmpty(group)) {
            return menus;
        }
        return menus.stream().filter((menu) -> group.equals(menu.getGroup())).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIdWithCache(List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        boolean result = this.removeByIds(ids);
        if (result) {
            resourceService.removeByMenuIdWithCache(ids);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWithCache(Menu menu) {
        boolean result = this.updateById(menu);
        if (result) {
            cacheChannel.clear(CacheKey.USER_MENU);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveWithCache(Menu menu) {
        menu.setIsEnable(Convert.toBool(menu.getIsEnable(), true));
        menu.setIsPublic(Convert.toBool(menu.getIsPublic(), false));
        menu.setParentId(Convert.toLong(menu.getParentId(), DEF_PARENT_ID));
        save(menu);

        if (menu.getIsPublic()) {
            cacheChannel.evict(CacheKey.USER_MENU);
        }
        return true;
    }

    @Override
    public MulTenantResult<Menu> updateTenantMenu(Menu menu) {

        return mulTenantProcess.runInOtherTenant(()-> {
            Menu tenantMenu = this.getById(menu.getId());
            if (tenantMenu != null){
                this.updateById(menu);
            }
            return tenantMenu;
        });
    }

    @Override
    public MulTenantResult<Boolean> tenantMenuDelete(List<Long> menuIdList){
        return tenantMenuDeleteReal(menuIdList, mulTenantProcess.getAllTenantIncludeAdmin());
    }

    @Override
    public List<Menu> getAllMenuList(MulTenant mulTenant, Boolean isEnable) {

        MulTenantResult<List<Menu>> result =  mulTenantProcess.runInOtherTenant(()->{
            List<Menu> menuList = this.baseMapper.getAllMenuList(isEnable);
            return menuList;
        }, Arrays.asList(mulTenant));

        return result.getSucTList().get(0);
    }

    @Override
    public MulTenantResult<Boolean> tenantMenuDelete(List<Long> menuIdList, List<MulTenant> mulTenantList) {
        return tenantMenuDeleteReal(menuIdList, mulTenantList);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 14:38
     * @remark 多租户的菜单的删除
     */
    private MulTenantResult<Boolean> tenantMenuDeleteReal(List<Long> menuIdList, List<MulTenant> mulTenantList) {
        if (menuIdList.size() <= 0){
            return new MulTenantResult();
        }

        return mulTenantProcess.runInOtherTenant(() -> {
            // 删除权限
            List<Menu> menuList = this.baseMapper.getAllMenuAndSon(menuIdList);
            List<Long> allMenuIdList = menuList.stream().mapToLong(Menu::getId).boxed().collect(Collectors.toList());
            List<Resource> resourceList;
            if (allMenuIdList.size() > 0) {
                resourceList = resourceService.getMulMenuResource(allMenuIdList);
            } else {
                resourceList = Collections.emptyList();
            }
            List<Long> resourceIdList = resourceList.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
            // 管理平台
            if (mulTenantProcess.adminTenantUser()) {
                resourceService.removeByIds(resourceIdList);
            } else {
                // 删除角色的权限
                roleAuthorityService.removeByAuthorityId(allMenuIdList);
                // 删除资源
                resourceService.removeByIdWithCache(resourceIdList);
            }
            // 删除菜单
            this.removeByIdWithCache(allMenuIdList);
            return true;
        }, mulTenantList);
    }

    @Override
    public MulTenantResult<Boolean> saveOrUpdateBatch(List<Menu> menuList, List<MulTenant> mulTenantList) {
        return mulTenantProcess.runInOtherTenant(() -> {
            this.saveOrUpdateBatch(menuList);
            return true;
        }, mulTenantList);
    }

    @Override
    public MulTenantResult<Boolean> saveOrUpdateBatch(List<Menu> menuList, MulTenant mulTenant) {
        return saveOrUpdateBatch(menuList, Arrays.asList(mulTenant));
    }

    @Override
    public MulTenantResult<Boolean> saveOrUpdateBatch(Menu menu, MulTenant mulTenant) {
        return saveOrUpdateBatch(Arrays.asList(menu), Arrays.asList(mulTenant));
    }

    @Override
    public Menu getById(Serializable id, MulTenant mulTenant) {
        MulTenantResult<Menu> result =  mulTenantProcess.runInOtherTenant(() -> {
            Menu menu = this.getById(id);
            return menu;
        }, Arrays.asList(mulTenant));

        return result.getSucTList().get(0);
    }

    @Override
    public boolean save(Menu entity, MulTenant mulTenant) {
        MulTenantResult<Boolean> result =  mulTenantProcess.runInOtherTenant(() -> {
            this.save(entity);
            return true;
        }, Arrays.asList(mulTenant));

        return result.getSucTList().get(0);
    }
}

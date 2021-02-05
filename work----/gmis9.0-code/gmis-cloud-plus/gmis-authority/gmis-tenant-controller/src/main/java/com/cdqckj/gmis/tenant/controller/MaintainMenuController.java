package com.cdqckj.gmis.tenant.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.authority.dto.auth.MenuSaveDTO;
import com.cdqckj.gmis.authority.dto.auth.MenuUpdateDTO;
import com.cdqckj.gmis.authority.dto.auth.ResetTenantMRDTO;
import com.cdqckj.gmis.authority.dto.auth.RoleAuthoritySaveDTO;
import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.authority.service.auth.MenuService;
import com.cdqckj.gmis.authority.service.auth.ResourceService;
import com.cdqckj.gmis.authority.service.auth.RoleAuthorityService;
import com.cdqckj.gmis.authority.vo.auth.MenuVo;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.TransactionalTenantRest;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.TreeUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.R.success;


/**
 * <p>
 * 前端控制器
 * 运维平台的菜单处理菜单
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/menu")
@Api(value = "Menu", tags = "菜单")
@PreAuth(replace = "menu:")
public class MaintainMenuController extends MainBaseController{

    @Autowired
    private MenuService menuService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private MulTenantProcess mulTenantProcess;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    RoleAuthorityService roleAuthorityService;

    /**
     * @auth lijianguo
     * @date 2020/9/29 10:00
     * @remark 查询所有的菜单-只是操作主库
     */
    @ApiOperation(value = "管理员::查询所有的菜单")
    @GetMapping("/v1/maintain/tree")
    @SysLog("管理员::查询所有的菜单")
    @TransactionalTenantRest
    public R<List<Menu>> maintainAllTree(@RequestParam(required = false) Boolean isEnable) {

        List<Menu> list = menuService.getAllMenuList(mulTenantProcess.getAdminTenant(),isEnable);
        list = TreeUtil.buildTree(list);
        return success(list);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/10 11:50
     * @remark 添加一个菜单
     */
    @ApiOperation(value = "管理员::添加一个菜单")
    @PostMapping("/v1/maintain/save")
    @SysLog("管理员::添加一个菜单")
    @TransactionalTenantRest
    public R save(@RequestBody @Valid MenuSaveDTO menuSave) {
        Menu menu = BeanPlusUtil.toBean(menuSave, Menu.class);
        menu.setCreateUser(getAdminUserId());
        menu.setUpdateUser(getAdminUserId());
        menuService.saveOrUpdateBatch(menu, mulTenantProcess.getAdminTenant());
        return success(menu);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/10 13:29
     * @remark 更新一个菜单--租户的菜单也要更新
     */
    @ApiOperation(value = "管理员::更新一个菜单")
    @PostMapping("/v1/maintain/update")
    @SysLog("管理员::更新一个菜单")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R update(@RequestBody @Valid MenuUpdateDTO menuUpdate) {
        Menu menu = BeanPlusUtil.toBean(menuUpdate, Menu.class);
        menu.setUpdateUser(getAdminUserId());

        List<MulTenant> all = mulTenantProcess.getAllTenantIncludeAdmin();
        List<MulTenant> updateList = new ArrayList<>();
        for (MulTenant mulTenant: all){
            Menu m = menuService.getById(menuUpdate.getId(), mulTenant);
            if (m != null){
                updateList.add(mulTenant);
            }
        }
        menuService.saveOrUpdateBatch(Arrays.asList(menu), updateList);
        return success(menu);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/10 14:00
     * @remark 删除一个菜单 - 1）删除菜单 2）删除子菜单 3)删除菜单下面的资源 4）删除角色的权限
     */
    @ApiOperation(value = "管理员::删除一个菜单")
    @PostMapping("/v1/maintain/delete")
    @SysLog("管理员::删除一个菜单")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R tenantDelete(@NotEmpty @RequestBody List<Long> ids) {
        menuService.tenantMenuDelete(ids);
        return success();
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 8:41
     * @remark 查询某个租户的所有菜单--查询系统的所有的菜单-然后查询租户的所有菜单对比
     */
    @ApiOperation(value = "租户菜单的权限列表")
    @PostMapping("/v1/maintain/tenantAuthMenuList")
    @SysLog("租户菜单的权限列表")
    @TransactionalTenantRest
    public R tenantAuthMenuList(@RequestParam String code) {
        // 管理员菜单
        List<MenuVo> adminMenuList = BeanPlusUtil.toBeanList(menuService.getAllMenuList(mulTenantProcess.getAdminTenant(), null), MenuVo.class);
        Tenant tenant = tenantService.getByCode(code);
        MulTenant mulTenant = BeanUtil.copyProperties(tenant,MulTenant.class);
        // 租户的菜单
        List<MenuVo> tenantMenuList = BeanPlusUtil.toBeanList(menuService.getAllMenuList(mulTenant, null), MenuVo.class);
        SeparateListData<MenuVo> tenantMenuSep = new SeparateListData<>(tenantMenuList);
        for (MenuVo menuVo: adminMenuList){
            if (tenantMenuSep.getTheDataByKey(menuVo.indexKey()) == null){
                menuVo.setRentHasThisMenu(false);
            }else {
                menuVo.setRentHasThisMenu(true);
            }
        }
        List result = TreeUtil.buildTreeForInterface(adminMenuList);
        return success(result);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/16 10:03
     * @remark 租户的菜单列表
     */
    @ApiOperation(value = "租户菜单列表")
    @PostMapping("/v1/maintain/tenantMenuList")
    @SysLog("租户菜单列表")
    @TransactionalTenantRest
    public R tenantMenuList(@RequestParam String code) {
        // 管理员菜单
        Tenant tenant = tenantService.getByCode(code);
        if (tenant.getInitStatus() != 2){
            throw BizException.wrap("租户初始化数据未成功");
        }
        MulTenant mulTenant = new MulTenant(code);
        List<Menu> menuList = menuService.getAllMenuList(mulTenant, null);
        return success(menuList);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 13:25
     * @remark 重新设置一个租户的菜单---/////////---开始测试
     */
    @ApiOperation(value = "重新设置一个租户的菜单")
    @PostMapping("/v1/maintain/resetTenantMenuList")
    @SysLog("重新设置一个租户的菜单")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R resetTenantMenuList(@RequestParam String tenantCode,@NotEmpty @RequestBody List<Long> ids) {

        // 租户的菜单
        Tenant tenant = tenantService.getByCode(tenantCode);
        MulTenant mulTenant = BeanUtil.copyProperties(tenant,MulTenant.class);
        List<MenuVo> tenantMenuList = BeanPlusUtil.toBeanList(menuService.getAllMenuList(mulTenant, null), MenuVo.class);
        SeparateListData<MenuVo> tenantMenuSep = new SeparateListData<>(tenantMenuList);
        // 租户保留的菜单
        List<MenuVo> tenantKeepMenuList = BeanPlusUtil.toBeanList(menuService.listByIds(ids),MenuVo.class);
        // 租户添加的菜单
        List<Menu> tenantAdd = new ArrayList<>();
        for (MenuVo menuVo : tenantKeepMenuList){
            MenuVo deleteMenu = tenantMenuSep.deleteByKey(menuVo.indexKey());
            if (deleteMenu == null){
                tenantAdd.add(menuVo);
            }
        }
        // 租户菜单的添加
        menuService.saveOrUpdateBatch(tenantAdd, mulTenant);
        // 租户删除的菜单
        List<MenuVo> tenantDelete = tenantMenuSep.getAllData();
        List<Long> menuDeleteIdList = tenantDelete.stream().mapToLong(Menu::getId).boxed().collect(Collectors.toList());
        menuService.tenantMenuDelete(menuDeleteIdList, Arrays.asList(mulTenant));

        return success();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/10 13:59
     * @remark 要将权限分配到对应的管理员的角色中
     */
    @ApiOperation(value = "重新设置一个租户的菜单和资源")
    @PostMapping("/v1/maintain/resetTenantMenuAndResource")
    @SysLog("重新设置一个租户的菜单和资源")
//    @GlobalTransactional
//    @TransactionalTenantRest(type = 2)
    public R resetTenantMenuAResource(@Valid @RequestBody ResetTenantMRDTO resetTenantMRDTO) {

        // 租户的菜单
        Tenant tenant = tenantService.getByCode(resetTenantMRDTO.getTenantCode());
        MulTenant mulTenant = BeanUtil.copyProperties(tenant,MulTenant.class);
        List<MenuVo> tenantMenuList = BeanPlusUtil.toBeanList(menuService.getAllMenuList(mulTenant, null), MenuVo.class);
        SeparateListData<MenuVo> tenantMenuSep = new SeparateListData<>(tenantMenuList);
        // 租户保留的菜单
        List<MenuVo> tenantKeepMenuList = BeanPlusUtil.toBeanList(menuService.listByIds(resetTenantMRDTO.getMenuIdList()),MenuVo.class);
        // 租户添加的菜单
        List<Menu> tenantAdd = new ArrayList<>();
        for (MenuVo menuVo : tenantKeepMenuList){
            MenuVo deleteMenu = tenantMenuSep.deleteByKey(menuVo.indexKey());
            if (deleteMenu == null){
                tenantAdd.add(menuVo);
            }
        }
        // 租户菜单的添加
        menuService.saveOrUpdateBatch(tenantAdd, mulTenant);
        // 租户删除的菜单
        List<MenuVo> tenantDelete = tenantMenuSep.getAllData();
        List<Long> menuDeleteIdList = tenantDelete.stream().mapToLong(Menu::getId).boxed().collect(Collectors.toList());
        menuService.tenantMenuDelete(menuDeleteIdList, Arrays.asList(mulTenant));

        List<Resource> defaultResource = resourceService.getMulMenuResource(null, mulTenantProcess.getAdminTenant());
        List<Resource> tenantResource = resourceService.getMulMenuResource(null, mulTenant);
        List<Long> tenantResourceIdList = tenantResource.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
        // 删除资源
        List<Long> deleteResourceList = new ArrayList<>();
        // 添加资源
        List<Resource> addResourceList = new ArrayList<>();
        for (Resource resource: defaultResource){

            Boolean tenantInclude = tenantResourceIdList.contains(resource.getId());
            Boolean paramInclude = resetTenantMRDTO.getResourceIdList().contains(resource.getId());
            if (paramInclude == true && tenantInclude == false){
                addResourceList.add(resource);
            }
            if (paramInclude == false && tenantInclude == true){
                deleteResourceList.add(resource.getId());
            }
        }
        resourceService.saveTenantResource(addResourceList, Arrays.asList(mulTenant));
        resourceService.deleteTenantResource(deleteResourceList, Arrays.asList(mulTenant));

        // 要将添加的菜单和资源添加到主要的角色中去
        RoleAuthoritySaveDTO authoritySaveDTO = new RoleAuthoritySaveDTO();
        authoritySaveDTO.setRoleId(100L);
        authoritySaveDTO.setMenuIdList(resetTenantMRDTO.getMenuIdList().stream().collect(Collectors.toList()));
        authoritySaveDTO.setResourceIdList(resetTenantMRDTO.getResourceIdList().stream().collect(Collectors.toList()));
        roleAuthorityService.addTenantResource(authoritySaveDTO, mulTenant);

        roleAuthorityService.clearCacheByRole(100L);
        return success();
    }

}

package com.cdqckj.gmis.tenant.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.authority.dto.auth.ResourceSaveDTO;
import com.cdqckj.gmis.authority.dto.auth.ResourceUpdateDTO;
import com.cdqckj.gmis.authority.entity.auth.Menu;
import com.cdqckj.gmis.authority.entity.auth.Resource;
import com.cdqckj.gmis.authority.service.auth.MenuService;
import com.cdqckj.gmis.authority.service.auth.ResourceService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.Separate.SeparateListData;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantResult;
import com.cdqckj.gmis.common.domain.tenant.TransactionalTenantRest;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.tenant.dto.ResetTenantResourceDTO;
import com.cdqckj.gmis.tenant.vo.TenantMenuResourceVo;
import com.cdqckj.gmis.tenant.vo.TenantResourceVo;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * 资源
 * </p>
 *
 * @author gmis
 * @date 2019-07-22
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/resource")
@Api(value = "Resource", tags = "资源")
@PreAuth(replace = "resource:")
public class MaintainResourceController extends MainBaseController{

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MulTenantProcess mulTenantProcess;

    @Autowired
    private MenuService menuService;

    /**
     * @auth lijianguo
     * @date 2020/9/29 14:56
     * @remark 获取这个菜单上的资源
     */
    @ApiOperation(value = "管理员::获取这个菜单上的资源")
    @GetMapping("/v1/maintain/menuResource")
    @SysLog("管理员::获取这个菜单上的资源")
    @TransactionalTenantRest
    public R<List<Resource>> menuResource(@RequestParam Long menuId) {

        List<Resource> resourceList = resourceService.getMulMenuResource(menuId, mulTenantProcess.getAdminTenant());
        return R.success(resourceList);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/29 15:17
     * @remark 更新一个资源，更新需要同步到用户平台-不做分布式事务
     */
    @ApiOperation(value = "管理员::更新一个资源")
    @PostMapping("/v1/maintain/update")
    @SysLog("管理员::更新一个资源")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R update(@RequestBody @Valid ResourceUpdateDTO resourceUpdateDTO) {

        Resource resource = BeanPlusUtil.toBean(resourceUpdateDTO, Resource.class);
        resource.setUpdateUser(getAdminUserId());
        List<MulTenant> allTenant = mulTenantProcess.getAllTenantIncludeAdmin();
        List<MulTenant> updateTenant = new ArrayList<>();
        for (MulTenant mulTenant: allTenant){
            Resource r = resourceService.getById(resourceUpdateDTO.getId(), mulTenant);
            if (r != null){
                updateTenant.add(mulTenant);
            }
        }
        resourceService.saveOrUpdateBatch(Arrays.asList(resource), updateTenant);
        return R.success(resource);
    }

    /**
     * @auth lijianguo
     * @date 2020/9/29 15:17
     * @remark 添加一个资源，只需要保存到运维平台
     */
    @ApiOperation(value = "管理员::添加一个资源")
    @PostMapping("/v1/maintain/save")
    @SysLog("管理员::添加一个资源")
    @TransactionalTenantRest
    public R<Resource> save(@RequestBody @Valid ResourceSaveDTO resourceSaveDTO) {

        Resource resource = BeanPlusUtil.toBean(resourceSaveDTO, Resource.class);
        resource.setUpdateUser(getAdminUserId());
        resource.setCreateUser(getAdminUserId());
        resourceService.saveOrUpdateBatch(Arrays.asList(resource), Arrays.asList(mulTenantProcess.getAdminTenant()));
        return R.success(resource);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 13:48
     * @remark 删除一个菜单的资源，需要删除租户的资源，和权限
     */
    @ApiOperation(value = "管理员::删除一个资源")
    @PostMapping("/v1/maintain/delete")
    @SysLog("管理员::删除一个资源")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R delete(@NotEmpty @RequestBody List<Long> ids) {
        resourceService.deleteTenantResource(ids, mulTenantProcess.getAllTenantIncludeAdmin());
        return R.success();
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 14:07
     * @remark 获取这个资源的租户的权限情况
     */
    @ApiOperation(value = "管理员::获取这个资源的租户的权限情况")
    @PostMapping("/v1/maintain/tenantResource")
    @SysLog("管理员::获取这个资源的租户的权限情况")
    @TransactionalTenantRest
    public R tenantResource(@NotNull @RequestParam Long resourceId) {

        Resource defaultResource = resourceService.getById(resourceId, mulTenantProcess.getAdminTenant());
        MulTenantResult<TenantResourceVo> mulTenantResult = resourceService.getTenantResourceInfo(defaultResource);
        List list = mulTenantResult.getSucTList();
        return R.success(list);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/15 13:32
     * @remark 将这个菜单的资源重新分配给这个租户
     */
    @ApiOperation(value = "将这个菜单的资源重新分配给这个租户")
    @PostMapping("/v1/maintain/tenantMenuResource")
    @SysLog("将这个菜单的资源重新分配给这个租户")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R tenantResource(@NotNull @RequestParam Long menuId, @NotNull @RequestParam String tenantCode,@NotEmpty @RequestBody List<Long> resourceIds) {

        Menu menu = menuService.getById(menuId, new MulTenant(tenantCode));
        MulTenant tenant = new MulTenant(tenantCode);
        if (menu == null){
            menu = menuService.getById(menuId, mulTenantProcess.getAdminTenant());
            menuService.save(menu, tenant);
        }
        List<Resource> defaultResource = resourceService.getMulMenuResource(menuId, mulTenantProcess.getAdminTenant());
        List<Resource> tenantResource = resourceService.getMulMenuResource(menuId, new MulTenant(tenantCode));
        List<Long> tenantResourceIdList = tenantResource.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
        // 删除资源
        List<Long> deleteTenantList = new ArrayList<>();
        // 添加资源
        List<Resource> addTenantList = new ArrayList<>();
        for (Resource resource: defaultResource){

            Boolean tenantInclude = tenantResourceIdList.contains(resource);
            Boolean paramInclude = resourceIds.contains(resource);
            if (paramInclude == true && tenantInclude == false){
                addTenantList.add(resource);
            }
            if (paramInclude == false && tenantInclude == true){
                deleteTenantList.add(resource.getId());
            }
        }
        resourceService.saveTenantResource(addTenantList, Arrays.asList(tenant));
        resourceService.deleteTenantResource(deleteTenantList, Arrays.asList(tenant));
        return R.success();
    }

    /**
     * @auth lijianguo
     * @date 2020/10/15 14:28
     * @remark 获取这个一个租户的这个菜单的资源分配情况
     */
    @ApiOperation(value = "租户菜单资源权限列表")
    @PostMapping("/v1/maintain/tenantAuthMenuResourceList")
    @SysLog("获取这个一个租户的这个菜单的资源分配情况")
    @TransactionalTenantRest
    public R tenantAuthMenuResourceList(@NotNull @RequestParam Long menuId, @NotNull @RequestParam String tenantCode) {

        List<Resource> defaultResource = resourceService.getMulMenuResource(menuId, mulTenantProcess.getAdminTenant());
        List<Resource> tenAntResource = resourceService.getMulMenuResource(menuId, new MulTenant(tenantCode));

        List<TenantMenuResourceVo> defaultList = BeanPlusUtil.toBeanList(defaultResource, TenantMenuResourceVo.class);
        List<TenantMenuResourceVo> tenantList = BeanPlusUtil.toBeanList(tenAntResource, TenantMenuResourceVo.class);
        SeparateListData<TenantMenuResourceVo> tenantSep = new SeparateListData<>(tenantList);
        for(TenantMenuResourceVo defaultVo: defaultList){
            if (tenantSep.getTheDataByKey(defaultVo.indexKey()) == null){
                defaultVo.setHoldAuthority(false);
            }else {
                defaultVo.setHoldAuthority(true);
            }
        }
        return R.success(defaultList);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/16 10:19
     * @remark 租户的菜单资源
     */
    @ApiOperation(value = "租户的菜单资源列表")
    @PostMapping("/v1/maintain/tenantMenuResourceList")
    @SysLog("租户的菜单资源列表")
    @TransactionalTenantRest
    public R tenantMenuResourceList(@NotNull @RequestParam Long menuId, @NotNull @RequestParam String tenantCode) {

        List<Resource> tenAntResourceList = resourceService.getMulMenuResource(menuId, new MulTenant(tenantCode));
        return R.success(tenAntResourceList);
    }


    /**
     * @auth lijianguo
     * @date 2020/10/8 15:48
     * @remark 资源重新分配给租户，有可能给租户 1)添加的资源 2)删除的资源  3）不变的资源
     */
    @ApiOperation(value = "管理员::资源重新分配给租户")
    @PostMapping("/v1/maintain/resetTenantResource")
    @SysLog("管理员::资源重新分配给租户")
    @GlobalTransactional
    @TransactionalTenantRest(type = 2)
    public R resetTenantResource(@RequestBody @Validated ResetTenantResourceDTO tenantResourceDTO) {

        Resource defaultResource = resourceService.getById(tenantResourceDTO.getResourceId(), mulTenantProcess.getAdminTenant());
        MulTenantResult<TenantResourceVo> mulTenantResult = resourceService.getTenantResourceInfo(defaultResource);
        // 这个资源每个用户的拥有情况--数据库保存的情况
        List<TenantResourceVo> tenantResourceList = mulTenantResult.getSucTList();
        // 添加资源的租户列表--用户修改的情况
        SeparateListData<ResetTenantResourceDTO.TenantInfo> addResource = new SeparateListData<>(tenantResourceDTO.getTenantInfoList());
        // 删除资源的租户
        List<MulTenant> deleteTenantList = new ArrayList<>();
        // 添加资源的租户
        List<MulTenant> addTenantList = new ArrayList<>();
        for (TenantResourceVo tenantResourceVo: tenantResourceList){
            Boolean add = addResource.containKey(tenantResourceVo.getCode());
            // 用户添加  数据库没有   新增
            if (add == true && tenantResourceVo.getHoldAuthority() == false){
                addTenantList.add(BeanUtil.copyProperties(tenantResourceVo,MulTenant.class));
            }
            // 用户删除  数据库有   删除
            if (add ==false && tenantResourceVo.getHoldAuthority() == true){
                deleteTenantList.add(BeanUtil.copyProperties(tenantResourceVo,MulTenant.class));
            }
        }
        resourceService.saveTenantResource(Arrays.asList(defaultResource), addTenantList);
        resourceService.deleteTenantResource(defaultResource.getId(), deleteTenantList);
        return R.success();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:38
     * @remark 获取这个租户的所有的额资源
     */
    @ApiOperation(value = "获取这个租户的所有的资源")
    @PostMapping("/v1/maintain/tenantAllResource")
    @SysLog("获取这个租户的所有的资源")
    public R tenantAllResource(@NotNull @RequestParam String tenantCode) {
        List<Resource> tenAntResourceList = resourceService.tenantAllResource(new MulTenant(tenantCode));
        return R.success(tenAntResourceList);
    }


}

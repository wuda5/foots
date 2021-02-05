package com.cdqckj.gmis.tenant.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.tenant.service.GlobalUserService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.constant.BizConstant;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.tenant.dto.GlobalUserPageDTO;
import com.cdqckj.gmis.tenant.dto.GlobalUserSaveDTO;
import com.cdqckj.gmis.tenant.dto.GlobalUserUpdateDTO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.StrHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 全局账号
 * </p>
 *
 * @author gmis
 * @date 2019-10-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/globalUser")
@Api(value = "GlobalUser", tags = "全局账号")
@SysLog(enabled = false)
public class GlobalUserController extends SuperController<GlobalUserService, Long, GlobalUser, GlobalUserPageDTO, GlobalUserSaveDTO, GlobalUserUpdateDTO> {

    @Autowired
    private UserService userService;

    @Override
    public R<GlobalUser> handlerSave(GlobalUserSaveDTO model) {
        if (StrUtil.isEmpty(model.getTenantCode()) || BizConstant.SUPER_TENANT.equals(model.getTenantCode())) {
            //1. 运维平台下的用户保存
            return success(baseService.save(model));
        } else {
            // 2. 各个租户下的管理账号设置（方便初始了租户后其可以用此用户登录租户平台）--需要给其设置默认最大的组织 orgId=1(否则登录时会检测报错)
            BaseContextHandler.setTenant(model.getTenantCode());
            User user = BeanPlusUtil.toBean(model, User.class);
            user.setName(StrHelper.getOrDef(model.getName(), model.getAccount()));
            if (StrUtil.isEmpty(user.getPassword())) {
                user.setPassword(BizConstant.DEF_PASSWORD);
            }
            user.setStatus(true);
            RemoteData<Long, Org> org = new RemoteData<>();
            org.setKey(1L);
            user.setOrg(org);

            userService.initUser(user);
            return success(BeanPlusUtil.toBean(user, GlobalUser.class));
        }
    }

    @Override
    public R<GlobalUser> handlerUpdate(GlobalUserUpdateDTO model) {
        if (StrUtil.isEmpty(model.getTenantCode()) || BizConstant.SUPER_TENANT.equals(model.getTenantCode())) {
            return success(baseService.update(model));
        } else {
            BaseContextHandler.setTenant(model.getTenantCode());
            User user = BeanPlusUtil.toBean(model, User.class);
            userService.updateUser(user);
            return success(BeanPlusUtil.toBean(user, GlobalUser.class));
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantCode", value = "企业编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "账号", dataType = "string", paramType = "query"),
    })
    @ApiOperation(value = "检测账号是否可用", notes = "检测账号是否可用")
    @GetMapping("/check")
    public R<Boolean> check(@RequestParam String account) {
        String tenantCode = BaseContextHandler.getTenant();
        if (StrUtil.isEmpty(tenantCode) || BizConstant.SUPER_TENANT.equals(tenantCode)) {
            return success(baseService.check(account));
        } else {
            return success(userService.check(account));
        }
    }

    private void handlerUserWrapper(QueryWrap<User> wrapper, PageParams<GlobalUserPageDTO> params) {
        if (CollUtil.isNotEmpty(params.getMap())) {
            Map<String, String> map = params.getMap();
            //拼装区间
            for (Map.Entry<String, String> field : map.entrySet()) {
                String key = field.getKey();
                String value = field.getValue();
                if (StrUtil.isEmpty(value)) {
                    continue;
                }
                if (key.endsWith("_st")) {
                    String beanField = StrUtil.subBefore(key, "_st", true);
                    wrapper.ge(getDbField(beanField, getEntityClass()), DateUtils.getStartTime(value));
                }
                if (key.endsWith("_ed")) {
                    String beanField = StrUtil.subBefore(key, "_ed", true);
                    wrapper.le(getDbField(beanField, getEntityClass()), DateUtils.getEndTime(value));
                }
            }
        }
    }

    private void handlerUserWrapperEx(LbqWrapper<User> wrapper, PageParams<GlobalUserPageDTO> params) {
        if (CollUtil.isNotEmpty(params.getMap())) {
            Map<String, String> map = params.getMap();
            //拼装区间
            for (Map.Entry<String, String> field : map.entrySet()) {
                String key = field.getKey();
                String value = field.getValue();
                if (StrUtil.isEmpty(value)) {
                    continue;
                }
                if (key.endsWith("_st")) {
                    String beanField = StrUtil.subBefore(key, "_st", true);
                    wrapper.ge(User::getCreateTime, DateUtils.getStartTime(value));
                }
                if (key.endsWith("_ed")) {
                    String beanField = StrUtil.subBefore(key, "_ed", true);
                    wrapper.le(User::getCreateTime, DateUtils.getEndTime(value));
                }
            }
        }
    }

    @Override
    public void query(PageParams<GlobalUserPageDTO> params, IPage<GlobalUser> page, Long defSize) {
        GlobalUserPageDTO model = params.getModel();
        if (StrUtil.isEmpty(model.getTenantCode()) || BizConstant.SUPER_TENANT.equals(model.getTenantCode())) {
            QueryWrap<GlobalUser> wrapper = Wraps.q();
            handlerWrapper(wrapper, params);
            wrapper.lambda().eq(GlobalUser::getTenantCode, model.getTenantCode())
                    .like(GlobalUser::getAccount, model.getAccount())
                    .like(GlobalUser::getName, model.getName());
            baseService.page(page, wrapper);
            return;
        }
        BaseContextHandler.setTenant(model.getTenantCode());
        IPage<User> userPage = params.getPage();
        LbqWrapper<User> wrapper = new LbqWrapper<>();
        handlerUserWrapperEx(wrapper, params);
        wrapper.eq(User::getCreateUser,BaseContextHandler.getUserId())
                .like(User::getAccount, model.getAccount())
                .like(User::getName, model.getName());
        // updater by hc 2020/09/18
        IPage<User> iPage = userService.findPage(userPage, wrapper, model.getTenantCode());

        page.setCurrent(iPage.getCurrent());
        page.setSize(iPage.getSize());
        page.setTotal(iPage.getTotal());
        page.setPages(iPage.getPages());
        List<GlobalUser> list = BeanPlusUtil.toBeanList(iPage.getRecords(), GlobalUser.class);
        list.forEach(e->e.setTenantCode(model.getTenantCode()));
        page.setRecords(list);
    }


    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tenantCode", value = "企业编码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ids[]", value = "主键id", dataType = "array", paramType = "query"),
    })
    public R<Boolean> delete(@RequestParam String tenantCode, @RequestParam("ids[]") List<Long> ids) {
        if (StrUtil.isEmpty(tenantCode) || BizConstant.SUPER_TENANT.equals(tenantCode)) {
            return success(baseService.removeByIds(ids));
        } else {
            BaseContextHandler.setTenant(tenantCode);
            return success(userService.remove(ids));
        }
    }

}

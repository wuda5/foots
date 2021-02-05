package com.cdqckj.gmis.tenant.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperCacheController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.tenant.dto.TenantSaveDTO;
import com.cdqckj.gmis.tenant.dto.TenantUpdateDTO;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.tenant.vo.TenantVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.seata.core.context.RootContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;

/**
 * <p>
 * 前端控制器
 * 企业
 * </p>
 *
 * @author gmis
 * @date 2019-10-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tenant")
@Api(value = "Tenant", tags = "企业")
@SysLog(enabled = false)
public class TenantController extends SuperCacheController<TenantService, Long, Tenant, Tenant, TenantSaveDTO, TenantUpdateDTO> {


    @ApiOperation(value = "查询所有企业", notes = "查询所有企业")
    @GetMapping("/all")
    public R<List<Tenant>> list() {

            return success(baseService.list(Wraps.<Tenant>lbQ()
                    .eq(Tenant::getStatus, TenantStatusEnum.NORMAL)
            ));
    }

    @Override
    public R<Tenant> handlerSave(TenantSaveDTO model) {
        //add by hc 验证域名是否重复，取消分布式事务
//        RootContext.unbind();
//        Tenant tenant1 = baseService.getByUrl(model.getUrl());
//        if(null!=tenant1){
//            return R.fail("域名已重复");
//        }
//
//        Tenant tenant = baseService.save(model);
//        return success(tenant);
        return null;
    }

    @PostMapping(value = "saveTenant")
    public R<Tenant> saveTenant(@RequestBody TenantSaveDTO model){
        //add by hc 验证域名是否重复，取消分布式事务
        RootContext.unbind();
        Tenant tenant1 = baseService.getByUrl(model.getUrl());
        if(null!=tenant1){
            return R.fail("域名已重复");
        }

        Tenant tenant = baseService.save(model);
        return success(tenant);
    }

    @ApiOperation(value = "检测租户是否存在", notes = "检测租户是否存在")
    @GetMapping("/check/{code}")
    @ApiOperationSupport(author = "gmis")
    public R<Boolean> check(@PathVariable("code") String code) {
        return success(baseService.check(code));
    }

    @Override
    public R<Boolean> handlerDelete(List<Long> ids) {
        // 这个操作相当的危险，请谨慎操作！！
        return success(baseService.delete(ids));
    }

    @ApiOperationSupport(author = "gmis")
    @ApiOperation(value = "修改租户状态", notes = "修改租户状态")
    @PostMapping("/status")
    public R<Boolean> updateStatus(@RequestParam("ids[]") List<Long> ids,
                                   @RequestParam @NotNull(message = "状态不能为空") TenantStatusEnum status) {
        return success(baseService.update(Wraps.<Tenant>lbU().set(Tenant::getStatus, status).in(Tenant::getId, ids)));
    }

    @ApiOperation(value = "通过租户编码获取公司信息", notes = "通过租户编码获取公司信息")
    @GetMapping("/companyInfo")
    @ApiOperationSupport(author = "gmis")
    public R<Map<String,Object>> getCompanyInfoByTenantCode(@RequestParam("code") String code) {
        Map<String,Object> data = new HashMap<>(8);
        if (StringUtil.isEmpty(code)){
            code =BaseContextHandler.getTenant();
        }
        Tenant tenant = baseService.getByCode(code);
        if(tenant != null) {
            data.put("companyId",tenant.getCode());
            data.put("companyName",tenant.getName());
            data.put("expirationTime",tenant.getExpirationTime());
            // 还差多少天到期
            if (tenant.getExpirationTime()!=null){
                Duration duration = Duration.between(LocalDateTime.now(),tenant.getExpirationTime());
                long days = duration.toDays(); //相差的天数
                long hours = duration.toHours();//相差的小时数
                long l = hours % 24;
//            long minutes = duration.toMinutes();//相差的分钟数
                data.put("durationDay",days);
                data.put("durationHour",l);
            }

        }
       return success(data);
    }

    /**
     * 租户分页数据查询,
     * 仅限 获取自己创建的数据
     * @author hc 2020/08/31
     * @param params
     * @return
     */
    @PostMapping("/pageNew")
    public R<IPage<Tenant>> pageNew(@RequestBody PageParams<TenantVo> params) {

        PageParams<Tenant> paramNew = BeanUtil.copyProperties(params,PageParams.class);
        return super.page(paramNew);
    }

    @ApiOperation(value = "通过访问URL查询租户信息", notes = "通过访问URL查询租户信息")
    @GetMapping("/code/{url}")
    @ApiOperationSupport(author = "gmis")
    public String getTenantByUrl(@PathVariable("url") String url) throws Exception {
         Tenant tenant = baseService.getByUrl(url);
        if(tenant == null) {
            return  "";
        }else{
            return tenant.getCode();
        }
    }
}

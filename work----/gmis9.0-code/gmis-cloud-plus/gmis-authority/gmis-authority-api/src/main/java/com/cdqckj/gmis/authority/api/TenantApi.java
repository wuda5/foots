package com.cdqckj.gmis.authority.api;


import com.cdqckj.gmis.authority.api.hystrix.TenantApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.tenant.entity.Tenant;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户
 *
 * @author gmis
 * @date 2020/06/01
 */
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/tenant", qualifier = "tenantApi")
public interface TenantApi {
    /**
     * 根据url查询租户编码
     *
     * @param url
     * @return
     */
    @GetMapping("/code/{url}")
    String  findCodeByUrl(@PathVariable("url") String url);

    @ApiOperation(value = "住户查询",notes = "住户按条件查询")
    @PostMapping("/query")
    R<List<Tenant>> query(@RequestBody Tenant data);


    @ApiOperation(value = "根据id查询实体", notes = "根据id查询实体")
    @PostMapping("/getById")
    R<Tenant> getById(@RequestParam("id") Long id);

    @ApiOperation(value = "查询所有企业", notes = "查询所有企业")
    @GetMapping("/all")
    R<List<Tenant>> all();
}


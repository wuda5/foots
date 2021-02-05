package com.cdqckj.gmis.tenant.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.tenant.api.fallback.OauthDsApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 权限服务初始化数据源
 *
 * @author gmis
 * @date 2020年04月05日18:18:26
 */
@FeignClient(name = "${gmis.feign.oauth-server:gmis-oauth-server}", path = "/ds", fallback = OauthDsApiFallback.class)
public interface OauthDsApi {
    /**
     * 初始化数据源
     *
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    R<Boolean> init(@RequestParam(value = "tenant") String tenant);

    /**
     * 删除数据源
     *
     * @param tenant
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    R<Object> remove(@RequestParam(value = "tenant") String tenant);
}

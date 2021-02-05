package com.cdqckj.gmis.authority.api;

import com.cdqckj.gmis.authority.api.hystrix.TenantBizApiFallback;
import com.cdqckj.gmis.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * 租户对外接口
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.authority-server:gmis-authority-server}", fallback = TenantBizApiFallback.class
        , path = "/tenant", qualifier = "tenantBizApi")
public interface TenantBizApi {
    /**
     * @param code
     * @return
     */
    @RequestMapping(value = "/companyInfo", method = RequestMethod.GET)
    R<Map<String,Object>> getCompanyInfoByTenantCode(@RequestParam("code") String code);
}

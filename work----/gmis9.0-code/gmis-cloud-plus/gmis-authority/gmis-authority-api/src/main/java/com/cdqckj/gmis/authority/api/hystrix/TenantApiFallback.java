package com.cdqckj.gmis.authority.api.hystrix;

import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.tenant.entity.Tenant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 租户API熔断
 *
 * @author gmis
 * @date 2020/06/01
 */
@Component
public class TenantApiFallback implements TenantApi {
    @Override
    public String findCodeByUrl(@PathVariable("url") String url) {
        return "";
    }

    @Override
    public R<List<Tenant>> query(Tenant data) {
        return R.timeout();
    }

    @Override
    public R<Tenant> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<Tenant>> all() {
        return null;
    }

}
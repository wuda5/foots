package com.cdqckj.gmis.authority.api.hystrix;

import com.cdqckj.gmis.authority.api.TenantBizApi;
import com.cdqckj.gmis.base.R;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户API熔断
 *
 * @author gmis
 * @date 2019/07/23
 */
@Component
public class TenantBizApiFallback implements TenantBizApi {

    @Override
    public R<Map<String,Object>> getCompanyInfoByTenantCode(String code) {
        return R.timeout();
    }
}

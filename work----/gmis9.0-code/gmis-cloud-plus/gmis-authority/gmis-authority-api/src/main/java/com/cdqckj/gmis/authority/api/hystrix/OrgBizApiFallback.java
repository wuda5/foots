package com.cdqckj.gmis.authority.api.hystrix;

import com.cdqckj.gmis.authority.api.OrgBizApi;
import com.cdqckj.gmis.authority.api.TenantBizApi;
import com.cdqckj.gmis.authority.entity.core.Org;
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
public class OrgBizApiFallback implements OrgBizApi {

    @Override
    public R<Map<String, Object>> getOrgTree() {
        return R.timeout();
    }

    @Override
    public R<List<Org>> allOnlineOrg() {
        return R.timeout();
    }

    @Override
    public R<List<Org>> userAllOrg(Long userId) {
        return R.timeout();
    }
}

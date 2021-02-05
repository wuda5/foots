package com.cdqckj.gmis.tenant.api.fallback;

import com.cdqckj.gmis.tenant.api.OauthDsApi;
import com.cdqckj.gmis.base.R;
import org.springframework.stereotype.Component;

/**
 * 实现
 *
 * @author gmis
 * @date 2020年04月05日18:20:22
 */
@Component
public class OauthDsApiFallback implements OauthDsApi {
    @Override
    public R<Boolean> init(String tenant) {
        return R.timeout();
    }


    @Override
    public R<Object> remove(String tenant) {
        return R.timeout();
    }
}

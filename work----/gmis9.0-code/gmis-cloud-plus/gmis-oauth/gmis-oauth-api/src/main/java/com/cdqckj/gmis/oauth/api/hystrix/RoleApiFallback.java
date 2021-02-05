package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.oauth.api.RoleApi;
import com.cdqckj.gmis.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色查询API
 *
 * @author gmis
 * @date 2019/08/02
 */
@Component
public class RoleApiFallback implements RoleApi {
    @Override
    public R<List<Long>> findUserIdByCode(String[] codes) {
        return R.timeout();
    }
}

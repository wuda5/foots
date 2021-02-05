package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauth.api.UserApi;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 用户API熔断
 *
 * @author gmis
 * @date 2019/07/23
 */
@Component
public class UserApiFallback implements UserApi {
    @Override
    public Map<String, Object> getDataScopeById(Long id) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("dsType", 5);
        map.put("orgIds", Collections.emptyList());
        return map;
    }

    @Override
    public Map<Serializable, Object> findUserByIds(Set<Serializable> codes) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Serializable, Object> findUserNameByIds(Set<Serializable> codes) {
        return Collections.emptyMap();
    }

    @Override
    public R<User> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<User>> query(User data) {
        return R.timeout();
    }
}

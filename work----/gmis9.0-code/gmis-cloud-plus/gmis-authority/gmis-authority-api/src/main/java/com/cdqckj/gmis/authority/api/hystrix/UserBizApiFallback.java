package com.cdqckj.gmis.authority.api.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.authority.dto.auth.UserPageDTO;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户API熔断
 *
 * @author gmis
 * @date 2019/07/23
 */
@Component
public class UserBizApiFallback implements UserBizApi {
    @Override
    public R<List<User>> query(User query) {
        return R.timeout();
    }

    @Override
    public R<List<Long>> findAllUserId() {
        return R.timeout();
    }

    @Override
    public R<Page<User>> page(PageParams<UserPageDTO> params) {
        return R.timeout();
    }


    @Override
    public R<User> update( User user) {
        return R.timeout();
    }
    @Override
    public R<User> get( Long id) {
        return R.timeout();
    }

    @Override
    public R<User> update(Long orgId) {
        return R.timeout();
    }
}

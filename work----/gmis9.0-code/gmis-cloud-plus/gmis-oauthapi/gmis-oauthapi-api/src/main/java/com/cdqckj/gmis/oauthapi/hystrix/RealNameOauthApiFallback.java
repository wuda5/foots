package com.cdqckj.gmis.oauthapi.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauthapi.RealNameOauthApi;
import com.cdqckj.gmis.oauthapi.dto.RealNameOauthSaveDTO;
import com.cdqckj.gmis.oauthapi.entity.RealNameOauth;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断类
 *
 * @author hc
 * @date 2020/10/09
 */
@Component
public class RealNameOauthApiFallback implements RealNameOauthApi {
    @Override
    public R<RealNameOauth> save(RealNameOauthSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<List<RealNameOauth>> query(RealNameOauth data) {
        return R.timeout();
    }
}

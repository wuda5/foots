package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.oauth.api.ParameterApi;
import org.springframework.stereotype.Component;


/**
 * 熔断类
 *
 * @author gmis
 * @date 2020年02月09日11:24:23
 */
@Component
public class ParameterApiFallback implements ParameterApi {
    @Override
    public R<String> getValue(String key, String defVal) {
        return R.timeout();
    }
}

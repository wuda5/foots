package com.cdqckj.gmis.oauth.api.hystrix;

import com.cdqckj.gmis.oauth.api.LogApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.log.entity.OptLogDTO;
import org.springframework.stereotype.Component;

/**
 * 日志操作 熔断
 *
 * @author gmis
 * @date 2019/07/02
 */
@Component
public class LogApiHystrix implements LogApi {
    @Override
    public R<OptLogDTO> save(OptLogDTO log) {
        return R.timeout();
    }
}

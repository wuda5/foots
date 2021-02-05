package com.cdqckj.gmis.jobs.api.fallback;

import com.cdqckj.gmis.jobs.api.JobsTimingApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.jobs.dto.XxlJobInfo;
import org.springframework.stereotype.Component;

/**
 * 定时API 熔断
 *
 * @author gmis
 * @date 2019/07/16
 */
@Component
public class JobsTimingApiFallback implements JobsTimingApi {
    @Override
    public R<String> addTimingTask(XxlJobInfo xxlJobInfo) {
        return R.timeout();
    }
}

package com.cdqckj.gmis.jobs.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.jobs.api.fallback.JobsTimingApiFallback;
import com.cdqckj.gmis.jobs.dto.XxlJobInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 定时API
 *
 * @author gmis
 * @date 2019/07/05
 */
@FeignClient(name = "JobsTimingApi", url = "${gmis.feign.jobs-server:http://127.0.0.1:8767}",
        path = "/gmis-jobs-server", fallback = JobsTimingApiFallback.class)
public interface JobsTimingApi {

    /**
     * 定时发送接口
     *
     * @param xxlJobInfo
     * @return
     */
    @RequestMapping(value = "/jobinfo/addTimingTask", method = RequestMethod.POST)
    R<String> addTimingTask(@RequestBody XxlJobInfo xxlJobInfo);

}

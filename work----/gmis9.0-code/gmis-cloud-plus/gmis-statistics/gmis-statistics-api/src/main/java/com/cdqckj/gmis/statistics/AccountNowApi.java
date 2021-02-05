package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/11 15:21
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/accountNow")
public interface AccountNowApi {

    @ApiOperation(value = "这个时间段开户的数据的统计")
    @PostMapping("/accountNowTypeFrom")
    R<List<StsInfoBaseVo>> accountNowTypeFrom(@RequestBody StsSearchParam stsSearchParam);

}

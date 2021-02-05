package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.StsMeterInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: lijianguo
 * @time: 2020/11/13 11:33
 * @remark: 请输入接口说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/meterNow")
public interface MeterNowApi {

    @ApiOperation(value = "统计表具的信息")
    @PostMapping("/stsInfo")
    R<StsMeterInfoVo> stsInfo(@RequestBody StsSearchParam stsSearchParam);
}

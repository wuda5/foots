package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/19 18:49
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/gasFeiNow")
public interface GasFeiNowApi {

    @ApiOperation(value = "售气收费看板-用气类型统计")
    @PostMapping("/panel/gasFeiType")
    R<List<GasFeiNowTypeVo>> gasFeiType(@RequestBody StsSearchParam stsSearchParam);
}

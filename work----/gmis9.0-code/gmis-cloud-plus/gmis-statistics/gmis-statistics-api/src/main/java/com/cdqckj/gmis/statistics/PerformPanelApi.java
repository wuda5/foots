package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.StsFinanceVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: lijianguo
 * @time: 2020/11/11 15:21
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/performancePanel")
public interface PerformPanelApi {

    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/stsFinance")
    R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam);

}

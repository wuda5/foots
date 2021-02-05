package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/11 15:21
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/meterPlanNow")
public interface MeterPlanNowApi {

    @ApiOperation(value = "用户抄表的计划统计")
    @GetMapping("/stsUserPlan")
    R<List<MeterPlanNowStsVo>> stsUserPlan(@RequestParam("uId") Long uId);

    @ApiOperation(value = "抄表业务管理-普表抄表-抄表统计")
    @PostMapping("/generalGasMeter")
    R<List<MeterPlanNowStsVo>> generalGasMeter(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "抄表业务管理- 物联网抄表-抄表统计")
    @PostMapping("/internetGasMeter")
    R<List<MeterPlanNowStsVo>> internetGasMeter(@RequestBody StsSearchParam stsSearchParam);

}

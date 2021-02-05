package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.FeiDayByPayTypeVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/11 10:22
 * @remark: 每天的费用的统计
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/feiDay")
public interface FeiDayApi {

    @ApiOperation(value = "柜台日常综合_收费的统计")
    @PostMapping("/chargeFeiByPayType")
    R<List<StsInfoBaseVo>> chargeFeiByPayType(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "柜台日常综合_退费的统计")
    @PostMapping("/refundFeiByPayType")
    R<List<StsInfoBaseVo>> refundFeiByPayType(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "售气收费看板-收费的统计")
    @PostMapping("/panel/chargeFeeByType")
    R<List<StsInfoBaseVo<String, BigDecimal>>> chargeFeeByType(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "售气收费看板-支付方式统计")
    @PostMapping("/panel/chargeFeeByPayType")
    R<List<FeiDayByPayTypeVo>> chargeFeeByPayType(@RequestBody StsSearchParam stsSearchParam);
}

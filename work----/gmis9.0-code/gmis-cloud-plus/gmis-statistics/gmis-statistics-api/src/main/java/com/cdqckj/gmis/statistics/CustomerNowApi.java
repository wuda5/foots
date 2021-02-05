package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/11 15:21
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/customerNow")
public interface CustomerNowApi {

    @ApiOperation(value = "客户档案的统计信息")
    @PostMapping("/stsCustom")
    R<CustomerNowStsVo> stsCustom(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "客户信息看板-客户分类统计")
    @PostMapping("/panel/stsCustomType")
    R<List<StsInfoBaseVo<String, Long>>> stsCustomType(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "客户信息看板-客户增量统计")
    @PostMapping("/panel/stsCustomAddType")
    R<CustomerNowTypeKindVo> stsCustomAddType(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "客户信息看板-限购统计")
    @PostMapping("/panel/stsCustomPurchaseLimit")
    R<PurchaseLimitVo> stsCustomPurchaseLimit(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "业绩看板-财务数据统计")
    @PostMapping("/panel/stsFinance")
    R<StsFinanceVo> stsFinance(@RequestBody StsSearchParam stsSearchParam);
}

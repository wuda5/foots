package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/11/11 15:21
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/invoiceDay")
public interface InvoiceDayApi {

    @ApiOperation(value = "发票的类型")
    @GetMapping("/invoiceKind")
    R<List<InvoiceDayKindVo>> invoiceKind(@RequestParam("uId") Long uId, @RequestParam("stsDay") LocalDateTime stsDay);

    @ApiOperation(value = "售气收费看板-发票统计")
    @PostMapping("/panel/panelInvoiceKind")
    R<List<InvoiceDayKindVo>> panelInvoiceKind(@RequestBody StsSearchParam stsSearchParam);

    @ApiOperation(value = "柜台日常综合-发票统计")
    @PostMapping("/invoiceTypeSts")
    R<HashMap> invoiceTypeSts(@RequestBody StsSearchParam stsSearchParam);

}

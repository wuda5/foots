package com.cdqckj.gmis.statistics;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.vo.InsureNowTypeStsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: lijianguo
 * @time: 2020/11/19 9:44
 * @remark: 请输入类说明
 */
@FeignClient(name = "${gmis.feign.statistics-server:gmis-statistics-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "sts/insureNow")
public interface InsureNowApi {

    @ApiOperation(value = "客户信息看板-保险购买分类统计")
    @PostMapping("/panel/insureNowByType")
    R<InsureNowTypeStsVo> insureNowByType(@RequestBody StsSearchParam stsSearchParam);
}

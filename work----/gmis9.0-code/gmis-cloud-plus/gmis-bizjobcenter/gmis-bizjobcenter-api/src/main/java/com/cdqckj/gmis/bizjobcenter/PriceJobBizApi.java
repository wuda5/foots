package com.cdqckj.gmis.bizjobcenter;

import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 价格方案调度接口
 */
@FeignClient(name = "${gmis.feign.bizjobcenter-server:gmis-bizjobcenter-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/priceschemejob", qualifier = "priceJobBizApi")
public interface PriceJobBizApi {
    /**
     * 判断价格方案是否生效
     */
    @PostMapping(value="/isActivate")
    void isActivatePriceScheme();

}

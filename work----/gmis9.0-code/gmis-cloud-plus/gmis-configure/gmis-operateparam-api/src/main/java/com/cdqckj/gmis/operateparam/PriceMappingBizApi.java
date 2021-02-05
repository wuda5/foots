package com.cdqckj.gmis.operateparam;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "/priceMapping")
public interface PriceMappingBizApi {

    @ApiOperation(value = "重新设置这个表的价格方案")
    @GetMapping("/restGasMeterPriceMapping")
    R<PriceScheme> restGasMeterPriceMapping(@RequestParam("gasMeterCode") String gasMeterCode, @RequestParam("priceId") Long priceId, @RequestParam("yId") Long yId);

    /**
     * 获取最新调价记录
     * @param gasCode
     * @return
     */
    @GetMapping("/getGasMeterPriceMapping")
    PriceMapping getGasMeterPriceMapping(@RequestParam(value = "gasCode") String gasCode);

    /**
     * 保存调价后的记录
     * @param priceMapping
     * @return
     */
    @PostMapping("/saveChangePrice")
    int saveChangePrice(@RequestBody PriceMapping priceMapping);
}

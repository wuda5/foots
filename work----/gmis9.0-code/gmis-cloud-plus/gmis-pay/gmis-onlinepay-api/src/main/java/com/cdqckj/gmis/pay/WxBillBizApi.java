package com.cdqckj.gmis.pay;

import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${gmis.feign.onlinepay-server:gmis-onlinepay-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/wxBill", qualifier = "wxBillBizApi")
public interface WxBillBizApi {

    /**
     * 微信下载交易账单
     * @param params
     * @return
     */
    @PostMapping(value = "/exportBill")
    Response exportBill(@RequestBody @Validated PageParams<WxBillPageDTO> params);
}

package com.cdqckj.gmis.bizjobcenter;

import com.cdqckj.gmis.bizjobcenter.hystrix.GiveReductionConfJobBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${gmis.feign.bizjobcenter-server:gmis-bizjobcenter-server}", fallback = GiveReductionConfJobBizApiFallback.class
        , path = "/piveReductionConfJobController", qualifier = "giveReductionConfJob")
public interface GiveReductionConfJobBizApi {
    @PostMapping(value="/updateStatus")
    void updateGiveReductionStatus();
}

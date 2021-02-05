package com.cdqckj.gmis.oauthapi;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.oauthapi.dto.RealNameOauthSaveDTO;
import com.cdqckj.gmis.oauthapi.entity.RealNameOauth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 实名认证
 * @author hc
 * @date 2020/10/13
 */
@FeignClient(name = "${gmis.feign.oauthapi-server:gmis-oauthapi-server}", path = "/realNameOauth",
        qualifier = "/realNameOauth", fallbackFactory = HystrixTimeoutFallbackFactory.class)
public interface RealNameOauthApi {

    /**
     * 新增
     * @param saveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<RealNameOauth> save( RealNameOauthSaveDTO saveDTO);


    @PostMapping("/query")
    public R<List<RealNameOauth>> query(@RequestBody RealNameOauth data);
}

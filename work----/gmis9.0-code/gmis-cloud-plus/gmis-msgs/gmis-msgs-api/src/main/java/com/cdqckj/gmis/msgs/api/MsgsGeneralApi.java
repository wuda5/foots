package com.cdqckj.gmis.msgs.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.msgs.api.fallback.MsgsGeneralApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * 通用API
 *
 * @author gmis
 * @date 2019/07/26
 */
@FeignClient(name = "${gmis.feign.msgs-serve/**/r:gmis-msgs-server}", fallback = MsgsGeneralApiFallback.class)
public interface MsgsGeneralApi {
    /**
     * 查询系统中常用的枚举类型等
     *
     * @return
     */
    @GetMapping("/enums")
    R<Map<String, Map<String, String>>> enums();
}

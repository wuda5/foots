package com.cdqckj.gmis.gateway.fallback;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * 响应超时熔断处理器
 *
 * @author gmis
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<R> fallback() {
        return Mono.just(R.fail(ExceptionCode.SYSTEM_TIMEOUT));
    }
}

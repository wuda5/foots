package com.cdqckj.gmis.statistics.statistics.config;

import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/27 16:19
 * @remark: 服务与服务之间传送用户的token
 */
@Log4j2
public class HeaderSetInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String tenant = BaseContextHandler.getTenant();
        BaseContextHandler.getLocalMap().put("gmisUrl",requestTemplate.feignTarget().url());
        BaseContextHandler.getLocalMap().put("gmisSN",requestTemplate.feignTarget().name());
        log.info("codenameis 4 {} {}", tenant, requestTemplate.feignTarget().url());
        requestTemplate.header(BaseContextConstants.JWT_KEY_TENANT, tenant);
    }
}

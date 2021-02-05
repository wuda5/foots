package com.cdqckj.gmis.cloud.interceptor;

import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * feign client 拦截器， 实现将 feign 调用方的 请求头封装到 被调用方的请求头
 *
 * @author gmis
 * @date 2019-07-25 11:23
 */
@Slf4j
public class FeignAddHeaderRequestInterceptor implements RequestInterceptor {

    private static final List<String> HEADER_NAME_LIST = Arrays.asList(
            BaseContextConstants.JWT_KEY_TENANT, BaseContextConstants.JWT_KEY_USER_ID,
            BaseContextConstants.JWT_KEY_ACCOUNT, BaseContextConstants.JWT_KEY_NAME, BaseContextConstants.GRAY_VERSION,
            BaseContextConstants.TRACE_ID_HEADER,
            BaseContextConstants.JWT_KEY_TENANT_NAME,
            BaseContextConstants.JWT_KEY_TENANT_ID,
            BaseContextConstants.JWT_KEY_ORG_ID,
            BaseContextConstants.JWT_KEY_ORG_NAME,
            "X-Real-IP", "x-forwarded-for"
    );

    public FeignAddHeaderRequestInterceptor() {
        super();
    }

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        BaseContextHandler.getLocalMap().put("gmisUrl",template.feignTarget().url());
        BaseContextHandler.getLocalMap().put("gmisSN",template.feignTarget().name());
        if (requestAttributes == null) {
            HEADER_NAME_LIST.forEach((headerName) -> template.header(headerName, BaseContextHandler.get(headerName)));
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            log.warn("path={}, 在FeignClient API接口未配置FeignConfiguration类， 故而无法在远程调用时获取请求头中的参数!", template.path());
            return;
        }
        String beginId = BaseContextHandler.getLocalMap().get(BaseContextConstants.CODE_CREATE_BEGIN_ID);
        if (beginId != null){
            template.header(BaseContextConstants.CODE_CREATE_BEGIN_ID, beginId);
        }
        HEADER_NAME_LIST.forEach((headerName) -> template.header(headerName, request.getHeader(headerName)));

        String currentXid = RootContext.getXID();
        if(currentXid!=null){
            template.header(RootContext.KEY_XID,currentXid);
        }
    }
}

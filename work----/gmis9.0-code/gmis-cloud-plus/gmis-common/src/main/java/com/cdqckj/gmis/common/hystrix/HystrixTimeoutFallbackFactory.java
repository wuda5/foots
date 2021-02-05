package com.cdqckj.gmis.common.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lijianguo
 * @time: 2020/10/17 9:35
 * @remark: 请输入类说明
 */
@Log4j2
@Component
public class HystrixTimeoutFallbackFactory<T> implements FallbackFactory {

    @Override
    public T create(Throwable throwable) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Throwable cause = throwable.getCause();
        String causeMsg = null;
        if (cause != null) {
            causeMsg = cause.getMessage();
        }
        String serviceName = "服务名未知";
        if (StringUtils.isNotEmpty(causeMsg)){
            String[] splitList = causeMsg.split(":");
            if (splitList.length >= 2){
                serviceName = splitList[1];
            }
        }else {
            String localName = BaseContextHandler.getLocalMap().get("gmisSN");
            if (StringUtils.isNotBlank(localName)){
                serviceName = localName;
            }
        }
        String url = null;
        String tenant = BaseContextHandler.getTenant();
        if (requestAttributes != null){
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            url = httpServletRequest.getRequestURI();
        }else {
            url = BaseContextHandler.getLocalMap().get("gmisUrl");
        }

        log.error("上游服务器关闭 租户【{}】 服务【{}】 请求地址【{}】", tenant, serviceName, url);
        return (T) R.timeout();
    }
}

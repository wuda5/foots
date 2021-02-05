package com.cdqckj.gmis.database.dynamic.processor;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.cdqckj.gmis.context.BaseContextHandler;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 从请求头或者Thread变量中获取参数
 *
 * @author gmis
 * @date 2020年03月15日11:12:54
 */
public class DsHeaderOrThreadProcessor extends DsProcessor {

    /**
     * 请求头或线程变量 前缀
     */
    private static final String HEADER_PREFIX = "#headerThread";

    @Override
    public boolean matches(String key) {
        return key.startsWith(HEADER_PREFIX);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        String ds = BaseContextHandler.get(key.substring(HEADER_PREFIX.length() + 1));
        if (ds != null && !"".equals(ds)) {
            return ds;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return null;
        }
        return request.getHeader(key.substring(HEADER_PREFIX.length() + 1));

    }

}

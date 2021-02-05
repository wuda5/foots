package com.cdqckj.gmis.security.interceptor;

import cn.hutool.core.util.URLUtil;
import com.cdqckj.gmis.context.BaseContextConstants;
import com.cdqckj.gmis.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网关：
 * 获取token，并解析，然后将所有的用户、应用信息封装到请求头
 * <p>
 * 拦截器：
 * 解析请求头数据， 将用户信息、应用信息封装到BaseContextHandler
 * 考虑请求来源是否网关（ip等）
 * <p>
 * 该拦截器要优先于系统中其他的业务拦截器
 * <p>
 *
 * @author gmis
 * @date 2019-06-20 22:22
 */
@Slf4j
public class ContextHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        //租户code设置 add by hc
        String tennent = getHeader(request, BaseContextConstants.JWT_KEY_TENANT);
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(tennent)){
            BaseContextHandler.setTenant(tennent);
        }
        if (!BaseContextHandler.getBoot()) {
            BaseContextHandler.setUserId(getHeader(request, BaseContextConstants.JWT_KEY_USER_ID));
            BaseContextHandler.setAccount(getHeader(request, BaseContextConstants.JWT_KEY_ACCOUNT));
            BaseContextHandler.setName(getHeader(request, BaseContextConstants.JWT_KEY_NAME));
            BaseContextHandler.setTenantId(getHeader(request, BaseContextConstants.JWT_KEY_TENANT_ID));
            BaseContextHandler.setTenantName(getHeader(request, BaseContextConstants.JWT_KEY_TENANT_NAME));
            BaseContextHandler.set(BaseContextConstants.CODE_CREATE_BEGIN_ID,getHeader(request, BaseContextConstants.CODE_CREATE_BEGIN_ID));
            String langType=getHeader(request, BaseContextConstants.LANG_TYPE);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(langType)){
                BaseContextHandler.setLang(Integer.parseInt(langType));
            }else{
                BaseContextHandler.setLang(1);
            }

            String orgId=getHeader(request, BaseContextConstants.JWT_KEY_ORG_ID);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(orgId)){
                BaseContextHandler.setOrgId(Long.parseLong(getHeader(request, BaseContextConstants.JWT_KEY_ORG_ID)));
            }
            BaseContextHandler.setOrgName(getHeader(request, BaseContextConstants.JWT_KEY_ORG_NAME));

            //设置appId hc
            String appId = getHeader(request,BaseContextConstants.APP_ID);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(appId)) {
                BaseContextHandler.setAppId(appId);
            }
        }
        // cloud
        BaseContextHandler.setGrayVersion(getHeader(request, BaseContextConstants.GRAY_VERSION));
        return super.preHandle(request, response, handler);
    }

    private String getHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        return URLUtil.decode(value);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }

}

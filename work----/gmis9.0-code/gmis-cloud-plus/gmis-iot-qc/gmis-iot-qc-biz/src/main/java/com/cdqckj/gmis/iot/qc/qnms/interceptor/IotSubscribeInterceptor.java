package com.cdqckj.gmis.iot.qc.qnms.interceptor;

import cn.hutool.core.map.MapUtil;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.qnms.annotation.QnmsIotSubscribe;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISHeaderConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.RedisConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotSpringUtils;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import com.cdqckj.gmis.utils.SpringUtils;
import com.cdqckj.gmis.utils.StrPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 远传系统订阅验证拦截器
 * @author: 秦川物联网科技
 * @date: 2020/10/12  10:20
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class IotSubscribeInterceptor extends HandlerInterceptorAdapter {
    private final RedisService redisService = IotSpringUtils.getBean(RedisService.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler ;
        Method method = handlerMethod.getMethod();
        //所有带@QnmsIotSubscribe注解的方法均要拦截
        if (method.getAnnotation(QnmsIotSubscribe.class) != null) {
            String tenant = BaseContextHandler.getTenant();
            String domainId = request.getHeader(GMISHeaderConstant.DOMAIN_ID);
            String licence =  null;
            if(!redisService.hasKey(RedisConstant.IOT_GMIS+ StrPool.COLON+tenant+StrPool.COLON
                    +RedisConstant.FACTORY_CODE+StrPool.COLON+domainId)){
                IotSubscribe isb = IotSpringUtils.getBean(IotSubscribeService.class).queryByTenant(tenant,domainId);
                domainId = isb.getDomainId();
                licence = isb.getLicence();
                if(isb.getDomainId()==null){return false;}
                redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+
                        RedisConstant.DOMAIN_ID+StrPool.COLON+domainId,isb.getDomainId());
                redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                        RedisConstant.LICENCE+StrPool.COLON+domainId,isb.getLicence());
                redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                        RedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+domainId,isb);
                redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON
                        +RedisConstant.FACTORY_CODE+StrPool.COLON+domainId,isb.getFactoryCode());
            }else{
                licence = (String)redisService.get((RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                        RedisConstant.LICENCE+StrPool.COLON+domainId));
            }
            String subsrcibe = (String)redisService.get(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                    RedisConstant.IOT_SUBSRCIBE+StrPool.COLON+domainId);
            //如果订阅存在直接放行
            if (!StringUtil.isNullOrBlank(subsrcibe)&&"1".equals(subsrcibe.trim())) {
                return true ;
            }
            //如果订阅不存在则先订阅在放行
            else {
                IotR r = SpringUtils.getBean(IotSubscribeService.class).isSubscribe(domainId);
                //如果没有订阅，则先订阅
                if(r != null && MapUtil.getInt(r, IotRConstant.CODE) != 0){
                    SpringUtils.getBean(IotSubscribeService.class).subscribe(domainId);
                }
                redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                        RedisConstant.IOT_SUBSRCIBE+StrPool.COLON+domainId,"1");
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            if(modelAndView != null){
                int i=0;
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}

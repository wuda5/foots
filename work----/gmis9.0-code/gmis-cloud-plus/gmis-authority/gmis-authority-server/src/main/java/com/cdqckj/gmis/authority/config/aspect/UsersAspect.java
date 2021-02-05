package com.cdqckj.gmis.authority.config.aspect;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.constant.CacheKey;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class UsersAspect {

    @Autowired
    private CacheChannel channel;

    @Pointcut("execution(public * com.cdqckj.gmis.authority.controller.auth.UserController.updateUser(..))")
    public void pointcut(){}

    //@After("pointcut()")
    @AfterReturning(value = "execution(public * com.cdqckj.gmis.authority.controller.auth.UserController.updateUser(..))",returning = "userR")
    public void afterAdvice(JoinPoint joinPoint, R<User> userR){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long userId = userR.getData().getId();
        //CacheObject userCacheObject = channel.get(CacheKey.TOKEN_LIST, tokenKey);
        String newToken = "";//request.getHeader("token");
        Boolean bool = userR.getData().getStatus();
        if(!bool){//被禁用
            String tokenKey = CacheKey.buildKey(newToken);
            CacheObject tokenCache = channel.get(CacheKey.TOKEN_USER_ID, tokenKey);
            channel.set(CacheKey.TOKEN_USER_ID, tokenKey,"forbidden");
        }
    }
}

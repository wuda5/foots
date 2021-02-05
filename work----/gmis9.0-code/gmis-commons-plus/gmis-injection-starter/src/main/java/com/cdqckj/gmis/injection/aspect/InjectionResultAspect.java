package com.cdqckj.gmis.injection.aspect;

import com.cdqckj.gmis.injection.annonation.InjectionResult;
import com.cdqckj.gmis.injection.core.InjectionCore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * InjectionResult 注解的 AOP 工具
 *
 * @author gmis
 * @create 2020年01月19日09:27:41
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class InjectionResultAspect {
    private InjectionCore injectionCore;


    @Pointcut("@annotation(com.cdqckj.gmis.injection.annonation.InjectionResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint pjp, InjectionResult anno) throws Throwable {
        try {
            return injectionCore.injection(pjp, anno);
        } catch (Exception e) {
            log.error("AOP拦截@RemoteResult出错", e);
            return pjp.proceed();
        }
    }
}

package com.cdqckj.gmis.common.domain.tenant;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author: lijianguo
 * @time: 2020/10/30 9:26
 * @remark: 对切换租户的注解的拦截
 */
@Log4j2
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TenantRestAspect {

    /** 多租户处理的功能**/
    private MulTenantProcess mulTenantProcess;

    public TenantRestAspect(MulTenantProcess mulTenantProcess) {
        this.mulTenantProcess = mulTenantProcess;
    }

    @Pointcut(value = "@annotation(com.cdqckj.gmis.common.domain.tenant.TransactionalTenantRest)")
    public void methodPointcut(){
        log.info("切换用户的注解");
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 开始执行
     */
    @Before("methodPointcut()&&@annotation(tenantRest)")
    public void before(JoinPoint joinPoint, TransactionalTenantRest tenantRest) throws NoSuchMethodException {

        // 得到这个注解的类信息和方法的信息
        Class targetCls = joinPoint.getTarget().getClass();
        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        Method targetMethod = targetCls.getDeclaredMethod(ms.getName(),ms.getParameterTypes());

        Annotation transactional = targetMethod.getAnnotation(Transactional.class);
        Annotation globalTransactional = targetMethod.getAnnotation(GlobalTransactional.class);
        int type = tenantRest.type();
        if (transactional != null){
            throw new RuntimeException("@Transactional 不能同时使用");
        }
        if (type == 2){
            if (globalTransactional == null){
                throw new RuntimeException("@GlobalTransactional 需要同时添加");
            }
        }else {
            if (globalTransactional != null){
                throw new RuntimeException("@GlobalTransactional 请删除");
            }
        }
        TxInfoTypeThreadLocal.setCurrentTxInfo(tenantRest.type(), tenantRest.exception());
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 正常结束
     */
    @AfterReturning("methodPointcut()&&@annotation(transactionalTenantRest)")
    public void after(JoinPoint joinPoint, TransactionalTenantRest transactionalTenantRest) {
        TxInfoTypeThreadLocal.clearTxInfo();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 异常结束
     */
    @AfterThrowing("methodPointcut()&&@annotation(transactionalTenantRest)")
    public void afterThrowing(JoinPoint joinPoint, TransactionalTenantRest transactionalTenantRest) {
        TxInfoTypeThreadLocal.clearTxInfo();
    }
}

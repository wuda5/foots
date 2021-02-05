package com.cdqckj.gmis.common.domain.code;

import com.cdqckj.gmis.common.domain.code.clear.*;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.lock.DistributedLock;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: lijianguo
 * @time: 2020/12/07 18:48
 * @remark: code不丢失的切面--一定要在第一个进入的controller上面使用
 */
@Log4j2
@Aspect
@Component
public class CodeNotLostAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DistributedLock redisDistributedLock;

    @Pointcut(value = "@annotation(com.cdqckj.gmis.common.domain.code.CodeNotLost)")
    public void methodPointcut(){
        log.info("服务之间生成不丢失的code的注解");
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 开始执行
     */
    @Before("methodPointcut()")
    public void before(JoinPoint joinPoint){
        String beginId = BizCodeBaseUtil.initNotLossCodeBeginId();
        log.info("beginId is {}", beginId);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 正常结束不清空，因为beginId会自动过期 local会被下一个新生成的覆盖
     */
    @AfterReturning("methodPointcut()")
    public void after(JoinPoint joinPoint) {
//        String beginId = BizCodeBaseUtil.getNotLossCodeBeginId();
//        if (beginId != null){
//            String key = CacheKeyUtil.createTenantKey(RedisCommonKey.CODE_NOT_LOST_CODE_KEY, beginId);
//            redisTemplate.delete(key);
//            BizCodeBaseUtil.clearBeginId();
//        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/2 13:28
     * @remark 异常结束
     */
    @AfterThrowing(pointcut = "methodPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {

        String beginId = BizCodeBaseUtil.getNotLossCodeBeginId();
        if (StringUtils.isBlank(beginId)){
            return;
        }
        CodeClear codeClear;
        if (ex instanceof ClearNowException){
            codeClear = new CodeClearNow(redisTemplate, redisDistributedLock);
        }else if(ex instanceof ClearNoneException) {
            codeClear = new CodeClearNone();
        }else {
            codeClear = new CodeClearAll(redisTemplate);
        }
        codeClear.clearCache(beginId);
        BizCodeBaseUtil.clearBeginId();
    }
}

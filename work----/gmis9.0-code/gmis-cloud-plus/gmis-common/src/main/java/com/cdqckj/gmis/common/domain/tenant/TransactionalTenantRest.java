package com.cdqckj.gmis.common.domain.tenant;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijianguo
 * @time: 2020/10/30 9:22
 * @remark: 在添加本地事务的注解的时候添加这个注解
 * 可以重新切换租户----尽量用在controller上面和@Transactional注解一起使用
 * 《就是解决使用@Transactional注解不能再函数内部切换租户的问题 当然还要用MulTenantProcess来处理自己的业务》
 *  type 0没有事务,只切换租户可以不用添加这个注解 1本地事务 2分布式事务
 *  exception 0分布式事务自动回滚 1 分布式事务手动回滚
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping
public @interface TransactionalTenantRest {

    @ApiModelProperty(value = "0没有事务,只切换租户可以不用添加这个注解 1本地事务 2分布式事务")
    int type() default 0;

    @ApiModelProperty(value = "0事务自动回滚 1事务手动回滚")
    int exception() default 0;
}

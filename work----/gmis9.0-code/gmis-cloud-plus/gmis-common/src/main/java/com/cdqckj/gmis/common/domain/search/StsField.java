package com.cdqckj.gmis.common.domain.search;


import java.lang.annotation.*;

/**
 * @auth lijianguo
 * @date 2020/11/17 10:51
 * @remark 统计的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StsField {

    /** 数据库的字段的名字 **/
    String value() default "";

    /** 要进行的处理操作 **/
    String op() default "";
}

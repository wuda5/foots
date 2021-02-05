package com.cdqckj.gmis.log.annotation;

import java.lang.annotation.*;

/**
 * 自定义导出注解
 *
 * @author gmis
 * @date 2019/2/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelSelf {
    /**
     * 描述
     *
     * @return {String}
     */
    String name() default "";

    /**
     * 枚举值数组
     * @return
     */
    String[]  value() default {};

    /**
     * 枚举类
     * @return
     */
    Class clazz() default String.class;
}

package com.cdqckj.gmis.common.domain.code;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: lijianguo
 * @time: 2020/12/07 18:44
 * @remark: 生成的code不丢失--在controller上面注释以后可以保证code不丢失，连续产生
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeNotLost {
}

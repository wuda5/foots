package com.cdqckj.gmis.scale.annotation;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 是否保留精度全局统一处理注解
 * @author: 秦川物联网科技
 * @date: 2021/2/1  11:20
 * @author: yjb
 * @Copyright: 2021 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldNoScale {
}

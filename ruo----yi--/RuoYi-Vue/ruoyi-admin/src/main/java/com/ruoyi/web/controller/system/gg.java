package com.ruoyi.web.controller.system;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface gg {
    /**
     * @return the Spring-EL expression to be evaluated before invoking the protected
     * method
     */
    String value();
}

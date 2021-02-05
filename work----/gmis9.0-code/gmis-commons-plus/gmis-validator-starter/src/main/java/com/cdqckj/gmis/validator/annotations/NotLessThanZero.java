package com.cdqckj.gmis.validator.annotations;

import com.cdqckj.gmis.validator.constraintvalidators.NotLessThanZeroValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 大于零验证注解
 * @Auther hc
 */
@Constraint(validatedBy = {NotLessThanZeroValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotLessThanZero {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

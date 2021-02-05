package com.cdqckj.gmis.validator.annotations;

import com.cdqckj.gmis.validator.constraintvalidators.NotEmptyConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * RemoteData.class类型验证注解
 * @author hc
 * @date 2020/9/18
 */
@Documented
@Constraint(validatedBy = {NotEmptyConstraintValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RemoteNotEmpty.List.class)
@Deprecated
public @interface RemoteNotEmpty {
    String message() default "{javax.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        RemoteNotEmpty[] value();
    }
}

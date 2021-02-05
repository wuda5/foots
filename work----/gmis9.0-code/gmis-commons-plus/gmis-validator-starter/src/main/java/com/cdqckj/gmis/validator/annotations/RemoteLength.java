package com.cdqckj.gmis.validator.annotations;


import com.cdqckj.gmis.validator.constraintvalidators.LengthConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.cdqckj.gmis.validator.annotations.RemoteLength.List;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * RemoteData.class类型验证注解
 * @author hc
 * @date 2020/9/18
 */
@Documented
@Constraint(validatedBy = {LengthConstraintValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = List.class)
@Deprecated
public @interface RemoteLength {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "{org.hibernate.validator.constraints.Length.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
       RemoteLength[] value();
    }

}

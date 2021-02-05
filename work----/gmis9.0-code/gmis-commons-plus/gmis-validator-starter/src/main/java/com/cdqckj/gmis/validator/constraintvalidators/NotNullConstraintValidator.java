package com.cdqckj.gmis.validator.constraintvalidators;


import com.cdqckj.gmis.base.validation.IValidatable;
import com.cdqckj.gmis.validator.annotations.RemoteNotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义一个验证 NotNull 的校验器。自定义类需要实现IValidatable接口
 *
 * @author gmis
 * @updater hc
 * @date 2020年02月02日20:59:21
 */
public class NotNullConstraintValidator implements ConstraintValidator<RemoteNotNull, IValidatable> {

    private NotNullValidator notNullValidator = new NotNullValidator();

//    @Override
//    public void initialize(RemoteNotNull parameters) {
//        notNullValidator.initialize(parameters);
//    }

    @Override
    public boolean isValid(IValidatable value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.value() != null;
    }
}

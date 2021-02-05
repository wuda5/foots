package com.cdqckj.gmis.validator.constraintvalidators;


import com.cdqckj.gmis.base.validation.IValidatable;
import com.cdqckj.gmis.validator.annotations.RemoteNotEmpty;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCharSequence;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义一个验证 NotEmpty 的校验器。自定义类需要实现IValidatable接口
 *
 * @author gmis
 * @updater hc
 * @date 2020年02月02日20:59:21
 */
public class NotEmptyConstraintValidator implements ConstraintValidator<RemoteNotEmpty, IValidatable> {

    private NotEmptyValidatorForCharSequence notEmptyValidator = new NotEmptyValidatorForCharSequence();

//    @Override
//    public void initialize(RemoteNotEmpty parameters) {
//        notEmptyValidator.initialize(parameters);
//    }

    @Override
    public boolean isValid(IValidatable value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.value() != null && !"".equals(value.value());
    }
}

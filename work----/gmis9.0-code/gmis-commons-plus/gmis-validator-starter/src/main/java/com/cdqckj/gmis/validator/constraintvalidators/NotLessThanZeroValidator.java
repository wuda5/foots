package com.cdqckj.gmis.validator.constraintvalidators;

import com.cdqckj.gmis.validator.annotations.NotLessThanZero;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 大于零验证器
 * @auther hc
 */
public class NotLessThanZeroValidator implements ConstraintValidator<NotLessThanZero, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        if (value instanceof Number) {
            if (((Number) value).intValue() >= 0) {
                return true;
            }
        }
        return false;
    }
}

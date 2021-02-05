package com.cdqckj.gmis.validator.config;

import com.cdqckj.gmis.validator.annotations.RemoteLength;
import com.cdqckj.gmis.validator.annotations.RemoteNotEmpty;
import com.cdqckj.gmis.validator.annotations.RemoteNotNull;
import com.cdqckj.gmis.validator.constraintvalidators.LengthConstraintValidator;
import com.cdqckj.gmis.validator.constraintvalidators.NotEmptyConstraintValidator;
import com.cdqckj.gmis.validator.constraintvalidators.NotNullConstraintValidator;
import com.cdqckj.gmis.validator.controller.FormValidatorController;
import com.cdqckj.gmis.validator.extract.DefaultConstraintExtractImpl;
import com.cdqckj.gmis.validator.extract.IConstraintExtract;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 验证器配置
 *
 * @author gmis
 * @updater hc
 * @date 2019/07/14
 */
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = warp(Validation.byProvider(HibernateValidator.class)
                .configure()
                //快速失败返回模式
                .addProperty("hibernate.validator.fail_fast", "true"))
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    private Configuration<HibernateValidatorConfiguration> warp(HibernateValidatorConfiguration configuration) {
        addValidatorMapping(configuration);
        //其他操作
        return configuration;
    }

    private void addValidatorMapping(HibernateValidatorConfiguration configuration) {
        //增加一个我们自定义的校验处理器与length的映射
        ConstraintMapping mapping = new DefaultConstraintMapping();
        ConstraintDefinitionContext<RemoteLength> length = mapping.constraintDefinition(RemoteLength.class);
        length.includeExistingValidators(true);
        length.validatedBy(LengthConstraintValidator.class);

        ConstraintDefinitionContext<RemoteNotNull> notNull = mapping.constraintDefinition(RemoteNotNull.class);
        notNull.includeExistingValidators(true);
        notNull.validatedBy(NotNullConstraintValidator.class);

        ConstraintDefinitionContext<RemoteNotEmpty> notEmpty = mapping.constraintDefinition(RemoteNotEmpty.class);
        notEmpty.includeExistingValidators(true);
        notEmpty.validatedBy(NotEmptyConstraintValidator.class);

        configuration.addMapping(mapping);
    }

    /**
     * Method:  开启快速返回
     * Description:
     * 如果参数校验有异常，直接抛异常，不会进入到 controller，使用全局异常拦截进行拦截
     * Author: liu kai
     * Date: 2018/7/12 17:33
     *
     * @param
     * @return org.springframework.validation.beanvalidation.MethodValidationPostProcessor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        /**设置validator模式为快速失败返回*/
        postProcessor.setValidator(validator);
        return postProcessor;
    }

    @Bean
    public IConstraintExtract constraintExtract(Validator validator) {
        return new DefaultConstraintExtractImpl(validator);
    }

    @Bean
    public FormValidatorController getFormValidatorController(IConstraintExtract constraintExtract, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        return new FormValidatorController(constraintExtract, requestMappingHandlerMapping);
    }

}

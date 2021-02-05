package com.cdqckj.gmis.config;


import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.cdqckj.gmis.common.domain.tenant.MulTenantData;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcess;
import com.cdqckj.gmis.common.domain.tenant.MulTenantProcessDefault;
import com.cdqckj.gmis.common.domain.tenant.TenantRestAspect;
import com.cdqckj.gmis.file.properties.FileServerProperties;
import com.cdqckj.gmis.file.storage.TencentCosAutoConfigure;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.cdqckj.gmis.converter.String2DateConverter;
import com.cdqckj.gmis.utils.CodeGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * @author gmis
 * @createTime 2017-12-15 14:42
 */
@Configuration
public class JobsConfiguration {

    /**
     * json 类型参数 序列化问题
     * Long -> string
     * date -> string
     *
     * @param builder
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .timeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                .build();
        //忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //日期格式
        SimpleDateFormat outputFormat = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        objectMapper.setDateFormat(outputFormat);
        SimpleModule simpleModule = new SimpleModule();
        /**
         *  将Long,BigInteger序列化的时候,转化为String
         */
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

    /**
     * date 类型参数 格式问题
     *
     * @return
     */
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new String2DateConverter();
    }

    /**
     * 长度都是8位的字符串
     *
     * @param machineCode
     * @return
     */
    @Bean("codeGenerate")
    public CodeGenerate codeGenerate(@Value("${id-generator.machine-code:1}") Long machineCode) {
        return new CodeGenerate(machineCode.intValue());
    }

    /**
     * @auth lijianguo
     * @date 2020/10/20 11:25
     * @remark 注入
     */
    @Bean
    public MulTenantProcess initMulTenantProcess(@Autowired(required=false) MulTenantData mulTenantData,
                                                 @Autowired(required=false) RedisTemplate redisTemplate,
                                                 @Autowired(required=false) TransactionTemplate transactionTemplate,
                                                 @Autowired(required=false) DynamicRoutingDataSource dataSource){
        return new MulTenantProcessDefault(mulTenantData, redisTemplate, transactionTemplate, dataSource);
    }

    @Bean
    public TenantRestAspect initTenantRestInterceptor(MulTenantProcess mulTenantProcess){
        return new TenantRestAspect(mulTenantProcess);
    }

}

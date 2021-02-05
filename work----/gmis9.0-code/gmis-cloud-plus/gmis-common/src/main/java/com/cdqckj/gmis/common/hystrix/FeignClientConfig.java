package com.cdqckj.gmis.common.hystrix;

import feign.Feign;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignClientConfig {

    @Bean
    public OkHttpInterceptorGmis okHttpInterceptor(){
        return new OkHttpInterceptorGmis();
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient(OkHttpClientFactory okHttpClientFactory) {
        OkHttpClient okHttpClient = okHttpClientFactory.createBuilder(false)
                .followRedirects(false)
                .addInterceptor(okHttpInterceptor())
                .build();
        return okHttpClient;
    }
}

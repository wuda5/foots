package com.cdqckj.gmis.common.hystrix;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * @author: lijianguo
 * @time: 2020/10/17 14:53
 * @remark: 上游服务器的异常的说明和处理
 */
@Log4j2
@Component
public class FeignErrorInterceptor implements ErrorDecoder  {

    @Override
    public Exception decode(String s, Response response) {

        try {
            String body = Util.toString(response.body().asReader(Charset.forName("utf8")));
            FeignInterceptorResult result = JSON.parseObject(body, FeignInterceptorResult.class);
            Request request = response.request();
            String serverName = request.requestTemplate().feignTarget().name();
            String url = URLDecoder.decode(request.url(), "GBK");
            String bodyParam = null;
            if (request.body() != null) {
                bodyParam = new String(request.body());
            }
            log.error("上游服务器异常:【{}】 type:【{}】 请求地址:【{}】 请求参数:【{}】 api:【{}】",
                    serverName, request.httpMethod(), url, bodyParam, s);
            if (body.length() < 4000) {
                log.error("上游服务器异常详情: 【{}】", body);
            }else {
                log.error("上游服务器异常详情: body超出4000限制");
            }
            if (StringUtils.isNotBlank(result.getMessage())){
                return new HystrixBadRequestException(result.getMessage());
            }else if (StringUtils.isNotBlank(result.getMsg())){
                return new HystrixBadRequestException(result.getMsg());
            }else {
                return new HystrixBadRequestException("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HystrixBadRequestException("");
    }
}

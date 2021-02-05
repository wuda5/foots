package com.cdqckj.gmis.common.hystrix;

import feign.Response;
import feign.Util;
import feign.codec.StringDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author: lijianguo
 * @time: 2020/10/19 11:51
 * @remark: 这里没有使用了，无用的类
 */
@Log4j2
public class FeignInterceptor extends StringDecoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {

        String body = Util.toString(response.body().asReader(Charset.forName("utf8")));
        Integer codeIndex = body.indexOf("data");
        String codeStr = body.substring(8, codeIndex - 2);
        if (!"0".equals(codeStr)){
            throw new RuntimeException("这里有错误的处理，还在弄");
        }
        if (response.status() == 404 || response.status() == 204)
            return Util.emptyValueOf(type);
        if (response.body() == null)
            return null;
        if (byte[].class.equals(type)) {
            return Util.toByteArray(response.body().asInputStream());
        }
        return super.decode(response, type);
    }

}

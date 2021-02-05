package com.cdqckj.gmis.common.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * @author: lijianguo
 * @time: 2020/10/19 15:49
 * @remark: 请输入类说明
 */
@Log4j2
public class OkHttpInterceptorGmis implements okhttp3.Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if(!HttpHeaders.hasBody(response)){

        } else if (bodyEncoded(response.headers())) {

        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                return response;
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                Integer codeIndex = result.indexOf("{\"code\":");
                if(!result.contains("resultData")&&codeIndex!=-1){
                    String codeStr = JSONObject.parseObject(result).getString("code");
                    //String codeStr = result.substring(8, codeIndex - 2);
                    if (!"0".equals(codeStr)){
                        ReflectionUtil.setFieldValueByFieldName(response,"code",500);
                    }
                }
            }
        }
        return response;
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }
}

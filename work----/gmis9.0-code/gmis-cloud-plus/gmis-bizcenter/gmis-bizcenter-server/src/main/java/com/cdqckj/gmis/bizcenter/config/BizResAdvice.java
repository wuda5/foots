package com.cdqckj.gmis.bizcenter.config;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.scale.annotation.FieldNoScale;
import com.cdqckj.gmis.utils.StrHelper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletRequest;
@ControllerAdvice
public class BizResAdvice  implements ResponseBodyAdvice {
        /**
     * supports方法是来给定条件判断是否该调用beforeBodyWrite，MethodParameter里面有各种数据，
     **/
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if(methodParameter.hasMethodAnnotation(FieldNoScale.class)){
            return false;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
//                Enumeration<String> names=request.getHeaderNames();
//                while (names.hasMoreElements()) {
//                     System.out.println( names.nextElement());
//                }
                if(request.getHeader("field_no_scale")!=null && "true".equals(request.getHeader("field_no_scale"))){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 响应结果拦截根据业务去封装返回体
     **/
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        methodParameter.getMethodAnnotation();
        if(o instanceof R){
            R r=(R)o;
            if(r.getIsSuccess()) {
                StrHelper.convertBigDecimalDigits(r.getData(), 0);
            }
        }
        return o;
    }
}

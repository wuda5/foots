package com.cdqckj.gmis.boot.handler;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.utils.I18nUtil;
import com.cdqckj.gmis.utils.SpringUtils;
import com.cdqckj.gmis.utils.StrPool;
import com.cdqckj.gmis.validator.annotations.NotLessThanZero;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author gmis
 * @createTime 2017-12-13 17:04
 */
@Slf4j
public abstract class DefaultGlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public R<String> bizException(BizException ex, HttpServletRequest request) {
        log.error("BizException:", ex);
        return R.result(ex.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    /**
     * @auth lijianguo
     * @date 2020/10/20 8:32
     * @remark 上游服务器请求超时
     */
    @ExceptionHandler(HystrixRuntimeException.class)
    public R<String> hystrixRuntimeException(HystrixRuntimeException ex, HttpServletRequest request) {
        String msg = HystrixExceptionProcess.hystrixRuntimeExceptionProcess(ex);
        R r = R.fail(msg);
        r.setPath(request.getRequestURI());
        return r;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/17 16:02
     * @remark 上游服务器异常
     */
    @ExceptionHandler(HystrixBadRequestException.class)
    public R<String> feignRuntimeException(HystrixBadRequestException ex, HttpServletRequest request) {
        String msg = HystrixExceptionProcess.hystrixBadRequestExceptionProcess(ex);
        R r = R.fail(msg);
        r.setPath(request.getRequestURI());
        return r;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R httpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("HttpMessageNotReadableException:", ex);
        String message = ex.getMessage();
        if (StrUtil.containsAny(message, "Could not read document:")) {
            String msg = String.format("无法正确的解析json类型的参数：%s", StrUtil.subBetween(message, "Could not read document:", " at "));
            return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg).setPath(request.getRequestURI());
        }
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(BindException.class)
    public R bindException(BindException ex, HttpServletRequest request) {
        log.error("BindException:", ex);
        try {
            String msgs = ex.getBindingResult().getFieldError().getDefaultMessage();
            if (StrUtil.isNotEmpty(msgs)) {
                return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msgs).setPath(request.getRequestURI());
            }
        } catch (Exception ee) {
        }
        StringBuilder msg = new StringBuilder();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        fieldErrors.forEach((oe) ->
                msg.append("参数:[").append(oe.getObjectName())
                        .append(".").append(oe.getField())
                        .append("]的传入值:[").append(oe.getRejectedValue()).append("]与预期的字段类型不匹配.")
        );
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.error("MethodArgumentTypeMismatchException:", ex);
        MethodArgumentTypeMismatchException eee = (MethodArgumentTypeMismatchException) ex;
        StringBuilder msg = new StringBuilder("参数：[").append(eee.getName())
                .append("]的传入值：[").append(eee.getValue())
                .append("]与预期的字段类型：[").append(eee.getRequiredType().getName()).append("]不匹配");
        return R.result(ExceptionCode.PARAM_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalStateException.class)
    public R illegalStateException(IllegalStateException ex, HttpServletRequest request) {
        log.error("IllegalStateException:", ex);
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, ExceptionCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.error("MissingServletRequestParameterException:", ex);
        StringBuilder msg = new StringBuilder();
        msg.append("缺少必须的[").append(ex.getParameterType()).append("]类型的参数[").append(ex.getParameterName()).append("]");
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public R nullPointerException(NullPointerException ex, HttpServletRequest request) {
        log.error("NullPointerException:", ex);
        return R.result(ExceptionCode.NULL_POINT_EX.getCode(), StrPool.EMPTY, ExceptionCode.NULL_POINT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public R illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("IllegalArgumentException:", ex);
        return R.result(ExceptionCode.ILLEGALA_ARGUMENT_EX.getCode(), StrPool.EMPTY, ExceptionCode.ILLEGALA_ARGUMENT_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        log.error("HttpMediaTypeNotSupportedException:", ex);
        MediaType contentType = ex.getContentType();
        if (contentType != null) {
            StringBuilder msg = new StringBuilder();
            msg.append("请求类型(Content-Type)[").append(contentType.toString()).append("] 与实际接口的请求类型不匹配");
            return R.result(ExceptionCode.MEDIA_TYPE_EX.getCode(), StrPool.EMPTY, msg.toString()).setPath(request.getRequestURI());
        }
        return R.result(ExceptionCode.MEDIA_TYPE_EX.getCode(), StrPool.EMPTY, "无效的Content-Type类型").setPath(request.getRequestURI());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public R missingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request) {
        log.error("MissingServletRequestPartException:", ex);
        return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(ServletException.class)
    public R servletException(ServletException ex, HttpServletRequest request) {
        log.error("ServletException:", ex);
        String msg = "UT010016: Not a multi part request";
        if (msg.equalsIgnoreCase(ex.getMessage())) {
            return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg());
        }
        return R.result(ExceptionCode.SYSTEM_BUSY.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MultipartException.class)
    public R multipartException(MultipartException ex, HttpServletRequest request) {
        log.error("MultipartException:", ex);
        return R.result(ExceptionCode.REQUIRED_FILE_PARAM_EX.getCode(), StrPool.EMPTY, ExceptionCode.REQUIRED_FILE_PARAM_EX.getMsg()).setPath(request.getRequestURI());
    }

    /**
     * jsr 规范中的验证异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R<String> constraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("ConstraintViolationException:", ex);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return R.result(ExceptionCode.BASE_VALID_PARAM.getCode(), StrPool.EMPTY, message).setPath(request.getRequestURI());
    }

    /**
     * spring 封装的参数验证异常， 在conttoller中没有写result参数时，会进入
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("MethodArgumentNotValidException:", ex);
        BeanPropertyBindingResult result= (BeanPropertyBindingResult)ex.getBindingResult();
        Object target=result.getTarget();
        Class t=target.getClass();
        List<FieldError> errors=result.getFieldErrors();
        String fieldName;
        Field field=null;
        String enName="";
        String chName="";
        StringBuilder errorMsgBuilder=new StringBuilder();
        I18nUtil i18nUtil=(I18nUtil)SpringUtils.getBean("i18nUtil");
        try {
            for (FieldError error : errors) {
                fieldName = error.getField();
                String errCode=error.getCode();
                String[] linkNames=fieldName.split("\\.");
                Object target1=target;
                Class t1=t;
                for (int i=0;i<linkNames.length;i++) {
                    if(i==linkNames.length-1){
                        field = t1.getDeclaredField(linkNames[i]);
                        enName=linkNames[i];
                    }else{
                        field=t1.getDeclaredField(linkNames[i]);
                        if(!field.isAccessible()){
                            field.setAccessible(true);
                        }
                        target1=field.get(target1);
                        t1=target1.getClass();
                    }
                }
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                Annotation[] annotations= field.getAnnotations();
                ApiModelProperty ano= field.getAnnotation(ApiModelProperty.class);
                if(ano!=null){
                    chName=ano.value();
                }
                errorMsgBuilder=errorMsgBuilder.append(I18nUtil.getMsg(chName,enName))
                        .append(I18nUtil.getMsg("：",":"))
                        .append(getValidMsg(annotations,errCode,i18nUtil))
                        .append(I18nUtil.getMsg("；",";"));
                //如果只返回一个字段错误直接break,如果返回所有字段错误，等循环完成。
            }
        }catch (Exception e){
            log.error("",e);
            errorMsgBuilder=errorMsgBuilder.append(result.getFieldError().getDefaultMessage());
        }

        return R.result(ExceptionCode.BASE_VALID_PARAM.getCode(), StrPool.EMPTY,
                errorMsgBuilder.toString()).setPath(request.getRequestURI());
    }

    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> otherExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("Exception:", ex);
        if (ex.getCause() instanceof BizException) {
            return this.bizException((BizException) ex.getCause(), request);
        }
        if (ex instanceof RuntimeException){
            return R.result(ExceptionCode.RUNTIMEEXCEPTION.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
        }
        return R.result(ExceptionCode.SYSTEM_BUSY.getCode(), StrPool.EMPTY, ExceptionCode.SYSTEM_BUSY.getMsg()).setPath(request.getRequestURI());
    }

    /**
     * 返回状态码:405
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException:", ex);
        return R.result(ExceptionCode.METHOD_NOT_ALLOWED.getCode(), StrPool.EMPTY, ExceptionCode.METHOD_NOT_ALLOWED.getMsg()).setPath(request.getRequestURI());
    }


    @ExceptionHandler(PersistenceException.class)
    public R<String> persistenceException(PersistenceException ex, HttpServletRequest request) {
        log.error("PersistenceException:", ex);
        if (ex.getCause() instanceof BizException) {
            BizException cause = (BizException) ex.getCause();
            return R.result(cause.getCode(), StrPool.EMPTY, cause.getMessage());
        }
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(MyBatisSystemException.class)
    public R<String> myBatisSystemException(MyBatisSystemException ex, HttpServletRequest request) {
        log.error("PersistenceException:", ex);
        if (ex.getCause() instanceof PersistenceException) {
            return this.persistenceException((PersistenceException) ex.getCause(), request);
        }
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(SQLException.class)
    public R sqlException(SQLException ex, HttpServletRequest request) {
        log.error("SQLException:", ex);
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ex.getMessage()).setPath(request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("DataIntegrityViolationException:", ex);
        return R.result(ExceptionCode.SQL_EX.getCode(), StrPool.EMPTY, ExceptionCode.SQL_EX.getMsg()).setPath(request.getRequestURI());
    }

    private String getValidMsg(Annotation[] annotations,String errorCode,I18nUtil i18nUtil){
        if(annotations==null) return "";
        String msgTemp;
        String result="";
        for (Annotation annotation : annotations) {
            msgTemp=null;
            if(annotation instanceof Length && "Length".equals(errorCode)){
                Length vobj=(Length)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_LENGTH)
                        ,vobj.min()<0?0:vobj.min(),vobj.max()>10000?10000:vobj.max());
            }else if(annotation instanceof Null && "Null".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NULL);
            }else if(annotation instanceof NotNull && "NotNull".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NOTNULL);
            }else if(annotation instanceof AssertTrue && "AssertTrue".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_ASSERTTRUE);
            }else if(annotation instanceof AssertFalse && "AssertFalse".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_ASSERTFALSE);
            }else if(annotation instanceof Min && "Min".equals(errorCode)){
                Min vobj=(Min)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_MIN),vobj.value());
            }else if(annotation instanceof Max && "Max".equals(errorCode)){
                Max vobj=(Max)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_MAX), vobj.value());
            }else if(annotation instanceof DecimalMin && "DecimalMin".equals(errorCode)){
                DecimalMin vobj=(DecimalMin)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_DECIMALMIN), vobj.value());
            }else if(annotation instanceof DecimalMax && "DecimalMax".equals(errorCode)){
                DecimalMax vobj=(DecimalMax)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_DECIMALMAX), vobj.value());
            }else if(annotation instanceof Negative && "Negative".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NEGATIVE);
            }else if(annotation instanceof NegativeOrZero && "NegativeOrZero".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NEGATIVEORZERO);
            }else if(annotation instanceof Size && "Size".equals(errorCode)){
                Size vobj=(Size)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_SIZE),vobj.min()<0?0:vobj.min(),vobj.max()>10000?10000:vobj.max());
            }else if(annotation instanceof Digits && "Digits".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_DIGITS);
            }else if(annotation instanceof Past && "Past".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_PAST);
            }else if(annotation instanceof PastOrPresent && "PastOrPresent".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_PASTORPRESENT);
            }else if(annotation instanceof Future && "Future".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_FUTURE);
            }else if(annotation instanceof FutureOrPresent && "FutureOrPresent".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_FUTUREORPRESENT);
            }else if(annotation instanceof Pattern && "Pattern".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_PATTERN);
            }else if(annotation instanceof NotEmpty && "NotEmpty".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NOTEMPTY);
            }else if(annotation instanceof NotBlank && "NotBlank".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_NOTBLANK);
            }else if(annotation instanceof Email && "Email".equals(errorCode)){
                msgTemp=i18nUtil.getMessage(MessageConstants.SYS_VALID_EMAIL);
            }else if(annotation instanceof Range && "Range".equals(errorCode)){
                Range vobj=(Range)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_RANGE),vobj.min(),vobj.max());
            }else if(annotation instanceof NotLessThanZero && "NotLessThanZero".equals(errorCode)){
//                NotLessThanZero vobj=(NotLessThanZero)annotation;
                msgTemp=String.format(i18nUtil.getMessage(MessageConstants.SYS_VALID_SIZE),1,10000);
            }

            if(msgTemp!=null){
                if(result.equals("")){
                    result=msgTemp;
                }else{
                    result+=I18nUtil.getMsg("；",";")+msgTemp;
                }
            }
        }
        return result;
    }

}

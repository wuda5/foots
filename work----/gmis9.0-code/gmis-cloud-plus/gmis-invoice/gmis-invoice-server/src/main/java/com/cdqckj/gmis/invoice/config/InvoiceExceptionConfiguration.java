package com.cdqckj.gmis.invoice.config;

import com.cdqckj.gmis.boot.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发票服务-全局异常处理
 *
 * @author gmis
 * @date 2020-07-13
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class InvoiceExceptionConfiguration extends DefaultGlobalExceptionHandler {
}

package com.cdqckj.gmis.charges.config;

import com.cdqckj.gmis.boot.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收费模块-全局异常处理
 *
 * @author gmis
 * @date 2020-08-21
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class ChargesExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
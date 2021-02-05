package com.cdqckj.gmis.customercenter.config;

import com.cdqckj.gmis.boot.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户服务API聚合服务-全局异常处理
 *
 * @author gmis
 * @date 2020-09-30
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class CustomercenterExceptionConfiguration extends DefaultGlobalExceptionHandler {
}

package com.cdqckj.gmis.operateparam.config;

import com.cdqckj.gmis.boot.handler.DefaultGlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 *
 * @author gmis
 * @date 2020年01月02日17:19:27
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class OperateExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
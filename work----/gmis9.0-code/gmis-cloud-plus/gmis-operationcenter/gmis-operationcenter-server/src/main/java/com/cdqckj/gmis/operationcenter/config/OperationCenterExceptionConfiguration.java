package com.cdqckj.gmis.operationcenter.config;

import com.cdqckj.gmis.boot.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工程服务API聚合服务-全局异常处理
 *
 * @author gmis
 * @date 2020-10-14
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class OperationCenterExceptionConfiguration extends DefaultGlobalExceptionHandler {
}

package com.cdqckj.gmis.appmanager.controller;

import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerUpdateDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.appmanager.service.AppmanagerService;
import com.cdqckj.gmis.base.controller.SuperController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 应用管理表
 * </p>
 *
 * @author gmis
 * @date 2020-09-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appmanager")
@Api(value = "Appmanager", tags = "应用管理表")
//@PreAuth(replace = "appmanager:")
public class AppmanagerController extends SuperController<AppmanagerService, Long, Appmanager, AppmanagerPageDTO, Appmanager, AppmanagerUpdateDTO> {
}

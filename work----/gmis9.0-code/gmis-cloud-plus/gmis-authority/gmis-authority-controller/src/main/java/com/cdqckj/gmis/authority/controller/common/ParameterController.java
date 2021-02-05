package com.cdqckj.gmis.authority.controller.common;


import com.cdqckj.gmis.authority.dto.common.ParameterPageDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterSaveDTO;
import com.cdqckj.gmis.authority.dto.common.ParameterUpdateDTO;
import com.cdqckj.gmis.authority.entity.common.Parameter;
import com.cdqckj.gmis.authority.service.common.ParameterService;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 参数配置
 * </p>
 *
 * @author gmis
 * @date 2020-02-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/parameter")
@Api(value = "Parameter", tags = "参数配置")
@PreAuth(replace = "parameter:")
public class ParameterController extends SuperController<ParameterService, Long, Parameter, ParameterPageDTO, ParameterSaveDTO, ParameterUpdateDTO> {

}

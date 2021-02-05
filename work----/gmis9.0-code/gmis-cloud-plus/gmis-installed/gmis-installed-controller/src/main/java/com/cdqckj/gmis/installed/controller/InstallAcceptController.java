package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.dto.InstallAcceptSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallAcceptUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallAcceptPageDTO;
import com.cdqckj.gmis.installed.service.InstallAcceptService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 报装验收信息结果
 * </p>
 *
 * @author tp
 * @date 2020-11-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installAccept")
@Api(value = "InstallAccept", tags = "报装验收信息结果")
//@PreAuth(replace = "installAccept:")
public class InstallAcceptController extends SuperController<InstallAcceptService, Long, InstallAccept, InstallAcceptPageDTO, InstallAcceptSaveDTO, InstallAcceptUpdateDTO> {


}

package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.installed.entity.InstallDesign;
import com.cdqckj.gmis.installed.dto.InstallDesignSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDesignUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallDesignPageDTO;
import com.cdqckj.gmis.installed.enumeration.DesignProcessEnum;
import com.cdqckj.gmis.installed.service.InstallDesignService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * <p>
 * 前端控制器
 * 报装设计及预算
 * </p>
 *
 * @author tp
 * @date 2020-11-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installDesign")
@Api(value = "InstallDesign", tags = "报装设计及预算")
@PreAuth(replace = "installDesign:")
public class InstallDesignController extends SuperController<InstallDesignService, Long, InstallDesign, InstallDesignPageDTO, InstallDesignSaveDTO, InstallDesignUpdateDTO> {
//    @Override
//    public R<InstallDesign> handlerSave(@RequestBody @Valid InstallDesignSaveDTO saveDTO){
//        saveDTO.setDataStatus(DataStatusEnum.NORMAL.getValue());
//        saveDTO.setProcessStatus(DesignProcessEnum.TO_BE_REVIEWED.getCode());
//        return super.save(saveDTO);
//    }
}

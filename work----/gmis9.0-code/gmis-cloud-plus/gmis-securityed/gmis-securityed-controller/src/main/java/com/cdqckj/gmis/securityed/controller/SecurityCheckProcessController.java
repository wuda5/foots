package com.cdqckj.gmis.securityed.controller;

import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessUpdateDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessPageDTO;
import com.cdqckj.gmis.securityed.service.SecurityCheckProcessService;
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
 * 安检流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckProcess")
@Api(value = "SecurityCheckProcess", tags = "安检流程操作记录")
@PreAuth(replace = "securityCheckProcess:")
public class SecurityCheckProcessController extends SuperController<SecurityCheckProcessService, Long, SecurityCheckProcess, SecurityCheckProcessPageDTO, SecurityCheckProcessSaveDTO, SecurityCheckProcessUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SecurityCheckProcess> securityCheckProcessList = list.stream().map((map) -> {
            SecurityCheckProcess securityCheckProcess = SecurityCheckProcess.builder().build();
            //TODO 请在这里完成转换
            return securityCheckProcess;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(securityCheckProcessList));
    }
}

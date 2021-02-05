package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfPageDTO;
import com.cdqckj.gmis.operateparam.service.ExceptionRuleConfService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 抄表异常规则配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/exceptionRuleConf")
@Api(value = "ExceptionRuleConf", tags = "抄表异常规则配置")
@PreAuth(replace = "exceptionRuleConf:")
public class ExceptionRuleConfController extends SuperController<ExceptionRuleConfService, Long, ExceptionRuleConf, ExceptionRuleConfPageDTO, ExceptionRuleConfSaveDTO, ExceptionRuleConfUpdateDTO> {

    /**
     * 查询
     *
     * @param list
     */
    @PostMapping(value = "/queryByGasTypeId")
    public R<List<ExceptionRuleConf>> queryByGasTypeId(@RequestBody List<Long> list){
        return R.success(baseService.queryByGasTypeId(list));
    }
}

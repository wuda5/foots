package com.cdqckj.gmis.operation.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceUpdateDTO;
import com.cdqckj.gmis.operation.dto.OperationAcceptancePageDTO;
import com.cdqckj.gmis.operation.service.OperationAcceptanceService;
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
 * 运行维护受理
 * </p>
 *
 * @author gmis
 * @date 2020-08-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operationAcceptance")
@Api(value = "OperationAcceptance", tags = "运行维护受理")
@PreAuth(replace = "operationAcceptance:")
public class OperationAcceptanceController extends SuperController<OperationAcceptanceService, Long, OperationAcceptance, OperationAcceptancePageDTO, OperationAcceptanceSaveDTO, OperationAcceptanceUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OperationAcceptance> operationAcceptanceList = list.stream().map((map) -> {
            OperationAcceptance operationAcceptance = OperationAcceptance.builder().build();
            //TODO 请在这里完成转换
            return operationAcceptance;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(operationAcceptanceList));
    }
}

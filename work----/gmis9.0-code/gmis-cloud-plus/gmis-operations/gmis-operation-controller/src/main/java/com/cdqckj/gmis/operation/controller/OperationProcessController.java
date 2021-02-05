package com.cdqckj.gmis.operation.controller;

import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.dto.OperationProcessSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessUpdateDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessPageDTO;
import com.cdqckj.gmis.operation.service.OperationProcessService;
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
 * 运行维护流程操作记录
 * </p>
 *
 * @author gmis
 * @date 2020-07-31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operationProcess")
@Api(value = "OperationProcess", tags = "运行维护流程操作记录")
@PreAuth(replace = "operationProcess:")
public class OperationProcessController extends SuperController<OperationProcessService, Long, OperationProcess, OperationProcessPageDTO, OperationProcessSaveDTO, OperationProcessUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OperationProcess> operationProcessList = list.stream().map((map) -> {
            OperationProcess operationProcess = OperationProcess.builder().build();
            //TODO 请在这里完成转换
            return operationProcess;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(operationProcessList));
    }
}

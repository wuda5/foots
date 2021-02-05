package com.cdqckj.gmis.operation.controller;

import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordPageDTO;
import com.cdqckj.gmis.operation.service.OperationRecordService;
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
 * 运行维护工单
 * </p>
 *
 * @author gmis
 * @date 2020-07-31
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operationRecord")
@Api(value = "OperationRecord", tags = "运行维护工单")
@PreAuth(replace = "operationRecord:")
public class OperationRecordController extends SuperController<OperationRecordService, Long, OperationRecord, OperationRecordPageDTO, OperationRecordSaveDTO, OperationRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OperationRecord> operationRecordList = list.stream().map((map) -> {
            OperationRecord operationRecord = OperationRecord.builder().build();
            //TODO 请在这里完成转换
            return operationRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(operationRecordList));
    }
}

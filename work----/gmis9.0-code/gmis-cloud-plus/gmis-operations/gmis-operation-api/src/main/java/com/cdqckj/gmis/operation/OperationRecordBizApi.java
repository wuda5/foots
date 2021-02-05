package com.cdqckj.gmis.operation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.dto.OperationRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.hystrix.OperationRecordBizApiFallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}", fallback = OperationRecordBizApiFallBack.class
        , path = "/operationRecord", qualifier = "OperationRecordBizApi")
public interface OperationRecordBizApi {
    /**
     * 运行维护工单列表分页查询
     * @param params
     * @return
     */
    @ApiOperation(value = "运行维护工单列表分页查询")
    @PostMapping(value = "/page")
    @SysLog(value = "'运行维护工单列表分页查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    //@PreAuth("hasPermit('{}view')")
    public R<Page<OperationRecord>> page(@RequestBody @Validated PageParams<OperationRecordPageDTO> params);

    /**
     * 创建工单
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "创建工单")
    @PostMapping
    @SysLog(value = "创建工单", request = false)
    //@PreAuth("hasPermit('{}add')")
    public R<OperationRecord> save(@RequestBody @Validated OperationRecordSaveDTO saveDTO);

    /**
     * 更新工单
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "更新工单")
    @PutMapping
    @SysLog(value = "'更新工单:' + #updateDTO?.id", request = false)
    public R<OperationRecord> update(@RequestBody @Validated(SuperEntity.Update.class) OperationRecordUpdateDTO updateDTO);
}

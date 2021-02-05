package com.cdqckj.gmis.operation;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.hystrix.MaintenanceBizApiFallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${gmis.feign.operation-server:gmis-operation}", fallback = MaintenanceBizApiFallBack.class
        , path = "/operation", qualifier = "MaintenanceBizApi")
public interface MaintenanceBizApi {
    /**
     * 新增运维受理信息
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "新增运维受理信息")
    @PostMapping(value = "/saveOperationAcceptance")
    @SysLog(value = "新增运维受理信息", request = false)
    public R<OperationAcceptance> saveOperationAcceptance(@RequestBody @Validated OperationAcceptanceSaveDTO saveDTO);
    /**
     * 创建工单
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "创建工单")
    @PostMapping(value = "/createOperation")
    @SysLog(value = "创建工单", request = false)
    public R<OperationRecord> createOperation(@RequestBody @Validated OperationRecordSaveDTO saveDTO,
                                              @RequestParam("isOrNotReviewed") String isOrNotReviewed);
    /**
     * 提审
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "提审", notes = "提审")
    @PutMapping(value = "/initiateReview")
    public R<Boolean> initiateReview(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);

    /**
     * 审核
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "审核", notes = "审核")
    @PutMapping(value = "/approvalOperation")
    public R<Boolean> approvalOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);

    /**
     * 驳回运行维护工单
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "驳回", notes = "驳回")
    @PutMapping(value = "/rejectOperation")
    public R<Boolean> rejectOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);

    /**
     * 撤回运行维护工单
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "撤回", notes = "撤回")
    @PutMapping(value = "/revokeOperation")
    public R<Boolean> revokeOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    /**
     * 终止
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "终止", notes = "终止")
    @PutMapping(value = "/terminateOperation")
    public R<Boolean> terminateOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    /**
     * 运行维护工单接单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "接单", notes = "接单")
    @PutMapping(value = "/recevieOperationOrder")
    public R<Boolean> recevieOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO);
    /**
     * 运行维护工单拒单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "拒单", notes = "拒单")
    @PutMapping(value = "/rejectOperationOrder")
    public R<Boolean> rejectOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO);
    /**
     * 运行维护工单转单
     * @param saveDTO
     * @param operationId
     * @return
     */
    @ApiOperation(value = "转单", notes = "转单")
    @PostMapping(value = "/transforOperationOrder")
    public R<Boolean> transforOperationOrder(@RequestBody @Validated OperationRecordSaveDTO saveDTO,
                                             @RequestParam("operationId") Long operationId);
    /**
     * 运行维护工单退单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "退单", notes = "退单")
    @PostMapping(value = "/returnOperationOrder")
    public R<Boolean> returnOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO);
    /**
     * 运行维护预约
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "预约", notes = "预约")
    @PutMapping(value = "/bookOperation")
    public R<Boolean> bookOperation(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO);
    /**
     * 登记结果
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "登记结果", notes = "登记结果")
    @PutMapping(value = "/registerResult")
    public R<Boolean> registerResult(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO);
}

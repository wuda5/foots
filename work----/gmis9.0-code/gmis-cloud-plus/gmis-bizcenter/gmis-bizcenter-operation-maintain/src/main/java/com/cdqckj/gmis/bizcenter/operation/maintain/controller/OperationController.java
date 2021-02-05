package com.cdqckj.gmis.bizcenter.operation.maintain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.MaintenanceBizApi;
import com.cdqckj.gmis.operation.OperationAcceptanceBizApi;
import com.cdqckj.gmis.operation.OperationProcessBizApi;
import com.cdqckj.gmis.operation.OperationRecordBizApi;
import com.cdqckj.gmis.operation.dto.*;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/operation")
@Api(value = "operation", tags = "运行维护")
//@PreAuth(replace = "OperationController:")
public class OperationController {

    @Autowired
    private OperationRecordBizApi operationRecordBizApi;
    @Autowired
    private MaintenanceBizApi maintenanceBizApi;
    @Autowired
    private OperationAcceptanceBizApi operationAcceptanceBizApi;
    @Autowired
    private OperationProcessBizApi operationProcessBizApi;
    /**
     * 运行维护工单列表分页查询
     * @param params
     * @return
     */
    @ApiOperation(value = "工单列表分页列表查询")
    @PostMapping(value = "/getOperationRecordPage")
    @SysLog(value = "'工单列表分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<OperationRecord>> page(@RequestBody @Validated PageParams<OperationRecordPageDTO> params){
        return operationRecordBizApi.page(params);
    }

    /**
     * 创建工单
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "创建工单")
    @PostMapping(value = "/createOperation")
    @SysLog(value = "创建工单", request = false)
    public R<OperationRecord> createOperation(@RequestBody @Validated OperationRecordSaveDTO saveDTO,
                                              @RequestParam("isOrNotReviewed") String isOrNotReviewed){
        return maintenanceBizApi.createOperation(saveDTO,isOrNotReviewed);
    }

    /**
     * 新增运维受理信息
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "新增受理信息")
    @PostMapping(value = "/saveOperationAcceptance")
    @SysLog(value = "新增受理信息", request = false)
    public R<OperationAcceptance> saveOperationAcceptance(@RequestBody @Validated OperationAcceptanceSaveDTO saveDTO){
        return maintenanceBizApi.saveOperationAcceptance(saveDTO);
    }

    /**
     * 运行维护受理信息分页列表查询
     * @param params
     * @return
     */
    @ApiOperation(value = "受理信息分页列表查询")
    @PostMapping(value = "/operationAcceptancePage")
    @SysLog(value = "'受理信息分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<OperationAcceptance>> operationAcceptancePage(@RequestBody @Validated PageParams<OperationAcceptancePageDTO> params) {
        return operationAcceptanceBizApi.page(params);
    }

    /**
     * 提审
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "提审", notes = "提审")
    @PutMapping(value = "/initiateReview")
    public R<Boolean> initiateReview(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return maintenanceBizApi.initiateReview(operationRecordUpdateDTOList);
    }

    /**
     * 修改运行维护工单信息
     * @param operationRecordUpdateDTO
     * @param operationStatus
     * @param operationProcessState
     */
    private void modifyOperationRecord(OperationRecordUpdateDTO operationRecordUpdateDTO,
                                     Integer operationStatus, Integer operationProcessState) {
        operationRecordUpdateDTO.setOperationStatus(operationStatus);
        operationRecordUpdateDTO.setOperationProcessState(operationProcessState);
        R<OperationRecord> result = operationRecordBizApi.update(operationRecordUpdateDTO);
        //保存流程信息
//        saveOperationProcess(result);
    }

    /**
     * 审核运行维护工单
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "审核", notes = "审核")
    @PutMapping(value = "/approvalOperation")
    public R<Boolean> approvalOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList){
        return maintenanceBizApi.approvalOperation(operationRecordUpdateDTOList);
    }

    /**
     * 驳回运行维护工单
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "驳回", notes = "驳回")
    @PutMapping(value = "/rejectOperation")
    public R<Boolean> rejectOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList){
        return maintenanceBizApi.rejectOperation(operationRecordUpdateDTOList);
    }

    /**
     * 撤回运行维护工单
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "撤回", notes = "撤回")
    @PutMapping(value = "/revokeOperation")
    public R<Boolean> revokeOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList){
        return maintenanceBizApi.revokeOperation(operationRecordUpdateDTOList);
    }

    /**
     * 终止
     * @param operationRecordUpdateDTOList
     * @return
     */
    @ApiOperation(value = "终止", notes = "终止")
    @PutMapping(value = "/terminateOperation")
    public R<Boolean> terminateOperation(@RequestBody @Validated List<OperationRecordUpdateDTO> operationRecordUpdateDTOList){
        return maintenanceBizApi.terminateOperation(operationRecordUpdateDTOList);
    }

    /**
     * 运行维护工单接单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "接单", notes = "接单")
    @PutMapping(value = "/recevieOperationOrder")
    public R<Boolean> recevieOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO){
        return maintenanceBizApi.recevieOperationOrder(operationRecordUpdateDTO);
    }

    /**
     * 运行维护工单拒单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "拒单", notes = "拒单")
    @PutMapping(value = "/rejectOperationOrder")
    public R<Boolean> rejectOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO){
        return maintenanceBizApi.rejectOperationOrder(operationRecordUpdateDTO);
    }

    /**
     * 运行维护工单转单
     * @param saveDTO
     * @param operationId
     * @return
     */
    @ApiOperation(value = "转单", notes = "转单")
    @PostMapping(value = "/transforOperationOrder")
    public R<Boolean> transforOperationOrder(@RequestBody @Validated OperationRecordSaveDTO saveDTO,
                                             @RequestParam("operationId") Long operationId) {
        return maintenanceBizApi.transforOperationOrder(saveDTO, operationId);
    }

    /**
     * 运行维护工单退单
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "退单", notes = "退单")
    @PostMapping(value = "/returnOperationOrder")
    public R<Boolean> returnOperationOrder(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return maintenanceBizApi.returnOperationOrder(operationRecordUpdateDTO);
    }

    /**
     * 运行维护预约
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "预约", notes = "预约")
    @PutMapping(value = "/bookOperation")
    public R<Boolean> bookOperation(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO){
       return maintenanceBizApi.bookOperation(operationRecordUpdateDTO);
    }

    /**
     * 登记结果
     * @param operationRecordUpdateDTO
     * @return
     */
    @ApiOperation(value = "登记结果", notes = "登记结果")
    @PutMapping(value = "/registerResult")
    public R<Boolean> registerResult(@RequestBody @Validated OperationRecordUpdateDTO operationRecordUpdateDTO){
       return maintenanceBizApi.registerResult(operationRecordUpdateDTO);
    }

    /**
     * 运行维护流程信息分页列表查询
     * @param params
     * @return
     */
    @ApiOperation(value = "流程信息分页列表查询")
    @PostMapping(value = "/operationProcessPage")
    @SysLog(value = "'流程信息分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<OperationProcess>> operationProcessPage(@RequestBody @Validated PageParams<OperationProcessPageDTO> params) {
        return operationProcessBizApi.page(params);
    }

}

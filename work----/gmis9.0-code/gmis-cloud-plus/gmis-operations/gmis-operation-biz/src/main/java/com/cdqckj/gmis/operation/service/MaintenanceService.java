package com.cdqckj.gmis.operation.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.entity.OperationRecord;

import java.util.List;

public interface MaintenanceService {

    public R<OperationAcceptance> saveOperationAcceptance(OperationAcceptanceSaveDTO saveDTO);
    public R<OperationRecord> createOperation(OperationRecordSaveDTO saveDTO, String isOrNotReviewed);
    public R<Boolean> initiateReview(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    public R<Boolean> approvalOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    public R<Boolean> rejectOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);

    public R<Boolean> revokeOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    public R<Boolean> terminateOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList);
    public R<Boolean> recevieOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO);
    public R<Boolean> rejectOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO);
    public R<Boolean> transforOperationOrder(OperationRecordSaveDTO saveDTO, Long operationId);
    public R<Boolean> returnOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO);
    public R<Boolean> bookOperation(OperationRecordUpdateDTO operationRecordUpdateDTO);
    public R<Boolean> registerResult(OperationRecordUpdateDTO operationRecordUpdateDTO);
}

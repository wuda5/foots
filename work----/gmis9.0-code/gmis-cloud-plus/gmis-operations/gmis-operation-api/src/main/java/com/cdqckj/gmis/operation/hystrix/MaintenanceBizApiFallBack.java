package com.cdqckj.gmis.operation.hystrix;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.MaintenanceBizApi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaintenanceBizApiFallBack implements MaintenanceBizApi {
    @Override
    public R<OperationAcceptance> saveOperationAcceptance(OperationAcceptanceSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<OperationRecord> createOperation(OperationRecordSaveDTO saveDTO, String isOrNotReviewed) {
        return R.timeout();
    }

    @Override
    public R<Boolean> initiateReview(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return R.timeout();
    }

    @Override
    public R<Boolean> approvalOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return R.timeout();
    }

    @Override
    public R<Boolean> rejectOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return R.timeout();
    }

    @Override
    public R<Boolean> revokeOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return R.timeout();
    }

    @Override
    public R<Boolean> terminateOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        return R.timeout();
    }

    @Override
    public R<Boolean> recevieOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> rejectOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> transforOperationOrder(OperationRecordSaveDTO saveDTO, Long operationId) {
        return R.timeout();
    }

    @Override
    public R<Boolean> returnOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> bookOperation(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> registerResult(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        return R.timeout();
    }
}

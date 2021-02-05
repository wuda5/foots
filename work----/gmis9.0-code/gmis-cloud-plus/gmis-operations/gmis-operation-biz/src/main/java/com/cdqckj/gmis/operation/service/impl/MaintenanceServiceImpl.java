package com.cdqckj.gmis.operation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.enumeration.OperationProcessStateEnum;
import com.cdqckj.gmis.operation.enumeration.OperationStateEnum;
import com.cdqckj.gmis.operation.service.MaintenanceService;
import com.cdqckj.gmis.operation.service.OperationAcceptanceService;
import com.cdqckj.gmis.operation.service.OperationProcessService;
import com.cdqckj.gmis.operation.service.OperationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Slf4j
@Service
@DS("#thread.tenant")
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    private OperationAcceptanceService operationAcceptanceService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private OperationProcessService operationProcessService;
    @Autowired
    RedisService redisService;
    @Override
    public R<OperationAcceptance> saveOperationAcceptance(OperationAcceptanceSaveDTO saveDTO) {
        R<OperationAcceptance> result = R.successDef();
        if (result.getDefExec()) {
            //TODO 运维受理编号
            saveDTO.setOperAcceptNo("");
            OperationAcceptance model = BeanUtil.toBean(saveDTO, OperationAcceptance.class);
            operationAcceptanceService.save(model);
            result.setData(model);
        }
        return result;
    }

    @Transactional
    @Override
    public R<OperationRecord> createOperation(OperationRecordSaveDTO saveDTO, String isOrNotReviewed) {
        R<OperationRecord> result = R.successDef();
        try {
            //需要审核
            if("0".equals(isOrNotReviewed)) {
                saveDTO.setOperationStatus(OperationStateEnum.PENDING_INITIATE_REVIEW.getCode());
                saveDTO.setOperationProcessState(OperationProcessStateEnum.PENDING_INITIATE_REVIEW.getCode());
            }
            //不需要审核
            else{
                saveDTO.setOperationStatus(OperationStateEnum.WAIT_RECEIVE_ORDER.getCode());
                saveDTO.setOperationProcessState(OperationProcessStateEnum.CONFIRM_ORDER_RECEIVE.getCode());
            }
            if (result.getDefExec()) {
                OperationRecord operationRecord = saveOperationRecord(saveDTO);
                result.setData(operationRecord);
            }

            //保存流程信息
            saveOperationProcess(result);
        } catch (Exception e) {
            log.error("创建运行维护工单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    private OperationRecord saveOperationRecord(OperationRecordSaveDTO saveDTO) {
        OperationRecord operationRecord = BeanUtil.toBean(saveDTO, OperationRecord.class);
        operationRecordService.save(operationRecord);
        return operationRecord;
    }

    @Transactional
    @Override
    public R<Boolean> initiateReview(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        boolean flag = true;
        try {
            operationRecordUpdateDTOList.stream().forEach(operationRecordUpdateDTO->{
                modifyOperationRecord(operationRecordUpdateDTO,
                        OperationStateEnum.PENDING_APPROVAL.getCode(),
                        OperationProcessStateEnum.PENDING_APPROVAL.getCode());
            });
        } catch (Exception e) {
            flag = false;
            log.error("运行维护提审异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> approvalOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        boolean flag = true;
        try {
            operationRecordUpdateDTOList.stream().forEach(operationRecordUpdateDTO->{
                modifyOperationRecord(operationRecordUpdateDTO,
                        OperationStateEnum.WAIT_RECEIVE_ORDER.getCode(),
                        OperationProcessStateEnum.CONFIRM_ORDER_RECEIVE.getCode());
            });
        } catch (Exception e) {
            flag = false;
            log.error("审核运行维护工单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> rejectOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        boolean flag = true;
        try {
            operationRecordUpdateDTOList.stream().forEach(operationRecordUpdateDTO->{
                modifyOperationRecord(operationRecordUpdateDTO,
                        OperationStateEnum.PENDING_INITIATE_REVIEW.getCode(),
                        OperationProcessStateEnum.REJECT.getCode());
            });
        } catch (Exception e) {
            flag = false;
            log.error("驳回运行维护工单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> revokeOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        boolean flag = true;
        try {
            operationRecordUpdateDTOList.stream().forEach(operationRecordUpdateDTO->{
                modifyOperationRecord(operationRecordUpdateDTO,
                        OperationStateEnum.PENDING_INITIATE_REVIEW.getCode(),
                        OperationProcessStateEnum.WITHDRAW.getCode());
            });
        } catch (Exception e) {
            flag = false;
            log.error("撤回运行维护工单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.fail(getLangMessage(MessageConstants.REVOKE_EXCEPTION));
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> terminateOperation(List<OperationRecordUpdateDTO> operationRecordUpdateDTOList) {
        boolean flag = true;
        try {
            operationRecordUpdateDTOList.stream().forEach(operationRecordUpdateDTO->{
                modifyOperationRecord(operationRecordUpdateDTO,
                        OperationStateEnum.TERMINATION.getCode(),
                        OperationProcessStateEnum.TERMINATION.getCode());
            });
        } catch (Exception e) {
            flag = false;
            log.error("终止运行维护工单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.fail(getLangMessage(MessageConstants.TERMINATION_EXCEPTION));
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> recevieOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        boolean flag = true;
        try {
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.TO_BE_MAINTAINED.getCode(),
                    OperationProcessStateEnum.APPOINT.getCode());
        } catch (Exception e) {
            flag = false;
            log.error("运行维护工单接单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> rejectOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        boolean flag = true;
        try {
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.CANCELLATION.getCode(),
                    OperationProcessStateEnum.TO_BE_ASSIGNED.getCode());
        } catch (Exception e) {
            flag = false;
            log.error("运行维护工单拒单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> transforOperationOrder(OperationRecordSaveDTO saveDTO, Long operationId) {
        boolean flag = true;
        try {
            //
            saveDTO.setOperationStatus(OperationStateEnum.WAIT_RECEIVE_ORDER.getCode());
            saveDTO.setOperationProcessState(OperationProcessStateEnum.CONFIRM_ORDER_RECEIVE.getCode());
            OperationRecord operationRecord = saveOperationRecord(saveDTO);

            OperationRecordUpdateDTO operationRecordUpdateDTO = new OperationRecordUpdateDTO();
            operationRecordUpdateDTO.setTransferId(operationRecord.getId());
            operationRecordUpdateDTO.setId(operationId);
            operationRecordUpdateDTO.setAcceptNo(operationRecord.getAcceptNo());
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.CANCELLATION.getCode(),
                    OperationProcessStateEnum.TRANSFOR_ORDER.getCode());


        } catch (Exception e) {
            flag = false;
            log.error("运行维护工单转单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> returnOperationOrder(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        boolean flag = true;
        try {
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.CANCELLATION.getCode(),
                    OperationProcessStateEnum.BACK_ORDER.getCode());
        } catch (Exception e) {
            flag = false;
            log.error("运行维护工单退单异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> bookOperation(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        boolean flag = true;
        try {
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.TO_BE_MAINTAINED.getCode(),
                    OperationProcessStateEnum.VISIT.getCode());
        } catch (Exception e) {
            flag = false;
            log.error("运行维护预约异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    @Transactional
    @Override
    public R<Boolean> registerResult(OperationRecordUpdateDTO operationRecordUpdateDTO) {
        boolean flag = true;
        try {
            modifyOperationRecord(operationRecordUpdateDTO,
                    OperationStateEnum.END_ORDER.getCode(),
                    OperationProcessStateEnum.END_ORDER.getCode());
        } catch (Exception e) {
            flag = false;
            log.error("运行维护登记结果异常，{}",e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.success(flag);
    }

    /**
     * 保存流程信息
     * @param result
     */
    private void saveOperationProcess(R<OperationRecord> result) {
        OperationRecord operationRecord = result.getData();
        OperationProcess operationProcess = new OperationProcess();
        operationProcess.setCompanyCode(BaseContextHandler.getTenantId());
        operationProcess.setCompanyName(BaseContextHandler.getTenantName());
        operationProcess.setOrgId(BaseContextHandler.getOrgId());
        operationProcess.setOrgName(BaseContextHandler.getOrgName());
        operationProcess.setAcceptId(operationRecord.getAcceptNo());
//        operationProcess.setAcceptId("1212123412121212341212121234121212123412");
        operationProcess.setOperProcessState(String.valueOf(operationRecord.getOperationProcessState()));
        operationProcessService.save(operationProcess);
    }

    /**
     * 修改运行维护工单信息
     * @param operationRecordUpdateDTO
     * @param operationStatus
     * @param operationProcessState
     */
    private void modifyOperationRecord(OperationRecordUpdateDTO operationRecordUpdateDTO,
                                       Integer operationStatus, Integer operationProcessState) {
        R<OperationRecord> result = R.successDef();
        if (result.getDefExec()) {
            operationRecordUpdateDTO.setOperationStatus(operationStatus);
            operationRecordUpdateDTO.setOperationProcessState(operationProcessState);
            OperationRecord operationRecord = BeanUtil.toBean(operationRecordUpdateDTO, OperationRecord.class);
            operationRecordService.updateById(operationRecord);
            result.setData(operationRecord);
        }
        //保存流程信息
        saveOperationProcess(result);
    }


    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+ BaseContextHandler.getTenant());
        switch(langType){
            case 1:
                message = (String) redisService.hmget("langZh").get(key);
                break;
            case 2:
                message = (String) redisService.hmget("langEn").get(key);
                break;
            default:
                message = null;
        }
        return message;
    }


}

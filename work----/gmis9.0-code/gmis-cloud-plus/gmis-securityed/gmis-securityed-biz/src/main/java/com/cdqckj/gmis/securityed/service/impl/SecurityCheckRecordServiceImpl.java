package com.cdqckj.gmis.securityed.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.common.enums.BizOCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.securityed.dao.SecurityCheckRecordMapper;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordUpdateDTO;
import com.cdqckj.gmis.securityed.entity.Enum.SecurityCheckRecordStatusEnum;
import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.service.SecurityCheckProcessService;
import com.cdqckj.gmis.securityed.service.SecurityCheckRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 安检计划记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SecurityCheckRecordServiceImpl extends SuperServiceImpl<SecurityCheckRecordMapper, SecurityCheckRecord> implements SecurityCheckRecordService {
   @Autowired
    SecurityCheckProcessService securityCheckProcessService;
    @Override

    /*@TODO  新增安检计划
    * */
    @CodeNotLost
    public Boolean saveSecurityCheckRecord(List<SecurityCheckRecord> securityCheckRecords) {
        List<SecurityCheckRecord> saveDTOS=new ArrayList<>();
        List<SecurityCheckProcess> securityCheckProcessSaveDTOS=new ArrayList<>();
        SecurityCheckProcess securityCheckProcessSaveDTO=null;
        for (SecurityCheckRecord securityCheckRecord : securityCheckRecords) {
            securityCheckProcessSaveDTO=new SecurityCheckProcess();
            securityCheckRecord.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_INITIATE_REVIEW.getCode());
            //securityCheckRecord.setScNo(BizCodeUtil.genTaskFlowCode(BizFCode.AJ,BizCodeUtil.TASK_SECURITY));
            securityCheckRecord.setScNo(BizCodeNewUtil.getSafeCheckCode());
            saveDTOS.add(securityCheckRecord);
            /*@TODO 流程信息
            * */
            securityCheckProcessSaveDTO.setSecurityCheckNunber(securityCheckRecord.getScNo());
            securityCheckProcessSaveDTO.setProcessState(securityCheckRecord.getDataStatus());
            securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(securityCheckRecord.getPlanSecurityCheckUserId()));
            securityCheckProcessSaveDTO.setProcessUserName(securityCheckRecord.getPlanSecurityCheckUserName());
            securityCheckProcessSaveDTO.setProcessDesc(securityCheckRecord.getSecurityCheckContent());
            securityCheckProcessSaveDTO.setProcessTime(LocalDateTime.now());
            securityCheckProcessSaveDTOS.add(securityCheckProcessSaveDTO);
        }
        Boolean saveBatch=saveBatch(saveDTOS);
        if (saveBatch){
            securityCheckProcessService.saveSecurityCheckProcess(securityCheckProcessSaveDTOS);
            return  saveBatch;
        }
       return false;
    }

    @Override
    /*@TODO  审核安检计划
     * */
    public Integer approvaledSecurityCheckRecord(SecurityCheckRecord params) {
        params.setReviewUserId(BaseContextHandler.getUserId());
        params.setReviewUserName(BaseContextHandler.getTenantName());
        params.setReviewTime(LocalDateTime.now());
        params.setDataStatus(SecurityCheckRecordStatusEnum.TO_BE_ASSIGNED.getCode());
        /*@TODO 流程信息
         * */
        SecurityCheckProcess securityCheckProcessSaveDTO=new SecurityCheckProcess();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getReviewUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getReviewUserName());
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        securityCheckProcessSaveDTO.setProcessTime(params.getReviewTime());
        if(! StringUtils.isEmpty(params.getReviewObjection())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getReviewObjection());
        }
        int update = baseMapper.updateById(params);
        if(update>0){
            securityCheckProcessService.save(securityCheckProcessSaveDTO);
            return update;
        }
        return -1;
    }

    @Override
    public Integer rejectSecurityCheckRecord(SecurityCheckRecord params) {
        params.setRejectUserId(BaseContextHandler.getUserId());
        params.setRejectUserName(BaseContextHandler.getTenantName());
        params.setRejectTime(LocalDateTime.now());
        params.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_INITIATE_REVIEW.getCode());
        return baseMapper.updateById(params);
    }

    @Override
    public Integer endSecurityCheckRecord(SecurityCheckRecord params) {
        /*@TODO 安检计划
         * */
        params.setDataStatus(SecurityCheckRecordStatusEnum.TERMINATION.getCode());
        params.setStopTime(LocalDateTime.now());
        params.setStopUserId(BaseContextHandler.getUserId());
        params.setStopUserName(BaseContextHandler.getName());
        /*@TODO 流程信息
         * */
        SecurityCheckProcess securityCheckProcessSaveDTO=new SecurityCheckProcess();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getStopUserId()));
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessUserName(params.getStopUserName());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        if(! StringUtils.isEmpty(params.getStopDesc())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getStopDesc());
        }
        int i = baseMapper.updateById(params);
        if (i>0){
            securityCheckProcessService.save(securityCheckProcessSaveDTO);
            return i;
        }
        return -1;
    }

    @Override
    public Integer leafletSecurityCheckRecord(SecurityCheckRecord params) {
      /*  params.setDataStatus(SecurityCheckRecordStatusEnum.WAITING_FOR_CHECK.getCode());*/

     /*  @TODO  安检工单状态在聚合中心去设置
      params.setOrderState(OrderTypeEnum.SECURITY_ORDER.getCode());*/
        //派工人
        params.setDistributionTime(LocalDateTime.now());
        params.setDistributionUserId(BaseContextHandler.getUserId());
        params.setDistributionUserName(BaseContextHandler.getName());
        params.setDataStatus(SecurityCheckRecordStatusEnum.WAITING_FOR_CHECK.getCode());

        //流程信息
        SecurityCheckProcess securityCheckProcessSaveDTO=new SecurityCheckProcess();
     /*   securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getDistributionUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getDistributionUserName());*/

        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getSecurityCheckUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getSecurityCheckUserName());
       /* @TODO  差一个流程描述  工单内容*/
        securityCheckProcessSaveDTO.setProcessDesc(params.getSecurityCheckContent());
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessTime(params.getSecurityCheckTime());
        /*securityCheckProcessSaveDTO.setProcessTime(params.getDistributionTime());*/
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        int i = baseMapper.updateById(params);
        if(i==1){
            securityCheckProcessService.save(securityCheckProcessSaveDTO);
            return i;
        }
        return -1;
    }

    /*@TODO 接单
    * */
    @Override
    public Integer giveOrder(SecurityCheckRecord params) {
        //@TODO  实际安检时间 聚合中心设置
        params.setSecurityCheckUserId(BaseContextHandler.getUserId());
        params.setSecurityCheckUserName(BaseContextHandler.getName());
        params.setDataStatus(SecurityCheckRecordStatusEnum.WAITING_FOR_CHECK.getCode());

        //流程信息
        SecurityCheckProcess securityCheckProcessSaveDTO=new SecurityCheckProcess();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getSecurityCheckUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getSecurityCheckUserName());
        if(! StringUtils.isEmpty(params.getSecurityCheckContent())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getSecurityCheckContent());
        }
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessTime(params.getSecurityCheckTime());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        int i = baseMapper.updateById(params);
        if(i>0){
            securityCheckProcessService.save(securityCheckProcessSaveDTO);
            return i;
        }
        return -1;
    }

    @Override
    public Integer completeOrder(SecurityCheckRecord params) {
        params.setEndJobUserId(BaseContextHandler.getUserId());
        params.setEndJobUserName(BaseContextHandler.getName());
        params.setEndJobTime(LocalDateTime.now());
        params.setDataStatus(SecurityCheckRecordStatusEnum.END_ORDERS.getCode());

        //流程信息
        SecurityCheckProcess securityCheckProcessSaveDTO=new SecurityCheckProcess();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getEndJobUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getEndJobUserName());
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessTime(params.getEndJobTime());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        int i = baseMapper.updateById(params);
        if(i>0){
            securityCheckProcessService.save(securityCheckProcessSaveDTO);
            return i;
        }
        return -1;
    }
}

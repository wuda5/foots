package com.cdqckj.gmis.bizcenter.securitycheckrecord.service.impl;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.securitycheckrecord.service.SecurityCheckRecordService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.common.enums.BizOCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.securityed.SecurityCheckProcessApi;
import com.cdqckj.gmis.securityed.SecurityCheckRecordApi;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordUpdateDTO;
import com.cdqckj.gmis.securityed.entity.Enum.SecurityCheckRecordStatusEnum;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.vo.SendOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class SecurityCheckRecordServiceIml implements SecurityCheckRecordService {
    @Autowired
    private  SecurityCheckRecordApi securityCheckRecordApi;
    @Autowired
    private SecurityCheckProcessApi securityCheckProcessApi;
    @Autowired
    private OrderRecordApi orderRecordApi;
    @Override
    /*新增安检计划
    * */
    @CodeNotLost
    public R<SecurityCheckRecord> saveList(List<SecurityCheckRecordSaveDTO> securityCheckRecordSaveDTOS) {
        List<SecurityCheckRecordSaveDTO> saveDTOS=new ArrayList<>();
        List<SecurityCheckProcessSaveDTO> securityCheckProcessSaveDTOS=new ArrayList<>();
        SecurityCheckProcessSaveDTO securityCheckProcessSaveDTO=null;
        for (SecurityCheckRecordSaveDTO securityCheckRecordSaveDTO : securityCheckRecordSaveDTOS) {
            securityCheckProcessSaveDTO=new SecurityCheckProcessSaveDTO();
            securityCheckRecordSaveDTO.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_INITIATE_REVIEW.getCode());
            //securityCheckRecordSaveDTO.setScNo(BizCodeUtil.genTaskFlowCode(BizFCode.AJ,BizCodeUtil.TASK_SECURITY));
            securityCheckRecordSaveDTO.setScNo(BizCodeNewUtil.getSafeCheckCode());
            saveDTOS.add(securityCheckRecordSaveDTO);
            securityCheckProcessSaveDTO.setSecurityCheckNunber(securityCheckRecordSaveDTO.getScNo());
            securityCheckProcessSaveDTO.setProcessState(securityCheckRecordSaveDTO.getDataStatus());
            securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(securityCheckRecordSaveDTO.getPlanSecurityCheckUserId()));
            securityCheckProcessSaveDTO.setProcessUserName(securityCheckRecordSaveDTO.getPlanSecurityCheckUserName());
            securityCheckProcessSaveDTO.setProcessDesc(securityCheckRecordSaveDTO.getSecurityCheckContent());
            securityCheckProcessSaveDTO.setProcessTime(LocalDateTime.now());
            securityCheckProcessSaveDTOS.add(securityCheckProcessSaveDTO);
        }
        R<SecurityCheckRecord> securityCheckRecordR = securityCheckRecordApi.saveList(saveDTOS);
        if(securityCheckRecordR.getIsSuccess()){
            securityCheckProcessApi.saveList(securityCheckProcessSaveDTOS);
        }
        return securityCheckRecordR;

    }
    /*
    * 审核
    * */
    @Override
    public R<SecurityCheckRecord> approvaled(SecurityCheckRecordUpdateDTO params) {
        params.setReviewUserId(BaseContextHandler.getUserId());
        params.setReviewUserName(BaseContextHandler.getTenantName());
        params.setReviewTime(LocalDateTime.now());
        params.setDataStatus(SecurityCheckRecordStatusEnum.TO_BE_ASSIGNED.getCode());
        SecurityCheckProcessSaveDTO securityCheckProcessSaveDTO=new SecurityCheckProcessSaveDTO();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getReviewUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getReviewUserName());
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        securityCheckProcessSaveDTO.setProcessTime(params.getReviewTime());
        if(! StringUtils.isEmpty(params.getReviewObjection())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getReviewObjection());
        }
        R<SecurityCheckRecord> update = securityCheckRecordApi.update(params);
        if(update.getIsSuccess()){
            securityCheckProcessApi.save(securityCheckProcessSaveDTO);
        }
        return update;
    }
    /*驳回
    * */
    @Override
    public R<SecurityCheckRecord> reject(SecurityCheckRecordUpdateDTO params) {
        params.setRejectUserID(BaseContextHandler.getUserId());
        params.setRejectUserName(BaseContextHandler.getTenantName());
        params.setRejectTime(LocalDateTime.now());
        params.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_INITIATE_REVIEW.getCode());
        return securityCheckRecordApi.update(params);
    }
    /*结束
    * */
    @Override
    public R<SecurityCheckRecord> endSecurityCheckRecord(SecurityCheckRecordUpdateDTO params) {
        params.setDataStatus(SecurityCheckRecordStatusEnum.TERMINATION.getCode());
        params.setStopTime(LocalDateTime.now());
        params.setStopUserId(BaseContextHandler.getUserId());
        params.setStopUserName(BaseContextHandler.getName());
        SecurityCheckProcessSaveDTO securityCheckProcessSaveDTO=new SecurityCheckProcessSaveDTO();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getStopUserId()));
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessUserName(params.getStopUserName());
        securityCheckProcessSaveDTO.setProcessState(params.getDataStatus());
        if(! StringUtils.isEmpty(params.getStopDesc())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getStopDesc());
        }
        R<SecurityCheckRecord> update = securityCheckRecordApi.update(params);
        if(update.getIsSuccess()){
            securityCheckProcessApi.save(securityCheckProcessSaveDTO);
        }
        return update;
    }
    /*
    *
    * 派单
    * */
    @Override
    public R<SecurityCheckRecord> leaflet(SendOrderVo params) {
        SecurityCheckRecordUpdateDTO securityCheckRecordUpdateDTO=new SecurityCheckRecordUpdateDTO();
      /*  securityCheckRecordUpdateDTO.setDataStatus(SecurityCheckRecordStatusEnum.WAITING_FOR_CHECK.getCode());*/
        securityCheckRecordUpdateDTO.setOrderState(OrderStateEnum.WAIT_RECEIVE.getCode());
        //派工人
        securityCheckRecordUpdateDTO.setDistributionTime(LocalDateTime.now());
        securityCheckRecordUpdateDTO.setDistributionUserId(BaseContextHandler.getUserId());
        securityCheckRecordUpdateDTO.setDistributionUserName(BaseContextHandler.getName());
        //安检工单
        OrderRecordSaveDTO orderRecord=new OrderRecordSaveDTO();
        orderRecord.setBusinessType(OrderTypeEnum.SECURITY_ORDER.getCode());
        orderRecord.setBusinessOrderNumber(params.getScNo());
        orderRecord.setUrgency(params.getUrgency());//从数据字点里拿
        orderRecord.setWorkOrderDesc(params.getWorkOrderDesc());
        orderRecord.setWorkOrderNo(BizCodeNewUtil.genWorkOrderCode(BizFCode.YW, BizOCode.AJ));
        orderRecord.setOrderStatus(OrderStateEnum.WAIT_RECEIVE.getCode());

        //流程信息
        SecurityCheckProcessSaveDTO securityCheckProcessSaveDTO=new SecurityCheckProcessSaveDTO();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getDistributionUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getDistributionUserName());
        securityCheckProcessSaveDTO.setProcessDesc(params.getWorkOrderDesc());
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessTime(params.getDistributionTime());
        securityCheckProcessSaveDTO.setProcessState(securityCheckRecordUpdateDTO.getDataStatus());
        R<SecurityCheckRecord> update = securityCheckRecordApi.update(securityCheckRecordUpdateDTO);
        if(update.getIsSuccess()){
            securityCheckProcessApi.save(securityCheckProcessSaveDTO);
            orderRecordApi.save(orderRecord);
        }
        return update;
    }
    /*
    * 接单
    * */
    @Override
    public R<SecurityCheckRecord> giveOrder(SendOrderVo params) {
        SecurityCheckRecordUpdateDTO securityCheckRecordUpdateDTO=new SecurityCheckRecordUpdateDTO();
        //实际安检人
        securityCheckRecordUpdateDTO.setSecurityCheckUserId(params.getSecurityCheckUserId());
        securityCheckRecordUpdateDTO.setSecurityCheckUserName(params.getSecurityCheckUserName());
        securityCheckRecordUpdateDTO.setSecurityCheckTime(params.getSecurityCheckTime());
        securityCheckRecordUpdateDTO.setSecurityCheckContent(params.getSecurityCheckContent());
        securityCheckRecordUpdateDTO.setDataStatus(SecurityCheckRecordStatusEnum.WAITING_FOR_CHECK.getCode());
        securityCheckRecordUpdateDTO.setOrderState(OrderStateEnum.RECEIVE.getCode());
        //安检工单
        OrderRecordUpdateDTO orderRecord=new OrderRecordUpdateDTO();
        //接单人
        orderRecord.setReceiveUserId(params.getSecurityCheckUserId());
        orderRecord.setReceiveUserName(params.getSecurityCheckUserName());
        orderRecord.setReceiveTime(params.getSecurityCheckTime());
        orderRecord.setReceiveUserMobile(params.getReceiveUserMobile());
        orderRecord.setOrderStatus(OrderStateEnum.RECEIVE.getCode());
        //流程信息
        SecurityCheckProcessSaveDTO securityCheckProcessSaveDTO=new SecurityCheckProcessSaveDTO();
        securityCheckProcessSaveDTO.setProcessUserId(String.valueOf(params.getSecurityCheckUserId()));
        securityCheckProcessSaveDTO.setProcessUserName(params.getSecurityCheckUserName());
        if(! StringUtils.isEmpty(params.getSecurityCheckContent())){
            securityCheckProcessSaveDTO.setProcessDesc(params.getSecurityCheckContent());
        }
        securityCheckProcessSaveDTO.setSecurityCheckNunber(params.getScNo());
        securityCheckProcessSaveDTO.setProcessTime(params.getSecurityCheckTime());
        securityCheckProcessSaveDTO.setProcessState(securityCheckRecordUpdateDTO.getDataStatus());
        R<SecurityCheckRecord> update = securityCheckRecordApi.update(securityCheckRecordUpdateDTO);
        if(update.getIsSuccess()){
            securityCheckProcessApi.save(securityCheckProcessSaveDTO);
            orderRecordApi.update(orderRecord);
        }
        return update;
    }
    /*
    * 确认接单
    * */
    @Override
    public R<OrderRecord> confirmOrder(OrderRecordUpdateDTO params) {
        return orderRecordApi.update(params);
    }
    /*
    * 拒单
    * */
    @Override
    public R<SecurityCheckRecord> refuseOrder(SendOrderVo params) {
        //安检工单
        OrderRecordUpdateDTO orderRecord=new OrderRecordUpdateDTO();
        orderRecord.setInvalidDesc(params.getInvalidDesc());
        orderRecord.setOrderStatus(OrderStateEnum.TERMINATION.getCode());
        //安检计划
        SecurityCheckRecordUpdateDTO securityCheckRecordUpdateDTO=new SecurityCheckRecordUpdateDTO();
     /*   securityCheckRecordUpdateDTO.setDataStatus(SecurityCheckRecordStatusEnum.TO_BE_ASSIGNED.getCode());*/
        securityCheckRecordUpdateDTO.setOrderState(OrderStateEnum.TERMINATION.getCode());
        R<SecurityCheckRecord> update = securityCheckRecordApi.update(securityCheckRecordUpdateDTO);
        if(update.getIsSuccess()){
            orderRecordApi.update(orderRecord);
        }
        return update;
    }
    /*转单
    * */
    @Override
    public R<SecurityCheckRecord> transferOrder(SendOrderVo params) {
        //安检工单
        OrderRecordUpdateDTO orderRecord=new OrderRecordUpdateDTO();
        orderRecord.setInvalidDesc(params.getInvalidDesc());
        orderRecord.setOrderStatus(OrderStateEnum.TERMINATION.getCode());
        orderRecordApi.update(orderRecord);
        return leaflet(params);
    }
}

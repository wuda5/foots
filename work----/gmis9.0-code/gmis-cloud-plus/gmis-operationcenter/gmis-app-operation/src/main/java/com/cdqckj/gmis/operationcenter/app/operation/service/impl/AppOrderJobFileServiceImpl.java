package com.cdqckj.gmis.operationcenter.app.operation.service.impl;

import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.enumeration.InstallStatus;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.vo.JobFileWorkerVo;

import com.cdqckj.gmis.operationcenter.app.operation.service.AppOrderJobFileService;
import com.cdqckj.gmis.securityed.SecurityCheckProcessApi;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Log4j2
public class AppOrderJobFileServiceImpl implements AppOrderJobFileService {


    @Autowired
    public OrderJobFileBizApi jobFileBizApi;

    @Autowired
    private OrderRecordApi orderRecordApi;

    @Autowired
    private InstallRecordApi installRecordApi;
    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;
    @Autowired
    private AppOrderJobFileService appOrderJobFileService;
    @Autowired
    private SecurityCheckProcessApi securityCheckProcessApi;


    @Override
    public void saveWorkeMessage(JobFileWorkerVo vo) {
        // 一：不论是 录入 踏勘，施工，验收 的工单资料，这个工单都已完结--设置 结束此单,并设置备注
        OrderRecord orderRecord = orderRecordApi.getById(vo.getJobId()).getData();
        if (Objects.equals(OrderStateEnum.COMPLETE.getCode(),orderRecord.getOrderStatus())){
            // 如果查询到该工单状态已经完结，证明是修改，需要先删除原来所有的 工单附件后，重新保存
            jobFileBizApi.delete(new OrderJobFile().setJobId(vo.getJobId()));
        }
        jobFileBizApi.saveList(vo.getFileList());
        // 结单
        orderRecordApi.completeOrder(orderRecord.setRemark(vo.getRemark()));//???WHY id 没了
        // ---》 根据工单大的业务类型具体分类逻辑--》 99.保存工单对应材料
        if (Objects.equals(vo.getOrderTypeEnum(), OrderTypeEnum.SECURITY_ORDER.getCode())){
            //安检的 流程记录
            SecurityCheckProcessSaveDTO process = new SecurityCheckProcessSaveDTO();

            process.setSecurityCheckNunber(orderRecord.getBusinessOrderNumber())// 关联业务编号
                    .setProcessState(6)
//                .setProcessState("录入安检资料")
                    .setProcessTime(LocalDateTime.now())
                    .setProcessUserName(BaseContextHandler.getName())
                    .setProcessUserId(BaseContextHandler.getUserId()+"");

//        // 二：新增记录流程
            securityCheckProcessApi.save(process);
            return;
        }
        if (Objects.equals(vo.getOrderTypeEnum(), OrderTypeEnum.SECURITY_ORDER.getCode())){

        }
        // ?新增流程？，多次录入呢，如何避免存多条，光检查此报装记录是否由此流程不行哒（本来就可能几次同一xx）,?流程记录的状态
        // 每次录入工单资料，就生成条流程记录
        InstallRecord install = installRecordApi.getByInstallNumber(orderRecord.getBusinessOrderNumber()).getData();

        InstallOrderType orderType = vo.getWorkOrderType();
        // 流程记录
        InstallProcessRecordSaveDTO process = new InstallProcessRecordSaveDTO();

        String userName = BaseContextHandler.getName();
        Long userId = BaseContextHandler.getUserId();

        process.setProcessTime(LocalDateTime.now())
                .setProcessUserName(userName)
                .setProcessUserId(userId)
                .setInstallNumber(orderRecord.getBusinessOrderNumber());// 关联业务记录编号
        switch (orderType) {
            case SURVEY:
                // 1.如是勘察工单，设置 踏勘人信息，流程记录的状态设置为：待踏勘（前端显示为踏勘）
                process.setProcessState(InstallStatus.WAITE_SURVEY.getCode());
                install.setStepOnUserId(userId)
                        .setStepOnUserName(userName)
                        .setStepOnTime(LocalDateTime.now());
                break;
            case INSTALL:
                //2. 如是 安装工单，设置 施工人，施工时间，流程记录的状态设置为：待施工（前端显示为施工）
                process.setProcessState(InstallStatus.WAITE_INSTALL.getCode());
                install.setInstallUserId(userId)
                        .setInstallUserName(userName)
                        .setInstallTime(LocalDateTime.now());
                break;
            case ACCEPT:
                //3. 如是 验收 工单，设置为设置 验收人 信息，流程记录的状态设置为：待验收（前端显示为验收）
                process.setProcessState(InstallStatus.WAITE_ACCEPT.getCode());
                install.setAcceptUser(userId)
                        .setAcceptUserName(userName)
                        .setAcceptTime(LocalDateTime.now());
                break;
            default:
                break;
        }
//        // 二：新增记录流程
        installProcessRecordApi.save(process);
        // 修改报装记录--相关人 信息
        installRecordApi.update(BeanPlusUtil.toBean(install, InstallRecordUpdateDTO.class));
    }
}

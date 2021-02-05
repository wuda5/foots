package com.cdqckj.gmis.operationcenter.app.securityed.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordSaveDTO;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.enumeration.InstallStatus;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.vo.JobFileWorkerVo;
import com.cdqckj.gmis.securityed.SecurityCheckProcessApi;
import com.cdqckj.gmis.securityed.dto.SecurityCheckProcessSaveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 前端控制器
 * 报装材料
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appCheck/file")
@Api(value = "/appCheck/file", tags = "安检--工单现场材料")
//@PreAuth(replace = "gasInstallFile:")
public class AppCheckOrderJobFileController {

    @Autowired
    public OrderJobFileBizApi jobFileBizApi;

    @Autowired
    private OrderRecordApi orderRecordApi;

    @Autowired
    private InstallRecordApi installRecordApi;

    @Autowired
    private SecurityCheckProcessApi securityCheckProcessApi;

    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;


    /**
     * 保存 工单现场 材料信息
     * 1.会保存录入的资料附件
     * 2.增加 报装所派单过程对应的 踏勘，施工，验收 3个流程记录
     * 3.
     *
     * @param
     * @return
     */
    @ApiOperation(value = "保存 安检-》工单现场 材料信息")
    @PostMapping("/saveList")
    public R<Boolean> saveList(@RequestBody JobFileWorkerVo vo) {
        // 一： 录入  的工单资料，这个工单都已完结--设置 结束此单,并设置备注
        OrderRecord orderRecord = orderRecordApi.getById(vo.getJobId()).getData();
        if (Objects.equals(OrderStateEnum.COMPLETE.getCode(),orderRecord.getOrderStatus())){
            // 如果查询到该工单状态已经完结，证明是修改，需要先删除原来所有的 工单附件后，重新保存
            jobFileBizApi.delete(new OrderJobFile().setJobId(vo.getJobId()));
        }
        jobFileBizApi.saveList(vo.getFileList());
        // 结单,并设置 工单描述
        orderRecordApi.completeOrder(orderRecord.setRemark(vo.getRemark()));//???WHY id 没了
        // 99.保存工单对应材料
        // ?新增流程？，多次录入呢，如何避免存多条，光检查此报装记录是否由此流程不行哒（本来就可能几次同一xx）,?流程记录的状态
        // 每次录入工单资料，就生成条流程记录


        // 流程记录
        SecurityCheckProcessSaveDTO process = new SecurityCheckProcessSaveDTO();

        process.setSecurityCheckNunber(orderRecord.getBusinessOrderNumber())// 关联业务编号
                .setProcessState(6)
//                .setProcessState("录入安检资料")
                .setProcessTime(LocalDateTime.now())
                .setProcessUserName(BaseContextHandler.getName())
                .setProcessUserId(BaseContextHandler.getUserId()+"");

//        // 二：新增记录流程
        securityCheckProcessApi.save(process);

        return R.success();
    }


    /**
     * 根据 工单 id 获取对应工单的 材料信息
     *
     * @return
     */
    @ApiOperation(value = "根据 工单 id 获取对应工单的 材料信息")
    @GetMapping("/findFilesByJobId")
    public R<List<OrderJobFile>> findFilesByInstallNumber(@RequestParam(name = "jobId") Long jobId) {
        OrderJobFile file = new OrderJobFile();
        file.setJobId(jobId);
        return jobFileBizApi.query(file);
    }
//    /**
//     * 根据 工单 id 获取对应工单的 材料信息
//     *
//     * @return
//     */
//    @ApiOperation(value = "根据 工单 ids 获取对应工单的 材料信息")
//    @GetMapping("/findFilesByJobId")
//    public R<Map<String,List<OrderJobFile>>> findFilesBy(@RequestBody List<Long> jobIds) {
//        HashMap<> map = new HashMap<>();
//
//        OrderJobFile file = new OrderJobFile();
//        file.setJobId(jobId);
//        return jobFileBizApi.query(file);
//    }

}

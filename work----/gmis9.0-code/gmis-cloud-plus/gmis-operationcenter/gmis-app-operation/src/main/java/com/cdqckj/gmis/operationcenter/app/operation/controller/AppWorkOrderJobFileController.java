package com.cdqckj.gmis.operationcenter.app.operation.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.vo.JobFileWorkerVo;

import com.cdqckj.gmis.operationcenter.app.operation.service.AppOrderJobFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * operation
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appOperation/jobFile")
@Api(value = "appWorkOrder", tags = "运维--工单现场材料")
public class AppWorkOrderJobFileController {

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

    /**
     * 保存 工单现场 材料信息
     * 1.会保存录入的资料附件
     * 2.增加 报装所派单过程对应的 踏勘，施工，验收 3个流程记录
     * 3.
     *
     * @param
     * @return
     */
    @ApiOperation(value = "保存 报装，工单现场(踏勘，安装，验收 资料) 材料信息")
    @PostMapping("/saveList")
    public R<Boolean> saveList(@RequestBody JobFileWorkerVo vo) {
        appOrderJobFileService.saveWorkeMessage(vo);
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
//    @ApiOperation(value = "根据 工单 编号code 获取对应工单的 材料信息")
//    @GetMapping("/findFilesByJobCode")
//    public R<List<OrderJobFile>> findFilesByJobCode(@RequestParam(name = "jobCode") String jobCode) {
//        OrderJobFile file = new OrderJobFile();
//        file.(jobCode);
//        return jobFileBizApi.query(file);
//    }


}

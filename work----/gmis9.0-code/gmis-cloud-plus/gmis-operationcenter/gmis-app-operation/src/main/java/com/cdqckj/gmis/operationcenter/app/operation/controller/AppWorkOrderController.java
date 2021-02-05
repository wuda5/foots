package com.cdqckj.gmis.operationcenter.app.operation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.vo.WorkOrderOperCommonVo;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operationcenter.app.operation.dto.PageSelfOrderJobDto;
import com.cdqckj.gmis.operationcenter.app.operation.dto.SelfOrderJobDto;
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
 * 报装材料
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appOperation/job")
@Api(value = "appOperation", tags = "运维--工单")
public class AppWorkOrderController {

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
     * app 个人 工单分页查询
     * @param
     * @return
     */
    @PostMapping(value = "/pageOrder")
    public R<Page<OrderRecord>> pageOrder(@RequestBody @Validated PageSelfOrderJobDto dto) {
//
        PageParams<OrderRecordPageDTO> scopeParams = new PageParams<OrderRecordPageDTO>();
        scopeParams.setSize(dto.getPageSize());
        scopeParams.setCurrent(dto.getPageNo());

        OrderRecordPageDTO pa = new OrderRecordPageDTO()
                .setBusinessType(dto.getOrderTypeEnum())// 工单大的业务类型查询
                .setReceiveUserId(dto.getReceiveUserId())// 接单人
                .setOrderStatus(dto.getOrderState());
        if (dto.getInstallOrderType()!=null){
            pa.setWorkOrderType(dto.getInstallOrderType().getCode());
        }

        scopeParams.setModel(pa);
        // 1.查询工单
        R<Page<OrderRecord>> re = orderRecordApi.page(scopeParams);

        return re;
    }


    /**
     *根据 业务订单编号+接单人(报装编号installNumber or 安检计划编号scNo) 查询所有对应的 工单信息
     *
     * @return
     */
    @ApiOperation(value = "根据 业务订单编号+接单人(报装编号installNumber or 安检计划编号scNo) 查询所有对应的 工单信息")
    @PostMapping("/findSelfOrder")
    public R<List<OrderRecord>> findFilesByInstallNumber(@RequestBody SelfOrderJobDto dto) {
        OrderRecord job = new OrderRecord();
        job.setBusinessOrderNumber(dto.getBusinessOrderNumber())
                .setReceiveUserId(dto.getReceiveUserId());

        return orderRecordApi.query(job);
    }


    /**
     * 安检--> 拒单
     */
    @ApiOperation(value = "安检--> 拒单", notes = " 安检--> 拒单")
    @PostMapping("/rejectOrder")
    public R<Boolean> rejectOrder(@RequestBody @Validated WorkOrderOperCommonVo data) {

//        InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(data.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));
//        OrderRecord order = Optional.ofNullable(orderRecordService.getById(data.getWorkOrderId())).orElseThrow(() -> new BizException("无此工单"));
//        // 设置该 xx 工单的状态为已接单，接收人，
        OrderRecord orderRecord = orderRecordApi.getById(data.getWorkOrderId()).getData();

        R<Boolean> r = orderRecordApi.refuseOrder(orderRecord.setRejectDesc(data.getRemark()));

        //@TODO 安检计划 对应的工单状态 和 安检状态 修改为xxx
//        InstallRecord install = securityCheckRecordApi.query(orderRecord.getBusinessOrderNumber()).getData();
//        SecurityCheckRecordUpdateDTO scRecord=new SecurityCheckRecordUpdateDTO();
//        scRecord.setId()
//        scRecord.setOrderState(OrderStateEnum.WAIT_RECEIVE.getCode());
//
//        R<SecurityCheckRecord> update = securityCheckRecordApi.update(scRecord);

        return r;
    }
//    /**
//     * 设置预约时间
//     */
//    @ApiOperation(value = "设置预约时间", notes = " 设置预约时间")
//    @PostMapping("/setAppointTime")
//    public R<Boolean> rejectOrder(@RequestBody @Validated WorkOrderAppointVo data) {
//        //        // 设置该 xx 工单的状态为已接单，接收人，
//        OrderRecord orderRecord = orderRecordApi.getById(data.getWorkOrderId()).getData();
//        R<OrderRecord> update = orderRecordApi.update(BeanPlusUtil.toBean(orderRecord, OrderRecordUpdateDTO.class)
//                .setBookOperationTime(data.getBookOperationTime()));
//
//        return R.success(true);
//    }

}

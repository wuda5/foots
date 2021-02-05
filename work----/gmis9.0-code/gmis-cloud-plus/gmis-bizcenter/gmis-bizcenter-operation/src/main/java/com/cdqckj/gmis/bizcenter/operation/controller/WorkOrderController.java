package com.cdqckj.gmis.bizcenter.operation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.vo.CheckOrderVo;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.vo.WorkOrderOperCommonVo;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.vo.DispatchWorkerVo;
import com.cdqckj.gmis.securityed.SecurityCheckRecordApi;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
@RequestMapping("/operation/job")
@Api(value = "appOperation", tags = "运维--工单")
public class WorkOrderController {

    @Autowired
    public OrderJobFileBizApi jobFileBizApi;

    @Autowired
    private OrderRecordApi orderRecordApi;

    @Autowired
    private InstallRecordApi installRecordApi;
    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;
//    @Autowired
//    private AppOrderJobFileService appOrderJobFileService;

    @Autowired
    private SecurityCheckRecordApi securityCheckRecordApi;

    /**
     * 分页查询安检工单列表
     * @param params
     * @return
     */
    @ApiOperation(value = "查询安检工单列表")
    @PostMapping(value = "/listOrder")
    R<Page<OrderRecord>> pageOrder(@RequestBody @Validated PageParams<OrderRecordPageDTO> params){
        return  orderRecordApi.page(params);
    }


//    /**
//     * 踏勘，施工，验收--> 接单
//     */
//    @ApiOperation(value = " 踏勘，施工，验收 接单", notes = " 踏勘，施工，验收 接单")
//    @PostMapping("/receiveOrder")
//    public R<Boolean> serveyReceiveOrder(@RequestBody @Validated WorkOrderOperCommonVo data) {
//
////        InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(data.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));
////        OrderRecord order = Optional.ofNullable(orderRecordService.getById(data.getWorkOrderId())).orElseThrow(() -> new BizException("无此工单"));
////        // 设置该 xx 工单的状态为已接单，接收人，
//        OrderRecord orderRecord = orderRecordApi.getById(data.getWorkOrderId()).getData();
//
//        R<Boolean> r = orderRecordApi.giveOrder(orderRecord);
//
//        return r;
//    }

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

    /***派工---------------------------------派工-------------------**/
    /**
     * 工单派工（查勘，施工，验收 或者 安检）
     */
    @ApiOperation(value = "报装--》工单派工", notes = "工单派工")
    @PostMapping(value = "/dispatch")
    public R<Boolean> dispatchSave(@RequestBody DispatchWorkerVo vo) {

        OrderRecordSaveDTO saveDTO = BeanPlusUtil.toBean(vo, OrderRecordSaveDTO.class);
        /**
         * 1报装工单，2安检工单，3运维工单，4客服工单 类工单
         */
        saveDTO.setBusinessType(OrderTypeEnum.INSTALL_ORDER.getCode());
        // 查询此工单xxx
        orderRecordApi.save(saveDTO);
//        // 注意：如果是派单类型是 踏勘工单，还需要再 修改报装表的 状态 为 待踏勘
//        InstallRecord install = installRecordApi.getByInstallNumber(vo.getBusinessOrderNumber()).getData();
//        if (Objects.equals(vo.getWorkOrderType(), InstallOrderType.SERVEY.name())) {
//            installRecordApi.update(BeanPlusUtil.toBean(install, InstallRecordUpdateDTO.class).setDataStatus(InstallStatus.WAITE_SURVEY.getCode()));
//        }
        return R.success(true);
    }


    /**
     * 派工
     * @param params
     * @return
     */
    @ApiOperation(value = "安检--》派工")
    @PostMapping(value = "/leaflet")
    R<Boolean> leafletSecurityCheckRecord(@RequestBody @Validated CheckOrderVo params){
        SecurityCheckRecord securityCheckRecord= params.getSecurityCheckRecord();
        /*securityCheckRecord.setOrderState(OrderStateEnum.WAIT_RECEIVE.getCode());*/
        securityCheckRecord.setOrderState(OrderStateEnum.RECEIVE.getCode());
        securityCheckRecord.setSecurityCheckContent(params.getOrderRecord().getWorkOrderDesc());
        OrderRecordUpdateDTO orderRecord=params.getOrderRecord();
        orderRecord.setBusinessOrderNumber(securityCheckRecord.getScNo());
        orderRecord.setBusinessType(OrderTypeEnum.SECURITY_ORDER.getCode());
        /*securityCheckRecord.setPlanSecurityCheckUserId(orderRecord.getReceiveUserId());
        securityCheckRecord.setPlanSecurityCheckUserName(orderRecord.getReceiveUserName());
        securityCheckRecord.setPlanSecurityCheckTime(orderRecord.getReceiveTime());*/
        //新加
        securityCheckRecord.setSecurityCheckUserId(orderRecord.getReceiveUserId());
        securityCheckRecord.setSecurityCheckUserName(orderRecord.getReceiveUserName());
        securityCheckRecord.setSecurityCheckTime(orderRecord.getReceiveTime());
        /*securityCheckRecord.setSecurityCheckResult(0);*/
        R<Integer> integerR = securityCheckRecordApi.leafletSecurityCheckRecord(securityCheckRecord);
        if(integerR.getData()==1){
            OrderRecordSaveDTO orderRecordSaveDTO= BeanPlusUtil.toBean(orderRecord,OrderRecordSaveDTO.class);
            orderRecordApi.save(orderRecordSaveDTO);
            return R.success(true);
        }
        return R.fail("派工失败");
    }

}

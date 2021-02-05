package com.cdqckj.gmis.bizcenter.operation.maintain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operation.MaintenanceBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.vo.DispatchWorkerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Validated
@RestController
@RequestMapping("/operation")
@Api(value = "operation", tags = "工单")
public class OrderRecordController {

//    @Autowired
//    private OrderRecordApi orderRecordBizApi;
//    @Autowired
//    private MaintenanceBizApi maintenanceBizApi;
//    @Autowired
//    private InstallRecordApi installRecordApi;
//
//    /**
//     * 运行维护工单列表分页查询
//     *
//     * @param params
//     * @return
//     */
//    @ApiOperation(value = "工单列表分页列表查询")
//    @PostMapping(value = "/getOrderRecordPage")
//    @SysLog(value = "'工单列表分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
//    public R<Page<OrderRecord>> page(@RequestBody @Validated PageParams<OrderRecordPageDTO> params) {
//
//        return orderRecordBizApi.page(params);
//    }
//
//    /**
//     * 工单派工（查勘，施工，验收 或者 安检）
//     */
//    @ApiOperation(value = "工单派工（查勘，施工，验收或者 安检）", notes = "报装工单派工（查勘，施工，验收 或者 安检）")
//    @PostMapping("/dispatch")
//    public R<OrderRecord> serveyDispatch(@RequestBody @Validated DispatchWorkerVo data) {
//        //1. 报装类工单
//        if (Objects.equals(OrderTypeEnum.INSTALL_ORDER, data.getOrderTypeEnum())) {
//            //        InstallRecord record = this.baseService.getOne(Wraps.<InstallRecord>lbQ().eq(InstallRecord::getInstallNumber, data.getBusinessOrderNumber()));
////        BizAssert.notNull(record, "无对应报装记录");
////        //1. 校验此报装受理记录 所处是否为正确可以派单状态（带查看）及 此时有无对应的活动的派工记录
////        BizAssert.equals(record.getDataStatus(), InstallStatus.WAITE_SURVEY.getCode(), "此报装受理记录无法判单");
////        // 此此报装受理记录 所对应的所有 查勘（或者其他类型）工单列表里，只要有一个处于：待接单或 接单中 就不能给派
////        InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(data.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));
//
////        List<OrderRecord> list = orderRecordService.list(Wraps.<OrderRecord>lbQ()
////                .eq(OrderRecord::getWorkOrderType, installOrderType.name()) // 哪一类工单
////                .in(OrderRecord::getOrderStatus, OrderStateEnum.WAIT_RECEIVE.getCode(), OrderStateEnum.WAIT_RECEIVE.getCode()));
////        BizAssert.mustEmpty(list, "此报装记录 有对应的活动的派工记录，此时无法派此类型工单");
//
//        }
//
//        // 2.安检类工单
//        if (Objects.equals(OrderTypeEnum.SECURITY_ORDER, data.getOrderTypeEnum())) {
//            // 校验
//        }
//
//
//        // 2.指明此工单是 报装工单-查勘or 其他 工单,状态是待接单,关联报装记录
//        OrderRecordSaveDTO serveryOrder = new OrderRecordSaveDTO();
//
//        serveryOrder.setBusinessType(data.getOrderTypeEnum().getCode())// 哪种大类型的工单
//                .setWorkOrderType(data.getWorkOrderType()) // 报装工单，则指明具体类型之一
//                .setOrderStatus(OrderStateEnum.WAIT_RECEIVE.getCode())
//                .setReceiveUserId(data.getReceiveUserId())// 所派人
//                .setReviewTime(LocalDateTime.now())
//                .setBusinessOrderNumber(data.getBusinessOrderNumber());
//
//        R<OrderRecord> r = orderRecordBizApi.save(serveryOrder);
//
//        return r;
//    }


}

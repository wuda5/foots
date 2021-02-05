package com.cdqckj.gmis.installed.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.installed.vo.QuerySelfOrderVo;
import com.cdqckj.gmis.installed.vo.WorkOrderOperCommonVo;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.service.OrderRecordService;
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
import java.util.Optional;


/**
 * <p>
 * 前端控制器
 * 报装工单记录
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installOrderRecord")
@Api(value = "InstallOrderRecord", tags = "报装相关的工单类型 操作")
public class InstallOrderRecordController  {

    @Autowired
    OrderRecordService orderRecordService;

//    /**
//     * 踏勘，施工，验收--> 接单
//     */
//    @ApiOperation(value = " 踏勘，施工，验收 接单", notes = " 踏勘，施工，验收 接单")
//    @PostMapping("/receiveOrder")
//    public R<Boolean> serveyReceiveOrder(@RequestBody @Validated WorkOrderOperCommonVo data) {
//
//        InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(data.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));
//
//        OrderRecord order = Optional.ofNullable(orderRecordService.getById(data.getWorkOrderId())).orElseThrow(() -> new BizException("无此工单"));
//        // 设置该 xx 工单的状态为已接单，接收人，
//        order.setOrderStatus(OrderStateEnum.RECEIVE.getCode())
//                .setReceiveTime(LocalDateTime.now())
//                .setReceiveUserName(BaseContextHandler.getName())
//                .setReceiveUserId(BaseContextHandler.getUserId());
//
//        boolean b = orderRecordService.updateById(order);
//
//        return R.success(b);
//    }

    /**
     * 分页条件查询(我的)--报装某一种类相关工单
     * @return
     */
    @ApiOperation(value = "分页条件查询(我的)工单")
    @PostMapping("/findPage")
    R<IPage<OrderRecord>> findSelfOrders(@RequestBody @Validated PageParams<QuerySelfOrderVo> pageQuery){

        QuerySelfOrderVo qy = pageQuery.getModel();
        LbqWrapper<OrderRecord> wp = Wraps.<OrderRecord>lbQ().eq(OrderRecord::getReceiveUserId, qy.getReceiveUserId())
                .eq(OrderRecord::getBusinessType, OrderTypeEnum.INSTALL_ORDER.getCode())
                .ge(OrderRecord::getCreateTime, qy.getStartTime())
                .le(OrderRecord::getCreateTime, qy.getEndTime());

        IPage page = orderRecordService.page(pageQuery.getPage(), wp);
        return R.success(page);
    }

}

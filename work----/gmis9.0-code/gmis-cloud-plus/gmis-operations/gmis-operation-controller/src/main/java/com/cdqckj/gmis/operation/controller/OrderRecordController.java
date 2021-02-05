package com.cdqckj.gmis.operation.controller;

import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.service.OrderRecordService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 客户工单记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/orderRecord")
@Api(value = "OrderRecord", tags = "客户工单记录")
@PreAuth(replace = "orderRecord:")
public class OrderRecordController extends SuperController<OrderRecordService, Long, OrderRecord, OrderRecordPageDTO, OrderRecordSaveDTO, OrderRecordUpdateDTO> {


    @Override
    @CodeNotLost
    public R<OrderRecord> handlerSave(OrderRecordSaveDTO model) {

        List<OrderRecord> list = this.baseService.list(Wraps.<OrderRecord>lbQ()
                .eq(OrderRecord::getBusinessOrderNumber,model.getBusinessOrderNumber())
                .eq(OrderRecord::getBusinessType, model.getBusinessType()) // 哪一大类 工单
                .eq(OrderRecord::getWorkOrderType, model.getWorkOrderType()) // 哪一小 类工单
                .in(OrderRecord::getOrderStatus, OrderStateEnum.WAIT_RECEIVE.getCode(), OrderStateEnum.RECEIVE.getCode()));
        BizAssert.mustEmpty(list, "此报装记录 有对应的活动的派工记录，此时无法派此类型工单");

        // 2.指明此工单是 报装工单-查勘or 其他 工单,状态是待接单,关联报装(或者安检计划)记录

        OrderRecord orderRecord = BeanPlusUtil.toBean(model, OrderRecord.class);

        // 构造工单编号
        String workOrderNo = null;

        if (Objects.equals(OrderTypeEnum.SECURITY_ORDER.getCode(), model.getBusinessType())) {
            // 安检工单 编号
            //workOrderNo = BizCodeUtil.genTaskFlowCode(BizFCode.BZ, BizCodeUtil.TASK_INSTALL);
            workOrderNo = BizCodeNewUtil.getInstallOrderRecordCode();
        }
        if (Objects.equals(OrderTypeEnum.INSTALL_ORDER.getCode(), model.getBusinessType())) {
            // 构造具体哪一类报装工单 类型的编号
            InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(model.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));

            switch (installOrderType) {
                case SURVEY:
                    //workOrderNo = BizCodeUtil.genTaskFlowCode(BizFCode.BZ, BizCodeUtil.ORDER_INSTALL_SERVEY);
                    workOrderNo = BizCodeNewUtil.getInstallOrderRecordCode();
                    break;
                case INSTALL:
                    //workOrderNo = BizCodeUtil.genTaskFlowCode(BizFCode.BZ, BizCodeUtil.ORDER_INSTALL_SG);
                    workOrderNo = BizCodeNewUtil.getInstallOrderRecordCode();
                    break;
                case ACCEPT:
                    //workOrderNo = BizCodeUtil.genTaskFlowCode(BizFCode.BZ, BizCodeUtil.ORDER_INSTALL_SG);
                    workOrderNo = BizCodeNewUtil.getInstallOrderRecordCode();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: ");
            }
        }
        orderRecord
                .setOrderStatus(OrderStateEnum.RECEIVE.getCode())//默认接单了
                .setReceiveTime(LocalDateTime.now())
                .setWorkOrderNo(workOrderNo);

        this.baseService.save(orderRecord);// 自动会设置相关公司人
        return success(orderRecord);
    }
    @Override
    public R<List<OrderRecord>> query(@RequestBody OrderRecord data) {
        QueryWrap<OrderRecord> wrapper = Wraps.q(data);
        wrapper.orderByDesc("create_time");
        return success(getBaseService().list(wrapper));
    }

    /*@TODO 接单
    * */
    @PostMapping("/giveOrder")
    public R<Boolean> giveOrder(@RequestBody OrderRecord model){
        model.setOrderStatus(OrderStateEnum.RECEIVE.getCode());
        return R.success(baseService.updateById(model));
    }
    /*@TODO 拒单
    * */
    @PostMapping("/refuseOrder")
    public R<Boolean> refuseOrder(@RequestBody OrderRecord model){
        model.setOrderStatus(OrderStateEnum.REJECT.getCode());
        return R.success(baseService.updateById(model));
    }

    // 结束工单
    @PostMapping("/completeOrder")
    public R<Boolean> completeOrder(@RequestBody OrderRecord model) {
        model.setOrderStatus(OrderStateEnum.COMPLETE.getCode())
                .setEndJobTime(LocalDateTime.now())
                .setEndJobUserId(BaseContextHandler.getUserId())
                .setEndJobUserName(BaseContextHandler.getName());
        return R.success(baseService.updateById(model));
    }
}

package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderUpdateDTO;
import com.cdqckj.gmis.charges.dto.GenSceneOrderDTO;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
import com.cdqckj.gmis.charges.service.CustomerSceneChargeOrderService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 客户场景费用单
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerSceneChargeOrder")
@Api(value = "CustomerSceneChargeOrder", tags = "客户场景费用单")
//@PreAuth(replace = "customerSceneChargeOrder:")
public class CustomerSceneChargeOrderController extends SuperController<CustomerSceneChargeOrderService, Long, CustomerSceneChargeOrder, CustomerSceneChargeOrderPageDTO, CustomerSceneChargeOrderSaveDTO, CustomerSceneChargeOrderUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerSceneChargeOrder> customerSceneChargeOrderList = list.stream().map((map) -> {
            CustomerSceneChargeOrder customerSceneChargeOrder = CustomerSceneChargeOrder.builder().build();
            //TODO 请在这里完成转换
            return customerSceneChargeOrder;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerSceneChargeOrderList));
    }

    @ApiOperation(value = "业务单号撤回/取消费用单")
    @PostMapping("/cancelByBizCode")
    public R<Boolean> cancelByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode){
        CustomerSceneChargeOrder order=new CustomerSceneChargeOrder()
                .setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        boolean updateResult=baseService.update(order,new LbqWrapper<CustomerSceneChargeOrder>()
                .eq(CustomerSceneChargeOrder::getBusinessNo,bizCode)
                .eq(CustomerSceneChargeOrder::getTollItemScene,sceneCode)
        );
        return R.success(updateResult);
    }

    @ApiOperation(value = "业务单号查询费用单")
    @PostMapping("/queryByBizCode")
    public R<List<CustomerSceneChargeOrder>> queryByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode){
        List<CustomerSceneChargeOrder> result= baseService.list(
                new LbqWrapper<CustomerSceneChargeOrder>()
                        .eq(CustomerSceneChargeOrder::getBusinessNo,bizCode)
                        .eq(CustomerSceneChargeOrder::getTollItemScene,sceneCode)
                        .eq(CustomerSceneChargeOrder::getDataStatus,DataStatusEnum.NORMAL.getValue()));
        return R.success(result);
    }
    @ApiOperation(value = "生成业务场景收费单")
    @PostMapping("/creatSceneOrders")
    @CodeNotLost
    public R<Boolean> creatSceneOrders(@RequestBody @Validated GenSceneOrderDTO param){
        return R.success(baseService.creatSceneOrders(param.getGasMeterCode(),param.getCustomerCode(),param.getSceneCode(),param.getBizNoOrId(),param.getIsImportOpenAccount()));
    }
}

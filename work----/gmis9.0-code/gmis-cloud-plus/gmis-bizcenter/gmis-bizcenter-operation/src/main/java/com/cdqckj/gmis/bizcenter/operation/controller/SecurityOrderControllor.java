package com.cdqckj.gmis.bizcenter.operation.controller;

//@Slf4j
//@Validated
//@RestController
//@RequestMapping("/securityCheckOrder")
//@Api(value = "securityCheckOrder", tags = "安检工单")
public class SecurityOrderControllor {

//    @Autowired
//    private OrderRecordApi orderRecordApi;
//
//    @Autowired
//    private SecurityCheckRecordApi securityCheckRecordApi;
//
//    @Autowired
//    private OrderJobFileBizApi orderJobFileBizApi;
//    /**
//     * 分页查询安检工单列表
//     * @param params
//     * @return
//     */
//    @ApiOperation(value = "查询安检工单列表")
//    @PostMapping(value = "/listOrder")
//    R<Page<OrderRecord>> pageOrder(@RequestBody @Validated PageParams<OrderRecordPageDTO> params){
//        return  orderRecordApi.page(params);
//    }
//
//    @PostMapping("/saveOrderFile")
//    R<List<OrderJobFile>> saveList(@RequestBody List<OrderJobFileSaveDTO> saveDTO){
//       return orderJobFileBizApi.saveList(saveDTO);
//    }

//    /**
//     * 派工
//     * @param params
//     * @return
//     */
//    @ApiOperation(value = "派工")
//    @PostMapping(value = "/leaflet")
//    R<Boolean> leafletSecurityCheckRecord(@RequestBody @Validated CheckOrderVo params){
//        SecurityCheckRecord securityCheckRecord= params.getSecurityCheckRecord();
//        /*securityCheckRecord.setOrderState(OrderStateEnum.WAIT_RECEIVE.getCode());*/
//        securityCheckRecord.setOrderState(OrderStateEnum.RECEIVE.getCode());
//        securityCheckRecord.setSecurityCheckContent(params.getOrderRecord().getWorkOrderDesc());
//        OrderRecordUpdateDTO orderRecord=params.getOrderRecord();
//        orderRecord.setBusinessOrderNumber(securityCheckRecord.getScNo());
//        orderRecord.setBusinessType(OrderTypeEnum.SECURITY_ORDER.getCode());
//        /*securityCheckRecord.setPlanSecurityCheckUserId(orderRecord.getReceiveUserId());
//        securityCheckRecord.setPlanSecurityCheckUserName(orderRecord.getReceiveUserName());
//        securityCheckRecord.setPlanSecurityCheckTime(orderRecord.getReceiveTime());*/
//        //新加
//        securityCheckRecord.setSecurityCheckUserId(orderRecord.getReceiveUserId());
//        securityCheckRecord.setSecurityCheckUserName(orderRecord.getReceiveUserName());
//        securityCheckRecord.setSecurityCheckTime(orderRecord.getReceiveTime());
//
//        R<Integer> integerR = securityCheckRecordApi.leafletSecurityCheckRecord(securityCheckRecord);
//        if(integerR.getData()==1){
//            OrderRecordSaveDTO orderRecordSaveDTO= BeanPlusUtil.toBean(orderRecord,OrderRecordSaveDTO.class);
//            orderRecordApi.save(orderRecordSaveDTO);
//            return R.success(true);
//        }
//        return R.fail("派工失败");
//    }

//    /*
//     * 拒单接单
//     * @param params
//     * @return*/
//
//    @ApiOperation(value = "拒单")
//    @PostMapping(value = "/refuseOrder")
//    R<SecurityCheckRecord> refuseOrder(@RequestBody @Validated CheckOrderVo params){
//        //@TODO 安检计划
//        SecurityCheckRecordUpdateDTO securityCheckRecordUpdateDTO=new SecurityCheckRecordUpdateDTO();
//        securityCheckRecordUpdateDTO.setOrderState(OrderStateEnum.WAIT_RECEIVE.getCode());
//        R<SecurityCheckRecord> update = securityCheckRecordApi.update(securityCheckRecordUpdateDTO);
//        if(update.getIsSuccess()){
//            orderRecordApi.refuseOrder(BeanPlusUtil.toBean(params.getOrderRecord(),new OrderRecord().getClass()));
//        }
//        return update;
//    }
//
//    /*
//     * 转单
//     * @param params
//     * @return*/
//
//    @ApiOperation(value = "转单")
//    @PostMapping(value = "/transferOrder")
//    R<Boolean> transferOrder(@RequestBody @Validated CheckOrderVo params){
//        OrderRecordUpdateDTO orderRecord = params.getOrderRecord();
//        orderRecord.setOrderStatus(OrderStateEnum.TERMINATION.getCode());
//        orderRecordApi.update(orderRecord);
//        return leafletSecurityCheckRecord(params);
//    }

//    @ApiOperation(value = "结单")
//    @PostMapping(value = "/completeOrder")
//    R<Boolean> completeOrder(@RequestBody @Validated CheckOrderVo params){
//        SecurityCheckRecord securityCheckRecord =params.getSecurityCheckRecord();
//        securityCheckRecord.setOrderState(OrderStateEnum.COMPLETE.getCode());
//        R<Integer> integerR = securityCheckRecordApi.completeOrder(securityCheckRecord);
//        if(integerR.getIsSuccess()){
//            orderRecordApi.completeOrder(BeanPlusUtil.toBean(params.getOrderRecord(),new OrderRecord().getClass()));
//            return R.success(true);
//        }
//        return R.fail("完成接单失败");
//    }

    /*
    @ApiOperation(value = "接单")
    @PostMapping(value = "/giveOrder")
    R<Boolean> giveOrder(@RequestBody @Validated CheckOrderVo params){
        SecurityCheckRecord securityCheckRecord = params.getSecurityCheckRecord();
        OrderRecordSaveDTO orderRecord = params.getOrderRecord();
        securityCheckRecord.setSecurityCheckTime(orderRecord.getReceiveTime());
        securityCheckRecord.setOrderState(OrderStateEnum.RECEIVE.getCode());
        R<Integer> integerR = securityCheckRecordApi.giveOrder(securityCheckRecord);
        if(integerR.getData()>0){
            orderRecordApi.giveOrder(BeanPlusUtil.toBean(orderRecord,new OrderRecord().getClass()));
            return R.success(true);
        }
        return R.fail("接单失败");
    }
*/
}

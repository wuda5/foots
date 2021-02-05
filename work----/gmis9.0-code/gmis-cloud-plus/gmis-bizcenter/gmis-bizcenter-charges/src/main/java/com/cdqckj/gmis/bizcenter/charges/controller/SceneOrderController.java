package com.cdqckj.gmis.bizcenter.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderSaveDTO;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
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
 * 场景费用单API
 * </p>
 *
 * @author tp
 * @date 2020-10-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/charges/sceneOrder")
@Api(value = "sceneOrder", tags = "场景费用单API")
/*
@PreAuth(replace = "charges:")*/
public class SceneOrderController {
    @Autowired
    public CustomerSceneChargeOrderBizApi customerSceneChargeBizApi;

    @ApiOperation(value = "创建收费单")
    @PostMapping("/create")
    public R<CustomerSceneChargeOrder> calculateConvert(@RequestBody CustomerSceneChargeOrderSaveDTO saveDTO){
        return customerSceneChargeBizApi.save(saveDTO);
    }

    @ApiOperation(value = "业务单号撤回/取消费用单")
    @PostMapping("/cancelByBizCode")
    public R<Boolean> cancelByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode){
        return customerSceneChargeBizApi.cancelByBizCode(bizCode,sceneCode);
    }

    @ApiOperation(value = "业务单号查询费用单")
    @PostMapping("/queryByBizCode")
    public R<List<CustomerSceneChargeOrder>> queryByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode){
        return customerSceneChargeBizApi.queryByBizCode(bizCode,sceneCode);
    }
}

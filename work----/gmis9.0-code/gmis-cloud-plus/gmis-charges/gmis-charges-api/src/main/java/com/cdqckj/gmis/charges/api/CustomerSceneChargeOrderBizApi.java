package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerSceneChargeOrderUpdateDTO;
import com.cdqckj.gmis.charges.dto.GenSceneOrderDTO;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
/**
 * 场景收费订单API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}",  fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerSceneChargeOrder", qualifier = "CustomerSceneChargeOrderBizApi")
public interface CustomerSceneChargeOrderBizApi {
    /**
     * 保存场景收费订单
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<CustomerSceneChargeOrder> save(@RequestBody CustomerSceneChargeOrderSaveDTO saveDTO);

    /**
     * 更新场景收费订单
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<CustomerSceneChargeOrder> update(@RequestBody CustomerSceneChargeOrderUpdateDTO updateDTO);

    /**
     * 删除场景收费订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询场景收费订单
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<CustomerSceneChargeOrder>> page(@RequestBody PageParams<CustomerSceneChargeOrderPageDTO> params);

    /**
     * ID查询场景收费订单
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<CustomerSceneChargeOrder> get(@PathVariable("id") Long id);


    /**
     * 查询场景收费单
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<CustomerSceneChargeOrder>> query(@RequestBody CustomerSceneChargeOrder queryInfo);

    /**
     * 批量修改场景收费单收费状态为完成
     * @param ids
     * @return
     */
    @PostMapping(value = "/updateChargeStatusComplete")
    R<Integer> updateChargeStatusComplete(@RequestBody List<Long> ids,@NotBlank @RequestParam(value = "chargeNo") String chargeNo);

    /**
     * 批量修改场景收费单收费状态为待缴费
     * @param ids
     * @return
     */
    @PostMapping(value = "/updateChargeStatusUncharge")
    R<Integer> updateChargeStatusUncharge(@RequestBody List<Long> ids);

    /**
     * 批量查询场景收费单收费状态为完成
     * @param ids
     * @return
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    R<List<CustomerSceneChargeOrder>> queryList(@RequestParam("ids[]") List<Long> ids, @RequestHeader("tenant") String tenant);


    /**
     * 批量查询场景收费单收费状态为完成
     * @param ids
     * @return
     */
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    R<List<CustomerSceneChargeOrder>> queryList(@RequestParam("ids[]") List<Long> ids);

    /**
     * 业务单号撤回/取消费用单
     * @param bizCode
     * @return
     */
    @PostMapping("/cancelByBizCode")
    R<Boolean> cancelByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode);
    /**
     * 业务单号查询费用单
     * @param bizCode
     * @return
     */
    @PostMapping("/queryByBizCode")
    R<List<CustomerSceneChargeOrder>> queryByBizCode(@RequestParam(value = "bizCode")String bizCode,@RequestParam(value = "sceneCode")String sceneCode);


    /**
     * @param
     * @return
     */
    @PostMapping(value = "/saveList")
    R<CustomerSceneChargeOrder> saveList(@RequestBody List<CustomerSceneChargeOrderSaveDTO> saveDTOs);
    /**
     * 生成业务场景收费单
     * @param
     * @return
     */
    @PostMapping("/creatSceneOrders")
    R<Boolean> creatSceneOrders(@RequestBody @Validated GenSceneOrderDTO param);
}

package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitPageDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.hystrix.PurchaseLimitBizApiFallback;
import com.cdqckj.gmis.operateparam.vo.PurchaseLimitVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = PurchaseLimitBizApiFallback.class
        , path = "/purchaseLimit", qualifier = "purchaseApi")
public interface PurchaseLimitBizApi {
    /**
     * 保存购气配额限制
     *
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<PurchaseLimit> save(@RequestBody PurchaseLimitSaveDTO saveDTO);

    /**
     * 更新购气配额限制
     *
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<PurchaseLimit> update(@RequestBody PurchaseLimitUpdateDTO updateDTO);

    /**
     * 删除购气配额限制
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询购气配额限制
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<PurchaseLimit>> page(@RequestBody PageParams<PurchaseLimitPageDTO> params);

    /**
     * 根据ID查询用户配额记录
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<PurchaseLimit> get(@PathVariable(value = "id") Long id);

    /**
     * 查询所有限购配置
     *
     * @return
     */
    @GetMapping(value = "/getAllRecord")
    List<PurchaseLimit> getAllRecord();


    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    R<List<PurchaseLimit>> query(@RequestBody PurchaseLimit data);

    /**
     * 查询用户的限购信息
     *
     * @param pageParams 分页查询参数
     * @return 限购信息列表
     */
    @PostMapping(value = "/pageCustomerLimitInfo")
    R<Page<PurchaseLimit>> pageCustomerLimitInfo(@RequestBody PageParams<PurchaseLimitVO> pageParams);
}

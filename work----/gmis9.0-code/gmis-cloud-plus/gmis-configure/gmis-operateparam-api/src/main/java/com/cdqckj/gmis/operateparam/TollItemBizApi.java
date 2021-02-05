package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.systemparam.dto.TollItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收费配置信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/tollItem", qualifier = "tollItemBizApi")
public interface TollItemBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<TollItem> save(@RequestBody TollItemSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<TollItem> update(@RequestBody TollItemUpdateDTO updateDTO);

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateBatch",method = RequestMethod.PUT)
    R<Boolean> updateBatch(@RequestBody List<TollItemUpdateDTO> list);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE ,value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<TollItem>> page(@RequestBody PageParams<TollItemPageDTO> params);

    @PostMapping("/query")
    R<List<TollItem>> query(@RequestBody TollItem data);

    @PostMapping("/queryList")
    R<List<TollItem>> queryList(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody TollItemSaveDTO tollItemSaveDTO);

    /**
     * 判断场景是否进行收费
     * @author hc
     * @param sceneCode 判断场景是否进行收费
     * @return
     */
    @GetMapping("/whetherSceneCharge")
    R<Boolean> whetherSceneCharge(@RequestParam("sceneCode") String sceneCode);
}

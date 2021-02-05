package com.cdqckj.gmis.operateparam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemPageDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.hystrix.SecurityCheckItemApiFallback;
import com.cdqckj.gmis.operateparam.vo.ScItemsVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/securityCheckItem", qualifier = "securityCheckItemApi")
public interface SecurityCheckItemApi {

    /**
     * @param saveDTO
     * @return
     */
    @PostMapping
    R<SecurityCheckItem> saveTemplate(@RequestBody SecurityCheckItemSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @PutMapping
    R< SecurityCheckItem> update(@RequestBody SecurityCheckItemUpdateDTO updateDTO);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page< SecurityCheckItem>> page(@RequestBody PageParams<SecurityCheckItemPageDTO> params);

    @PostMapping (value = "/getAllCheckInfos")
    @ResponseBody
    R< List<ScItemsVo>> getAllCheckInfos(@RequestBody Map<String,Object> map);

    @ApiOperation(value = "查询所有安检配置子项", notes = "查询所有安检配置子项")
    @GetMapping(value = "/getAllPzCks")
    R< List<SecurityCheckItem> > getAllPzCks();

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody SecurityCheckItemSaveDTO securityCheckItemSaveDTO);

    @PostMapping(value = "/checkupadate")
    R<Boolean> checkupadate(@RequestBody SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO);
}

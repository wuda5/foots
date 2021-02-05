package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.hystrix.BusinessBizApiFallback;
import com.cdqckj.gmis.operateparam.hystrix.FunctionSwitchBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.*;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitch;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模块信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class, path = "/functionSwitch")
public interface FunctionSwitchBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<FunctionSwitch> save(@RequestBody FunctionSwitchSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<FunctionSwitch> update(@RequestBody FunctionSwitchUpdateDTO updateDTO);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<FunctionSwitch>> page(@RequestBody PageParams<FunctionSwitchPageDTO> params);

//    /**
//     * @param queryDTO
//     * @return
//     */
//    @PostMapping(value = "/query")
//    R<List<FunctionSwitch>> query(@RequestBody FunctionSwitch queryDTO);


}

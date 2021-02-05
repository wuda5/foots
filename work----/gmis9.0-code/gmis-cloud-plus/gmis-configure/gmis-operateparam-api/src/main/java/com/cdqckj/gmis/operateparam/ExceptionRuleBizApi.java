package com.cdqckj.gmis.operateparam;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.operateparam.hystrix.ExceptionRuleConfBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抄表异常信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.device-server:gmis-operateparam-server}", fallback = ExceptionRuleConfBizApiFallback.class
        , path = "/exceptionRuleConf", qualifier = "exceptionRuleConf")
public interface ExceptionRuleBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ExceptionRuleConf> save(@RequestBody ExceptionRuleConfSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ExceptionRuleConf> update(@RequestBody ExceptionRuleConfUpdateDTO updateDTO);


    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<ExceptionRuleConfUpdateDTO> updateBatch);


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
    R<Page<ExceptionRuleConf>> page(@RequestBody PageParams<ExceptionRuleConfPageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value ="/query")
    R<List<ExceptionRuleConf>> query(@RequestBody ExceptionRuleConf queryDTO);

    /**
     * 根据用气类型id集合获取数据
     * @param typeIds
     * @return
     */
    @PostMapping(value ="/queryByGasTypeId")
    R<List<ExceptionRuleConf>> queryByGasTypeId(@RequestBody List<Long> typeIds);
}



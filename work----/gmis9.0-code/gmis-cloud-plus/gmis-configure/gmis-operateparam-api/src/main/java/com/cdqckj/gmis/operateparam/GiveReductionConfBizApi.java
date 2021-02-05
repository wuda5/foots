package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.operateparam.hystrix.GiveReductionConfBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 赠送活动信息
 * @author lmj
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = GiveReductionConfBizApiFallback.class
        , path = "/giveReductionConf", qualifier = "giveReductionConf")
public interface GiveReductionConfBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<GiveReductionConf> save(@RequestBody GiveReductionConfSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<GiveReductionConf> update(@RequestBody GiveReductionConfUpdateDTO updateDTO);


    /**
     * @param updateBatch
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<GiveReductionConfUpdateDTO> updateBatch);


    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<GiveReductionConf>> page(@RequestBody PageParams<GiveReductionConfPageDTO> params);

    /**
     * @param queryDTO
     * @return
     */
    @PostMapping(value ="/query")
    R<List<GiveReductionConf>> query(@RequestBody GiveReductionConf queryDTO);

    /**
     *条件查询有效可用的减免项或者活动项
     * @param queryDTO
     * @return
     */
    @PostMapping(value ="/queryEffectiveGiveReduction")
    R<List<GiveReductionConf>> queryEffectiveGiveReduction(@RequestBody GiveReductionQueryDTO queryDTO);

}

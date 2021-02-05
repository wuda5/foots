package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.PreferentialPageDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Preferential;
import com.cdqckj.gmis.operateparam.hystrix.PreferentialBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠活动信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = PreferentialBizApiFallback.class
        , path = "/preferential", qualifier = "preferentialBizApi")
public interface PreferentialBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Preferential> save(@RequestBody PreferentialSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Preferential> update(@RequestBody PreferentialUpdateDTO updateDTO);


    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE )
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);


    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Preferential>> page(@RequestBody PageParams<PreferentialPageDTO> params);

    /**
     * @param data
     * @return
     */
    @PostMapping(value = "/query")
    R<List<Preferential>> query(@RequestBody Preferential data);

}

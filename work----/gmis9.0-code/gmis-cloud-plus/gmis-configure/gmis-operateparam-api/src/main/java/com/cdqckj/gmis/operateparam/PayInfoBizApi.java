package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.hystrix.PayInfoBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.PayInfoPageDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoSaveDTO;
import com.cdqckj.gmis.systemparam.dto.PayInfoUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = PayInfoBizApiFallback.class
        , path = "/payInfo", qualifier = "payInfoBizApi")
public interface PayInfoBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<PayInfo> save(@RequestBody PayInfoSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<PayInfo> update(@RequestBody PayInfoUpdateDTO updateDTO);

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
    R<Page<PayInfo>> page(@RequestBody PageParams<PayInfoPageDTO> params);


    @PostMapping(value = "/query")
    R<List<PayInfo>> query(@RequestBody PayInfo data);

}

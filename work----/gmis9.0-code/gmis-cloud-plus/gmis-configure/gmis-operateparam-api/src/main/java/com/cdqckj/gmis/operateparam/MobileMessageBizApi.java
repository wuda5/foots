package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.hystrix.MobileMessageBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.MobileMessagePageDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageSaveDTO;
import com.cdqckj.gmis.systemparam.dto.MobileMessageUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.MobileMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = MobileMessageBizApiFallback.class
        , path = "/mobileMessage", qualifier = "mobileMessageBizApi")
public interface MobileMessageBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<MobileMessage> save(@RequestBody MobileMessageSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<MobileMessage> update(@RequestBody MobileMessageUpdateDTO updateDTO);

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
    R<Page<MobileMessage>> page(@RequestBody PageParams<MobileMessagePageDTO> params);
}

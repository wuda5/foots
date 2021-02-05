package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoicePlatConfigUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoicePlatConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电子发票服务平台配置信息
 *
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/invoicePlatConfig", qualifier = "invoicePlatConfigBizApi")
public interface InvoicePlatConfigBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<InvoicePlatConfig> save(@RequestBody InvoicePlatConfigSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<InvoicePlatConfig> update(@RequestBody InvoicePlatConfigUpdateDTO updateDTO);

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<InvoicePlatConfig>> page(@RequestBody PageParams<InvoicePlatConfigPageDTO> params);

    /**
     * 查询当前公司生效的发票服务平台配置
     *
     * @return
     */
    @PostMapping(value = "/getOne")
    R<InvoicePlatConfig> getOne();

    /**
     * 通过ID获取对象
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<InvoicePlatConfig> get(@PathVariable(value = "id") Long id);
}

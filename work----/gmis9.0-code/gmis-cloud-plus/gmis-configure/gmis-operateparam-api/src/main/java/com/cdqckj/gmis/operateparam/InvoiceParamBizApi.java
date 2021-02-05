package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.hystrix.InvoiceParamBizApiFallback;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoiceParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 开票参数信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = InvoiceParamBizApiFallback.class
        , path = "/invoiceParam", qualifier = "invoiceParamBizApi")
public interface InvoiceParamBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<InvoiceParam> save(@RequestBody InvoiceParamSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<InvoiceParam> update(@RequestBody InvoiceParamUpdateDTO updateDTO);

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
    R<Page<InvoiceParam>> page(@RequestBody PageParams<InvoiceParamPageDTO> params);
}

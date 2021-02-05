package com.cdqckj.gmis.invoice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 票据打印记录
 *
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.invoice-server:gmis-invoice-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/receiptPrintRecord", qualifier = "ReceiptPrintRecordBizApi")
public interface ReceiptPrintRecordBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ReceiptPrintRecord> save(@RequestBody ReceiptPrintRecordSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ReceiptPrintRecord> update(@RequestBody ReceiptPrintRecordUpdateDTO updateDTO);

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
    R<Page<ReceiptPrintRecord>> page(@RequestBody PageParams<ReceiptPrintRecordPageDTO> params);

    /**
     * 获取打印
     *
     * @param templateCode
     * @return
     */
    @GetMapping(value = "/getPrintTemplate")
    R<TemplateItem> getPrintTemplate(@RequestParam(value = "templateCode") TemplateTypeEnum.Type.Ticket templateCode);
}

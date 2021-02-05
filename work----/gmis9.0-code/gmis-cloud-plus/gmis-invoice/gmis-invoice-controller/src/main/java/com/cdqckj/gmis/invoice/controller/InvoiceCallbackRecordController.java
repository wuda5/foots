package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.InvoiceCallbackRecordPageDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceCallbackRecordSaveDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceCallbackRecordUpdateDTO;
import com.cdqckj.gmis.invoice.entity.InvoiceCallbackRecord;
import com.cdqckj.gmis.invoice.service.InvoiceCallbackRecordService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 电子发票回调记录表
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoiceCallbackRecord")
@Api(value = "InvoiceCallbackRecord", tags = "电子发票回调记录表")
@PreAuth(replace = "invoiceCallbackRecord:")
public class InvoiceCallbackRecordController extends SuperController<InvoiceCallbackRecordService, Long, InvoiceCallbackRecord, InvoiceCallbackRecordPageDTO, InvoiceCallbackRecordSaveDTO, InvoiceCallbackRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<InvoiceCallbackRecord> invoiceCallbackRecordList = list.stream().map((map) -> {
            InvoiceCallbackRecord invoiceCallbackRecord = InvoiceCallbackRecord.builder().build();
            //TODO 请在这里完成转换
            return invoiceCallbackRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoiceCallbackRecordList));
    }
}

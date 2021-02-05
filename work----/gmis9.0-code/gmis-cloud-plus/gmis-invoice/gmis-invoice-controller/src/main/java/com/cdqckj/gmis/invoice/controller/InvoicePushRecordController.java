package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.InvoicePushRecordPageDTO;
import com.cdqckj.gmis.invoice.dto.InvoicePushRecordSaveDTO;
import com.cdqckj.gmis.invoice.dto.InvoicePushRecordUpdateDTO;
import com.cdqckj.gmis.invoice.entity.InvoicePushRecord;
import com.cdqckj.gmis.invoice.service.InvoicePushRecordService;
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
 * 电子发票推送记录表
 * </p>
 *
 * @author gmis
 * @date 2020-11-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoicePushRecord")
@Api(value = "InvoicePushRecord", tags = "电子发票推送记录表")
@PreAuth(replace = "invoicePushRecord:")
public class InvoicePushRecordController extends SuperController<InvoicePushRecordService, Long, InvoicePushRecord, InvoicePushRecordPageDTO, InvoicePushRecordSaveDTO, InvoicePushRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<InvoicePushRecord> invoicePushRecordList = list.stream().map((map) -> {
            InvoicePushRecord invoicePushRecord = InvoicePushRecord.builder().build();
            //TODO 请在这里完成转换
            return invoicePushRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoicePushRecordList));
    }
}

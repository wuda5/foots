package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptPrintRecord;
import com.cdqckj.gmis.invoice.service.ReceiptPrintRecordService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.TemplateTypeEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 票据打印记录
 * </p>
 *
 * @author gmis
 * @date 2020-10-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receiptPrintRecord")
@Api(value = "ReceiptPrintRecord", tags = "票据打印记录")
@PreAuth(replace = "receiptPrintRecord:")
public class ReceiptPrintRecordController extends SuperController<ReceiptPrintRecordService, Long, ReceiptPrintRecord, ReceiptPrintRecordPageDTO, ReceiptPrintRecordSaveDTO, ReceiptPrintRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<ReceiptPrintRecord> receiptPrintRecordList = list.stream().map((map) -> {
            ReceiptPrintRecord receiptPrintRecord = ReceiptPrintRecord.builder().build();
            //TODO 请在这里完成转换
            return receiptPrintRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(receiptPrintRecordList));
    }

    /**
     * 获取打印模板
     *
     * @param templateCode
     * @return
     */
    @GetMapping("/getPrintTemplate")
    R<TemplateItem> getPrintTemplate(@RequestParam(value = "templateCode") TemplateTypeEnum.Type.Ticket templateCode) {
        TemplateItem item = baseService.getPrintTemplate(templateCode);
        return R.success(item);
    }
}

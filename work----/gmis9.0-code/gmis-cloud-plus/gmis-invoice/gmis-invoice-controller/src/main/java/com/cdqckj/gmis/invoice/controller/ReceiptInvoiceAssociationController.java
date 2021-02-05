package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptInvoiceAssociation;
import com.cdqckj.gmis.invoice.service.ReceiptInvoiceAssociationService;
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
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receiptInvoiceAssociation")
@Api(value = "ReceiptInvoiceAssociation", tags = "票据发票关联表")
@PreAuth(replace = "receiptInvoiceAssociation:")
public class ReceiptInvoiceAssociationController extends SuperController<ReceiptInvoiceAssociationService, Long, ReceiptInvoiceAssociation, ReceiptInvoiceAssociationPageDTO, ReceiptInvoiceAssociationSaveDTO, ReceiptInvoiceAssociationUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ReceiptInvoiceAssociation> receiptInvoiceAssociationList = list.stream().map((map) -> {
            ReceiptInvoiceAssociation receiptInvoiceAssociation = ReceiptInvoiceAssociation.builder().build();
            //TODO 请在这里完成转换
            return receiptInvoiceAssociation;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(receiptInvoiceAssociationList));
    }
}

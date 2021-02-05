package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsPageDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsSaveDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsUpdateDTO;
import com.cdqckj.gmis.invoice.entity.InvoiceDetails;
import com.cdqckj.gmis.invoice.service.InvoiceDetailsService;
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
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/invoiceDetails")
@Api(value = "InvoiceDetails", tags = "发票明细")
@PreAuth(replace = "invoiceDetails:")
public class InvoiceDetailsController extends SuperController<InvoiceDetailsService, Long, InvoiceDetails, InvoiceDetailsPageDTO, InvoiceDetailsSaveDTO, InvoiceDetailsUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<InvoiceDetails> invoiceDetailsList = list.stream().map((map) -> {
            InvoiceDetails invoiceDetails = InvoiceDetails.builder().build();
            //TODO 请在这里完成转换
            return invoiceDetails;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(invoiceDetailsList));
    }
}

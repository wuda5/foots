package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptDetail;
import com.cdqckj.gmis.invoice.service.ReceiptDetailService;
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
 * @date 2020-09-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receiptDetail")
@Api(value = "ReceiptDetail", tags = "票据明细")
@PreAuth(replace = "receiptDetail:")
public class ReceiptDetailController extends SuperController<ReceiptDetailService, Long, ReceiptDetail, ReceiptDetailPageDTO, ReceiptDetailSaveDTO, ReceiptDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ReceiptDetail> receiptDetailList = list.stream().map((map) -> {
            ReceiptDetail receiptDetail = ReceiptDetail.builder().build();
            //TODO 请在这里完成转换
            return receiptDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(receiptDetailList));
    }
}

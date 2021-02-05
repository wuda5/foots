package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoPageDTO;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoSaveDTO;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoUpdateDTO;
import com.cdqckj.gmis.invoice.entity.SellerTaxpayerInfo;
import com.cdqckj.gmis.invoice.service.SellerTaxpayerInfoService;
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
@RequestMapping("/sellerTaxpayerInfo")
@Api(value = "SellerTaxpayerInfo", tags = "销售方税务相关信息")
@PreAuth(replace = "sellerTaxpayerInfo:")
public class SellerTaxpayerInfoController extends SuperController<SellerTaxpayerInfoService, Long, SellerTaxpayerInfo, SellerTaxpayerInfoPageDTO, SellerTaxpayerInfoSaveDTO, SellerTaxpayerInfoUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SellerTaxpayerInfo> sellerTaxpayerInfoList = list.stream().map((map) -> {
            SellerTaxpayerInfo sellerTaxpayerInfo = SellerTaxpayerInfo.builder().build();
            //TODO 请在这里完成转换
            return sellerTaxpayerInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(sellerTaxpayerInfoList));
    }
}

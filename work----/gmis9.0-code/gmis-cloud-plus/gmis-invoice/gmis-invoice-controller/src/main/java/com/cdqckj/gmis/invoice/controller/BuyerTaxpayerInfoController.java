package com.cdqckj.gmis.invoice.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoPageDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoSaveDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoUpdateDTO;
import com.cdqckj.gmis.invoice.entity.BuyerTaxpayerInfo;
import com.cdqckj.gmis.invoice.service.BuyerTaxpayerInfoService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 购买方税务相关信息
 * </p>
 *
 * @author houp
 * @date 2020-10-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/buyerTaxpayerInfo")
@Api(value = "BuyerTaxpayerInfo", tags = "购买方税务相关信息")
@PreAuth(replace = "buyerTaxpayerInfo:")
public class BuyerTaxpayerInfoController extends SuperController<BuyerTaxpayerInfoService, Long, BuyerTaxpayerInfo, BuyerTaxpayerInfoPageDTO, BuyerTaxpayerInfoSaveDTO, BuyerTaxpayerInfoUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<BuyerTaxpayerInfo> buyerTaxpayerInfoList = list.stream().map((map) -> {
            BuyerTaxpayerInfo buyerTaxpayerInfo = BuyerTaxpayerInfo.builder().build();
            //TODO 请在这里完成转换
            return buyerTaxpayerInfo;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(buyerTaxpayerInfoList));
    }

    /**
     * 根据纳税人识别号、购买方名称、购买方开户行精确查询数据
     *
     * @param buyerTaxpayerInfo
     * @return
     */
    @ApiOperation(value = "查询购买方纳税人信息")
    @PostMapping("/queryBuyerTaxpayerInfo")
    R<BuyerTaxpayerInfo> queryBuyerTaxpayerInfo(@RequestBody BuyerTaxpayerInfo buyerTaxpayerInfo) {
        LbqWrapper<BuyerTaxpayerInfo> wrapper = Wraps.lbQ();
        wrapper.eq(BuyerTaxpayerInfo::getBuyerTinNo, buyerTaxpayerInfo.getBuyerTinNo())
                .eq(BuyerTaxpayerInfo::getBuyerName, buyerTaxpayerInfo.getBuyerName())
                .eq(BuyerTaxpayerInfo::getBuyerBankAccount, buyerTaxpayerInfo.getBuyerBankAccount())
                .last("limit 1");
        return success(getBaseService().getOne(wrapper));
    }
}

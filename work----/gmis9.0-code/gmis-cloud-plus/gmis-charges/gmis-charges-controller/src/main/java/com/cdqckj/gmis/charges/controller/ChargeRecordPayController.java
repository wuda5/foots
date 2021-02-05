package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.ChargeRecordPayPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeRecordPaySaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeRecordPayUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecordPay;
import com.cdqckj.gmis.charges.service.ChargeRecordPayService;
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
 * 第三方支付记录明细
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargeRecordPay")
@Api(value = "ChargeRecordPay", tags = "第三方支付记录明细")
//@PreAuth(replace = "chargeRecordPay:")
public class ChargeRecordPayController extends SuperController<ChargeRecordPayService, Long, ChargeRecordPay, ChargeRecordPayPageDTO, ChargeRecordPaySaveDTO, ChargeRecordPayUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ChargeRecordPay> chargeRecordPayList = list.stream().map((map) -> {
            ChargeRecordPay chargeRecordPay = ChargeRecordPay.builder().build();
            //TODO 请在这里完成转换
            return chargeRecordPay;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(chargeRecordPayList));
    }
}

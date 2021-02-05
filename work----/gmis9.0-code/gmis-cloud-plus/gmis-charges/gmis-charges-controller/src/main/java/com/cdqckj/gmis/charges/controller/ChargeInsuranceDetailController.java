package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;
import com.cdqckj.gmis.charges.service.ChargeInsuranceDetailService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 保险明细
 * </p>
 *
 * @author tp
 * @date 2020-09-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargeInsuranceDetail")
@Api(value = "ChargeInsuranceDetail", tags = "保险明细")
@PreAuth(replace = "chargeInsuranceDetail:")
public class ChargeInsuranceDetailController extends SuperController<ChargeInsuranceDetailService, Long, ChargeInsuranceDetail, ChargeInsuranceDetailPageDTO, ChargeInsuranceDetailSaveDTO, ChargeInsuranceDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ChargeInsuranceDetail> chargeInsuranceDetailList = list.stream().map((map) -> {
            ChargeInsuranceDetail chargeInsuranceDetail = ChargeInsuranceDetail.builder().build();
            //TODO 请在这里完成转换
            return chargeInsuranceDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(chargeInsuranceDetailList));
    }

    /**
     * @auth lijianguo
     * @date 2020/11/7 15:09
     * @remark 这个客户的所有的有效的保单的个数
     */
    @ApiOperation(value = "这个客户的所有的有效的保单的个数")
    @GetMapping("/customerInsureSum")
    R<Integer> customerInsureSum(@RequestParam("customerCode") String customerCode){
        Integer sum = baseService.customerValidInsureSum(customerCode);
        return R.success(sum);
    }
}

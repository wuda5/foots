package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.biztemporary.*;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.vo.*;
import com.cdqckj.gmis.card.api.CardRepRecordBizApi;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcernsChange")
@Api(value = "interestConcernsChangeController", tags = "兴趣关注点-变更记录")
public class InterestConcernsChangeController {

    @Autowired
    ChangeMeterRecordBizApi changeMeterRecordBizApi;
    @Autowired
    RemoveMeterRecordBizApi removeMeterRecordBizApi;
    @Autowired
    CardRepRecordBizApi cardRepRecordBizApi;
    @Autowired
    BusinessTemporaryBizApi businessTemporaryBizApi;
    @Autowired
    GasTypeChangeRecordBizApi gasTypeChangeRecordBizApi;
    @Autowired
    SupplementGasRecordBizApi supplementGasRecordBizApi;

    @ApiOperation("业务关注点：获取所有变更记录")
    @GetMapping("/getChangeRecord")
    public R<HashMap<String, Object>> getChangeRecord(@RequestParam("customerCode") String customerCode,
                                                      @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode) {
        HashMap<String, Object> result = new HashMap<>();
        //补换卡记录
        List<CardRepRecordVO> cardRepList = cardRepRecordBizApi.queryFocusInfo(customerCode, gasMeterCode).getData();
        //补气记录
        List<SupplementGasRecordVO> cardRepGasList = supplementGasRecordBizApi.queryFocusInfo(customerCode, gasMeterCode).getData();
        //换表记录
        List<ChangeMeterRecordVO> changeMeterList = changeMeterRecordBizApi.queryFocusInfo(customerCode, gasMeterCode).getData();
        //拆表记录
        List<RemoveMeterRecordVO> removeMeterList = removeMeterRecordBizApi.queryFocusInfo(customerCode, gasMeterCode).getData();
        //过户记录
        List<GasMeterTransferAccountVO> transferAccountList = businessTemporaryBizApi.queryFocusInfo(customerCode, gasMeterCode).getData();
        //用气类型变更记录
        GasTypeChangeRecord gasTypeChangeQuery = GasTypeChangeRecord.builder().customerCode(customerCode).gasMeterCode(gasMeterCode).build();
        List<GasTypeChangeRecordVO> gasTypeChangeList = gasTypeChangeRecordBizApi.queryFocusInfo(gasTypeChangeQuery).getData();

        result.put("补换卡记录", cardRepList);
        result.put("补气记录", cardRepGasList);
        result.put("换表记录", changeMeterList);
        result.put("拆表记录", removeMeterList);
        result.put("过户记录", transferAccountList);
        result.put("用气类型变更记录", gasTypeChangeList);
        return R.success(result);

    }

}

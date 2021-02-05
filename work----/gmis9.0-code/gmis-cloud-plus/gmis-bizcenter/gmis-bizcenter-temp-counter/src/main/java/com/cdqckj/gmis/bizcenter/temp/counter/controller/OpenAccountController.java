package com.cdqckj.gmis.bizcenter.temp.counter.controller;

import cn.hutool.core.util.ObjectUtil;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.temp.counter.constants.TempCounterMessageConstants;
import com.cdqckj.gmis.bizcenter.temp.counter.service.OpenAccountService;
import com.cdqckj.gmis.biztemporary.OpenAccountRecordBizApi;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.biztemporary.enums.ChargeStateEnum;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/openAccount")
@Api(value = "openAccount", tags = "开户记录")
public class OpenAccountController {
    @Autowired
    private OpenAccountRecordBizApi openAccountRecordBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Resource
    private I18nUtil i18nUtil;
    @Resource
    private OpenAccountService openAccountService;

    /**
     * 判断开户是否收费
     * @param customerCode
     * @param gasCode
     * @return
     */
    @ApiOperation(value = "判断开户是否收费", notes = "判断开户是否收费")
    @PostMapping("/whetherCharged")
    public R<Boolean> whetherCharged(@RequestParam("customerCode") final String customerCode, @RequestParam("gasCode") final String gasCode) {
        //查询客户表具关联关系
        R<List<CustomerGasMeterRelated>> customerGasMeterRelateListR = getCustomerMeterRelationListR(customerCode, gasCode);
        OpenAccountRecord openAccountRecord = OpenAccountRecord
                .builder()
                .relateId(customerGasMeterRelateListR.getData().get(0).getId())
                .status(ChargeStateEnum.CHARGED.getCode())
                .build();
        R<List<OpenAccountRecord>> openAccountRecordListR = openAccountRecordBizApi.query(openAccountRecord);
        if (openAccountRecordListR.getIsError()) {
            throw new BizException(i18nUtil.getMessage(TempCounterMessageConstants.ABNORMAL_QUERY_OF_ACCOUNT_OPENING_RECORD));
        }
        if (openAccountRecordListR.getData().size() > 0) {
            return R.fail(i18nUtil.getMessage(TempCounterMessageConstants.METER_CHARGED_FOR_OPEN_ACCOUNT), "该表具开户已收费");
        }
        return R.success(true);
    }

    /**
     * 开户收费回调
     * @param bizNoOrId
     * @return
     */
    @ApiOperation(value = "开户收费回调")
    @PutMapping("/chargeCallback")
    @GlobalTransactional
    public R<Boolean> chargeCallback(@RequestParam("bizNoOrId") final String bizNoOrId, @RequestParam("isRefund") final Boolean isRefund){
        return openAccountService.chargeCallback(bizNoOrId, isRefund);
    }

    /**
     * 查询客户表具关联关系
     * @param customerCode
     * @param gasCode
     * @return
     */
    private R<List<CustomerGasMeterRelated>> getCustomerMeterRelationListR(String customerCode, String gasCode) {
        CustomerGasMeterRelated customerGasMeterRelate = CustomerGasMeterRelated
                .builder()
                .customerCode(customerCode)
                .gasMeterCode(gasCode)
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build();
        R<List<CustomerGasMeterRelated>> customerGasMeterRelateListR = customerGasMeterRelatedBizApi.query(customerGasMeterRelate);
        if (customerGasMeterRelateListR.getIsError()) {
            throw new BizException(i18nUtil.getMessage(TempCounterMessageConstants.QUERY_ACCOUNT_OPENING_ASSOCIATION_EXCEPTION));
        }
        if (ObjectUtil.isNotNull(customerGasMeterRelateListR.getData())) {
            if (customerGasMeterRelateListR.getData().size() > 1) {
                throw new BizException(i18nUtil.getMessage(TempCounterMessageConstants.NUMBER_OF_OPEN_ACCOUNT_GREATER_THAN_ONE));
            }
        }
        return customerGasMeterRelateListR;
    }
}

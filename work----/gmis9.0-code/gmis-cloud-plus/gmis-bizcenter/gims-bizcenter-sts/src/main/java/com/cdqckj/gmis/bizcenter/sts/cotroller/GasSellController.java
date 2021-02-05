package com.cdqckj.gmis.bizcenter.sts.cotroller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.sts.entity.StsGasDetailVo;
import com.cdqckj.gmis.charges.api.ChargeItemRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.api.GasmeterSettlementDetailBizApi;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.statistics.vo.PanelTimeGroupVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author: lijianguo
 * @time: 2021/01/08 16:18
 * @remark: 请添加类说明
 */
@Slf4j
@Validated
@RestController
@RequestMapping("biz/sts/gasSell")
@Api(value = "stsGas", tags = "售气数据")
@PreAuth(replace = "gasSell:")
public class GasSellController {

    @Autowired
    ReadMeterDataApi readMeterDataApi;

    @Autowired
    ChargeItemRecordBizApi chargeItemRecordBizApi;

    @Autowired
    GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    GasmeterSettlementDetailBizApi gasmeterSettlementDetailBizApi;

    @Autowired
    ReadMeterDataIotApi readMeterDataIotApi;

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 14:21
    * @remark 业绩看板-售气数据
    * 普表-从结算明细表里面统计用气量和费用
    *
    *
    */
    @ApiOperation(value = "业绩看板-售气数据")
    @PostMapping("/stsGasDetail")
    public R<StsGasDetailVo> stsGasDetail(@RequestBody StsSearchParam stsSearchParam){

        StsGasDetailVo vo = new StsGasDetailVo();
        // 普表
        stsSearchParam.addSearchKey("gasMeterType", OrderSourceNameEnum.READMETER_PAY.getCode());
        // StsDateVo generalGasMeter = gasmeterArrearsDetailBizApi.stsAfterGasMeter(stsSearchParam).getData();
        StsDateVo generalGasMeter = gasmeterSettlementDetailBizApi.stsGeneralGasMeterGas(stsSearchParam).getData();
        generalGasMeter.setTodayNum(null);
        // IC卡表
        stsSearchParam.addSearchKey("gasMeterType", OrderSourceNameEnum.IC_RECHARGE.getCode());
        StsDateVo cardGasMeter = chargeRecordBizApi.stsBeforeGasMeter(stsSearchParam).getData();
        // 物联网后付费表
        stsSearchParam.addSearchKey("gasMeterType", OrderSourceNameEnum.REMOTE_READMETER.getCode());
        //StsDateVo afterGasMeter = gasmeterArrearsDetailBizApi.stsAfterGasMeter(stsSearchParam).getData();
        StsDateVo afterGasMeter = readMeterDataIotApi.stsInternetGasMeterGas(stsSearchParam).getData();
        // 物联网预付费表
        stsSearchParam.addSearchKey("gasMeterType", OrderSourceNameEnum.CENTER_RECHARGE.getCode());
        // StsDateVo preGasMeter = chargeRecordBizApi.stsBeforeGasMeter(stsSearchParam).getData();
        StsDateVo preGasMeter = readMeterDataIotApi.stsInternetGasMeterGas(stsSearchParam).getData();
        // 物联网表端计费表
        stsSearchParam.addSearchKey("gasMeterType", OrderSourceNameEnum.REMOTE_RECHARGE.getCode());
        StsDateVo lastGasMeter = chargeRecordBizApi.stsBeforeGasMeter(stsSearchParam).getData();

        vo.setGeneralGasMeter(generalGasMeter);
        vo.setCardGasMeter(cardGasMeter);
        vo.setAfterGasMeter(afterGasMeter);
        vo.setPreGasMeter(preGasMeter);
        vo.setLastGasMeter(lastGasMeter);
        return R.success(vo);
    }




}

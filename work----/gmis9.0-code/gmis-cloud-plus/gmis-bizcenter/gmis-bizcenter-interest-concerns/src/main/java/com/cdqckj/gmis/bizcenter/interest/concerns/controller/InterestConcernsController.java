package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.interest.concerns.vo.ChargeRecordVO;
import com.cdqckj.gmis.bizcenter.interest.concerns.vo.GasMeterInfoFocusVO;
import com.cdqckj.gmis.bizcenter.interest.concerns.vo.PriceSchemeVO;
import com.cdqckj.gmis.bizcenter.interest.concerns.vo.UseGasPriceSchemeVO;
import com.cdqckj.gmis.biztemporary.SupplementGasRecordBizApi;
import com.cdqckj.gmis.card.api.CardRepRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeItemRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.GasmeterSettlementDetailBizApi;
import com.cdqckj.gmis.charges.dto.ChargeRecordPageDTO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.ValveState;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.IotAlarm;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.lot.IotAlarmBizApi;
import com.cdqckj.gmis.lot.IotGasMeterUploadDataBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcerns")
@Api(value = "InterestConcernsController", tags = "兴趣关注点接口")
public class InterestConcernsController {
    @Autowired
    private GasmeterSettlementDetailBizApi gasmeterSettlementDetailBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private CardRepRecordBizApi cardRepRecordBizApi;
    @Autowired
    private SupplementGasRecordBizApi supplementGasRecordBizApi;
    @Autowired
    private ReadMeterDataApi readMeterDataApi;
    @Autowired
    private ReadMeterLatestRecordApi readMeterLatestRecordApi;
    @Autowired
    private ChargeItemRecordBizApi chargeItemRecordBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private IotAlarmBizApi iotAlarmBizApi;
    @Autowired
    private ChargeRecordBizApi chargeRecordBizApi;
    @Autowired
    private IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi;

    /**
     * 获取兴趣关注点卡信息
     *
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "获取气价方案信息")
    @GetMapping("/useGasType/getPriceAndUsed")
    public R<UseGasPriceSchemeVO> getPriceAndUsed(@RequestParam("gasMeterCode") String gasMeterCode) {
        UseGasPriceSchemeVO viewVO = new UseGasPriceSchemeVO();
        GasMeter gasMeter = gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
        if (Objects.isNull(gasMeter)) {
            return R.fail("表具不存在");
        }
        GasmeterSettlementDetail latestOne = gasmeterSettlementDetailBizApi.getLatestOne(gasMeter.getGasCode()).getData();
        UseGasTypeVO useGasTypeVO = useGasTypeBizApi.queryUseGasTypeAndPrice(gasMeter.getUseGasTypeId()).getData();
        viewVO.setUseGasType(useGasTypeVO.getUseGasType());
        List<PriceSchemeVO> priceList = new ArrayList<>();
        useGasTypeVO.getPriceSchemeList().forEach(data -> {
            PriceSchemeVO vo = BeanUtil.copyProperties(data, PriceSchemeVO.class);
            if (Objects.nonNull(latestOne) && latestOne.getLadderChargeModeId().equals(data.getId())) {
                vo.setGas1Used(latestOne.getGas1());
                vo.setPrice1Used(latestOne.getPrice1());
                vo.setGas2Used(latestOne.getGas2());
                vo.setPrice2Used(latestOne.getPrice2());
                vo.setGas3Used(latestOne.getGas3());
                vo.setPrice3Used(latestOne.getPrice3());
                vo.setGas4Used(latestOne.getGas4());
                vo.setPrice4Used(latestOne.getPrice4());
                vo.setGas5Used(latestOne.getGas5());
                vo.setPrice5Used(latestOne.getPrice5());
                vo.setGas6Used(latestOne.getGas6());
                vo.setPrice6Used(latestOne.getPrice6());
                vo.setGasUsed(latestOne.getGas());
                vo.setPriceUsed(latestOne.getGasMoney());
            }
            priceList.add(vo);
        });
        viewVO.setPriceSchemeList(priceList);
        return R.success(viewVO);
    }

    /**
     * 获取兴趣关注点卡信息
     *
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "获取气表使用情况")
    @GetMapping("/getMeterInfo")
    public R<GasMeterInfoFocusVO> getMeterInfo(@RequestParam(value = "customerCode", required = false) String customerCode, @RequestParam("gasMeterCode") String gasMeterCode) {
        GasMeter meter = gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
        if (Objects.isNull(meter)) {
            return R.fail("未查询到表具信息，请核对参数！");
        }
        if (StringUtils.isEmpty(customerCode)) {
            CustomerGasMeterRelated related = customerGasMeterRelatedBizApi.findGasCodeByCode(gasMeterCode).getData();
            if (Objects.isNull(related)) {
                return R.fail("该表具信息还没有使用信息");
            }
            customerCode = related.getCustomerCode();
        }
        GasMeterInfo gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode, customerCode).getData();
        GasMeterInfoFocusVO vo = BeanUtil.copyProperties(gasMeterInfo, GasMeterInfoFocusVO.class);
        GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        String orderSourceName = version.getOrderSourceName();
        vo.setUseGasTypeId(meter.getUseGasTypeId());
        vo.setGasMeterType(orderSourceName);
        vo.setOrderSourceName(orderSourceName);
        vo.setAmountMark(version.getAmountMark());
        //如果是卡表 查询补卡、补气次数
        if (OrderSourceNameEnum.IC_RECHARGE.eq(orderSourceName)) {
            vo.setTotalReplacementCardCount(cardRepRecordBizApi.countCardRep(gasMeterCode).getData());
            vo.setTotalAdditionalCount(supplementGasRecordBizApi.countCardRep(gasMeterCode).getData());
        }
        //普表：累计缴费次数、累计抄表次数、最后抄表月、最后缴费时间、最后抄表量
        if (OrderSourceNameEnum.READMETER_PAY.eq(orderSourceName)) {
            vo.setTotalReadMeterCount(readMeterDataApi.countReadMeterData(gasMeterCode).getData());
            ReadMeterLatestRecord query = ReadMeterLatestRecord.builder()
                    .gasMeterCode(gasMeterCode)
                    .build();
            ReadMeterLatestRecord lastReaMeter = readMeterLatestRecordApi.queryOne(query).getData();
            if (Objects.nonNull(lastReaMeter)) {
                vo.setLastReadMeterTime(lastReaMeter.getCurrentReadTime());
                vo.setReadMeterGas(lastReaMeter.getCurrentTotalGas());
            }
            ChargeItemVO data = chargeItemRecordBizApi.getLastUpdateTimeAndCount(gasMeterCode).getData();
            if (Objects.nonNull(data)) {
                vo.setLastChargeTime(data.getUpdateTime());
                vo.setTotalChargeCount(data.getCount());
            }
        }
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(orderSourceName) || OrderSourceNameEnum.REMOTE_RECHARGE.eq(orderSourceName)
                || OrderSourceNameEnum.CENTER_RECHARGE.eq(orderSourceName)) {

            List<IotAlarm> data = iotAlarmBizApi.queryAfterCreateTime(meter.getGasMeterNumber(), meter.getOpenAccountTime());
            vo.setExceptionInfoList(data);

            //查询阀控状态
            IotGasMeterUploadData query = IotGasMeterUploadData.builder()
                    .gasMeterCode(gasMeterCode)
                    .customerCode(customerCode)
                    .gasMeterNumber(meter.getGasMeterNumber())
                    .build();
            IotGasMeterUploadData uploadData = iotGasMeterUploadDataBizApi.queryOne(query).getData();
            if (Objects.nonNull(uploadData)) {
                ValveState valveState = ValveState.get(uploadData.getValveStatus());
                vo.setValveState(valveState == null ? null : valveState.name());
            }
        }
        return R.success(vo);
    }

    /**
     * 分页查询缴费记录
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询缴费记录")
    @PostMapping(value = "/chargeList")
    public R<Page<ChargeRecordVO>> chargeList(@RequestBody PageParams<ChargeRecordPageDTO> params) {
        Page<ChargeRecordVO> page = new Page<>(params.getCurrent(), params.getSize());
        Page<ChargeRecord> data = chargeRecordBizApi.pageQueryFocusInfo(params).getData();
        List<ChargeRecord> records = data.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return R.success(page);
        }
        List<ChargeRecordVO> result = new ArrayList<>();
        records.forEach(temp -> {
            ChargeRecordVO chargeRecordVO = BeanUtil.copyProperties(temp, ChargeRecordVO.class);
            List<ChargeItemRecord> item = chargeItemRecordBizApi.getChangeItemByChargeNo(temp.getChargeNo()).getData();
            chargeRecordVO.setChargeItemList(item);
            result.add(chargeRecordVO);
        });
        page.setRecords(result);
        return R.success(page);
    }


}

package com.cdqckj.gmis.bizcenter.card.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.biztemporary.SupplementGasRecordBizApi;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.card.api.CardBackGasRecordBizApi;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.api.CardOperBizApi;
import com.cdqckj.gmis.card.dto.CardBackGasRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardBackGasRecordUpdateDTO;
import com.cdqckj.gmis.card.dto.CardSupplementGasDTO;
import com.cdqckj.gmis.card.dto.RecCardLoadDTO;
import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.util.CardUtils;
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeRefundBizApi;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController("operCardLoadController")
@RequestMapping("/card")
@Api(value = "card", tags = "读写卡数据加载接口")
/*
@PreAuth(replace = "card:")*/
public class OperCardLoadController {


    @Autowired
    CardOperBizApi cardOperBizApi;
    @Autowired
    CardBizService cardBizService;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    GasMeterBizApi gasMeterBizApi;
    @Autowired
    CardInfoBizApi cardInfoBizApi;

    @Autowired
    CardBackGasRecordBizApi cardBackGasRecordBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;
    @Autowired
    ChargeRefundBizApi chargeRefundBizApi;

    @Autowired
    SupplementGasRecordBizApi  supplementGasRecordBizApi;

    /**
     * 读卡数据加载
     * @return
     */
    @ApiOperation(value = "读卡数据加载")
    @GetMapping("/readCardLoad")
    public R<JSONObject> readCardLoad(){
        return cardOperBizApi.readCardLoad();
    }

    /**
     * 写开户卡加载
     * @param
     * @return
     */
    @ApiOperation(value = "写开户卡加载")
    @PostMapping("/issueCardLoad")
    public R<JSONObject> issueCardLoad(@RequestParam(value = "id") Long id,
                                     @RequestBody JSONObject readData){
        return cardOperBizApi.issueCardLoad(id,readData);
    }

    /**
     * 购气写卡数据加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "购气写卡数据加载")
    @PostMapping("/buyGasLoad")
    public R<JSONObject> buyGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                @RequestBody JSONObject readData
                                ){
        return cardOperBizApi.buyGasLoad(gasMeterCode,readData);
    }

    /**
     * 回收写卡数据加载
     * @return
     */
    @ApiOperation(value = "回收写卡数据加载")
    @PostMapping("/recCardLoad")
    public R<RecCardLoadDTO> recCardLoad(@RequestBody JSONObject readData){
        return cardOperBizApi.recCardLoad(readData);
    }

    /**
     * 补卡写卡数据加载
     * @param id
     * @return
     */
    @ApiOperation(value = "补卡写卡数据加载")
    @PostMapping("/repCardLoad")
    public R<JSONObject> repCardLoad(@RequestParam(value = "id") Long id,
                                 @RequestBody JSONObject readData){
        return cardOperBizApi.repCardLoad(id,readData);
    }

    /**
     * 补气写卡数据加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "补气写卡数据加载")
    @PostMapping("/repGasLoad")
    public R<JSONObject> repGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                   @NotNull @RequestParam(value = "bizIdOrNo") String bizIdOrNo,
                                @RequestBody JSONObject readData){
        CardSupplementGasDTO repGasData = new CardSupplementGasDTO();
        repGasData.setReadCardData(readData);
        SupplementGasRecord supplementGasRecord=supplementGasRecordBizApi.get(Long.parseLong(bizIdOrNo)).getData();
        repGasData.setCustomerCode(supplementGasRecord.getCustomerCode());
        repGasData.setCustomerName(supplementGasRecord.getCustomerName());
        repGasData.setGasMeterCode(supplementGasRecord.getGasMeterCode());
        repGasData.setRepGasMethod(supplementGasRecord.getRepGasMethod());
        ChargeUtils.setNullFieldAsZero(supplementGasRecord);
        repGasData.setRepVal(supplementGasRecord.getRepGas().compareTo(BigDecimal.ZERO)>0?
                supplementGasRecord.getRepGas():supplementGasRecord.getRepMoney());
        return cardOperBizApi.repGasLoad(repGasData);
    }

    /**
     * 退气写卡数据加载
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "退气写卡数据加载")
    @PostMapping("/backGasLoad")
    public R<JSONObject> backGasLoad(@NotBlank @RequestParam(value = "gasMeterCode") String gasMeterCode,
                                    @RequestParam(value = "bizIdOrNo") String bizIdOrNo,
                                    @RequestBody JSONObject readData){
        if(StringUtil.isNotBlank(bizIdOrNo)){
            List<CardBackGasRecord> list= cardBackGasRecordBizApi.query(CardBackGasRecord.builder().bizIdOrNo(bizIdOrNo)
                    .build()).getData();
            if(CollectionUtils.isNotEmpty(list)) {
                for (CardBackGasRecord backGasRecord : list) {
                    if (backGasRecord.getDataStatus() == DataStatusEnum.NORMAL.getValue()) {
                        throw BizException.wrap("已退气,不能再次退气");
                    }
                }
            }
            ChargeRecord record=chargeRecordBizApi.getChargeRecordByNo(bizIdOrNo).getData();
            ChargeUtils.setNullFieldAsZero(record);
            if(record.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
                throw BizException.wrap("该笔退费没有充值，不能退气");
            }
            List<ChargeRefund> refunds=chargeRefundBizApi.query(ChargeRefund.builder()
                    .chargeNo(bizIdOrNo)
                    .refundStatus(RefundStatusEnum.REFUNDED.getCode())
                    .build()).getData();
            if(CollectionUtils.isEmpty(refunds)){
                throw BizException.wrap("退费单未完成退费，不能退气");
            }
            cardOperBizApi.readCardCallBack(readData).getData();
            if(!gasMeterCode.equals(record.getGasMeterCode())){
                throw BizException.wrap("读卡器中卡信息和当前缴费用户信息不一致");
            }
            GasMeter meter=gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
            GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
            Boolean isMoenyMeter=AmountMarkEnum.MONEY.eq(version.getAmountMark());
            String desc = isMoenyMeter ? "金额" : "气量";
            BigDecimal balance=CardUtils.getBalance(readData).setScale(2, RoundingMode.UP);
            if(CollectionUtils.isNotEmpty(list)) {
                if(balance.compareTo(BigDecimal.ZERO)==0){
                    //卡上都无气量了，更新该退费单，退气状态。
                    list.stream().sorted(Comparator.comparing(CardBackGasRecord::getCreateTime));
                    Long bid=list.get(0).getId();
                    CardBackGasRecordUpdateDTO gasRecord=CardBackGasRecordUpdateDTO.builder().id(bid).build();
                    gasRecord.setDataStatus(DataStatusEnum.NORMAL.getValue());
                    cardBackGasRecordBizApi.update(gasRecord);
                    //修复这种异常情况
                    throw BizException.wrap("卡上已无"+desc);
                }
            }

            CardBackGasRecordSaveDTO gasRecord=CardBackGasRecordSaveDTO.builder()
                    .bizIdOrNo(bizIdOrNo)
                    .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .cardNo(CardUtils.getCardNumber(readData))
                    .backGasType("RECHARGE")
                    .backValue(AmountMarkEnum.GAS.eq(version.getAmountMark())?
                            record.getRechargeGas().add(record.getRechargeGiveGas()):
                            record.getRechargeMoney().add(record.getRechargeGiveMoney()))
                    .customerCode(record.getCustomerCode())
                    .gasMeterCode(record.getGasMeterCode())
                    .build();
            if(balance.compareTo(BigDecimal.ZERO)==0){
                //修复这种异常情况
                gasRecord.setDataStatus(DataStatusEnum.NORMAL.getValue());
                cardBackGasRecordBizApi.save(gasRecord);
                throw BizException.wrap("卡上已无"+desc);
            }
            if(balance.subtract(new BigDecimal(0.01)).compareTo(gasRecord.getBackValue())>0 ||
                    balance.add(new BigDecimal(0.01)).compareTo(gasRecord.getBackValue())<0
            ){
                throw BizException.wrap("卡上" + desc + balance.toPlainString() + "不等于充值写卡" + desc + gasRecord.getBackValue().toPlainString() );
            };
            cardBackGasRecordBizApi.save(gasRecord);
        }else{
            throw BizException.wrap("未选择退费申请单");
        }
        return cardOperBizApi.backGasLoad(gasMeterCode,readData);
    }
}

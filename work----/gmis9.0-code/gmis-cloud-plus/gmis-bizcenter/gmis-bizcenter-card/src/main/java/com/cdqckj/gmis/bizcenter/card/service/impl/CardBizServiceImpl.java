package com.cdqckj.gmis.bizcenter.card.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.card.dto.CardRepLoadDTO;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.biztemporary.SupplementGasRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.enums.RepGasMethodEnum;
import com.cdqckj.gmis.biztemporary.enums.RepGasStatusEnum;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.api.CardOperBizApi;
import com.cdqckj.gmis.card.api.CardRepRecordBizApi;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.GenSceneOrderDTO;
import com.cdqckj.gmis.charges.dto.RechargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.enums.MoneyFlowStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 读写卡相关业务逻辑处理
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class CardBizServiceImpl extends SuperCenterServiceImpl implements CardBizService {


    @Autowired
    TollItemBizApi tollItemBizApi;

    @Autowired
    CardInfoBizApi cardInfoBizApi;

    @Autowired
    CardRepRecordBizApi cardRepRecordBizApi;

    @Autowired
    CardOperBizApi cardOperBizApi;

    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    GasMeterInfoBizApi gasMeterInfoBizApi;

    @Autowired
    RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;


    @Autowired
    CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;


    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    SupplementGasRecordBizApi supplementGasRecordBizApi;

    @Override
    public R<CardInfo> issueCard(String gasMeterCode,String customerCode) {
        CardInfo cardInfo=cardInfoBizApi.getByGasMeterCode(gasMeterCode,customerCode).getData();
        R<CardInfo> r=cardOperBizApi.issueCard(gasMeterCode,customerCode);
        if(r.getIsSuccess()){
            //新建的卡
            if(cardInfo==null) {
                Boolean whetherSceneCharge=tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.ISSUE_CARD.getCode()).getData();
                if(whetherSceneCharge){
                    customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                            .customerCode(customerCode)
                            .gasMeterCode(gasMeterCode)
                            .sceneCode(TolltemSceneEnum.ISSUE_CARD.getCode())
                            .bizNoOrId(r.getData().getId().toString()).build());
                }
            }
        }
        return r;
    }

    public R<Boolean> watherIssueCard(String gasMeterCode,String customerCode){
        CardInfo cardInfo=cardInfoBizApi.getByGasMeterCode(gasMeterCode,customerCode).getData();
//        GasMeter meter=gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
//        if(StringUtils.isBlank(meter.getSendCardStauts()) || SendCardState.WAITE_SEND.eq(meter.getSendCardStauts())){
//            //发卡未完成，不能这么判断，不然不能补新用户卡
//            return R.success(true);
//        }
        if(cardInfo==null){
            //没有发卡
            return R.success(true);
        }
        if(CardStatusEnum.WAITE_WRITE_CARD.eq(cardInfo.getCardStatus())){
            //发卡是待写卡状态
            return R.success(true);
        }
        //发卡完成
        return R.success(false);
    }

    /**
     * 补卡加载
     * @param gasMeterCode
     * @return
     */
    public R<CardRepLoadDTO> repCard(String gasMeterCode,String  customerCode){
        CardRepLoadDTO repLoadDTO=new CardRepLoadDTO();
        repLoadDTO.setCardRepRecord(cardOperBizApi.repCard(gasMeterCode,customerCode).getData());
        repLoadDTO.setGasMeterInfo(gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode,customerCode).getData());
        return R.success(repLoadDTO);
    }

    @Override
    public R<CardRepLoadDTO> repCardSave(CardRepRecordSaveDTO saveDto, Long id) {
        CardRepLoadDTO repLoadDTO=new CardRepLoadDTO();
//        CardRepRecord repRecord=new CardRepRecord();
//        BeanPlusUtil.copyProperties(saveDto,repRecord);
//        repRecord.setId(id);
        R<CardRepRecord> r=cardOperBizApi.repCardSave(saveDto,id);
        if(r.getIsSuccess()){
            if(id==null) {
                Boolean whetherSceneCharge=tollItemBizApi.whetherSceneCharge(TolltemSceneEnum.CARD_REPLACEMENT.getCode()).getData();
                if(whetherSceneCharge) {
                    customerSceneChargeOrderBizApi.creatSceneOrders(GenSceneOrderDTO.builder()
                            .customerCode(saveDto.getCustomerCode())
                            .gasMeterCode(saveDto.getGasMeterCode())
                            .sceneCode(TolltemSceneEnum.CARD_REPLACEMENT.getCode())
                            .bizNoOrId(r.getData().getId().toString()).build());
                }
            }
            //处理掉上次充值状态,如果是补上次充值，上次充值应该是待上表,
            rechargeRecordBizApi.dealUnCompleteRecord(saveDto.getGasMeterCode());
            repLoadDTO.setCardRepRecord(r.getData());
            repLoadDTO.setGasMeterInfo(gasMeterInfoBizApi.getByMeterAndCustomerCode(saveDto.getGasMeterCode(),saveDto.getCustomerCode()).getData());
            return R.success(repLoadDTO);
        }
       return R.fail(r.getMsg());
    }

    @Override
    public R<CardInfo> issueCardCallBack(Long id, JSONObject callBackData) {
        R<CardInfo> r=cardOperBizApi.issueCardCallBack(id,callBackData);
        if(r.getIsSuccess()) {
            GasMeter meter=gasMeterBizApi.findGasMeterByCode(r.getData().getGasMeterCode()).getData();
            GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
            //ID卡发完就修改表具发卡状态
            if(!OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())){
                gasMeterBizApi.update(GasMeterUpdateDTO.builder()
                        .id(meter.getId()).sendCardStauts(SendCardState.SENDED.getCode()).build());
            }else{
                //IC 卡，发完处理充值状态为待上表。
                RechargeRecord record = rechargeRecordBizApi.getByGasMeterCodeAndStatus(meter.getGasCode(),r.getData().getCustomerCode(), MoneyFlowStatusEnum.waite_deal.getCode()).getData();
                if (record != null) {
                    RechargeRecordUpdateDTO rechargeRecordUpdateDTO = RechargeRecordUpdateDTO
                            .builder()
                            .id(record.getId())
                            .moneyFlowStatus(MoneyFlowStatusEnum.waite_to_meter.getCode()).build();
                    //处理充值记录状态，否则下次不能充值
                    rechargeRecordBizApi.update(rechargeRecordUpdateDTO);
                }else{
                    log.info("无充值数据");
                }
            }
        }
        return r;
    }

    @Override
    public R<CardInfo> readCardCallBack(JSONObject callBackData) {
        R<CardInfo> r=cardOperBizApi.readCardCallBack(callBackData);
        if(r.getIsSuccess() && r.getData()!=null) {
            String gasMeterCode=r.getData().getGasMeterCode();
            if(gasMeterCode!=null) {
                GasMeter meter = gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
                GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
                //如果能判断是IC卡还是ID卡，只有IC卡才做下面操作
                if (version.getOrderSourceName().equals(OrderSourceNameEnum.IC_RECHARGE.getCode())) {
                    //如果卡上有余额，说明没上表，不应该处理充值和补气
                    if(r.getData().getCardBalance().compareTo(BigDecimal.ZERO)>0){
                        return r;
                    }
                    //处理充值记录，读卡回写修改充值状态状态，否则不能充值。
                    RechargeRecord record = rechargeRecordBizApi.getByGasMeterCodeAndStatus(gasMeterCode,r.getData().getCustomerCode(), MoneyFlowStatusEnum.waite_to_meter.getCode()).getData();
                    if (record != null) {
                        RechargeRecordUpdateDTO rechargeRecordUpdateDTO = RechargeRecordUpdateDTO
                                .builder()
                                .id(record.getId())
                                .moneyFlowStatus(MoneyFlowStatusEnum.success.getCode()).build();
                        //处理充值记录状态，否则下次不能充值
                        rechargeRecordBizApi.update(rechargeRecordUpdateDTO);
                    }

                    SupplementGasRecord record1=supplementGasRecordBizApi.queryUnfinishedRecord(gasMeterCode).getData();
                    if(record1!=null && RepGasStatusEnum.WAIT_TO_METER.eq(record1.getRepGasStatus())){
                        supplementGasRecordBizApi.updateStatusToSuccess(gasMeterCode);
                    }
                }
            }
        }
        return r;
    }

    @Override
    public R<CardInfo> backGasCallBack(String gasMeterCode,String customerCode, JSONObject callBackData) {
        R<CardInfo> r=cardOperBizApi.backGasCallBack(gasMeterCode,callBackData);
        if(r.getIsSuccess()) {
            //退气之后作废充值记录,如果退完气，退费失败，撤销退费，这个时候只有走补气，补上次充值才能完成
            RechargeRecord record = rechargeRecordBizApi.getByGasMeterCodeAndStatus(gasMeterCode,customerCode, MoneyFlowStatusEnum.waite_to_meter.getCode()).getData();
            if (record != null) {
                RechargeRecordUpdateDTO rechargeRecordUpdateDTO = RechargeRecordUpdateDTO
                        .builder()
                        .id(record.getId())
                        .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue()).build();
                //处理充值记录状态，否则不能退费
                rechargeRecordBizApi.update(rechargeRecordUpdateDTO);
            }
        }
        return r;
    }

    @Override
    public R<CardInfo> buyGasCallBack(String gasMeterCode,String customerCode, JSONObject callBackData) {
        R<CardInfo> r=cardOperBizApi.buyGasCallBack(gasMeterCode,callBackData);
        if(r.getIsSuccess()) {
            RechargeRecord record = rechargeRecordBizApi.getByGasMeterCodeAndStatus(gasMeterCode,customerCode, MoneyFlowStatusEnum.waite_deal.getCode()).getData();
            if (record != null) {
                RechargeRecordUpdateDTO rechargeRecordUpdateDTO = RechargeRecordUpdateDTO
                        .builder()
                        .id(record.getId())
                        .moneyFlowStatus(MoneyFlowStatusEnum.waite_to_meter.getCode()).build();
                rechargeRecordBizApi.update(rechargeRecordUpdateDTO);
            }else{
                log.info("无充值数据");
            }
        }
        return r;
    }


    /**
     * 补气回调
     * @param gasMeterCode
     * @return
     */
     public R<SupplementGasRecord> repGasCallBack(String gasMeterCode, Long repGasRecordId, JSONObject callBackData){
         R<Boolean> r=cardOperBizApi.repGasCallBack(gasMeterCode,callBackData);
         if(r.getIsSuccess()){
             SupplementGasRecord record=supplementGasRecordBizApi.get(repGasRecordId).getData();
//             SupplementGasRecord record1=supplementGasRecordBizApi.queryUnfinishedRecord(gasMeterCode).getData();
//             if(record1==null){
//                 throw BizException.wrap("不存在未完成的补气记录不能写卡");
//             }
             SupplementGasRecordUpdateDTO updateDTO= new SupplementGasRecordUpdateDTO();
             updateDTO.setId(record.getId());
             updateDTO.setDataStatus(DataStatusEnum.NORMAL.getValue());
             updateDTO.setRepGasStatus(RepGasStatusEnum.WAIT_TO_METER.getCode());
             supplementGasRecordBizApi.update(updateDTO);
//             SupplementGasRecord record=supplementGasRecordBizApi.updateStatusAfterOperate(gasMeterCode).getData();
             //按需补气更新气表使用信息
             if (!RepGasMethodEnum.REP_PRE_CHARGE.eq(record.getRepGasMethod())) {
                 GasMeter meter=gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
                 GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
                 GasMeterInfo meterInfo=gasMeterInfoBizApi.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode()).getData();
                 ChargeUtils.setNullFieldAsZero(record);
                 BigDecimal val=record.getRepGas().compareTo(BigDecimal.ZERO)>0?record.getRepGas():record.getRepMoney();
                 repGasUpdateMeterInfo(meterInfo,version,val);
             }
             return R.success(record);
         }
         return R.success(null);
     }


    /**
     * 按需补气更新气表使用信息
     * @param meterInfo
     * @param version
     * @param supplement
     * @return
     */
    private void repGasUpdateMeterInfo( GasMeterInfo meterInfo, GasMeterVersion version, BigDecimal supplement) {
        log.info("补气修改表具使用情况");
        GasMeterInfoUpdateDTO update = new GasMeterInfoUpdateDTO();
        ChargeUtils.setNullFieldAsZero(meterInfo);
        update.setId(meterInfo.getId());
        //总使用金额和总使用气量（包含赠送），IC卡表读取不到直接维护，普表和物联网表冒泡结算维护
        if (AmountMarkEnum.GAS.eq(version.getAmountMark())) {
            update.setTotalUseGas(meterInfo.getTotalUseGas().add(supplement));
            update.setCycleUseGas(meterInfo.getCycleUseGas().add(supplement));
        } else {
            update.setTotalUseGasMoney(meterInfo.getTotalUseGasMoney().add(supplement));
        }
        //上次充值气量--其实是本次
        update.setValue1(supplement);
        //上上次充值气量
        update.setValue2(meterInfo.getValue1());
        //上上上次充值气量
        update.setValue3(meterInfo.getValue2());
        //累计充值上表次数，必须更新，否则上表校验不通过
        update.setTotalRechargeMeterCount(meterInfo.getTotalRechargeMeterCount().intValue() + 1);
        gasMeterInfoBizApi.update(update);
    }
}

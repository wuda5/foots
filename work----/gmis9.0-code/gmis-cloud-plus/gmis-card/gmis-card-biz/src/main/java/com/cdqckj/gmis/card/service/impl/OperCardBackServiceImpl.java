package com.cdqckj.gmis.card.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.card.enums.CardTypeEnum;
import com.cdqckj.gmis.card.enums.RepCardMethodEnum;
import com.cdqckj.gmis.card.enums.RepCardStatusEnum;
import com.cdqckj.gmis.card.service.*;
import com.cdqckj.gmis.card.util.CardUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterFactoryService;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.service.PriceMappingService;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 写卡数据加载
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
@DS("#thread.tenant")
public class OperCardBackServiceImpl extends SuperCenterServiceImpl implements OperCardBackService {
    @Autowired
    I18nUtil i18nUtil;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    CardInfoService cardInfoService;

    @Autowired
    CardRepRecordService cardRepRecordService;

    @Autowired
    CardRecRecordService cardRecRecordService;


    @Autowired
    GasMeterInfoService gasMeterInfoService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GasMeterFactoryService gasMeterFactoryService;

    @Autowired
    CardPriceSchemeService cardPriceSchemeService;

    /**
     * 写开户卡完成回调
     * @param callBackData
     * @return
     */
    public R<CardInfo> issueCardCallBack(Long id,JSONObject callBackData) {
        //回调卡号和当前表卡号是否一致需要校验
        log.info("发卡回调: id-{},读卡器回写结果{}",id,callBackData);
        CardInfo card = cardInfoService.getById(id);
        CardUtils.valid(callBackData);
        String cardNo=CardUtils.getCardNumber(callBackData);
        if(CardUtils.getOperType(callBackData)!=CardUtils.OPEN_ACCOUNT || StringUtils.isBlank(cardNo) || "0".equals(cardNo)){
            throw BizException.wrap("未知操作");
        }
        //更新发卡信息状态，
        CardInfo cardUpdateDTO = CardInfo.builder()
                .id(card.getId())
                .cardNo(CardUtils.getCardNumber(callBackData))
                .cardStatus(CardStatusEnum.ISSUE_CARD.getCode())
                .cardBalance(CardUtils.getBalance(callBackData))
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .cardChargeCount(CardUtils.getReChargeCount(callBackData))
                .totalAmount(CardUtils.getTotalAmount(callBackData))
                .build();
        if(cardUpdateDTO.getTotalAmount()==null) {
            cardUpdateDTO.setTotalAmount(new BigDecimal("0.00"));
        }
        cardInfoService.updateById(cardUpdateDTO);
        card.setId(card.getId());
        card.setCardNo(cardUpdateDTO.getCardNo());
        card.setCardStatus(CardStatusEnum.ISSUE_CARD.getCode());
        card.setCardBalance(CardUtils.getBalance(callBackData));
        card.setCardChargeCount(CardUtils.getReChargeCount(callBackData));
        card.setTotalAmount(cardUpdateDTO.getTotalAmount());
        return R.success(card);
    }

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    public R<CardInfo> buyGasCallBack(String gasMeterCode,JSONObject callBackData) {
        log.info("购气回调: 气表编号-{},读卡器回写结果{}",gasMeterCode,callBackData);
        CardUtils.valid(callBackData);
        CardInfo cardInfo=getCardInfoByCardNo(CardUtils.getCardNumber(callBackData));
        saveReadCardInfo(cardInfo,callBackData);
        return R.success(getCardInfoByGasMeterCode(gasMeterCode));
    }

    /**
     * 补卡写卡完成回调
     * @param id
     * @return
     */
    public R<CardRepRecord> repCardCallBack(Long id,JSONObject callBackData) {
        log.info("补卡回调: id-{},读卡器回写结果{}",id,callBackData);
        CardUtils.valid(callBackData);
        CardRepRecord repRecord=cardRepRecordService.getById(id);
//        CardRepRecord repRecord=cardRepRecordService.getOne(
//                new LbqWrapper<CardRepRecord>()
//                        .eq(CardRepRecord::getGasMeterCode,gasMeterCode)
//                .eq(CardRepRecord::getRepCardStatus,RepCardStatusEnum.WAITE_WRITE_CARD)
//        );
        BigDecimal b=CardUtils.getBalance(callBackData);
        if(RepCardMethodEnum.REP_PRE_RECHARGE.eq(repRecord.getRepCardMethod())){
            if(repRecord.getRepCardGas()!=null && repRecord.getRepCardGas().compareTo(BigDecimal.ZERO)>0){
                if(b.compareTo(BigDecimal.ZERO)<=0){
                    log.info("补卡量写卡失败，需清卡后重新写卡,{},{}",repRecord.getRepCardGas(),b);
                    throw BizException.wrap("补卡量写卡失败，需清卡后重新写卡");
                }
            }
            if(repRecord.getRepCardMoney()!=null && repRecord.getRepCardMoney().compareTo(BigDecimal.ZERO)>0){
                if(b.compareTo(BigDecimal.ZERO)<=0){
                    log.info("补卡量写卡失败，需清卡后重新写卡,{},{}",repRecord.getRepCardMoney(),b);
                    throw BizException.wrap("补卡量写卡失败，需清卡后重新写卡");
                }
            }
        }
        //更新补卡记录状态
        cardRepRecordService.updateById(CardRepRecord.builder()
                .id(repRecord.getId())
                .repCardStatus(RepCardStatusEnum.REP_CARD_SUCCESS.getCode())
                .build());
        return R.success(repRecord);
    }


    /**
     * 补气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    public R<Boolean> repGasCallBack(String gasMeterCode,JSONObject callBackData) {
        log.info("补气回调: 气表编号-{},读卡器回写结果{}",gasMeterCode,callBackData);
        CardUtils.valid(callBackData);
        //写卡成功，更新卡信息
        CardInfo card = getCardInfoByGasMeterCode(gasMeterCode);
        saveReadCardInfo(card,callBackData);
        //更新补气记录,Biz中去更新

        return R.success(true);
    }

    /**
     * 回收卡写卡完成回调
     * @param cardNo
     * @return
     */
    public R<Boolean> recCardCallBack(String cardNo,JSONObject callBackData) {
        log.info("回收卡日志: 卡号-{},读卡器回写结果{}",cardNo,callBackData);
        CardUtils.valid(callBackData);
        //回收卡回调
        if(StringUtils.isBlank(cardNo) || cardNo.equals("0")){
            return R.success(true);
        }
//        List<CardInfo> cards = cardInfoService.list(new LbqWrapper<CardInfo>()
//            .eq(CardInfo::getCardNo,cardNo)
//        );
//        StringBuilder gasMeterCode=new StringBuilder();
//        if(cards.size()>0){
//            for (CardInfo card : cards) {
//                gasMeterCode.append(card.getGasMeterCode());
//                gasMeterCode.append(",");
//            }
//            log.info("回收卡号【{}】对应多个气表[{}]，无法记录对哪个表的回收记录",cardNo,gasMeterCode.toString());
//            return R.success(true);
//        }else{
//            if(CollectionUtils.isNotEmpty(cards)){
//                CardInfo card=cards.get(0);
//                CardRecRecord saveDTO=CardRecRecord.builder()
//                        .cardNo(card.getCardNo())
//                        .gasMeterCode(card.getGasMeterCode())
//                        .build();
//                cardRecRecordService.save(saveDTO);
//            }
//        }
        //暂时只做个记录，以后在考虑其他业务
        return R.success(true);
    }


    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    public R<CardInfo> backGasCallBack(String gasMeterCode,JSONObject callBackData) {
        log.info("退气回调: 气表编号-{},读卡器回写结果{}",gasMeterCode,callBackData);
        CardUtils.valid(callBackData);
        //退气成功，如果是待写卡状态的充值单，可能写卡成功回调失败，这个只能人工主动清卡完成。
        if(CardUtils.getBalance(callBackData).compareTo(BigDecimal.ZERO)==0) {
            //退气回写卡信息表
            CardInfo card = getCardInfoByGasMeterCode(gasMeterCode);
            if(card==null){
                return R.success(null);
            }
            saveReadCardInfo(card,callBackData);
            GasMeter meter = gasMeterService.findGasMeterByCode(gasMeterCode);
            GasMeterVersion gasMeterVersion= gasMeterVersionService.getById(meter.getGasMeterVersionId());
            //补气又退气的场景暂时不考虑，理论上不应该存在

            //退气的时候，将上次置为0
            if(OrderSourceNameEnum.IC_RECHARGE.eq(gasMeterVersion.getOrderSourceName()) &&
                    AmountMarkEnum.MONEY.eq(gasMeterVersion.getAmountMark())
            ){
                if(CardUtils.getBalance(callBackData).compareTo(BigDecimal.ZERO)==0){
                    cardPriceSchemeService.backGas(gasMeterCode);
                }
            }
            return R.success(card);
        }else{
            throw BizException.wrap("卡退气失败");
        }
    }

    /**
     * 回收时读卡
     * @param callBackData
     * @return
     */
    public R<CardInfo> recCardReadCallBack(JSONObject callBackData){
        log.info("读卡数据：{}",callBackData.toJSONString());
        CardUtils.valid(callBackData);
        int oper = CardUtils.getOperType(callBackData);
        if (oper != CardUtils.READ_CARD) {
            return R.success(null);
        }
        String cardNo = CardUtils.getCardNumber(callBackData);
        if (StringUtils.isBlank(cardNo) || "0".equals(cardNo)) {
//            throw BizException.wrap("无卡号（新卡）");
            return R.success(null);
        }
        try {
            //卡上存在未上表数据不能充值和补气
            CardInfo card = getCardInfoByCardNo(cardNo);
            return R.success(card);
        }catch (BizException e){
            return R.success(null);
        }
    }


    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    public R<CardInfo> readCardCallBack(JSONObject callBackData){
        log.info("读卡数据：{}",callBackData.toJSONString());
        CardUtils.valid(callBackData);
        int oper=CardUtils.getOperType(callBackData);
        if(oper!=CardUtils.READ_CARD){
            throw BizException.wrap("异常数据进入");
        }
        String cardNo=CardUtils.getCardNumber(callBackData);
        if(StringUtils.isBlank(cardNo) ||"0".equals(cardNo)){
//            throw BizException.wrap("无卡号（新卡）");
            return R.success(null);
        }

        //会验证卡号重复或者无效
        CardInfo card=getCardInfoByCardNo(cardNo);

        String gasMeterCode=card.getGasMeterCode();
        if(CardTypeEnum.IC.eq(card.getCardType())) {
            //IC卡读取的时候，看是否上表，然后回写发卡状态
            Long realId=CardUtils.getMeterRealId(callBackData);
            GasMeter meter = gasMeterService.findGasMeterByCode(gasMeterCode);
            GasMeterVersion gasMeterVersion= gasMeterVersionService.getById(meter.getGasMeterVersionId());
            if("01".equals(gasMeterVersion.getCompanyCode())&& "0".equals(gasMeterVersion.getIcCardCoreMark())){
                //6a的表，如果充值金额为0，直接更新
               BigDecimal b= CardUtils.getBalance(callBackData);
               if(b.compareTo(BigDecimal.ZERO)==0){
                   if (!SendCardState.SENDED.getCode().equals(meter.getSendCardStauts())) {
                       GasMeter updateDTO = GasMeter.builder()
                               .id(meter.getId()).build();
                       updateDTO.setGasMeterRealId(CardUtils.getMeterRealId(callBackData));
                       updateDTO.setSendCardStauts(SendCardState.SENDED.getCode());
                       gasMeterService.updateById(updateDTO);
                   }
               }
            }else{
                if(realId!=null && realId!=0) {
                    if (!SendCardState.SENDED.getCode().equals(meter.getSendCardStauts())) {
                        GasMeter updateDTO = GasMeter.builder()
                                .id(meter.getId()).build();
                        updateDTO.setGasMeterRealId(CardUtils.getMeterRealId(callBackData));
                        updateDTO.setSendCardStauts(SendCardState.SENDED.getCode());
                        gasMeterService.updateById(updateDTO);

                        //发卡上表成功，需要记录上表的方案号
                        if(OrderSourceNameEnum.IC_RECHARGE.eq(gasMeterVersion.getOrderSourceName()) &&
                                AmountMarkEnum.MONEY.eq(gasMeterVersion.getAmountMark())
                        ){
                            cardPriceSchemeService.readCardCallBack(gasMeterCode);
                        }
                    }else{
                        //查看是否有金额，如果没有金额，说明金额已上表，方案也上去了？如何区分退气后读卡，退气的时候，将上次只置为0
                        if(OrderSourceNameEnum.IC_RECHARGE.eq(gasMeterVersion.getOrderSourceName()) &&
                                AmountMarkEnum.MONEY.eq(gasMeterVersion.getAmountMark())
                        ){
                            if(CardUtils.getBalance(callBackData).compareTo(BigDecimal.ZERO)==0){
                                cardPriceSchemeService.readCardCallBack(gasMeterCode);
                            }
                        }
                    }
                }
            }

        }
        saveReadCardInfo(card,callBackData);
        return R.success(card);
    }

    public CardInfo saveReadCardInfo(CardInfo card,JSONObject callBackData){
//        GasMeterInfo meterInfo=gasMeterInfoService.getByGasMeterCode(gasMeterCode);
        //这里只能从meterinfo中获取合计，因为卡上不能读取
        CardInfo cardUpdateDTO = CardInfo.builder()
                .id(card.getId())
                .cardBalance(CardUtils.getBalance(callBackData))
                .cardChargeCount(CardUtils.getReChargeCount(callBackData))
//                .totalAmount(settlementType.equals(AmountMarkEnum.MONEY)?
//                        meterInfo.getTotalChargeMoney():meterInfo.getTotalChargeGas())
                .build();
        if(cardUpdateDTO.getTotalAmount()==null) {
            cardUpdateDTO.setTotalAmount(new BigDecimal("0.00"));
        }
        card.setId(card.getId());
        card.setCardStatus(CardStatusEnum.ISSUE_CARD.getCode());
        card.setCardBalance(CardUtils.getBalance(callBackData));
        card.setCardChargeCount(CardUtils.getReChargeCount(callBackData));
        card.setTotalAmount(cardUpdateDTO.getTotalAmount());
        cardInfoService.updateById(cardUpdateDTO);
        return card;
    }

    private CardInfo getCardInfoByGasMeterCode(String gasMeterCode){
        CardInfo cardInfo=cardInfoService.getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasMeterCode)
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue()));
        return cardInfo;
    }

    private CardInfo getCardInfoByCardNo(String cardNo){

        List<CardInfo> list=cardInfoService.list(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getCardNo,cardNo)
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue())
        );

        if(CollectionUtils.isEmpty(list)){
            throw BizException.wrap("卡号无效");
        }
        if(list.size()>1){
            throw BizException.wrap("卡号重复，需要特殊处理");
        }
        return list.get(0);
    }


}

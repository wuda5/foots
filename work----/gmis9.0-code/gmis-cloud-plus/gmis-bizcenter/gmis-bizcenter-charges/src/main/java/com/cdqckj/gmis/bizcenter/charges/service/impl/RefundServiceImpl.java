package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.bizcenter.charges.service.BizOrderService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeRefundService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeSendOrderService;
import com.cdqckj.gmis.bizcenter.charges.service.RefundService;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.card.api.CardBackGasRecordBizApi;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.api.CardRepRecordBizApi;
import com.cdqckj.gmis.card.dto.CardBackGasRecordSaveDTO;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordUpdateDTO;
import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.card.util.CardUtils;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.ChargeRefundBizApi;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.api.RefundBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.enums.MoneyFlowStatusEnum;
import com.cdqckj.gmis.charges.enums.RefundMethodEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.invoice.InvoiceBizApi;
import com.cdqckj.gmis.invoice.dto.InvoiceChDTO;
import com.cdqckj.gmis.invoice.entity.Invoice;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import io.seata.common.util.CollectionUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 退费相关业务操作
 * @author tp
 * @date 2020-09-04
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Log4j2
public class RefundServiceImpl extends SuperCenterServiceImpl implements RefundService {

    @Autowired
    RefundBizApi refundBizApi;

    @Autowired
    UseGasTypeBizApi useGasTypeBizApi;

    @Autowired
    BusinessService businessService;

    @Autowired
    ChargeRefundBizApi chargeRefundBizApi;
    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    BizOrderService bizOrderService;
    @Autowired
    GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    CardInfoBizApi cardInfoBizApi;


    @Autowired
    CardRepRecordBizApi cardRepRecordBizApi;

    @Autowired
    InvoiceBizApi invoiceBizApi;
    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    ReadMeterDataIotApi readMeterDataIotApi;


    @Autowired
    CardBackGasRecordBizApi cardBackGasRecordBizApi;

    @Autowired
    ChargeSendOrderService chargeSendOrderService;
    @Autowired
    ChargeRefundService chargeRefundService;

    @Autowired
    CardBizService cardBizService;

    /**
     * 退费申请
     * @param applyDTO
     * @return
     */
    public R<ChargeRefund> apply(RefundApplySaveReqDTO applyDTO) {
        ChargeRecord record=chargeRecordBizApi.getChargeRecordByNo(applyDTO.getChargeNo()).getData();
//        String gasMeterCode=record.getGasMeterCode();
        if(applyDTO.getIsCardMeter()){
            if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0) {
                //有卡退费才做这个校验
                if (applyDTO.getWhetherNoCard().intValue() != 1) {
                    JSONObject readData = applyDTO.getReadData();
                    if (readData == null) {
                        throw BizException.wrap("未读卡");
                    } else {
                        CardInfo cardInfo = cardBizService.readCardCallBack(readData).getData();
                        if (!record.getGasMeterCode().equals(cardInfo.getGasMeterCode())) {
                            throw BizException.wrap("读卡器中卡信息和当前缴费用户信息不一致");
                        }
                        if (applyDTO.getIsCardRefund()) {
                            validCardInfo(applyDTO.getIsMoneyMeter(), record, readData);
//                    GasMeter meter=gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
//                    GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
                        }
                    }
                }
            }
        }
        return refundBizApi.apply(applyDTO);
    }

    private void validCardInfo(Boolean isMoenyMeter,ChargeRecord record,JSONObject readData){
        ChargeUtils.setNullFieldAsZero(record);
        String desc = isMoenyMeter ? "金额" : "气量";
        BigDecimal backValue = !isMoenyMeter ?
                record.getRechargeGas().add(record.getRechargeGiveGas()) :
                record.getRechargeMoney().add(record.getRechargeGiveMoney());
        backValue = backValue.setScale(2, RoundingMode.UP);
        BigDecimal balance = CardUtils.getBalance(readData);
        if (balance.subtract(new BigDecimal(0.01)).compareTo(backValue) > 0 ||
                balance.add(new BigDecimal(0.01)).compareTo(backValue) < 0
        ) {
            throw BizException.wrap("卡上" + desc + balance.toPlainString() + "不等于充值写卡" + desc + backValue.toPlainString() + "，不能退费");
        }
    }

    /**
     * 审核
     * @param auditDTO
     * @return
     */
    public R<Boolean> audit(RefundAuditSaveReqDTO auditDTO){
        R<Boolean> r=refundBizApi.audit(auditDTO);
        if(!auditDTO.getStatus()){
            checkAndSendOrder(auditDTO.getRefundId());
        }
        return r;
    }

    /**
     * 取消
     * @param refundId
     * @return
     */
    public  R<Boolean> cancelRefund(Long refundId){
        R<Boolean> r=refundBizApi.cancelRefund(refundId);
        checkAndSendOrder(refundId);
        return r;
    }
    /**
     * 加载退费详细信息
     * @param refundId
     * @return
     */
    public R<RefundLoadDTO> loadRefundAllInfo(Long refundId){
        R<RefundLoadDTO> r = refundBizApi.loadRefundAllInfo(refundId);
        if(!r.getIsSuccess()){
            return r;
        }
        ChargeRefund refund=r.getData().getChargeRefund();
        if(RefundStatusEnum.THIRDPAY_REFUND.eq(refund.getRefundStatus())){
            RefundCompleteDTO completeDTO=new RefundCompleteDTO();
            chargeRefundService.wxRefundQuery(refund.getRefundNo(),completeDTO);
           r.getData().setChargeRefund( refundComplete(completeDTO).getData().getChargeRefund());
        }
        if(RefundStatusEnum.REFUNDED.eq(r.getData().getChargeRefund().getRefundStatus())){
            if(r.getIsSuccess() && r.getData().getIsCardRefund()) {
                //有卡退费，如果退过了，就不用退了。
                if(r.getData().getChargeRefund().getWhetherNoCard()!=1){
                    if(validCardIsRefund(r.getData().getChargeRecord().getChargeNo())){
                        r.getData().setIsCardRefund(false);
                    }
                }
            }
        }
        return r;
    }

    private Boolean validCardIsRefund(String chargeNo){
        List<CardBackGasRecord> list = cardBackGasRecordBizApi.query(CardBackGasRecord.builder()
                .bizIdOrNo(chargeNo)
                .dataStatus(DataStatusEnum.NORMAL.getValue())
                .build()).getData();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            return true;
        }
        return false;
    }

    /**
     * 物联网表端计费表重发充值指令
     */
    private void checkAndSendOrder(Long refundId){
        ChargeRefund refund=chargeRefundBizApi.get(refundId).getData();
        ChargeRecord record=chargeRecordBizApi.getChargeRecordByNo(refund.getChargeNo()).getData();
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
            GasMeter meter=gasMeterBizApi.findGasMeterByCode(record.getGasMeterCode()).getData();
            GasMeterInfo meterInfo=gasMeterInfoBizApi.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode()).getData();
            GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
            if(OrderSourceNameEnum.REMOTE_RECHARGE.eq(version.getOrderSourceName())){
                chargeSendOrderService.refundCancelSendRechargeOrder(record,meter,meterInfo);
            }
        }
    }

    /**
     * 退费
     * @param refundDTO
     * @return
     */
    public R<RefundResultDTO> refund(RefundDTO refundDTO) {
        Long refundId=refundDTO.getRefundId();
        ChargeRefund refund=chargeRefundBizApi.get(refundId).getData();

        ChargeRecord record=validAndGetChargeRecord(refund.getChargeNo());
        JSONObject readData=refundDTO.getReadData();
        if(refundDTO.getIsCardRefund()){
            CardInfo cardInfo=cardBizService.readCardCallBack(readData).getData();
            if(!record.getGasMeterCode().equals(cardInfo.getGasMeterCode())){
                throw BizException.wrap("读卡器中卡信息和当前缴费用户信息不一致");
            }
            validCardInfo(refundDTO.getIsMoneyMeter(), record, readData);
        }
        if(RefundStatusEnum.THIRDPAY_REFUND.eq(refund.getRefundStatus())){
           throw BizException.wrap("第三方支付退费中，请稍后重试");
        }
        ChargeUtils.setNullFieldAsZero(record);

        R<RefundResultDTO> refundResult=refundBizApi.refund(refundId);
        RefundResultDTO resultDTO=refundResult.getData();
        if(!RefundMethodEnum.PAY_METHOD_REFUND.eq(refund.getBackMethodCode())){
            if(refundResult.getIsSuccess()){
                RefundResultDTO dto=refundResult.getData();
                GasMeter meter=gasMeterBizApi.findGasMeterByCode(dto.getChargeRecord().getGasMeterCode()).getData();
                GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
                if(refundDTO.getIsCardRefund()){
                    resultDTO.setIsCardRefund(refundDTO.getIsCardRefund());
                    if(validCardIsRefund(record.getChargeNo())){
                        resultDTO.setIsCardRefund(false);
                    }
                }
                //处理充值数据
                refundDealRechargeData(record,version,meter,refundResult.getData());

                //退费处理抄表数据
                refundDealArrearsData(dto,version);

                //退回业务
                refundDealBizData(dto,record);

                //作废调价补差记录
                if(CollectionUtils.isNotEmpty(dto.getAdjIds())){
                    bizOrderService.updateAdjustPriceBizStatus(dto.getAdjIds(),true);
                }

                //作废票据
                refundDealInvoices( record);
            }
            return refundResult;
        }else{
            RefundCompleteDTO completeDTO=new RefundCompleteDTO();
            chargeRefundService.refund(record,refund,completeDTO);
            if(completeDTO.getRefundStatus()==null){
                resultDTO.setIsLoopRequest(true);
                return refundResult;
            }else{
                return refundComplete(completeDTO);
            }
        }
    }

    @GlobalTransactional
    public  R<RefundResultDTO> refundComplete(RefundCompleteDTO refundInfo) {
        if(StringUtils.isBlank(refundInfo.getRefundNo())){
            log.error("退费回调单号为空");
            throw BizException.wrap("退费回调单号为空");
        }
        String refundNo=refundInfo.getRefundNo();
        log.info("微信退费回调，单号{}",refundNo);
        ChargeRefund refundCheck=refundBizApi.getByRefundNo(refundNo).getData();
        if(refundCheck==null){
            log.error("订单不存在，业务出问题，需要查找回调记录日志");
            throw BizException.wrap("订单不存在，业务出问题，需要查找回调记录日志");
        }
        if(RefundStatusEnum.REFUNDED.eq(refundCheck.getRefundStatus())){
            log.info("退费完成，无需继续回调");
            return R.success(null);
        }
        R<RefundResultDTO> refundResult=refundBizApi.refundComplete(refundInfo);

        if(refundResult.getIsSuccess() &&  refundInfo.getRefundStatus()) {
            RefundResultDTO dto = refundResult.getData();
            ChargeRecord record = dto.getChargeRecord();
            GasMeter meter = gasMeterBizApi.findGasMeterByCode(dto.getChargeRecord().getGasMeterCode()).getData();
            GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
//            refundResult.getData().setIsBackGas(false);

            //处理充值数据
            refundDealRechargeData(record, version, meter, refundResult.getData());

            //退费处理抄表数据
            refundDealArrearsData(dto, version);

            //退回业务
            refundDealBizData(dto, record);


            //作废调价补差记录
            if (CollectionUtils.isNotEmpty(dto.getAdjIds())) {
                bizOrderService.updateAdjustPriceBizStatus(dto.getAdjIds(), true);
            }
            //作废票据
            refundDealInvoices(record);
            return refundResult;
        }
        return refundResult;
    }

    private void  refundDealInvoices(ChargeRecord record){
        List<Invoice> invoices=invoiceBizApi.query(Invoice.builder().payNo(record.getChargeNo()).build())
                .getData();
        if(CollectionUtils.isNotEmpty(invoices)){
            Invoice invoice=invoices.get(0);
            InvoiceChDTO invoiceChDTO=new InvoiceChDTO();
            invoiceChDTO.setInvoiceId(invoice.getId());
            invoiceChDTO.setReason("退费");
            invoiceBizApi.invoiceCh(invoiceChDTO);
        }
    }

    private void  refundDealBizData(RefundResultDTO dto,ChargeRecord record){
        //作废补卡记录
        if(dto.getRepCardId()!=null){
            CardRepRecordUpdateDTO updateDTO=new CardRepRecordUpdateDTO();
            updateDTO.setId(dto.getRepCardId());
            updateDTO.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            cardRepRecordBizApi.update(updateDTO);
        }
        //作废发卡数据
        CardInfo card=cardInfoBizApi.getByGasMeterCode(record.getGasMeterCode(),record.getCustomerCode()).getData();
        if(card!=null && card.getChargeNo()!=null && record.getChargeNo().equals(card.getChargeNo())){
            CardInfoUpdateDTO updateCard= new CardInfoUpdateDTO();
            updateCard.setId(card.getId());
            updateCard.setCardStatus(CardStatusEnum.WAITE_ISSUE_CARD.getCode());
            updateCard.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            cardInfoBizApi.update(updateCard);
        }
    }

    private void refundDealArrearsData(RefundResultDTO dto,GasMeterVersion version){
        if(CollectionUtils.isNotEmpty(dto.getArrearIds())){
            if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())){
                List<ReadMeterDataIotUpdateDTO> updateDTOS=new ArrayList<>();
                for (Long arrearId : dto.getArrearIds()) {
//                    updateDTOS.add(ReadMeterDataIotUpdateDTO.builder()
//                            .id(arrearId)
//                            .chargeStatus(ChargeEnum.NO_CHARGE)
//                            .build());
                }
                R<Boolean> r= readMeterDataIotApi.updateBatch(updateDTOS);
                if(r.getIsError()){
                    throw new BizException(r.getMsg());
                }
            }else{
                R<Boolean> r= bizOrderService.updateReadMeterBizStatus(dto.getArrearIds(),true);
                if(r.getIsError()){
                    throw BizException.wrap(r.getMsg());
                }
            }
        }
    }

    private void refundDealRechargeData(ChargeRecord record,GasMeterVersion version,GasMeter meter,RefundResultDTO refundResult){
        //处理充值数据
        if(OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName()) && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
//            RechargeRecord rechargeRecord=rechargeRecordBizApi.getByChargeNo(record.getChargeNo()).getData();
//            //设置该退费是否需要退气
//            if(rechargeRecord!=null && MoneyFlowStatusEnum.waite_to_meter.eq(rechargeRecord.getMoneyFlowStatus())){
//                List<CardBackGasRecord> list = cardBackGasRecordBizApi.query(CardBackGasRecord.builder()
//                        .bizIdOrNo(record.getChargeNo())
//                        .dataStatus(DataStatusEnum.NORMAL.getValue())
//                        .build()).getData();
//                if (CollectionUtils.isEmpty(list) && refundResult.getChargeRefund().getWhetherNoCard()!=1) {
//                    refundResult.setIsBackGas(true);
//                }
//            }
            //处理充值数据，并下发相关指令
            GasMeterInfo meterInfo=saveMeterUseInfo(record);
            if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
                if(OrderSourceNameEnum.REMOTE_READMETER.eq(record.getChargeType()) ||
                        OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())
                ){
                    chargeSendOrderService.backFeeSendMeterAcountOrder(record,meter,meterInfo);
                    if(OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())){
                        if(meterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
                            chargeSendOrderService.sendCloseMeterOrder(record,meter,meterInfo);
                        }
                    }
                }
            }
        }
    }


    public GasMeterInfo saveMeterUseInfo(ChargeRecord record){
        if(record.getRechargeMoney()==null || record.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
            return null;
        }
        String gasMeterCode=record.getGasMeterCode();

        BigDecimal rechargeMoney=record.getRechargeMoney()==null?BigDecimal.ZERO:record.getRechargeMoney();
        BigDecimal rechargeGas=record.getRechargeGas()==null?BigDecimal.ZERO:record.getRechargeGas();

        BigDecimal giveMoney=record.getRechargeGiveMoney()==null?BigDecimal.ZERO:record.getRechargeGiveMoney();
        BigDecimal giveGas=record.getRechargeGiveGas()==null?BigDecimal.ZERO:record.getRechargeGiveGas();

        GasMeterInfo info=gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode,record.getCustomerCode()).getData();
        ChargeUtils.setNullFieldAsZero(info);
        GasMeterInfoUpdateDTO updateDto=new GasMeterInfoUpdateDTO();
        updateDto.setId(info.getId());
        //累计充值金额  所有充值的表都应该有
        updateDto.setTotalChargeMoney(info.getTotalChargeMoney()
                .subtract(rechargeMoney)
        );
        //累计充值气量 只有IC卡表且是气量表才会有
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())){
            updateDto.setTotalChargeGas(info.getTotalChargeGas()
                    .subtract(rechargeGas)
            );
        }
        //总使用金额和总使用气量（包含赠送），IC卡表读取不到直接维护，普表和物联网表冒泡结算维护
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType())) {
            updateDto.setTotalUseGasMoney(info.getTotalUseGasMoney()
                    .subtract(rechargeMoney)
                    .subtract(giveMoney)
            );
            if(AmountMarkEnum.GAS.eq(record.getSettlementType())) {
                updateDto.setTotalUseGas(info.getTotalUseGas()
                        .subtract(rechargeGas)
                        .subtract(giveGas)
                );
            }
        }
        //退费时已冻结计算，不应该在处理
//        if(OrderSourceNameEnum.REMOTE_READMETER.eq(record.getChargeType()) ||
//                OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())){
//            updateDto.setGasMeterBalance(updateDto.getGasMeterBalance().subtract(rechargeMoney));
//            updateDto.setGasMeterGive(updateDto.getGasMeterGive().subtract(giveMoney));
//            if(updateDto.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
//                throw BizException.wrap("费用已结算不能退费");
//            }
//        }
        //周期使用气量（金额）物联网表通过上报更新周期累计，IC卡表且是气量表维护：并且购气阶梯换算会使用到，定时任务清零0
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())) {
            updateDto.setCycleUseGas(info.getCycleUseGas()
                    .subtract(rechargeGas)
                    .subtract(giveGas)
            );
            if(updateDto.getCycleUseGas().compareTo(BigDecimal.ZERO)<0){
                updateDto.setCycleUseGas(BigDecimal.ZERO);
            }
        }

        //上次充值气量--本次清零
        updateDto.setValue1(BigDecimal.ZERO);
        //累计充值次数
        int count=info.getTotalChargeCount()==null?0:info.getTotalChargeCount().intValue();
        updateDto.setTotalChargeCount(count-1);
        //累计充值上表次数
        int mcount=info.getTotalRechargeMeterCount()==null?0:info.getTotalRechargeMeterCount().intValue();
        updateDto.setTotalRechargeMeterCount(mcount-1);
        gasMeterInfoBizApi.update(updateDto);
        info.setValue1(updateDto.getValue1());
        info.setTotalRechargeMeterCount(updateDto.getTotalRechargeMeterCount());
        info.setTotalChargeCount(updateDto.getTotalChargeCount());
        return info;
    }
    public R<Page<ChargeRefundResDTO>> pageList(PageParams<RefundListReqDTO> params){
        return chargeRefundBizApi.pageList(params);
    }

    private ChargeRecord validAndGetChargeRecord(String chargeNo){
//        Invoice invoice=invoiceBizApi.getEffectiveInvoice(chargeNo).getData();
//        if(invoice.)
        ChargeRecord record=chargeRecordBizApi.getChargeRecordByNo(chargeNo).getData();
        ChargeUtils.setNullFieldAsZero(record);
        GasMeter meter=gasMeterBizApi.findGasMeterByCode(record.getGasMeterCode()).getData();
        GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        //并不是无卡退气，才做校验
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 ) {
            if (OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName()) ) {
                //判断充值是否上表
                RechargeRecord rechargeRecord=rechargeRecordBizApi.getByChargeNo(record.getChargeNo()).getData();
                if(rechargeRecord!=null && MoneyFlowStatusEnum.success.eq(rechargeRecord.getMoneyFlowStatus())){
                    throw BizException.wrap("充值记录已上表，不能退费");
                }
            }
        }
        record.setSettlementType(version.getAmountMark());
        record.setChargeType(version.getOrderSourceName());
        return  record;
    }


}

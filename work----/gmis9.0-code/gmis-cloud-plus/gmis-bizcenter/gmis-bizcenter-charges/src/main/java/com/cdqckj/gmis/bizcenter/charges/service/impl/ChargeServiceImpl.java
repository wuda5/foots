package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.card.service.CardBizService;
import com.cdqckj.gmis.bizcenter.charges.service.*;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.ChangeMeterService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.biztemporary.ChangeMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.RemoveMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.ChargeChannelEnum;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import io.seata.common.util.CollectionUtils;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class ChargeServiceImpl extends SuperCenterServiceImpl implements ChargeService{



    @Autowired
    ChargeBizApi chargeBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;
    @Autowired
    RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    CardInfoBizApi cardInfoBizApi;

    @Autowired
    UseGasTypeBizApi useGasTypeBizApi;

    @Autowired
    BizOrderService bizOrderService;

    @Autowired
    ReadMeterDataIotApi readMeterDataIotApi;

    @Autowired
    GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    FunctionSwitchBizApi functionSwitchBizApi;

    @Autowired
    BusinessService businessService;

    @Autowired
    ChangeMeterService changeMeterService;
    @Autowired
    RemoveMeterService removeMeterService;

    @Autowired
    ChangeMeterRecordBizApi changeMeterRecordBizApi;
    @Autowired
    RemoveMeterRecordBizApi removeMeterRecordBizApi;

    @Autowired
    ChargePayServiceImpl chargePayServiceImpl;
    @Autowired
    ChargeSendOrderService chargeSendOrderService;

    @Autowired
    ChargeRefundService chargeRefundService;

    @Autowired
    RefundService refundService;
    @Autowired
    CardBizService cardBizService;
    /**
     * 柜台收费加载
     * @return
     */
    public R<ChargeLoadDTO> chargeLoad(ChargeLoadReqDTO param) {
        String gasMeterCode=param.getGasMeterCode();
        String customerCode=param.getCustomerCode();
        GasMeter meter = gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
        //IC卡表要判断，物联网表不用，只用在收费内部判断充值记录即可，IC卡表不判断，充值后可能引起不必要的麻烦，退费，清零等。
        GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        if (OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())) {
            if(param.getReadData()!=null){
                //读卡回调处理该处理的数据
                CardInfo cardInfo=cardBizService.readCardCallBack(param.getReadData()).getData();
                CardInfo cmCard=cardInfoBizApi.getByGasMeterCode(gasMeterCode,customerCode).getData();
                if(cmCard!=null){
                    if(cardInfo==null){
                        throw BizException.wrap("未知的IC卡");
                    }
                    if(!cardInfo.getCustomerCode().equals(customerCode) || !cardInfo.getGasMeterCode().equals(gasMeterCode)){
                        throw BizException.wrap("IC卡信息和当前户表信息不一致");
                    }
                    if(CardStatusEnum.WAITE_WRITE_CARD.eq(cmCard.getCardStatus())){
                        throw BizException.wrap("发卡未完成，处于待写卡状态，请先完成发卡");
                    }
                    if(CardStatusEnum.ISSUE_CARD.eq(cmCard.getCardStatus())){
                        if (SendCardState.WAITE_SEND.eq(meter.getSendCardStauts())) {
                            throw BizException.wrap("卡未上表");
                        }
                    }
                }else{
                    throw BizException.wrap("未发卡");
                }
            }
//            if (SendCardState.WAITE_SEND.eq(meter.getSendCardStauts())) {
//                throw BizException.wrap("IC卡表需先发卡且卡必须上表，若上表成功请进行读卡");
//            }
            //判断卡上是否还有余额
            Boolean haveBalance = cardInfoBizApi.rechargeCheckCardBalance(gasMeterCode,customerCode).getData();
            if (haveBalance) {
                throw BizException.wrap("卡上余额未上表不能充值");
            }
        }
        //是否存在未完成的支付订单
        ChargeRecord record=chargeBizApi.getUnCompleteChargeRecord(gasMeterCode,customerCode).getData();
        if(record!=null){
            if(ChargePayMethodEnum.WECHATPAY.eq(record.getChargeMethodCode())){
                ChargeCompleteDTO dto=chargePayServiceImpl.wxQueryByLoad(record.getChargeNo());
                if(dto.getChargeStatus()){
                   chargeComplete(dto).getData();
                }else{
                    throw BizException.wrap("存在未完成收费单");
                }
            }else{
                throw BizException.wrap("存在未完成收费单");
            }
        }
        //是否存在未完成的支付订单
        ChargeRefund refund=chargeBizApi.getUnCompleteChargeRefund(gasMeterCode,customerCode).getData();
        if(refund!=null){
            if(RefundStatusEnum.THIRDPAY_REFUND.eq(refund.getRefundStatus())){
                RefundCompleteDTO completeDTO=new RefundCompleteDTO();
                chargeRefundService.wxRefundQuery(refund.getRefundNo(),completeDTO);
                if(completeDTO.getRefundStatus()){
                    refundService.refundComplete(completeDTO);
                }else{
                    throw BizException.wrap("存在未完成退费单");
                }
            }else{
                throw BizException.wrap("存在未完成退费单");
            }
        }

        return chargeBizApi.chargeLoad(param);
    }

    /**
     * 收费发起收费，如果现金支付，会直接支付完成，非现金支付只会存入收费记录和收费项明细，但是状态为待支付。
     * @param infoDTO
     * @return
     */
    public R<ChargeResultDTO> charge(ChargeDTO infoDTO) {
        GasMeter meter=gasMeterBizApi.findGasMeterByCode(infoDTO.getGasMeterCode()).getData();
        //IC卡表要判断，物联网表不用，只用在收费内部判断充值记录即可，IC卡表不判断，充值后可能引起不必要的麻烦，退费，清零等。
        GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        ChargeRecord record=infoDTO.getChargeRecord();
        record.setChargeType(version.getOrderSourceName());
        record.setSettlementType(version.getAmountMark());
        record.setGasMeterCode(infoDTO.getGasMeterCode());
        infoDTO.setMeter(meter);
        chargeValid(record,infoDTO.getScene(),meter.getSendCardStauts());

        GasMeterInfo info=gasMeterInfoBizApi.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode()).getData();
        infoDTO.setGasMeterInfo(info);
        R<ChargeResultDTO> chargeResult=chargeBizApi.charge(infoDTO);
        ChargeResultDTO resultDTO=chargeResult.getData();
        record=chargeResult.getData().getChargeRecord();
        record.setChargeType(version.getOrderSourceName());
        record.setSettlementType(version.getAmountMark());
        String chargeNo=record.getChargeNo();
        if(record.getChargeChannel()==null){
            record.setChargeChannel(ChargeChannelEnum.GT.getCode());
        }
        //根据现金非现金处理场景业务单状态
        if (!ChargePayMethodEnum.CASH.eq(infoDTO.getChargeRecord().getChargeMethodCode())) {
            dealSceneChargeByUnCashPay(infoDTO,chargeResult.getData());
            //柜台第三方支付，需要发起支付
            if(ChargeChannelEnum.GT.eq(record.getChargeChannel())) {
                thirdPay(resultDTO,infoDTO,record);
            }
        }else{
            dealSceneChargeByCashPay(infoDTO,chargeResult.getData());
            //现金支付完成直接处理燃气费
            dealGasFee(chargeResult.getData());
            //处理充值数据，如果是物联网表下发指令
            dealRechargeAndSendOrder(record,meter,chargeResult.getData().getGasMeterInfo());
        }
        return chargeResult;
    }

    private  R<ChargeResultDTO>  thirdPay(ChargeResultDTO resultDTO,ChargeDTO infoDTO,ChargeRecord record){
        //下单
        String xid = RootContext.unbind();
        //挂起发起微信支付的事务，独立事务防止外部异常导致支付记录回滚
        try {
            log.info("第三方支付下单:{}",record.toString());
            chargePayServiceImpl.pay(resultDTO, infoDTO);
            log.info("下单执行，待业务处理");
        } catch (Exception e) {
            RootContext.bind(xid);
            try {
                GlobalTransactionContext.reload(xid).commit();
            } catch (Exception e1) {
                log.error("请求异常，事务处理失败:", e1);
                throw BizException.wrap("请求异常，事务处理失败");
            }
            ChargeRecordUpdateDTO updateDTO = new ChargeRecordUpdateDTO();
            updateDTO.setId(record.getId());
            updateDTO.setChargeStatus(ChargeStatusEnum.CHARGE_EXEC.getCode());
            updateDTO.setPayErrReason("未知异常");
            chargeRecordBizApi.update(updateDTO);
            throw e;
        }

        RootContext.bind(xid);
        try {
            GlobalTransactionContext.reload(xid).commit();
        } catch (Exception e1) {
            log.error("请求异常，事务处理失败:", e1);
            throw BizException.wrap("请求异常，事务处理失败");
        }

        //提交完事务才进行业务业务回调处理，否则可能导致事务回滚数据丢失
        try {
            // TODO: 运行在全局事务外的业务逻辑
            if ("SUCCESS".equals(resultDTO.getThirdPayStatus())) {
                log.info("下单成功，业务处理");
                ChargeCompleteDTO cdto = ChargeCompleteDTO.builder().chargeNo(record.getChargeNo()).chargeStatus(true).build();
                return chargeComplete(cdto);
            } else if ("FAIL".equals(resultDTO.getThirdPayStatus())) {
                log.info("下单失败，业务处理");
                ChargeCompleteDTO cdto = ChargeCompleteDTO.builder().chargeNo(record.getChargeNo())
                        .chargeStatus(false)
                        .remark(resultDTO.getPayErrReason())
                        .build();
                return chargeComplete(cdto);
            } else if ("EXCEPTION".equals(resultDTO.getThirdPayStatus())) {
                log.info("下单异常，记录异常单据");
                ChargeRecordUpdateDTO updateDTO = new ChargeRecordUpdateDTO();
                updateDTO.setId(record.getId());
                updateDTO.setChargeStatus(ChargeStatusEnum.CHARGE_EXEC.getCode());
                updateDTO.setPayErrReason(resultDTO.getPayErrReason());
                chargeRecordBizApi.update(updateDTO);
                throw BizException.wrap("交易异常："+resultDTO.getPayErrReason());
            } else{
                log.info("待重新获取收费单状态");
            }
        } catch (Exception e) {
            log.error("支付结果返回，但业务处理异常", e);
            RootContext.bind(xid);
            throw BizException.wrap("支付成功，但业务处理异常");
        }
        return R.success(resultDTO);
    }

    private void dealRechargeAndSendOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(ChargeStatusEnum.CHARGED.eq(record.getChargeStatus())){
            if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
                if(meterInfo==null) {
                    //gasMeterInfo再处理业务后创建的，那么需要处理业务后保存数据，如果处理业务前保存的这里就不做处理
                    meterInfo = saveMeterUseInfo(record);
                }
                //物联网表
                if (OrderSourceNameEnum.REMOTE_RECHARGE.eq(record.getChargeType())) {
                    chargeSendOrderService.sendRechargeOrder(record, meter, meterInfo);
                } else if (OrderSourceNameEnum.REMOTE_READMETER.eq(record.getChargeType()) ||
                        OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())) {
                    chargeSendOrderService.sendMeterAcountOrder(record, meter, meterInfo);
                    chargeSendOrderService.sendOpenMeterOrder(record, meter, meterInfo);
                }
            }
        }
    }

    /**
     * 收费发起收费，非现金支付异步处理业务状态，会直接支付完成
     * @param infoDTO
     * @return
     */
    @GlobalTransactional
    public R<ChargeResultDTO> chargeComplete(ChargeCompleteDTO infoDTO) {
        if(StringUtils.isBlank(infoDTO.getChargeNo())){
            log.error("支付回调单号为空");
            throw BizException.wrap("支付回调单号为空");
        }
        ChargeRecord record= chargeRecordBizApi.getChargeRecordByNo(infoDTO.getChargeNo())
                .getData();
        if(record==null){
            log.error("订单不存在，业务出问题，需要查找回调记录日志");
            throw BizException.wrap("订单不存在，业务出问题，需要查找回调记录日志");
        }
        if(ChargeStatusEnum.CHARGED.eq(record.getChargeStatus())){
            log.info("订单已完成处理，不用微信继续回调");
            return R.success(null);
        }

        GasMeter meter=gasMeterBizApi.findGasMeterByCode(record.getGasMeterCode()).getData();
        infoDTO.setMeter(meter);
        //IC卡表要判断，物联网表不用，只用在收费内部判断充值记录即可，IC卡表不判断，充值后可能引起不必要的麻烦，退费，清零等。
        GasMeterVersion version=gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        record.setChargeType(version.getOrderSourceName());
        record.setSettlementType(version.getAmountMark());
        infoDTO.setRecord(record);
        R<ChargeResultDTO> chargeResult=chargeBizApi.chargeComplete(infoDTO);
        if(chargeResult.getIsSuccess() &&  infoDTO.getChargeStatus()) {
            record=chargeResult.getData().getChargeRecord();
            record.setChargeType(version.getOrderSourceName());
            record.setSettlementType(version.getAmountMark());
            //收费成功处理以下数据
            //处理场景收费
            dealSceneChargeCompleteByUnCashPay(record);
            //处理燃气费
            dealGasFee(chargeResult.getData());
            //处理充值数据，如果是物联网表下发指令
            dealRechargeAndSendOrder(record,meter,chargeResult.getData().getGasMeterInfo());
        }
        return chargeResult;
    }

    /**
     * 更新气表使用信息
     * @param record
     * @return
     */
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
        updateDto.setTotalChargeMoney(info.getTotalChargeMoney().add(rechargeMoney));
        //累计充值气量 只有IC卡表且是气量表才会有
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())){
            updateDto.setTotalChargeGas(info.getTotalChargeGas().add(rechargeGas));
        }
        //总使用金额和总使用气量（包含赠送），IC卡表读取不到直接维护，普表和物联网表冒泡结算维护
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType())) {
            updateDto.setTotalUseGasMoney(info.getTotalUseGasMoney()
                    .add(rechargeMoney)
                    .add(giveMoney)
            );
            if(AmountMarkEnum.GAS.eq(record.getSettlementType())) {
                updateDto.setTotalUseGas(info.getTotalUseGas()
                        .add(rechargeGas)
                        .add(giveGas)
                );
            }
        }
        //远程抄表 和 中心预付费表 充值要记录表端余额用于结账和下发显示
        if(OrderSourceNameEnum.REMOTE_READMETER.eq(record.getChargeType()) ||
                OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())){
            updateDto.setGasMeterBalance(info.getGasMeterBalance().add(rechargeMoney));
            updateDto.setGasMeterGive(info.getGasMeterGive().add(giveMoney));
        }
        //周期使用气量（金额）物联网表通过上报更新周期累计，IC卡表且是气量表维护：并且购气阶梯换算会使用到，定时任务清零0
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())) {
            updateDto.setCycleUseGas(info.getCycleUseGas()
                    .add(rechargeGas)
                    .add(giveGas)
            );
        }

        //累计计算近三次充值
        BigDecimal chargeVal;
        //只有IC卡表气量表是气量，其他都是金额
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())) {
            chargeVal=rechargeGas.add(giveGas);
        }else{
            chargeVal=rechargeMoney.add(giveMoney);
        }
        if(!OrderSourceNameEnum.REMOTE_RECHARGE.eq(record.getChargeType()) ){
            if(info.getValue1().compareTo(BigDecimal.ZERO)!=0){
                updateDto.setValue1(chargeVal);//上次充值气量--其实是本次
                updateDto.setValue2(info.getValue1());//上上次充值气量
                updateDto.setValue3(info.getValue2());//上上上次充值气量
            }else{
                updateDto.setValue1(chargeVal);//上次充值气量--其实是本次
            }
            //累计充值上表次数
            int mcount=info.getTotalRechargeMeterCount()==null?0:info.getTotalRechargeMeterCount().intValue();
            updateDto.setTotalRechargeMeterCount(mcount+1);
        }
        //累计充值次数
        int count=info.getTotalChargeCount()==null?0:info.getTotalChargeCount().intValue();
        updateDto.setTotalChargeCount(count+1);


        //当前价格，表端计价方案号，应该定时任务获取后更新
        info.setCycleUseGas(updateDto.getCycleUseGas());
        info.setGasMeterBalance(updateDto.getGasMeterBalance());
        info.setCycleChargeGas(updateDto.getCycleChargeGas());
        info.setTotalUseGas(updateDto.getTotalUseGas());
        info.setTotalUseGasMoney(updateDto.getTotalUseGasMoney());
        info.setTotalChargeGas(updateDto.getTotalChargeGas());
        info.setTotalChargeMoney(updateDto.getTotalChargeMoney());
        info.setTotalRechargeMeterCount(updateDto.getTotalRechargeMeterCount());
        info.setTotalChargeCount(updateDto.getTotalRechargeMeterCount());//累计充值次数
        info.setValue1(updateDto.getValue1());//上次充值气量--其实是本次
        info.setValue2(updateDto.getValue2());//上上次充值气量
        info.setValue3(updateDto.getValue3());//上上上次充值气量
        gasMeterInfoBizApi.update(updateDto);
        return info;
    }


    /**
     * 不管现金非现金，燃气费处理方式一致。
     * @param chargeResult
     */
    private void dealGasFee(ChargeResultDTO chargeResult) {
        ChargeResultDTO dto = chargeResult;
        //处理欠费记录
        if (CollectionUtils.isNotEmpty(dto.getArrearIds())) {
            GasMeter meter = gasMeterBizApi.findGasMeterByCode(dto.getChargeRecord().getGasMeterCode()).getData();
            GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
            //处理远程抄表
            if (OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())) {
                R<Integer> r= readMeterDataIotApi.updateByGasOweCode(dto.getArrearIds());
//                List<ReadMeterDataIotUpdateDTO> updateDTOS = new ArrayList<>();
//                for (Long arrearId : dto.getArrearIds()) {
//                    updateDTOS.add(ReadMeterDataIotUpdateDTO.builder()
//                            .id(arrearId)
//                            .chargeStatus(ChargeEnum.CHARGED)
//                            .build());
//                }
//                R<Boolean> r = readMeterDataIotApi.updateBatch(updateDTOS);
                if (r.getIsError()) {
                    throw new BizException(r.getMsg());
                }
            } else {
                //处理普表抄表
                R<Boolean> r = bizOrderService.updateReadMeterBizStatus(dto.getArrearIds(), false);
                if (r.getIsError()) {
                    throw new BizException(r.getMsg());
                }
            }
        }
        //处理调价补差
        if (CollectionUtils.isNotEmpty(dto.getAdjustPriceIds())) {
            bizOrderService.updateAdjustPriceBizStatus(dto.getAdjustPriceIds(), false);
        }
    }

    /**
     * 如果是现金支付，直接应该是正常处理完成
     * @param infoDTO
     */
    private void dealSceneChargeByCashPay(ChargeDTO infoDTO,ChargeResultDTO resultDto){
        ChargeRecord record =resultDto.getChargeRecord();
        if(StringUtils.isNotBlank(infoDTO.getScene()) && infoDTO.getBizNoOrId()!=null) {
            if (ChargePayMethodEnum.CASH.eq(record.getChargeMethodCode())) {
                if(TolltemSceneEnum.ISSUE_CARD.eq(infoDTO.getScene())) {
                    CardInfoUpdateDTO updateCard = CardInfoUpdateDTO.builder().id(infoDTO.getBizNoOrId()).build();
                    updateCard.setCardStatus(CardStatusEnum.WAITE_WRITE_CARD.getCode());
                    updateCard.setDataStatus(DataStatusEnum.NORMAL.getValue());
                    cardInfoBizApi.update(updateCard);
                }else if(TolltemSceneEnum.CHANGE_METER.eq(infoDTO.getScene())){
                    changeMeterService.updateAfterPaid(infoDTO.getBizNoOrId());
                }else if(TolltemSceneEnum.DIS_METER.eq(infoDTO.getScene())){
                    removeMeterService.updateAfterPaid(infoDTO.getBizNoOrId());
                }
            }
        }

        //处理场景收费项
        if (CollectionUtils.isNotEmpty(resultDto.getSceneIds())) {
            R<Boolean> r = bizOrderService.updateSceneBizStatus(resultDto.getSceneIds(), false);
            if (r.getIsError()) {
                throw new BizException(r.getMsg());
            }
        }
    }

    /**
     * 如果是非现金支付：当前业务处于支付中状态，业务有效，如何防止重复办理？所有业务表正常都应该有支付状态
     * 待支付，支付中，已支付，支付完成：特殊说明支付失败业务无效--通过收费结果控制数据是否有效。
     * @param infoDTO
     */
    private void dealSceneChargeByUnCashPay(ChargeDTO infoDTO,ChargeResultDTO resultDto){
        ChargeRecord record =resultDto.getChargeRecord();
        if(StringUtils.isNotBlank(infoDTO.getScene()) && infoDTO.getBizNoOrId()!=null) {
            String chargeNo=record.getChargeNo();
            if (!ChargePayMethodEnum.CASH.eq(infoDTO.getChargeRecord().getChargeMethodCode())) {
                if(TolltemSceneEnum.ISSUE_CARD.eq(infoDTO.getScene())) {
                    CardInfoUpdateDTO updateCard = CardInfoUpdateDTO.builder().id(infoDTO.getBizNoOrId()).build();
                    updateCard.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
                    updateCard.setChargeNo(chargeNo);
                    cardInfoBizApi.update(updateCard);
                }else if(TolltemSceneEnum.CHANGE_METER.eq(infoDTO.getScene())){
                    ChangeMeterRecordUpdateDTO update=new ChangeMeterRecordUpdateDTO();
                    update.setId(infoDTO.getBizNoOrId());
                    update.setChargeNo(chargeNo);
                    update.setDeleteStatus(DeleteStatusEnum.DELETE.getValue());
                    changeMeterRecordBizApi.update(update);
                }else if(TolltemSceneEnum.DIS_METER.eq(infoDTO.getScene())){
                    RemoveMeterRecordUpdateDTO update=new RemoveMeterRecordUpdateDTO();
                    update.setId(infoDTO.getBizNoOrId());
                    update.setChargeNo(chargeNo);
                    update.setDeleteStatus(DeleteStatusEnum.DELETE.getValue());
                    removeMeterRecordBizApi.update(update);
                }
            }
        }

        //处理场景收费项
        if (CollectionUtils.isNotEmpty(resultDto.getSceneIds())) {
            R<Boolean> r = bizOrderService.updateSceneBizStatus(resultDto.getSceneIds(), false);
            if (r.getIsError()) {
                throw new BizException(r.getMsg());
            }
        }
    }


    /**
     * 非现金支付收费完成处理场景数据状态
     * @param record
     */
    private void dealSceneChargeCompleteByUnCashPay(ChargeRecord record){
        String chargeNo=record.getChargeNo();
        // check card
        CardInfo card= cardInfoBizApi.getByChargeNO(record.getChargeNo()).getData();
        if(card!=null){
            CardInfoUpdateDTO updateCard= CardInfoUpdateDTO.builder().id(card.getId())
                    .chargeNo(chargeNo).build();
            updateCard.setCardStatus(CardStatusEnum.WAITE_WRITE_CARD.getCode());
            updateCard.setDataStatus(DataStatusEnum.NORMAL.getValue());
            cardInfoBizApi.update(updateCard);
        }
        // check dis meter
        RemoveMeterRecord rmeter=removeMeterRecordBizApi.getOneByChargeNo(chargeNo).getData();
        if(rmeter!=null){
            removeMeterService.updateAfterPaid(chargeNo);
        }

        // check change meter
        ChangeMeterRecord cmeter=changeMeterRecordBizApi.getOneByChargeNo(chargeNo).getData();
        if(cmeter!=null){
            changeMeterService.updateAfterPaid(chargeNo);
        }
    }

    public R<Page<ChargeRecordResDTO>> pageList(PageParams<ChargeListReqDTO> params){
        return chargeRecordBizApi.pageList(params);
    }


    private void chargeValid(ChargeRecord record,String scene,String sendCardStatus){
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
            if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType())){
                //需要判断当前是否是发卡来缴费的，如果是发卡来缴费，不做校验，其他方式充值缴费需要校验卡状态
                //6a的表现在是卡库原因无法获取表ID,其他公司的表也存在这种情况，所以在发卡时状态直接改变，如果发卡没有充值，这个校验不会生效，没法控制
                if(scene==null){
                    if(SendCardState.WAITE_SEND.eq(sendCardStatus)){
                        throw BizException.wrap("IC卡表需先发卡且卡必须上表，若上表成功请进行读卡");
                    }
                }else{
                    if(!TolltemSceneEnum.ISSUE_CARD.eq(scene)){
                        if(SendCardState.WAITE_SEND.eq(sendCardStatus)){
                            throw BizException.wrap("IC卡表需先发卡且卡必须上表，若上表成功请进行读卡");
                        }
                    }
                }
                //判断卡上是否还有余额
                Boolean haveBalance=cardInfoBizApi.rechargeCheckCardBalance(record.getGasMeterCode(),record.getCustomerCode()).getData();
                if(haveBalance){
                    throw BizException.wrap("系统记录卡上余额未上表不能充值，请确认是否读卡");
                }
            }
        }
    }
}

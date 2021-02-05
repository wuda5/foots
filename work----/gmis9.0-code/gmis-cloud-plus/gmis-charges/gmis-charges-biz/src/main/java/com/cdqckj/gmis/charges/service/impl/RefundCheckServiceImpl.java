package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.charges.dto.RefundCheckDTO;
import com.cdqckj.gmis.charges.dto.RefundLoadDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.enums.MoneyFlowStatusEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.enums.SummaryBillStatusEnum;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 业务实现类
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RefundCheckServiceImpl implements RefundCheckService {
    @Autowired
    GasMeterInfoService gasMeterInfoService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    ChargeRecordService chargeRecordService;
    @Autowired
    RechargeRecordService rechargeRecordService;
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    ChargeRefundService chargeRefundService;
    @Autowired
    BusinessHallService businessHallService;
    @Autowired
    I18nUtil i18nUtil;
    @Override
    public RefundCheckDTO validRefund(String chargeNo){
        RefundCheckDTO dto=new RefundCheckDTO();
        dto.setIsCardMeter(false);
        dto.setIsCardRefund(false);
        dto.setIsMoneyMeter(false);
        try{
            ChargeRecord chargeRecord= chargeRecordService.getOne( new LbqWrapper<ChargeRecord>()
                    .eq(ChargeRecord:: getChargeNo,chargeNo)
                    .eq(ChargeRecord:: getDataStatus, DataStatusEnum.NORMAL.getValue()));
            if(chargeRecord==null){
                //异常进入
                dto.setNonRefund("收费单不存在");
                dto.setChargeRefund(null);
                dto.setChargeRecord(chargeRecord);
                dto.setIsApplyRefund(false);
                return dto;
            }else{
                //判断是否已结账，如果已结账不允许申请退费，需要先反结账。没有扎帐的情况要限制1个月，因为可能燃气公司财务自己做帐不使用扎帐功能
                if(SummaryBillStatusEnum.BILLED.eq(chargeRecord.getSummaryBillStatus())){
//                    throw BizException.wrap("该收费单已扎帐，反扎帐后才能进行退费申请");
                    dto.setNonRefund("该收费单已扎帐，反扎帐后才能进行退费申请");
                    dto.setChargeRefund(null);
                    dto.setChargeRecord(chargeRecord);
                    dto.setIsApplyRefund(false);
                    return dto;
                }
//                if(chargeRecord.getCreateTime().isBefore(LocalDateTime.now().minusMonths(1))){
//                    dto.setNonRefund("收费单已超过一个月，不能退费");
//                    dto.setChargeRefund(null);
//                    dto.setChargeRecord(chargeRecord);
//                    dto.setIsApplyRefund(false);
//                    return dto;
//                }
                dto.setChargeRecord(chargeRecord);
                if(ChargeStatusEnum.CHARGE_FAILURE.eq(chargeRecord.getChargeStatus())) {
                    dto.setNonRefund("收费失败");
                    dto.setIsApplyRefund(false);
                    return dto;
                }
                if(ChargeStatusEnum.UNCHARGE.eq(chargeRecord.getChargeStatus())) {
                    dto.setNonRefund("收费未完成");
                    dto.setIsApplyRefund(false);
                    return dto;
                }
                if (StringUtils.isNotBlank(chargeRecord.getRefundStatus())) {
                    if (RefundStatusEnum.REFUNDED.eq(chargeRecord.getRefundStatus())) {
                        dto.setNonRefund("已退费");
                        dto.setIsApplyRefund(false);
                        return dto;
                    } else if (RefundStatusEnum.APPLY.eq(chargeRecord.getRefundStatus())) {
                        dto.setNonRefund("已申请，退费处理中");
                        dto.setIsApplyRefund(false);
                        return dto;
                    }
                }
                ChargeUtils.setNullFieldAsZero(chargeRecord);
                dto.setIsApplyRefund(true);
                validAccount(chargeRecord,dto);
                if(!dto.getIsApplyRefund()){
                    return dto;
                }
                meterCheck(chargeRecord,dto);
                if(!dto.getIsApplyRefund()){
                    return dto;
                }
                dto.setIsApplyRefund(true);
            }
        }catch (BizException e){
            dto.setIsApplyRefund(false);
            dto.setNonRefund(e.getMessage());
        }catch (Exception e){
            log.error("检查缴费单是否可以退费异常",e);
            dto.setIsApplyRefund(false);
            dto.setNonRefund("未知原因");
        }
        return dto;
    }

    @Override
    public RefundLoadDTO loadInfo(Long refundId){
        RefundLoadDTO dto=new RefundLoadDTO();
        ChargeRefund refund =chargeRefundService.getById(refundId);
        dto.setChargeRefund(refund);
        ChargeRecord chargeRecord= chargeRecordService.getOne( new LbqWrapper<ChargeRecord>()
                .eq(ChargeRecord:: getChargeNo,refund.getChargeNo())
                .eq(ChargeRecord:: getDataStatus,DataStatusEnum.NORMAL.getValue()));
        dto.setChargeRecord(chargeRecord);
        dto.setIsCardMeter(false);
        dto.setIsCardRefund(false);
        dto.setIsMoneyMeter(false);
        if(chargeRecord.getRechargeMoney()!=null && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
            GasMeter meter=gasMeterService.findGasMeterByCode(chargeRecord.getGasMeterCode());
            GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
            RechargeRecord rechargeRecord= rechargeRecordService.getOne(
                    new LbqWrapper<RechargeRecord>()
                            .eq(RechargeRecord:: getChargeNo,chargeRecord.getChargeNo()));
            dto.setRechargeRecord(rechargeRecord);
            if(OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())){
                dto.setIsCardMeter(true);
                dto.setIsMoneyMeter(AmountMarkEnum.MONEY.eq(version.getAmountMark()));
                if(MoneyFlowStatusEnum.waite_to_meter.eq(rechargeRecord.getMoneyFlowStatus())){
                    dto.setIsCardRefund(true);
                }
            }
        }
        return dto;
    }


    private void validAccount(ChargeRecord chargeRecord,RefundCheckDTO dto){
        if (chargeRecord.getPrechargeMoney()!=null &&
                chargeRecord.getPrechargeMoney().compareTo(new BigDecimal("0.00")) > 0) {
            CustomerAccount account= customerAccountService.getOne(
                    new LbqWrapper<CustomerAccount>()
                            .eq(CustomerAccount:: getCustomerCode,chargeRecord.getCustomerCode()));
            if (account.getAccountMoney().compareTo(chargeRecord.getPrechargeMoney()) < 0) {
                dto.setIsApplyRefund(false);
                dto.setNonRefund("账户发生变更且余额不足，不能退费");
            }
        }
    }

    private void meterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        ChargeUtils.setNullFieldAsZero(chargeRecord);
        GasMeter meter=gasMeterService.findGasMeterByCode(chargeRecord.getGasMeterCode());
        if(GasMeterStatusEnum.Dismantle.getCode().intValue()==meter.getDataStatus().intValue()){
            dto.setIsApplyRefund(false);
            dto.setNonRefund("表已拆除，不能退费");
            return;
        }
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if(OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())){
            iotPreChargeMeterCheck(chargeRecord,dto);
        }else if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())){
            iotReadMeterCheck(chargeRecord,dto);
        }else if(OrderSourceNameEnum.REMOTE_RECHARGE.eq(version.getOrderSourceName())){
            iotRechargeMeterCheck(chargeRecord,dto);
        }else if(OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())){
            dto.setIsCardMeter(true);
            dto.setIsMoneyMeter(AmountMarkEnum.MONEY.eq(version.getAmountMark()));
            icCardMeterCheck(chargeRecord,dto);
        }else if(OrderSourceNameEnum.READMETER_PAY.eq(version.getOrderSourceName())){
            pmeterCheck(chargeRecord,dto);
        }
    }
    private void icCardMeterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        //只要进行了最新的充值，或者补气，就不能退以前的收费，以前的收费必然上表了。
        //场景费可以每笔单独退，场景业务状态不退回（发卡，补卡例外可以退回业务状态）。包含周期费
        RechargeRecord rechargeRecord =getRecharge(chargeRecord);
        dto.setRechargeRecord(rechargeRecord);
        if(rechargeRecord!=null) {
            if (rechargeRecord.getMoneyFlowStatus().equals(MoneyFlowStatusEnum.success.getCode())) {
                dto.setIsApplyRefund(false);
                dto.setNonRefund("充值已上表不能退费");
                return;
            }
            if (MoneyFlowStatusEnum.waite_to_meter.eq(rechargeRecord.getMoneyFlowStatus())) {
                dto.setIsCardRefund(true);
            }
        }
    }

    private void iotRechargeMeterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        RechargeRecord rechargeRecord =getRecharge(chargeRecord);
        dto.setRechargeRecord(rechargeRecord);
        if(rechargeRecord!=null) {
            if(rechargeRecord.getMoneyFlowStatus().equals(MoneyFlowStatusEnum.success.getCode())){
                dto.setIsApplyRefund(false);
                dto.setNonRefund("物联网表端计费表，充值指令已向表端发送，不能退费");
                return;
            }
            if(rechargeRecord.getMoneyFlowStatus().equals(MoneyFlowStatusEnum.waite_to_meter.getCode())){
                dto.setIsApplyRefund(false);
                dto.setNonRefund("物联网表端计费表充值，请先发送取消指令，取消成功后可退费");
                return;
            }
        }
    }
    private void iotPreChargeMeterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        RechargeRecord rechargeRecord=getRecharge(chargeRecord);
        dto.setRechargeRecord(rechargeRecord);
        if(rechargeRecord!=null){
            GasMeterInfo info = gasMeterInfoService.getByMeterAndCustomerCode(rechargeRecord.getGasmeterCode(),rechargeRecord.getCustomerCode());
            ChargeUtils.setNullFieldAsZero(info);
            if (info.getGasMeterBalance().compareTo(chargeRecord.getRechargeMoney()) < 0) {
                dto.setIsApplyRefund(false);
                dto.setNonRefund("户表账户发生变更且余额不足，不能退费");
                return;
            }
        }
    }
    private void pmeterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        ChargeRecord nrecord=chargeRecordService.nearestCharge(chargeRecord.getGasMeterCode(),
                chargeRecord.getCustomerCode(),chargeRecord.getCreateTime());
        if(nrecord!=null ){
            if(StringUtils.isBlank(nrecord.getRefundStatus()) ||
                    RefundStatusEnum.APPLY.eq(nrecord.getRefundStatus()) ||
                    RefundStatusEnum.NOMAL.eq(nrecord.getRefundStatus())
            ){
                dto.setNonRefund("存在最新收费记录未完成退费");
                dto.setNearNewRecord(nrecord);
                dto.setIsApplyRefund(false);
                return ;
            }
        }
    }
    private void iotReadMeterCheck(ChargeRecord chargeRecord,RefundCheckDTO dto){
        RechargeRecord rechargeRecord=getRecharge(chargeRecord);
        dto.setRechargeRecord(rechargeRecord);
        if(rechargeRecord!=null){
            GasMeterInfo info = gasMeterInfoService.getByMeterAndCustomerCode(rechargeRecord.getGasmeterCode(),rechargeRecord.getCustomerCode());
            ChargeUtils.setNullFieldAsZero(info);
            if (info.getGasMeterBalance().compareTo(chargeRecord.getRechargeMoney()) < 0) {
                dto.setIsApplyRefund(false);
                dto.setNonRefund("户表账户发生变更且余额不足，不能退费");
                return;
            }
        }
        //校验最近一笔收费记录
        ChargeRecord nrecord=chargeRecordService.nearestCharge(chargeRecord.getGasMeterCode(),
                chargeRecord.getCustomerCode(),chargeRecord.getCreateTime());
        if(nrecord!=null ){
            if(StringUtils.isBlank(nrecord.getRefundStatus()) ||
                    RefundStatusEnum.APPLY.eq(nrecord.getRefundStatus()) ||
                    RefundStatusEnum.NOMAL.eq(nrecord.getRefundStatus())
            ){
                dto.setNonRefund("存在最新收费记录未完成退费");
                dto.setNearNewRecord(nrecord);
                dto.setIsApplyRefund(false);
                return ;
            }
        }
    }

    private RechargeRecord getRecharge(ChargeRecord chargeRecord){
        if(chargeRecord.getRechargeMoney()!=null && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)>0) {
            //有充值，需要锁定充值记录。 是否锁定收费项呢？如果收费项用于了实时统计，那么可能导致实时统计不正确。所以实时统计情况需要考虑退费。
            RechargeRecord rechargeRecord = rechargeRecordService.getOne(
                    new LbqWrapper<RechargeRecord>()
                            .eq(RechargeRecord::getChargeNo, chargeRecord.getChargeNo())
                            .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue()));
            return rechargeRecord;
        }
        return null;
    }
}

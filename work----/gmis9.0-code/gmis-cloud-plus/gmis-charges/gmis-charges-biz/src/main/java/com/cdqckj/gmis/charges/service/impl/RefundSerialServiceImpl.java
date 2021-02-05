package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.meterinfo.MeterAccountSerialSceneEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.utils.I18nUtil;
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
public class RefundSerialServiceImpl implements RefundSerialService {
    @Autowired
    GasMeterInfoService gasMeterInfoService;
    @Autowired
    GasMeterInfoSerialService gasMeterInfoSerialService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    CustomerAccountSerialService customerAccountSerialService;
    @Autowired
    I18nUtil i18nUtil;
    /**
     * 取消退费解冻户表账户预存金额
     * @param record
     */
    @Override
    public void cancelBackAndUnfreezeMeterAccount(ChargeRecord record){
        if(record.getRechargeMoney()!=null &&
                record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0
        ){
            GasMeter meter=gasMeterService.findGasMeterByCode(record.getGasMeterCode());
            GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
            GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode());
            if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName()) ||
                    OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())){
                GasMeterInfo update=new GasMeterInfo();
                update.setGasMeterBalance(meterInfo.getGasMeterBalance().add(record.getRechargeMoney()));
                update.setGasMeterGive(meterInfo.getGasMeterGive().add(record.getRechargeGiveMoney()));
                gasMeterInfoService.updateById(update);
            }
        }
    }

    @Override
    public void saveFreezeCustomerMeterAccountSerial(ChargeRecord chargeRecord,GasMeterVersion version,GasMeterInfo gasMeterInfo){
        if(chargeRecord.getRechargeMoney()==null || chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
            return ;
        }
        if(!OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())
                &&  !OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())
        ){
            return ;
        }
        ChargeUtils.setNullFieldAsZero(chargeRecord);
//        GasMeterInfo gasMeterInfo=gasMeterInfoService.getByGasMeterCode(meter.getGasCode());
        ChargeUtils.setNullFieldAsZero(gasMeterInfo);
        GasMeterInfoSerial meterInfoSerial= new GasMeterInfoSerial();
        meterInfoSerial.setSerialNo(BizCodeNewUtil.getGasMeterInfoSerialCode(BizSCode.CHARGE));
        meterInfoSerial.setBillNo(chargeRecord.getChargeNo());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setGasMeterCode(chargeRecord.getGasMeterCode());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setBillType(MeterAccountSerialSceneEnum.PRECHARGE_REFUND_FORZEN.getCode());
        meterInfoSerial.setGiveBookPreMoney(gasMeterInfo.getGasMeterGive());
        meterInfoSerial.setGiveBookPreMoney(chargeRecord.getRechargeGiveMoney());
        meterInfoSerial.setGiveBookAfterMoney(gasMeterInfo.getGasMeterGive().subtract(chargeRecord.getRechargeGiveMoney()));
        meterInfoSerial.setCustomerCode(chargeRecord.getCustomerCode());
        meterInfoSerial.setBookPreMoney(gasMeterInfo.getGasMeterBalance());
        meterInfoSerial.setBookAfterMoney(gasMeterInfo.getGasMeterBalance().subtract(chargeRecord.getRechargeMoney()));
        meterInfoSerial.setBookMoney(chargeRecord.getRechargeMoney());
        //新增户表账户流水
        gasMeterInfoSerialService.save(meterInfoSerial);
    }

    @Override
    public void saveUnFreezeCustomerMeterAccountSerial(ChargeRecord record){
        if(record.getRechargeMoney()==null || record.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
            return ;
        }
        GasMeter meter=gasMeterService.findGasMeterByCode(record.getGasMeterCode());
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        GasMeterInfo gasMeterInfo=gasMeterInfoService.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode());


        if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())
                || OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())
        ){
            return ;
        }
        ChargeUtils.setNullFieldAsZero(gasMeterInfo);
        GasMeterInfoSerial meterInfoSerial= new GasMeterInfoSerial();
        meterInfoSerial.setSerialNo(BizCodeNewUtil.getGasMeterInfoSerialCode(BizSCode.CHARGE));
        meterInfoSerial.setBillNo(record.getChargeNo());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setGasMeterCode(record.getGasMeterCode());
        meterInfoSerial.setCustomerCode(record.getCustomerCode());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setBillType(MeterAccountSerialSceneEnum.PRECHARGE_REFUND_CANCEL.getCode());
        meterInfoSerial.setGiveBookPreMoney(gasMeterInfo.getGasMeterGive());
        meterInfoSerial.setGiveBookPreMoney(record.getRechargeGiveMoney());
        meterInfoSerial.setGiveBookAfterMoney(gasMeterInfo.getGasMeterGive().add(record.getRechargeGiveMoney()));
        meterInfoSerial.setBookPreMoney(gasMeterInfo.getGasMeterBalance());
        meterInfoSerial.setBookAfterMoney(gasMeterInfo.getGasMeterBalance().add(record.getRechargeMoney()));
        meterInfoSerial.setBookMoney(record.getRechargeMoney());
        //新增户表账户流水
        gasMeterInfoSerialService.save(meterInfoSerial);
    }

    /**
     * update 冻结账户流水 refund
     * @return
     */
    @Override
    public void refundCustomerMeterAccountSerial(String refundNo){
        GasMeterInfoSerial serial = gasMeterInfoSerialService.getOne(new LbqWrapper<GasMeterInfoSerial>()
                .eq(GasMeterInfoSerial::getBillNo,refundNo)
                .eq(GasMeterInfoSerial::getBillType,MeterAccountSerialSceneEnum.PRECHARGE_REFUND.getCode())
        );
        if(serial!=null ){
            GasMeterInfoSerial serialupdate=new GasMeterInfoSerial();
            serialupdate.setId(serial.getId());
            serial.setBillType(AccountSerialSceneEnum.REFUND.getCode());
            gasMeterInfoSerialService.updateById(serialupdate);
        }
    }


    /**
     * 创建抵扣账户流水记录
     * @return
     */
    @Override
    public CustomerAccountSerial createAccountRedSeria(CustomerAccount account,ChargeRecord chargeRecord){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        //继续使用收费编号，虽然是退费但是是同一笔收费产生的账户流水
        serial.setBillNo(chargeRecord.getChargeNo());
        serial.setGasMeterCode(chargeRecord.getGasMeterCode());
            //抵扣
        serial.setBillType(AccountSerialSceneEnum.DEDUCTION_REFUND.getCode());
        serial.setBookMoney(chargeRecord.getPrechargeDeductionMoney());
        serial.setBookAfterMoney(account.getAccountMoney().add(chargeRecord.getPrechargeDeductionMoney()));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookMoney(chargeRecord.getGiveDeductionMoney());
        serial.setGiveBookAfterMoney(account.getGiveMoney().add(chargeRecord.getGiveDeductionMoney()));
        serial.setGiveBookPreMoney(account.getGiveMoney());
//        serial.setSerialNo(BizCodeUtil.genSerialDataCode(BizSCode.REFUND, BizCodeUtil.ACCOUNT_SERIAL));
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(chargeRecord.getCustomerCode());
        serial.setBusinessHallId(chargeRecord.getBusinessHallId());
        serial.setBusinessHallName(chargeRecord.getBusinessHallName());
        return serial;
    }

    /**
     * 创建收费资金退回账户流水记录
     * @return
     */
    @Override
    public CustomerAccountSerial createBackAccountSeria(CustomerAccount account,ChargeRefund refund){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        //继续使用收费编号，虽然是退费但是是同一笔收费产生的账户流水
        serial.setBillNo(refund.getChargeNo());

        serial.setBillType(AccountSerialSceneEnum.CHARGE_REFUND.getCode());
        serial.setBookMoney(refund.getBackAmount());
        //赠送金额无
        serial.setGiveBookMoney(BigDecimal.ZERO);
        serial.setBookAfterMoney(account.getAccountMoney().add(refund.getBackAmount()));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookAfterMoney(account.getGiveMoney());
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setGasMeterCode(refund.getGasMeterCode());
//        serial.setSerialNo(BizCodeUtil.genSerialDataCode(BizSCode.REFUND, BizCodeUtil.ACCOUNT_SERIAL));
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(refund.getCustomerCode());
        serial.setBusinessHallId(refund.getBusinessHallId());
        serial.setBusinessHallName(refund.getBusinessHallName());
        return serial;
    }


    /**
     * 创建冻结账户流水
     * @return
     */
    @Override
    public CustomerAccountSerial createFrozenAccountSeria(CustomerAccount account,ChargeRecord record,String refundNo){
        BigDecimal zero=new BigDecimal("0.00");
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        //refund no
        serial.setBillNo(refundNo);
        BigDecimal preCharge=record.getPrechargeMoney();
        serial.setBillType(AccountSerialSceneEnum.PRECHARGE_REFUND.getCode());
        serial.setBookMoney(preCharge);
        //赠送金额无
        serial.setGiveBookMoney(zero);
        serial.setBookAfterMoney(account.getAccountMoney().subtract(preCharge));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookAfterMoney(account.getGiveMoney());
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setGasMeterCode(record.getGasMeterCode());
//
//        serial.setSerialNo(BizCodeUtil.genSerialDataCode(BizSCode.REFUND, BizCodeUtil.ACCOUNT_SERIAL));
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(record.getCustomerCode());
        serial.setBusinessHallId(record.getBusinessHallId());
        serial.setBusinessHallName(record.getBusinessHallName());
        return serial;
    }

    /**
     * update 冻结账户流水 refund
     * @return
     */
    @Override
    public void refundFrozenAccountSeria(String refundNo){
        CustomerAccountSerial serial = customerAccountSerialService.getOne(new LbqWrapper<CustomerAccountSerial>()
            .eq(CustomerAccountSerial::getBillNo,refundNo)
            .eq(CustomerAccountSerial::getBillType,AccountSerialSceneEnum.PRECHARGE_REFUND.getCode())
        );
        if(serial!=null ){
            CustomerAccountSerial serialupdate=new CustomerAccountSerial();
            serialupdate.setId(serial.getId());
            serial.setBillType(AccountSerialSceneEnum.REFUND.getCode());
            customerAccountSerialService.updateById(serialupdate);
        }
    }


    /**
     * 创建预存解冻并取消退费账户流水
     * @return
     */
    @Override
    public CustomerAccountSerial createCancelBackAndUnfreezeAccountSeria(CustomerAccount account,ChargeRecord record){

        BigDecimal zero=new BigDecimal("0.00");
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        //继续使用收费编号，虽然是退费但是是同一笔收费产生的账户流水
        serial.setBillNo(record.getChargeNo());
        BigDecimal preCharge=record.getPrechargeMoney();

        serial.setBillType(AccountSerialSceneEnum.PRECHARGE_REFUND_CANCEL.getCode());
        serial.setBookMoney(preCharge);
        //赠送金额无
        serial.setGiveBookMoney(zero);
        serial.setBookAfterMoney(account.getAccountMoney().add(preCharge));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookAfterMoney(account.getGiveMoney());
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setGasMeterCode(record.getGasMeterCode());
//        serial.setSerialNo(BizCodeUtil.genSerialDataCode(BizSCode.REFUND, BizCodeUtil.ACCOUNT_SERIAL));
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(record.getCustomerCode());
        serial.setBusinessHallId(record.getBusinessHallId());
        serial.setBusinessHallName(record.getBusinessHallName());
        return serial;
    }


}

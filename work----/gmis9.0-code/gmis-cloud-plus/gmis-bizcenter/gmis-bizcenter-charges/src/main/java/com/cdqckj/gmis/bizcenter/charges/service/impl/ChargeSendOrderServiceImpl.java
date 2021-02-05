package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.charges.service.ChargePayService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeSendOrderService;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.RechargeVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateBalanceVO;
import com.cdqckj.gmis.iot.qc.vo.ValveControlVO;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.utils.DateUtils;
import com.google.common.collect.Lists;
import io.seata.common.util.CollectionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收费支付封装
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class ChargeSendOrderServiceImpl implements ChargeSendOrderService{


    @Autowired
    UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    BusinessService businessService;

    @Autowired
    RechargeRecordBizApi rechargeRecordBizApi;
    /**
     * 物联网表充值指令发送
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void sendRechargeOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 &&
                OrderSourceNameEnum.REMOTE_RECHARGE.eq(record.getChargeType())) {
            RechargeVO rechargeVO = new RechargeVO();
            rechargeVO.setGasMeterNumber(meter.getGasMeterNumber());
            rechargeVO.setRechargeTimes(meterInfo.getTotalRechargeMeterCount());
            if(record.getRechargeGiveMoney()!=null){
                rechargeVO.setRechargeAmount(record.getRechargeMoney().add(record.getRechargeGiveMoney()));
            }else{

                rechargeVO.setRechargeAmount(record.getRechargeMoney());
            }
//            rechargeVO.setRechargeAmount1(meterInfo.getValue2());
//            rechargeVO.setRechargeAmount2(meterInfo.getValue3());
            UseGasType useGasType=useGasTypeBizApi.get(meter.getUseGasTypeId()).getData();
            rechargeVO.setAlarmAmount(useGasType.getAlarmGas().toBigInteger().intValue());
            IotR r=businessService.recharge(rechargeVO);

            if(r.getIsSuccess()){
                String serialNo=r.get(IotRConstant.MESSAGE).toString();
                //发出充值指令成功
                rechargeRecordBizApi.iotRechargeOrderCallBack(record.getChargeNo(),serialNo);
            }
        }
    }

    /**
     * open meter order
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void sendOpenMeterOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 ) {
            if(meterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)>0 &&
                    meterInfo.getGasMeterBalance().subtract(record.getRechargeMoney()).compareTo(BigDecimal.ZERO)<=0
            ){
                ValveControlVO vo=new ValveControlVO();
                vo.setGasMeterNumber(meter.getGasMeterNumber());
                vo.setControlCode("10");
                businessService.valveControl(Lists.newArrayList(vo));
            }
        }
    }

    /**
     * 下发表端余额指令
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void sendMeterAcountOrder(ChargeRecord record, GasMeter meter, GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 ) {
            UpdateBalanceVO updateBalanceVO = new UpdateBalanceVO();
            updateBalanceVO.setBalance(meterInfo.getGasMeterBalance());
            updateBalanceVO.setGasMeterNumber(meter.getGasMeterNumber());
//            updateBalanceVO.setGasMeterFactoryCode(version.getCompanyCode());
            UseGasType useGasType=useGasTypeBizApi.get(meter.getUseGasTypeId()).getData();

            updateBalanceVO.setAlarmMoney(useGasType.getAlarmGas());
//            rechargeVO.setAlarmAmount(useGasType.getAlarmGas().toBigInteger().intValue());
            IotR r=businessService.updatebalance(updateBalanceVO);

            if(r.getIsSuccess()){
                String serialNo=r.get(IotRConstant.MESSAGE).toString();
                //发出充值指令成功
                rechargeRecordBizApi.iotRechargeOrderCallBack(record.getChargeNo(),serialNo);
            }
        }
    }

    /**
     * close meter order
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void sendCloseMeterOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null && record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 ) {
            if(meterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)>0 &&
                    meterInfo.getGasMeterBalance().subtract(record.getRechargeMoney()).compareTo(BigDecimal.ZERO)<0
            ){
                ValveControlVO vo=new ValveControlVO();
                vo.setGasMeterNumber(meter.getGasMeterNumber());
                vo.setControlCode("01");
                businessService.valveControl(Lists.newArrayList(vo));
            }
        }
    }

    /**
     * 退费下发表端余额指令
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void backFeeSendMeterAcountOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null &&
                record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 ) {
            UpdateBalanceVO updateBalanceVO = new UpdateBalanceVO();
            updateBalanceVO.setBalance(meterInfo.getGasMeterBalance());
            updateBalanceVO.setGasMeterNumber(meter.getGasMeterNumber());
//            updateBalanceVO.setGasMeterFactoryCode(version.getCompanyCode());
            UseGasType useGasType=useGasTypeBizApi.get(meter.getUseGasTypeId()).getData();

            updateBalanceVO.setAlarmMoney(useGasType.getAlarmGas());
//            rechargeVO.setAlarmAmount(useGasType.getAlarmGas().toBigInteger().intValue());
            IotR r=businessService.updatebalance(updateBalanceVO);
            if(r.getIsSuccess()){
                String serialNo=r.get(IotRConstant.MESSAGE).toString();
                //发出充值指令成功
                rechargeRecordBizApi.iotRechargeOrderCallBack(record.getChargeNo(),serialNo);
            }
        }
    }

    /**
     * 退费取消物联网表充值指令发送
     * @param record
     * @param meter
     * @param meterInfo
     */
    public void refundCancelSendRechargeOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo){
        if(record.getRechargeMoney()!=null &&
                record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0 &&
                OrderSourceNameEnum.REMOTE_RECHARGE.eq(record.getChargeType())) {
            RechargeVO rechargeVO = new RechargeVO();
            rechargeVO.setGasMeterNumber(meter.getGasMeterNumber());
            rechargeVO.setRechargeTimes(meterInfo.getTotalRechargeMeterCount());
            rechargeVO.setRechargeAmount(meterInfo.getValue1());
            rechargeVO.setRechargeAmount1(meterInfo.getValue2());
            rechargeVO.setRechargeAmount2(meterInfo.getValue3());
            UseGasType useGasType=useGasTypeBizApi.get(meter.getUseGasTypeId()).getData();
            rechargeVO.setAlarmAmount(useGasType.getAlarmGas().toBigInteger().intValue());
            IotR r=businessService.recharge(rechargeVO);

            if(r.getIsSuccess()){
                String serialNo=r.get(IotRConstant.MESSAGE).toString();
                //发出充值指令成功
                rechargeRecordBizApi.iotRechargeOrderCallBack(record.getChargeNo(),serialNo);
            }
        }
    }
}

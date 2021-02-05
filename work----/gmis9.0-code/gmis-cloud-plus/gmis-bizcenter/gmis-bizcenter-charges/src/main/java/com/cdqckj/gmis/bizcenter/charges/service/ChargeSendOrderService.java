package com.cdqckj.gmis.bizcenter.charges.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.RechargeVO;
import com.cdqckj.gmis.operateparam.entity.UseGasType;

import java.math.BigDecimal;

/**
 * 收费物联网表发指令
 * @author tp
 * @date 2020-09-04
 */
public interface ChargeSendOrderService {

    /**
     * 物联网表充值指令发送
     * @param record
     * @param meter
     * @param meterInfo
     */
    void sendRechargeOrder(ChargeRecord record, GasMeter meter, GasMeterInfo meterInfo);

    /**
     * open meter order
     * @param record
     * @param meter
     * @param meterInfo
     */
    void sendOpenMeterOrder(ChargeRecord record, GasMeter meter, GasMeterInfo meterInfo);
    /**
     * close meter order
     * @param record
     * @param meter
     * @param meterInfo
     */
    void sendCloseMeterOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo);

    /**
     * 下发表端余额指令
     * @param record
     * @param meter
     * @param meterInfo
     */
    void sendMeterAcountOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo);

    /**
     * 退费下发表端余额指令
     * @param record
     * @param meter
     * @param meterInfo
     */
    void backFeeSendMeterAcountOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo);

    /**
     * 物联网表充值指令发送
     * @param record
     * @param meter
     * @param meterInfo
     */
     void refundCancelSendRechargeOrder(ChargeRecord record,GasMeter meter,GasMeterInfo meterInfo);
}

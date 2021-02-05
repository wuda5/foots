package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;

public interface RefundSerialService {

    void cancelBackAndUnfreezeMeterAccount(ChargeRecord record);
    void saveFreezeCustomerMeterAccountSerial(ChargeRecord chargeRecord, GasMeterVersion version, GasMeterInfo gasMeterInfo);
    void saveUnFreezeCustomerMeterAccountSerial(ChargeRecord record);
    void refundCustomerMeterAccountSerial(String refundNo);

    CustomerAccountSerial createAccountRedSeria(CustomerAccount account, ChargeRecord chargeRecord);
    CustomerAccountSerial createBackAccountSeria(CustomerAccount account, ChargeRefund refund);
    CustomerAccountSerial createFrozenAccountSeria(CustomerAccount account,ChargeRecord record,String refundNo);
    void refundFrozenAccountSeria(String refundNo);
    CustomerAccountSerial createCancelBackAndUnfreezeAccountSeria(CustomerAccount account,ChargeRecord record);
}

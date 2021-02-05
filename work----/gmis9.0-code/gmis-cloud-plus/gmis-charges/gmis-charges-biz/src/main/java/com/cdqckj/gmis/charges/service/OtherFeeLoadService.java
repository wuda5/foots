package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.entity.Customer;

import java.util.List;

public interface OtherFeeLoadService {
    List<ChargeItemRecord> loadOtherFeeItem(GasMeter meter, Customer customer);
}

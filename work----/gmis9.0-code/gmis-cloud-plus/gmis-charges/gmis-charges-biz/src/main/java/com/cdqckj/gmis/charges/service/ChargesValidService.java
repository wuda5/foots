package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.entity.Customer;

import java.util.List;
import java.util.Map;

public interface ChargesValidService {
    R<Boolean> baseValid(ChargeRecord chargeRecord, GasMeter meter, Customer customer, ChargeDTO chargeDTO);

    void validUncompleteCharge(String gasMeterCode,String customerCode);
    R<Boolean> unknownItemValid(GasMeter meter,
                     List<ChargeItemRecord> chargeItems,
                     Map<Long, CustomerEnjoyActivityRecord> reductsLoadMap,
                     List<ChargeItemRecord> gasFeeList
    );
    R<Boolean> accountValid(ChargeRecord chargeRecord, CustomerAccount account, Boolean useAccountDec);

    ChargeRecord getUnCompleteChargeRecord(String gasMeterCode,String customerCode);
    ChargeRefund getUnCompleteChargeRefund(String gasMeterCode, String customerCode);
}

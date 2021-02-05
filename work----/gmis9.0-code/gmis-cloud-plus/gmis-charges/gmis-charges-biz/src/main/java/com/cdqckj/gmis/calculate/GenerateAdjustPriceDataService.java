package com.cdqckj.gmis.calculate;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.entity.AdjustCalculationRecord;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;

import java.util.List;

/**
 * @author songyz
 */
public interface GenerateAdjustPriceDataService {
    /**
     *生成调价补差数据
     * @param adjustPrice
     * @return
     */
    R<Boolean> generate(AdjustPrice adjustPrice);

    /**
     *人工录入核算
     * @param adjustPriceRecords
     * @return
     */
    R<Boolean> manualAccount(List<AdjustPriceRecord> adjustPriceRecords);

    /**
     *生成核算数据重试
     * @param adjustPrice
     * @param adjustCalculationRecord
     * @return
     */
    R<Boolean> retry(AdjustPrice adjustPrice, AdjustCalculationRecord adjustCalculationRecord);
}

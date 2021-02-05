package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PurchaseLimitUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import org.springframework.web.bind.annotation.RequestBody;

public interface GaslimitService extends SuperCenterService {
    /**
     * 新增限购配置方案
     * @param saveDTO
     * @return
     */
    R<PurchaseLimit> savePurchaseLimit(PurchaseLimitSaveDTO saveDTO);

    /**
     * 更新限购配置方案
     * @param updateDTO
     * @return
     */
    R<PurchaseLimit> updatePurchaseLimit(@RequestBody PurchaseLimitUpdateDTO updateDTO);
}

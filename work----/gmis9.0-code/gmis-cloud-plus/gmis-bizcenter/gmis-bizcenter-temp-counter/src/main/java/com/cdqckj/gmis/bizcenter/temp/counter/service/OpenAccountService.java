package com.cdqckj.gmis.bizcenter.temp.counter.service;

import com.cdqckj.gmis.base.R;

public interface OpenAccountService {
    /**
     * 开户收费回调
     * @param bizNoOrId
     * @return
     */
    public R<Boolean> chargeCallback(final String bizNoOrId, final Boolean isRefund);
    /**
     * 开户不收费回调
     * @param bizNoOrId
     * @return
     */
    public R<Boolean> noChargeCallback(final String bizNoOrId, final long priceSchemeId);
}

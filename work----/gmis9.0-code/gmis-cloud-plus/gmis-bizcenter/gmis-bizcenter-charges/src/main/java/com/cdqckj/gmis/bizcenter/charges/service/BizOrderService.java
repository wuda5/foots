package com.cdqckj.gmis.bizcenter.charges.service;

import com.cdqckj.gmis.base.R;

import java.util.List;

/**
 * 改场景业务状态相关操作
 * @author tp
 * @date 2020-09-04
 */
public interface BizOrderService {
    /**
     * 修改场景业务状态
     * @param sceneIds 场景收费单ID
     * @param isBack 是否是退费或者还原状态
     * @return
     */
    R<Boolean> updateSceneBizStatus(List<Long> sceneIds,Boolean isBack) ;
    /**
     * 修改抄表业务状态
     * @param ids 欠费记录ID
     * @param isBack 是否是退费或者还原状态
     * @return
     */
    R<Boolean> updateReadMeterBizStatus(List<Long> ids,Boolean isBack);

    /**
     * 修改调价补差数据状态
     * @param ids 调价补差数据ID
     * @param isBack 是否是退费或者还原状态
     * @return
     */
    R<Boolean> updateAdjustPriceBizStatus(List<Long> ids,Boolean isBack);
}

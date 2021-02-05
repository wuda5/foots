package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 客户场景费用单
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
public interface CustomerSceneChargeOrderService extends SuperService<CustomerSceneChargeOrder> {
    int updateChargeStatusComplete(List<Long> ids,String chargeNo);
    int updateChargeStatusUncharge(List<Long> ids);
    Boolean creatSceneOrders(String gasMeterCode,String customerCode,String sceneCode,String bizNoOrId,Boolean isImportOpenAccount);

    /**
     * 根据场景编码和场景业务ID获取收费项
     * @param sceneCode
     * @param bizNoOrId
     * @return
     */
    List<CustomerSceneChargeOrder> loadSceneOrders(String sceneCode,String bizNoOrId);

    CustomerSceneChargeOrder loadOpenAccountSceneOrders(String sceneCode,String gasMeterCode,String customerCode);
}

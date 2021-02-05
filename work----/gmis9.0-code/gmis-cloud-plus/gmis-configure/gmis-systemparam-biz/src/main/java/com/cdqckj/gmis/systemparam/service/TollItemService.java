package com.cdqckj.gmis.systemparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;

/**
 * <p>
 * 业务接口
 * 收费项配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
public interface TollItemService extends SuperService<TollItem> {

    Boolean check(TollItemSaveDTO tollItemSaveDTO);

    /**
     * 该收费场景是否存在收费项
     * @author hc
     * @param sceneCode 收费项code
     * @return
     */
    Boolean existTollItem(String sceneCode);
}

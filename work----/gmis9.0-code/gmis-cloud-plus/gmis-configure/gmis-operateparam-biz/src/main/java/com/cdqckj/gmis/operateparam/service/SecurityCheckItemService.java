package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;

/**
 * <p>
 * 业务接口
 * 安检子项配置
 * </p>
 *
 * @author gmis
 * @date 2020-11-03
 */
public interface SecurityCheckItemService extends SuperService<SecurityCheckItem> {

    Boolean check(SecurityCheckItemSaveDTO securityCheckItemSaveDTO);

    Boolean checkupadate(SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO);
}

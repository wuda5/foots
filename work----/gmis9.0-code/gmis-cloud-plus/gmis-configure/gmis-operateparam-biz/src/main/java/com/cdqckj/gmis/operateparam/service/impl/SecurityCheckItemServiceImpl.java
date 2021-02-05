package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.operateparam.dao.SecurityCheckItemMapper;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.service.SecurityCheckItemService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.systemparam.entity.TollItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 安检子项配置
 * </p>
 *
 * @author gmis
 * @date 2020-11-03
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SecurityCheckItemServiceImpl extends SuperServiceImpl<SecurityCheckItemMapper, SecurityCheckItem> implements SecurityCheckItemService {
    @Override
    public Boolean check(SecurityCheckItemSaveDTO securityCheckItemSaveDTO) {
        return super.count(Wraps.<SecurityCheckItem>lbQ().eq(SecurityCheckItem::getSecurityCode,securityCheckItemSaveDTO.getSecurityCode()).
                eq(SecurityCheckItem::getName,securityCheckItemSaveDTO.getName()))>0;
    }

    @Override
    public Boolean checkupadate(SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO) {
        return super.count(Wraps.<SecurityCheckItem>lbQ().eq(SecurityCheckItem::getSecurityCode,securityCheckItemUpdateDTO.getSecurityCode()).
                eq(SecurityCheckItem::getName,securityCheckItemUpdateDTO.getName()).
                ne(SecurityCheckItem::getId,securityCheckItemUpdateDTO.getId()))>0;
    }
}

package com.cdqckj.gmis.sim.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.sim.dao.CardMapper;
import com.cdqckj.gmis.sim.entity.Card;
import com.cdqckj.gmis.sim.service.CardService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardServiceImpl extends SuperServiceImpl<CardMapper, Card> implements CardService {
    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Override
    public R<Card> findCardByCustomcode(String code) {
        CustomerGasMeterRelated oneByCode = customerGasMeterRelatedService.findOneByCode(code);
        LbuWrapper<Card> cardLbuWrapper=new LbuWrapper<>();
        cardLbuWrapper.eq(Card::getGasCode,oneByCode.getGasMeterCode());
        return R.success(baseMapper.selectOne(cardLbuWrapper));
    }
}

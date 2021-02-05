package com.cdqckj.gmis.sim.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.sim.entity.Card;


/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-09
 */
public interface CardService extends SuperService<Card> {

    R<Card> findCardByCustomcode(String code);
}

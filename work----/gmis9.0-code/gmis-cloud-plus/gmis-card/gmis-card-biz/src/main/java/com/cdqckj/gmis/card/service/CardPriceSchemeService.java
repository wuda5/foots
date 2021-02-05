package com.cdqckj.gmis.card.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.card.entity.CardPriceScheme;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.entity.UseGasType;

import java.util.Map;

/**
 * <p>
 * 业务接口
 * 卡表价格方案记录
 * </p>
 *
 * @author tp
 * @date 2021-01-12
 */
public interface CardPriceSchemeService extends SuperService<CardPriceScheme> {
    CardPriceScheme getByGasMeterCode(String gasMeterCode);

    Map<String,Object> getScheme(GasMeter meter, GasMeterVersion gasMeterVersion, UseGasType useGasType);


    void backGas(String gasMeterCode);

    void readCardCallBack(String gasMeterCode);
}

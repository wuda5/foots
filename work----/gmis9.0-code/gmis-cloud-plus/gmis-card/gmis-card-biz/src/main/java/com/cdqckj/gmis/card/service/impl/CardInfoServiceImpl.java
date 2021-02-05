package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.card.dao.CardInfoMapper;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.ConcernsCardInfo;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.card.service.CardInfoService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 气表卡信息
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardInfoServiceImpl extends SuperServiceImpl<CardInfoMapper, CardInfo> implements CardInfoService {
    @Autowired
    GasMeterService gasMeterService;

    @Autowired
    GasMeterVersionService gasMeterVersionService;

    @Override
    public ConcernsCardInfo getConcernsCardInfo(String gasCode,String customerCode) {
        CardInfo cardInfo=getOne(new LbqWrapper<CardInfo>()
                .eq(CardInfo::getGasMeterCode,gasCode)
                .eq(CardInfo::getCustomerCode,customerCode)
                .eq(CardInfo::getDataStatus, DataStatusEnum.NORMAL.getValue()));

        ConcernsCardInfo concernsCardInfo= baseMapper.getConcernsCardInfo(gasCode,customerCode);
        if(concernsCardInfo!=null && cardInfo!=null) {
            concernsCardInfo.setCardBalance(cardInfo.getCardBalance());
            concernsCardInfo.setMeterCount(cardInfo.getCardChargeCount());
            concernsCardInfo.setCardType(cardInfo.getCardType());
            GasMeter meter = gasMeterService.getOne(new LbqWrapper<GasMeter>().eq(GasMeter::getGasCode, gasCode));
            GasMeterVersion version = gasMeterVersionService.getById(meter.getGasMeterVersionId());
            concernsCardInfo.setAmountMark(version.getAmountMark());
        }
        return concernsCardInfo;
    }

    @Override
    public Long stsSendCardNum(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsSendCardNum(stsSearchParam, dataScope);
    }
}

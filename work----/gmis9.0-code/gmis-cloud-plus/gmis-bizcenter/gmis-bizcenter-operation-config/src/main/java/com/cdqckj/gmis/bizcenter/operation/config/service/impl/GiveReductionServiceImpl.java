package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.GiveReductionService;
import com.cdqckj.gmis.operateparam.GiveReductionConfBizApi;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.utils.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GiveReductionServiceImpl extends SuperCenterServiceImpl implements GiveReductionService {
    @Autowired
    private GiveReductionConfBizApi giveReductionConfBizApi;
    @Autowired
    private I18nUtil i18nUtil;
    @Override
    public R<GiveReductionConf> saveGiveReduction(GiveReductionConfSaveDTO giveReductionConfSaveDTO) {
        if (giveReductionConfSaveDTO.getStartTime().isAfter(giveReductionConfSaveDTO.getEndTime())){
            return R.fail(getLangMessage(MessageConstants.INVALID_ENG_TIME));
        }
        return giveReductionConfBizApi.save(giveReductionConfSaveDTO);
    }

    @Override
    public R<GiveReductionConf> updateGiveReduction(GiveReductionConfUpdateDTO giveReductionConfUpdateDTO) {
        if (giveReductionConfUpdateDTO.getStartTime().isAfter(giveReductionConfUpdateDTO.getEndTime())){
            return R.fail(getLangMessage(MessageConstants.INVALID_ENG_TIME));
        }else if(giveReductionConfUpdateDTO.getEndTime().isBefore(LocalDate.now())){
           return  R.fail(i18nUtil.getMessage("give.expier"));
        }
        return giveReductionConfBizApi.update(giveReductionConfUpdateDTO);
    }
}

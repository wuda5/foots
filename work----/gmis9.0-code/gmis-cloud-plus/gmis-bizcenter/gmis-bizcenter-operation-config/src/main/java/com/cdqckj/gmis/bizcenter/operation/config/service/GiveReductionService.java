package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;

public interface GiveReductionService {
    //新增活动时间校验
    R<GiveReductionConf> saveGiveReduction(GiveReductionConfSaveDTO giveReductionConfSaveDTO);
    //修改活动时间校验
    R<GiveReductionConf> updateGiveReduction(GiveReductionConfUpdateDTO giveReductionConfUpdateDTO);
}

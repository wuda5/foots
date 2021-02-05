package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 赠送减免活动配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-01
 */
public interface GiveReductionConfService extends SuperService<GiveReductionConf> {
    R<List<GiveReductionConf>> queryEffectiveGiveReduction(GiveReductionQueryDTO queryDTO);
}

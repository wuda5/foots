package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.card.dao.CardRepRecordMapper;
import com.cdqckj.gmis.card.entity.CardRepRecord;
import com.cdqckj.gmis.card.service.CardRepRecordService;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 补换卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardRepRecordServiceImpl extends SuperServiceImpl<CardRepRecordMapper, CardRepRecord> implements CardRepRecordService {
    /**
     * 查询业务关注点补卡记录列表
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 数据列表
     */
    @Override
    public List<CardRepRecordVO> queryFocusInfo(String customerCode, String gasMeterCode) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(customerCode, gasMeterCode, orgIds);
    }
}

package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.card.dao.CardBackGasRecordMapper;
import com.cdqckj.gmis.card.entity.CardBackGasRecord;
import com.cdqckj.gmis.card.service.CardBackGasRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 卡退气记录
 * </p>
 *
 * @author tp
 * @date 2021-01-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardBackGasRecordServiceImpl extends SuperServiceImpl<CardBackGasRecordMapper, CardBackGasRecord> implements CardBackGasRecordService {
}

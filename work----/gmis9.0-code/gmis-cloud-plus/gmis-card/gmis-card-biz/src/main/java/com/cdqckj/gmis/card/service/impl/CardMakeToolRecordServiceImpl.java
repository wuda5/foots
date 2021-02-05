package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.card.dao.CardMakeToolRecordMapper;
import com.cdqckj.gmis.card.entity.CardMakeToolRecord;
import com.cdqckj.gmis.card.service.CardMakeToolRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 制工具卡记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardMakeToolRecordServiceImpl extends SuperServiceImpl<CardMakeToolRecordMapper, CardMakeToolRecord> implements CardMakeToolRecordService {
}

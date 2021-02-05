package com.cdqckj.gmis.card.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.card.dao.CardRecRecordMapper;
import com.cdqckj.gmis.card.entity.CardRecRecord;
import com.cdqckj.gmis.card.service.CardRecRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 卡收回记录
 * </p>
 *
 * @author tp
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CardRecRecordServiceImpl extends SuperServiceImpl<CardRecRecordMapper, CardRecRecord> implements CardRecRecordService {
}

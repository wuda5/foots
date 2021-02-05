package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.readmeter.dao.BankWithholdRecordMapper;
import com.cdqckj.gmis.readmeter.entity.BankWithholdRecord;
import com.cdqckj.gmis.readmeter.service.BankWithholdRecordService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 银行代扣记录
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class BankWithholdRecordServiceImpl extends SuperServiceImpl<BankWithholdRecordMapper, BankWithholdRecord> implements BankWithholdRecordService {
}

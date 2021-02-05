package com.cdqckj.gmis.tenant.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.tenant.dao.LivingExpensesMapper;
import com.cdqckj.gmis.tenant.entity.LivingExpenses;
import com.cdqckj.gmis.tenant.service.LivingExpensesService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 支付宝微信-生活缴费关联租户
 * </p>
 *
 * @author gmis
 * @date 2021-01-22
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class LivingExpensesServiceImpl extends SuperServiceImpl<LivingExpensesMapper, LivingExpenses> implements LivingExpensesService {
}

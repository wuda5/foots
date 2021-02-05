package com.cdqckj.gmis.operateparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.operateparam.dao.ExceptionRuleConfMapper;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import com.cdqckj.gmis.operateparam.service.ExceptionRuleConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 抄表异常规则配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-07
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ExceptionRuleConfServiceImpl extends SuperServiceImpl<ExceptionRuleConfMapper, ExceptionRuleConf> implements ExceptionRuleConfService {
    @Autowired
    public ExceptionRuleConfMapper exceptionRuleConfMapper;

    @Override
    public List<ExceptionRuleConf> queryByGasTypeId(List<Long> list) {
        LambdaQueryWrapper<ExceptionRuleConf> lambda = new LambdaQueryWrapper<ExceptionRuleConf>();
        lambda.in(ExceptionRuleConf::getUseGasTypeId, list);
        return exceptionRuleConfMapper.selectByTypeIds(lambda);
    }
}

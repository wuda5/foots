package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 抄表异常规则配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-07
 */
public interface ExceptionRuleConfService extends SuperService<ExceptionRuleConf> {
    List<ExceptionRuleConf> queryByGasTypeId(List<Long> list);
}

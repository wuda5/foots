package com.cdqckj.gmis.operateparam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 抄表异常规则配置
 * </p>
 *
 * @author gmis
 * @date 2020-09-07
 */
@Repository
public interface ExceptionRuleConfMapper extends SuperMapper<ExceptionRuleConf> {
    @Select("select * from pz_exception_rule_conf ${ew.customSqlSegment}")
    List<ExceptionRuleConf> selectByTypeIds(@Param(Constants.WRAPPER) Wrapper<ExceptionRuleConf> wrapper);
}

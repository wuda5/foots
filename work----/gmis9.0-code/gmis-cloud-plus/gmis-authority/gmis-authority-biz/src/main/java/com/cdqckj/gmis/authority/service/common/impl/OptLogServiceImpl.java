package com.cdqckj.gmis.authority.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.common.OptLogMapper;
import com.cdqckj.gmis.authority.entity.common.OptLog;
import com.cdqckj.gmis.authority.service.common.OptLogService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.log.entity.OptLogDTO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 业务实现类
 * 系统日志
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class OptLogServiceImpl extends SuperServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    @Override
    public boolean save(OptLogDTO entity) {
        return super.save(BeanPlusUtil.toBean(entity, OptLog.class));
    }

    @Override
    public boolean clearLog(LocalDateTime clearBeforeTime, Integer clearBeforeNum) {
        return baseMapper.clearLog(clearBeforeTime, clearBeforeNum);
    }
}

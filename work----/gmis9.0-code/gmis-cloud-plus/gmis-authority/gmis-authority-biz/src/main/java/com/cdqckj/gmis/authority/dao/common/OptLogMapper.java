package com.cdqckj.gmis.authority.dao.common;

import com.cdqckj.gmis.authority.entity.common.OptLog;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 * Mapper 接口
 * 系统日志
 * </p>
 *
 * @author gmis
 * @date 2019-07-02
 */
@Repository
public interface OptLogMapper extends SuperMapper<OptLog> {
    /**
     * 清理日志
     *
     * @param clearBeforeTime 多久之前的
     * @param clearBeforeNum  多少条
     * @return
     */
    boolean clearLog(@Param("clearBeforeTime") LocalDateTime clearBeforeTime, @Param("clearBeforeNum") Integer clearBeforeNum);
}

package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterUploadStsNow;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 在线,报警两个指标
 * </p>
 *
 * @author gmis
 * @date 2020-11-13
 */
@Repository
public interface MeterUploadStsNowMapper extends SuperMapper<MeterUploadStsNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/16 10:02
     * @remark 查询记录
     */
    MeterUploadStsNow getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 11:44
     * @remark 统计当天的表数
     */
    Long stsMeterNumInfo(@Param("type") Integer type, @Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql);
}

package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InsureNow;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 保险的统计信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Repository
public interface InsureNowMapper extends SuperMapper<InsureNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 14:38
     * @remark 查询相同的记录
     */
    InsureNow getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 15:31
     * @remark 统计数据
     */
    Integer stsInsureNum(@Param("type") Integer type, @Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);

    /**
     * @auth lijianguo
     * @date 2020/11/17 16:11
     * @remark 统计数目
     */
    List<StsInfoBaseVo<Integer, Long>> insureNowByType(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);
}

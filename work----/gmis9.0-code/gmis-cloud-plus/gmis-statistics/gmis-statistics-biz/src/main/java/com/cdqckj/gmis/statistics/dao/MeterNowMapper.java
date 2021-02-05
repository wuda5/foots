package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterNow;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
@Repository
public interface MeterNowMapper extends SuperMapper<MeterNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 14:38
     * @remark 查询相同的记录
     */
    MeterNow getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/13 11:13
     * @remark 统计表具的数据
     */
    List<StsInfoBaseVo<String,Long>> stsTypeAmount(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);

    /**
     * @auth lijianguo
     * @date 2020/11/13 11:20
     * @remark 统计厂家
     */
    List<StsInfoBaseVo<String,Long>> stsFactoryAmount(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);
}

package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.GasFeiNow;

import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 燃气费
 * </p>
 *
 * @author gmis
 * @date 2020-11-19
 */
@Repository
public interface GasFeiNowMapper extends SuperMapper<GasFeiNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/9 15:16
     * @remark 当时的数据
     */
    GasFeiNow getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/19 17:19
     * @remark 用气类型
     */
    List<GasFeiNowTypeVo> getGasFeiType(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 20:37
    * @remark 统计费用
    */
    GasFeiNowTypeVo stsGasFei(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);
}

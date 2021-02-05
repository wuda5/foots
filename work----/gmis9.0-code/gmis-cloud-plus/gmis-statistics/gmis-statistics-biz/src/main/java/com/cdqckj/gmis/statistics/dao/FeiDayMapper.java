package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.FeiDay;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
@Repository
public interface FeiDayMapper extends SuperMapper<FeiDay> {

    /**
     * @auth lijianguo
     * @date 2020/11/9 15:16
     * @remark 当时的数据
     */
    FeiDay getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/10 17:03
     * @remark 统计数据
     */
    List<StsInfoBaseVo<String,BigDecimal>> stsNow(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql, @Param("type") Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/20 9:02
     * @remark 收费的统计
     */
    List<StsInfoBaseVo<String, BigDecimal>> chargeFeeType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql, @Param("type") Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/20 14:05
     * @remark 支付方式的统计
     */
    List<StsInfoBaseVo<String, BigDecimal>> chargeFeeByPayType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql,@Param("type") Integer type);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 9:14
    * @remark 统计费用
    */
    BigDecimal changeFeeTotal(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql);
}

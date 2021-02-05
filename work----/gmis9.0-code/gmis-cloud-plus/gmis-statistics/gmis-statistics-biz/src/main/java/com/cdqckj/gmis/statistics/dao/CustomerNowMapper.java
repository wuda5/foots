package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.CustomerNow;

import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
@Repository
public interface CustomerNowMapper extends SuperMapper<CustomerNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:24
     * @remark 查询记录
     */
    CustomerNow getNowRecord(@Param("sql") String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 15:11
     * @remark 统计
     */
    Integer stsCustomType(@Param("typeList") List<Integer> typeList, @Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);

    /**
     * @auth lijianguo
     * @date 2020/11/17 13:26
     * @remark 哦统计
     */
    List<StsInfoBaseVo<String, Long>> stsCustomTypeWithCondition(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:45
     * @remark 客户增量统计
     * @return
     */
    List<StsInfoBaseVo<String, Long>> stsCustomAddType(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql, @Param("type") Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/18 8:41
     * @remark 统计和这个额类型的的数量
     */
    StsInfoBaseVo<Integer, Long> stsCustomBlackList(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql, @Param("type") Integer type);
}

package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.AccountNow;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Repository
public interface AccountNowMapper extends SuperMapper<AccountNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/6 15:44
     * @remark 查询一条记录
     */
    AccountNow getNowRecord(@Param("searchStr") String searchStr);

    /**
     * @auth lijianguo
     * @date 2020/11/12 13:25
     * @remark 统计从这个时间开始的开户各个类型的数据
     */
    List<StsInfoBaseVo<Integer, Long>> accountNowTypeFrom(@Param("stsSearchParam") StsSearchParam stsParam, @Param("searchSql") String searchSql);
}

package com.cdqckj.gmis.biztemporary.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
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
 * @date 2020-11-24
 */
@Repository
public interface OpenAccountRecordMapper extends SuperMapper<OpenAccountRecord> {

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 15:53
    * @remark 统计开户的数量
    */
    Long stsOpenAccountRecord(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:09
    * @remark 统计开户数目
    */
    List<StsInfoBaseVo<String, Long>> stsOpenCustomerType(@Param("stsSearchParam") StsSearchParam stsSearchParam);
}

package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InsureNow;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 保险的统计信息
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
public interface InsureNowService extends SuperService<InsureNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:23
     * @remark 查询有没有这个记录当前的
     */
    InsureNow getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 15:30
     * @remark 统计 1新增 2续保 3作废
     */
    Integer stsInsureNum(Integer type, StsSearchParam stsSearchParam);

    /**
     * @auth lijianguo
     * @date 2020/11/17 16:10
     * @remark 查询数据的统计
     * @return
     */
    List<StsInfoBaseVo<Integer, Long>> insureNowByType(StsSearchParam stsSearchParam);
}

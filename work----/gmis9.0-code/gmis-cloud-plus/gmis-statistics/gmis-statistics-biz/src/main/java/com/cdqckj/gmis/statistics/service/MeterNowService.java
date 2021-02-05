package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterNow;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 表具的厂家，类型的两个维度
 * </p>
 *
 * @author gmis
 * @date 2020-11-12
 */
public interface MeterNowService extends SuperService<MeterNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:23
     * @remark 查询有没有这个记录当前的
     */
    MeterNow getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/13 11:12
     * @remark 表具的类型信息
     * @return
     */
    List<StsInfoBaseVo<String,Long>> stsTypeAmount(StsSearchParam stsSearchParam);

    /**
     * @auth lijianguo
     * @date 2020/11/13 11:20
     * @remark 厂家的统计
     */
    List<StsInfoBaseVo<String,Long>> stsFactoryAmount(StsSearchParam stsSearchParam);
}

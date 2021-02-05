package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.MeterUploadStsNow;

/**
 * <p>
 * 业务接口
 * 在线,报警两个指标
 * </p>
 *
 * @author gmis
 * @date 2020-11-13
 */
public interface MeterUploadStsNowService extends SuperService<MeterUploadStsNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:23
     * @remark 查询有没有这个记录当前的
     * @return
     */
    MeterUploadStsNow getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 11:42
     * @remark 这里统计的整个公司的
     * @return
     */
    Long stsMeterNumInfo(Integer type, StsSearchParam stsSearchParam);
}

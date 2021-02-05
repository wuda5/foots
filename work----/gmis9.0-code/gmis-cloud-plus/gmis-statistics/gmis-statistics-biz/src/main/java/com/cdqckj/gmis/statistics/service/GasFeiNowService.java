package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.GasFeiNow;
import com.cdqckj.gmis.statistics.vo.GasFeiNowTypeVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 燃气费
 * </p>
 *
 * @author gmis
 * @date 2020-11-19
 */
public interface GasFeiNowService extends SuperService<GasFeiNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:23
     * @remark 查询有没有这个记录当前的
     */
    GasFeiNow getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/19 17:18
     * @remark 用气类型的统计
     */
    List<GasFeiNowTypeVo> gasFeiType(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 20:20
    * @remark 统计用气量和费用
    */
    GasFeiNowTypeVo stsGasFei(StsSearchParam stsSearchParam);
}

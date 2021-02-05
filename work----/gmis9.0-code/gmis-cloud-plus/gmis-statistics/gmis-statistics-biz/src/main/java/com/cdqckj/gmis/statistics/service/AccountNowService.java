package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.AccountNow;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
public interface AccountNowService extends SuperService<AccountNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:15
     * @remark 查询这个记录是否存在
     */
    AccountNow getNowRecord(String searchStr);

    /**
     * @auth lijianguo
     * @date 2020/11/12 13:24
     * @remark 统计开户记录
     * @return
     */
    List<StsInfoBaseVo<Integer, Long>> accountNowTypeFrom(StsSearchParam stsSearchParam);
}

package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-24
 */
public interface OpenAccountRecordService extends SuperService<OpenAccountRecord> {

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 15:52
    * @remark 统计数量
    */
    Long stsOpenAccountRecord(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:09
    * @remark 统计开户的数目
    */
    List<StsInfoBaseVo<String, Long>> stsOpenCustomerType(StsSearchParam stsSearchParam);
}

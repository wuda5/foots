package com.cdqckj.gmis.userarchive.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 销户记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-14
 */
public interface MeterAccountCancelService extends SuperService<MeterAccountCancel> {

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:26
    * @remark
    */
    List<StsInfoBaseVo<String, Long>> stsCancelCustomerType(StsSearchParam stsSearchParam);
}

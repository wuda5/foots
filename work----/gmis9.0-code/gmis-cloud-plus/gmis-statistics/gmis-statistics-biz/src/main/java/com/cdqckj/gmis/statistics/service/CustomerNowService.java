package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.CustomerNow;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 用户的客户档案的统计
 * </p>
 *
 * @author gmis
 * @date 2020-11-07
 */
public interface CustomerNowService extends SuperService<CustomerNow> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 10:23
     * @remark 查询有没有这个记录当前的
     */
    CustomerNow getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/16 15:08
     * @remark 统计一个类型的数目
     */
    Integer stsCustomType(List<Integer> typeList, StsSearchParam stsParam);

    /**
     * @auth lijianguo
     * @date 2020/11/17 13:25
     * @remark 统计这个条件
     * @param stsParam
     * @return
     */
    List<StsInfoBaseVo<String, Long>> stsCustomTypeWithCondition(StsSearchParam stsParam);

    /**
     * @auth lijianguo
     * @date 2020/11/17 14:44
     * @remark 客户增量统计
     * @return
     */
    List<StsInfoBaseVo<String, Long>> stsCustomAddType(StsSearchParam stsSearchParam, Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/18 8:40
     * @remark 统计这个类型的数量
     * @return
     */
    StsInfoBaseVo<Integer, Long> stsCustomBlackNum(StsSearchParam stsSearchParam, Integer type);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 19:02
    * @remark 统计整个的数量
    */
    StsInfoBaseVo<Integer, Long> stsCustomBlackNumNotNull(StsSearchParam stsSearchParam);
}

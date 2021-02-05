package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.FeiDay;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 整个系统的费用的统计历史数据按照天来统计，实时统计。
 * 数据统计的维度到项目和收费员这个最小的维度，每个项目
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
public interface FeiDayService extends SuperService<FeiDay> {

    /**
     * @auth lijianguo
     * @date 2020/11/9 15:16
     * @remark 当时
     */
    FeiDay getNowRecord(String sql);

    /**
     * @auth lijianguo
     * @date 2020/11/10 17:01
     * @remark 统计一天的数据 type：1收费 2退费
     * @return
     */
    List<StsInfoBaseVo<String,BigDecimal>> stsNow(StsSearchParam stsSearchParam, Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/20 9:01
     * @remark 收费的统计
     * @return
     */
    List<StsInfoBaseVo<String, BigDecimal>> chargeFeeType(StsSearchParam stsSearchParam, Integer type);

    /**
     * @auth lijianguo
     * @date 2020/11/20 14:04
     * @remark 支付方式统计
     * @return
     */
    List<StsInfoBaseVo<String, BigDecimal>> chargeFeeByPayType(StsSearchParam stsSearchParam, Integer type);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/7 9:09
    * @remark 统计一个时间段的费用
     * @return
    */
    BigDecimal changeFeeTotal(StsSearchParam stsSearchParam);

}

package com.cdqckj.gmis.operateparam.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
public interface PriceSchemeService extends SuperService<PriceScheme> {
    /**
     * 获取最新一条数据
     * @return
     */
    PriceScheme queryRecentRecord(Long useGasTypeId);

    /**
     * 根据用气类型id查询最新一条采暖方案
     * @param useGasTypeId
     * @return
     */
    PriceScheme queryRecentHeatingRecord(Long useGasTypeId);

    /**
     * 根据时间查询价格方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    PriceScheme queryRecentRecordByTime(Long useGasTypeId,LocalDate activateDate);

    /**
     * 预调价
     * @param useGasTypeId
     * @return
     */
    PriceScheme queryPrePriceScheme(Long useGasTypeId);

    /**
     * 查询采暖预调价
     * @param useGasTypeId
     * @return
     */
    PriceScheme queryPreHeatingPriceScheme(Long useGasTypeId);
    /**
     * 查询所欲气价方案
     * @return
     */
    List<PriceScheme> queryAllPriceScheme();

    /**
     * 根据id更新价格方案
     * @param priceScheme
     * @return
     */
    int updateByPriceId(PriceScheme priceScheme);

    /**
     * 将价格方案置为无效
     * @return
     */
    int updatePriceStatus();

    /**
     *
     * @param useGasTypeId
     * @return
     */
    List<PriceScheme> queryRecordNum(Long useGasTypeId);

    /**
     * 根据生效日期查询方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    PriceScheme queryPriceByTime(Long useGasTypeId, LocalDate activateDate);

    /**
     * 查询预调价方案
     * @param useGasTypeId
     * @return
     */
    PriceScheme queryPrePrice(Long useGasTypeId);

    /**
     * 根据抄表时间查询采暖方案
     * @param useGasTypeId
     * @param activateDate
     * @return
     */
    PriceScheme queryPriceHeatingByTime(Long useGasTypeId, LocalDate activateDate);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/3 15:37
    * @remark 这个用气类型的预调价方案
    */
    PriceScheme getAdvancePriceScheme(Long useGasTypeId, LocalDate now);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/12 17:15
    * @remark 获取这个时间段的气价方案
    */
    List<PriceScheme> getPriceSchemeDuring(StsSearchParam stsSearchParam);
}

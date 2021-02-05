package com.cdqckj.gmis.operateparam.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Repository
public interface PriceSchemeMapper extends SuperMapper<PriceScheme> {
     /**
      * 查询最新一条非采暖价格方案
      * @return
      */
     PriceScheme queryRecentRecord(@Param("useGasTypeId")Long useGasTypeId);

     /**
      * 根据用气类型Id查询最新一条采暖方案
      * @param useGasTypeId
      * @return
      */
     PriceScheme queryRecentHeatingRecord(@Param("useGasTypeId")Long useGasTypeId);

     /**
      * 根据生效时间查询用气类型
      * @param useGasTypeId
      * @param activateDate
      * @return
      */
     PriceScheme queryRecentRecordByTime(@Param("useGasTypeId")Long useGasTypeId, @Param("activateDate")LocalDate activateDate);

     /**
      * 预调价
      * @param useGasTypeId
      * @return
      */
     PriceScheme queryPrePriceScheme(@Param("useGasTypeId")Long useGasTypeId,@Param("now") LocalDate now);

     /**
      * 查询采暖预调价
      * @param useGasTypeId
      * @return
      */
     PriceScheme queryPreHeatingPriceScheme(@Param("useGasTypeId")Long useGasTypeId,@Param("now") LocalDate now);
     /**
      * 根据生效时间查询方案
      * @param useGasTypeId
      * @param activateDate
      * @return
      */
     PriceScheme queryPriceByTime(@Param("useGasTypeId")Long useGasTypeId, @Param("activateDate")LocalDate activateDate);

     /**
      * 根据生效时间查询采暖方案
      * @param useGasTypeId
      * @param activateDate
      * @return
      */
     PriceScheme queryPriceHeatingByTime(@Param("useGasTypeId")Long useGasTypeId, @Param("activateDate")LocalDate activateDate);

     /**
      * 将价格方案置为无效
      */
     int updatePriceStatus();

     /**
     * @Author: lijiangguo
     * @Date: 2020/12/3 15:38
     * @remark 预调价方案
     */
     PriceScheme getAdvancePriceScheme(@Param("useGasTypeId") Long useGasTypeId, @Param("now") LocalDate now);

     /**
     * @Author: lijiangguo
     * @Date: 2021/1/13 13:41
     * @remark 方案
     */
     List<PriceScheme> getPriceSchemeDuring(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("priceType") String priceType);
}

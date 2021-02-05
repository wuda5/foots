package com.cdqckj.gmis.pay.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.pay.entity.WxPay;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Repository
public interface WxPayMapper extends SuperMapper<WxPay> {

    boolean updatePayState(@Param("orderNumber") String orderNumber,@Param("payState") Integer payState);
}

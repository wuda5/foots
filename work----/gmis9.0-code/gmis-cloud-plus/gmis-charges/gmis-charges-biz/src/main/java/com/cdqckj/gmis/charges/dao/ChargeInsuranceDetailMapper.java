package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 保险明细
 * </p>
 *
 * @author tp
 * @date 2020-09-25
 */
@Repository
public interface ChargeInsuranceDetailMapper extends SuperMapper<ChargeInsuranceDetail> {

    /**
     * @auth lijianguo
     * @date 2020/11/7 15:15
     * @remark 有效的保单数目
     */
    Integer customerValidInsureSum(@Param("customerCode") String customerCode);
}

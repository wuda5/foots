package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 客户场景费用单
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Repository
public interface CustomerSceneChargeOrderMapper extends SuperMapper<CustomerSceneChargeOrder> {
    int updateChargeStatusComplete(@Param(value = "list") List<Long> list,@Param(value = "chargeNo") String chargeNo);
    int updateChargeStatusUncharge(List<Long> list);

}

package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.CustomerAccount;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 账户表
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Repository
public interface CustomerAccountMapper extends SuperMapper<CustomerAccount> {
    CustomerAccount queryAccountByCharge(@Param(value = "customerCode") String customerCode);

    /**
     * 关联查询最新的用户名
     *
     * @param customerAccount 查询条件
     * @return 账户
     */
    CustomerAccount queryAccountByParam(@Param(value = "customerAccount") CustomerAccount customerAccount);
}

package com.cdqckj.gmis.charges.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.CustomerAccountUpdateDTO;
import com.cdqckj.gmis.charges.dto.UpdateAccountParamDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;

/**
 * <p>
 * 业务接口
 * 账户表
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
public interface CustomerAccountService extends SuperService<CustomerAccount> {
    CustomerAccount queryAccountByCharge(String customerCode);
    CustomerAccount queryAccount(String customerCode);
    CustomerAccount queryByCustomerCode(String customerCode);

    /**
     * 换表更新账户余额并生成对应的流水数据
     *
     * @param updateDTO
     * @return
     */
    Boolean updateAccountAfterChangeMeter(UpdateAccountParamDTO updateDTO);

    /**
     * 关联查询最新的用户名
     *
     * @param customerAccount 查询条件
     * @return 账户
     */
    CustomerAccount queryAccountByParam(CustomerAccount customerAccount);

}

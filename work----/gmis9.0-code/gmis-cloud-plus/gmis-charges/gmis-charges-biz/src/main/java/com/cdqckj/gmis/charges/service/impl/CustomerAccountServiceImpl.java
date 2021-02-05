package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.CustomerAccountMapper;
import com.cdqckj.gmis.charges.dto.CustomerAccountUpdateDTO;
import com.cdqckj.gmis.charges.dto.UpdateAccountParamDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.service.CustomerAccountSerialService;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 业务实现类
 * 账户表
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerAccountServiceImpl extends SuperServiceImpl<CustomerAccountMapper, CustomerAccount> implements CustomerAccountService {

    @Autowired
    CustomerAccountSerialService customerAccountSerialService;

    public CustomerAccount queryAccountByCharge(String customerCode) {
        return baseMapper.queryAccountByCharge(customerCode);
    }
    public CustomerAccount queryAccount(String customerCode){
        return getOne(new LbqWrapper<CustomerAccount>().eq(CustomerAccount::getCustomerCode,customerCode)
        .eq(CustomerAccount::getStatus, DataStatusEnum.NORMAL.getValue())
        .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
        );
    }

    @Override
    public CustomerAccount queryByCustomerCode(String customerCode) {
        return super.getOne(Wraps.<CustomerAccount>lbQ().eq(CustomerAccount::getCustomerCode,customerCode));
    }

    /**
     * 更新账户余额并生成对应的流水数据
     *
     * @param updateDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAccountAfterChangeMeter(UpdateAccountParamDTO updateDTO) {
        if (Objects.isNull(updateDTO.getCustomerCode()) || Objects.isNull(updateDTO.getAccountMoney())) {
            throw new BizException("客户code或账户余额不能为空");
        }
        CustomerAccount account = baseMapper.queryAccountByCharge(updateDTO.getCustomerCode());

        CustomerAccountSerial customerAccountSerial = CustomerAccountSerial.builder()
                .accountCode(account.getAccountCode())
                .billNo(updateDTO.getBillNo())
                .billType(updateDTO.getBillType())
                .bookMoney(updateDTO.getAccountMoney())
                .bookAfterMoney(updateDTO.getAccountMoney().add(account.getAccountMoney()))
                .bookPreMoney(account.getAccountMoney())
//                .serialNo(BizCodeUtil.genSerialDataCode(BizSCode.CHARGE, BizCodeUtil.ACCOUNT_SERIAL))
                .serialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE))
                .customerCode(updateDTO.getCustomerCode())
                .businessHallId(updateDTO.getBusinessHallId())
                .businessHallName(updateDTO.getBusinessHallName())
                .build();
        customerAccountSerial.setGasMeterCode(updateDTO.getGasMeterCode());
        customerAccountSerialService.save(customerAccountSerial);

        CustomerAccount update = CustomerAccount.builder()
                .id(account.getId())
                .accountMoney(account.getAccountMoney().add(updateDTO.getAccountMoney()))
                .build();
        return updateById(update);
    }

    /**
     * 关联查询最新的用户名
     *
     * @param customerAccount 查询条件
     * @return 账户
     */
    @Override
    public CustomerAccount queryAccountByParam(CustomerAccount customerAccount) {
        return baseMapper.queryAccountByParam(customerAccount);
    }

}

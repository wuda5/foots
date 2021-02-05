package com.cdqckj.gmis.bizcenter.customer.archives.service.impl;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.MeterAccountCancelBizApi;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.customer.archives.service.CancelAccountService;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.charges.dto.CustomerAccountUpdateDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelSaveDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 销户聚合服务
 *
 * @author hp
 */
@Slf4j
@Service
public class CancelAccountServiceImpl implements CancelAccountService {
    @Autowired
    CustomerGasMeterRelatedBizApi gasMeterRelatedBizApi;
    @Autowired
    ReadMeterDataApi readMeterDataApi;
    @Autowired
    CustomerAccountBizApi customerAccountBizApi;
    @Autowired
    MeterAccountCancelBizApi meterAccountCancelBizApi;
    @Autowired
    CustomerBizApi customerBizApi;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<MeterAccountCancel> add(MeterAccountCancelSaveDTO saveDTO) {
        String customerCode = saveDTO.getCustomerCode();
        //校验表具状态
        List<MeterRelatedVO> data = gasMeterRelatedBizApi.queryAllRelation(customerCode).getData();

        if (!CollectionUtils.isEmpty(data)) {
            return R.fail("该用户还有未拆除的表具。");
        }
        //查询账户余额
        CustomerAccount account = customerAccountBizApi.queryValidAccount(customerCode).getData();

        if (Objects.nonNull(account) && Objects.nonNull(account.getAccountMoney())
                && BigDecimal.ZERO.compareTo(account.getAccountMoney()) < 0) {
            return R.fail("该用户账户还有余额，请先进行账户退费。");
        }

        R<MeterAccountCancel> save = meterAccountCancelBizApi.save(saveDTO);
        //客户档案逻辑删除
        Customer customer = customerBizApi.findCustomerByCode(customerCode).getData();
        CustomerUpdateDTO customerUpdateDTO = CustomerUpdateDTO.builder()
                .id(customer.getId())
                .deleteStatus(DeleteStatusEnum.DELETE.getValue())
                .deleteTime(LocalDateTime.now())
                .deleteUser(BaseContextHandler.getUserId())
                .build();
        customerBizApi.update(customerUpdateDTO);
        //账户表逻辑删除
        if (Objects.nonNull(account)) {
            CustomerAccountUpdateDTO updateDTO = CustomerAccountUpdateDTO.builder()
                    .id(account.getId())
                    .deleteStatus(DeleteStatusEnum.DELETE.getValue())
                    .build();
            customerAccountBizApi.update(updateDTO);
        }
        return save;
    }

    /**
     * 批量新增
     *
     * @param saveDTOList 新增的销户
     * @return 新增记录
     */
    @Override
    public R<Boolean> batchAdd(List<MeterAccountCancelSaveDTO> saveDTOList) {
        if (CollectionUtils.isEmpty(saveDTOList)) {
            return R.fail("请选择需要销户的客户");
        }
        boolean error = false;
        StringBuilder sb = new StringBuilder();
        List<CustomerAccount> accountList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        for (MeterAccountCancelSaveDTO saveDTO : saveDTOList) {
            List<MeterRelatedVO> data = gasMeterRelatedBizApi.queryAllRelation(saveDTO.getCustomerCode()).getData();
            if (!CollectionUtils.isEmpty(data)) {
                sb.append(saveDTO.getCustomerName()).append("还有未拆除的表具。");
                error = true;
            }
            Customer customer = customerBizApi.findCustomerByCode(saveDTO.getCustomerCode()).getData();
            if (CustomerStatusEnum.INVAID.getCode().equals(customer.getCustomerStatus())) {
                sb.append(saveDTO.getCustomerName()).append("已销户。");
                error = true;
            }
            customerList.add(customer);

            //查询账户余额
            CustomerAccount account = customerAccountBizApi.queryValidAccount(saveDTO.getCustomerCode()).getData();
            if (Objects.isNull(account)) {
                continue;
            }
            accountList.add(account);
            if (Objects.nonNull(account.getAccountMoney()) && BigDecimal.ZERO.compareTo(account.getAccountMoney()) < 0) {
                sb.append(saveDTO.getCustomerName()).append("账户还有余额，请先进行账户退费。");
                error = true;
            }
        }
        if (error) {
            return R.fail(sb.toString());
        }

        meterAccountCancelBizApi.saveList(saveDTOList);
        //客户档案逻辑删除
        List<CustomerUpdateDTO> collect = customerList.stream().map(temp -> CustomerUpdateDTO.builder()
                .id(temp.getId())
                .customerStatus(CustomerStatusEnum.INVAID.getCode())
                .deleteTime(LocalDateTime.now())
                .deleteUser(BaseContextHandler.getUserId())
                .build()).collect(Collectors.toList());
        customerBizApi.updateBatchById(collect);
        if (!CollectionUtils.isEmpty(accountList)) {
            List<CustomerAccountUpdateDTO> updateList = accountList.stream().map(temp -> CustomerAccountUpdateDTO.builder()
                    .id(temp.getId())
                    .deleteStatus(DeleteStatusEnum.DELETE.getValue())
                    .build()).collect(Collectors.toList());
            customerAccountBizApi.updateBatchById(updateList);
        }

        return R.success();
    }
}

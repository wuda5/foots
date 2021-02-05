package com.cdqckj.gmis.bizcenter.operation.config.service.impl;

import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.service.OperatorLimitService;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.model.RemoteData;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.operateparam.CompanyUserQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OperatorLimitServiceImpl extends SuperCenterServiceImpl implements OperatorLimitService {

    @Autowired
    private CompanyUserQuotaRecordBizApi companyUserQuotaRecordBizApi;
    @Autowired
    private UserBizApi userBizApi;
    @Autowired
    private BusinessHallBizApi businessHallBizApi;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<CompanyUserQuotaRecord> saveCompanyUserQuotaRecord(CompanyUserQuotaRecordSaveDTO saveDTO) {
        //校验： 查询对应营业厅的限额 hallBalance 对比旗厅下（组织）所有 操作员配额（根据orgId 查询 user）之和
        BusinessHall hall = businessHallBizApi.queryById(saveDTO.getBusinessHallId()).getData();
        // 1.总的营业厅限额
        BigDecimal hallBalance = hall.getBalance();
        Map<Long, User> userMap = userBizApi.query(new User().setOrg((new RemoteData<>(hall.getOrgId())))).getData().stream().collect(Collectors.toMap(User::getId, Function.identity()));
        // 2.总的营业厅下的操作员总共限额
        BigDecimal totalUserBalance = userMap.values().stream().filter(x->x.getBalance()!=null).map(aa -> aa.getBalance())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        User currUser = userMap.get(saveDTO.getUserId());
        BigDecimal newTotalUserBalance = totalUserBalance.add(
                "CUMULATIVE".equalsIgnoreCase(saveDTO.getQuotaType().getCode()) ?
                        saveDTO.getMoney() :
                        saveDTO.getMoney().subtract(currUser.getBalance()==null?BigDecimal.ZERO:currUser.getBalance())
        );
        if (BigDecimalUtils.greaterThan(newTotalUserBalance,hallBalance)){
            throw new BizException("所有操作员配额不能大于对应营业厅的限额");
        }

        //测试方便，暂时写在这
        CompanyUserQuotaRecord record = companyUserQuotaRecordBizApi.queryRecentRecord();
        if(record==null){
            saveDTO.setTotalMoney(saveDTO.getMoney());
            R<CompanyUserQuotaRecord> r = companyUserQuotaRecordBizApi.save(saveDTO);
            updateUser(r,saveDTO);
            return r;
        }

        if("CUMULATIVE".equalsIgnoreCase(saveDTO.getQuotaType().getCode())){
            // 累加
            saveDTO.setTotalMoney(saveDTO.getMoney().add(record.getTotalMoney()));
        }else{
            // 覆盖
            saveDTO.setTotalMoney(saveDTO.getMoney());
        }

        R<CompanyUserQuotaRecord> r = companyUserQuotaRecordBizApi.save(saveDTO);
        updateUser(r,saveDTO);
        return r;
    }

    /**
     * 更新用户配额
     * @param r
     * @param saveDTO
     */
    private void updateUser(R<CompanyUserQuotaRecord> r,CompanyUserQuotaRecordSaveDTO saveDTO){
        User userEntity = userBizApi.get(r.getData().getUserId()).getData();
        userEntity.setAccount(userEntity.getAccount());
        userEntity.setName(userEntity.getName());
        userEntity.setId(r.getData().getUserId());
        userEntity.setPointOfSale(saveDTO.getMoney());
        userEntity.setBalance(saveDTO.getTotalMoney());
        userEntity.setSingleLimit(saveDTO.getSingleLimit());
        userBizApi.update(userEntity);
    }
}

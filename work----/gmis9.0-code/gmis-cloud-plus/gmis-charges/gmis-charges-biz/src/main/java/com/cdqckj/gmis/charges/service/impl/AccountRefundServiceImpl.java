package com.cdqckj.gmis.charges.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dao.AccountRefundMapper;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.AccountRefund;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.charges.enums.RefundMethodEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.service.AccountRefundService;
import com.cdqckj.gmis.charges.service.CustomerAccountSerialService;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 账户退费记录
 * </p>
 *
 * @author tp
 * @date 2021-01-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AccountRefundServiceImpl extends SuperServiceImpl<AccountRefundMapper, AccountRefund> implements AccountRefundService {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerAccountService customerAccountService;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    CustomerAccountSerialService customerAccountSerialService;
    public R<AccountRefund> apply(AccountRefundApplyReqDTO applyDTO){

        List<AccountRefund> refunds=list(new LbqWrapper<AccountRefund>().eq(AccountRefund::getCustomerCode,applyDTO.getCustomerCode()));
        for (AccountRefund refund : refunds) {
            if(RefundStatusEnum.REFUNDABLE.eq(refund.getRefundStatus()) ||
                    RefundStatusEnum.WAITE_AUDIT.eq(refund.getRefundStatus()) ||
                    RefundStatusEnum.APPLY.eq(refund.getRefundStatus())
            ){
                throw BizException.wrap("存在未完成的退费申请");
            }
        }

        CustomerAccount account= customerAccountService.getOne(new LbqWrapper<CustomerAccount>()
            .eq(CustomerAccount::getCustomerCode,applyDTO.getCustomerCode())
                .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
                .eq(CustomerAccount::getStatus, DataStatusEnum.NORMAL.getValue())
        );
        if(applyDTO.getActualRefundMoney().compareTo(BigDecimal.ZERO)==0|| account.getAccountMoney().compareTo(applyDTO.getActualRefundMoney())<0){
            throw BizException.wrap("实退金额不能为0且不能大于账户余额");
        }
        if(!RefundMethodEnum.CASH.eq(applyDTO.getRefundMethod())){
            throw BizException.wrap("不支持的退费方式");
        }
//        Customer customer=customerService.findCustomer(applyDTO.getCustomerCode());
        AccountRefund applyInfo= AccountRefund.builder()
                .accountMoney(account.getAccountMoney())
                .refundMoney(applyDTO.getActualRefundMoney())
                .accountCode(account.getAccountCode())
                .customerName(account.getCustomerName())
                .customerCode(account.getCustomerCode())
                .auditUserId(BaseContextHandler.getUserId())
                .applyTime(LocalDateTime.now())
                .methodCode(applyDTO.getRefundMethod())
                .methodName(RefundMethodEnum.CASH.getDesc())
                .applyUserName(BaseContextHandler.getName())
                .applyReason(applyDTO.getRefundReason())
                .refundStatus(RefundStatusEnum.WAITE_AUDIT.getCode())
                .build();
        setCommonParams(applyInfo);
        save(applyInfo);
        //冻结账户余额
        frozenAccount(account,applyDTO.getActualRefundMoney(),applyInfo.getId().toString());
        return R.success(applyInfo);
    }
    @Transactional(rollbackFor = Exception.class)
    public R<AccountRefundResultDTO> refund(Long refundId) {
        AccountRefund refund= getById(refundId);
        if(refund==null || !RefundStatusEnum.REFUNDABLE.eq(refund.getRefundStatus())){
            //异常进入
            return R.fail("异常进入，不存在退费记录或者审核状态不是可退费状态");
        }
        AccountRefund updateDTO=new AccountRefund();
        updateDTO.setId(refundId);
        updateDTO.setRefundStatus(RefundStatusEnum.REFUNDED.getCode());
        updateDTO.setFinishTime(LocalDateTime.now());
        updateById(updateDTO);
        refundFrozenAccountSeria(refundId.toString());

        refund.setRefundStatus(RefundStatusEnum.REFUNDED.getCode());
        refund.setFinishTime(LocalDateTime.now());

        AccountRefundResultDTO refundResultDTO=new AccountRefundResultDTO();
        refundResultDTO.setAccountRefund(refund);
        return R.success(refundResultDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> audit(AccountRefundAuditReqDTO auditDTO){
        AccountRefund refund= getById(auditDTO.getRefundId());
        if(refund==null || !RefundStatusEnum.WAITE_AUDIT.eq(refund.getRefundStatus())){
            //异常进入
            return R.fail("异常进入，不存在退费记录或者审核状态不是待审核");
        }
        AccountRefund updateDTO=new AccountRefund();
        if(auditDTO.getStatus()){
            updateDTO.setRefundStatus(RefundStatusEnum.REFUNDABLE.getCode());
        }else{
            //不予退费
            CustomerAccount account= customerAccountService.getOne(new LbqWrapper<CustomerAccount>()
                    .eq(CustomerAccount::getCustomerCode,refund.getCustomerCode())
                    .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
                    .eq(CustomerAccount::getStatus, DataStatusEnum.NORMAL.getValue())
            );
            cancelRefundAccount(account,refund.getRefundMoney(),refund.getId().toString());
            updateDTO.setRefundStatus(RefundStatusEnum.NONREFUND.getCode());
        }
        updateDTO.setId(auditDTO.getRefundId());
        updateDTO.setAuditRemark(auditDTO.getAuditOpinion());
        updateDTO.setAuditUserId(BaseContextHandler.getUserId());
        updateDTO.setAuditUserName(BaseContextHandler.getName());
        updateDTO.setAuditTime(LocalDateTime.now());
        return R.success(updateById(updateDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> cancelRefund(Long refundId){
        AccountRefund refund= getById(refundId);
        if(refund==null){
            throw BizException.wrap("未知操作");
        }
        if(RefundStatusEnum.NONREFUND.eq(refund.getRefundStatus())){
            throw BizException.wrap("该单据已完成，不予退费，不能继续操作");
        }
        if(RefundStatusEnum.REFUNDED.eq(refund.getRefundStatus())){
            throw BizException.wrap("已退费完成不能取消");
        }
        CustomerAccount account= customerAccountService.getOne(new LbqWrapper<CustomerAccount>()
                .eq(CustomerAccount::getCustomerCode,refund.getCustomerCode())
                .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
                .eq(CustomerAccount::getStatus, DataStatusEnum.NORMAL.getValue())
        );
        cancelRefundAccount(account,refund.getRefundMoney(),refund.getId().toString());

        AccountRefund updateDTO=new AccountRefund();
        updateDTO.setRefundStatus(RefundStatusEnum.CANCEL.getCode());
        updateDTO.setId(refund.getId());
        return R.success(updateById(updateDTO));
    }

    public AccountRefundCheckDTO checkRefundApply(String customerCode){
        AccountRefundCheckDTO refundCheckDTO=new AccountRefundCheckDTO();
        List<AccountRefund> refunds=list(new LbqWrapper<AccountRefund>().eq(AccountRefund::getCustomerCode,customerCode));
        for (AccountRefund refund : refunds) {
            if(RefundStatusEnum.REFUNDABLE.eq(refund.getRefundStatus()) ||
                    RefundStatusEnum.WAITE_AUDIT.eq(refund.getRefundStatus()) ||
                    RefundStatusEnum.APPLY.eq(refund.getRefundStatus())
            ){
                refundCheckDTO.setNonRefund("存在未完成的退费申请");
                refundCheckDTO.setIsApplyRefund(false);
                return refundCheckDTO;
            }
        }

        CustomerAccount account= customerAccountService.getOne(new LbqWrapper<CustomerAccount>()
                .eq(CustomerAccount::getCustomerCode,customerCode)
                .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
                .eq(CustomerAccount::getStatus, DataStatusEnum.NORMAL.getValue())
        );
        if(account.getAccountMoney().compareTo(BigDecimal.ZERO)==0){
            refundCheckDTO.setNonRefund("账户金额为0");
            refundCheckDTO.setIsApplyRefund(false);
            return refundCheckDTO;
        }
        refundCheckDTO.setIsApplyRefund(true);
        return refundCheckDTO;
    }

    @Override
    public BigDecimal sumAccountRefundByTime(LocalDateTime startTime, LocalDateTime endTime) {
        long chargeUserId = BaseContextHandler.getUserId();
        BigDecimal sum = baseMapper.sumAccountRefundByTime(startTime, endTime, RefundStatusEnum.REFUNDED.getCode(), chargeUserId);
        return sum == null ? BigDecimal.ZERO : sum;
    }

    @Override
    public Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime) {
        List<String> refundStatusList = new ArrayList<>();
        refundStatusList.add(RefundStatusEnum.REFUNDED.getCode());
        refundStatusList.add(RefundStatusEnum.CANCEL.getCode());
        refundStatusList.add(RefundStatusEnum.NONREFUND.getCode());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<AccountRefund> wrapper =  new QueryWrapper<>();
        wrapper.lambda().notIn(AccountRefund::getRefundStatus, refundStatusList).eq(AccountRefund::getCreateUser, chargeUserId);
        if(ObjectUtil.isNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter))
                    .apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        return super.count(wrapper) > 0;
    }


    public Page<AccountRefundResDTO> pageList(PageParams<AccountRefundListReqDTO> params){

        Page<AccountRefundResDTO> page=new Page<>();
        AccountRefundListReqDTO model=params.getModel();
        model.setOrgIds(UserOrgIdUtil.getUserDataScopeList());
        if(StringUtils.isNotBlank(model.getAuditStatus())){
            if(RefundStatusEnum.AUDITED.eq(model.getAuditStatus())){
                List<String> list=new ArrayList<>();
                for (RefundStatusEnum value : RefundStatusEnum.values()) {
                    if(!RefundStatusEnum.WAITE_AUDIT.eq(value.getCode()) &&
                            !RefundStatusEnum.APPLY.eq(value.getCode())
                    ){
                        list.add(value.getCode());
                    }
                }
                model.setAuditStatus(StringUtils.join(list.toArray(),","));
            }
        }
        int count=baseMapper.pageListCount(model);
        List<AccountRefundResDTO> list= baseMapper.pageList(model, PageUtil.getStart((int)params.getCurrent()-1,(int)params.getSize()),(int)params.getSize());
        if(CollectionUtils.isNotEmpty(list)){
            page.setRecords(list);
        }
        page.setCurrent(params.getCurrent());
        page.setPages(PageUtil.totalPage(count,(int)params.getSize()));
        page.setSize(params.getSize());
        page.setTotal(count);
        return page;
     }

    public Page<CustomerAccountResDTO> custPageList(PageParams<CustomerAccountListReqDTO> params){
        Page<CustomerAccountResDTO> page=new Page<>();
        params.getModel().setOrgIds(UserOrgIdUtil.getUserDataScopeList());
        int count=baseMapper.custPageListCount(params.getModel());
        List<CustomerAccountResDTO> list= baseMapper.custPageList(params.getModel(), PageUtil.getStart((int)params.getCurrent()-1,(int)params.getSize()),(int)params.getSize());
        if(CollectionUtils.isNotEmpty(list)){
            String temp;
            for (CustomerAccountResDTO dto : list) {
                temp=dto.getIdentityCardNo();
                if(dto.getIdentityCardNo()!=null){
                    dto.setIdentityCardNo(temp.substring(0,4)+"******"+temp.substring(temp.length()-4,temp.length()));
                }
            }
            page.setRecords(list);
        }
        page.setCurrent(params.getCurrent());
        page.setPages(PageUtil.totalPage(count,(int)params.getSize()));
        page.setSize(params.getSize());
        page.setTotal(count);
        return page;    }


    /**
     * 取消退费，冻结还原账户
     * @param account
     */
    private void cancelRefundAccount(CustomerAccount account, BigDecimal refundMoney,String refundNo){
        CustomerAccountSerial saveDTO=createCancelAccountSeria(account,refundMoney,refundNo);
        customerAccountSerialService.save(saveDTO);
        //账户金额变更
        CustomerAccount accountUpdateDTO = new CustomerAccount();
        BeanUtils.copyProperties(account, accountUpdateDTO);
        accountUpdateDTO.setId(account.getId());
        accountUpdateDTO.setAccountMoney(saveDTO.getBookAfterMoney());
        accountUpdateDTO.setGiveMoney(saveDTO.getGiveBookAfterMoney());
        Boolean udpate=customerAccountService.updateById(accountUpdateDTO);
        if(!udpate){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX));
        }
    }


    /**
     * 冻结账户预存金额
     * @param account
     */
    private void frozenAccount(CustomerAccount account, BigDecimal refundMoney,String refundNo){
        BigDecimal zero = new BigDecimal("0.00");
        CustomerAccountSerial saveDTO=createFrozenAccountSeria(account,refundMoney,refundNo);
        if(saveDTO.getBookAfterMoney().compareTo(zero)<0){
            throw new BizException(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_INA));
        }
        customerAccountSerialService.save(saveDTO);

        //账户金额变更
        CustomerAccount accountUpdateDTO = new CustomerAccount();
        BeanUtils.copyProperties(account, accountUpdateDTO);
        accountUpdateDTO.setId(account.getId());
        accountUpdateDTO.setAccountMoney(saveDTO.getBookAfterMoney());
        accountUpdateDTO.setGiveMoney(saveDTO.getGiveBookAfterMoney());
        Boolean udpate=customerAccountService.updateById(accountUpdateDTO);
        if(!udpate){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX));
        }
    }

    /**
     * 账户退费冻结流水
     * @return
     */
    public CustomerAccountSerial createFrozenAccountSeria(CustomerAccount account,BigDecimal refundMoney,String refundNo){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        //refund no
        serial.setBillNo(refundNo);
        BigDecimal preCharge=refundMoney;
        serial.setBillType(AccountSerialSceneEnum.ACCOUNT_REFUND_FORZEN.getCode());
        serial.setBookMoney(preCharge);
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setBookAfterMoney(account.getAccountMoney().subtract(preCharge));
        //赠送金额无
        serial.setGiveBookMoney(BigDecimal.ZERO);
        serial.setGiveBookAfterMoney(account.getGiveMoney());
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setGasMeterCode(null);
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(account.getCustomerCode());
        serial.setBusinessHallId(account.getBusinessHallId());
        serial.setBusinessHallName(account.getBusinessHallName());
        return serial;
    }

    /**
     * update 冻结的账户流水改为退费 refund
     * @return
     */
    private void refundFrozenAccountSeria(String refundNo){
        CustomerAccountSerial serial = customerAccountSerialService.getOne(new LbqWrapper<CustomerAccountSerial>()
                .eq(CustomerAccountSerial::getBillNo,refundNo)
                .eq(CustomerAccountSerial::getBillType,AccountSerialSceneEnum.ACCOUNT_REFUND_FORZEN.getCode())
        );
        if(serial!=null ){
            CustomerAccountSerial serialupdate=new CustomerAccountSerial();
            serialupdate.setId(serial.getId());
            serialupdate.setBillType(AccountSerialSceneEnum.ACCOUNT_REFUND.getCode());
            customerAccountSerialService.updateById(serialupdate);
        }
    }


    /**
     * 账户退费取消冻结流水
     * @return
     */
    public CustomerAccountSerial createCancelAccountSeria(CustomerAccount account,BigDecimal refundMoney,String refundNo){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        serial.setBillNo(refundNo);
        serial.setBillType(AccountSerialSceneEnum.ACCOUNT_REFUND_CANCEL.getCode());
        serial.setBookMoney(refundMoney);
        serial.setBookAfterMoney(account.getAccountMoney().add(refundMoney));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookMoney(BigDecimal.ZERO);
        serial.setGiveBookAfterMoney(account.getGiveMoney());
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setGasMeterCode(null);
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.REFUND));
        serial.setCustomerCode(account.getCustomerCode());
        serial.setBusinessHallId(account.getBusinessHallId());
        serial.setBusinessHallName(account.getBusinessHallName());
        return serial;
    }
}

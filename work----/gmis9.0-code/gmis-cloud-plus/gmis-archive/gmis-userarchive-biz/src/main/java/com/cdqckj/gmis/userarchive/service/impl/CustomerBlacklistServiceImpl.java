package com.cdqckj.gmis.userarchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.userarchive.dao.CustomerBlacklistMapper;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import com.cdqckj.gmis.userarchive.enumeration.BlackStatusEnum;
import com.cdqckj.gmis.userarchive.service.CustomerBlacklistService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerBlacklistServiceImpl extends SuperServiceImpl<CustomerBlacklistMapper, CustomerBlacklist> implements CustomerBlacklistService {

    @Override
    public CustomerBlacklist findBlacklistStatusByCustomerCode(String customerCode) {
        LbqWrapper<CustomerBlacklist> wrapper=new LbqWrapper<>();
        wrapper.eq(CustomerBlacklist::getCustomerCode,customerCode);
        CustomerBlacklist customerBlacklist = this.baseMapper.selectOne(wrapper);
        return customerBlacklist;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean SetBlacklist(String customerCode) {
        CustomerBlacklist customerBlacklist=null;
        LbqWrapper<CustomerBlacklist> blacklistLbqWrapper = new LbqWrapper<>();
        blacklistLbqWrapper.eq(CustomerBlacklist::getCustomerCode,customerCode);
        customerBlacklist = this.getBaseMapper().selectOne(blacklistLbqWrapper);
        if(customerBlacklist ==null){
            customerBlacklist=new CustomerBlacklist();
            customerBlacklist.setCustomerCode(customerCode);
            // 1.表示黑名单 0 表示正常
            customerBlacklist.setStatus(BlackStatusEnum.Set_Blacklist.getCode());
            int insert = this.getBaseMapper().insert(customerBlacklist);
            if(insert>0){
               return true;
            }else {
                return false;
            }

        }
        customerBlacklist.setStatus(1);
        int update = this.getBaseMapper().update(customerBlacklist, blacklistLbqWrapper);
        if(update>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean RemoveBlacklist( String customerCode) {
        LbqWrapper<CustomerBlacklist> blacklistLbqWrapper = new LbqWrapper<>();
        blacklistLbqWrapper.eq(CustomerBlacklist::getCustomerCode,customerCode);
        CustomerBlacklist customerBlacklist = this.getBaseMapper().selectOne(blacklistLbqWrapper);
        customerBlacklist.setStatus(BlackStatusEnum.Remove_Blacklist.getCode() );
        int update = this.getBaseMapper().update(customerBlacklist,blacklistLbqWrapper);
        if(update>0){
            return true;
        }else {
            return false;
        }
    }

}

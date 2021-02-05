package com.cdqckj.gmis.userarchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.userarchive.dao.PurchaseLimitCustomerMapper;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.service.PurchaseLimitCustomerService;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class PurchaseLimitCustomerServiceImpl extends SuperServiceImpl<PurchaseLimitCustomerMapper, PurchaseLimitCustomer> implements PurchaseLimitCustomerService {
    @Override
    public Page<CustomerDeviceDTO> findLimitCustomerPage(CustomerDeviceLimitVO params) {
        Page<CustomerDeviceDTO> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.findLimitCustomerPage(page, params);
    }

    @Override
    public List<PurchaseLimitCustomer> selectByIds(List<Long> limitIds) {
        return baseMapper.selectByIds(limitIds);
    }
}

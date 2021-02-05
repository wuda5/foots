package com.cdqckj.gmis.tenant.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.common.domain.tenant.MulTenant;
import com.cdqckj.gmis.common.domain.tenant.MulTenantData;
import com.cdqckj.gmis.tenant.dao.TenantMapper;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/12 9:16
 * @remark: 请输入类说明
 */
@Log4j2
@Service
@DS("master")
public class MulTenantDataImpl implements MulTenantData {

    @Autowired
    TenantMapper tenantMapper;

    @Override
    public List<MulTenant> getAllTenantForMulTenant() {

        List<Tenant> tenantList = tenantMapper.selectList(null);
        return BeanPlusUtil.toBeanList(tenantList, MulTenant.class);
    }
}

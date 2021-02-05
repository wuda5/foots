package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.devicearchive.dao.CustomerGasMeterBindMapper;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterBindUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindPrame;
import com.cdqckj.gmis.devicearchive.dto.GasMeterBindResult;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterBind;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterBindService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.userarchive.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 客户气表绑定关系表 - 代缴业务
 * </p>
 *
 * @author wanghuaqiao
 * @date 2020-10-16
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerGasMeterBindServiceImpl extends SuperServiceImpl<CustomerGasMeterBindMapper, CustomerGasMeterBind> implements CustomerGasMeterBindService {
    @Override
    public Boolean check(CustomerGasMeterBindUpdateDTO customerUpdateDTO) {
        return super.count(Wraps.<CustomerGasMeterBind>lbQ()
                .eq(CustomerGasMeterBind::getCustomerCode,customerUpdateDTO.getCustomerCode())
                .eq(CustomerGasMeterBind::getBindCustomerCode,customerUpdateDTO.getBindCustomerCode())
        )>0;
    }

    @Override
    public Boolean checkAdd(CustomerGasMeterBindSaveDTO customerGasMeterBindSaveDTO) {
        return super.count(Wraps.<CustomerGasMeterBind>lbQ()
                .eq(CustomerGasMeterBind::getCustomerCode,customerGasMeterBindSaveDTO.getCustomerCode())
                .eq(CustomerGasMeterBind::getBindCustomerCode,customerGasMeterBindSaveDTO.getBindCustomerCode())
                .eq(CustomerGasMeterBind::getBindStatus,1)
        )>0;
    }

    @Override
    public List<GasMeterBindResult> getGasMeterInfo(GasMeterBindPrame gasMeterBindPrame) {
        return baseMapper.getGasMeterInfo(gasMeterBindPrame);
    }
}

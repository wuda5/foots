package com.cdqckj.gmis.userarchive.service.impl;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.userarchive.dao.CustomerMapper;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.userarchive.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerServiceImpl extends SuperServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private GasMeterService gasMeterService;
    @Override
    public IPage<CustomerSecurityQuerypParametersDto> findCustomerGmIf( CustomerSecurityQuerypParametersVo params) {
        Page<CustomerSecurityQuerypParametersDto> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.findCustomerGmIf(page, params);
    }

    @Override
    public Customer findCustomer(String customerCode) {
        LbqWrapper<Customer> customerLbqWrapper=new LbqWrapper<>();
        customerLbqWrapper.eq(Customer::getCustomerCode,customerCode);
//         Wraps.<Customer>lbQ().eq(Customer::getCustomerCode,customerCode);
        return baseMapper.selectOne(customerLbqWrapper);
    }
    /*编辑
     *
     * */
    @Override
    public Boolean updateCustomerMeter(CustomGasMesterVO params) {
        Customer customer=new Customer();
        if( ! StringUtils.isEmpty(params.getCustomerId())){
            customer.setId(params.getCustomerId());
        }
        if( ! StringUtils.isEmpty(params.getCustomerCode())){
            customer.setCustomerCode(params.getCustomerCode());
        }
        if( ! StringUtils.isEmpty(params.getCustomerName())){
            customer.setCustomerName(params.getCustomerName());
        }
        if( ! StringUtils.isEmpty(params.getTelphone())){
            customer.setTelphone(params.getTelphone());
        }
        if( ! StringUtils.isEmpty(params.getSex())){
            customer.setSex(params.getSex());
        }
        if( ! StringUtils.isEmpty(params.getContactAddress())){
            customer.setContactAddress(params.getContactAddress());
        }
        if( ! StringUtils.isEmpty(params.getRemark())){
            customer.setRemark(params.getRemark());
        }
        if( ! StringUtils.isEmpty(params.getCustomerTypeCode())){
            customer.setCustomerTypeCode(params.getCustomerTypeCode());
        }
        if( ! StringUtils.isEmpty(params.getIdTypeCode())){
            customer.setIdTypeCode(params.getIdTypeCode());
        }
        if( ! StringUtils.isEmpty(params.getIdNumber())){
            customer.setIdNumber(params.getIdNumber());
        }
        GasMeter gasMeter=new GasMeter();
        if( ! StringUtils.isEmpty(params.getGasMeterId())){
            gasMeter.setId(params.getGasMeterId());
        }
        if( ! StringUtils.isEmpty(params.getGasCode())){
            gasMeter.setGasCode(params.getGasCode());
        }
        if( ! StringUtils.isEmpty(params.getGasNeterFactoryID())){
            gasMeter.setGasMeterFactoryId(params.getGasNeterFactoryID());
        }
        if( ! StringUtils.isEmpty(params.getGasMeterVersionId())){
            gasMeter.setGasMeterVersionId(params.getGasMeterVersionId());
        }
        if( ! StringUtils.isEmpty(params.getDirection())){
            gasMeter.setDirection(params.getDirection());
        }
        if( ! StringUtils.isEmpty(params.getUseGasTypeId())){
            gasMeter.setUseGasTypeId(params.getUseGasTypeId());
        }
        if( ! StringUtils.isEmpty(params.getUseGasTypeCode())){
            gasMeter.setUseGasTypeCode(params.getUseGasTypeCode());
        }
        if( ! StringUtils.isEmpty(params.getStreetId())){
            gasMeter.setStreetId(params.getStreetId());
        }
        if( ! StringUtils.isEmpty(params.getCommunityId())){
            gasMeter.setCommunityId(params.getCommunityId());
        }
        if( ! StringUtils.isEmpty(params.getGasMeterAddress())){
            gasMeter.setGasMeterAddress(params.getGasMeterAddress());
        }
        if( ! StringUtils.isEmpty(params.getPopulation())){
            gasMeter.setPopulation(params.getPopulation());
        }
        if( ! StringUtils.isEmpty(params.getNodeNumber())){
            gasMeter.setNodeNumber(params.getNodeNumber());
        }
        if( ! StringUtils.isEmpty(params.getVentilateStatus())){
            gasMeter.setVentilateStatus(params.getVentilateStatus());
        }
        if( ! StringUtils.isEmpty(params.getVentilateUserId())){
            gasMeter.setVentilateUserId(params.getVentilateUserId());
        }
        if( ! StringUtils.isEmpty(params.getVentilateTime())){
            gasMeter.setVentilateTime(params.getVentilateTime());
        }
        if( ! StringUtils.isEmpty(params.getSecurityUserId())){
            gasMeter.setSecurityUserId(params.getSecurityUserId());
        }
        if( ! StringUtils.isEmpty(params.getLatitude())){
            gasMeter.setLatitude(params.getLatitude());
        }
        if( ! StringUtils.isEmpty(params.getLongitude())){
            gasMeter.setLongitude(params.getLongitude());
        }
        int updatecustomer = baseMapper.updateById(customer);
        boolean updategasMeter = gasMeterService.updateById(gasMeter);
        if(updatecustomer>0 && updategasMeter==true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public IPage<GasMeterCustomerDto> findGasMeterCustomer(GasMeterCustomerParme parmes) {
        Page<GasMeterCustomerDto> page = new Page<>(parmes.getPageNo(),parmes.getPageSize());
        return baseMapper.findGasMeterCustomer(page,parmes);
    }



    @Override
    public List<String> findIdNumber() {
        return baseMapper.findIdNumber();
    }

    @Override
    public Page<Customer> findCustomerPage(CustomerPageVo customerPageVo) {
//        10000
//        0,100,
//        101,200
        Page<CustomerPageDTO> page = new Page<>(customerPageVo.getPageNo(),customerPageVo.getPageSize());
        return baseMapper.findCustomerPage(page,customerPageVo,null);
//        return baseMapper.findCustomerPage(page,customerPageVo,new DataScope());
    }

    @Override
    public Page<CustomerGasDto> findCustomerGasMeterPage(CustomerPageVo customerPageVo) {
        customerPageVo.setOrgIds(UserOrgIdUtil.getUserDataScopeList());
        Page<CustomerGasDto> page = new Page<>(customerPageVo.getPageNo(),customerPageVo.getPageSize());
        return baseMapper.findCustomerGasMeterPage(page,customerPageVo,new DataScope());
    }

    @Override
    public Page<CustomerGasDto> findCustomerGasMeterPageTwo(CustomerPageVo customerPageVo) {
        customerPageVo.setOrgIds(UserOrgIdUtil.getUserDataScopeList());
        Page<CustomerGasDto> page = new Page<>(customerPageVo.getPageNo(),customerPageVo.getPageSize());
        return baseMapper.findCustomerGasMeterPageTwo(page,customerPageVo,new DataScope());
    }

    @Override
    public Boolean check(CustomerUpdateDTO customerUpdateDTO) {
        return super.count(Wraps.<Customer>lbQ()
                .eq(Customer::getIdCard,customerUpdateDTO.getIdCard())
                .ne(Customer::getId,customerUpdateDTO.getId())
                .ne(Customer::getCustomerStatus,2)
        )>0;
    }

    @Override
    public Boolean checkByCustomerCode(CustomerUpdateDTO customerUpdateDTO) {
        return super.count(Wraps.<Customer>lbQ()
                .eq(Customer::getCustomerCode,customerUpdateDTO.getCustomerCode()))>0;
    }

    @Override
    public Boolean checkAdd(CustomerSaveDTO customerSaveDTO) {
        return super.count(Wraps.<Customer>lbQ()
                .eq(Customer::getIdCard,customerSaveDTO.getIdCard())
                .eq(Customer::getCustomerStatus,1)
        )>0;
    }

    @Override
    public List<CustomerGasInfoDTO> findGasByChargeNos(List<String> customerChargeNos) {

        return baseMapper.findGasByChargeNos(customerChargeNos);
    }
    /**
     * 获取兴趣关注点客户信息
     * @param params
     * @return
     */
    @Override
    public ConcernsCustomer getConcernsCustomer(Customer params){
        return baseMapper.getConcernsCustomer(params);
    }

    @Override
    public List<StsInfoBaseVo<Integer, Long>> stsByCustomerStatus(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsByCustomerStatus(stsSearchParam, dataScope);
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsCustomType(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsCustomType(stsSearchParam, dataScope);
    }

    @Override
    public Long stsCustomBlackNum(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsCustomBlackNum(stsSearchParam, dataScope);
    }
}




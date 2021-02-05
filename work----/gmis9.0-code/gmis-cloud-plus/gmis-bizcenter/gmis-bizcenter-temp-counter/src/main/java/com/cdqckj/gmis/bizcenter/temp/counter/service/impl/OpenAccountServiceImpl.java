package com.cdqckj.gmis.bizcenter.temp.counter.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.temp.counter.component.IotOpenAccountComponent;
import com.cdqckj.gmis.bizcenter.temp.counter.constants.TempCounterMessageConstants;
import com.cdqckj.gmis.bizcenter.temp.counter.service.OpenAccountService;
import com.cdqckj.gmis.biztemporary.CustomerTempBizApi;
import com.cdqckj.gmis.biztemporary.GasMeterTempBizApi;
import com.cdqckj.gmis.biztemporary.OpenAccountRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.CustomerTemp;
import com.cdqckj.gmis.biztemporary.entity.GasMeterTemp;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.biztemporary.enums.ChargeStateEnum;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OpenAccountServiceImpl implements OpenAccountService {
    @Autowired
    private OpenAccountRecordBizApi openAccountRecordBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Resource
    private I18nUtil i18nUtil;
    @Autowired
    private IotOpenAccountComponent iotOpenAccountComponent;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private CustomerAccountBizApi customerAccountBizApi;
    @Autowired
    public ReadMeterLatestRecordApi readMeterLatestApi;
    @Autowired
    public GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private GasMeterTempBizApi gasMeterTempBizApi;
    @Autowired
    private CustomerTempBizApi customerTempBizApi;
    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;

    @GlobalTransactional
    @Override
    public R<Boolean> chargeCallback(final String bizNoOrId, final Boolean isRefund) {
        try {
            //已收费回调直接返回
            OpenAccountRecord openAccountRecordParam = new OpenAccountRecord();
            openAccountRecordParam.setId(Long.parseLong(bizNoOrId));
            openAccountRecordParam.setStatus(ChargeStateEnum.CHARGED.getCode());
            R<List<OpenAccountRecord>> openAccountRecordListR = openAccountRecordBizApi.query(openAccountRecordParam);
            if(openAccountRecordListR.getIsError()){
                throw new BizException("查询开户记录异常");
            }
            if(ObjectUtil.isNotNull(openAccountRecordListR.getData()) && openAccountRecordListR.getData().size() > 0){
                return R.success(true);
            }
            //查询开户记录
            OpenAccountRecord openAccountRecord = getOpenAccountRecord(bizNoOrId);
            String gasMeterCode =  openAccountRecord.getGasMeterId();
            String customerCode =  openAccountRecord.getCustomerId();
            long customerGasMeterRelateId = openAccountRecord.getRelateId();

            //查询客户表具关联关系
            R<List<CustomerGasMeterRelated>> customerGasMeterRelateListR = getCustomerMeterRelationListR(customerGasMeterRelateId, false);
            //@tangping 临时解决方案，开户退费后，进行收费回调已没有处理关系，后续在收费项中增加回调标识比卖你该问题
            if(CollectionUtils.isEmpty(customerGasMeterRelateListR.getData())){
                return R.success(true);
            }
            CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelateListR.getData().get(0);
            //客户、表具信息置为有效
            handleCustomer(customerCode);
            //收完费开户才成功
            CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO = CustomerGasMeterRelatedUpdateDTO
                    .builder()
                    .id(customerGasMeterRelateId)
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            customerGasMeterRelatedBizApi.update(customerGasMeterRelatedUpdateDTO);
            //查询客户信息
            R<Customer> customerR = customerBizApi.findCustomerByCode(customerCode);
            if(customerR.getIsError() || ObjectUtil.isNull(customerR.getData())){
                throw new BizException("查询客户信息异常");
            }
            Customer customer = customerR.getData();
            //收费回调
            new OpenAccountRecordUpdateDTO();
            if(!isRefund) {
                //更新开户记录收费状态
                OpenAccountRecordUpdateDTO updateDTO = OpenAccountRecordUpdateDTO
                        .builder()
                        .relateId(customerGasMeterRelateId)
                        .status(ChargeStateEnum.CHARGED.getCode())
                        .build();
                openAccountRecordBizApi.updateByRelateId(updateDTO);
                //物联网开户
                //收费回调更新表具信息
                updateGasMeterCallback(gasMeterCode);
                R<GasMeter> gasMeterR = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
                GasMeter gasMeter = gasMeterR.getData();
                if(gasMeterR.getIsError() || ObjectUtil.isNull(gasMeterR.getData())){
                    throw new BizException("查询气表异常");
                }
                GasMeterFactory factory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
                gasMeter.setGasMeterNumber(StringUtils.isBlank(gasMeter.getGasMeterNumber())?BizCodeNewUtil.genGasMeterNumber(gasMeter.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), gasMeter.getGasMeterNumber()):gasMeter.getGasMeterNumber());
                gasMeterBizApi.updateByCode(gasMeter);
                //气价方案
                PriceScheme priceScheme = null;
                try {
                    priceScheme = getEffectivePriceScheme(gasMeter);
                } catch (Exception e) {
                    log.error("查询气价方案异常，{}", e.getMessage(), e);
                    return R.fail("查询气价方案异常");
                } finally {
                }
                //新增气表使用情况
                GasMeterInfoSaveDTO gasMeterInfoSave= GasMeterInfoSaveDTO
                        .builder()
                        .gasmeterCode(gasMeterCode)
                        .customerCode(customerCode)
                        .customerName(customer.getCustomerName())
                        .priceSchemeId(priceScheme.getId())
                        .dataStatus(DataStatusEnum.NORMAL.getValue())
                        .build();
                gasMeterInfoBizApi.save(gasMeterInfoSave);
                R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
                if(gasMeterVersionR.getIsError() || ObjectUtil.isNull(gasMeterVersionR.getData())){
                    throw new BizException("查询版号异常");
                }
                if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName().trim())||
                        OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())||
                        OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())){
                    //物联网表下发开户指令
                    iotOpenAccountComponent.iotOpenAccount(gasMeter);
                    //物联网表更新远传设备
                    iotOpenAccountComponent.updateIotDevice(gasMeter, customerCode);
                }
                //保存抄表初始数据
                saveInitMeterLatest(gasMeter, customer, customerGasMeterRelated.getCustomerChargeNo());
            }
            //退费回调
            else{
                //更新开户记录收费状态
                OpenAccountRecordUpdateDTO updateDTO = OpenAccountRecordUpdateDTO
                        .builder()
                        .relateId(customerGasMeterRelateId)
                        .status(ChargeStateEnum.WAIT_CHARGE.getCode())
                        .build();
                openAccountRecordBizApi.updateByRelateId(updateDTO);
            }

        } catch (Exception e) {
            log.error("\""+i18nUtil.getMessage("ABNORMAL_ACCOUNT_OPENING_CHARGE_CALLBACK")+",异常信息为{}\"",e.getMessage(),e);
            throw new BizException(e.getMessage());
        } finally {
        }
        return R.success(true);
    }

    /**
     * 收费回调更新表具信息
     * @param gasMeterCode
     */
    private void updateGasMeterCallback(String gasMeterCode) {
        R<List<GasMeterTemp>> gasMeterTempListR = gasMeterTempBizApi.query(new GasMeterTemp().setGasCode(gasMeterCode));
        if(gasMeterTempListR.getIsError()){
            throw new BizException("查询表具临时表异常");
        }
        //已存在表具
        if(ObjectUtil.isNull(gasMeterTempListR.getData()) || gasMeterTempListR.getData().size() == 0){
            //收费完成后将表具从待开户状态改为待通气
            GasMeter gasMeterParam = GasMeter
                    .builder()
                    .gasCode(gasMeterCode)
                    .removeGasmeter("000000")
                    .dataStatus(GasMeterStatusEnum.UnVentilation.getCode())
                    .build();
            gasMeterBizApi.updateByCode(gasMeterParam);
            return;
        }
        GasMeterTemp gasMeterTemp = gasMeterTempListR.getData().get(0);
        GasMeterFactory factory = gasMeterFactoryBizApi.get(gasMeterTemp.getGasMeterFactoryId()).getData();
        GasMeter gasMeterNew = GasMeter
                .builder()
                .gasCode(gasMeterTemp.getGasCode())
                .gasMeterFactoryId(gasMeterTemp.getGasMeterFactoryId())
                .gasMeterVersionId(gasMeterTemp.getGasMeterVersionId())
                .gasMeterModelId(gasMeterTemp.getGasMeterModelId())
                .installNumber(gasMeterTemp.getInstallNumber())
                .installTime(gasMeterTemp.getInstallTime())
                .direction(gasMeterTemp.getDirection())
                .provinceCode(gasMeterTemp.getProvinceCode())
                .cityCode(gasMeterTemp.getCityCode())
                .areaCode(gasMeterTemp.getAreaCode())
                .streetId(gasMeterTemp.getStreetId())
                .communityId(gasMeterTemp.getCommunityId())
                .communityName(gasMeterTemp.getCommunityName())
                .gasMeterAddress(gasMeterTemp.getGasMeterAddress())
                .moreGasMeterAddress(gasMeterTemp.getMoreGasMeterAddress())
                .useGasTypeId(gasMeterTemp.getUseGasTypeId())
                .useGasTypeName(gasMeterTemp.getUseGasTypeName())
                .population(gasMeterTemp.getPopulation())
                .nodeNumber(gasMeterTemp.getNodeNumber())
                .longitude(gasMeterTemp.getLongitude())
                .latitude(gasMeterTemp.getLatitude())
                .gasMeterNumber(StringUtils.isBlank(gasMeterTemp.getGasMeterNumber())?BizCodeNewUtil.genGasMeterNumber(gasMeterTemp.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), gasMeterTemp.getGasMeterNumber()):gasMeterTemp.getGasMeterNumber())
                .gasMeterBase(gasMeterTemp.getGasMeterBase())
                .dataStatus(GasMeterStatusEnum.UnVentilation.getCode())
                .removeGasmeter("000000")
                .build();
        if (!StringUtils.isBlank(gasMeterTemp.getGasMeterNumber())){
            gasMeterNew.setAutoGenerated(1);
        }
        gasMeterBizApi.updateByCode(gasMeterNew);
    }

    @GlobalTransactional
    @Override
    public R<Boolean> noChargeCallback(final String bizNoOrId, final long priceSchemeId) {
        try {
            //查询开户记录
            OpenAccountRecord openAccountRecord = getOpenAccountRecord(bizNoOrId);
            String gasMeterCode =  openAccountRecord.getGasMeterId();
            String customerCode =  openAccountRecord.getCustomerId();
            long customerGasMeterRelateId = openAccountRecord.getRelateId();
            //查询客户表具关联关系
            R<List<CustomerGasMeterRelated>> customerGasMeterRelateListR = getCustomerMeterRelationListR(customerGasMeterRelateId, true);
            CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelateListR.getData().get(0);
            String customerChargeNo = customerGasMeterRelated.getCustomerChargeNo();
            //客户、表具信息置为有效
            Customer customer = handleCustomer(customerCode);

            //查询表具信息
            R<GasMeter> gasMeterR = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
            if(gasMeterR.getIsError() || ObjectUtil.isNull(gasMeterR.getData())){
                throw new BizException("查询气表异常");
            }
            GasMeter gasMeter = gasMeterR.getData();
            GasMeterFactory factory = gasMeterFactoryBizApi.get(gasMeter.getGasMeterFactoryId()).getData();
            gasMeterBizApi.updateByCode(new GasMeter().setGasCode(gasMeterCode).setDataStatus(GasMeterStatusEnum.UnVentilation.getCode()).setRemoveGasmeter("000000")
                    .setGasMeterNumber(StringUtils.isBlank(gasMeter.getGasMeterNumber())?BizCodeNewUtil.genGasMeterNumber(gasMeter.getGasMeterFactoryId(), factory.getGasMeterFactoryCode(), gasMeter.getGasMeterNumber()):gasMeter.getGasMeterNumber()));
            //查询客户信息
            R<Customer> customerR = customerBizApi.findCustomerByCode(customerGasMeterRelated.getCustomerCode());
            if(customerR.getIsError() || ObjectUtil.isNull(customerR.getData())){
                throw new BizException("查询客户信息异常");
            }
            customer = customerR.getData();
            //新增气表使用情况
            GasMeterInfoSaveDTO gasMeterInfoSave= GasMeterInfoSaveDTO
                    .builder()
                    .gasmeterCode(gasMeterCode)
                    .customerCode(customerCode)
                    .customerName(customer.getCustomerName())
                    .priceSchemeId(priceSchemeId)
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .build();
            gasMeterInfoBizApi.save(gasMeterInfoSave);
            //查询版号
            R<GasMeterVersion> gasMeterVersionR = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId());
            if(gasMeterVersionR.getIsError() || ObjectUtil.isNull(gasMeterVersionR.getData())){
                throw new BizException("查询版号异常");
            }
            //物联网表判断
            if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName().trim())||
                    OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())||
                    OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersionR.getData().getOrderSourceName())){
                //物联网表下发开户指令
                iotOpenAccountComponent.iotOpenAccount(gasMeter);
                //物联网表更新远传设备
                iotOpenAccountComponent.updateIotDevice(gasMeter, customerCode);
            }

            //保存抄表初始数据
            saveInitMeterLatest(gasMeter, customer, customerChargeNo);
        } catch (BizException e) {
            log.error("\""+i18nUtil.getMessage("ABNORMAL_ACCOUNT_OPENING_CHARGE_CALLBACK")+",异常信息为{}\"",e.getMessage(),e);
            throw new BizException(e.getMessage());
        } finally {
        }
        return R.success(true);
    }

    private Customer handleCustomer(String customerCode) {
        Customer customer;
        R<List<CustomerTemp>> customerTempsR = customerTempBizApi.query(new CustomerTemp().setCustomerCode(customerCode));
        if (customerTempsR.getIsError()) {
            throw new BizException("查询客户临时表异常");
        }
        if (ObjectUtil.isNotNull(customerTempsR.getData()) && customerTempsR.getData().size() > 0) {
            CustomerTemp customerTemp = customerTempsR.getData().get(0);
            customer = new Customer().setCustomerCode(customerCode)
                    .setCustomerName(customerTemp.getCustomerName())
                    .setTelphone(customerTemp.getTelphone())
                    .setCustomerTypeCode(customerTemp.getCustomerTypeCode())
                    .setCustomerTypeName(customerTemp.getCustomerTypeName())
                    .setSex(customerTemp.getSex())
                    .setIdCard(customerTemp.getIdCard())
                    .setContactAddress(customerTemp.getContactAddress())
                    .setRemark(customerTemp.getRemark());
            customerBizApi.updateCustomerByCode(customer);
            List<Long> ids = new ArrayList<>();
            ids.add(customerTemp.getId());
            customerTempBizApi.delete(ids);
        } else {
            customer = new Customer().setCustomerCode(customerCode).setCustomerStatus(CustomerStatusEnum.EFFECTIVE.getCode());
            customerBizApi.updateCustomerByCode(customer);
        }
        return customer;
    }

    private OpenAccountRecord getOpenAccountRecord(String bizNoOrId) {
        OpenAccountRecord openAccountRecordParams = new OpenAccountRecord();
        openAccountRecordParams.setId(Long.parseLong(bizNoOrId));
        R<List<OpenAccountRecord>> openAccountRecordsR = openAccountRecordBizApi.query(openAccountRecordParams);
        if (openAccountRecordsR.getIsError() || ObjectUtil.isNull(openAccountRecordsR.getData()) || openAccountRecordsR.getData().size() == 0) {
            throw new BizException("查询开户记录异常");
        }
        return openAccountRecordsR.getData().get(0);
    }

    /**
     * 查询气价方案
     * @param gasMeter
     */
    private PriceScheme getEffectivePriceScheme(GasMeter gasMeter) {
        Long useGasTypeId = gasMeter.getUseGasTypeId();
        R<UseGasType> useGasTypeR = useGasTypeBizApi.get(useGasTypeId);
        if(useGasTypeR.getIsError()){
            throw new BizException("查询用气类型异常");
        }
        PriceScheme priceScheme = null;
        if(useGasTypeR.getData().getPriceType().equals(PriceType.HEATING_PRICE.getCode())){
            priceScheme = priceSchemeBizApi.queryPriceHeatingByTime(useGasTypeId, LocalDate.now());
        }else {
            priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
        }
        return priceScheme;
    }

    /**
     * 保存抄表初始数据
     * @param gasMeter
     * @param customer
     * @param chargeNo
     */
    private void saveInitMeterLatest(GasMeter gasMeter,Customer customer, String chargeNo) {
        String gasCode = gasMeter.getGasCode();
        List<ReadMeterLatestRecord> latestRecords = readMeterLatestApi.queryListByGasCodes(Arrays.asList(gasCode)).getData();
        if(latestRecords.size()==0){
            ReadMeterLatestRecordSaveDTO save = new ReadMeterLatestRecordSaveDTO();
            save.setYear(2000).setMonth(1);// 还得 设置此 表具初始的年月（2000.01） 和 --》底数（从表具档案获得）
            save.setReadTime(DateUtils.getDate8(2000, 1));
            BigDecimal gasMeterBase = gasMeter.getGasMeterBase();
            save.setCurrentTotalGas(gasMeterBase!=null?gasMeterBase:new BigDecimal(0.0000));
            save.setCustomerId(customer.getId().toString())
                    .setCustomerCode(customer.getCustomerCode()).setCustomerName(customer.getCustomerName())
                    .setGasMeterCode(gasMeter.getGasCode()).setGasMeterNumber(gasMeter.getGasMeterNumber());
            readMeterLatestApi.save(save);
        }
    }
    /**
     * 查询客户表具关联关系
     * @param bizNoOrId
     * @return
     */
    private R<List<CustomerGasMeterRelated>> getCustomerMeterRelationListR(final long bizNoOrId, Boolean flag) {
        Integer dataStatus = DataStatusEnum.NOT_AVAILABLE.getValue();
        if(flag){
             dataStatus = DataStatusEnum.NORMAL.getValue();
        }
        CustomerGasMeterRelated customerGasMeterRelate = CustomerGasMeterRelated
                .builder()
                .id(bizNoOrId)
                .dataStatus(dataStatus)
                .build();
        R<List<CustomerGasMeterRelated>> customerGasMeterRelateListR = customerGasMeterRelatedBizApi.query(customerGasMeterRelate);
        if (customerGasMeterRelateListR.getIsError() || ObjectUtil.isNull(customerGasMeterRelateListR.getData())) {
            throw new BizException(i18nUtil.getMessage(TempCounterMessageConstants.QUERY_ACCOUNT_OPENING_ASSOCIATION_EXCEPTION));
        }
        if (customerGasMeterRelateListR.getData().size() > 1) {
            throw new BizException(i18nUtil.getMessage(TempCounterMessageConstants.NUMBER_OF_OPEN_ACCOUNT_GREATER_THAN_ONE));
        }
        return customerGasMeterRelateListR;
    }
    /**
     * 创建账户
     * @param customer
     */
//    private void createAccount(Customer customer) {
//        //判断账户是否存在
//        new CustomerAccountUpdateDTO();
//        CustomerAccountUpdateDTO customerAccountUpdateDTO = CustomerAccountUpdateDTO
//                .builder()
//                .customerCode(customer.getCustomerCode())
//                .build();
//        R<Boolean> flagR = customerAccountBizApi.check(customerAccountUpdateDTO);
//        if(flagR.getIsError()){
//                throw new BizException("判断账户是否存在异常");
//        }
//        //不存在
//        if(!flagR.getData()) {
//            new CustomerAccount();
//            CustomerAccount customerAccount = CustomerAccount
//                    .builder()
//                    .companyCode(BaseContextHandler.getTenantId())
//                    .companyName(BaseContextHandler.getTenantName())
//                    .orgId(BaseContextHandler.getOrgId())
//                    .orgName(BaseContextHandler.getOrgName())
//                    .accountCode(BizCodeNewUtil.genAccountDataCode())
//                    .customerCode(customer.getCustomerCode())
//                    .customerName(customer.getCustomerName())
//                    .accountMoney(BigDecimal.ZERO)
//                    .giveMoney(BigDecimal.ZERO)
//                    .status(AccountStateEnum.ACTIVE.getCode())
//                    .build();
//            CustomerAccountSaveDTO customerAccountSaveDTO = BeanUtil.toBean(customerAccount, CustomerAccountSaveDTO.class);
//            customerAccountBizApi.save(customerAccountSaveDTO);
//        }
//    }

}

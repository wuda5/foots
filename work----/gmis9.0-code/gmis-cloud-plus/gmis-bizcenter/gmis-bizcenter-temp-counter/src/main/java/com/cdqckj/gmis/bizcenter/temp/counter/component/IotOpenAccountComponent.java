package com.cdqckj.gmis.bizcenter.temp.counter.component;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.iot.qc.vo.OpenAccountVO;
import com.cdqckj.gmis.iot.qc.vo.PriceVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateDeviceVO;
import com.cdqckj.gmis.operateparam.PriceMappingBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * songyz
 */
@Component
@Slf4j
public class IotOpenAccountComponent {
    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PriceSchemeService priceSchemeService;
    @Autowired
    private UseGasTypeService useGasTypeService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private PriceMappingBizApi priceMappingBizApi;
    /**
     * 物联网表更新远传设备
     * @param gasMeter
     * @param customerCode
     */
    public void updateIotDevice(GasMeter gasMeter, String customerCode) {
        //查询客户信息
        R<Customer> customerR = customerBizApi.findCustomerByCode(customerCode);
        if(customerR.getIsError() || ObjectUtil.isNull(customerR.getData())){
            throw new BizException("查询客户信息异常");
        }
        //物联网表更新远传设备
        UpdateDeviceVO updateDeviceVO = new UpdateDeviceVO();
        updateDeviceVO.setAddress(gasMeter.getMoreGasMeterAddress());
        updateDeviceVO.setCustomerCode(customerCode);
        updateDeviceVO.setCustomerName(customerR.getData().getCustomerName());
        //客户类型
        String customerTypeCode = customerR.getData().getCustomerTypeCode();
        switch (customerTypeCode){
            case "RESIDENT":
                updateDeviceVO.setCustomerType(0);
                break;
            case "BUSINESS":
                updateDeviceVO.setCustomerType(1);
                break;
            case "PUBLIC_WELFARE":
                updateDeviceVO.setCustomerType(3);
                break;
            case "INDUSTRY":
                updateDeviceVO.setCustomerType(2);
                break;
            default:
                break;
        }
        updateDeviceVO.setTelephone(customerR.getData().getTelphone());
        updateDeviceVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
        updateDeviceVO.setLatitude(gasMeter.getLatitude());
        updateDeviceVO.setLongitude(gasMeter.getLongitude());
        updateDeviceVO.setDeviceType(0);
        updateDeviceVO.setDirection(gasMeter.getDirection());
        //
//        R<GasMeterInfo> gasMeterInfoR = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeter.getGasCode(),customerCode);
//        if(gasMeterInfoR.getIsError() || ObjectUtil.isNull(gasMeterInfoR.getData())){
//            throw new BizException("查询气表使用情况异常");
//        }
        updateDeviceVO.setBaseNumber(gasMeter.getGasMeterBase() == null ? BigDecimal.ZERO : gasMeter.getGasMeterBase());
        String installationTime;
        if(gasMeter.getInstallTime() == null){
            installationTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        }else {
            installationTime = gasMeter.getInstallTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        }
        updateDeviceVO.setInstallationTime(Long.parseLong(installationTime));
        Long ignitionTime;
        if(gasMeter.getVentilateTime() == null) {
            ignitionTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }else{
            ignitionTime = gasMeter.getVentilateTime().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        updateDeviceVO.setIgnitionTime(ignitionTime);
        deviceService.updateDevice(updateDeviceVO);
    }

    /**
     * 物联网表下发开户指令
     * @param gasMeter
     * @throws Exception
     */
    public void iotOpenAccount(GasMeter gasMeter){
        //查询气价配置信息
        PriceScheme priceScheme = getEffectivePriceScheme(gasMeter.getUseGasTypeId());


        LbqWrapper<UseGasType> useGasTypeWrapper = Wraps.lbQ();
        useGasTypeWrapper.eq(UseGasType::getId, gasMeter.getUseGasTypeId());
        UseGasType useGasType = useGasTypeService.getOne(useGasTypeWrapper);
        priceScheme = isPopulationAddPriceNode(priceScheme,gasMeter.getPopulation(),useGasType);
        OpenAccountVO openAccountVO = new OpenAccountVO();

        PriceMapping priceMapping = priceMappingBizApi.getGasMeterPriceMapping(gasMeter.getGasCode());
        PriceMapping mapping = new PriceMapping();
        if(null==priceMapping){
            //说明未发生过调价，新增调价记录
            mapping.setPriceId(priceScheme.getId());
            mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
            mapping.setPriceNum(1);
            mapping.setUseGasTypeNum(1);
            mapping.setGasMeterCode(gasMeter.getGasCode());
            openAccountVO.setPriceNum(1);
            openAccountVO.setUseGasTypeNum(1);
        }else{
            mapping.setPriceId(priceScheme.getId());
            mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
            int priceNum = priceMapping.getPriceNum()+1;
            if(priceNum>127){priceNum=1;}
            mapping.setPriceNum(priceNum);
            int useGasNum = 0;
            if(!priceMapping.getUseGasTypeId().equals(priceScheme.getUseGasTypeId())){
                useGasNum = priceMapping.getUseGasTypeNum()+1;
                openAccountVO.setUseGasTypeNum(useGasNum);
            }else{
                useGasNum = priceMapping.getUseGasTypeNum();
                openAccountVO.setUseGasTypeNum(useGasNum);
            }
            if(useGasNum>127){useGasNum=1;}
            mapping.setUseGasTypeNum(useGasNum);
            mapping.setGasMeterCode(gasMeter.getGasCode());
            openAccountVO.setPriceNum(priceNum);
        }
        priceMappingBizApi.saveChangePrice(mapping);

        openAccountVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
        openAccountVO.setUseGasTypeId(gasMeter.getUseGasTypeId());
        openAccountVO.setUseGasTypeName(gasMeter.getUseGasTypeName());
        openAccountVO.setCycle(priceScheme.getCycle());
        openAccountVO.setEndTime(priceScheme.getEndTime());
        openAccountVO.setGasMeterFactoryCode(String.valueOf(gasMeter.getGasMeterFactoryId()));
        openAccountVO.setPriceChangeIsClear(priceScheme.getPriceChangeIsClear());
        openAccountVO.setIsClear(priceScheme.getPriceChangeIsClear());
        openAccountVO.setPriceId(priceScheme.getId());
        openAccountVO.setSettlementDay(priceScheme.getSettlementDay());
        openAccountVO.setStartMonth(priceScheme.getStartMonth());
        openAccountVO.setStartTime(priceScheme.getStartTime());
        openAccountVO.setRechargeTimes(1);
        if(useGasType.getAlarmGas()!=null){openAccountVO.setAlarmAmount(useGasType.getAlarmGas().intValue());}
        List<PriceVO> priceList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            PriceVO price = new PriceVO();
            switch (i) {
                case 1:
                    if (PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())) {
                        price.setTieredPrice(priceScheme.getFixedPrice().doubleValue());
                        openAccountVO.setCycle(12);
                        openAccountVO.setStartMonth(1);
                        openAccountVO.setSettlementDay(1);
                        price.setTieredValue(65535);
                    } else {
                        price.setTieredPrice(priceScheme.getPrice1().doubleValue());
                        price.setTieredValue(priceScheme.getGas1().intValue());
                    }
                    price.setTieredNum(1);
                    priceList.add(price);
                    break;
                case 2:
                    if (priceScheme.getGas2() == null) {
                        break;
                    }
                    price.setTieredNum(2);
                    price.setTieredPrice(priceScheme.getPrice2().doubleValue());
                    price.setTieredValue(priceScheme.getGas2().intValue());
                    priceList.add(price);
                    break;
                case 3:
                    if (priceScheme.getGas3() == null) {
                        break;
                    }
                    price.setTieredNum(3);
                    price.setTieredPrice(priceScheme.getPrice3().doubleValue());
                    price.setTieredValue(priceScheme.getGas3().intValue());
                    priceList.add(price);
                    break;
                case 4:
                    if (priceScheme.getGas4() == null) {
                        break;
                    }
                    price.setTieredNum(4);
                    price.setTieredPrice(priceScheme.getPrice4().doubleValue());
                    price.setTieredValue(priceScheme.getGas4().intValue());
                    priceList.add(price);
                    break;
                case 5:
                    if (priceScheme.getGas5() == null) {
                        break;
                    }
                    price.setTieredNum(5);
                    price.setTieredPrice(priceScheme.getPrice5().doubleValue());
                    price.setTieredValue(65535);
                    priceList.add(price);
                    break;
                default:
                    break;
            }
        }
        openAccountVO.setTiered(priceList);
        try {
            businessService.openAccount(openAccountVO);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
    }

    private PriceScheme isPopulationAddPriceNode(PriceScheme price,Integer population,UseGasType useGasType){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth()==1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            Integer cusNum = population;
            if(cusNum>cusBaseNum){
                BigDecimal cus = BigDecimal.valueOf(cusNum-cusBaseNum);
                //每阶梯地增量
                BigDecimal addGas = useGasType.getPopulationIncrease().multiply(cus);
                //设置每个阶梯的地增量
                price.setGas1(price.getGas1().add(addGas));
                if(price.getGas2()!=null){price.setGas2(price.getGas2().add(addGas.multiply(new BigDecimal(2))));}
                if(price.getGas3()!=null){price.setGas3(price.getGas3().add(addGas.multiply(new BigDecimal(3))));}
                if(price.getGas4()!=null){price.setGas4(price.getGas4().add(addGas.multiply(new BigDecimal(4))));}
                if(price.getGas5()!=null){price.setGas5(price.getGas5().add(addGas.multiply(new BigDecimal(5))));}
                if(price.getGas6()!=null){price.setGas6(price.getGas6().add(addGas.multiply(new BigDecimal(6))));}
                log.info("**********************启用人口递增后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }

    /**
     * 查询气价方案
     * @param useGasTypeId
     */
    private PriceScheme getEffectivePriceScheme(Long useGasTypeId) {
        R<UseGasType> useGasTypeR = useGasTypeBizApi.get(useGasTypeId);
        if(useGasTypeR.getIsError()){
            throw new BizException("查询用气类型异常");
        }
        PriceScheme priceScheme = null;
        if(useGasTypeR.getData().getPriceType().equals(PriceType.HEATING_PRICE.getCode())){
            PriceScheme priceSchemeHeating = priceSchemeBizApi.queryPriceHeatingByTime(useGasTypeId, LocalDate.now());
            LocalDate heatingDate = priceSchemeHeating.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                priceScheme = priceSchemeHeating;
            }else{
                log.info("===============抄表数据处于非采暖季，以非采暖方案计算===============");
                priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
            }
        }else {
            priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
        }
        return priceScheme;
    }
}

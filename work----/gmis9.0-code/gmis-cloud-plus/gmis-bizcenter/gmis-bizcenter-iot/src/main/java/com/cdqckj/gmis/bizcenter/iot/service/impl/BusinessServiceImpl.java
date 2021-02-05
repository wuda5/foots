package com.cdqckj.gmis.bizcenter.iot.service.impl;

import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.bizcenter.iot.strategy.factory.MeterTypeFactory;
import com.cdqckj.gmis.bizcenter.iot.utils.MeterCacheUtil;
import com.cdqckj.gmis.bizcenter.iot.vo.AutoAddVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceBatVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.MeterRedisConstant;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.lot.*;
import com.cdqckj.gmis.operateparam.PriceMappingBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotErrorApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotErrorUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotSaveDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BusinessServiceImpl extends SuperCenterServiceImpl implements BusinessService {
    @Autowired
    private BusinessBizApi businessBizApi;
    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;
    @Autowired
    private MeterCacheUtil meterCache;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private DeviceBizApi deviceBizApi;
    @Autowired
    private ReadMeterDataIotApi readMeterDataIotApi;
    @Autowired
    private ReadMeterDataIotErrorApi readMeterDataIotErrorApi;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private PriceMappingBizApi priceMappingBizApi;
    @Autowired
    private IotGasMeterCommandDetailBizApi iotGasMeterCommandDetailBizApi;
    @Autowired
    private IotGasMeterCommandBizApi iotGasMeterCommandBizApi;
    @Autowired
    private IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi;

    @Override
    public IotR valveControl(List<ValveControlVO> valveControlList) {
        IotR iotR = IotR.ok();

        List<String> meterList = new ArrayList<>();
        for(ValveControlVO vo:valveControlList){
            meterList.add(vo.getGasMeterNumber());
        }
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        IotR cacheRes = meterCache.matchMeterCache(meterCacheVO);
        if((Boolean) cacheRes.get(IotRConstant.IS_ERROR)){return cacheRes;}

        for(int i=0;i<valveControlList.size();i++){
            String domainId = meterFactoryCacheConfig.getReceiveDomainId(valveControlList.get(i).getGasMeterNumber());
            if(StringUtil.isNullOrBlank(domainId)){continue;}
            valveControlList.get(i).setDomainId(domainId);
        }
        Map<String, List<ValveControlVO>> groupByDomainId = valveControlList.stream().
                collect(Collectors.groupingBy(ValveControlVO::getDomainId));
        groupByDomainId.forEach((k,v)->{
            //判断厂家下发指令
            MeterStrategy strategy = getMeterStrategy(k);
            strategy.valveControl(v);
        });
        return iotR;
    }

    @Override
    public IotR openAccount(OpenAccountVO openAccountVO) throws ParseException {

        List<String> meterList = new ArrayList<>();
        meterList.add(openAccountVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(openAccountVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        return strategy.openAccount(openAccountVO);
    }

    @Override
    public IotR recharge(RechargeVO rechargeVO) {

        List<String> meterList = new ArrayList<>();
        meterList.add(rechargeVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(rechargeVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        return strategy.recharge(rechargeVO);
    }

    @Override
    public IotR changePrice(PriceVo priceVo) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        meterList.add(priceVo.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(priceVo.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        return strategy.changePrice(priceVo);
    }

    @Override
    public IotR changebatprice(PriceBatVo priceBatVo) {
        //查询物联网表表端计费版号
        GasMeterVersion versionParams = new GasMeterVersion();
        versionParams.setOrderSourceName(OrderSourceNameEnum.REMOTE_RECHARGE.getCode());
        versionParams.setGasMeterVersionName("NB20E1");
        versionParams.setDeleteStatus(0);
        GasMeterVersion version = gasMeterVersionBizApi.queryVersion(versionParams).getData();
        if(null==version){throw new BizException("未找到物联网表端计费的版号,不能进行调价");}
        GasMeter params = new GasMeter();
        params.setDataStatus(4);
        params.setUseGasTypeId(priceBatVo.getCurGasTypeId());
        params.setGasMeterVersionId(version.getId());
        List<GasMeter> gasMeterArr = new ArrayList<>();
        List<GasMeter> gasMeters = gasMeterBizApi.query(params).getData();
        if(null!=gasMeters&&gasMeters.size()>0){gasMeterArr.addAll(gasMeters);}
        params.setDataStatus(3);
        List<GasMeter> gasMetersEx = gasMeterBizApi.query(params).getData();
        if(null!=gasMetersEx&&gasMetersEx.size()>0){gasMeterArr.addAll(gasMetersEx);}
        if(gasMeterArr.size()==0){return IotR.ok();}
        //气表缓存
        List<String> meterList = new ArrayList<>();
        for(GasMeter gasMeter:gasMeterArr){
            meterList.add(gasMeter.getGasMeterNumber());
        }
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        List<PriceBatVo> priceBatVoList = new ArrayList<>();
        for(int i=0;i<gasMeterArr.size();i++){
            String domainId = meterFactoryCacheConfig.getReceiveDomainId(gasMeterArr.get(i).getGasMeterNumber());
            if(StringUtil.isNullOrBlank(domainId)){continue;}
            PriceBatVo priceBat = new PriceBatVo();
            priceBat.setCurGasTypeId(priceBatVo.getCurGasTypeId());
            priceBat.setDomainId(domainId);
            priceBat.setGasMeterCode(gasMeterArr.get(i).getGasCode());
            priceBat.setGasMeterNumber(gasMeterArr.get(i).getGasMeterNumber());
            priceBat.setPopulation(gasMeterArr.get(i).getPopulation());
            priceBat.setBasNum(gasMeterArr.get(i).getGasMeterBase());
            priceBatVoList.add(priceBat);
        }
        if(priceBatVoList.size()>0){
            Map<String, List<PriceBatVo>> groupByDomainId = priceBatVoList.stream().
                    collect(Collectors.groupingBy(PriceBatVo::getDomainId));
            groupByDomainId.forEach((k,v)->{
                //判断厂家下发指令
                MeterStrategy strategy = getMeterStrategy(k);
                strategy.changeBatPrice(v);
            });
        }
        return IotR.ok();
    }

    @Override
    public IotR readiotmeter(List<ParamsVO> paramsVOList) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        for(ParamsVO paramsVO:paramsVOList){
            meterList.add(paramsVO.getGasMeterNumber());
        }
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        deviceBizApi.meterCache(meterCacheVO);

        for(int i=0;i<paramsVOList.size();i++){
            String domainId = meterFactoryCacheConfig.getReceiveDomainId(paramsVOList.get(i).getGasMeterNumber());
            if(StringUtil.isNullOrBlank(domainId)){continue;}
            paramsVOList.get(i).setDomainId(domainId);
            paramsVOList.get(i).setDays(5);
            paramsVOList.get(i).setStartTime(LocalDateTime.now().minusDays(5));
        }
        Map<String, List<ParamsVO>> groupByDomianId = paramsVOList.stream().
                collect(Collectors.groupingBy(ParamsVO::getDomainId));
        groupByDomianId.forEach((k,v)->{
            //判断厂家下发指令
            businessBizApi.readiotmeter(k,v);
        });
        List<ReadMeterDataIotError> errorList = readMeterDataIotErrorApi.queryByMeterNo(meterList);
        if(errorList!=null&&errorList.size()>0){
            for(int i=0;i<errorList.size();i++){
                errorList.get(i).setDataStatus(2);
            }
            readMeterDataIotErrorApi.updateBathMeter(errorList);
        }
        return IotR.ok();
    }

    @Override
    public IotR autoAddIotMeterData(List<AutoAddVo> autoAddVoList) {
        Map<String,String> map = new HashMap<>();
        String msg = "";
        for(AutoAddVo addVo:autoAddVoList){
            ReadMeterDataIot meterDataIot = readMeterDataIotApi.getDataByMeterNo(addVo.getGasMeterNumber()).getData();
            ReadMeterDataIotError meterDataIotError = readMeterDataIotErrorApi.queryByGasMeterNumber(addVo
                    .getGasMeterNumber()).getData();
            if(meterDataIot!=null){
                LocalDate now = LocalDate.now();
                LocalDate old = meterDataIot.getDataTime().toLocalDate();
                long days = now.toEpochDay() - old.toEpochDay();
                if(days<=2){
                    ReadMeterDataIot model = new ReadMeterDataIot();
                    model.setCustomerCode(meterDataIot.getCustomerCode());
                    model.setCustomerName(meterDataIot.getCustomerName());
                    model.setUseGasTypeId(meterDataIot.getUseGasTypeId());
                    model.setUseGasTypeName(meterDataIot.getUseGasTypeName());
                    model.setLastTotalGas(meterDataIot.getLastTotalGas());
                    model.setCurrentTotalGas(meterDataIot.getCurrentTotalGas());
                    model.setMonthUseGas(meterDataIot.getMonthUseGas());
                    model.setGasMeterNumber(meterDataIot.getGasMeterNumber());
                    model.setGasMeterAddress(meterDataIot.getGasMeterAddress());
                    model.setDataStatus(1);
                    model.setProcessStatus(ProcessIotEnum.TO_BE_REVIEWED);
                    model.setDataTime(LocalDateTime.now());
                    model.setReadTime(Date.from( LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
                    ReadMeterDataIotSaveDTO saveDTO = BeanPlusUtil.toBean(model,ReadMeterDataIotSaveDTO.class);
                    readMeterDataIotApi.save(saveDTO);
                    meterDataIotError.setDataStatus(1);
                    meterDataIotError.setDataTime(LocalDateTime.now());
                    ReadMeterDataIotErrorUpdateDTO updateErrorDTO = BeanPlusUtil.toBean(meterDataIotError,
                            ReadMeterDataIotErrorUpdateDTO.class);
                    readMeterDataIotErrorApi.update(updateErrorDTO);
                }else{
                    msg = msg + meterDataIot.getGasMeterNumber()+",";

                }
            }
        }
        if(StringUtil.isNullOrBlank(msg)){
            return IotR.ok("填充成功");
        }else{
            msg = msg+"  当前时间离上次抄表时间超过2天，无法进行自动填充";
            return IotR.error(-1,msg);
        }
    }

    @Override
    public IotR clearMeter(ClearVO clearVO) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        meterList.add(clearVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(clearVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        return strategy.clearMeter(clearVO);
    }

    @Override
    public IotR updatebalance(UpdateBalanceVO updateBalanceVO) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        meterList.add(updateBalanceVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(updateBalanceVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        return strategy.updatebalance(updateBalanceVO);
    }

    @Override
    public IotR clearAndRegisterMeter(ClearVO clearVO, RegisterDeviceVO registerDeviceVO) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        meterList.add(clearVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(clearVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        OrderListVO orderListVO = new OrderListVO();
        orderListVO.setClearVO(clearVO);
        orderListVO.setRegisterDeviceVO(registerDeviceVO);
        return strategy.clearAndRegisterMeter(domainId, orderListVO);
    }

    /**
     * 判断缓存
     * @param domainId
     * @return
     */
    private MeterStrategy getMeterStrategy(String domainId){
        if(domainId==null){throw new BizException("未找到厂家标识");}
        if(!redisService.hasKey(MeterRedisConstant.IOT_GMIS+ StrPool.COLON+ BaseContextHandler.getTenant() +StrPool.COLON
                +MeterRedisConstant.FACTORY_CODE+StrPool.COLON+domainId)){throw new BizException("厂家不存在");}
        String factoryCode = (String) redisService.get(MeterRedisConstant.IOT_GMIS+StrPool.COLON+ BaseContextHandler.getTenant() +StrPool.COLON
                +MeterRedisConstant.FACTORY_CODE+StrPool.COLON+domainId);
        MeterStrategy strategy = MeterTypeFactory.getMeterStrategy(factoryCode,meterFactoryCacheConfig,deviceBizApi,
                businessBizApi,gasMeterVersionBizApi,gasMeterModelBizApi,useGasTypeBizApi,priceSchemeBizApi,gasMeterInfoBizApi,
                customerGasMeterRelatedBizApi,gasMeterBizApi,priceMappingBizApi,iotGasMeterCommandDetailBizApi,iotGasMeterCommandBizApi,iotGasMeterUploadDataBizApi);
        if(strategy==null){log.error("未找到对应的厂家");throw new BizException("厂家不存在");}
        return strategy;
    }
}

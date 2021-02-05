package com.cdqckj.gmis.bizcenter.iot.service.impl;

import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.bizcenter.iot.strategy.factory.MeterTypeFactory;
import com.cdqckj.gmis.bizcenter.iot.utils.MeterCacheUtil;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.MeterRedisConstant;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.bizcenter.iot.service.DeviceService;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.lot.*;
import com.cdqckj.gmis.operateparam.PriceMappingBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceServiceImpl extends SuperCenterServiceImpl implements DeviceService {

    @Autowired
    private DeviceBizApi deviceBizApi;

    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;

    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;

    @Autowired
    private MeterCacheUtil meterCache;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BusinessBizApi businessBizApi;

    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
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
    public IotR addDevice(List<GasMeter> gasMeterList) {
        IotR iotR = IotR.ok();
        List<String> meterList = new ArrayList<>();
        List<GasMeter> addList = new ArrayList<>();
        for(GasMeter vo:gasMeterList){
            GasMeterVersion data = gasMeterVersionBizApi.get(vo.getGasMeterVersionId()).getData();
            if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(data.getOrderSourceName().trim())||
               OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(data.getOrderSourceName())||
               OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(data.getOrderSourceName())){
                meterList.add(vo.getGasMeterNumber());
                addList.add(vo);
            }
        }
        if(meterList.size()==0){return iotR;}
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCacheVO.setGasMeterFactoryId(addList.get(0).getGasMeterFactoryId());
        meterCache.matchMeterCache(meterCacheVO);

        for(int i=0;i<addList.size();i++){
            String domainId = meterFactoryCacheConfig.getReceiveDomainId(addList.get(i).getGasMeterNumber());
            if(StringUtil.isNullOrBlank(domainId)){continue;}
            addList.get(i).setDomainId(domainId);
        }
        Map<String, List<GasMeter>> groupByDomainId = addList.stream().
                collect(Collectors.groupingBy(GasMeter::getDomainId));
        groupByDomainId.forEach((k,v)->{
            //判断厂家下发指令
            MeterStrategy strategy = getMeterStrategy(k);
            strategy.addDevice(v);
        });
        return iotR;
    }

    @Override
    public IotR registerDevice(RegisterDeviceVO registerDeviceVO) {
        List<String> meterList = new ArrayList<>();
        meterList.add(registerDeviceVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(registerDeviceVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        strategy.registerDevice(registerDeviceVO);
        return IotR.ok();
    }

    @Override
    public IotR removeDevice(RemoveDeviceVO removeDeviceVO) {
        List<String> meterList = new ArrayList<>();
        meterList.add(removeDeviceVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(removeDeviceVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        strategy.removeDevice(removeDeviceVO);
        return IotR.ok();
    }

    @Override
    public IotR updateDevice(UpdateDeviceVO updateDeviceVO) {
        List<String> meterList = new ArrayList<>();
        meterList.add(updateDeviceVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        meterCache.matchMeterCache(meterCacheVO);

        String domainId = meterFactoryCacheConfig.getReceiveDomainId(updateDeviceVO.getGasMeterNumber());
        MeterStrategy strategy = getMeterStrategy(domainId);
        strategy.updateDevice(updateDeviceVO);
        return IotR.ok();
    }

    /**
     * 判断缓存-->让参数设置方便复用
     * @param domainId
     * @return
     */
    @Override
    public MeterStrategy getMeterStrategy(String domainId){
        if(domainId==null){throw new BizException("未找到厂家标识");}
        if(!redisService.hasKey(MeterRedisConstant.IOT_GMIS+ StrPool.COLON+ BaseContextHandler.getTenant() +StrPool.COLON
                +MeterRedisConstant.FACTORY_CODE+StrPool.COLON+domainId)){throw new BizException("厂家不存在");}
        String factoryCode = (String) redisService.get(MeterRedisConstant.IOT_GMIS+StrPool.COLON+ BaseContextHandler.getTenant() +StrPool.COLON
                +MeterRedisConstant.FACTORY_CODE+StrPool.COLON+domainId);
        MeterStrategy strategy = MeterTypeFactory.getMeterStrategy(factoryCode,meterFactoryCacheConfig,deviceBizApi,
                businessBizApi,gasMeterVersionBizApi,gasMeterModelBizApi,useGasTypeBizApi,priceSchemeBizApi,
                gasMeterInfoBizApi,customerGasMeterRelatedBizApi,gasMeterBizApi,priceMappingBizApi,iotGasMeterCommandDetailBizApi,iotGasMeterCommandBizApi,iotGasMeterUploadDataBizApi);
        if(strategy==null){log.error("未找到对应的厂家");throw new BizException("厂家不存在");}
        return strategy;
    }
}

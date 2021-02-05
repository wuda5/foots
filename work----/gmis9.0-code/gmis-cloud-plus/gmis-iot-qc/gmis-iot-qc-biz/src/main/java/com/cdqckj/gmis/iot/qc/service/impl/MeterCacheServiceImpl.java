package com.cdqckj.gmis.iot.qc.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.MeterRedisConstant;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import com.cdqckj.gmis.iot.qc.service.MeterCacheService;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@DS("#thread.tenant")
public class MeterCacheServiceImpl implements MeterCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;

    @Autowired
    private IotSubscribeService iotSubscribeService;

    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

    @Override
    public IotR matchMeterCache(MeterCacheVO meterCacheVO) {
        String tenant = BaseContextHandler.getTenant();
        List<String> meterNoList = new ArrayList<>();
        List<String> meterList = meterCacheVO.getMeterList();
        Long gasMeterFactoryId = meterCacheVO.getGasMeterFactoryId();
        for(String meter:meterList){
            if(!redisService.hasKey(MeterRedisConstant.IOT_METER+ StrPool.COLON+tenant+StrPool.COLON+meter)){
                meterNoList.add(meter);
            }
        }
        if(meterNoList.size()>0){
            List<GasMeter> list = gasMeterBizApi.queryMeterList(meterNoList).getData();
            if(list!=null&&list.size()>0){
                for(GasMeter gasMeter:list){
                    GasMeterVersion gasMeterVersion = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
                    if(gasMeter.getGasMeterFactoryId()!=null&&(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName().trim())||
                            OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())||
                            OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersion.getOrderSourceName()))){
                        GasMeterFactory gasMeterFactory = gasMeterFactoryBizApi.
                                get(gasMeter.getGasMeterFactoryId()).getData();
                        if(gasMeterFactory!=null){
                            IotSubscribe iotSubscribe = iotSubscribeService.
                                    queryByFactoryCode(gasMeterFactory.getGasMeterFactoryCode());
                            redisService.set(MeterRedisConstant.IOT_METER+StrPool.COLON+tenant+StrPool
                                            .COLON+gasMeter.getGasMeterNumber()
                                    ,iotSubscribe.getDomainId());
                        }
                    }
                }
            }
            if(gasMeterFactoryId!=null){
                for(String meter:meterList){
                    GasMeterFactory gasMeterFactory = gasMeterFactoryBizApi.
                            get(gasMeterFactoryId).getData();
                    if(gasMeterFactory!=null){
                        IotSubscribe iotSubscribe = iotSubscribeService.
                                queryByFactoryCode(gasMeterFactory.getGasMeterFactoryCode());
                        redisService.set(MeterRedisConstant.IOT_METER+StrPool.COLON+tenant+StrPool
                                        .COLON+meter
                                ,iotSubscribe.getDomainId());
                    }
                }
            }
        }
        return IotR.ok();
    }
}

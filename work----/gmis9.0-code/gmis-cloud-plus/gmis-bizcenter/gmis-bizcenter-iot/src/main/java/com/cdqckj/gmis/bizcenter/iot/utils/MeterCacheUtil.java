package com.cdqckj.gmis.bizcenter.iot.utils;

import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.constant.MeterRedisConstant;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.lot.IotSubscribeBizApi;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MeterCacheUtil {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @Autowired
    private GasMeterFactoryBizApi gasMeterFactoryBizApi;

    @Autowired
    private IotSubscribeBizApi iotSubscribeBizApi;

    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;

    public IotR matchMeterCache(MeterCacheVO meterCacheVO) {
        String errorMsg = "未配置物联网厂家，请先配置";
        String tenant = BaseContextHandler.getTenant();
        List<String> meterNoList = new ArrayList<>();
        List<String> meterList = meterCacheVO.getMeterList();
        Long gasMeterFactoryId = meterCacheVO.getGasMeterFactoryId();
        IotR iotR = IotR.ok();
        for(String meter:meterList){
            if(!redisService.hasKey(MeterRedisConstant.IOT_METER+ StrPool.COLON+tenant+StrPool.COLON+meter)){
                meterNoList.add(meter);
            }
        }
        if(meterNoList.size()==0){return IotR.ok();}
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
                        IotSubscribe iotSubscribe = iotSubscribeBizApi.
                                queryByFactoryCode(gasMeterFactory.getGasMeterFactoryCode());
                        if(iotSubscribe==null){ log.error(gasMeter.getGasMeterNumber()+":"+errorMsg);throw new BizException(gasMeter.getGasMeterNumber()+":"+errorMsg);}
                        meterRedisCache(tenant,gasMeter.getGasMeterNumber(),iotSubscribe);
                    }
                }
            }
        }
        if(gasMeterFactoryId!=null){
            for(String meter:meterList){
                GasMeterFactory gasMeterFactory = gasMeterFactoryBizApi.
                        get(gasMeterFactoryId).getData();
                if(gasMeterFactory!=null){
                    IotSubscribe iotSubscribe = iotSubscribeBizApi.
                            queryByFactoryCode(gasMeterFactory.getGasMeterFactoryCode());
                    if(iotSubscribe==null){ log.error(meter+":"+errorMsg);throw new BizException(meter+":"+errorMsg);}
                    meterRedisCache(tenant,meter,iotSubscribe);
                }
            }
        }
        return iotR;
    }

    /**
     * 物联网表厂家缓存
     * @param tenant 租户caode
     * @param meterNo 表身号
     * @param iotSubscribe 物联网厂家实体
     */
    private void meterRedisCache(String tenant,String meterNo,IotSubscribe iotSubscribe ){
        //domainId缓存
        redisService.set(MeterRedisConstant.IOT_METER+StrPool.COLON+tenant+StrPool.COLON+meterNo,iotSubscribe.getDomainId());
        //domainId和租户缓存
        redisService.set(MeterRedisConstant.IOT_GMIS+StrPool.COLON+
                MeterRedisConstant.DOMAIN_ID+StrPool.COLON+iotSubscribe.getDomainId(),iotSubscribe.getDomainId());
        //licence缓存
        redisService.set(MeterRedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                MeterRedisConstant.LICENCE+StrPool.COLON+iotSubscribe.getDomainId(),iotSubscribe.getLicence());
        //厂家信息缓存
        redisService.set(MeterRedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON+
                MeterRedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+iotSubscribe.getDomainId(),iotSubscribe);
        //厂家code缓存
        redisService.set(MeterRedisConstant.IOT_GMIS+StrPool.COLON+tenant+StrPool.COLON
                +MeterRedisConstant.FACTORY_CODE+StrPool.COLON+iotSubscribe.getDomainId(),iotSubscribe.getFactoryCode());
    }
}

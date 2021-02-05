package com.cdqckj.gmis.bizcenter.iot.strategy.factory;

import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.bizcenter.iot.strategy.impl.QC3MeterStrategy;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.lot.*;
import com.cdqckj.gmis.operateparam.PriceMappingBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;

public class MeterTypeFactory {
    public static MeterStrategy getMeterStrategy(String factoryCode, MeterFactoryCacheConfig meterFactoryCacheConfig,
                                                 DeviceBizApi deviceBizApi, BusinessBizApi businessBizApi,
                                                 GasMeterVersionBizApi gasMeterVersionBizApi, GasMeterModelBizApi gasMeterModelBizApi,
                                                 UseGasTypeBizApi useGasTypeBizApi, PriceSchemeBizApi priceSchemeBizApi,
                                                 GasMeterInfoBizApi gasMeterInfoBizApi, CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi,
                                                 GasMeterBizApi gasMeterBizApi, PriceMappingBizApi priceMappingBizApi,
                                                 IotGasMeterCommandDetailBizApi iotGasMeterCommandDetailBizApi,
                                                 IotGasMeterCommandBizApi iotGasMeterCommandBizApi,IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi){
         MeterStrategy meterStrategy = null;
         switch (factoryCode){
             case "01":
                 meterStrategy = new QC3MeterStrategy(meterFactoryCacheConfig,deviceBizApi,
                         businessBizApi,gasMeterVersionBizApi,gasMeterModelBizApi,useGasTypeBizApi,
                         priceSchemeBizApi,gasMeterInfoBizApi,customerGasMeterRelatedBizApi,gasMeterBizApi,priceMappingBizApi,
                         iotGasMeterCommandDetailBizApi,iotGasMeterCommandBizApi,iotGasMeterUploadDataBizApi);
                 break;
             default:
                 break;
         }
        return meterStrategy;
    }
}

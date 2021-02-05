package com.cdqckj.gmis.bizcenter.temp.counter.component;


import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.ClearVO;
import com.cdqckj.gmis.iot.qc.vo.RegisterDeviceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * 表具清零 相关接口
 *
 * @author gmis
 */
@Slf4j
@Component
public class ClearMeterComponent {

    @Autowired
    private IotOpenAccountComponent iotOpenAccountComponent;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterModelBizApi gasMeterModelBizApi;


    /**
     * 表具清零
     *
     * @param record
     */
    public void clearMeter(ChangeMeterRecord record) {
        if (isIotMeter(record.getOldMeterType())) {
            String meterCode = record.getOldMeterCode();
            String customerCode = record.getCustomerCode();
            GasMeter gasMeter = gasMeterBizApi.findGasMeterByCode(meterCode).getData();
            GasMeterInfo meterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(meterCode, customerCode).getData();
            GasMeterInfoUpdateDTO updateDTO = GasMeterInfoUpdateDTO.builder()
                    .id(meterInfo.getId())
                    .dataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .build();
            gasMeterInfoBizApi.update(updateDTO);

            GasMeterInfoSaveDTO newMeterInfo = GasMeterInfoSaveDTO.builder()
                    .gasmeterCode(meterInfo.getGasmeterCode())
                    .customerCode(meterInfo.getCustomerCode())
                    .customerName(meterInfo.getCustomerName())
                    .cycleUseGas(BigDecimal.ZERO)
                    .cycleChargeGas(BigDecimal.ZERO)
                    .priceSchemeId(meterInfo.getPriceSchemeId())
                    .dataStatus(DataStatusEnum.NORMAL.getValue())
                    .initialMeasurementBase(meterInfo.getInitialMeasurementBase())
                    .totalRechargeMeterCount(1)
                    .totalUseGas(meterInfo.getTotalUseGas())
                    .totalUseGasMoney(meterInfo.getTotalUseGasMoney())
                    .gasMeterBalance(BigDecimal.ZERO)
                    .gasMeterInBalance(BigDecimal.ZERO)
                    .build();
            //新建使用数据表
            gasMeterInfoBizApi.save(newMeterInfo);

            //移除设备
            log.info("下发清零指令。表具编号：" + meterCode);
            ClearVO clearVO = new ClearVO();
            clearVO.setGasMeterNumber(gasMeter.getGasMeterNumber());

            log.info("注册设备。表具编号：" + meterCode);
            RegisterDeviceVO registerDeviceVO = new RegisterDeviceVO();
            registerDeviceVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
            registerDeviceVO.setDeviceType(0);
            registerDeviceVO.setBaseNumber(gasMeter.getGasMeterBase());
            registerDeviceVO.setCustomerCode(gasMeter.getGasMeterNumber());
            registerDeviceVO.setCustomerName(record.getCustomerName());
            registerDeviceVO.setLatitude(gasMeter.getLatitude());
            registerDeviceVO.setLongitude(gasMeter.getLongitude());

            GasMeterVersion version = gasMeterVersionBizApi.get(record.getNewMeterVersionId()).getData();
            GasMeterModel data = gasMeterModelBizApi.get(record.getNewMeterModelId()).getData();

            registerDeviceVO.setSpecification(data.getSpecification());
            registerDeviceVO.setCustomerType(0);
            registerDeviceVO.setVersionNo(version.getGasMeterVersionName());

            IotR iotR = businessService.clearAndRegisterMeter(clearVO, registerDeviceVO);
            if(iotR.getIsSuccess()){
                //开户
                log.info("清零后-下发开户指令开始;");
                iotOpenAccountComponent.iotOpenAccount(gasMeter);
                log.info("清零后-下发开户指令结束");
                log.info("清零后-下发更新指令开始;");
                iotOpenAccountComponent.updateIotDevice(gasMeter, customerCode);
                log.info("清零后-下发更新指令结束;");
            }
        }
    }


    /**
     * 是否是物理联网表
     *
     * @param meterType 表具类型
     * @return 是:true;否：false
     */
    private boolean isIotMeter(String meterType) {
        return OrderSourceNameEnum.CENTER_RECHARGE.eq(meterType) || OrderSourceNameEnum.REMOTE_RECHARGE.eq(meterType)
                || OrderSourceNameEnum.REMOTE_READMETER.eq(meterType);
    }
}

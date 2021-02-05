package com.cdqckj.gmis.bizcenter.iot.strategy.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.bizcenter.iot.strategy.MeterStrategy;
import com.cdqckj.gmis.common.constant.GMISIotConstant;
import com.cdqckj.gmis.bizcenter.iot.vo.CommandIotVO;
import com.cdqckj.gmis.bizcenter.iot.vo.GMISIotGfConstant;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceBatVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.dto.*;
import com.cdqckj.gmis.iot.qc.entity.*;
import com.cdqckj.gmis.iot.qc.enumeration.CommandState;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import com.cdqckj.gmis.iot.qc.enumeration.ExecuteState;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.lot.*;
import com.cdqckj.gmis.operateparam.PriceMappingBizApi;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QC3MeterStrategy implements MeterStrategy {

    private MeterFactoryCacheConfig meterFactoryCacheConfig;

    private DeviceBizApi deviceBizApi;

    private BusinessBizApi businessBizApi;

    private GasMeterVersionBizApi gasMeterVersionBizApi;

    private GasMeterModelBizApi gasMeterModelBizApi;

    private UseGasTypeBizApi useGasTypeBizApi;

    private PriceSchemeBizApi priceSchemeBizApi;

    private GasMeterInfoBizApi gasMeterInfoBizApi;

    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    private GasMeterBizApi gasMeterBizApi;

    private PriceMappingBizApi priceMappingBizApi;

    private IotGasMeterCommandDetailBizApi iotGasMeterCommandDetailBizApi;

    private IotGasMeterCommandBizApi iotGasMeterCommandBizApi;

    private IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi;

    public QC3MeterStrategy(MeterFactoryCacheConfig meterFactoryCacheConfig,
                            DeviceBizApi deviceBizApi, BusinessBizApi businessBizApi,
                            GasMeterVersionBizApi gasMeterVersionBizApi, GasMeterModelBizApi gasMeterModelBizApi,
                            UseGasTypeBizApi useGasTypeBizApi, PriceSchemeBizApi priceSchemeBizApi,
                            GasMeterInfoBizApi gasMeterInfoBizApi,CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi,
    GasMeterBizApi gasMeterBizApi,PriceMappingBizApi priceMappingBizApi,IotGasMeterCommandDetailBizApi iotGasMeterCommandDetailBizApi,
    IotGasMeterCommandBizApi iotGasMeterCommandBizApi,IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi) {
        this.meterFactoryCacheConfig = meterFactoryCacheConfig;
        this.deviceBizApi = deviceBizApi;
        this.businessBizApi = businessBizApi;
        this.gasMeterVersionBizApi = gasMeterVersionBizApi;
        this.gasMeterModelBizApi = gasMeterModelBizApi;
        this.useGasTypeBizApi = useGasTypeBizApi;
        this.priceSchemeBizApi = priceSchemeBizApi;
        this.gasMeterInfoBizApi = gasMeterInfoBizApi;
        this.customerGasMeterRelatedBizApi = customerGasMeterRelatedBizApi;
        this.gasMeterBizApi = gasMeterBizApi;
        this.priceMappingBizApi = priceMappingBizApi;
        this.iotGasMeterCommandBizApi = iotGasMeterCommandBizApi;
        this.iotGasMeterCommandDetailBizApi = iotGasMeterCommandDetailBizApi;
        this.iotGasMeterUploadDataBizApi = iotGasMeterUploadDataBizApi;
    }


    @Override
    public IotR setDeviceParamsQ(List<IotDeviceParams> IotDeviceParamsList) {
        String domainId = null;
        if (CollectionUtil.isNotEmpty(IotDeviceParamsList)){
            domainId = meterFactoryCacheConfig.getReceiveDomainId(IotDeviceParamsList.get(0).getGasMeterNumber());
            businessBizApi.setDeviceParams(domainId,IotDeviceParamsList);
        }
        return IotR.ok();
    }

    @Override
    public IotR addDevice(List<GasMeter> gasMeterList) {
        List<DeviceDataVO> deviceDataVOList = new ArrayList<>();
        String domainId = null;
        for (int i =0;i<gasMeterList.size();i++) {
            GasMeter gasMeter = gasMeterList.get(i);
            GasMeterVersion data = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
            //暂时针对秦川物联网表注册
            if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(data.getOrderSourceName().trim())||
                    OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(data.getOrderSourceName())||
                    OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(data.getOrderSourceName())){
                if(domainId==null){domainId = meterFactoryCacheConfig.getReceiveDomainId(gasMeter.getGasMeterNumber());}
                DeviceDataVO dataVO = new DeviceDataVO();
                dataVO.setDomainId(domainId);
                dataVO.setDeviceType(0);
                dataVO.setGasMeterCode(gasMeter.getGasCode());
                dataVO.setGasMeterNumber(gasMeter.getGasMeterNumber());
                dataVO.setGasMeterFactoryId(gasMeter.getGasMeterFactoryId());

                dataVO.setGasMeterVersionId(gasMeter.getGasMeterVersionId());
                dataVO.setGasMeterVersionName(data.getGasMeterVersionName());
                dataVO.setInitialMeasurementBase(gasMeter.getGasMeterBase());

                dataVO.setDirection(gasMeter.getDirection());
                dataVO.setOrgId(BaseContextHandler.getOrgId());
                dataVO.setOrgName(BaseContextHandler.getOrgName());
                dataVO.setGasMeterModelId(gasMeter.getGasMeterModelId());
                if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(data.getOrderSourceName())){
                    dataVO.setControlMode("READMETE");
                }else if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(data.getOrderSourceName())){
                    dataVO.setControlMode("SERVICECTRL");
                }else if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(data.getOrderSourceName())){
                    dataVO.setControlMode("METERCTRL");
                }else{
                    dataVO.setControlMode("SERVICECTRL");
                }
                GasMeterModel gasMeterModel = gasMeterModelBizApi.get(gasMeter.getGasMeterModelId()).getData();
                dataVO.setSpecification(gasMeterModel==null?"1.6":gasMeterModel.getSpecification());
                deviceDataVOList.add(dataVO);
            }
        }
        if(deviceDataVOList.size()>0){
            DeviceModel deviceModel = new DeviceModel();
            for(DeviceDataVO dataVO:deviceDataVOList){
                List<DeviceDetailModel> list = new ArrayList<>();
                if(domainId==null){domainId=dataVO.getDomainId();}
                deviceModel.setDomainId(dataVO.getDomainId());
                if(dataVO.getDeviceType()==0){deviceModel.setDeviceType(GMISIotConstant.GMIS_GAS_METER_TYPE);}
                else if(dataVO.getDeviceType()==1){deviceModel.setDeviceType(GMISIotConstant.GMIS_LLJ_METER_TYPE);}
                else if(dataVO.getDeviceType()==2){deviceModel.setDeviceType(GMISIotConstant.GMIS_WM_METER_TYPE);}
                DeviceDetailModel detailModel = new DeviceDetailModel();
                detailModel.setDeviceId(dataVO.getGasMeterNumber());
                list.add(detailModel);
                deviceModel.setData(list);
                String sendData = JSONObject.toJSONString(deviceModel);
                CommandIotVO commandIotVO = defendCommand(dataVO.getGasMeterNumber(), dataVO.getGasMeterCode(),null,BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                        CommandType.ADDDOMAIN, sendData,null,ExecuteState.Success.getCodeValue(), null);
                dataVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
                dataVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
            }
            try{
                deviceBizApi.addDevice(domainId,deviceDataVOList);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return IotR.ok();
    }

    @Override
    public IotR valveControl(List<ValveControlVO> valveControlList) {
        IotR iot = IotR.ok();
        if(valveControlList.size()>0){
            valveControlList.stream().forEach(e->{
                String domainId = meterFactoryCacheConfig.getReceiveDomainId(e.getGasMeterNumber());
                IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(e.getGasMeterNumber());
                ValveControlModel valveModel = new ValveControlModel();
                valveModel.setArchiveId(model.getArchiveId());
                valveModel.setDomainId(model.getDomainId());
                valveModel.setBusinessType(CommandType.VALVECONTROL.getCode());
                ValveControlBus bus = new ValveControlBus();
                bus.setControlCode(e.getControlCode());
                valveModel.setBusinessData(bus);
                String sendData = JSONObject.toJSONString(valveModel);
                if(GMISIotGfConstant.DEVICE_VALVE_CLOSE_CODE.equals(e.getControlCode())
                        ||(GMISIotGfConstant.DEVICE_VALVE_PRIVATE_CLOSE_CODE.equals(e.getControlCode()))){
                    CommandIotVO commandIotVO = defendCommand(e.getGasMeterNumber(), model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                            CommandType.CLOSEVALVE, sendData,null,ExecuteState.Success.getCodeValue(), null);
                    e.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
                    e.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
                }else if(GMISIotGfConstant.DEVICE_VALVE_CLOSE_OPEN.equals(e.getControlCode())){
                    CommandIotVO commandIotVO = defendCommand(e.getGasMeterNumber(), model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                            CommandType.OPENVALVE, sendData,null,ExecuteState.Success.getCodeValue(), null);
                    e.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
                    e.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
                }
                try{
                    businessBizApi.valueControl(domainId,e);
                }catch (Exception ex){
                    log.error(ex.getMessage());
                }
            });
        }
        return IotR.ok();
    }

    @Override
    public IotR registerDevice(RegisterDeviceVO registerDeviceVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(registerDeviceVO.getGasMeterNumber());
        return deviceBizApi.registerDevice(domainId,registerDeviceVO);
    }

    @Override
    public IotR removeDevice(RemoveDeviceVO removeDeviceVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(removeDeviceVO.getGasMeterNumber());
        GasMeter gasMeter = gasMeterBizApi.findEffectiveMeterByNumber(removeDeviceVO.getGasMeterNumber()).getData();
        if(null!=gasMeter){
            gasMeter.setDataStatus(5);
            GasMeterUpdateDTO updateDTO = BeanPlusUtil.toBean(gasMeter,GasMeterUpdateDTO.class);
            gasMeterBizApi.update(updateDTO);
        }
        return deviceBizApi.removeDevice(domainId,removeDeviceVO);
    }

    @Override
    public IotR updateDevice(UpdateDeviceVO updateDeviceVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(updateDeviceVO.getGasMeterNumber());
        return deviceBizApi.updateDevice(domainId,updateDeviceVO);
    }

    @Override
    public IotR openAccount(OpenAccountVO openAccountVO) throws ParseException {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(openAccountVO.getGasMeterNumber());
        IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(openAccountVO.getGasMeterNumber());
        GasMeterVersion gasMeterVersion = gasMeterVersionBizApi.get(model.getGasMeterVersionId()).getData();
        OpenAccountModel accountModel = new OpenAccountModel();
        accountModel.setDomainId(openAccountVO.getDomainId());
        accountModel.setArchiveId(model.getArchiveId());
        accountModel.setBusinessType(CommandType.OPENACCOUNT.getCode());
        //是否表端结算
        if(gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.REMOTE_RECHARGE.getCode())){
            BusinessDataModel bus = new BusinessDataModel();
            bus.setRechargeAmount(0.00d);
            if(openAccountVO.getRechargeAmount()!=null){bus.setRechargeAmount(openAccountVO.getRechargeAmount());}
            if(openAccountVO.getAlarmAmount()==null){bus.setAlarmAmount(0);}
            else{bus.setAlarmAmount(openAccountVO.getAlarmAmount());}
            bus.setRechargeTimes(openAccountVO.getRechargeTimes().compareTo(1)==0
                    ?openAccountVO.getRechargeTimes():1);
            PriceModel priceModel = new PriceModel();
            priceModel.setAdjDay(openAccountVO.getSettlementDay());
            priceModel.setAdjMonth(openAccountVO.getStartMonth());
            priceModel.setCycle(openAccountVO.getCycle());
            if(openAccountVO.getEndTime()== null){
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
                priceModel.setEndDate(sdf.parse(GMISIotConstant.GMIS_DEFAULT_DATE).getTime());
            }else{
                priceModel.setEndDate(openAccountVO.getEndTime().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
            }
            priceModel.setStartDate(openAccountVO.getStartTime().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
            priceModel.setGasTypeID(openAccountVO.getUseGasTypeNum());
            priceModel.setPriceNum(openAccountVO.getPriceNum());
            priceModel.setInitAmount(0d);
            if(openAccountVO.getPriceChangeIsClear()==0){priceModel.setIsClear(false);}
            else{priceModel.setIsClear(true);}
            priceModel.setTieredNum(openAccountVO.getTiered().size());
            List<PriceVO> list = openAccountVO.getTiered();
            List<TieredModel> tieredModels = new ArrayList<>();
            list.stream().forEach(e->{
                TieredModel node = new TieredModel();
                node.setTieredNum(e.getTieredNum());
                node.setTieredPrice(e.getTieredPrice());
                node.setTieredValue(e.getTieredValue());
                tieredModels.add(node);
            });
            priceModel.setTiered(tieredModels);
            bus.setPrice(priceModel);
            accountModel.setBusinessData(bus);
        }else{
            BusinessDataModel bus = new BusinessDataModel();
            accountModel.setBusinessData(bus);
        }
        String sendOpenData = JSONUtil.toJsonStr(accountModel);
        CommandIotVO commandIotVO = defendCommand(openAccountVO.getGasMeterNumber(), model.getGasMeterCode(),null,BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                CommandType.OPENACCOUNT, sendOpenData,null,ExecuteState.Success.getCodeValue(), null);
        openAccountVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
        openAccountVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
        return businessBizApi.openAccount(domainId,openAccountVO);
    }

    @Override
    public IotR recharge(RechargeVO rechargeVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(rechargeVO.getGasMeterNumber());
        IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(rechargeVO.getGasMeterNumber());
        //查询户表
        CustomerGasMeterRelated customerParams = new CustomerGasMeterRelated();
        customerParams.setGasMeterCode(model.getGasMeterCode());
        customerParams.setDataStatus(DataStatusEnum.NORMAL.getValue());
        List<CustomerGasMeterRelated> relateds = customerGasMeterRelatedBizApi.query(customerParams).getData();
        GasMeterInfo gasMeterInfo =null;
        if(relateds.size()>0){
            CustomerGasMeterRelated customerGasMeterRelated = relateds.get(0);
            gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                    customerGasMeterRelated.getCustomerCode()).getData();
            if(gasMeterInfo==null){
                throw new BizException("未获取到户表账户");
            }
        }
        RechargeBus rechargeBus = new RechargeBus();
        if(rechargeVO.getAlarmAmount()==null){rechargeBus.setAlarmAmount(0);}
        else{rechargeBus.setAlarmAmount(rechargeVO.getAlarmAmount());}
        rechargeBus.setRechargeAmount(rechargeVO.getRechargeAmount().doubleValue());
        if(gasMeterInfo.getValue1()!=null){rechargeBus.setRechargeAmount1(gasMeterInfo.getValue1().doubleValue());}
        if(gasMeterInfo.getValue2()!=null){rechargeBus.setRechargeAmount2(gasMeterInfo.getValue2().doubleValue());}
        if(gasMeterInfo.getTotalRechargeMeterCount()==null){
            rechargeBus.setRechargeTimes(2);
        }else{
            rechargeBus.setRechargeTimes(gasMeterInfo.getTotalRechargeMeterCount()+1);
        }
        RechargeModel rechargeModel = new RechargeModel();
        rechargeModel.setBusinessData(rechargeBus);
        rechargeModel.setArchiveId(model.getArchiveId());
        rechargeModel.setBusinessType(CommandType.RECHARGE.getCode());
        rechargeModel.setDomainId(model.getDomainId());
        String sendData = JSONUtil.toJsonStr(rechargeModel);
        CommandIotVO commandIotVO = defendCommand(rechargeVO.getGasMeterNumber(), model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                CommandType.RECHARGE, sendData,null,ExecuteState.Success.getCodeValue(), null);
        rechargeVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
        rechargeVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
        return businessBizApi.recharge(domainId,rechargeVO);
    }

    @Override
    public IotR changePrice(PriceVo priceVo) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(priceVo.getGasMeterNumber());
        List<PriceVO> priceList = new ArrayList<>();
        PriceChangeVO priceChangeVO = new PriceChangeVO();
        UseGasType useGasType = null;
        PriceScheme priceSchemeEx = null;
        if(priceVo.getNewGasTypeId()!=null){
            useGasType = useGasTypeBizApi.get(priceVo.getNewGasTypeId()).getData();
            priceSchemeEx = priceSchemeBizApi.queryRecentRecord(priceVo.getNewGasTypeId());
            priceChangeVO.setNewGasTypeNum(useGasType.getUseTypeNum());
            UseGasType useGasTypeOld = useGasTypeBizApi.get(priceVo.getCurGasTypeId()).getData();
            priceChangeVO.setCurGasTypeNum(useGasTypeOld.getUseTypeNum());
        }else{
            useGasType = useGasTypeBizApi.get(priceVo.getCurGasTypeId()).getData();
            priceSchemeEx = priceSchemeBizApi.queryRecentRecord(priceVo.getCurGasTypeId());
            priceChangeVO.setCurGasTypeNum(useGasType.getUseTypeNum());
        }
        if(null==priceSchemeEx){throw new BizException("未找到用气类型或价格方案");}
        GasMeter gasMeter = gasMeterBizApi.findEffectiveMeterByNumber(priceVo.getGasMeterNumber()).getData();
        if(null==gasMeter){throw new BizException("未找到对应的表具档案");}
        //是否多人口
        PriceScheme priceScheme = isPopulationAddPrice(priceSchemeEx,gasMeter,useGasType);
        CustomerGasMeterRelated customerGasMeterRelated = null;
        GasMeterInfo gasMeterInfo = null;
        if(StringUtil.isNullOrBlank(priceVo.getCustomerCode())){
            CustomerGasMeterRelated customerParams = new CustomerGasMeterRelated();
            customerParams.setGasMeterCode(priceVo.getGasMeterCode());
            customerParams.setDataStatus(DataStatusEnum.NORMAL.getValue());
            List<CustomerGasMeterRelated> relateds = customerGasMeterRelatedBizApi.query(customerParams).getData();
            if(relateds!=null&&relateds.size()>0){
                customerGasMeterRelated = relateds.get(0);
            }
            if(null==customerGasMeterRelated){throw new BizException("未找到客户气表关联关系");}
            gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                    customerGasMeterRelated.getCustomerCode()).getData();
        }else{
            gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(priceVo.getGasMeterCode(),priceVo.getCustomerCode()).getData();
        }
        if(null==gasMeterInfo){throw new BizException("未找到气表使用情况");}
        PriceMapping priceMapping = priceMappingBizApi.getGasMeterPriceMapping(gasMeter.getGasCode());
        PriceMapping mapping = new PriceMapping();
        if(null==priceMapping){
            //说明未发生过调价，新增调价记录
            mapping.setPriceId(priceScheme.getId());
            mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
            mapping.setPriceNum(1);
            mapping.setUseGasTypeNum(1);
            mapping.setGasMeterCode(gasMeter.getGasCode());
            priceChangeVO.setPriceNum(1);
            priceChangeVO.setCurGasTypeNum(1);
        }else{
            mapping.setPriceId(priceScheme.getId());
            mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
            int priceNum = priceMapping.getPriceNum()+1;
            if(priceNum>127){priceNum=1;}
            mapping.setPriceNum(priceNum);
            int useGasNum = 0;
            if(!priceMapping.getUseGasTypeId().equals(priceScheme.getUseGasTypeId())){
                useGasNum = priceMapping.getUseGasTypeNum()+1;
                priceChangeVO.setNewGasTypeNum(useGasNum);
                priceChangeVO.setCurGasTypeNum(priceMapping.getUseGasTypeNum());
            }else{
                useGasNum = priceMapping.getUseGasTypeNum();
                priceChangeVO.setCurGasTypeNum(useGasNum);
            }
            if(useGasNum>127){useGasNum=1;}
            mapping.setUseGasTypeNum(useGasNum);
            mapping.setGasMeterCode(gasMeter.getGasCode());
            priceChangeVO.setPriceNum(priceNum);
        }
        priceMappingBizApi.saveChangePrice(mapping);
        priceChangeVO.setDomainId(domainId);
        priceChangeVO.setAdjDay(priceScheme.getSettlementDay());
        priceChangeVO.setAdjMonth(priceScheme.getStartMonth());
        priceChangeVO.setGasMeterNumber(priceVo.getGasMeterNumber());
        priceChangeVO.setCycle(priceScheme.getCycle());
        priceChangeVO.setIsClear(priceScheme.getPriceChangeIsClear());
        priceChangeVO.setStartdate(priceScheme.getStartTime());
        if(gasMeterInfo.getInitialMeasurementBase()!=null){priceChangeVO.setInitAmount(gasMeterInfo.getInitialMeasurementBase());}
        else{priceChangeVO.setInitAmount(BigDecimal.ZERO);}
        for(int i=1;i<=5;i++){
            PriceVO price =new PriceVO();
            switch (i){
                case 1:
                    if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
                        price.setTieredPrice(priceScheme.getFixedPrice().doubleValue());
                        price.setTieredValue(65535);
                        priceChangeVO.setAdjDay(1);
                        priceChangeVO.setAdjMonth(1);
                        priceChangeVO.setCycle(12);
                    }else{
                        price.setTieredPrice(priceScheme.getPrice1().doubleValue());
                        price.setTieredValue(priceScheme.getGas1().intValue());
                    }
                    price.setTieredNum(1);
                    priceList.add(price);
                    break;
                case 2:
                    if(priceScheme.getGas2()==null){break;}
                    price.setTieredNum(2);
                    price.setTieredPrice(priceScheme.getPrice2().doubleValue());
                    price.setTieredValue(priceScheme.getGas2().intValue());
                    priceList.add(price);
                    break;
                case 3:
                    if(priceScheme.getGas3()==null){break;}
                    price.setTieredNum(3);
                    price.setTieredPrice(priceScheme.getPrice3().doubleValue());
                    price.setTieredValue(priceScheme.getGas3().intValue());
                    priceList.add(price);
                    break;
                case 4:
                    if(priceScheme.getGas4()==null){break;}
                    price.setTieredNum(4);
                    price.setTieredPrice(priceScheme.getPrice4().doubleValue());
                    price.setTieredValue(priceScheme.getGas4().intValue());
                    priceList.add(price);
                    break;
                case 5:
                    if(priceScheme.getGas5()==null){break;}
                    price.setTieredNum(5);
                    price.setTieredPrice(priceScheme.getPrice5().doubleValue());
                    price.setTieredValue(65535);
                    priceList.add(price);
                    break;
                default:
                    break;
            }
        }
        priceChangeVO.setTiered(priceList);
        return businessBizApi.changeprice(domainId,priceChangeVO);
    }

    @Override
    public IotR changeBatPrice(List<PriceBatVo> priceBatVos) {
        PriceBatVo priceVo = priceBatVos.get(0);
        String domainId = priceVo.getDomainId();
        List<PriceChangeVO> dataList = new ArrayList<>();
        List<PriceVO> priceList = new ArrayList<>();
        UseGasType useGasType = null;
        PriceScheme priceSchemeEx = null;
        Integer useNewGasNum = -1;
        if(priceVo.getNewGasTypeId()!=null){
            useGasType = useGasTypeBizApi.get(priceVo.getNewGasTypeId()).getData();
            priceSchemeEx = priceSchemeBizApi.queryRecentRecord(priceVo.getNewGasTypeId());
            useNewGasNum = useGasType.getUseTypeNum();
            UseGasType useGasTypeOld = useGasTypeBizApi.get(priceVo.getCurGasTypeId()).getData();
        }else{
            useGasType = useGasTypeBizApi.get(priceVo.getCurGasTypeId()).getData();
            priceSchemeEx = priceSchemeBizApi.queryRecentRecord(priceVo.getCurGasTypeId());
        }
        if(null==priceSchemeEx){throw new BizException("未找到用气类型或价格方案");}
        for(PriceBatVo vo:priceBatVos){
            PriceChangeVO priceChangeVO = new PriceChangeVO();
            if(useNewGasNum!=-1){priceChangeVO.setNewGasTypeNum(useNewGasNum);}
            //是否多人口
            PriceScheme priceScheme = isPopulationAddPriceNode(priceSchemeEx,vo.getPopulation(),useGasType);
            PriceMapping priceMapping = priceMappingBizApi.getGasMeterPriceMapping(vo.getGasMeterCode());
            PriceMapping mapping = new PriceMapping();
            if(null==priceMapping){
                //说明未发生过调价，新增调价记录
                mapping.setPriceId(priceScheme.getId());
                mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
                mapping.setPriceNum(1);
                mapping.setUseGasTypeNum(1);
                mapping.setGasMeterCode(vo.getGasMeterCode());
                priceChangeVO.setPriceNum(1);
                priceChangeVO.setCurGasTypeNum(1);
            }else{
                mapping.setPriceId(priceScheme.getId());
                mapping.setUseGasTypeId(priceScheme.getUseGasTypeId());
                int priceNum = priceMapping.getPriceNum()+1;
                if(priceNum>127){priceNum=1;}
                mapping.setPriceNum(priceNum);
                int useGasNum = 0;
                if(!priceMapping.getUseGasTypeId().equals(priceScheme.getUseGasTypeId())){
                    useGasNum = priceMapping.getUseGasTypeNum()+1;
                    priceChangeVO.setNewGasTypeNum(useGasNum);
                    priceChangeVO.setCurGasTypeNum(priceMapping.getUseGasTypeNum());
                }else{
                    useGasNum = priceMapping.getUseGasTypeNum();
                    priceChangeVO.setCurGasTypeNum(useGasNum);
                }
                if(useGasNum>127){useGasNum=1;}
                mapping.setUseGasTypeNum(useGasNum);
                mapping.setGasMeterCode(vo.getGasMeterCode());
                priceChangeVO.setPriceNum(priceNum);
            }

            priceChangeVO.setDomainId(domainId);
            priceChangeVO.setAdjDay(priceScheme.getSettlementDay());
            priceChangeVO.setAdjMonth(priceScheme.getStartMonth());
            priceChangeVO.setGasMeterNumber(vo.getGasMeterNumber());
            priceChangeVO.setCycle(priceScheme.getCycle());
            priceChangeVO.setIsClear(priceScheme.getPriceChangeIsClear());
            priceChangeVO.setPriceNum(priceScheme.getPriceNum());
            priceChangeVO.setStartdate(priceScheme.getStartTime());
            if(vo.getBasNum()!=null){priceChangeVO.setInitAmount(vo.getBasNum());}
            else{priceChangeVO.setInitAmount(BigDecimal.ZERO);}
            for(int i=1;i<=5;i++){
                PriceVO price =new PriceVO();
                switch (i){
                    case 1:
                        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
                            price.setTieredPrice(priceScheme.getFixedPrice().doubleValue());
                            price.setTieredValue(65535);
                            priceChangeVO.setAdjDay(1);
                            priceChangeVO.setAdjMonth(1);
                            priceChangeVO.setCycle(12);
                        }else{
                            price.setTieredPrice(priceScheme.getPrice1().doubleValue());
                            price.setTieredValue(priceScheme.getGas1().intValue());
                        }
                        price.setTieredNum(1);
                        priceList.add(price);
                        break;
                    case 2:
                        if(priceScheme.getGas2()==null){break;}
                        price.setTieredNum(2);
                        price.setTieredPrice(priceScheme.getPrice2().doubleValue());
                        price.setTieredValue(priceScheme.getGas2().intValue());
                        priceList.add(price);
                        break;
                    case 3:
                        if(priceScheme.getGas3()==null){break;}
                        price.setTieredNum(3);
                        price.setTieredPrice(priceScheme.getPrice3().doubleValue());
                        price.setTieredValue(priceScheme.getGas3().intValue());
                        priceList.add(price);
                        break;
                    case 4:
                        if(priceScheme.getGas4()==null){break;}
                        price.setTieredNum(4);
                        price.setTieredPrice(priceScheme.getPrice4().doubleValue());
                        price.setTieredValue(priceScheme.getGas4().intValue());
                        priceList.add(price);
                        break;
                    case 5:
                        if(priceScheme.getGas5()==null){break;}
                        price.setTieredNum(5);
                        price.setTieredPrice(priceScheme.getPrice5().doubleValue());
                        price.setTieredValue(65535);
                        priceList.add(price);
                        break;
                    default:
                        break;
                }
            }
           priceChangeVO.setTiered(priceList);
           dataList.add(priceChangeVO);
        }
        return businessBizApi.changebatprice(domainId,dataList);
    }

    @Override
    public IotR clearMeter(ClearVO clearVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(clearVO.getGasMeterNumber());
        return businessBizApi.clearmeter(domainId,clearVO);
    }

    @Override
    public IotR updatebalance(UpdateBalanceVO updateBalanceVO) {
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(updateBalanceVO.getGasMeterNumber());
        IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(updateBalanceVO.getGasMeterNumber());
        UpdateBalanceAndPriceModel params = new UpdateBalanceAndPriceModel();
        params.setArchiveId(model.getArchiveId());
        params.setBusinessType(CommandType.DAILYSETTLEMENT.getCode());
        params.setDomainId(model.getDomainId());
        List<SetUpParamsterModel<SetUpParamsterValueModel>> list = new ArrayList<>();
        if(model.getGasMeterCode()!=null&&model.getCustomerCode()!=null&&null==updateBalanceVO.getPrice()){
            GasMeterInfo gasMeterInfo = gasMeterInfoBizApi.getByMeterAndCustomerCode(model.getGasMeterCode(),model.getCustomerCode()).getData();
            if(null!=gasMeterInfo){
                updateBalanceVO.setPrice(gasMeterInfo.getCurrentPrice());
            }
        }
        if(updateBalanceVO.getPrice()!=null){
            list.add(getParamsList("STATE_PRICE",updateBalanceVO.getPrice().toString()));
        }
        if(updateBalanceVO.getBalance()!=null){
            list.add(getParamsList("STATE_BALANCE_AMOUNT",updateBalanceVO.getBalance().toString()));
            if(updateBalanceVO.getAlarmMoney()!=null){
                list.add(getParamsList("STATE_BALANCE_STATUS",updateBalanceVO.getBalance().compareTo(
                        updateBalanceVO.getAlarmMoney())<=0?"1":"0"));
            }
            list.add(getParamsList("STATE_OVERDRAFT",updateBalanceVO.getBalance()
                    .compareTo(BigDecimal.ZERO)<=0?"1":"0"));
        }
        params.setBusinessData(list);
        String sendData = JSONUtil.toJsonStr(params);
        CommandIotVO commandIotVO = defendCommand(updateBalanceVO.getGasMeterNumber(), model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                CommandType.DAILYSETTLEMENT, sendData,null,ExecuteState.Success.getCodeValue(), null);
        updateBalanceVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
        updateBalanceVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
        try{
            businessBizApi.updatebalance(domainId,updateBalanceVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return IotR.ok();
    }

    @Override
    public IotR clearAndRegisterMeter(String domainId, OrderListVO orderListVO) {
        return businessBizApi.clearAndRegisterMeter(domainId, orderListVO);
    }

    private PriceScheme isPopulationAddPrice(PriceScheme price,GasMeter gasMeter,UseGasType useGasType){
        return isPopulationAddPriceNode(price,gasMeter.getPopulation(),useGasType);
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
     * 指令维护
     * @param commandType 指令类型
     * @param commandData 指令信息
     * @param transactionNo 流水号
     * @param execStatus 执行状态
     */
    private CommandIotVO defendCommand(String gasMeterNumber, String gasMeterCode, String customerCode, Long orgId, String orgName,
                                       CommandType commandType, String commandData, String transactionNo, int execStatus, String error){
        CommandIotVO commandVO = new CommandIotVO();
        //保存指令信息
        IotGasMeterCommandSaveDTO command = new IotGasMeterCommandSaveDTO();
        command.setMeterNumber(gasMeterNumber);
        command.setCommandType(commandType);
        command.setCommandParameter(commandData);
        command.setGasMeterCode(gasMeterCode);
        command.setExecuteResult(execStatus);
        command.setTransactionNo(transactionNo);
        command.setOrgId(orgId);
        command.setOrgName(orgName);
        IotGasMeterCommand commData = iotGasMeterCommandBizApi.save(command).getData();
        commandVO.setIotGasMeterCommand(commData);
        //保存指令详情
        IotGasMeterCommandDetailSaveDTO commandDetail = new IotGasMeterCommandDetailSaveDTO();
        commandDetail.setTransactionNo(transactionNo);
        commandDetail.setCommandId(commData.getId());
        commandDetail.setCommandType(commandType);
        commandDetail.setExecuteTime(LocalDateTime.now());
        commandDetail.setCommandStatus(execStatus);
        commandDetail.setCommandParameter(commandData);
        commandDetail.setCustomerCode(customerCode);
        commandDetail.setGasMeterCode(gasMeterCode);
        commandDetail.setCommandStage((execStatus==0? CommandState.WaitSend.getCodeValue():
                execStatus));
        commandDetail.setMeterNumber(gasMeterNumber);
        commandDetail.setExecuteResult(execStatus);
        commandDetail.setErrorDesc(error);
        commandDetail.setOrgId(orgId);
        commandDetail.setOrgName(orgName);
        IotGasMeterCommandDetail iotGasMeterCommandDetail = iotGasMeterCommandDetailBizApi.save(commandDetail).getData();
        commandVO.setIotGasMeterCommandDetail(iotGasMeterCommandDetail);
        return commandVO;
    }

    public SetUpParamsterModel<SetUpParamsterValueModel> getParamsList(String paraType,String value){
        SetUpParamsterModel<SetUpParamsterValueModel> paramsModel = new SetUpParamsterModel<>();
        paramsModel.setParaType(paraType);
        SetUpParamsterValueModel s = new SetUpParamsterValueModel();
        s.setValue(value);
        paramsModel.setParameter(s);
        return paramsModel;
    }
}

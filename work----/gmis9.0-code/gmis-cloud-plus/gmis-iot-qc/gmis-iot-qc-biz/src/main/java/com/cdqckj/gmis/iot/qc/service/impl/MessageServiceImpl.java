package com.cdqckj.gmis.iot.qc.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.ValveState;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.dto.*;
import com.cdqckj.gmis.iot.qc.entity.*;
import com.cdqckj.gmis.iot.qc.enumeration.*;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.HttpCodeConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotRestUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.UploadDataVO;
import com.cdqckj.gmis.iot.qc.service.*;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.operateparam.entity.PriceMapping;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.service.PriceMappingService;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotErrorService;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import static org.jgroups.util.SuppressLog.Level.error;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列消费业务
 * @author: 秦川物联网科技
 * @date: 2020/10/15  13:05
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private IotDeviceDataHistoryService iotDeviceDataHistoryService;
    @Autowired
    private IotGasMeterUploadDataService iotGasMeterUploadDataService;
    @Autowired
    private IotAlarmService iotAlarmService;
    @Autowired
    private IotGasMeterCommandDetailService iotGasMeterCommandDetailService;
    @Autowired
    private IotGasMeterCommandService iotGasMeterCommandService;
    @Autowired
    private IotCacheUtil iotCacheUtil;
    @Autowired
    private IotRestUtil iotRestUtil;
    @Autowired
    private ReadMeterDataIotService readMeterDataIotService;
    @Autowired
    private ReadMeterDataIotErrorService readMeterDataIotErrorService;
    @Autowired
    private RechargeRecordBizApi rechargeRecordBizApi;
    @Autowired
    private GasMeterVersionBizApi gasMeterVersionBizApi;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private GasMeterInfoService gasMeterInfoService;
    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;
    @Autowired
    private IotGasMeterDayDataService iotGasMeterDayDataService;
    @Autowired
    private PriceMappingService priceMappingService;
    @Autowired
    private PriceSchemeService priceSchemeService;

    @Override
    public IotR deviceAddEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        List<DeviceDataVO> dataVOList = JSONObject.parseArray(data,DeviceDataVO.class);
        IotSubscribe iotGlobal = null;
        String sendData = null;
        //设置向qnms3.0加入设备参数
        DeviceModel deviceModel = new DeviceModel();
        List<DeviceDetailModel> list = new ArrayList<>();
        List<IotGasMeterUploadData> modelListEX = BeanPlusUtil.toBeanList(dataVOList,IotGasMeterUploadData.class);
        List<IotGasMeterUploadData> modelList = new ArrayList<>();
        for(IotGasMeterUploadData iotData:modelListEX){
            LbqWrapper<IotGasMeterUploadData> wrapperMeter = new LbqWrapper<>();
            wrapperMeter.eq(IotGasMeterUploadData::getGasMeterNumber,iotData.getGasMeterNumber());
            wrapperMeter.ne(IotGasMeterUploadData::getDeviceState,4);
            if(iotGasMeterUploadDataService.count(wrapperMeter)>0){
                continue;
            }else{
                modelList.add(iotData);
            }
        }
        if(modelList.size()==0){return IotR.ok();}
        iotGasMeterUploadDataService.saveBatch(modelList);
        List<IotGasMeterCommand> commandList = new ArrayList<>();
        List<IotGasMeterCommandDetail> detailList = new ArrayList<>();
        String domainId = null;
        for(DeviceDataVO dataVO:dataVOList){
            if(iotGlobal==null){
                iotGlobal = iotCacheUtil.getMessageFactoryEntity(dataVO.getDomainId());
            }
            if(domainId==null){domainId=dataVO.getDomainId();}
            deviceModel.setDomainId(dataVO.getDomainId());
            if(dataVO.getDeviceType()==0){deviceModel.setDeviceType(GMISConstant.GMIS_GAS_METER_TYPE);}
            else if(dataVO.getDeviceType()==1){deviceModel.setDeviceType(GMISConstant.GMIS_LLJ_METER_TYPE);}
            else if(dataVO.getDeviceType()==2){deviceModel.setDeviceType(GMISConstant.GMIS_WM_METER_TYPE);}
            DeviceDetailModel detailModel = new DeviceDetailModel();
            detailModel.setDeviceId(dataVO.getGasMeterNumber());
            list.add(detailModel);
            commandList.add(dataVO.getIotGasMeterCommand());
            detailList.add(dataVO.getIotGasMeterCommandDetail());
        }
        deviceModel.setData(list);
        sendData = JSONUtil.toJsonStr(deviceModel);
        //向3.0发送加入设备指令
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        JSONObject result = iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"domain/adddevice",sendData,
                true,domainId);
        if(!HttpCodeConstant.SUCCESS.equals(result.getString(HttpCodeConstant.RESULT_CODE))){
           log.error("request addDevice faild, error:"
                   +result.getString(HttpCodeConstant.RESULT_DATA)+", code:"+result.getString(HttpCodeConstant.RESULT_CODE));
            error = result.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        if(executeState==ExecuteState.Error){
            for(int i=0;i<commandList.size();i++){
                commandList.get(i).setExecuteResult(executeState.getCodeValue());
            }
            for(int i=0;i<detailList.size();i++){
                detailList.get(i).setExecuteResult(executeState.getCodeValue());
                detailList.get(i).setCommandStatus(executeState.getCodeValue());
                detailList.get(i).setCommandStage((executeState.getCodeValue()==0? CommandState.WaitSend.getCodeValue():
                        executeState.getCodeValue()));
                detailList.get(i).setErrorDesc(error);
            }
            iotGasMeterCommandDetailService.updateBatchById(detailList);
            iotGasMeterCommandService.updateBatchById(commandList);
        }
        for(IotGasMeterUploadData model:modelList){
            //defendCommand(model,CommandType.ADDDOMAIN,sendData,null,executeState.getCodeValue(),error);
            if(executeState == ExecuteState.Success) {
                model.setDeviceState(DeviceStage.Inactive.getCodeValue());
                iotGasMeterUploadDataService.updateById(model);
                for(DeviceDataVO dataVO:dataVOList){
                   if(dataVO.getGasMeterNumber().equals(model.getGasMeterNumber())){
                       //注册
                       register(model,dataVO,domainId,iotGlobal);
                       break;
                   }
                }
            }
        }
        return IotR.ok();
    }

    @Override
    public IotR updateDeviceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        UpdateDeviceVO updateDeviceVO = JSONObject.parseObject(data,UpdateDeviceVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,updateDeviceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());

        model.setCustomerCode(updateDeviceVO.getCustomerCode());
        model.setCustomerName(updateDeviceVO.getCustomerName());
        model.setMeterAddress((StringUtil.isNullOrBlank(updateDeviceVO.getAddress())
                ?"AddrDes":updateDeviceVO.getAddress()));
        //设置更新设备参数
        UpdateDeviceModel paramsModel = new UpdateDeviceModel();
        paramsModel.setCustomerName(StringUtil.isNullOrBlank(updateDeviceVO.getCustomerName())
                ? updateDeviceVO.getGasMeterNumber():updateDeviceVO.getCustomerName());
        paramsModel.setBaseNumber(updateDeviceVO.getBaseNumber()==null
                ?BigDecimal.ZERO:updateDeviceVO.getBaseNumber());
        paramsModel.setCustomerCode(StringUtil.isNullOrBlank(updateDeviceVO.getCustomerCode())?
                updateDeviceVO.getGasMeterNumber():updateDeviceVO.getCustomerCode());
        paramsModel.setTelephone(StringUtil.isNullOrBlank(updateDeviceVO.getTelephone())
                ?"13999999999":updateDeviceVO.getTelephone());
        paramsModel.setCustomerType(updateDeviceVO.getCustomerType());
        paramsModel.setAddress((StringUtil.isNullOrBlank(updateDeviceVO.getAddress())
                ?"AddrDes":updateDeviceVO.getAddress()));
        paramsModel.setIgnitionTime((updateDeviceVO.getIgnitionTime()==null||updateDeviceVO.getIgnitionTime()==0)
                ?LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli():updateDeviceVO.getIgnitionTime());
        paramsModel.setInstallationTime((updateDeviceVO.getInstallationTime()==null||updateDeviceVO.getInstallationTime()==0)
                ?LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli():updateDeviceVO.getInstallationTime());
        paramsModel.setUserCode(StringUtil.isNullOrBlank(updateDeviceVO.getCustomerCode())?
                updateDeviceVO.getGasMeterNumber():updateDeviceVO.getCustomerCode());
        GISModel gis = new GISModel();
        gis.setLatitude(updateDeviceVO.getLatitude().doubleValue());
        gis.setLongitude(updateDeviceVO.getLongitude().doubleValue());
        gis.setType(GMISConstant.GMIS_WGS_84);
        paramsModel.setGIS(gis);
        if(GMISConstant.DEVICE_GAS_DIRECTION_RIGHT.equals(updateDeviceVO.getDirection())){paramsModel.setIntakeDrection(1);}
        else{paramsModel.setIntakeDrection(0);}
        UpdateDeviceBaseModel params = new UpdateDeviceBaseModel();
        params.setDeviceData(paramsModel);
        if(updateDeviceVO.getDeviceType()==0){params.setDeviceType(GMISConstant.GMIS_GAS_METER_TYPE);}
        else if(updateDeviceVO.getDeviceType()==1){params.setDeviceType(GMISConstant.GMIS_LLJ_METER_TYPE);}
        else if(updateDeviceVO.getDeviceType()==2){params.setDeviceType(GMISConstant.GMIS_WM_METER_TYPE);}
        params.setDomainId(model.getDomainId());
        params.setArchiveId(model.getArchiveId());
        String sendUpdateData = JSONUtil.toJsonStr(params);
        //向3.0发送注册指令
        JSONObject updateRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/updatedevice",sendUpdateData,
                true,model.getDomainId());
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        if(!HttpCodeConstant.SUCCESS.equals(updateRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request updateDevice faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +updateRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+updateRes.getString(HttpCodeConstant.RESULT_CODE));
            error = updateRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        iotGasMeterUploadDataService.updateById(model);
        defendCommand(model,CommandType.UPDATEDEVICE,sendUpdateData,null,executeState.getCodeValue(),error);
        return IotR.ok();
    }

    @Override
    public IotR openAccountEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        OpenAccountVO openAccountVO = JSONObject.parseObject(data,OpenAccountVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                                      .eq(IotGasMeterUploadData::getGasMeterNumber,openAccountVO.getGasMeterNumber())
                                      .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(openAccountVO.getDomainId());
        GasMeterVersion gasMeterVersion = gasMeterVersionBizApi.get(model.getGasMeterVersionId()).getData();
        IotGasMeterCommand command = openAccountVO.getIotGasMeterCommand();
        IotGasMeterCommandDetail detail = openAccountVO.getIotGasMeterCommandDetail();
        //是否注册
        if(model.getDeviceState().equals(DeviceStage.Active.getCodeValue())){
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
                    priceModel.setEndDate(sdf.parse(GMISConstant.GMIS_DEFAULT_DATE).getTime());
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
            //向3.0发送开户指令
            ExecuteState executeState = ExecuteState.UnExecute;
            String error = null;
            JSONObject openAccountRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendOpenData,
                    true,model.getDomainId());
            if(!HttpCodeConstant.SUCCESS.equals(openAccountRes.getString(HttpCodeConstant.RESULT_CODE))){
                log.error("request openAccount faild,meterNo:"+model.getGasMeterNumber()+", error:"
                        +openAccountRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+openAccountRes.getString(HttpCodeConstant.RESULT_CODE));
                error = openAccountRes.getString(HttpCodeConstant.RESULT_DATA);
                executeState = ExecuteState.Error;
            }
            //指令记录
            if(null==command||null==detail){
                defendCommand(model,CommandType.OPENACCOUNT,sendOpenData,openAccountRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
            }else{
                //指令维护
                updateCommand(executeState,openAccountRes,command,detail,error);
            }
            if(executeState==ExecuteState.UnExecute){
                //设置密钥升级参数
                UpgradeKeyModel upgradeKeyModel = new UpgradeKeyModel();
                upgradeKeyModel.setArchiveId(model.getArchiveId());
                upgradeKeyModel.setDomainId(openAccountVO.getDomainId());
                UpgradeKeyCmd upgradeKeyCmd = new UpgradeKeyCmd();
                upgradeKeyCmd.setKeyNo(1);
                upgradeKeyModel.setBusinessData(upgradeKeyCmd);
                upgradeKeyModel.setBusinessType(CommandType.UPGRADEKEY.getCode());
                String upgrData = JSONUtil.toJsonStr(upgradeKeyModel);
                //向3.0发送密钥升级指令
                JSONObject upgradeKeyRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",upgrData,
                        true,model.getDomainId());
                if(!HttpCodeConstant.SUCCESS.equals(upgradeKeyRes.getString(HttpCodeConstant.RESULT_CODE))){
                    log.error("request upgradeKey faild,meterNo:"+model.getGasMeterNumber()+", error:"
                            +upgradeKeyRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+upgradeKeyRes.getString(HttpCodeConstant.RESULT_CODE));
                    error = upgradeKeyRes.getString(HttpCodeConstant.RESULT_DATA);
                    executeState = ExecuteState.Error;
                }
                //指令记录
                defendCommand(model,CommandType.UPGRADEKEY,upgrData,upgradeKeyRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
                model.setUseGasTypeId(openAccountVO.getUseGasTypeId());
                model.setPriceSchemeId(openAccountVO.getPriceId());
                model.setDeviceState(DeviceStage.Enabled.getCodeValue());
                model.setUseGasTypeName(openAccountVO.getUseGasTypeName());
                iotGasMeterUploadDataService.updateById(model);
                //中心计费更新表端单价
                if(gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.CENTER_RECHARGE)
                        ||gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.REMOTE_READMETER)){
                    List<PriceVO> list = openAccountVO.getTiered();
                    if(null!=list&&list.size()>0){
                        UpdateBalanceVO updateBalanceVO = new UpdateBalanceVO();
                        updateBalanceVO.setPrice(BigDecimal.valueOf(list.get(0).getTieredPrice()));
                        updateBalance(model,iotGlobal,updateBalanceVO);
                    }
                }
                ValveControlVO valveControlVO = new ValveControlVO();
                valveControlVO.setDomainId(model.getDomainId());
                valveControlVO.setGasMeterNumber(model.getGasMeterNumber());
                if(gasMeterVersion.getOpenValve()==1){
                    //发送开阀指令
                    valveControlVO.setControlCode("10");
                    valveOptions(valveControlVO,CommandType.OPENVALVE,iotGlobal);
                }else{
                    //发送关阀指令
                    valveControlVO.setControlCode("00");
                    valveOptions(valveControlVO,CommandType.CLOSEVALVE,iotGlobal);
                }
                //表端计费进行调价
                if(gasMeterVersion.getOrderSourceName().equals(OrderSourceNameEnum.REMOTE_RECHARGE.getCode())){
                    //调价
                    PriceChangeVO priceChangeVO = new PriceChangeVO();
                    priceChangeVO.setAdjDay(openAccountVO.getSettlementDay());
                    priceChangeVO.setAdjMonth(openAccountVO.getStartMonth());
                    priceChangeVO.setCurGasTypeNum(openAccountVO.getUseGasTypeNum());
                    priceChangeVO.setCycle(openAccountVO.getCycle());
                    priceChangeVO.setDomainId(openAccountVO.getDomainId());
                    priceChangeVO.setEnddate(openAccountVO.getEndTime());
                    priceChangeVO.setGasMeterNumber(openAccountVO.getGasMeterNumber());
                    priceChangeVO.setInitAmount(model.getInitialMeasurementBase());
                    priceChangeVO.setIsClear(openAccountVO.getIsClear());
                    priceChangeVO.setPriceNum(openAccountVO.getPriceNum());
                    priceChangeVO.setStartdate(openAccountVO.getStartTime());
                    priceChangeVO.setTiered(openAccountVO.getTiered());
                    this.changePriceSingle(priceChangeVO);
                    //更新充值次数
                    CustomerGasMeterRelated customerParams = new CustomerGasMeterRelated();
                    customerParams.setGasMeterCode(model.getGasMeterCode());
                    customerParams.setDataStatus(DataStatusEnum.NORMAL.getValue());
                    List<CustomerGasMeterRelated> relateds = customerGasMeterRelatedBizApi.query(customerParams).getData();
                    if(relateds.size()>0){
                       CustomerGasMeterRelated customerGasMeterRelated = relateds.get(0);
                       GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                                customerGasMeterRelated.getCustomerCode());
                       if(gasMeterInfo!=null){
                           gasMeterInfo.setTotalRechargeMeterCount(1);
                           gasMeterInfoService.updateById(gasMeterInfo);
                       }
                    }
                }
            }
        }else{
            log.error("表具未注册，请先进行表具注册！");
            IotR.error("表具未注册");
        }
        return IotR.ok();
    }

    @Override
    public IotR valveControlEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        ValveControlVO valveControlVO = JSONObject.parseObject(data,ValveControlVO.class);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(valveControlVO.getDomainId());
        if(GMISConstant.DEVICE_VALVE_CLOSE_CODE.equals(valveControlVO.getControlCode())
                ||(GMISConstant.DEVICE_VALVE_PRIVATE_CLOSE_CODE.equals(valveControlVO.getControlCode()))){
            valveOptions(valveControlVO,CommandType.CLOSEVALVE,iotGlobal);
        }else if(GMISConstant.DEVICE_VALVE_CLOSE_OPEN.equals(valveControlVO.getControlCode())){
            valveOptions(valveControlVO,CommandType.OPENVALVE,iotGlobal);
        }else{
            IotR.error();
        }
        return IotR.ok();
    }

    @Override
    public IotR valveBatControlEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        String domainId = null;
        String controlCode = null;
        List<ValveControlVO> valveControlVOList = JSONObject.parseArray(data,ValveControlVO.class);
        List<String> meterNo = new ArrayList<>();
        for(ValveControlVO vo:valveControlVOList){
            if(StringUtil.isNullOrBlank(domainId)){domainId = vo.getDomainId();}
            if(StringUtil.isNullOrBlank(controlCode)){controlCode = vo.getControlCode();}
            meterNo.add(vo.getGasMeterNumber());
        }
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        if(GMISConstant.DEVICE_VALVE_CLOSE_CODE.equals(controlCode)){
            valveBatOptions(meterNo,controlCode,CommandType.CLOSEVALVE,iotGlobal,domainId);
        }else if(GMISConstant.DEVICE_VALVE_CLOSE_OPEN.equals(controlCode)){
            valveBatOptions(meterNo,controlCode,CommandType.OPENVALVE,iotGlobal,domainId);
        }else{
            IotR.error();
        }
        return IotR.ok();
    }

    @Override
    public IotR registerDeviceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        RegisterDeviceVO registerDeviceVO = JSONObject.parseObject(data,RegisterDeviceVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,registerDeviceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //设置注册参数
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceId(model.getGasMeterNumber());
        deviceData.setCustomerName(StringUtil.isNullOrBlank(registerDeviceVO.getCustomerName())
                ? registerDeviceVO.getGasMeterNumber():registerDeviceVO.getCustomerName());
        deviceData.setBaseNumber(registerDeviceVO.getBaseNumber()==null
                ?BigDecimal.ZERO:registerDeviceVO.getBaseNumber());
        deviceData.setBaseType(model.getSpecification());
        deviceData.setControlMode(StringUtil.isNullOrBlank(registerDeviceVO.getControlMode())
                ?"SERVICECTRL":registerDeviceVO.getControlMode());
        deviceData.setCustomerCode(StringUtil.isNullOrBlank(registerDeviceVO.getCustomerCode())?
                registerDeviceVO.getGasMeterNumber():registerDeviceVO.getCustomerCode());
        deviceData.setTelephone(StringUtil.isNullOrBlank(registerDeviceVO.getTelephone())
                ?"13999999999":registerDeviceVO.getTelephone());
        deviceData.setCustomerType(registerDeviceVO.getCustomerType());
        deviceData.setAddress((StringUtil.isNullOrBlank(registerDeviceVO.getAddress())
                ?"AddrDes":registerDeviceVO.getAddress()));
        deviceData.setIgnitionTime((registerDeviceVO.getIgnitionTime()==null||registerDeviceVO.getIgnitionTime()==0)
                ?LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli():registerDeviceVO.getIgnitionTime());
        deviceData.setInstallationTime((registerDeviceVO.getInstallationTime()==null||registerDeviceVO.getInstallationTime()==0)
                ?LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli():registerDeviceVO.getInstallationTime());
        deviceData.setUserCode(StringUtil.isNullOrBlank(registerDeviceVO.getCustomerCode())?
                registerDeviceVO.getGasMeterNumber():registerDeviceVO.getCustomerCode());
        if(registerDeviceVO.getVersionNo()!=null&&registerDeviceVO.getVersionNo()
                .contains(GMISConstant.GMIS_DEVICE_VERSION_NB21_NO)){
            deviceData.setVersionNo(GMISConstant.GMIS_DEVICE_VERSION_NB21_NO);
        }else if(registerDeviceVO.getVersionNo()!=null&&registerDeviceVO.getVersionNo()
                .contains(GMISConstant.GMIS_DEVICE_VERSION_NB20E_NO)){
            deviceData.setVersionNo(GMISConstant.GMIS_DEVICE_VERSION_NB20E_NO);
        }
        GISModel gis = new GISModel();
        gis.setLatitude(registerDeviceVO.getLatitude().doubleValue());
        gis.setLongitude(registerDeviceVO.getLongitude().doubleValue());
        gis.setType(GMISConstant.GMIS_WGS_84);
        deviceData.setGIS(gis);
        if(GMISConstant.DEVICE_GAS_DIRECTION_RIGHT.equals(registerDeviceVO.getDirection())){deviceData.setIntakeDrection(1);}
        else{deviceData.setIntakeDrection(0);}
        RegisterUserModel registerUserModel = new RegisterUserModel();
        registerUserModel.setDeviceData(deviceData);
        if(registerDeviceVO.getDeviceType()==0){registerUserModel.setDeviceType(GMISConstant.GMIS_GAS_METER_TYPE);}
        else if(registerDeviceVO.getDeviceType()==1){registerUserModel.setDeviceType(GMISConstant.GMIS_LLJ_METER_TYPE);}
        else if(registerDeviceVO.getDeviceType()==2){registerUserModel.setDeviceType(GMISConstant.GMIS_WM_METER_TYPE);}
        registerUserModel.setDomainId(model.getDomainId());
        String sendRegisterData = JSONUtil.toJsonStr(registerUserModel);
        //向3.0发送注册指令
        JSONObject registerRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/registerdevice",sendRegisterData,
                true,model.getDomainId());
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        if(!HttpCodeConstant.SUCCESS.equals(registerRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request registerDevice faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +registerRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+registerRes.getString(HttpCodeConstant.RESULT_CODE));
            error = registerRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        if(executeState == ExecuteState.Success){
            model.setArchiveId(registerRes.getJSONObject(HttpCodeConstant.RESULT_DATA).getString(GMISConstant.GMIS_DEVICE_ARCHIVE_ID));
            model.setDeviceState(DeviceStage.Active.getCodeValue());
            //更新设备列表
            iotGasMeterUploadDataService.updateById(model);
        }
        defendCommand(model,CommandType.REGISTER,sendRegisterData,null,executeState.getCodeValue(),error);
        return IotR.ok();
    }

    @Override
    public IotR removeDeviceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        RemoveDeviceVO removeDeviceVO = JSONObject.parseObject(data,RemoveDeviceVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,removeDeviceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //设置拆除参数
        RemoveDeviceModel rdm = new RemoveDeviceModel();
        rdm.setArchiveId(model.getArchiveId());
        rdm.setDomainId(model.getDomainId());
        RemoveBus rb = new RemoveBus();
        rb.setIsClear(true);
        rdm.setControlData(rb);
        String sendRDMData = JSONUtil.toJsonStr(rdm);
        //向3.0发送移除设备指令
        JSONObject rdmRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/deletedevice",sendRDMData,
                true,model.getDomainId());
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        if(!HttpCodeConstant.SUCCESS.equals(rdmRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request removeDevice faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +rdmRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+rdmRes.getString(HttpCodeConstant.RESULT_CODE));
            error = rdmRes.getString(HttpCodeConstant.MESSAGE);
            executeState = ExecuteState.Error;
        }else{
            model.setArchiveId(null);
            model.setDeviceState(DeviceStage.Inactive.getCodeValue());
        }
        defendCommand(model,CommandType.REMOVEDEVICE,sendRDMData,null,executeState.getCodeValue(),error);
        if(model.getDeviceState().equals(DeviceStage.Inactive.getCodeValue())){
            //设置移除逻辑域参数
            RemoveDomainModel rd = new RemoveDomainModel();
            rd.setDomainId(model.getDomainId());
            if(removeDeviceVO.getDeviceType()==0){rd.setDeviceType(GMISConstant.GMIS_GAS_METER_TYPE);}
            else if(removeDeviceVO.getDeviceType()==1){rd.setDeviceType(GMISConstant.GMIS_LLJ_METER_TYPE);}
            else if(removeDeviceVO.getDeviceType()==2){rd.setDeviceType(GMISConstant.GMIS_WM_METER_TYPE);}
            List<RemoveDomainBus> list =new ArrayList<>();
            RemoveDomainBus rdb = new RemoveDomainBus();
            rdb.setDeviceId(model.getGasMeterNumber());
            list.add(rdb);
            rd.setData(list);
            String sendRDData = JSONObject.toJSONString(rd);
            //向3.0发送移除逻辑域指令
            String rError = null;
            JSONObject rdRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"domain/removedevice",sendRDData,
                    true,model.getDomainId());
            if(!HttpCodeConstant.SUCCESS.equals(rdRes.getString(HttpCodeConstant.RESULT_CODE))){
                log.error("request removeDomin faild,meterNo:"+model.getGasMeterNumber()+", error:"
                        +rdRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+rdRes.getString(HttpCodeConstant.RESULT_CODE));
                rError = rdRes.getString(HttpCodeConstant.RESULT_DATA);
                executeState = ExecuteState.Error;
            }else{model.setDeviceState(DeviceStage.Closed.getCodeValue());}
            defendCommand(model,CommandType.REMOVEDOMAIN,sendRDData,null,executeState.getCodeValue(),rError);
        }
        //更新设备数据
        iotGasMeterUploadDataService.updateById(model);
        return IotR.ok();
    }

    @Override
    public IotR rechargeEvent(RechargeVO rechargeVO, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        //RechargeVO rechargeVO = JSONObject.parseObject(data,RechargeVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,rechargeVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //查询户表
        CustomerGasMeterRelated customerParams = new CustomerGasMeterRelated();
        customerParams.setGasMeterCode(model.getGasMeterCode());
        customerParams.setDataStatus(DataStatusEnum.NORMAL.getValue());
        List<CustomerGasMeterRelated> relateds = customerGasMeterRelatedBizApi.query(customerParams).getData();
        GasMeterInfo gasMeterInfo =null;
        if(relateds.size()>0){
            CustomerGasMeterRelated customerGasMeterRelated = relateds.get(0);
            gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                    customerGasMeterRelated.getCustomerCode());
            if(gasMeterInfo==null){
                throw new BizException("未获取到户表账户");
            }
        }
        //设置充值参数
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
        //向3.0发送表端结算表的充值指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject rechargeRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(rechargeRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request recharge faild,meterNo:"+model.getGasMeterNumber()+" ,error:"
                    +rechargeRes.getString(HttpCodeConstant.RESULT_DATA)+" ,code:"+rechargeRes.getString(HttpCodeConstant.RESULT_CODE));
            error = rechargeRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令记录
        if(executeState==ExecuteState.UnExecute){
//            rechargeRecordBizApi.iotRechargeOrderRetryCallBack(rechargeRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
//                    .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
            GasMeterInfo update = GasMeterInfo.builder()
                    .id(gasMeterInfo.getId())
                    .build();

            //更新充值次数
            update.setTotalRechargeMeterCount((rechargeBus.getRechargeTimes()));

//                    gasMeterInfo.setTotalChargeCount((gasMeterInfo.getTotalChargeCount()+1));
            if(gasMeterInfo.getValue1()!=null&&gasMeterInfo.getValue1().compareTo(BigDecimal.ZERO)==0
                && Objects.nonNull(gasMeterInfo.getValue2())){
                update.setValue1(rechargeVO.getRechargeAmount());
                update.setValue2(gasMeterInfo.getValue2());
                update.setValue3(gasMeterInfo.getValue3());
            }else{
                update.setValue1(rechargeVO.getRechargeAmount());
                update.setValue2(gasMeterInfo.getValue1());
                update.setValue3(gasMeterInfo.getValue2());
            }
            gasMeterInfoService.updateById(update);
            IotGasMeterCommand command = rechargeVO.getIotGasMeterCommand();
            IotGasMeterCommandDetail detail = rechargeVO.getIotGasMeterCommandDetail();
            if(null==command||null==detail){
                defendCommand(model,CommandType.RECHARGE,sendData,rechargeRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
            }else{
                updateCommand(executeState,rechargeRes,command,detail,error);

            }
            return IotR.ok(rechargeRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                    .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
        }else{
            IotGasMeterCommand command = rechargeVO.getIotGasMeterCommand();
            IotGasMeterCommandDetail detail = rechargeVO.getIotGasMeterCommandDetail();
            if(null==command||null==detail){
                defendCommand(model,CommandType.RECHARGE,sendData,rechargeRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
            }else{
                updateCommand(executeState,rechargeRes,command,detail,error);
            }
        }
        return IotR.ok();
    }

    @Override
    public IotR changePriceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        PriceChangeVO priceChangeVO = JSONObject.parseObject(data,PriceChangeVO.class);
        return this.changePriceSingle(priceChangeVO);
    }

    @Override
    public IotR changeBatPriceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        List<PriceChangeVO> list = JSONObject.parseArray(data,PriceChangeVO.class);
        return changePriceBat(list);
    }

    @Override
    public IotR deviceDataEvent(String data, String tenant) {
        log.info("收到设备数据:"+data);
        BaseContextHandler.setTenant(tenant);
        ReportModel<ReportDeviceDataModel> deviceData = JSONObject.parseObject(data,new TypeReference<ReportModel<ReportDeviceDataModel>>(){});
        //保存设备历史数据
        saveDeviceHistoryData(deviceData,DataType.DeviceDataNotice.getCode(),data);
        //每日上报数据
        IotGasMeterDayData dayData = new IotGasMeterDayData();
        //獲取設備數據
        IotGasMeterUploadData gasMeterData = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber, deviceData.getDeviceId())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(gasMeterData==null){return IotR.error();}
        LbqWrapper<GasMeter> meterLbqWrapper = new LbqWrapper<>();
        meterLbqWrapper.eq(GasMeter::getGasMeterNumber,gasMeterData.getGasMeterNumber());
        meterLbqWrapper.notIn(GasMeter::getDataStatus,5,6);
        GasMeter gasMeter = gasMeterService.getOne(meterLbqWrapper);
        String tus = Double.toString(deviceData.getData().getDeviceData().getTotalUseGas());
        BigDecimal totalUseGas = new BigDecimal(tus);
        BigDecimal currentReadingNum =  totalUseGas.subtract(gasMeterData.getTotalUseGas()==null
                ?BigDecimal.ZERO:gasMeterData.getTotalUseGas());
        gasMeterData.setIsOnline(1);
        gasMeterData.setUploadTime(LocalDateTime.now());
        //设置阀门状态
        if(deviceData.getData().getDeviceData().getCloseValve()){
            gasMeterData.setValveStatus(ValveState.PowerClose.getValveCode());
        }else{ gasMeterData.setValveStatus((deviceData.getData().getDeviceData().getValveState() ?
                ValveState.Close.getValveCode() : ValveState.Open.getValveCode())); }
        //更新表内余额
        LbqWrapper<CustomerGasMeterRelated> relatedLbqWrapper =new LbqWrapper<>();
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getGasMeterCode,gasMeter.getGasCode());
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue());
        CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelatedService.getOne(relatedLbqWrapper);
        GasMeterVersion gasMeterVersion = null;
        GasMeterInfo gasMeterInfo = null;
        if(null!=customerGasMeterRelated){
            gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                    customerGasMeterRelated.getCustomerCode());
            if(null!=gasMeterInfo){
                gasMeterInfo.setGasMeterInBalance(BigDecimal.valueOf(deviceData.getData().getDeviceData().getBalance()));
                gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                if(deviceData.getData().getDeviceData().getAlarmConnection()!=null&&deviceData.getData().getDeviceData()
                        .getAlarmConnection()){gasMeterInfo.setAlarmStatus(1);}
                else {gasMeterInfo.setAlarmStatus(0);}
                dayData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                dayData.setGasMeterBalance(BigDecimal.valueOf(deviceData.getData().getDeviceData().getBalance()));
                dayData.setMeterType(1);
                gasMeterVersion = gasMeterVersionBizApi.get(gasMeter.getGasMeterVersionId()).getData();
                if(gasMeterVersion!=null&&OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                    gasMeterInfo.setCycleUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getCycleUseGas()));
                    gasMeterInfo.setCurrentLadder((deviceData.getData().getDeviceData().getCurTiered()+1));
                    gasMeterInfo = setNB20EParams(deviceData,gasMeter,gasMeterInfo);
                    gasMeterData.setTotalUseGas(totalUseGas);
                    dayData.setMeterType(0);
                }
            }
        }
        //表端数据、报警、每日上报
        saveAlarmAndDayData(deviceData,dayData,gasMeterData,gasMeter,gasMeterVersion,gasMeterInfo,currentReadingNum);
        return IotR.ok();
    }

    /**
     * 表端数据、报警、每日上报
     * @param deviceData
     * @param dayData
     * @param gasMeterData
     * @param gasMeter
     * @param gasMeterVersion
     * @param gasMeterInfo
     * @param currentReadingNum
     */
    public void saveAlarmAndDayData(ReportModel<ReportDeviceDataModel> deviceData,IotGasMeterDayData dayData,IotGasMeterUploadData gasMeterData,
                                    GasMeter gasMeter,GasMeterVersion gasMeterVersion,GasMeterInfo gasMeterInfo,BigDecimal currentReadingNum){
        UseGasModel dayUseGas = deviceData.getData().getDeviceData().getDailyUseGas();
        if(dayUseGas!=null&&dayUseGas.getUseGasDetail()!=null&&dayUseGas.getUseGasDetail().size()>0&&
                gasMeterData.getDeviceState().equals(DeviceStage.Enabled.getCodeValue())){
            List<ReadMeterDataIot> gasMeterList = new ArrayList<>();
            List<ReadMeterDataIot> gasMeterErrorList = new ArrayList<>();
            synchronized (this){
                boolean isBc = false;
                ReadMeterDataIot previous = null;
                UploadDataVO uploadDataVO = new UploadDataVO();
                uploadDataVO.setIsBc(isBc);
                uploadDataVO.setPrevious(previous);
                for(int i=0;i<dayUseGas.Days;i++){
                    if(0==i){dayData.setDayUse5(dayUseGas.getUseGasDetail().get(i));}
                    else if(1==i){ dayData.setDayUse4(dayUseGas.getUseGasDetail().get(i));}
                    else if(2==i){ dayData.setDayUse3(dayUseGas.getUseGasDetail().get(i));}
                    else if(3==i){ dayData.setDayUse2(dayUseGas.getUseGasDetail().get(i));}
                    else if(4==i){ dayData.setDayUse1(dayUseGas.getUseGasDetail().get(i));}
                    if(gasMeterData.getDayReadNum()==null||gasMeterData.getDayReadNum()==0){gasMeterData.setDayReadNum(1);}
                    else{gasMeterData.setDayReadNum((gasMeterData.getDayReadNum()+1));}
                    if(gasMeterData.getDayReadNum()==1){
                        gasMeterData.setInitialMeasurementBase(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                        gasMeter.setGasMeterBase(gasMeterData.getInitialMeasurementBase());
                        gasMeterService.updateById(gasMeter);
                    }
                    LocalDateTime sDate = dayUseGas.StartDate.plusDays(i);
                    dayData.setFreezingTime(sDate);
                    if(sDate.toLocalDate().isBefore(gasMeter.getOpenAccountTime().toLocalDate())){continue;}
                    if(readMeterDataIotService.check(sDate.toLocalDate(),gasMeterData.getGasMeterNumber(),
                            gasMeterData.getCustomerCode(),gasMeterData.getGasMeterCode())){continue;}
                    //数据上报处理
                    uploadDataVO  = uploadReadMeterDataNB(gasMeter,gasMeterData, dayUseGas,sDate,i,gasMeterVersion,uploadDataVO,gasMeterList,gasMeterInfo,deviceData);
                    ReadMeterDataIot igd = uploadDataVO.getIgd();
                    if(igd.getMonthUseGas()!=null&&igd.getLastTotalGas()!=null){
                        if(igd.getMonthUseGas().compareTo(BigDecimal.ZERO)<0){gasMeterErrorList.add(igd);}else{gasMeterList.add(igd);}
                    }
                }
                if(gasMeterList.size()>0){readMeterDataIotService.saveBatchSomeColumn(gasMeterList);}
                if(gasMeterErrorList.size()>0){
                    List<ReadMeterDataIotError> errors = BeanPlusUtil.toBeanList(gasMeterErrorList,ReadMeterDataIotError.class);
                    readMeterDataIotErrorService.saveBatchSomeColumn(errors);}
            }
            dayData.setCompanyCode(gasMeter.getCompanyCode());
            dayData.setCompanyName(gasMeter.getCompanyName());
            dayData.setOrgId(gasMeter.getOrgId());
            dayData.setOrgName(gasMeter.getOrgName());
            dayData.setGasMeterCode(gasMeter.getGasCode());
            dayData.setVersionName(gasMeterVersion.getGasMeterVersionName());
            dayData.setGasMeterNumber(gasMeter.getGasMeterNumber());
            dayData.setInstallAddress(gasMeter.getMoreGasMeterAddress());
            dayData.setCustomerName(gasMeterData.getCustomerName());
            if(deviceData.getData().getDeviceData().getCloseValve()){
                dayData.setValveState(ValveState.PowerClose.getValveCode());
            }else{ dayData.setValveState((deviceData.getData().getDeviceData().getValveState() ?
                    ValveState.Close.getValveCode() : ValveState.Open.getValveCode())); }
            iotGasMeterDayDataService.save(dayData);
        }
        if(null!=gasMeterInfo){gasMeterInfoService.updateById(gasMeterInfo);}
        //更新设备数据
        iotGasMeterUploadDataService.updateById(gasMeterData);
        //報警
        List<IotAlarm> alarms = new ArrayList<>();
        //阀门异常
        if (deviceData.getData().getDeviceData().getUploadWay() == 2){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.ValveException.getCode()
                    ,AlramType.ValveException.getDesc()));}
        //强磁干扰
        if(deviceData.getData().getDeviceData().getUploadWay() == 3){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.AntiMagnetic.getCode()
                    ,AlramType.AntiMagnetic.getDesc()));}
        //过流
        if(deviceData.getData().getDeviceData().getUploadWay() == 15){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.FlowOver.getCode()
                    ,AlramType.FlowOver.getDesc()));}
        //电池电量不足
        if(deviceData.getData().getDeviceData().getUploadWay() == 5){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.BatteryError.getCode()
                    ,AlramType.BatteryError.getDesc()));}
        //泄露报警
        if(deviceData.getData().getDeviceData().leakAlarm){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.Gasleakage.getCode()
                    ,AlramType.Gasleakage.getDesc()));}
        //不用气关阀
        if(deviceData.getData().getDeviceData().getUploadWay() == 20){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.NoUse.getCode()
                    ,AlramType.NoUse.getDesc()));}
        //外部报警
        if(deviceData.getData().getDeviceData().getUploadWay() == 8){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.ExternalTrigger.getCode()
                    ,AlramType.ExternalTrigger.getDesc()));}
        //通信异常
        if(deviceData.getData().getDeviceData().getUploadWay() == 10){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.CommunationException.getCode()
                    ,AlramType.CommunationException.getDesc()));}
        //读数异常变小
        if(currentReadingNum.compareTo(BigDecimal.ZERO)<0){
            alarms.add(getAlarmEntity(gasMeterData.getGasMeterNumber(),AlramType.ReadingException.getCode()
                    ,AlramType.ReadingException.getDesc()));
        }
        if(alarms.size()>0){iotAlarmService.saveBatch(alarms);}
    }

    /**
     * NB20E状态值设置
     * @param deviceData
     * @param gasMeter
     * @param gasMeterInfo
     * @return
     */
    public GasMeterInfo setNB20EParams(ReportModel<ReportDeviceDataModel> deviceData,GasMeter gasMeter,GasMeterInfo gasMeterInfo){
        List<PriceMapping> priceMappings = priceMappingService.list(Wraps.<PriceMapping>lbQ()
                .eq(PriceMapping::getGasMeterCode,gasMeter.getGasCode())
                .eq(PriceMapping::getPriceNum,deviceData.getData().getDeviceData().getPriceNum())
                .eq(PriceMapping::getUseGasTypeNum,deviceData.getData().getDeviceData().getCurrentUseType()));
        if(null!=priceMappings&&priceMappings.size()>0&&null!=priceMappings.get(0).getPriceId()){
            PriceScheme priceScheme = priceSchemeService.getById(priceMappings.get(0).getPriceId());
            if(null!=priceScheme){
                if(deviceData.getData().getDeviceData().getCurTiered()==0){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice1());
                }else if(deviceData.getData().getDeviceData().getCurTiered()==1){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice2());
                }else if(deviceData.getData().getDeviceData().getCurTiered()==2){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice3());
                }else if(deviceData.getData().getDeviceData().getCurTiered()==3){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice3());
                }else if(deviceData.getData().getDeviceData().getCurTiered()==4){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice5());
                }else if(deviceData.getData().getDeviceData().getCurTiered()==5){
                    gasMeterInfo.setCurrentPrice(priceScheme.getPrice6());
                }
            }
        }
        return gasMeterInfo;
    }

    @Override
    public IotR businessDataEvent(String data, String tenant) {
        log.info("收到业务数据:"+data);
        BaseContextHandler.setTenant(tenant);

        ReportModel<BusinessStateModel> businessData = JSONObject.parseObject(data,new TypeReference<ReportModel<BusinessStateModel>>(){});
        //保存业务历史数据
        saveDeviceHistoryData(businessData,DataType.BusinessStateNotice.getCode(),data);
        //獲取設備數據
        IotGasMeterUploadData gasMeterData = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber, businessData.getDeviceId())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(gasMeterData==null){log.error("未找到设备信息:"+businessData.getData().transactionNo);return IotR.error();}
        //获取指令信息
        IotGasMeterCommand igmc = iotGasMeterCommandService.getOne(Wraps.<IotGasMeterCommand>lbQ()
                .eq(IotGasMeterCommand::getMeterNumber,gasMeterData.getGasMeterNumber())
                .eq(IotGasMeterCommand::getTransactionNo,businessData.getData().transactionNo));
        if(igmc==null){log.error("未找到指令信息:"+businessData.getData().transactionNo);return IotR.error();}
        //添加指令详情
        LbqWrapper<IotGasMeterCommandDetail> lbqWrapper = new LbqWrapper<>();
        lbqWrapper.eq(IotGasMeterCommandDetail::getMeterNumber,gasMeterData.getGasMeterNumber());
        lbqWrapper.eq(IotGasMeterCommandDetail::getTransactionNo,businessData.getData().transactionNo);
        IotGasMeterCommandDetail igmcd = iotGasMeterCommandDetailService.getOne(lbqWrapper);
        if(null==igmcd){
           igmcd = new IotGasMeterCommandDetail();
        }
        igmcd.setCommandId(igmc.getId());
        igmcd.setCommandStage((int) businessData.getData().getPhase());
        if(businessData.getData().getIsEnd()){igmcd.setExecuteResult(1);}
        else{igmcd.setExecuteResult(-1);}
        igmcd.setCommandStatus((int) businessData.getData().getState());
        igmcd.setMeterNumber(gasMeterData.getGasMeterNumber());
        igmcd.setTransactionNo(businessData.getData().getTransactionNo());
        igmcd.setTo3Time(DateUtils.getDateTimeOfTimestamp(businessData.getData().getTime()));
        igmcd.setExecuteTime(LocalDateTime.now());
        synchronized (this){
            if(CommandType.OPENACCOUNT.getCode().equals(businessData.getData().getBusinessType())){
                if(businessData.getData().getState()==1&&businessData.getData().getIsEnd()){
                    gasMeterData.setDeviceState(DeviceStage.Enabled.getCodeValue());
                }else{
                    gasMeterData.setDeviceState(DeviceStage.Active.getCodeValue());
                }
                igmcd.setCommandType(CommandType.OPENACCOUNT);
                iotGasMeterUploadDataService.updateById(gasMeterData);
            }else if(CommandType.RECHARGE.getCode().equals(businessData.getData().getBusinessType())){
//            LbqWrapper<IotGasMeterCommandDetail> wrapper = new LbqWrapper<>();
//            wrapper.eq(IotGasMeterCommandDetail::getTransactionNo,businessData.getData().getTransactionNo());
//            IotGasMeterCommandDetail commandDetail = iotGasMeterCommandDetailService.getOne(wrapper);
//            //是否撤销
//            if(businessData.getData().getState()==ExecuteState.Cancel.getCodeValue()&&
//                    commandDetail.getCommandStatus().equals(ExecuteState.Cancel.getCodeValue())){
//                rechargeRecordBizApi.iotRechargeOrderCancelCallBack(businessData.getData().getTransactionNo());
//            }
//            //回调充值接口
//            rechargeRecordBizApi.iotRechargeMeterCallBack(businessData.getData().getTransactionNo(),
//                    businessData.getData().getIsEnd());
                igmcd.setCommandType(CommandType.RECHARGE);
            }else if(CommandType.VALVECONTROL.getCode().equals(businessData.getData().getBusinessType())){
                igmcd.setCommandType(CommandType.VALVECONTROL);
            }else if(CommandType.MODIFYPRICE.getCode().equals(businessData.getData().getBusinessType())){
                igmcd.setCommandType(CommandType.MODIFYPRICE);
            }else if(CommandType.PARAMETER.getCode().equals(businessData.getData().getBusinessType())){
                igmcd.setCommandType(CommandType.PARAMETER);
            }else if(CommandType.UPGRADEKEY.getCode().equals(businessData.getData().getBusinessType())){
                igmcd.setCommandType(CommandType.UPGRADEKEY);
            }
            if(null!=igmcd.getId()){iotGasMeterCommandDetailService.updateById(igmcd);}else{iotGasMeterCommandDetailService.save(igmcd);}
            if(businessData.getData().getIsEnd()){
                igmc.setExecuteResult(igmcd.getExecuteResult());
                //更新业务指令
                iotGasMeterCommandService.updateById(igmc);
            }
        }
        return IotR.ok();
    }

    @Override
    public IotR updateBalanceEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        UpdateBalanceVO updateBalanceVO = JSONObject.parseObject(data,UpdateBalanceVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,updateBalanceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        return updateBalance(model,iotGlobal,updateBalanceVO);
    }

    private IotR updateBalance(IotGasMeterUploadData model,IotSubscribe iotGlobal,UpdateBalanceVO updateBalanceVO) throws Exception {
        //设置参数
        IotGasMeterCommand command = updateBalanceVO.getIotGasMeterCommand();
        IotGasMeterCommandDetail detail = updateBalanceVO.getIotGasMeterCommandDetail();
        String sendData = null;
        if(command==null||detail==null){
            UpdateBalanceAndPriceModel params = new UpdateBalanceAndPriceModel();
            params.setArchiveId(model.getArchiveId());
            params.setBusinessType(CommandType.DAILYSETTLEMENT.getCode());
            params.setDomainId(model.getDomainId());
            List<SetUpParamsterModel<SetUpParamsterValueModel>> list = new ArrayList<>();
            if(model.getGasMeterCode()!=null&&model.getCustomerCode()!=null&&null==updateBalanceVO.getPrice()){
                GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(model.getGasMeterCode(),model.getCustomerCode());
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
            sendData = JSONUtil.toJsonStr(params);
        }else{
            sendData = detail.getCommandParameter();
        }
        //向3.0发送更新余额指令
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request update balance faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.RESULT_DATA)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            error = res.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        if(command==null||detail==null){
            defendCommand(model,CommandType.DAILYSETTLEMENT,sendData,null,executeState.getCodeValue(),error);
        }else{
            updateCommand(executeState,res,command,detail,error);
        }
        return IotR.ok();
    }

    @Override
    public IotR readIotMeterEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        ParamsVO paramsVO = JSONObject.parseObject(data,ParamsVO.class);
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,paramsVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //设置参数
        DataSupplementModel params = new DataSupplementModel();
        params.setArchiveId(model.getArchiveId());
        params.setBusinessType(CommandType.PARAMETER.getCode());
        params.setDomainId(model.getDomainId());
        SetUpParamsterModel<SetUpParamsterReading> setParams = new SetUpParamsterModel<>();
        setParams.setParaType("READLOGS_DAILY");
        SetUpParamsterReading readParams = new SetUpParamsterReading();
        readParams.setDays(paramsVO.getDays());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        readParams.setStartDate(sdf.format(Date.from(paramsVO.getStartTime().atZone(ZoneId.systemDefault()).toInstant())));
        setParams.setParameter(readParams);
        params.setBusinessData(setParams);
        String sendData = JSONUtil.toJsonStr(params);
        //向3.0发送补抄指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request read Meter Data faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.RESULT_DATA)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            error = res.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令记录
        defendCommand(model,CommandType.READMETER,sendData,res.getJSONObject(HttpCodeConstant.RESULT_DATA)
                .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
        return IotR.ok();
    }

    @Override
    public IotR setDeviceParamsEvent(String data, String tenant) throws Exception {
        BaseContextHandler.setTenant(tenant);
        List<IotDeviceParams> iotDeviceParams = JSONObject.parseArray(data,IotDeviceParams.class);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(iotDeviceParams.get(0).getDomainId());
        for(IotDeviceParams iot:iotDeviceParams){
            IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                    .eq(IotGasMeterUploadData::getGasMeterNumber,iot.getGasMeterNumber())
                    .ne(IotGasMeterUploadData::getDeviceState,4));
            //过流报警使能,0：禁止；1：使能
            if(!StringUtil.isNullOrBlank(iot.getFlowoverEnable())){
                sendSetParams(model,iot,iotGlobal,"SETUP_FLOWOVER_ENABLE",iot.getFlowoverEnable());
            }
            //上报时间设置
            if(!StringUtil.isNullOrBlank(iot.getTimedParamMeter())){
                sendSetParams(model,iot,iotGlobal,"SETUP_TIMED_PARAMETER",iot.getTimedParamMeter());
            }
            //多天未上报权限关阀,0--禁止；1~255 表示天数。 默认：5天
            if(!StringUtil.isNullOrBlank(iot.getUnUploadClose())){
                sendSetParams(model,iot,iotGlobal,"SETUP_UNUPLOAD_CLOSEVALVEDAY",iot.getUnUploadClose());
            }
            //多天不用气关阀控制参数,0--禁止；1~255 表示天数
            if(!StringUtil.isNullOrBlank(iot.getValveNoUpload())){
                sendSetParams(model,iot,iotGlobal,"SETUP_VALVECLOSE_NOUSE",iot.getValveNoUpload());
            }
            //设置错峰时间间隔,单位秒，范围15~43。默认15
            if(!StringUtil.isNullOrBlank(iot.getTimeInterva())){
                sendSetParams(model,iot,iotGlobal,"SETUP_TIME_INTERVAL",iot.getTimeInterva());
            }
        }
        return IotR.ok();
    }


    public void sendSetParams(IotGasMeterUploadData model,IotDeviceParams iot,IotSubscribe iotGlobal,String paramType,String businessData) throws Exception {
        SetParamsModel setParamsModel = new SetParamsModel();
        setParamsModel.setArchiveId(model.getArchiveId());
        setParamsModel.setDomainId(iot.getDomainId());
        setParamsModel.setBusinessType("PARAMETER");
        SetUpParamster<String> params = new SetUpParamster<>();
        params.setParaType(paramType);
        params.setParameter(businessData);
        setParamsModel.setBusinessData(params);
        //设置物联网下发参数
        String sendData = JSONUtil.toJsonStr(setParamsModel);
        //向3.0发送补抄指令
        ExecuteState executeState = ExecuteState.UnExecute;
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,iot.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request set params faild,meterNo:"+iot.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.RESULT_DATA)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            executeState = ExecuteState.Error;
        }
    }

    /**
     * 数据上报
     * @param gasMeter
     * @param gasMeterData
     * @param dayUseGas
     * @param sDate
     * @param previous
     * @param isBc
     * @param i
     * @return
     */
    public UploadDataVO uploadReadMeterDataNB(GasMeter gasMeter, IotGasMeterUploadData gasMeterData, UseGasModel dayUseGas
            , LocalDateTime sDate,int i, GasMeterVersion gasMeterVersion, UploadDataVO uploadDataVO,List<ReadMeterDataIot> list,
                                              GasMeterInfo gasMeterInfo, ReportModel<ReportDeviceDataModel> deviceData){
        ReadMeterDataIot previous = uploadDataVO.getPrevious();
        boolean isBc = uploadDataVO.getIsBc();
        ReadMeterDataIot igd = new ReadMeterDataIot();
        igd.setGasMeterNumber(gasMeterData.getGasMeterNumber());
        igd.setDataTime(sDate);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = sDate.atZone(zone).toInstant();
        igd.setReadTime(Date.from(instant));
        igd.setDataStatus(1);
        igd.setFrozenGas(dayUseGas.getUseGasDetail().get(i));
        igd.setUseGasTypeId(gasMeter.getUseGasTypeId());
        igd.setGasMeterCode(gasMeterData.getGasMeterCode());
        igd.setProcessStatus(ProcessIotEnum.APPROVED);
        igd.setCurrentTotalGas(dayUseGas.getUseGasDetail().get(i));
        igd.setCustomerCode(gasMeterData.getCustomerCode());
        igd.setCustomerName(gasMeterData.getCustomerName());
        igd.setUseGasTypeName(gasMeter.getUseGasTypeName());
        igd.setGasMeterAddress(gasMeterData.getMeterAddress());
        igd.setReadMeterMonth(sDate.getMonthValue());
        igd.setMeterType(gasMeterVersion.getOrderSourceName());
        igd.setOrgId(gasMeter.getOrgId());
        igd.setOrgName(gasMeter.getOrgName());
        igd.setCompanyCode(gasMeter.getCompanyCode());
        igd.setCompanyName(gasMeter.getCompanyName());
        igd.setIsFirst(0);
        igd.setIsCreateArrears(0);
        igd.setDataType(0);
        igd.setReadMeterYear(sDate.getYear());
        igd.setRecordTime(Date.from(instant));
        if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersion.getOrderSourceName())){
            if(i==0){
                //获取前一条数据
                previous = readMeterDataIotService.getPreviousData(gasMeterData.getGasMeterCode(), gasMeterData.getGasMeterNumber(),gasMeterData.getCustomerCode(),sDate);
                uploadDataVO.setPrevious(previous);
                if(null == previous){
                    igd.setIsFirst(1);
                    igd.setLastTotalGas(gasMeterData.getInitialMeasurementBase());
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                    if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                    uploadDataVO.setIsBc(true);
                    //纍計用氣量
                    gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                }else if(sDate.toLocalDate().compareTo((previous.getDataTime().toLocalDate().plusDays(1)))==0){
                    igd.setLastTotalGas(previous.getCurrentTotalGas());
                    if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(previous.getCurrentTotalGas()));
                    uploadDataVO.setIsBc(true);
                    //纍計用氣量
                    gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                }else{
                    //数据多天未上报处理
                    long days = (sDate.toLocalDate().toEpochDay())-(previous.getDataTime().toLocalDate().toEpochDay());
                    if(previous.getIsCreateArrears()==0||(previous.getDataTime().toLocalDate().getYear()!=sDate.getYear()
                            ||previous.getDataTime().toLocalDate().getMonthValue()!=sDate.getMonthValue())){
                        uploadDataVO.setIsBc(true);
                        igd.setLastTotalGas(previous.getCurrentTotalGas());
                        igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                        if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                        //纍計用氣量
                        gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                    }
                }
            }else{
                if(isBc){
                    igd.setLastTotalGas(dayUseGas.getUseGasDetail().get(i-1));
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(dayUseGas.getUseGasDetail().get(i-1)));
                    if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                    //纍計用氣量
                    gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                }else{
                    if(list.size()>0){
                        igd.setLastTotalGas((list.get(list.size()-1).getCurrentTotalGas()));
                        uploadDataVO.setIsBc(true);
                        igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                        if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                        //纍計用氣量
                        gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                    }else{
                        previous = readMeterDataIotService.getPreviousData(gasMeterData.getGasMeterCode(), gasMeterData.getGasMeterNumber(),gasMeterData.getCustomerCode(),sDate);
                        if(previous!=null){
                            if(previous.getIsCreateArrears()==0||(previous.getDataTime().toLocalDate().getYear()!=sDate.getYear()
                                    ||previous.getDataTime().toLocalDate().getMonthValue()!=sDate.getMonthValue())){
                                igd.setLastTotalGas(previous.getCurrentTotalGas());
                                uploadDataVO.setIsBc(true);
                                igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                                if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                                //纍計用氣量
                                gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                            }
                        }else{
                            igd.setLastTotalGas(gasMeterData.getInitialMeasurementBase());
                            igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                            igd.setIsFirst(1);
                            uploadDataVO.setIsBc(true);
                            if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
                            //纍計用氣量
                            gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
                        }
                    }
                }
            }
        }else if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
            if(i==0){
                //获取前一条数据
                previous = readMeterDataIotService.getPreviousData(gasMeterData.getGasMeterCode(), gasMeterData.getGasMeterNumber(),gasMeterData.getCustomerCode(),sDate);
                if(null == previous){
                    igd.setLastTotalGas(gasMeterData.getInitialMeasurementBase());
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                    igd.setIsFirst(1);
                }else if(sDate.toLocalDate().compareTo((previous.getDataTime().toLocalDate().plusDays(1)))==0){
                    igd.setLastTotalGas(previous.getCurrentTotalGas());
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(previous.getCurrentTotalGas()));
                }else{
                    //数据多天未上报处理
                    long days = (sDate.toLocalDate().toEpochDay())-(previous.getDataTime().toLocalDate().toEpochDay());
                    igd.setLastTotalGas(previous.getCurrentTotalGas());
                    igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
                }
            }else{
                if(list.size()>0){
                    igd.setLastTotalGas(dayUseGas.getUseGasDetail().get(i-1));
                }else{
                    previous = readMeterDataIotService.getPreviousData(gasMeterData.getGasMeterCode(), gasMeterData.getGasMeterNumber(),gasMeterData.getCustomerCode(),sDate);
                    if(null==previous){
                        igd.setLastTotalGas(gasMeterData.getInitialMeasurementBase());
                        igd.setIsFirst(1);
                    }else{
                        igd.setLastTotalGas(dayUseGas.getUseGasDetail().get(i-1));
                    }
                }
                igd.setMonthUseGas(igd.getCurrentTotalGas().subtract(igd.getLastTotalGas()));
            }
            //纍計用氣量
            gasMeterData.setTotalUseGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));
            if(null!=gasMeterInfo){gasMeterInfo.setMeterTotalGas(BigDecimal.valueOf(deviceData.getData().getDeviceData().getTotalUseGas()));}
        }
        uploadDataVO.setIgd(igd);
        return uploadDataVO;
    }

    public SetUpParamsterModel<SetUpParamsterValueModel> getParamsList(String paraType,String value){
        SetUpParamsterModel<SetUpParamsterValueModel> paramsModel = new SetUpParamsterModel<>();
        paramsModel.setParaType(paraType);
        SetUpParamsterValueModel s = new SetUpParamsterValueModel();
        s.setValue(value);
        paramsModel.setParameter(s);
        return paramsModel;
    }

    /**
     * 保存历史数据
     * @param deviceData 保存历史数据
     * @param dataType 数据类型
     * @param data 数据
     */
    private void saveDeviceHistoryData(ReportModel deviceData,String dataType,String data){
        IotDeviceDataHistory iotDeviceDataHistory = new IotDeviceDataHistory();
        iotDeviceDataHistory.setDataType(dataType);
        iotDeviceDataHistory.setDeviceId(deviceData.getDeviceId());
        iotDeviceDataHistory.setDeviceData(data);
        iotDeviceDataHistoryService.save(iotDeviceDataHistory);
    }

    /**
     * 创建报警信息
     * @param deviceId 设备id
     * @param alarmType 报警类型
     * @param alarmInfo 报警信息
     * @return
     */
    private IotAlarm getAlarmEntity(String deviceId,String alarmType,String alarmInfo){
        IotAlarm iotAlarm =new IotAlarm();
        iotAlarm.setDeviceId(deviceId);
        iotAlarm.setAlarmType(alarmType);
        iotAlarm.setAlarmContent(alarmInfo);
        return iotAlarm;
    }

    /**
     * 更新指令
     * @param executeState
     * @param res
     * @param command
     * @param detail
     */
    private void updateCommand(ExecuteState executeState,JSONObject res,IotGasMeterCommand command,IotGasMeterCommandDetail detail,String error){
        if(executeState==ExecuteState.UnExecute){
            command.setTransactionNo(res.getJSONObject(HttpCodeConstant.RESULT_DATA)
                    .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
            detail.setTransactionNo(res.getJSONObject(HttpCodeConstant.RESULT_DATA)
                    .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
        }else{
            command.setExecuteResult(executeState.getCodeValue());
            detail.setExecuteResult(executeState.getCodeValue());
            detail.setCommandStatus(executeState.getCodeValue());
            detail.setCommandStage((executeState.getCodeValue()==0? CommandState.WaitSend.getCodeValue():
                    executeState.getCodeValue()));
            detail.setErrorDesc(error);
        }
        iotGasMeterCommandService.updateById(command);
        iotGasMeterCommandDetailService.updateById(detail);
    }

    /**
     * 指令维护
     * @param deviceData 设备信息
     * @param commandType 指令类型
     * @param commandData 指令信息
     * @param transactionNo 流水号
     * @param execStatus 执行状态
     */
    private void defendCommand(IotGasMeterUploadData deviceData,CommandType commandType,String commandData,
                               String transactionNo,int execStatus,String error){
        //保存指令信息
        IotGasMeterCommand command = new IotGasMeterCommand();
        command.setMeterNumber(deviceData.getGasMeterNumber());
        command.setCommandType(commandType);
        command.setCommandParameter(commandData);
        command.setGasMeterCode(deviceData.getGasMeterCode());
        command.setExecuteResult(execStatus);
        command.setTransactionNo(transactionNo);
        command.setOrgId(deviceData.getOrgId());
        command.setOrgName(deviceData.getOrgName());
        iotGasMeterCommandService.save(command);
        //保存指令详情
        IotGasMeterCommandDetail commandDetail = new IotGasMeterCommandDetail();
        commandDetail.setTransactionNo(transactionNo);
        commandDetail.setCommandId(command.getId());
        commandDetail.setCommandType(commandType);
        commandDetail.setExecuteTime(LocalDateTime.now());
        commandDetail.setCommandStatus(execStatus);
        commandDetail.setCommandParameter(commandData);
        commandDetail.setCustomerCode(deviceData.getCustomerCode());
        commandDetail.setGasMeterCode(deviceData.getGasMeterCode());
        commandDetail.setCommandStage((execStatus==0? CommandState.WaitSend.getCodeValue():
                execStatus));
        commandDetail.setMeterNumber(deviceData.getGasMeterNumber());
        commandDetail.setExecuteResult(execStatus);
        commandDetail.setErrorDesc(error);
        commandDetail.setOrgId(deviceData.getOrgId());
        commandDetail.setOrgName(deviceData.getOrgName());
        iotGasMeterCommandDetailService.save(commandDetail);
    }

    /**
     * 阀门控制
     * @param gasMeterNumber 气表编号
     * @param controlCode 控制码
     * @param commandType 指令类型
     * @param iotGlobal 全局参数
     * @return
     * @throws Exception
     */
    private IotGasMeterUploadData valveOptions(ValveControlVO valveControlVO, CommandType commandType,
                                               IotSubscribe iotGlobal) throws Exception {
        //设置阀控参数
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,valveControlVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotGasMeterCommand command = valveControlVO.getIotGasMeterCommand();
        IotGasMeterCommandDetail detail = valveControlVO.getIotGasMeterCommandDetail();
        String sendData = null;
        if(null==command||null==detail){
            ValveControlModel valveModel = new ValveControlModel();
            valveModel.setArchiveId(model.getArchiveId());
            valveModel.setDomainId(model.getDomainId());
            valveModel.setBusinessType(CommandType.VALVECONTROL.getCode());
            ValveControlBus bus = new ValveControlBus();
            bus.setControlCode(valveControlVO.getControlCode());
            valveModel.setBusinessData(bus);
            sendData = JSONUtil.toJsonStr(valveModel);
        }else {
           sendData = detail.getCommandParameter();
        }
        //向3.0发送阀控指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject valveRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(valveRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request valve faild,meterNo:"+model.getGasMeterNumber()+"error:"
                    +valveRes.getString(HttpCodeConstant.RESULT_DATA)+"code:"+valveRes.getString(HttpCodeConstant.RESULT_CODE));
            error = valveRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令维护
        if(command==null||detail==null){
            if(GMISConstant.DEVICE_VALVE_CLOSE_OPEN.equals(valveControlVO.getControlCode())){
                defendCommand(model,CommandType.OPENACCOUNT,sendData,valveRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
            }else{
                defendCommand(model,CommandType.CLOSEVALVE,sendData,valveRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                        .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
            }
        }else{
            updateCommand(executeState,valveRes,command,detail,error);
        }
        return model;
    }

    /**
     * 批量阀控
     * @param gasMeterNumberList
     * @param controlCode
     * @param commandType
     * @param iotGlobal
     * @param domainId
     * @return
     * @throws Exception
     */
    private List<IotGasMeterUploadData> valveBatOptions(List<String> gasMeterNumberList,String controlCode, CommandType commandType,
                                               IotSubscribe iotGlobal,String domainId) throws Exception {
        //设置阀控参数
        List<IotGasMeterUploadData> modelList = iotGasMeterUploadDataService.list(Wraps.<IotGasMeterUploadData>lbQ()
                .in(IotGasMeterUploadData::getGasMeterNumber,gasMeterNumberList)
                .ne(IotGasMeterUploadData::getDeviceState,4));
        List<ValveBatBus> list = new ArrayList<>();
        ValveBatControlModel valve = new ValveBatControlModel();
        valve.setDomainId(domainId);
        valve.setBusinessType(CommandType.VALVECONTROL.getCode());
        modelList.stream().forEach(e->{
            ValveBatBus valveBatBus = new ValveBatBus();
            valveBatBus.setArchiveId(e.getArchiveId());
            valveBatBus.setControlCode(controlCode);
            list.add(valveBatBus);
        });
        valve.setData(list);
        String sendData = JSONUtil.toJsonStr(valve);
        //向3.0发送阀控指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject valveRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/batchbusiness",sendData,
                true,domainId);
        if(!HttpCodeConstant.SUCCESS.equals(valveRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request batValve faild error:"
                    +valveRes.getString(HttpCodeConstant.RESULT_DATA)+"code:"+valveRes.getString(HttpCodeConstant.RESULT_CODE));
            error = valveRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令记录
        for(IotGasMeterUploadData iot:modelList){
             defendCommand(iot,commandType,sendData,valveRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
        }
        return modelList;
    }

    private IotR changePriceSingle(PriceChangeVO priceChangeVO) throws Exception {
        IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,priceChangeVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //设置调价参数
        PriceChangeBus bus = new PriceChangeBus();
        bus.setAdjDay(priceChangeVO.getAdjDay());
        bus.setAdjMonth(priceChangeVO.getAdjMonth());
        bus.setCurGasTypeID(priceChangeVO.getCurGasTypeNum());
        //bus.setCurGasTypeID(1);
        if(bus.getNewGasTypeID()!=null){
            bus.setNewGasTypeID(priceChangeVO.getNewGasTypeNum());
        }else{
            bus.setNewGasTypeID(0);
        }
        bus.setCycle(priceChangeVO.getCycle());
        if(priceChangeVO.getEnddate() == null){
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
            bus.setEnddate(sdf.parse(GMISConstant.GMIS_DEFAULT_DATE).getTime());
        }else{
            bus.setEnddate(priceChangeVO.getEnddate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
        }
        bus.setStartdate(priceChangeVO.getStartdate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
        if(priceChangeVO.getIsClear()==0){bus.setIsClear(false);}
        else{bus.setIsClear(true);}
        bus.setPriceNum(priceChangeVO.getPriceNum());
        bus.setInitAmount(priceChangeVO.getInitAmount().doubleValue());
        bus.setTieredNum(priceChangeVO.getTiered().size());
        List<PriceVO> list = priceChangeVO.getTiered();
        List<TieredModel> tieredModels = new ArrayList<>();
        list.stream().forEach(e->{
            TieredModel node = new TieredModel();
            node.setTieredNum(e.getTieredNum());
            node.setTieredPrice(e.getTieredPrice());
            node.setTieredValue(e.getTieredValue());
            tieredModels.add(node);
        });
        bus.setTiered(tieredModels);
        PriceChangeModel priceChangeModel =new PriceChangeModel();
        priceChangeModel.setBusinessData(bus);
        priceChangeModel.setArchiveId(model.getArchiveId());
        priceChangeModel.setBusinessType(CommandType.MODIFYPRICE.getCode());
        priceChangeModel.setDomainId(model.getDomainId());
        String sendData = JSONUtil.toJsonStr(priceChangeModel);
        //向3.0发送表端结算表的充值指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request recharge faild,meterNo:"+model.getGasMeterNumber()+" .error:"
                    +res.getString(HttpCodeConstant.RESULT_DATA)+" ,code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            error = res.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令记录
        defendCommand(model,CommandType.MODIFYPRICE,sendData,res.getJSONObject(HttpCodeConstant.RESULT_DATA)
                .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue(),error);
        return IotR.ok();
    }

    private IotR changePriceBat(List<PriceChangeVO> priceChangeList) throws Exception{
        String domainId = priceChangeList.get(0).getDomainId();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //设置批量调价参数
        ChangePriceBatModel priceBatModel = new ChangePriceBatModel();
        priceBatModel.setBusinessType(CommandType.MODIFYPRICE.getCode());
        priceBatModel.setDomainId(domainId);
        List<ChangePriceBatVO> listBat = new ArrayList<>();
        List<IotGasMeterUploadData> modelList = new ArrayList<>();
        for(PriceChangeVO priceChangeVO:priceChangeList){
            IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                    .eq(IotGasMeterUploadData::getGasMeterNumber,priceChangeVO.getGasMeterNumber())
                    .ne(IotGasMeterUploadData::getDeviceState,4));
            if(null==model){continue;}
            modelList.add(model);
            ChangePriceBatVO bus = new ChangePriceBatVO();
            bus.setArchiveID(model.getArchiveId());
            bus.setAdjDay(priceChangeVO.getAdjDay());
            bus.setAdjMonth(priceChangeVO.getAdjMonth());
            bus.setCurGasTypeID(priceChangeVO.getCurGasTypeNum());
            if(bus.getNewGasTypeID()!=null){
                bus.setNewGasTypeID(priceChangeVO.getNewGasTypeNum());
            }else{
                bus.setNewGasTypeID(0);
            }
            bus.setCycle(priceChangeVO.getCycle());
            if(priceChangeVO.getEnddate()== null){
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
                bus.setEnddate(sdf.parse(GMISConstant.GMIS_DEFAULT_DATE).getTime());
            }else{
                bus.setEnddate(priceChangeVO.getEnddate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
            }
            bus.setStartdate(priceChangeVO.getStartdate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
            if(priceChangeVO.getIsClear()==0){bus.setIsClear(false);}
            else{bus.setIsClear(true);}
            bus.setPriceNum(priceChangeVO.getPriceNum());
            bus.setInitAmount(priceChangeVO.getInitAmount().doubleValue());
            bus.setTieredNum(priceChangeVO.getTiered().size());
            List<PriceVO> list = priceChangeVO.getTiered();
            List<TieredModel> tieredModels = new ArrayList<>();
            list.stream().forEach(e->{
                TieredModel node = new TieredModel();
                node.setTieredNum(e.getTieredNum());
                node.setTieredPrice(e.getTieredPrice());
                node.setTieredValue(e.getTieredValue());
                tieredModels.add(node);
            });
            bus.setTiered(tieredModels);
            listBat.add(bus);
        }
        priceBatModel.setData(listBat);
        String sendData = JSONUtil.toJsonStr(priceBatModel);
        //向3.0发送表端结算表的充值指令
        ExecuteState executeState = ExecuteState.UnExecute;
        String error = null;
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/batchbusiness",sendData,
                true,domainId);
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request rechargeBat faild,error:"
                    +res.getString(HttpCodeConstant.MESSAGE)+" ,code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            error = res.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        //指令记录
        if(modelList.size()>0){
            //批量调价时 返回对象res中resultData是一个数组
            Map<String, String> tempMap = new HashMap<>();
            JSONArray jsonArray = res.getJSONArray(HttpCodeConstant.RESULT_DATA);
            for(int i=0; i< jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                tempMap.put(jsonObject.getString(GMISConstant.GMIS_DEVICE_ARCHIVE_ID),
                        jsonObject.getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
            }
            for(IotGasMeterUploadData model:modelList){
                defendCommand(model,CommandType.MODIFYPRICE,sendData,tempMap.get(model.getArchiveId()),
                        executeState.getCodeValue(),error);
            }
        }
        return IotR.ok();
    }

    public IotR deviceAddEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception{
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送加入设备指令
        ExecuteState executeState = ExecuteState.Success;
        JSONObject result = iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"domain/adddevice",data,true,domainId);
        if(!HttpCodeConstant.SUCCESS.equals(result.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request addDevice faild, error:"
                    +result.getString(HttpCodeConstant.MESSAGE)+", code:"+result.getString(HttpCodeConstant.RESULT_CODE));
            executeState = ExecuteState.Error;
        }
        if(executeState==ExecuteState.Success){
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(1);
            iotGasMeterCommandDetailService.updateById(detail);
            DeviceModel deviceModel = JSONObject.parseObject(data,DeviceModel.class);
            if(deviceModel!=null){
                IotGasMeterUploadData model = iotGasMeterUploadDataService.getOne(Wraps.<IotGasMeterUploadData>lbQ()
                        .eq(IotGasMeterUploadData::getGasMeterNumber, deviceModel.getData().get(0).getDeviceId())
                        .ne(IotGasMeterUploadData::getDeviceState,4));
                DeviceDataVO dataVO = new DeviceDataVO();
                dataVO.setGasMeterNumber(deviceModel.getData().get(0).getDeviceId());
                dataVO.setGasMeterVersionName(model.getGasMeterVersionName());
                dataVO.setDirection(model.getDirection());
                dataVO.setDeviceType(0);
                register(model,dataVO,domainId,iotGlobal);
            }
        }
        return IotR.ok();
    }

    public IotR registerDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception{
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送注册指令
        JSONObject registerRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/registerdevice",data,
                true,domainId);
        detail.setCommandStage(CommandState.SendToNet.getCodeValue());
        detail.setCommandStatus(1);
        if(!HttpCodeConstant.SUCCESS.equals(registerRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request registerDevice faild, error:"
                    +registerRes.getString(HttpCodeConstant.MESSAGE)+", code:"+registerRes.getString(HttpCodeConstant.RESULT_CODE));
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(11);
        }
        iotGasMeterCommandDetailService.updateById(detail);
        return IotR.ok();
    }

    public IotR updateDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception{
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送注册指令
        JSONObject updateRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/updatedevice",data,
                true,domainId);
        detail.setCommandStage(CommandState.SendToNet.getCodeValue());
        detail.setCommandStatus(1);
        if(!HttpCodeConstant.SUCCESS.equals(updateRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request updateDevice faild, error:"
                    +updateRes.getString(HttpCodeConstant.MESSAGE)+", code:"+updateRes.getString(HttpCodeConstant.RESULT_CODE));
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(11);
        }
        iotGasMeterCommandDetailService.updateById(detail);
        return IotR.ok();
    }

    public IotR removeDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception{
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送注册指令
        JSONObject rdmRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/deletedevice",data,
                true,domainId);
        detail.setCommandStage(CommandState.SendToNet.getCodeValue());
        detail.setCommandStatus(1);
        if(!HttpCodeConstant.SUCCESS.equals(rdmRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request updateDevice faild, error:"
                    +rdmRes.getString(HttpCodeConstant.MESSAGE)+", code:"+rdmRes.getString(HttpCodeConstant.RESULT_CODE));
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(11);
        }
        iotGasMeterCommandDetailService.updateById(detail);
        return IotR.ok();
    }

    public IotR removeDomainEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception{
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送注册指令
        JSONObject rdRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"domain/removedevice",data,
                true,domainId);
        detail.setCommandStage(CommandState.SendToNet.getCodeValue());
        detail.setCommandStatus(1);
        if(!HttpCodeConstant.SUCCESS.equals(rdRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request updateDevice faild, error:"
                    +rdRes.getString(HttpCodeConstant.MESSAGE)+", code:"+rdRes.getString(HttpCodeConstant.RESULT_CODE));
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(11);
        }
        iotGasMeterCommandDetailService.updateById(detail);
        return IotR.ok();
    }

    @Override
    public IotR rechargeEventRetry(String domainId, IotGasMeterCommandDetail detail) throws Exception {
        String data = detail.getCommandParameter();
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //向3.0发送充值指令
        JSONObject rechargeRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",data,
                true,domainId);
        detail.setCommandStage(CommandState.SendToNet.getCodeValue());
        detail.setCommandStatus(1);
        ExecuteState executeState = ExecuteState.UnExecute;
        if(!HttpCodeConstant.SUCCESS.equals(rechargeRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request recharge faild, error:"
                    +rechargeRes.getString(HttpCodeConstant.MESSAGE)+", code:"+rechargeRes.getString(HttpCodeConstant.RESULT_CODE));
            executeState = ExecuteState.Error;
            detail.setCommandStage(CommandState.SendToNet.getCodeValue());
            detail.setCommandStatus(11);
        }
        if(executeState==ExecuteState.UnExecute){
            RechargeModel rechargeModel = JSONObject.parseObject(data,RechargeModel.class);
            //查询户表
            CustomerGasMeterRelated customerParams = new CustomerGasMeterRelated();
            customerParams.setGasMeterCode(detail.getGasMeterCode());
            customerParams.setDataStatus(DataStatusEnum.NORMAL.getValue());
            List<CustomerGasMeterRelated> relateds = customerGasMeterRelatedBizApi.query(customerParams).getData();
            GasMeterInfo gasMeterInfo =null;
            if(relateds.size()>0){
                CustomerGasMeterRelated customerGasMeterRelated = relateds.get(0);
                gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(customerGasMeterRelated.getGasMeterCode(),
                        customerGasMeterRelated.getCustomerCode());
                if(gasMeterInfo==null){
                    throw new BizException("未获取到户表账户");
                }
            }
            GasMeterInfo update = GasMeterInfo.builder().id(gasMeterInfo.getId()).build();
            //更新充值次数
            update.setTotalRechargeMeterCount((rechargeModel.getBusinessData().getRechargeTimes()));
//                    gasMeterInfo.setTotalChargeCount((gasMeterInfo.getTotalChargeCount()+1));
            if(gasMeterInfo.getValue1()!=null&&gasMeterInfo.getValue1().compareTo(BigDecimal.ZERO)==0
                    && Objects.nonNull(gasMeterInfo.getValue2())){
                update.setValue1(BigDecimal.valueOf(rechargeModel.getBusinessData().getRechargeAmount()));
                update.setValue2(gasMeterInfo.getValue2());
                update.setValue3(gasMeterInfo.getValue3());
            }else{
                update.setValue1(BigDecimal.valueOf(rechargeModel.getBusinessData().getRechargeAmount()));
                update.setValue2(gasMeterInfo.getValue1());
                update.setValue3(gasMeterInfo.getValue2());
            }
            gasMeterInfoService.updateById(update);
            detail.setTransactionNo(rechargeRes.getJSONObject(HttpCodeConstant.RESULT_DATA)
                    .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO));
        }
        iotGasMeterCommandDetailService.updateById(detail);
        return IotR.ok();
    }

    public IotR register(IotGasMeterUploadData model,DeviceDataVO dataVO,String domainId,IotSubscribe iotGlobal) throws Exception {
        //设置注册参数
        GasMeterVersion gasMeterVersion = gasMeterVersionBizApi.get(model.getGasMeterVersionId()).getData();
        if(null==gasMeterVersion){throw new BizException(model.getGasMeterNumber()+"未设置版号");}
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceId(dataVO.getGasMeterNumber());
        deviceData.setCustomerName(dataVO.getGasMeterNumber());
        deviceData.setBaseNumber(BigDecimal.ZERO);
        deviceData.setBaseType(dataVO.getSpecification()==null?"1.6":dataVO.getSpecification());
        deviceData.setCustomerCode(dataVO.getGasMeterNumber());
        deviceData.setTelephone("13999999999");
        deviceData.setCustomerType(0);
        deviceData.setAddress("AddrDes");
        deviceData.setIgnitionTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        deviceData.setInstallationTime(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        deviceData.setUserCode(dataVO.getGasMeterNumber());
        if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equalsIgnoreCase(gasMeterVersion.getOrderSourceName())
                ||OrderSourceNameEnum.CENTER_RECHARGE.getCode().equalsIgnoreCase(gasMeterVersion.getOrderSourceName())){
            deviceData.setControlMode("SERVICECTRL");
            deviceData.setVersionNo(GMISConstant.GMIS_DEVICE_VERSION_NB21_NO);
        }else if(OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equalsIgnoreCase(gasMeterVersion.getOrderSourceName())){
            deviceData.setControlMode("METERCTRL");
            deviceData.setVersionNo(GMISConstant.GMIS_DEVICE_VERSION_NB20E_NO);
        }
        GISModel gis = new GISModel();
        gis.setLatitude(0d);
        gis.setLongitude(0d);
        gis.setType(GMISConstant.GMIS_WGS_84);
        deviceData.setGIS(gis);
        if(GMISConstant.DEVICE_GAS_DIRECTION_RIGHT.equals(dataVO.getDirection())){deviceData.setIntakeDrection(1);}
        else{deviceData.setIntakeDrection(0);}
        RegisterUserModel registerUserModel = new RegisterUserModel();
        registerUserModel.setDeviceData(deviceData);
        if(dataVO.getDeviceType()==0){registerUserModel.setDeviceType(GMISConstant.GMIS_GAS_METER_TYPE);}
        else if(dataVO.getDeviceType()==1){registerUserModel.setDeviceType(GMISConstant.GMIS_LLJ_METER_TYPE);}
        else if(dataVO.getDeviceType()==2){registerUserModel.setDeviceType(GMISConstant.GMIS_WM_METER_TYPE);}
        registerUserModel.setDomainId(domainId);
        String sendRegisterData = JSONUtil.toJsonStr(registerUserModel);
        //向3.0发送注册指令
        JSONObject registerRes= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/registerdevice",sendRegisterData,
                true,domainId);
        ExecuteState executeState = ExecuteState.Success;
        String error = null;
        if(!HttpCodeConstant.SUCCESS.equals(registerRes.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request registerDevice faild,meterNo:"+dataVO.getGasMeterNumber()+", error:"
                    +registerRes.getString(HttpCodeConstant.RESULT_DATA)+", code:"+registerRes.getString(HttpCodeConstant.RESULT_CODE));
            error = registerRes.getString(HttpCodeConstant.RESULT_DATA);
            executeState = ExecuteState.Error;
        }
        if(executeState == ExecuteState.Success){
            model.setArchiveId(registerRes.getJSONObject(HttpCodeConstant.RESULT_DATA).getString(GMISConstant.GMIS_DEVICE_ARCHIVE_ID));
            model.setDeviceState(DeviceStage.Active.getCodeValue());
            //更新设备列表
            iotGasMeterUploadDataService.updateById(model);
        }
        defendCommand(model,CommandType.REGISTER,sendRegisterData,null,executeState.getCodeValue(),error);
        return IotR.ok();
    }

    /**
     * 后付费表是否补数据
     * @param gasMeter
     * @param gasMeterData
     * @param sDate
     * @return
     */
    public boolean isBC(GasMeter gasMeter,IotGasMeterUploadData gasMeterData,LocalDateTime sDate){
        GasmeterArrearsDetail arrearsParams = new GasmeterArrearsDetail();
        arrearsParams.setGasmeterCode(gasMeter.getGasCode());
        arrearsParams.setCustomerCode(gasMeterData.getCustomerCode());
        String monthStr = Integer.toString(sDate.toLocalDate().getMonthValue());
        if(monthStr.length()==1){monthStr="0"+monthStr;}
        String readMonth = sDate.toLocalDate().getYear()+"-"+sDate.toLocalDate().getMonthValue();
        arrearsParams.setReadmeterMonth(readMonth);
        boolean isBc = gasmeterArrearsDetailBizApi.checkExits(arrearsParams);
        if(isBc){
            return false;
        }else{
            return true;
        }
    }
}

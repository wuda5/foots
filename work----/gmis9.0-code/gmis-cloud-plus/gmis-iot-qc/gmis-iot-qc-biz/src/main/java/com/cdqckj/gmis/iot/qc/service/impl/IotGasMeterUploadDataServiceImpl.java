package com.cdqckj.gmis.iot.qc.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.ErrorCode;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.iot.qc.dao.IotGasMeterUploadDataMapper;
import com.cdqckj.gmis.iot.qc.dto.ClearBusModel;
import com.cdqckj.gmis.iot.qc.dto.ClearModel;
import com.cdqckj.gmis.iot.qc.dto.MessageDTO;
import com.cdqckj.gmis.iot.qc.entity.*;
import com.cdqckj.gmis.iot.qc.enumeration.CommandState;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import com.cdqckj.gmis.iot.qc.enumeration.DeviceStage;
import com.cdqckj.gmis.iot.qc.enumeration.ExecuteState;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.HttpCodeConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotRestUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.MQUtils;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandDetailService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterUploadDataService;
import com.cdqckj.gmis.iot.qc.service.MessageService;
import com.cdqckj.gmis.iot.qc.vo.*;
import com.cdqckj.gmis.mq.producer.utils.ProducerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 业务实现类
 * 物联网气表上报数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotGasMeterUploadDataServiceImpl extends SuperServiceImpl<IotGasMeterUploadDataMapper, IotGasMeterUploadData> implements IotGasMeterUploadDataService {
    @Autowired
    private ProducerUtils producerUtils;
    @Autowired
    private MessageService messageService;
    @Autowired
    private IotCacheUtil iotCacheUtil;
    @Autowired
    private IotRestUtil iotRestUtil;
    @Autowired
    private IotGasMeterCommandDetailService iotGasMeterCommandDetailService;
    @Autowired
    private IotGasMeterCommandService iotGasMeterCommandService;
    @Autowired
    private IotGasMeterUploadDataService iotGasMeterUploadDataService;

    @Override
    public IotR addMeter(String domainId, List<DeviceDataVO> deviceDataVOList) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        for(DeviceDataVO e:deviceDataVOList){
            if(e.getGasMeterCode()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+e.getGasMeterNumber());}
            if(e.getGasMeterNumber()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+e.getGasMeterNumber());}
            if(e.getGasMeterModelId()==null){throw new  BizException(ErrorCode.METERTYPE_NOTEXIST.getDesc()+e.getGasMeterNumber());}
            IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
            checkParams.setGasMeterNumber(e.getGasMeterNumber());
            if(check(checkParams)){throw new  BizException(ErrorCode.METERNO_EXIST.getDesc()+e.getGasMeterNumber());}
            e.setDomainId(domainId);
        }
        String data = JSONObject.toJSONString(deviceDataVOList);
        //往mq里面加入新增设备信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.DeviceAdd, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR updateMeter(String domainId, UpdateDeviceVO updateDeviceVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(updateDeviceVO.getGasMeterNumber()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+updateDeviceVO.getGasMeterNumber());}
        IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
        checkParams.setGasMeterNumber(updateDeviceVO.getGasMeterNumber());
        if(!check(checkParams)){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+updateDeviceVO.getGasMeterNumber());}
        updateDeviceVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(updateDeviceVO);
        //往mq里面加入修改设备信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.DeviceModify, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR registerMeter(String domainId, RegisterDeviceVO registerDeviceVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(registerDeviceVO.getGasMeterNumber()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+registerDeviceVO.getGasMeterNumber());}
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,registerDeviceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+registerDeviceVO.getGasMeterNumber());}
        if(!model.getDeviceState().equals(DeviceStage.Inactive.getCodeValue())){throw new  BizException(ErrorCode.DEVICE_STATE_REGISTER_ERROR.getDesc()+registerDeviceVO.getGasMeterNumber());}
        registerDeviceVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(registerDeviceVO);
        //往mq里面加入注册设备信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.DeviceRegistry, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR removeMeter(String domainId, RemoveDeviceVO removeDeviceVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(removeDeviceVO.getGasMeterNumber())){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+removeDeviceVO.getGasMeterNumber());}
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,removeDeviceVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+removeDeviceVO.getGasMeterNumber());}
        if(model.getDeviceState().equals(DeviceStage.Closed.getCodeValue())){throw new  BizException(ErrorCode.DEVICE_STAGE_IS_STOP.getDesc()+removeDeviceVO.getGasMeterNumber());}
        removeDeviceVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(removeDeviceVO);
        //往mq里面加入移除设备信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.DeviceRemove, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR openAccount(String domainId, OpenAccountVO openAccountVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(openAccountVO.getGasMeterNumber())){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+openAccountVO.getGasMeterNumber());}
        IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
        checkParams.setGasMeterNumber(openAccountVO.getGasMeterNumber());
        if(!check(checkParams)){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+openAccountVO.getGasMeterNumber());}
        openAccountVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(openAccountVO);
        //往mq里面加入开户信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.OpenAccount, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR valveControl(String domainId, ValveControlVO valveControlVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(valveControlVO.getGasMeterNumber())){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+valveControlVO.getGasMeterNumber());}
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,valveControlVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+valveControlVO.getGasMeterNumber());}
        if(model.getDeviceState().equals(DeviceStage.UnKnown.getCodeValue())
                ||model.getDeviceState().equals(DeviceStage.Closed.getCodeValue())
                ||model.getDeviceState().equals(DeviceStage.Inactive.getCodeValue())){throw new  BizException(ErrorCode.DEVICE_VALVE_IS_FAIL.getDesc()+valveControlVO.getGasMeterNumber());}
        valveControlVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(valveControlVO);
        //往mq里面加入阀控信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ValveControl, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR valveBatControl(String domainId, List<ValveControlVO> valveControlVOList) throws Exception {
//        if(StringUtil.isNullOrBlank(domainId)){return IotR.error(ErrorCode.DOMAIN_ID_NOTEXIST);}
//        if(StringUtil.isNullOrBlank(valveControlVO.getGasMeterNumber())){return IotR.error(ErrorCode.METERNO_NOTEXIST);}
//        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
//                .eq(IotGasMeterUploadData::getGasMeterNumber,valveControlVO.getGasMeterNumber()));
//        if(model==null){return IotR.error(ErrorCode.METERNO_NOTEXIST);}
//        if(model.getDeviceState().equals(DeviceStage.UnKnown.getCodeValue())
//                ||model.getDeviceState().equals(DeviceStage.Closed.getCodeValue())
//                ||model.getDeviceState().equals(DeviceStage.Inactive.getCodeValue())){IotR.ok(ErrorCode.SUCCESS);}
//        valveControlVO.setDomainId(domainId);
//        String data = JSONObject.toJSONString(valveControlVO);
        //往mq里面加入阀控信息
//        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
//                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ValveControl, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR recharge(String domainId, RechargeVO rechargeVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(rechargeVO.getGasMeterNumber())){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+rechargeVO.getGasMeterNumber());}
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,rechargeVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+rechargeVO.getGasMeterNumber());}
        if(!DeviceStage.Enabled.getCodeValue().equals(model.getDeviceState())){
           throw new  BizException(ErrorCode.DEVICE_STATE_OPENACCOUNT_ERROR.getDesc()+rechargeVO.getGasMeterNumber());
        }
        rechargeVO.setDomainId(domainId);
        IotR res = messageService.rechargeEvent(rechargeVO,BaseContextHandler.getTenant());
        return res;
    }

    @Override
    public IotR changePrice(String domainId, PriceChangeVO priceChangeVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(priceChangeVO.getGasMeterNumber())){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+priceChangeVO.getGasMeterNumber());}
        if(priceChangeVO.getTiered()==null||priceChangeVO.getTiered().size()==0
                ||priceChangeVO.getTiered().size()>5){
            throw new BizException(ErrorCode.DEVICE_PRICE_SCHEME_ERROR.getDesc()+priceChangeVO.getGasMeterNumber());
        }
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,priceChangeVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+priceChangeVO.getGasMeterNumber());}
        if(!DeviceStage.Enabled.getCodeValue().equals(model.getDeviceState())){
            throw new BizException(ErrorCode.DEVICE_STATE_OPENACCOUNT_ERROR.getDesc()+priceChangeVO.getGasMeterNumber());
        }
        priceChangeVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(priceChangeVO);
        //往mq里面加入充值信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ChangePrice, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR changeBatPrice(String domainId, List<PriceChangeVO> priceChangeList) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        for(int i=0;i<priceChangeList.size();i++){
            priceChangeList.get(i).setDomainId(domainId);
            if(priceChangeList.get(i).getGasMeterNumber()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()
                    +priceChangeList.get(i).getGasMeterNumber());}
            IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
            checkParams.setGasMeterNumber(priceChangeList.get(i).getGasMeterNumber());
            if(!check(checkParams)){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+priceChangeList.get(i).getGasMeterNumber());}
        }
        String data = JSONObject.toJSONString(priceChangeList);
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ChangeBatPrice, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR readIotMeter(String domainId, List<ParamsVO> paramsVOList) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        for(int i=0;i<paramsVOList.size();i++){
            ParamsVO paramsVO = paramsVOList.get(i);
            if(StringUtil.isNullOrBlank(paramsVO.getGasMeterNumber())){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+paramsVO.getGasMeterNumber());}
            IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                    .eq(IotGasMeterUploadData::getGasMeterNumber,paramsVO.getGasMeterNumber())
                    .ne(IotGasMeterUploadData::getDeviceState,4));
            if(model==null){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+paramsVO.getGasMeterNumber());}
            paramsVO.setDomainId(domainId);
            String data = JSONObject.toJSONString(paramsVO);
            //往mq里面加入补抄信息
            producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                    MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ReadMeterData, BaseContextHandler.getTenant())));
        }
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR setDeviceParams(String domainId, List<IotDeviceParams> iotDeviceParams) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
//        for(IotDeviceParams e:iotDeviceParams){
//            if(e.getGasMeterNumber()==null){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+e.getGasMeterNumber());}
//            IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
//            checkParams.setGasMeterNumber(e.getGasMeterNumber());
//            if(check(checkParams)){throw new  BizException(ErrorCode.METERNO_EXIST.getDesc()+e.getGasMeterNumber());}
//            e.setDomainId(domainId);
//        }
        String data = JSONObject.toJSONString(iotDeviceParams);
        //往mq里面加入补抄信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.ParamsSetting, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR clearmeter(String domainId, ClearVO clearVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(clearVO.getGasMeterNumber())){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+clearVO.getGasMeterNumber());}
        IotGasMeterUploadData model = baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,clearVO.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4));
        if(model==null){throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+clearVO.getGasMeterNumber());}
        clearVO.setDomainId(domainId);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(model.getDomainId());
        //设置参数
        ClearModel params = new ClearModel();
        params.setArchiveId(model.getArchiveId());
        params.setBusinessType(CommandType.METERCLEAN.getCode());
        params.setDomainId(model.getDomainId());
        ClearBusModel clearBusModel = new ClearBusModel();
        params.setBusinessData(clearBusModel);
        String sendData = JSONUtil.toJsonStr(params);
        //向3.0发送清零指令
        ExecuteState executeState = ExecuteState.UnExecute;
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/business",sendData,
                true,model.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request clear Meter Data faild,meterNo:"+model.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.MESSAGE)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            executeState = ExecuteState.Error;
        }
        //指令记录
        defendCommand(model,CommandType.METERCLEAN,sendData,res.getJSONObject(HttpCodeConstant.RESULT_DATA)
                .getString(GMISConstant.GMIS_DEVICE_TRANSACTION_NO),executeState.getCodeValue());
        //更新设备状态
        IotGasMeterUploadData update = IotGasMeterUploadData.builder()
                .id(model.getId())
                .deviceState(DeviceStage.Inactive.getCodeValue())
                .build();
        iotGasMeterUploadDataService.updateById(update);

        return IotR.ok();
    }

    @Override
    public IotR updatebalance(String domainId, UpdateBalanceVO updateBalanceVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){throw new  BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());}
        if(StringUtil.isNullOrBlank(updateBalanceVO.getGasMeterNumber())){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+updateBalanceVO.getGasMeterNumber());}
        IotGasMeterUploadData checkParams = new IotGasMeterUploadData();
        checkParams.setGasMeterNumber(updateBalanceVO.getGasMeterNumber());
        if(!check(checkParams)){throw new  BizException(ErrorCode.METERNO_NOTEXIST.getDesc()+updateBalanceVO.getGasMeterNumber());}
        updateBalanceVO.setDomainId(domainId);
        String data = JSONObject.toJSONString(updateBalanceVO);
        //往mq里面加入更新设备余额信息
        producerUtils.produce(GMISConstant.GMIS_MQ_NAME_PRE+BaseContextHandler.getTenant(),
                MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.UpdateBalance, BaseContextHandler.getTenant())));
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public Boolean check(IotGasMeterUploadData params) {
        return super.count(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,params.getGasMeterNumber())
                .ne(IotGasMeterUploadData::getDeviceState,4))>0;
    }

    @Override
    public IotGasMeterUploadData queryIotGasMeter(String gasMeterCode, String customerCode) {
        LbqWrapper<IotGasMeterUploadData> wrapper = new LbqWrapper<>();
        wrapper.eq(IotGasMeterUploadData::getGasMeterCode,gasMeterCode);
        wrapper.eq(IotGasMeterUploadData::getCustomerCode,customerCode);
        wrapper.ne(IotGasMeterUploadData::getDeviceState,4);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IotR updateIotGasMeter(IotGasMeterUploadData iotGasMeterUploadData) {
        int count = baseMapper.updateById(iotGasMeterUploadData);
        if(count>0){
          return  IotR.ok();
        }else{
            return IotR.error();
        }

    }

    @Override
    public IotR clearAndRegisterMeter(String domainId, OrderListVO orderListVO) throws Exception {
        if (StringUtil.isNullOrBlank(domainId)) {
            throw new BizException(ErrorCode.DOMAIN_ID_NOTEXIST.getDesc());
        }
        ClearVO clearVO = orderListVO.getClearVO();
        String gasMeterNumber = clearVO.getGasMeterNumber();
        if (StringUtil.isNullOrBlank(gasMeterNumber)) {
            throw new BizException(ErrorCode.METERNO_NOTEXIST.getDesc() + clearVO.getGasMeterNumber());
        }
        IotR clearMeter = clearmeter(domainId, clearVO);
        if(clearMeter.getIsError()){
            log.error("清零指令下发失败");
            return clearMeter;
        }
        //设备注册
        RegisterDeviceVO registerDeviceVO = orderListVO.getRegisterDeviceVO();
        registerDeviceVO.setGasMeterNumber(gasMeterNumber);
        registerDeviceVO.setDomainId(domainId);
        IotR register = messageService.registerDeviceEvent(JSONUtil.toJsonStr(registerDeviceVO), BaseContextHandler.getTenant());
        if(register.getIsError()){
            log.error("注册指令下发失败");
            return register;
        }

        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotGasMeterUploadData queryActivateMeter(String gasMeterNumber) {
        return baseMapper.selectOne(Wraps.<IotGasMeterUploadData>lbQ()
                .eq(IotGasMeterUploadData::getGasMeterNumber,gasMeterNumber)
                .ne(IotGasMeterUploadData::getDeviceState,4));
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
                               String transactionNo,int execStatus){
        //保存指令信息
        IotGasMeterCommand command = new IotGasMeterCommand();
        command.setMeterNumber(deviceData.getGasMeterNumber());
        command.setCommandType(commandType);
        command.setCommandParameter(commandData);
        command.setGasMeterCode(deviceData.getGasMeterCode());
        command.setExecuteResult(execStatus);
        command.setTransactionNo(transactionNo);
        iotGasMeterCommandService.save(command);
        //保存指令详情
        IotGasMeterCommandDetail commandDetail = new IotGasMeterCommandDetail();
        commandDetail.setTransactionNo(transactionNo);
        commandDetail.setCommandId(command.getId());
        commandDetail.setCommandType(commandType);
        commandDetail.setExecuteTime(LocalDateTime.now());
        commandDetail.setCommandStatus(execStatus);
        commandDetail.setCustomerCode(deviceData.getCustomerCode());
        commandDetail.setGasMeterCode(deviceData.getGasMeterCode());
        commandDetail.setCommandStage((execStatus==0? CommandState.WaitSend.getCodeValue():
                CommandState.CancleExecute.getCodeValue()));
        commandDetail.setMeterNumber(deviceData.getGasMeterNumber());
        commandDetail.setExecuteResult(execStatus);
        iotGasMeterCommandDetailService.save(commandDetail);
    }
}

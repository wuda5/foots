package com.cdqckj.gmis.iot.qc.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.iot.qc.dao.IotDeviceDataHistoryMapper;
import com.cdqckj.gmis.iot.qc.dto.MessageDTO;
import com.cdqckj.gmis.iot.qc.entity.DataType;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceDataHistory;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.entity.MessageType;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISHeaderConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.RedisConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.MQUtils;
import com.cdqckj.gmis.iot.qc.service.IotDeviceDataHistoryService;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import com.cdqckj.gmis.mq.producer.utils.ProducerUtils;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotDeviceDataHistoryServiceImpl extends SuperServiceImpl<IotDeviceDataHistoryMapper, IotDeviceDataHistory> implements IotDeviceDataHistoryService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private IotCacheUtil iotCacheUtil;
    @Autowired
    private IotSubscribeService iotSubscribeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ProducerUtils producerUtils;
    @Override
    public IotR receiveData(Map<String, Object> map) throws Exception {
        IotR result = IotR.ok();
        IotSubscribe iotSubscribe = null;
        String domainId = iotCacheUtil.getReceiveDomainId();
        if(StringUtil.isNullOrBlank(domainId)){domainId = MapUtil.getStr(map, GMISHeaderConstant.DOMAIN_ID);}
        if(StringUtil.isNullOrBlank(domainId)){return IotR.error();}
        if(!redisService.hasKey(RedisConstant.IOT_GMIS+StrPool.COLON+
                RedisConstant.DOMAIN_ID+StrPool.COLON+domainId)){
            iotSubscribe = iotSubscribeService.getOne(Wraps.<IotSubscribe>lbQ().eq(IotSubscribe::getDomainId,domainId));
            if(iotSubscribe==null){return IotR.error();}
            else{redisService.set(RedisConstant.IOT_GMIS+StrPool.COLON+
                    RedisConstant.DOMAIN_ID+StrPool.COLON+domainId,domainId);}
        }
        String tenant = (String)redisService.get(RedisConstant.IOT_GMIS+StrPool.COLON+
                RedisConstant.DOMAIN_ID_TENANT+StrPool.COLON+domainId);
        if(StringUtil.isNullOrBlank(tenant)){
            iotSubscribe = iotSubscribeService.getOne(Wraps.<IotSubscribe>lbQ().eq(IotSubscribe::getDomainId,domainId));
            if(iotSubscribe==null){return IotR.error();}
            tenant = iotSubscribe.getTenantCode();
        }
        BaseContextHandler.setTenant(tenant);
        String data = "";
        if(DataType.DeviceDataNotice.getCode().equals(MapUtil.getStr(map, GMISConstant.DATA_TYPE))){
            data = JSONObject.toJSONString(map);
            log.info("收到设备推送:"+ data);
            //往mq里面加入设备信息
            producerUtils.produce(GMISConstant.GMIS_MQ_RECIVE_PRE+BaseContextHandler.getTenant(),
                    MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.DeviceData, BaseContextHandler.getTenant())));
        }else if(DataType.BusinessStateNotice.getCode().equals(MapUtil.getStr(map,GMISConstant.DATA_TYPE))){
            data = JSONObject.toJSONString(map);
            log.info("收到业务推送:"+data);
            //往mq里面加入业务信息
            producerUtils.produce(GMISConstant.GMIS_MQ_RECIVE_PRE+BaseContextHandler.getTenant(),
                    MQUtils.getBytesFromObject(new MessageDTO(data, MessageType.BusinessData, BaseContextHandler.getTenant())));
        }else{
            return IotR.error();
        }
        return result;
    }
}

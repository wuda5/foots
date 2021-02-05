package com.cdqckj.gmis.iot.qc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.iot.qc.dao.IotSubscribeMapper;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISHeaderConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.HttpCodeConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.RedisConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.HttpContextUtils;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotRestUtil;
import com.cdqckj.gmis.iot.qc.service.IotSubscribeService;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-12
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotSubscribeServiceImpl extends SuperServiceImpl<IotSubscribeMapper, IotSubscribe> implements IotSubscribeService {
    @Autowired
    private IotCacheUtil iotCacheUtil;
    @Autowired
    private IotRestUtil iotRestUtil;
    @Autowired
    private RedisService redisService;

    @Override
    public IotR isSubscribe(String domainId) throws Exception {
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        if(iotGlobal.getSubscribe()==1){
            return IotR.ok();
        }else{
            return IotR.error();
        }
    }

    @Override
    public IotR subscribe(String domainId) throws Exception {
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        //设备上行数据
        JSONObject json = new JSONObject();
        json.put("noticeType","AllNotice");
        json.put("noticeUrl", iotGlobal.getNoticeUrl());
        json.put("noticeCacheDay",iotGlobal.getNoticeCacheDay());
        json.put(GMISHeaderConstant.DOMAIN_ID, iotCacheUtil.getDomainId());
        //流量计加上内容
//        json.put("domainId", SensorCacheUtil.getDomainId());

        //请求订阅
        JSONObject deviceResult = iotRestUtil.post(iotGlobal.getGatewayUrl()+"notice/subscribe",json.toJSONString(),true);
        //判断上面请求执行的结果
        if(HttpCodeConstant.SUCCESS.equals(deviceResult.getString(HttpCodeConstant.RESULT_CODE))){
            //保存数据订阅信息
            IotSubscribe iotSubscribe = new IotSubscribe();
            iotSubscribe.setLicence(iotGlobal.getLicence());
            iotSubscribe.setDomainId(iotGlobal.getDomainId());
            iotSubscribe.setId(iotGlobal.getId());
            iotSubscribe.setSubscribe(1);
            iotSubscribe.setNoticeType("AllNotice");
            iotSubscribe.setNoticeUrl(iotGlobal.getNoticeUrl());
            iotSubscribe.setNoticeCacheDay(iotGlobal.getNoticeCacheDay());
            baseMapper.updateById(iotSubscribe);
            return IotR.ok();
        }else{
            return IotR.error();
        }
    }

    @Override
    public IotR unsubscribe(String domainId) throws Exception {
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        JSONObject json = new JSONObject();
        json.put("noticeType","AllNotice");
        json.put(GMISHeaderConstant.DOMAIN_ID, iotCacheUtil.getDomainId());
        JSONObject result = iotRestUtil.post(iotGlobal.getGatewayUrl()+"notice/unsubscribe",json.toJSONString(),true);
        if(HttpCodeConstant.SUCCESS.equals(result.getString(HttpCodeConstant.RESULT_CODE))){
            //删除数据库中已订阅的数据记录
            IotSubscribe iotSubscribe = new IotSubscribe();
            iotSubscribe.setLicence(iotGlobal.getLicence());
            iotSubscribe.setDomainId(iotGlobal.getDomainId());
            iotSubscribe.setSubscribe(0);
            iotSubscribe.setId(iotGlobal.getId());
            baseMapper.updateById(iotSubscribe);
            //删除已订阅的厂家信息
            if(redisService.hasKey(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                    RedisConstant.IOT_SUBSRCIBE+StrPool.COLON+domainId)){
                redisService.del(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                        RedisConstant.IOT_SUBSRCIBE+StrPool.COLON+domainId);
            }
            if(redisService.hasKey(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                    RedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+domainId)){
                redisService.del(RedisConstant.IOT_GMIS+StrPool.COLON+BaseContextHandler.getTenant()+StrPool.COLON+
                        RedisConstant.FACTORY_IOT_ENTITY+StrPool.COLON+domainId);
            }
            return IotR.ok();
        }else{
            return IotR.error();
        }
    }

    @Override
    public IotSubscribe queryByTenant(String tenant, String domainId) {
        return baseMapper.selectOne(Wraps.<IotSubscribe>lbQ()
                .eq(IotSubscribe::getDomainId,domainId)
                .eq(IotSubscribe::getTenantCode,tenant));
    }

    @Override
    public Boolean check(IotSubscribeUpdateDTO iotSubscribeUpdateDTO) {
        return super.count(Wraps.<IotSubscribe>lbQ()
                .eq(IotSubscribe::getFactoryName,iotSubscribeUpdateDTO.getFactoryName())
                .eq(IotSubscribe::getFactoryCode,iotSubscribeUpdateDTO.getFactoryCode())
                .eq(IotSubscribe::getDomainId,iotSubscribeUpdateDTO.getDomainId())
                .eq(IotSubscribe::getTenantCode, BaseContextHandler.getTenant())
                .ne(IotSubscribe::getId,iotSubscribeUpdateDTO.getId())) > 0;
    }

    @Override
    public Boolean checkAdd(IotSubscribeSaveDTO iotSubscribeSaveDTO) {
        return super.count(Wraps.<IotSubscribe>lbQ()
                .eq(IotSubscribe::getFactoryName,iotSubscribeSaveDTO.getFactoryName())
                .eq(IotSubscribe::getFactoryCode,iotSubscribeSaveDTO.getFactoryCode())
                .eq(IotSubscribe::getDomainId,iotSubscribeSaveDTO.getDomainId())
                .eq(IotSubscribe::getTenantCode, BaseContextHandler.getTenant())) > 0;
    }

    @Override
    public IotSubscribe queryByFactoryCode(String factoryCode) {
        return baseMapper.selectOne(Wraps.<IotSubscribe>lbQ().eq(IotSubscribe::getFactoryCode,factoryCode).eq(IotSubscribe::getDataStatus,1));
    }

    private boolean check(IotSubscribe iotSubscribe){
       return super.count(Wraps.<IotSubscribe>lbQ()
               .eq(IotSubscribe::getLicence,iotSubscribe.getLicence())
               .eq(IotSubscribe::getDomainId,iotSubscribe.getDomainId())
               .eq(IotSubscribe::getSubscribe,1)) > 0;
    }
}

package com.cdqckj.gmis.iot.qc.service.impl;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import com.cdqckj.gmis.iot.qc.helper.TokenHelper;
import com.cdqckj.gmis.iot.qc.qnms.config.IotConfig;
import com.cdqckj.gmis.iot.qc.qnms.constant.HttpCodeConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.RequestConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotRestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@DS("#thread.tenant")
public class IotLoginServiceImpl implements TokenHelper {
    @Autowired
    private IotConfig iotConfig;
    @Autowired
    private IotRestUtil iotRestUtil;
    @Autowired
    private IotCacheUtil iotCacheUtil;

    @Override
    public IotR login(String licence) throws Exception {
        IotSubscribe isGlobal = iotCacheUtil.getFactoryEntity();
        JSONObject parma = new JSONObject();
        parma.put("licence",licence);
        parma.put("tokenTime",iotConfig.getTokenTime());

        JSONObject result = iotRestUtil.post(isGlobal.getGatewayUrl()+"account/login",parma.toJSONString(),false);
        log.debug(result.toJSONString());

        if(HttpCodeConstant.SUCCESS.equals(result.getString(HttpCodeConstant.RESULT_CODE))){
            Map<String,String> tokenMap = MapUtil.newHashMap();
            tokenMap.put(RequestConstant.ACCESS_TOKEN,result
                    .getJSONObject(HttpCodeConstant.RESULT_DATA)
                    .getString(HttpCodeConstant.ACCESS_TOKEN));
            return IotR.ok().object(tokenMap);
        }else{
            return IotR.error(HttpCodeConstant.ERROR_INT,result.getString(HttpCodeConstant.MESSAGE));
        }
    }
}

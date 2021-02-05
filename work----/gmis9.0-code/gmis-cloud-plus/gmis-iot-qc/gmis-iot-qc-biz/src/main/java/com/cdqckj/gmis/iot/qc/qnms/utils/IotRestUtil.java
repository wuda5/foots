package com.cdqckj.gmis.iot.qc.qnms.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cdqckj.gmis.iot.qc.qnms.config.SignKeyConfig;
import com.cdqckj.gmis.iot.qc.qnms.constant.RequestConstant;
import com.cdqckj.gmis.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 网络请求公共方法
 * @author: 秦川物联网科技
 * @date: 2020/10/12 17:00
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Component
@DependsOn("iotSpringUtils")
public class IotRestUtil {
    @Autowired
    private IotCacheUtil iotCacheUtil;
    private  SignKeyConfig signKeyConfig = IotSpringUtils.getBean(SignKeyConfig.class);
    private  RestTemplate restTemplate = IotSpringUtils.getBean(RestTemplate.class);

    public JSONObject post(String url, String jsonParma,boolean containToken) throws Exception{
        //签名需要统一key字母顺序
        String sortJson = JSONObject.toJSONString(JSONObject.parseObject(jsonParma), SerializerFeature.MapSortField);

        HttpHeaders headers = new HttpHeaders();
        headers.set(RequestConstant.TYPE, RequestConstant.TYPE_VAL);
        if(containToken) {
            headers.set(RequestConstant.ACCESS_TOKEN, "Token "+iotCacheUtil.getAccessToken());
            //数据加签
//            headers.set(RequestConstant.SIGN, SignUtils.sign(sortJson.getBytes(StandardCharsets.UTF_8), signKeyConfig.getPrivateKey()));
        }else {

        }

        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> httpEntity = new HttpEntity<>(sortJson, headers);
        log.info("请求地址:"+url);
        log.info("请求参数:"+JSONObject.toJSONString(httpEntity));
        HttpEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
        JSONObject result = response.getBody();
        log.info("返回结果:"+result.toJSONString());
        return result;
    }

    public JSONObject postMessage(String url, String jsonParma,boolean containToken,String domainId) throws Exception{
        //签名需要统一key字母顺序
        String sortJson = JSONObject.toJSONString(JSONObject.parseObject(jsonParma), SerializerFeature.MapSortField);

        HttpHeaders headers = new HttpHeaders();
        headers.set(RequestConstant.TYPE, RequestConstant.TYPE_VAL);
        if(containToken) {
            headers.set(RequestConstant.ACCESS_TOKEN, "Token "+iotCacheUtil.getImplAccessToken(domainId));
            //数据加签
//            headers.set(RequestConstant.SIGN, SignUtils.sign(sortJson.getBytes(StandardCharsets.UTF_8), signKeyConfig.getPrivateKey()));
        }else {

        }

        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> httpEntity = new HttpEntity<>(sortJson, headers);
        log.info("请求地址:"+url);
        log.info("请求参数:"+JSONObject.toJSONString(httpEntity));
        HttpEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
        JSONObject result = response.getBody();
        log.info("返回结果:"+result.toJSONString());
        return result;
    }
}

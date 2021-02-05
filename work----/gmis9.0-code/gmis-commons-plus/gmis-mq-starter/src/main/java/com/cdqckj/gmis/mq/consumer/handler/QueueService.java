package com.cdqckj.gmis.mq.consumer.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.mq.properties.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列动态管理
 * @author: 秦川物联网科技
 * @date: 2020/11/06  15:06
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
@Slf4j
@DependsOn("mqProperties")
public class QueueService {

    @Autowired
    private MqProperties mqProperties;



    private CloseableHttpClient getBasicHttpClient() {

        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置BasicAuth
        CredentialsProvider provider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials( mqProperties.getUsername(), mqProperties.getPassword());
        provider.setCredentials(scope, credentials);
        httpClientBuilder.setDefaultCredentialsProvider(provider);
        return httpClientBuilder.build();
    }

    /**
     * 根据API获得相关的MQ信息
     *
     * @return
     */

    public List<String> getMQJSONArray() {
        String url = "http://"+mqProperties.getIp()+":15672/api/queues";
        HttpGet httpPost = new HttpGet(url);
        CloseableHttpClient pClient = getBasicHttpClient();
        CloseableHttpResponse response = null;
        JSONArray jsonArray = null;
        List queueNameList = new ArrayList();
        try {
            response = pClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                String string = EntityUtils.toString(response.getEntity());
                jsonArray = (JSONArray) JSONObject.parse(string);
                if (null != jsonArray) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        String name = (String) jsonArray.getJSONObject(i).get("name");
                        if(name.contains("gmis_iot_mq_")||name.contains("gmis_queue_iot_qc")){
                            System.out.println("------------------物联网MQ-----------------:"+name);
                            queueNameList.add(name);
                        }
                    }
                    return queueNameList;
                }
            } else {
                System.out.println("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception e) {
            log.error("地址url:" + url + "，异常：" + e.toString());
        } finally {
            closeAll(response, pClient);
        }
        return queueNameList;
    }

    public void closeAll(CloseableHttpResponse response, CloseableHttpClient pClient) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

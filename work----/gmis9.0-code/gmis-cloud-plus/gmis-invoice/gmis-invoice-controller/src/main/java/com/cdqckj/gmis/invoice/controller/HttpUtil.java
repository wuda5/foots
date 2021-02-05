package com.cdqckj.gmis.invoice.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class HttpUtil {


    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 2 * 60 * 1000;

    static {
	// 设置连接池
	connMgr = new PoolingHttpClientConnectionManager();
	// 设置连接池大小
	connMgr.setMaxTotal(100);
	connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

	RequestConfig.Builder configBuilder = RequestConfig.custom();
	// 设置连接超时
	configBuilder.setConnectTimeout(MAX_TIMEOUT);
	// 设置读取超时
	configBuilder.setSocketTimeout(MAX_TIMEOUT);
	// 设置从连接池获取连接实例的超时
	configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
	// 在提交请求之前 测试连接是否可用
	configBuilder.setStaleConnectionCheckEnabled(true);
	requestConfig = configBuilder.build();
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * 
     * @param apiUrl
     *            API接口URL
     * @param params
     *            参数map
     * @return
     */
    public static JSONObject doPostJSON(String apiUrl, Map<String, Object> params) {
	JSONObject json = null;
	try {
	    String result = doPost(apiUrl, params);
	    json = JSONUtil.parseObj(result);
	} catch (Exception e) {
	    log.error("发送 POST 请求异常，{}", e.getMessage(), e);
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", params);
	    throw new RuntimeException(error.toString(), e);
	}
	return json;
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * 
     * @param url
     * @return
     */
    public static String doGet(String url) {
	return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），字符串形式
     * 
     * @param url
     * @return
     */

    public static String doGet(String url, String params) {
	String apiUrl = url;
	apiUrl += params;
	log.debug("doget: url=" + apiUrl);
	String result = null;
	CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();
	try {
	    HttpGet httpPost = new HttpGet(apiUrl);
	    HttpResponse response = httpclient.execute(httpPost);
	    int statusCode = response.getStatusLine().getStatusCode();

		log.debug("执行状态码 : " + statusCode);

	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		InputStream instream = entity.getContent();
		result = IOUtils.toString(instream, "UTF-8");
	    }
	} catch (IOException e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", params);
	    throw new RuntimeException(error.toString(), e);
	}
		log.debug("doget: url=" + apiUrl + "; result" + result);
	return result;
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * 
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {

	String apiUrl = url;
	StringBuffer param = new StringBuffer();
	int i = 0;
	for (String key : params.keySet()) {
	    if (i == 0)
		param.append("?");
	    else
		param.append("&");
	    param.append(key).append("=").append(params.get(key));
	    i++;
	}
	apiUrl += param;
		log.debug("doGet: url=" + apiUrl);
	String result = null;
	CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();
	try {
	    HttpGet httpPost = new HttpGet(apiUrl);
	    HttpResponse response = httpclient.execute(httpPost);
	    int statusCode = response.getStatusLine().getStatusCode();

		log.debug("执行状态码 : " + statusCode);

	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		InputStream instream = entity.getContent();
		result = IOUtils.toString(instream, "UTF-8");
	    }
	} catch (IOException e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", params);
	    throw new RuntimeException(error.toString(), e);
	}
		log.debug("doget: url=" + apiUrl + "; result" + result);
	return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * 
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
	return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * 
     * @param apiUrl
     *            API接口URL
     * @param params
     *            参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
	CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();

	String httpStr = null;
	HttpPost httpPost = new HttpPost(apiUrl);
	CloseableHttpResponse response = null;
		log.debug("doPost: url=" + apiUrl + "; params" + params);
	try {
	    httpPost.setConfig(requestConfig);
	    List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
	    for (Map.Entry<String, Object> entry : params.entrySet()) {
		NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
		pairList.add(pair);
	    }
	    httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
	    response = httpClient.execute(httpPost);
		log.debug(response.toString());
	    HttpEntity entity = response.getEntity();
	    httpStr = EntityUtils.toString(entity, "UTF-8");
	} catch (IOException e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", params);
	    throw new RuntimeException(error.toString(), e);
	} finally {
	    if (response != null) {
		try {
		    EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
		    JSONObject error = new JSONObject();
		    error.put("requestURL", apiUrl);
		    error.put("param", params);
		    throw new RuntimeException(error.toString(), e);
		}
	    }
	}
		log.debug("doPost: url=" + apiUrl + "; result" + httpStr);
	return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTP），JSON形式
     * 
     * @param apiUrl
     *            API接口URL
     * @param json
     *            JSON对象
     * @return
     */
    public static JSONObject doPostJSON2(String apiUrl, Object json) {
	CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();
	HttpPost httpPost = new HttpPost(apiUrl);
	CloseableHttpResponse response = null;
	String httpStr = null;

	try {
	    httpPost.setConfig(requestConfig);
	    StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
	    stringEntity.setContentEncoding("UTF-8");
	    stringEntity.setContentType("application/json");
	    httpPost.setEntity(stringEntity);
	    response = httpClient.execute(httpPost);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode != HttpStatus.SC_OK) {
		return null;
	    }
	    HttpEntity entity = response.getEntity();
	    if (entity == null) {
		return null;
	    }
	    httpStr = EntityUtils.toString(entity, "utf-8");
	} catch (Exception e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", json);
	    throw new RuntimeException(error.toString(), e);
	} finally {
	    if (response != null) {
		try {
		    EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
		    JSONObject error = new JSONObject();
		    error.put("requestURL", apiUrl);
		    error.put("param", json);
		    throw new RuntimeException(error.toString(), e);
		}
	    }
	}
	return JSONUtil.parseObj(httpStr);
    }

    public static String doPostEncode(String apiUrl, Object content, String encode) {
	CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();
	HttpPost httpPost = new HttpPost(apiUrl);
	CloseableHttpResponse response = null;
	String httpStr = null;
	encode = encode == null || "".equals(encode) ? "UTF-8" : encode;
	try {
	    httpPost.setConfig(requestConfig);
	    StringEntity stringEntity = new StringEntity(content.toString(), encode);// 解决中文乱码问题
	    stringEntity.setContentEncoding(encode);
	    httpPost.setEntity(stringEntity);
	    response = httpClient.execute(httpPost);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode != HttpStatus.SC_OK) {
		return null;
	    }
	    HttpEntity entity = response.getEntity();
	    if (entity == null) {
		return null;
	    }
	    httpStr = EntityUtils.toString(entity, encode);
	} catch (Exception e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", content);
	    throw new RuntimeException(error.toString(), e);
	} finally {
	    if (response != null) {
		try {
		    EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
		    JSONObject error = new JSONObject();
		    error.put("requestURL", apiUrl);
		    error.put("param", content);
		    throw new RuntimeException(error.toString(), e);
		}
	    }
	}
	return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * 
     * @param apiUrl
     * @param json
     *            json对象
     * @return
     */
    public static String doPostJSON(String apiUrl, Object json) {
	CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr)
		.setDefaultRequestConfig(requestConfig).build();
	String httpStr = null;
	HttpPost httpPost = new HttpPost(apiUrl);
	CloseableHttpResponse response = null;

	try {
	    httpPost.setConfig(requestConfig);
	    StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
	    stringEntity.setContentEncoding("UTF-8");
	    stringEntity.setContentType("application/json");
	    httpPost.setEntity(stringEntity);
	    response = httpClient.execute(httpPost);
	    HttpEntity entity = response.getEntity();
		log.debug("状态码为,{}",response.getStatusLine().getStatusCode());
	    httpStr = EntityUtils.toString(entity, "UTF-8");
	} catch (IOException e) {
	    JSONObject error = new JSONObject();
	    error.put("requestURL", apiUrl);
	    error.put("param", json);
	    throw new RuntimeException(error.toString(), e);
	} finally {
	    if (response != null) {
		try {
		    EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
		    JSONObject error = new JSONObject();
		    error.put("requestURL", apiUrl);
		    error.put("param", json);
		    throw new RuntimeException(error.toString(), e);
		}
	    }
	}
	return httpStr;
    }

}

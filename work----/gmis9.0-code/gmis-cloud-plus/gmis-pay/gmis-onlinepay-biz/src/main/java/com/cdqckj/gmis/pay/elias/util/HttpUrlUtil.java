package com.cdqckj.gmis.pay.elias.util;

import com.cdqckj.gmis.pay.elias.util.CertUtil;
import lombok.extern.log4j.Log4j2;
import net.sf.json.JSONObject;
import okhttp3.HttpUrl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cdqckj.gmis.utils.HttpClientUtils.*;

@Log4j2
public class HttpUrlUtil {
    public static String SCHEMA = "WECHATPAY2-SHA256-RSA2048";
    public static String merchantId = "1603115705"; // 服务商

    public static String POST = "POST";
    public static String GET = "GET";

    public static String host = "https://api.mch.weixin.qq.com";
    public static String APPLY_PATH = "/v3/applyment4sub/applyment/"; // 申请单url
    public static String CERT_PATH = "/v3/certificates"; // 获取微信平台证书url
    public static String APPLY_QUERY_PATH = "/v3/applyment4sub/applyment/business_code/"; // 查询申请状态

    /**
     * POST请求
     */
    public static String sendPost(String body) {
        String url = host + APPLY_PATH;
        return doPostJson(url, body);
    }

    /**
     * POST请求
     */
    public static String sendPost(Map<String, Object> param) throws Exception {
        String url = host + APPLY_PATH;
        String body = JSONObject.fromObject(param).toString();
        // 获取微信平台商户证书序列号
        String wxSerialNo = CertUtil.getCertSerialNo();//"2339B893C2371AC03D7EB6A56840AFEB595F1C8E";
        String authorization = getToken(POST, url, body);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json");
        headers.put("user-agent", "application/json");
        headers.put("Wechatpay-Serial", wxSerialNo);
        headers.put("Authorization", authorization);
        return doPostJson2(url, body, headers);

    }


    public static String doPostJson2(String url, String json, Map<String, String> headers) {

        log.info("=====请求地址:"+url);
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if(headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            }

            // 创建请求内容
            log.info("=====请求参数:"+json);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            log.info("=====响应参数:"+response);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("系统错误:",e);
        } finally {
            try {
                if (response!=null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("系统错误:",e);
            }
        }
        return resultString;
    }

    /**
     * 获取加密串
     *
     * @param method
     * @param url
     * @param body
     * @return
     */
    public static String getToken(String method, String url, String body) {
        String nonceStr = UUID.randomUUID().toString().replaceAll("-","");
        long timestamp = System.currentTimeMillis() / 1000;
        HttpUrl httpUrl = HttpUrl.parse(url);
        String message = buildMessage(method, httpUrl, timestamp, nonceStr, body);
        String signature = null;
        String certificateSerialNo = null;
        try {
            signature = sign(message.getBytes("utf-8"));
            certificateSerialNo = "2339B893C2371AC03D7EB6A56840AFEB595F1C8E";//商户证书序列号
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SCHEMA + " mchid=\"" + merchantId + "\"," + "nonce_str=\"" + nonceStr + "\"," + "timestamp=\"" + timestamp + "\"," + "serial_no=\""
                + certificateSerialNo + "\"," + "signature=\"" + signature + "\"";
    }

    /**
     * 得到签名字符串
     */
    public static String sign(byte[] message) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        PrivateKey privateKey = CertUtil.getPrivateKey();
        sign.initSign(privateKey);
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n" + canonicalUrl + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
    }

    /**
     * get请求
     */
    public static String sendGet() {
        // 请求URL
        String url = host + CERT_PATH;
        try {
            String authorization = getToken(GET, url, "");
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("Accept", "application/json");
            headers.put("user-agent", "application/json");
            headers.put("Authorization", authorization);
            return doGet(url, headers, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String statusQuery(String businessCode) {
        // 请求URL
        String url = host + APPLY_QUERY_PATH + businessCode;
        try {
            String authorization = getToken(GET, url, "");
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("Accept", "application/json");
            headers.put("user-agent", "application/json");
            headers.put("Authorization", authorization);
            return doGet(url, headers, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        return ip;
    }
}

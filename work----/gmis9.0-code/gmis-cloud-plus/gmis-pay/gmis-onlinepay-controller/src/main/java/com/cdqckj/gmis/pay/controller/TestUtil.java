package com.cdqckj.gmis.pay.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestUtil {

    private static Log logger = LogFactory.getLog(TestUtil.class);
    public static void main(String[] args) {
        //testSelf();
        test();
    }

    public static String test(){
        String json = "{\"txn_no\":\"20170704093423192\",\"mcht_no\":\"M201707040001\",\"term_id\":\"红牌楼店\",\"business_type\":\"3003\",\"txn_amt\":\"0.1\",\"subject\":\"测试\",\"body\":\"商品1\",\"notify_url\":\"http://rms.vipgz4.idcfengye.com/\",\"sign_type\":\"MD5\"}";

        /*Map<String,String> headMap =  new HashMap<String,String>();
        headMap.put("tenant", tenant.getCode());*/
        String code = doPostJson("http://uchannel.payexpress.biz/payfront/json/trans/codePassive",json.toString());
        System.out.println(code);
        return code;
    }

    public static String testSelf(){

        StringBuffer json = new StringBuffer();
        json.append("{");
        json.append("'model':{}");
        json.append("}");
        /*Map<String,String> headMap =  new HashMap<String,String>();
        headMap.put("tenant", tenant.getCode());*/
        String code = doPostJson("http://172.16.92.34:8802/sysparam/template/template/page",json.toString());

        return code;
    }

    public static String doPostJson(String url, String json) {

        logger.info("=====请求地址:"+url);
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            logger.info("=====请求参数:"+json);
            //httpPost.setHeader("tenant","0000");
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            logger.info("=====响应参数:"+response);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.error("系统错误:",e);
        } finally {
            try {
                if (response!=null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("系统错误:",e);
            }
        }
        return resultString;
    }


    public static String test3(){
        String resp= null;
        String query = "{\"appid\":\" wxd930ea5d5a258f4f\",\" mch_id\":\" 10000100\",\" device_info\":\"100\",\"body\":\"test\",\" nonce_str\":\" ibuaiVcKdpRxkhJA\",\"sign\":\" 9A0A8659F005D6984697E2CA0A9CF3B7\"}";
        logger.info("发送到URL的报文为："+query);
        logger.info(query);
        try {
            URL url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder"); //url地址

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.connect();

            try (OutputStream os = connection.getOutputStream()) {
                os.write(query.getBytes("UTF-8"));
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String lines;
                StringBuffer sbf = new StringBuffer();
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sbf.append(lines);
                }
                logger.info("返回来的报文："+sbf.toString());
                resp = sbf.toString();

            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JSONObject json = (JSONObject) JSON.parse(resp);
        }
        return resp;
    }


    public static String test4() throws IOException {
        String UTF8 = "UTF-8";
        String reqBody = "{\"txn_no\":\"20170704093423192\",\"mcht_no\":\"M201707040001\",\"term_id\":\"红牌楼店\",\"business_type\":\"3003\",\"txn_amt\":\"0.1\",\"subject\":\"测试\",\"body\":\"商品1\",\"notify_url\":\"http://rms.vipgz4.idcfengye.com/\",\"sign_type\":\"MD5\",\"nonce_str\":\"45454545454\",\"sign\":\"C380BEC2BFD727A4B6845133519F3AD6\"}";
        URL httpUrl = new URL("http://uchannel.payexpress.biz/payfront/json/trans/codePassive");
        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","application/json");
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(reqBody.getBytes(UTF8));
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException var18) {
                var18.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }

        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }
        System.out.println(resp);
        return resp;
    }

    private void setRequestHeader(HttpServletRequest request, String key, String value){
        Class<?extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 =(MimeHeaders)headers.get(o1);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            logger.error("请求API ： 设置ClientID到请求头失败。", e);
        }

    }

}

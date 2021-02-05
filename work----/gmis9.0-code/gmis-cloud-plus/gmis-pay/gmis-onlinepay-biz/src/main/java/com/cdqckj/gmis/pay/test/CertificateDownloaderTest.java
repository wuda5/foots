package com.cdqckj.gmis.pay.test;

import com.cdqckj.gmis.pay.elias.CertificateDownloader;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.net.URI;
import java.security.PrivateKey;

/**
 * @author: chen
 * @date: 2019/7/25
 **/

public class CertificateDownloaderTest {
    //用于证书解密的密钥(管理员身份登录微信商户平台自己设置)
    private String apiV3key = "e1dea31d334f48948313d90ac71efabb";
    // 商户号
    private static String mchId = "1603115705";
    // 商户证书序列号
    private static String mchSerialNo = "2339B893C2371AC03D7EB6A56840AFEB595F1C8E";
    // 商户私钥
    private static String mchPrivateKeyFilePath = "certificate/apiclient_key.pem";
    // 微信支付平台证书(非必须)
    private static String wechatpayCertificateFilePath = "";
    //下载成功后保存证书的路径
    private static String outputFilePath = "weixinpay/certificate";
    private CloseableHttpClient httpClient;
    private AutoUpdateCertificatesVerifier verifier;
    private static String privateKey = "-----BEGIN PRIVATE KEY-----\n"
            +"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDinVSNnWJ/aLD7\n" +
            "QragBg++wUZ3apmQnkZ8NHof3RkoxHMHvhYqxq03Q8i5gXB1JELijr/trXNB88be\n" +
            "JqKtlk5WLHhbmDN/hzz3+b1Yv9vcFEZM8UQJXXQ1FXbupo8SwbEoIZTWQ8nRIffI\n" +
            "8WskuHj4S+Tb594lWtz/w9Lxj5cBkaYvXx4d6IbCO+CP68qYEPXZ793F7DVNJPF1\n" +
            "AROD0PMjEDnoAB6OWpnp3nUJJJmyVUYGaRBco5aArTkRRXC1Ihqjvsuqp7qtsOYZ\n" +
            "ParjmAAgdE2Q9avoEtlfY82rFLscLSF3z9qquy9MzU1AotjgZz5G38MEKguyV+RN\n" +
            "Vlts+io3AgMBAAECggEAcRPMozWLdsQIu/fYJRWhObA7t07L/evchBGzdr7e1Qbh\n" +
            "11U0vneQ62i5ekdqlClZ7q8IelL4lkpMS4G/3xqHUFy0WhAiclpLQ/msT+K8lJ7R\n" +
            "TYd+SaYGXI6vX/pgnh1khv/qwtfklqR6fhxpOFOMmWlVc53JrZ4fdMiEM+FQmojg\n" +
            "c6ZI0mvOVcZ3v7g6T5x0cgWyOhK8kRWM0K72tv+s9XQTSCSYSwXZ81oV6uALxTG+\n" +
            "/pqHIaNlUwsFXb3zhEAAR+dfj7X+HH0lGCkqqewq9hhDRVOQrbvhe6hari4xJNJn\n" +
            "1HTN3VooulXbdv0ofX50/ipJMPfkPfdty545//exQQKBgQD6ZxQOFvpel+a5xYvz\n" +
            "5uX9wDPxzFw9I+xBL2fyB3CJvMsM6Xeo0rdlQY/B7FyDjNj6mkvUw7LwfDmN/wVQ\n" +
            "eoJQxsWiqYe72vU5I07xZnZ/KYORN59FJhKMTAh2hug/CtMZ7wKQL79WEaTX3Swc\n" +
            "/lV8hOi4IrrX2d3uN9cHc1iARwKBgQDnriAY7hwaWAsdkT5tZzNMSC+XHkT4NZrX\n" +
            "FSSWmNZORgZ7Mmt16H4LjLRlApanXeJXqbn08CyS/cOHblj6hsgFi/ARYsc9agsu\n" +
            "ykOvpeDZ3ECGwob3yi9m7Zwz6PAyVU34AxMR0XRjOVJXYkzjWR0+tSXgUPqNRt+K\n" +
            "8CgHue1ukQKBgQDITPw16RuN32So5eT1zZXcTYs/uIFwRvQNkKZNbLYQ9/xU78zQ\n" +
            "Nv6M+MzOCwxWDqziidpu77yLslM3yu+tWNI1W0tsur/g/V17v0q+v3+6aLLdzuiQ\n" +
            "n8vmfkumxHOzOi1zaUVboVYkuyhppHpNFwW6/XZLFTzwvU5R6EkRpKr+sQKBgQDK\n" +
            "rZzs0lmJfl5zBl5brT3GTRw9EsD5d0O3R4rTqG9K2J3Q5wH1i2fBSN2DxGjxkANz\n" +
            "90p0CiykhxWoBBQZTxHrMEnbm9N7Wargyxe9sNrtHG7itYkHezxOyi2th+mhzti/\n" +
            "0Ei9fMRaDYqIYbmr5ojcE/NHsvAN6eOkE4ahg7k/0QKBgCF3EI+hJcSp3Z4xdL5Q\n" +
            "10WpX9b+aHKhV+OzGH3VKWNN1rk3B96b6/qH8v3KqXfPDTF8iYv4BNjL9sdrwdKT\n" +
            "5NToYSkm4UzzcGJT7pSW0ezwnbA0HAeYfyyerNhwZnBTe8vfwsJjaEf1B9qOKMz7\n" +
            "CDDML0/gUrayglRhRKj3EJ6H"
            + "-----END PRIVATE KEY-----\n";

    @Before
    public void setup() throws IOException {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(privateKey.getBytes("utf-8")));

        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3key.getBytes("utf-8"));

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    @After
    public void after() throws IOException {
        httpClient.close();
    }

    /**
     * 下载平台证书到指定位置
     */
    @Test
    public void testCertDownload() {
        String[] args = {"-k", apiV3key, "-m", mchId, "-f", mchPrivateKeyFilePath,
                "-s", mchSerialNo, "-o", outputFilePath};//, "-c", wechatpayCertificateFilePath
        CommandLine.run(new CertificateDownloader(), args);
    }

    /**
     * 图片上传
     * @throws Exception
     */
    @Test
    public void uploadImageTest() throws Exception {
        String filePath = "C:\\Users\\Administrator\\Pictures\\test.jpg";

        URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");

        File file = new File(filePath);
        try (FileInputStream s1 = new FileInputStream(file)) {
            String sha256 = DigestUtils.sha256Hex(s1);
            try (InputStream s2 = new FileInputStream(file)) {
                WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
                        .withImage(file.getName(), sha256, s2)
                        .build();

                CloseableHttpResponse response1 = httpClient.execute(request);
                assertEquals(200, response1.getStatusLine().getStatusCode());
                try {
                    HttpEntity entity1 = response1.getEntity();
                    String s = EntityUtils.toString(entity1);
                    System.out.println("图片："+s);
                } finally {
                    response1.close();
                }
            }
        }
    }

}

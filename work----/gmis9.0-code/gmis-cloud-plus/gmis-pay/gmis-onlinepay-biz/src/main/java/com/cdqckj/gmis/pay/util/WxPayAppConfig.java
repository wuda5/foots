package com.cdqckj.gmis.pay.util;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Setter
@Getter
@Component(value = "wxPayAppConfig")
@ConfigurationProperties(prefix = "pay.wxpay.app1")
public class WxPayAppConfig implements WXPayConfig {

    //用于证书解密的密钥(管理员身份登录微信商户平台自己设置)
    @Value("${gmis.service.apiV3key}")
    private String apiV3key;

    // 商户证书序列号
    @Value("${gmis.service.mchSerialNo}")
    private String mchSerialNo;

    /**
     * 默认回调地址
     */
    @Value("${gmis.service.defaultUrl}")
    private String defaultUrl;

    /**
     * 服务商的APPID
     */
    @Value("${gmis.service.appId}")
    private String appID;

    /**
     * 服务商商户号
     */
    @Value("${gmis.service.mchId}")
    private String mchID;

    /**
     * API 密钥
     */
    @Value("${gmis.service.apiKey}")
    private String key;

    /**
     * 商户sub_Appid
     */
    @Value("${gmis.service.appSubAppid}")
    private String appSubAppid;

    /**
     * 商户小程序sub_Appid
     */
    @Value("${gmis.service.appletSubAppid}")
    private String appletSubAppid;

    /**
     * API证书绝对路径 (本项目放在了 resources/cert/wxpay/apiclient_cert.p12")
     */
    private String certPath = "certificate/apiclient_cert.p12";

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    private int httpConnectTimeoutMs = 8000;

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    private int httpReadTimeoutMs = 10000;

    /**
     * 微信支付异步通知地址
     */
    private String payNotifyUrl;

    /**
     * 微信退款异步通知地址
     */
    @Value("${gmis.service.refundUrl}")
    private String refundNotifyUrl;

    @Override
    public InputStream getCertStream() {
        InputStream certStream  =getClass().getClassLoader().getResourceAsStream(certPath);
        return certStream;
    }

    @Override
    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
        this.httpConnectTimeoutMs = httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        this.httpReadTimeoutMs = httpReadTimeoutMs;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getRefundNotifyUrl() {
        return refundNotifyUrl;
    }

    public void setRefundNotifyUrl(String refundNotifyUrl) {
        this.refundNotifyUrl = refundNotifyUrl;
    }
}

package com.cdqckj.gmis.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.pay.elias.CertificateDownloader;
import com.cdqckj.gmis.pay.elias.util.CertUtil;
import com.cdqckj.gmis.pay.service.WxConfService;
import com.cdqckj.gmis.pay.util.WxPayAppConfig;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import picocli.CommandLine;

import java.io.*;
import java.net.URI;
import java.security.PrivateKey;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class WxConfServiceImpl implements WxConfService {

    @Autowired
    private WxPayAppConfig config;

    //用于证书解密的密钥(管理员身份登录微信商户平台自己设置)
    private String apiV3key;
    // 商户号
    private static String mchId;
    // 商户证书序列号
    private static String mchSerialNo;
    // 微信支付平台证书(非必须)
    private static String wechatpayCertificateFilePath = "";
    //下载成功后保存证书的路径
    private static String outputFilePath = "weixinpay/certificate";
    private PrivateKey merchantPrivateKey;
    private AutoUpdateCertificatesVerifier verifier;
    private CloseableHttpClient httpClient;

    public void init() throws IOException {
        mchId = null==mchId? config.getMchID():mchId;
        apiV3key = null==apiV3key? config.getApiV3key():apiV3key;
        mchSerialNo = null==mchSerialNo? config.getMchSerialNo():mchSerialNo;
        //privateKey = null==privateKey? CertUtil.getResourceByPath(CertUtil.mchPrivateKeyFilePath):privateKey;
        merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(CertUtil.privateKey.getBytes("utf-8")));
        //使用自动更新的签名验证器，不需要传入证书
        verifier = new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3key.getBytes("utf-8"));
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();
    }

    @Override
    public R<String> uploadImage(MultipartFile simpleFile) throws Exception {
        init();
        String oldFileName = simpleFile.getOriginalFilename();
        URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");
        File file = new File(UUID.randomUUID().toString()+oldFileName.substring(oldFileName.lastIndexOf(".")));
        //InputStream inputStream = simpleFile.getInputStream();
        FileUtils.copyInputStreamToFile(simpleFile.getInputStream(), file);
        simpleFile.transferTo(file);
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
                    return R.success(s);
                } finally {
                    response1.close();
                    s2.close();
                    s1.close();
                    httpClient.close();
                    FileUtils.forceDelete(file);
                }
            }
        }
    }

    /**
     * 下载证书到服务器
     */
    @Override
    public void certDownload() throws Exception {
        init();
        String[] args = {"-k", apiV3key, "-m", mchId, "-f", CertUtil.mchPrivateKeyFilePath,
                "-s", mchSerialNo, "-o", outputFilePath};//, "-c", wechatpayCertificateFilePath
        CommandLine.run(new CertificateDownloader(), args);
    }

}

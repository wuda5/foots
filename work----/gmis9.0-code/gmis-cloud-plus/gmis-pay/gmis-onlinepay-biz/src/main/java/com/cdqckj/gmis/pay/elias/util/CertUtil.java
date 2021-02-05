package com.cdqckj.gmis.pay.elias.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class CertUtil {
    public static String privateKey;
    // 商户私钥
    public static String mchPrivateKeyFilePath = "certificate/apiclient_key.pem";

    static {
        try {
            privateKey = null==privateKey? CertUtil.getResourceByPath(mchPrivateKeyFilePath):privateKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取私钥。
     *
     * @param  私钥文件路径 (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey() throws IOException {
        return PemUtil.loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
    }

    /**
     * 获取微信平台证书序列号
     *
     * @return
     * @throws Exception
     */
    public static String getCertSerialNo() throws Exception {
        try {
            String str = HttpUrlUtil.sendGet();
            System.out.println(str);
            JSONObject json = JSONObject.fromObject(str);
            JSONArray jsonArray = JSONArray.fromObject(json.optString("data"));
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return jsonObject.optString("serial_no");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取配置文件
     * @param path
     * @return
     * @throws IOException
     */
    public static String getResourceByPath(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer data = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            data.append(str+"\n");
        }
        br.close();
        isr.close();
        is.close();
        System.out.println(data);
        return data.toString();
    }

    /*// 微信证书私钥路径（从微信商户平台下载，保存在本地）
    public static String APICLIENT_KEY = "C:\\Users\\Administrator\\Desktop\\test\\apiclient_key.pem";
    *//**
     * 获取私钥。
     *
     * @param  私钥文件路径 (required)
     * @return 私钥对象
     *//*
    public static PrivateKey getPrivateKey() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(APICLIENT_KEY)), StandardCharsets.UTF_8);
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }*/
}

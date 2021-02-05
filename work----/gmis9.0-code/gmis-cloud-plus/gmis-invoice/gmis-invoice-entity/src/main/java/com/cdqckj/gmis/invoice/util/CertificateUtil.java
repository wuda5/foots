package com.cdqckj.gmis.invoice.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * 瑞宏证书工具类
 *
 * @author ASUS
 */
public class CertificateUtil {

    /**
     * Java密钥库(Java 密钥库，JKS)KEY_STORE
     */
    public static final String KEY_STORE = "JKS";

    /**
     * <p>
     * 获得密钥库
     * </p>
     *
     * @param keyStorePath 密钥库存储路径
     * @param password     密钥库密码
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
        URL remoteFile = new URL(keyStorePath);
        System.out.println("打开HttpURLConnection.");
        HttpURLConnection httpConn = (HttpURLConnection) remoteFile.openConnection();
        System.out.println("得到HttpInputStream.");
        InputStream httpIn = httpConn.getInputStream();

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(httpIn, password.toCharArray());
        httpIn.close();
        System.out.println("关闭HttpURLConnection.");
        httpConn.disconnect();
        return keyStore;
    }

    /**
     * <p>
     * 获得密钥库
     * </p>
     *
     * @param inputStream 密钥库文件流
     * @param password    密钥库密码
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStoreByInputStream(InputStream inputStream, String password)
            throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(inputStream, password.toCharArray());
        return keyStore;
    }

    /**
     * 从文件中加载私钥
     *
     * @param keyStorePath     密钥库文件路径
     * @param certificateAlias 证书别名
     * @param certificatePwd   证书密码
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKeyByFile(String keyStorePath, String certificateAlias,
                                                  String certificatePwd) throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, certificatePwd);
        //加载私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(certificateAlias, certificatePwd.toCharArray());
        return privateKey;
    }

    /**
     * 签名
     *
     * @param message          需要签名的内容
     * @param keyStorePath     密钥库存储路径
     * @param certificateAlias 证书别名
     * @param certificatePwd   证书密码
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] message, String keyStorePath, String certificateAlias,
                              String certificatePwd) throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, certificatePwd);

        X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(certificateAlias);
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(certificateAlias, certificatePwd.toCharArray());

        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(message);
        return signature.sign();
    }

    /**
     * 签名
     *
     * @param message          需要签名的内容
     * @param inputStream      密钥库输入流
     * @param certificateAlias 证书别名
     * @param certificatePwd   证书密码
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] message, InputStream inputStream, String certificateAlias,
                              String certificatePwd) throws Exception {

        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(inputStream, certificatePwd.toCharArray());

        X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate(certificateAlias);
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(certificateAlias, certificatePwd.toCharArray());

        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(message);
        return signature.sign();
    }

    /**
     * <p>
     * 生成数据签名并以BASE64编码
     * </p>
     *
     * @param data         源数据
     * @param keyStorePath 密钥库存储路径
     * @param alias        密钥库别名
     * @param password     密钥库密码
     * @return
     * @throws Exception
     */
    public static String signToBase64(byte[] data, String keyStorePath, String alias, String password)
            throws Exception {
        return Base64.getEncoder().encodeToString(sign(data, keyStorePath, alias, password));
    }

    /**
     * <p>
     * 生成数据签名并以BASE64编码
     * </p>
     *
     * @param data        源数据
     * @param inputStream 密钥库文件流
     * @param alias       密钥库别名
     * @param password    密钥库密码
     * @return
     * @throws Exception
     */
    public static String signToBase64(byte[] data, InputStream inputStream, String alias, String password)
            throws Exception {
        return Base64.getEncoder().encodeToString(sign(data, inputStream, alias, password));
    }

    public static InputStream getStoreKeyFile(URL remoteFile) {

        HttpURLConnection httpConn = null;
        InputStream httpIn = null;
        try {
            System.out.println("打开HttpURLConnection.");
            httpConn = (HttpURLConnection) remoteFile.openConnection();
            System.out.println("得到HttpInputStream.");
            httpIn = httpConn.getInputStream();
            System.out.println("关闭HttpURLConnection.");
            httpConn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return httpIn;
    }

}

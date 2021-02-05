package com.cdqckj.gmis.pay.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 微信退款解密工具类
 */
public class RefundDecryptUtil {
    private static MessageDigest sMd5MessageDigest;
    private static StringBuilder sStringBuilder;
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * 微信退款解密接口
     * @param reqInfoSecret
     * @param key
     * @return
     */
    public static String getRefundDecrypt(String reqInfoSecret, String key) {
        String result = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            byte[] bt = decoder.decodeBuffer(reqInfoSecret);
            String b = new String(bt);
            String md5key = md5(key).toLowerCase();

            System.out.println(md5key);

            SecretKey secretKey = new SecretKeySpec(md5key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] resultbt = cipher.doFinal(bt);
            result = new String(resultbt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String md5(String s) {
        sMd5MessageDigest.reset();
        sMd5MessageDigest.update(s.getBytes());
        byte[] digest = sMd5MessageDigest.digest();
        sStringBuilder.setLength(0);

        for (int i = 0; i < digest.length; ++i) {
            int b = digest[i] & 255;
            if (b < 16) {
                sStringBuilder.append('0');
            }

            sStringBuilder.append(Integer.toHexString(b));
        }

        return sStringBuilder.toString().toUpperCase();
    }

    static {
        try {
            sMd5MessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {

        }

        sStringBuilder = new StringBuilder();
    }

}
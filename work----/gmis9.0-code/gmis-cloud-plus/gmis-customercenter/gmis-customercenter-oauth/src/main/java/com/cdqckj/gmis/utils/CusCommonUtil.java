package com.cdqckj.gmis.utils;

import cn.hutool.crypto.digest.MD5;
import com.cdqckj.gmis.constants.CusOauthConstants;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

/**
 * 通用工具类
 * @author hc
 * @date 2020/11/20
 */
public class CusCommonUtil {

    /**
     * 生成redis认证key
     * @param open_token 租户编码
     * @param open_token 手机号
     * @return
     */
    public static String buildCatchKey(String open_token){
        StringBuilder builder = new StringBuilder(CusOauthConstants.REDIS_CATCH_DIFF);
        builder.append(CusOauthConstants.OAUTH);
        builder.append(open_token);
        return builder.toString();
    }

    /**
     * 生成认证凭证
     * @author hc
     * @param len 指定长度
     * @return
     */
    public static String buildAuthVoucher(int len){

        String alphabetic = RandomStringUtils.randomAlphabetic(len);
        String timeStiff =  String.valueOf(LocalDateTime.now().getSecond());

        return MD5.create().digestHex(alphabetic+timeStiff);
    }
}

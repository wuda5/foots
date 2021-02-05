package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: lijianguo
 * @time: 2020/12/09 16:27
 * @remark: 请添加类说明
 */
public class CacheKeyUtil {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/9 16:28
    * @remark 根据不同的租户生成
    */
    public static String createTenantKey(String ... keys){
        String tenant = BaseContextHandler.getTenant();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(tenant)) {
            sb.append(tenant).append(":");
        }
        return stringAppend(sb, keys);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 9:37
    * @remark 不添加租户
    */
    public static String createNoTenantKey(String ... keys){
        StringBuilder sb = new StringBuilder();
        return stringAppend(sb, keys);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/11 13:24
    * @remark 字符串的拼接
    */
    private static String stringAppend(StringBuilder sb, String[] keys) {
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            sb.append(key);
            char c = sb.charAt(sb.length() - 1);
            if (':' != c && i < keys.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

}

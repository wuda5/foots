package com.cdqckj.gmis.common.properties;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * 忽略token 配置类
 * <p>
 * 做接口权限时，考虑修改成读取配置文件
 *
 * @author gmis
 * @date 2019/01/03
 */
@Data
@ConfigurationProperties(prefix = IgnoreTokenProperties.PREFIX)
public class IgnoreTokenProperties {
    public static final String PREFIX = "ignore.token";
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private List<String> baseUrl = CollUtil.newArrayList(
            "/error",
            "/actuator/**",
            "/gate/**",
            "/static/**",
            "/anno/**",
            "/**/anno/**",
            "/**/iotQc/**",
            "/**/swagger-ui.html",
            "/**/doc.html",
            "/**/webjars/**",
            "/**/v2/api-docs/**",
            "/**/v2/api-docs-ext/**",
            "/**/swagger-resources/**",
            "/**/bill/rhKpCallback"

    );

    /**
     * pc应用忽略uri
     *
     * @updater hc
     * @date 2020/10/14
     */
    private List<String> url = CollUtil.newArrayList(
            "/error",
            "/actuator/**",
            "/gate/**",
            "/static/**",
            "/**/iotQc/**",
            "/**/swagger-ui.html",
            "/**/doc.html",
            "/**/webjars/**",
            "/**/v2/api-docs/**",
            "/**/v2/api-docs-ext/**",
            "/**/swagger-resources/**",

            "/anno/**",
            "/**/anno/**",
            "/authority/tenant/code/**",
            "/**/bill/rhKpCallback"
    );

    /**
     * app应用忽略uri
     *
     * @auther hc
     * @date 2020/10/14
     */
    private List<String> appUrl = CollUtil.newArrayList(
            "/error",
            "/actuator/**",
            "/gate/**",
            "/static/**",
            "/**/swagger-ui.html",
            "/**/doc.html",
            "/**/webjars/**",
            "/**/v2/api-docs/**",
            "/**/v2/api-docs-ext/**",
            "/**/swagger-resources/**",

            "/customercenter/cusOauth/ticket/**",
            "/customercenter/cusOauth/token/**",
            "/**/bill/rhKpCallback"
    );

    /**
     * 获取pc应用忽略uri
     *
     * @param path
     * @return
     * @updater hc
     */
    public boolean isIgnoreToken(String path) {
        return getUrl().stream().anyMatch((url) -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path));
    }

    /**
     * 获取app应用忽略uri
     *
     * @param path
     * @return
     * @auther hc
     */
    public boolean isAppIgnoreToken(String path) {
        return getAppUrl().stream().anyMatch((url) -> path.startsWith(url) || ANT_PATH_MATCHER.match(url, path));
    }
}

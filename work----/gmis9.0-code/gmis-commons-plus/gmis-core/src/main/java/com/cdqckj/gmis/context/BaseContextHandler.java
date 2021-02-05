package com.cdqckj.gmis.context;

import cn.hutool.core.convert.Convert;
import com.cdqckj.gmis.utils.StrPool;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取当前域中的 用户id appid 用户昵称
 * 注意： appid 通过token解析，  用户id 和 用户昵称必须在前端 通过请求头的方法传入。 否则这里无法获取
 *
 * @author gmis
 * @createTime 2017-12-13 16:52
 */
public class BaseContextHandler {
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, String> map = getLocalMap();
        map.put(key, value == null ? StrPool.EMPTY : value.toString());
    }

    public static <T> T get(String key, Class<T> type) {
        Map<String, String> map = getLocalMap();
        return Convert.convert(type, map.get(key));
    }

    public static <T> T get(String key, Class<T> type, Object def) {
        Map<String, String> map = getLocalMap();
        return Convert.convert(type, map.getOrDefault(key, String.valueOf(def == null ? "" : def)));
    }

    public static String get(String key) {
        Map<String, String> map = getLocalMap();
        return map.getOrDefault(key, "");
    }

    public static Map<String, String> getLocalMap() {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setLocalMap(Map<String, String> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }


    public static Boolean getBoot() {
        return get(BaseContextConstants.IS_BOOT, Boolean.class, false);
    }

    /**
     * 账号id
     *
     * @param val
     */
    public static void setBoot(Boolean val) {
        set(BaseContextConstants.IS_BOOT, val);
    }

    /**
     * 账号id
     *
     * @return
     */
    public static Long getUserId() {
        return get(BaseContextConstants.JWT_KEY_USER_ID, Long.class, 0L);
    }

    public static String getUserIdStr() {
        return String.valueOf(getUserId());
    }

    /**
     * 账号id
     *
     * @param userId
     */
    public static void setUserId(Long userId) {
        set(BaseContextConstants.JWT_KEY_USER_ID, userId);
    }

    public static void setUserId(String userId) {
        set(BaseContextConstants.JWT_KEY_USER_ID, userId);
    }

    /**
     * 账号表中的name
     *
     * @return
     */
    public static String getAccount() {
        return get(BaseContextConstants.JWT_KEY_ACCOUNT, String.class);
    }

    /**
     * 账号表中的name
     *
     * @param name
     */
    public static void setAccount(String name) {
        set(BaseContextConstants.JWT_KEY_ACCOUNT, name);
    }


    /**
     * 登录的账号
     *
     * @return
     */
    public static String getName() {
        return get(BaseContextConstants.JWT_KEY_NAME, String.class);
    }

    /**
     * 登录的账号
     *
     * @param account
     */
    public static void setName(String account) {
        set(BaseContextConstants.JWT_KEY_NAME, account);
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public static String getToken() {
        return get(BaseContextConstants.BEARER_HEADER_KEY, String.class);
    }

    public static void setToken(String token) {
        set(BaseContextConstants.BEARER_HEADER_KEY, token);
    }


    public static String getTenant() {
        return get(BaseContextConstants.JWT_KEY_TENANT, String.class);
    }

    public static String getClientId() {
        return get(BaseContextConstants.JWT_KEY_CLIENT_ID, String.class);
    }

    public static void setTenant(String val) {
        set(BaseContextConstants.JWT_KEY_TENANT, val);
    }

    public static void setClientId(String val) {
        set(BaseContextConstants.JWT_KEY_CLIENT_ID, val);
    }

    public static String getGrayVersion() {
        return get(BaseContextConstants.GRAY_VERSION, String.class);
    }

    public static void setGrayVersion(String val) {
        set(BaseContextConstants.GRAY_VERSION, val);
    }


    public static String getTenantId() {
        return get(BaseContextConstants.JWT_KEY_TENANT_ID, String.class);
    }
    public static void setTenantId(String val) {
        set(BaseContextConstants.JWT_KEY_TENANT_ID, val);
    }

    public static void setAppId(String appId){
        set(BaseContextConstants.APP_ID, appId);
    }
    public static String getAppId() {
        return get(BaseContextConstants.APP_ID, String.class);
    }

    public static String getTenantName() {
        return get(BaseContextConstants.JWT_KEY_TENANT_NAME, String.class);
    }

    public static void setTenantName(String val) {
        set(BaseContextConstants.JWT_KEY_TENANT_NAME, val);
    }



    public static Long getOrgId() {
        return get(BaseContextConstants.JWT_KEY_ORG_ID, Long.class);
    }
    public static void setOrgId(Long val) {
        set(BaseContextConstants.JWT_KEY_ORG_ID, val);
    }



    public static Integer getLang() {
        return get(BaseContextConstants.LANG_TYPE, Integer.class);
    }
    public static void setLang(Integer val) {
        set(BaseContextConstants.LANG_TYPE, val);
    }



    public static void setOrgId(String val) {
        set(BaseContextConstants.JWT_KEY_USER_ID, val);
    }

    public static String getOrgName() {
        return get(BaseContextConstants.JWT_KEY_ORG_NAME, String.class);
    }

    public static void setOrgName(String val) {
        set(BaseContextConstants.JWT_KEY_ORG_NAME, val);
    }

    public static void remove() {
        if (THREAD_LOCAL != null) {
            THREAD_LOCAL.remove();
        }
    }

}

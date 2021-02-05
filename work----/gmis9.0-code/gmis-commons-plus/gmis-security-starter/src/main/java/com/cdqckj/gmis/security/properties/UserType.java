package com.cdqckj.gmis.security.properties;

/**
 * 调用用户信息的类型
 *
 * @author gmis
 * @date 2020年02月24日10:47:49
 */
public enum UserType {
    /**
     * feign 远程调用
     */
    FEIGN,
    /**
     * Service 本地调用
     */
    SERVICE,
    ;
}

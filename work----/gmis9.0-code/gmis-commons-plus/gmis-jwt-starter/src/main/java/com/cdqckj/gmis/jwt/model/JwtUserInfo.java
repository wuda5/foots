package com.cdqckj.gmis.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * jwt 存储的 内容
 *
 * @author gmis
 * @date 2018/11/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserInfo implements Serializable {
    /**
     * 账号id
     */
    private Long userId;
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;

    private String tenantId;

    private String tenantName;

    private Long orgId;

    private String orgName;

    private String appId;

    private String appSecret;

}

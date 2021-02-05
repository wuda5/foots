package com.cdqckj.gmis.iot.qc.qnms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 签名公钥、私钥配置
 * @author: 秦川物联网科技
 * @date: 2020/10/12 15:05
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@Configuration
@ConfigurationProperties(prefix="sign")
public class SignKeyConfig {
    private String publicKey;
    private String privateKey;
}

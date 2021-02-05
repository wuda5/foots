package com.cdqckj.gmis.sms.properties;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * @author gmis
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "gmis.provider")
@RefreshScope
public class SmsServerProperties {
    /**
     * 为以下3个值，指定不同的自动化配置
     * qiniu：七牛oss
     * aliyun：阿里云oss
     * fastdfs：本地部署的fastDFS
     */
    //private FileStorageType type = FileStorageType.LOCAL;
    private String type = "TENCENT";

    private AliSms ali;

    private TencentSms tencent;

    @Data
    public static class AliSms {
        private String uriPrefix;
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }

    @Data
    public static class TencentSms {
        private String appId;
        private String appSecret;
        private String secretId;
        private String secretKey;
        private String reginName;
        private String bucketName;
        private String path;
    }
}

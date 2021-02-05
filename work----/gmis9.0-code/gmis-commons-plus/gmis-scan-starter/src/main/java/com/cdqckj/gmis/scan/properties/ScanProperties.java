package com.cdqckj.gmis.scan.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统接口扫描配置
 *
 * @author gmis
 * @date 2019/12/17
 */
@Data
@ConfigurationProperties(prefix = ScanProperties.PREFIX)
public class ScanProperties {
    public final static String PREFIX = "gmis.scan";

    private ScanPersistenceType type = ScanPersistenceType.FEIGN;
}

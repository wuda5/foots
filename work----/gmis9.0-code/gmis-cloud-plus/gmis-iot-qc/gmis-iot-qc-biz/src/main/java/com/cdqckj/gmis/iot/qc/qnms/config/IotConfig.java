package com.cdqckj.gmis.iot.qc.qnms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 远传系统配置类
 * @author: 秦川物联网科技
 * @date: 2020/10/12  09:58
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Configuration
@Data
public class IotConfig {
    @Value("${gmis.iot.baseAddress}")
    public String baseAddress;
    @Value("${gmis.cis.address}")
    public String gmisAddress;
    @Value("${gmis.iot.token_time}")
    public long tokenTime;
    @Value("${gmis.iot.subscribe_cache_day}")
    public int subscribeCacheDay;
}
